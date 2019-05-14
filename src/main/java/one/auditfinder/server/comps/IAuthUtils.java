package one.auditfinder.server.comps;

import one.auditfinder.server.vo.auth.LoginUserVo;

public interface IAuthUtils {
	
	static final String ATTR_LOGIN_USER_INFO = "_XX_LOGIN_USER_INFO";
	static final String ATTR_LOGIN_BACKURL = "_XX_LOGIN_BACK_URL";
	
	void clearSession();
	void resetSession();
	
	LoginUserVo getUserInfo() ;
	void setUserInfo(LoginUserVo luser);
	void removeLoginUserInfo();
	
	String getBackUrl();
	void setBackUrl(String url);
	void removeBackUrl();
	
	LoginUserVo checkLoginPage();
	LoginUserVo checkLoginApi();
	
}
