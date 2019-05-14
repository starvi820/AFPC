package one.auditfinder.server.comps;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class CommonUtils {

	public String requestUri(){
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		return request.getRequestURI();
	}
	
	public HttpServletRequest getRequest(){
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}
	
	public String getReferer(){
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("referer");
	}
}
