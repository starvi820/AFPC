package one.auditfinder.server.mapper.version;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import one.auditfinder.sever.vo.version.AcrobatVo;
import one.auditfinder.sever.vo.version.AppInfoVo;
import one.auditfinder.sever.vo.version.HotfixVo;
import one.auditfinder.sever.vo.version.VersionVo;

public interface VersionMapper {
	
	final String INSERT_HOTFIX = "INSERT INTO af_hotfix_info(date_posted,bulletin_id,bulletin_kb,severity,"
			+ "impact,title,affected_product,component_kb,affected_component,supersedes,reboot,cves,impact1,"
			+ "severity1)"
			+ "VALUES(#{date_posted},#{bulletin_id},#{bulletin_kb},#{severity},#{impact},#{title},"
			+ "#{affected_product},#{component_kb},#{affected_component},#{supersedes},#{reboot},"
			+ "#{cves},#{impact1},#{severity1})";
	
	final String GET_HOTFIX_INFO = "SELECT date_posted,bulletin_id,bulletin_kb,severity, "
			 + "  impact,title,affected_product,component_kb,affected_component,supersedes,reboot,cves,impact1, "
			+ "severity1 FROM af_hotfix_info";
	
	final String SELECT_HOTFIX_NEWEST = "SELECT date_posted FROM af_hotfix_info order by date_posted desc limit 1";
	
	
	final String SELECT_HOTFIX_DATACOUNT = "SELECT count(seq) FROM af_hotfix_info";
	
	@Insert(INSERT_HOTFIX)
	void insertHotfix(HotfixVo hotvo);
	
	@Select(GET_HOTFIX_INFO)
	List<HotfixVo> getHotfixList();
	
	@Select(SELECT_HOTFIX_NEWEST)
	Date selectHotfixNewest();
	
	@Select(SELECT_HOTFIX_DATACOUNT)
	int selectHotfixDatacount();

	// acrobat reader
	
	final String INSERT_ACROBATREADER = "insert into af_actobatreader_info(acrobatVersion,acrobatName,acrobatSize,acrobatDate) values (#{acrobatVersion},#{acrobatName},#{acrobatSize},#{acrobatDate})";
	
	final String SELECT_ACROBAT_NEWEST = "select acrobatDate from af_acrobatreader_info order by acrobatDate desc limit 1";
	
	final String SELECT_ACROBAT_DATACOUNT = "select count(acrobatId) from af_acrobatreader_info";
	
	@Insert(INSERT_ACROBATREADER)
	void insertAcrobat(VersionVo vo);
	
	@Select(SELECT_ACROBAT_NEWEST)
	Date select_actobat_newest();
	
	@Select(SELECT_ACROBAT_DATACOUNT)
	int select_acrobat_datacount();
	
	//flash player
	
	final String INSERT_FLASH = "insert into af_flash_info(flashId,flashPlatform,flashBrowser,flashVersion)values(#{flashId},#{flashPlatform},#{flashBrowser},#{flashVersion})";
	
	final String SELECT_FLASH_INFO = "select flashVersion from af_flash_info where flashBrowser = #{flashName} and flashPlatform = #{flashPlatform}";
	
	final String UPDATE_FLASH = "update af_flash_info set flashVersion = #{flashVersion} where flashBrowser=#{flashBrowser} and flashPlatform = #{flashPlatform}";
	
	@Update(UPDATE_FLASH)
	void updateFlash(VersionVo vo);
	
	@Insert(INSERT_FLASH)
	void insertFlash(VersionVo vo);

	@Select(SELECT_FLASH_INFO)
	String select_flash_info(@Param("flashName")String flashName,@Param("flashPlatform") String flashPlatform);
	
	
	// java 
	
	final String INSERT_JAVA = "insert into af_java_info(javaVersion,javaDate)values(#{javaVersion},#{javaDate})";
	
	final String SELECT_JAVA_NEWEST = "select javaDate from af_java_info order by javaDate desc limit 1";
	
	final String SELECT_JAVA_DATACOUNT = "select count(javaId) from af_java_info";
	
	@Insert(INSERT_JAVA)
	void insertJava (VersionVo vo);
	
	@Select(SELECT_JAVA_NEWEST)
	Date select_java_newest();
	
	@Select(SELECT_JAVA_DATACOUNT)
	int select_java_datacount();
	
	// appInfo
	
	final String INSERT_APP_INFO = "insert into (seq,appType,recentVersion,updateDate,platformName,appDescription) values(#{seq},#{appType},#{recentVersion},#{updateDate},#{platformName},#{appDescription})";
	
	@Insert(INSERT_APP_INFO)
	void insertAppInfo(AppInfoVo vo);
	
	
	
}
	
	
