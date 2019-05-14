package one.auditfinder.server.mapper.auth;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import one.auditfinder.server.vo.auth.LoginHistoryVo;
import one.auditfinder.server.vo.auth.LoginUserVo;
import one.auditfinder.server.vo.config.PageHistoryVo;


public interface DefaultAuth {
	
	final String SQL_USERAUTH_CHECK = "select seq, id, pw, name, email, tel, regDate from af_admin where id = #{id}";
	
	@Select(SQL_USERAUTH_CHECK)
	public LoginUserVo getCheckLogin(@Param("id") String id);
	
	final String INSERT_LOGIN_HISTORY = "insert into af_admin_login_history(af_admin_id, accessIp, accessTime, loginYn) values(#{af_admin_id}, #{accessIp}, now(), #{loginYn})";
	
	@Insert(INSERT_LOGIN_HISTORY)
	void insertLoginHistory(LoginHistoryVo vo);
	
	final String INSERT_PAGE_HISTORY = "insert into af_admin_page_history(af_admin_id, accessIp, accessPage, accessMenu, accessTime, accessTable, accessId, action) "+
				"values(#{af_admin_id}, #{accessIp}, #{accessPage}, #{accessMenu}, now(), #{accessTable}, #{accessId}, #{action}) ";

	@Insert(INSERT_PAGE_HISTORY)
	void insertPageHistory(PageHistoryVo vo);
	
}