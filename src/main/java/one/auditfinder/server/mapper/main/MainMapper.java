package one.auditfinder.server.mapper.main;


import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

import one.auditfinder.server.vo.main.HeaderInfoVo;
import one.auditfinder.sever.vo.version.AcrobatVo;
import one.auditfinder.sever.vo.version.AppInfoVo;
import one.auditfinder.sever.vo.version.FlashPlayerVo;
import one.auditfinder.sever.vo.version.HotfixVo;
import one.auditfinder.sever.vo.version.JavaVo;

public interface MainMapper {
	
	final String GET_USER_INFO = "select aa.id, aa.name, date_format(aalh.accessTime, '%Y-%m-%d %H:%m:%s') accessTime from af_admin aa, af_admin_login_history aalh where aa.id = aalh.af_admin_id and aa.id = #{id} and aalh.loginYn = 1 order by aalh.accessTime desc limit 1";
	
	final String GET_HOTFIX_INFO = "select bulletin_id,bulletin_kb,date_format(date_posted,'%y-%m-%d')date_posted from af_hotfix_info where date_posted = (select date_posted from af_hotfix_info order by date_posted desc limit 1) "
			+ "group by bulletin_id order by bulletin_id desc";
	
	final String GET_HOTFIX_DETAIL = "select bulletin_id,bulletin_kb,date_format(date_posted,'%y-%m-%d')date_posted,"
			+ "title,affected_component,affected_product,cves "
			+ "from af_hotfix_info " 
			+ "where bulletin_id=#{bulletin_id}";
	
	final String GET_HOTFIX_DETAIL_ALL = "SELECT bulletin_id,bulletin_kb,date_format(date_posted,'%y-%m-%d')date_posted,title,"
			+ "affected_component,affected_product,cves "
			+ " from af_hotfix_info "
			+ " where DATE_POSTED = (select DATE_POSTED FROM af_hotfix_info order by DATE_POSTED desc limit 1)order by BULLETIN_ID desc";
			
	
	@Select(GET_HOTFIX_DETAIL_ALL)
	List<HotfixVo> hotfix_info_detailAll();
	
	@Select(GET_HOTFIX_DETAIL)
	List<HotfixVo> hotfix_info_detail(@Param("bulletin_id") String bulletin_id);
	
	
	
	@Select(GET_USER_INFO)
	HeaderInfoVo getUserInfo(@Param("id") String id);
	
	@Select(GET_HOTFIX_INFO)
	List<HotfixVo> getHotfixInfo();
	
	
	// -----------------------------------------------
	final String GET_ACROBATREADER_INFO = "select acrobatId , acrobatVersion , acrobatName,acrobatSize,date_format(acrobatDate, '%y-%m-%d')acrobatDate from af_acrobatreader_info where acrobatVersion=(select acrobatVersion from af_acrobatreader_info order by acrobatVersion desc limit 1)";
	
	@Select(GET_ACROBATREADER_INFO)
	List<AcrobatVo> getAcrobatInfo();
	
	// ----------------------------------------------
	
	final String GET_FLASHPLAYER_INFO = "select flashId,flashPlatform,flashBrowser,flashVersion from af_flash_info";
	
	@Select(GET_FLASHPLAYER_INFO)
	List<FlashPlayerVo> getFlashInfo();
	
	// ------------------------------------------------------
	final String GET_JAVA_INFO = "select javaId,javaVersion,date_format(javaDate,'%y-%m-%d')javaDate from af_java_info where javaDate = (select javaDate from af_java_info order by javaDate desc limit 1) ";
	
	@Select(GET_JAVA_INFO)
	List<JavaVo> getJavaInfo();
	// -----------------------------------------------------------
	
	final String INSERT_APP_INFO = "insert into af_app_info(seq,appType,recentVersion,updateDate,platformName,appDescription) values(#{seq},#{appType},#{recentVersion},#{updateDate},#{platformName},#{appDescription})";
	
	@Insert(INSERT_APP_INFO)
	void insertAppInfo(AppInfoVo vo);
	
}
