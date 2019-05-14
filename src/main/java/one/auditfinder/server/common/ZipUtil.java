package one.auditfinder.server.common;


import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class ZipUtil {
	
	private final static Logger log = LoggerFactory.getLogger(ZipUtil.class);
	
	 public static void addFilesToZip(File source, File destination) throws IOException, ArchiveException {
	        OutputStream archiveStream = new FileOutputStream(destination);
	        ArchiveOutputStream archive = new ArchiveStreamFactory().createArchiveOutputStream(ArchiveStreamFactory.ZIP, archiveStream);

	        Collection<File> fileList = FileUtils.listFiles(source, null, true);

	        for (File file : fileList) {
	            String entryName = getEntryName(source, file);
	            ZipArchiveEntry entry = new ZipArchiveEntry(entryName);
	            archive.putArchiveEntry(entry);

	            BufferedInputStream input = new BufferedInputStream(new FileInputStream(file));

	            IOUtils.copy(input, archive);
	            input.close();
	            archive.closeArchiveEntry();
	        }

	        archive.finish();
	        archiveStream.close();
	    }
	 
    private static String getEntryName(File source, File file) throws IOException {
        int index = source.getAbsolutePath().length() + 1;
        String path = file.getCanonicalPath();

        return path.substring(index);
    }
    
    public static File convert(MultipartFile file) throws IOException{
    	File convFile = new File(file.getOriginalFilename());
    	convFile.createNewFile();
    	FileOutputStream fos = new FileOutputStream(convFile);
    	fos.write(file.getBytes());
    	fos.close();
    	return convFile;
    }
    
    public static List<File> unzip(File zippedFile) throws IOException {
    	List<File> flst = unzip(zippedFile, "UTF-8");
    	return flst;
	}
	
	public static List<File> unzip(File zippedFile, String encoding ) throws IOException {
		List<File> flst = unzip(zippedFile, zippedFile.getParentFile(), encoding);
		return flst;
	}
	
	public static List<File> unzip(File zippedFile, File destDir, String encoding) throws IOException {
		List<File> flst = unzip(new FileInputStream(zippedFile), destDir, encoding);
		return flst;
	}
	
	public static List<File> unzip( InputStream is, File destDir, String encoding) throws IOException {
		ZipArchiveInputStream zis ;
		ZipArchiveEntry entry ;
		String name ;
		File target ;
		int nWritten = 0;
		BufferedOutputStream bos ;
		byte [] buf = new byte[1024 * 8];
		
		List<File> flst = new ArrayList<File>();

		ensureDestDir(destDir);
		
		zis = new ZipArchiveInputStream(is, encoding, false);
		
		while ( (entry = zis.getNextZipEntry()) != null ){
			name = entry.getName();
			target = new File (destDir, name);
			if ( entry.isDirectory() ){
				ensureDestDir(target);
			} else {
				target.createNewFile();
				bos = new BufferedOutputStream(new FileOutputStream(target));
				while ((nWritten = zis.read(buf)) >= 0 ){
					bos.write(buf, 0, nWritten);
				}
				bos.close();
				log.debug ("file : " + name);
			}
			flst.add(target);
		}
		zis.close();
		return flst;
	}
	
	private static void ensureDestDir(File dir) throws IOException {
		if(!dir.exists()){
			dir.mkdirs();
		}
	}
	
}
