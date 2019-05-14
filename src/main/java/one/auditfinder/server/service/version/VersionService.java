package one.auditfinder.server.service.version;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.eclipse.jdt.internal.compiler.parser.ParserBasicInformation;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import one.auditfinder.server.mapper.version.VersionMapper;
import one.auditfinder.sever.vo.version.AppInfoVo;
import one.auditfinder.sever.vo.version.HotfixVo;
import one.auditfinder.sever.vo.version.VersionVo;

@Component
public class VersionService {

	@Autowired
	private VersionMapper versionMapper;

	@Value("#{fileConf['fileConf.taskDir']}")
	private String taskDir;

	@Scheduled(initialDelay = 10000, fixedDelay = 3600000)
	public void getVersionScheduled() throws IOException, ParseException {
		getHotfixInfo();
		getAcrobatreaderInfo();
		getflashplayerInfo();
		getJavaInfo();

	}

	public void getHotfixInfo() throws IOException, ParseException {
		FileOutputStream fos = null;
		InputStream is = null;
		String targetFilename = taskDir + File.separator + "BulletinSearch.xlsx";

		fos = new FileOutputStream(targetFilename);
		String sourceUrl = "https://download.microsoft.com/download/6/7/3/673E4349-1CA5-40B9-8879-095C72D5B49D/BulletinSearch.xlsx";
		URL url = new URL(sourceUrl);
		URLConnection urlConnection = url.openConnection();
		is = urlConnection.getInputStream();
		byte[] buffer = new byte[1024];
		int readBytes;
		while ((readBytes = is.read(buffer)) != -1) {
			fos.write(buffer, 0, readBytes);
		}
		if (fos != null) {
			fos.close();
		}
		if (is != null) {
			is.close();
		}
		File file = new File(targetFilename);
		XSSFWorkbook wb;

		wb = new XSSFWorkbook(new FileInputStream(file));
		DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
		int cnt = versionMapper.selectHotfixDatacount();
		Date newest = null;

		if (cnt == 0) {
			String date = "1900-01-01";
			newest = df.parse(date);
		} else {
			newest = versionMapper.selectHotfixNewest();
		}

		for (Row row : wb.getSheetAt(0)) {
			HotfixVo vo = new HotfixVo();
			if (row.getRowNum() > 0) {
				Date data = row.getCell(0).getDateCellValue();
				int compare = newest.compareTo(data);

				if (compare < 0) {
					String str = df.format((row.getCell(0).getDateCellValue()));
					vo.setDate_posted(str);
					vo.setBulletin_id(row.getCell(1).getStringCellValue());
					if (row.getCell(2) == null) {
						vo.setBulletin_kb(" ");
					} else {
						vo.setSeverity(row.getCell(3).getStringCellValue());
						vo.setImpact(row.getCell(4).getStringCellValue());
						vo.setTitle(row.getCell(5).getCellFormula());
						vo.setAffected_product(row.getCell(6).getStringCellValue());

						if (row.getCell(7) == null) {
							vo.setComponent_kb(" ");
						} else {
							vo.setComponent_kb(row.getCell(7).getStringCellValue());
						}
						if (row.getCell(8) == null) {
							vo.setAffected_component("");
						} else {
							vo.setAffected_component(row.getCell(8).getStringCellValue());
						}

						vo.setImpact1(row.getCell(9).getCellFormula());
						vo.setSeverity1(row.getCell(10).getStringCellValue());

						if (row.getCell(11) == null) {
							vo.setSupersedes("");
						} else {
							vo.setSupersedes(row.getCell(11).getStringCellValue());
						}
						if (row.getCell(12) == null) {
							vo.setReboot("");
						} else {
							vo.setReboot(row.getCell(12).getStringCellValue());
						}
						if (row.getCell(13) == null) {
							vo.setCves("");

						} else {
							vo.setCves(row.getCell(13).getStringCellValue());
						}
						versionMapper.insertHotfix(vo);
						vo = null;
						compare = ' ';
					}
				}
			}
			file.delete();
		}

	}

	public void getAcrobatreaderInfo() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat df1 = new SimpleDateFormat("MM/dd/yyyy");
		int cnt = versionMapper.select_acrobat_datacount();
		Date newest = null;

		if (cnt == 0) {
			String date = "1900-01-01";

			try {
				newest = df.parse(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}

		} else {
			newest = versionMapper.select_actobat_newest();
		}

		String versionRegex = "^Version \\d{2,4}.\\d{1,3}.\\d{2,5}$";
		String url = "http://www.adobe.com/support/downloads/product.jsp?product=10&platform=Windows";

		Document doc;
		try {

			doc = Jsoup.connect(url).get();
			Elements tableElements = doc.select("table");

			for (Element tmp : tableElements) {
				Elements tableRowElements = tmp.select(":not(thead tr");
				String version = "";
				for (int i = 0; i < tableRowElements.size(); i++) {
					VersionVo vo = new VersionVo();
					Element row = tableRowElements.get(i);
					Elements rowItems = row.select("th");
					Elements rowItems1 = row.select("td");
					if (rowItems.size() == 1 && rowItems.get(0).text().matches(versionRegex)) {
						version = rowItems.get(0).text();
					}
					if (!version.equals("")) {
						vo.setVersion(version);
						if (rowItems1.size() == 3) {
							vo.setVersion_name(rowItems1.get(0).text());
							vo.setVersion_size(rowItems1.get(1).text());

							Date data;

							try {
								data = df1.parse(rowItems1.get(2).text());
								int compare = newest.compareTo(data);
								if (compare < 0) {
									vo.setVersion_date(df.format(data));
								}
							} catch (ParseException e) {
								e.printStackTrace();
							}
						}
						if (vo.getVersion() != null && vo.getVersion_date() != null
								&& vo.getVersion_name() != null & vo.getVersion_size() != null) {

							versionMapper.insertAcrobat(vo);
						}
					} else {
						break;
					}
				}

			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void getflashplayerInfo() {
		String version = "";
		String url = "http://www.adobe.com/software/flash/about/";

		try {
			Document doc = Jsoup.connect(url).get();
			Elements tableElements = doc.select("table");

			for (Element tmp : tableElements) {
				Elements tableRowElements = tmp.select(":not(thead) tr");

				for (int i = 0; i < tableRowElements.size(); i++) {
					VersionVo vo = new VersionVo();
					Element row = tableRowElements.get(i);
					Elements rowItems1 = row.select("td");

					if (rowItems1.size() == 3)
						version = rowItems1.get(0).text();
					if (rowItems1.size() != 0) {
						vo.setVersion_platform(version);
						if (rowItems1.size() == 3) {
							vo.setVersion_name(rowItems1.get(1).text());
							vo.setVersion(rowItems1.get(2).text());
						} else {
							vo.setVersion_name(rowItems1.get(0).text());
							vo.setVersion(rowItems1.get(1).text());
						}
						String v_name = versionMapper.select_flash_info(vo.getVersion_name(), vo.getVersion_platform());
						if (v_name == null)
							versionMapper.insertFlash(vo);
						else if (!v_name.equals(vo.getVersion())) {
							versionMapper.updateFlash(vo);
						}
					}
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void getJavaInfo() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat df1 = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
		int cnt = versionMapper.select_java_datacount();
		Date newest = null;
		if (cnt == 0) {
			String date = "1900-01-01";

			try {
				newest = df.parse(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else {
			newest = versionMapper.select_java_newest();
		}
		String url = "https://java.com/en/download/faq/release_dates.xml";

		Document doc;
		try {
			doc = Jsoup.connect(url).get();
			Elements tableElements = doc.select("table");

			for (Element tmp : tableElements) {
				Elements tableRowElements = tmp.select(":not(thead) tr");
				for (int i = 1; i < tableRowElements.size(); i++) {
					VersionVo vo = new VersionVo();
					Element row = tableRowElements.get(i);
					Elements rowItems1 = row.select("td");
					doc.select("a").unwrap();
					doc.select("sup").remove();
					String index0 = rowItems1.get(0).html();
					String a[] = index0.split("<br/>");

					try {
						Date data = df1.parse(rowItems1.get(1).text());
						for (int j = 0; j < a.length; j++) {
							int compare = newest.compareTo(data);
							if (compare < 0)
								vo.setVersion_date(df.format(data));
							vo.setVersion_name(a[j]);
							if (vo.getVersion_name() != null && vo.getVersion_date() != null)
								versionMapper.insertJava(vo);
						}
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	
}
