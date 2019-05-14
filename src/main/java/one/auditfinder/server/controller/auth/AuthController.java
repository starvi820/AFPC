package one.auditfinder.server.controller.auth;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import one.auditfinder.server.comps.IAuthUtils;
import one.auditfinder.server.comps.MsgUtils;
import one.auditfinder.server.comps.RsaUtils;
import one.auditfinder.server.service.auth.AuthService;
import one.auditfinder.server.statics.Funcs;
import one.auditfinder.server.statics.Strs;
import one.auditfinder.server.vo.auth.LoginHistoryVo;
import one.auditfinder.server.vo.auth.LoginItem;
import one.auditfinder.server.vo.auth.LoginUserVo;
import one.auditfinder.server.vo.common.CommonResult;
import one.auditfinder.server.vo.config.PageHistoryVo;

@Controller
public class AuthController {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private IAuthUtils authUtils;
	
	@Autowired
	private AuthService authService; 
	
	@Autowired
	private RsaUtils rsaUtils;
	
	@Autowired
	private MsgUtils msgUtils;
	
	
	@RequestMapping(value="/login_check", method=RequestMethod.POST, consumes="application/json", produces="application/json")
	@ResponseBody
	public CommonResult login_check(@RequestBody LoginItem item){
		if(log.isDebugEnabled())
			log.debug("Request Login Check");
		
		CommonResult result = new CommonResult();
		LoginHistoryVo lHistoryVo = new LoginHistoryVo();
			
		String backUrl = authUtils.getBackUrl();
		String decryptedId = rsaUtils.decrypt(item.getId());
		String decryptedPw = rsaUtils.decrypt(item.getPw());
		
		LoginUserVo luser = authService.getCheckLogin(decryptedId, Funcs.getSha512Str(decryptedPw));
		
		lHistoryVo.setAf_admin_id(decryptedId);
		HttpServletRequest req = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
        String ip = req.getHeader("WL-Proxy-Client-IP");
        
        if (ip == null)
            lHistoryVo.setAccessIp(req.getRemoteAddr());
		
		if(luser == null){
			result.setCode(-10);
			result.setMessage(msgUtils.getMsg(Strs.ID_LOGIN_FAIL_MSG));
			lHistoryVo.setLoginYn(0);
			authService.insertLoginHistory(lHistoryVo);
			return result;
		}
		
		result.setCode(10);
		if(backUrl != null) result.setExinfo(backUrl);
		else result.setExinfo("");
		lHistoryVo.setLoginYn(1);
		authService.insertLoginHistory(lHistoryVo);
		
		return result;
	}
	
	@RequestMapping(value="/insertPageHistory", method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public void insertPageHistory(@RequestBody PageHistoryVo vo) {
		if(log.isDebugEnabled()) {
			log.debug("Insert Page History");
		}
		
		vo.setAf_admin_id(authUtils.getUserInfo().getId());
		
		HttpServletRequest req = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
        String ip = req.getHeader("WL-Proxy-Client-IP");
        if (ip == null)
        	vo.setAccessIp(req.getRemoteAddr());
        
        authService.insertPageHistory(vo);
	}
	

}
