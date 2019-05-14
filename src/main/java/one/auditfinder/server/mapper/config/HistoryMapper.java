package one.auditfinder.server.mapper.config;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import one.auditfinder.server.vo.config.LoginHistoryVo;
import one.auditfinder.server.vo.config.PageHistoryVo;

public interface HistoryMapper {
	
	final String GET_LOGIN_HIST_CNT = "select count(seq) cnt from af_admin_login_history";
	
	final String GET_LOGIN_HIST = "select seq, af_admin_id, accessIp, date_format(accessTime, '%Y-%m-%d %H:%m:%s') accessTime, loginYn from af_admin_login_history order by seq desc limit #{num} offset #{offset}";
	
	@Select(GET_LOGIN_HIST_CNT)
	int getLoginHistCnt();
	
	@Select(GET_LOGIN_HIST)
	List<LoginHistoryVo> getLoginHist(@Param("num") int num, @Param("offset") int offset);
	
	@SelectProvider(type=one.auditfinder.server.mapper.config.HistoryMapperProvider.class, method="searchLoginHistCnt")
	int searchLoginHistCnt(Map<String, Object> map);
	
	@SelectProvider(type=one.auditfinder.server.mapper.config.HistoryMapperProvider.class, method="searchLoginHist")
	List<LoginHistoryVo> searchLoginHist(Map<String, Object> map);
	
	final String GET_PAGE_HIST_CNT = "select count(seq) cnt from af_admin_page_history";
	
	final String GET_PAGE_HIST = "select seq, af_admin_id, accessIp, accessMenu, date_format(accessTime, '%Y-%m-%d %H:%m:%s') accessTime, accessTable, action, accessId from af_admin_page_history order by seq desc limit #{num} offset #{offset}";
	
	@Select(GET_PAGE_HIST_CNT)
	int getPageHistCnt();
	
	@Select(GET_PAGE_HIST)
	List<PageHistoryVo> getPageHist(@Param("num") int num, @Param("offset") int offset);
	
	@SelectProvider(type=one.auditfinder.server.mapper.config.HistoryMapperProvider.class, method="searchPageHistCnt")
	int searchPageHistCnt(Map<String, Object> map);
	
	@SelectProvider(type=one.auditfinder.server.mapper.config.HistoryMapperProvider.class, method="searchPageHist")
	List<PageHistoryVo> searchPageHist(Map<String, Object> map);
	
}
