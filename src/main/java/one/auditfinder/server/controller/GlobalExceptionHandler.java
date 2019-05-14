package one.auditfinder.server.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import one.auditfinder.server.comps.IAuthUtils;
import one.auditfinder.server.exception.ApiException;
import one.auditfinder.server.exception.PageException;
import one.auditfinder.server.statics.Values;
import one.auditfinder.server.vo.common.CommonResult;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	@Qualifier("authUtils") 
	private IAuthUtils authUtils;
		
	@ExceptionHandler(PageException.class)
	public ModelAndView handlePageException(HttpServletRequest req, PageException e) {
		if( log.isDebugEnabled()) {
			log.debug( " Error Page : {}  raised {} ", req.getRequestURI(), e.getMessage());
		}
		ModelAndView mav = new ModelAndView();
		
		//login 하지않았거나 Session 만료시.
		if(e.getCode() == Values.ID_NOTLOGIN_SESSIONEXP) {
			authUtils.setBackUrl(req.getRequestURI());
			mav.addObject("exception", e);
			mav.setViewName("error_sessionexp");
			return mav;
		}
		
		mav.addObject("url" , req.getRequestURL());
		mav.addObject("exception", e);
		String backUrl = e.getUrl();
		if( backUrl != null &&  backUrl.length() > 0 )
			mav.addObject("backUrl" ,  backUrl);
		else
			mav.addObject("backUrl" , "history.back()");
		mav.setViewName("error");		
		return mav;
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ApiException.class)
	@ResponseBody
	public CommonResult handleApiException(HttpServletRequest req, ApiException e) {
		if( log.isDebugEnabled()) {
			log.debug( " API Error : {}  raised {} ", req.getRequestURI(),e.getMessage());
		}
		
		CommonResult result = new CommonResult();
		String backUrl = e.getUrl();
		result.setCode(e.getCode());
		result.setMessage( e.getMessage());
		if( backUrl != null && backUrl.length() > 0)
			result.setExinfo(backUrl);
		return result;
	}
	
	
	

}
