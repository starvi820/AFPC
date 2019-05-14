package one.auditfinder.server.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import one.auditfinder.server.comps.IAuthUtils;
import one.auditfinder.server.comps.RsaUtils;
import one.auditfinder.server.vo.auth.LoginUserVo;

@Controller
public class CommonApi {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private IAuthUtils authUtils;
	
	@Autowired
	private RsaUtils rsaUtils;
	
	@RequestMapping(value="/cUserInfo", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public LoginUserVo getUserInfo(){
		if(log.isDebugEnabled())
			log.debug("api get user info");
		
		LoginUserVo user = authUtils.checkLoginApi();
		user.setPw("");
		return user;
	}
	
	@RequestMapping(value="/setLog/page/{iActUrl}/{sActUrl}", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public void setLog(@PathVariable("iActUrl") String iActUrl, 
					   @PathVariable("sActUrl") String sActUrl, 
					   HttpServletRequest request){
		authUtils.checkLoginApi();
		
		sActUrl = "/"+sActUrl;
		iActUrl = "/"+iActUrl;
	}
	
	@RequestMapping(value="/cGetRsakey")
	@ResponseBody
	public HashMap<String, String> passwdChange(Model model){
		if(log.isDebugEnabled())
			log.debug("Password change");
		
		authUtils.checkLoginApi();
		
		HashMap<String, String> rMap = new HashMap<String, String>();
		
		rMap.put("KeyModules", rsaUtils.getPublicKeyModulus());
		rMap.put("KeyExp", rsaUtils.getPublicKeyExponent());
		rMap.put("UserId", authUtils.getUserInfo().getId());
		
		return rMap;
	}
	
			
}
