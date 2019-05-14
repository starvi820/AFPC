package one.auditfinder.server.comps;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import one.auditfinder.server.exception.ApiException;
import one.auditfinder.server.exception.PageException;
import one.auditfinder.server.statics.Strs;
import one.auditfinder.server.statics.Values;
import one.auditfinder.server.vo.auth.LoginUserVo;

@Component(value="authUtils")
public class AuthUtils implements IAuthUtils{
	
	@Autowired
	private MsgUtils msgUtils;
	
	public AuthUtils() {
		
	}
	
	@Override
	public void clearSession() {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession();
		session.invalidate();
	}
	
	@Override
	public void resetSession() {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession();
		session.invalidate();
		attr.getRequest().getSession(true);
	}
	
	@Override
	public LoginUserVo getUserInfo() {
		Object o = RequestContextHolder.getRequestAttributes().getAttribute(ATTR_LOGIN_USER_INFO, RequestAttributes.SCOPE_SESSION);
		if( o != null ) 
			return (LoginUserVo)o;
		return null;
	}
	
	@Override
	public void setUserInfo(LoginUserVo luser) {
		RequestContextHolder.getRequestAttributes().setAttribute(ATTR_LOGIN_USER_INFO, luser, RequestAttributes.SCOPE_SESSION);
	}
	
	@Override
	public void removeLoginUserInfo() {
		RequestContextHolder.getRequestAttributes().removeAttribute(ATTR_LOGIN_USER_INFO, RequestAttributes.SCOPE_SESSION);
	}
	
	@Override
	public String getBackUrl() {
		Object o = RequestContextHolder.getRequestAttributes().getAttribute(ATTR_LOGIN_BACKURL, RequestAttributes.SCOPE_SESSION);
		if( o != null ) 
			return (String)o;
		return null;
	}
	
	@Override
	public void setBackUrl(String url) {
		RequestContextHolder.getRequestAttributes().setAttribute(ATTR_LOGIN_BACKURL, url, RequestAttributes.SCOPE_SESSION);
	}
	
	@Override
	public void removeBackUrl() {
		RequestContextHolder.getRequestAttributes().removeAttribute(ATTR_LOGIN_BACKURL, RequestAttributes.SCOPE_SESSION);
	}

	@Override
	public LoginUserVo checkLoginPage() {
		Object o = RequestContextHolder.getRequestAttributes().getAttribute(ATTR_LOGIN_USER_INFO, RequestAttributes.SCOPE_SESSION);
		if( o == null ) {			
			throw new PageException(msgUtils.getMsg(Strs.ID_NOTLOGIN_SESSION), Values.ID_NOTLOGIN_SESSIONEXP , null);
		} 
		return (LoginUserVo)o;	
	}

	@Override
	public LoginUserVo checkLoginApi() {
		Object o = RequestContextHolder.getRequestAttributes().getAttribute(ATTR_LOGIN_USER_INFO, RequestAttributes.SCOPE_SESSION);
		if( o == null ) {			
			throw new ApiException(msgUtils.getMsg(Strs.ID_NOTLOGIN_SESSION), Values.ID_NOTLOGIN_SESSIONEXP , null);
		}
		return (LoginUserVo)o;
	}


}
