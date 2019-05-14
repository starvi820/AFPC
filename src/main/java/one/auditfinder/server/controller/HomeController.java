package one.auditfinder.server.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import one.auditfinder.server.comps.IAuthUtils;
import one.auditfinder.server.comps.RsaUtils;
import one.auditfinder.server.vo.auth.LoginUserVo;


/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping("/")
public class HomeController {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private IAuthUtils authUtils;
	
	@Autowired
	private RsaUtils rsaUtils;

//    @RequestMapping(method = RequestMethod.GET)
//    public String index(){
//    	if(log.isDebugEnabled())
//    		log.debug("Request /");
//        return "index";
//    }
    
    @RequestMapping(value = "cLogin")
	public String login( Model model) {
		if( log.isDebugEnabled())
			log.debug("Request /page/cLogin ... ");
		
		LoginUserVo luser = authUtils.getUserInfo();
		
		if(luser != null && luser.getSeq() > 0){
			String url = authUtils.getBackUrl();
			if(url != null){
				return new StringBuilder().append("redirect:").append(url).toString();
			}else{
				return "redirect:/page/cDashboard";
			}
		}
		
		model.addAttribute("KeyModules", rsaUtils.getPublicKeyModulus());
		model.addAttribute("KeyExp", rsaUtils.getPublicKeyExponent());
		return "cLogin";
	}
	
	@RequestMapping("cLogout")
	public String logout(Model model){
		if(log.isDebugEnabled())
			log.debug("Request logout");
		
		model.addAttribute("KeyModules", rsaUtils.getPublicKeyModulus());
		model.addAttribute("KeyExp", rsaUtils.getPublicKeyExponent());
		
		authUtils.clearSession();
		return "cLogin";
	}
	
	@RequestMapping(value="cMain")
	public String cMain(){
		if(log.isDebugEnabled()) log.debug("Request /page/cMain");
		
		authUtils.checkLoginPage();
		return "page/cDashboard";
	}
	
	@RequestMapping(value="cDashboard")
	public String cDashboard(){
		if(log.isDebugEnabled()) log.debug("Request /page/cDashboard");
		
		authUtils.checkLoginPage();
		return "page/cDashboard";
	}
	
	@RequestMapping(value="cInfo")
	public String cInfo(){
		if(log.isDebugEnabled()) log.debug("Request /page/cInfo");
		
		authUtils.checkLoginPage();
		return "page/cInfo";
	}
	
	@RequestMapping(value="cAgent")
	public String cAgent() {
		if(log.isDebugEnabled()) log.debug("Request /page/cAgent");
		
		authUtils.checkLoginPage();
		return "page/cAgent";
	}
	
	@RequestMapping(value="cList")
	public String cList() {
		if(log.isDebugEnabled()) log.debug("Request /page/cList");
		
		authUtils.checkLoginPage();
		return "/page/cList";
	}
	
   @RequestMapping(value="cCheck")
   public String cCheck() {
	   if(log.isDebugEnabled()) log.debug("Request /page/cCheck");
	   
	   authUtils.checkLoginPage();
	   return "/page/cCheck";
   }
   
   @RequestMapping(value="cResult")
   public String cResult() {
	   if(log.isDebugEnabled()) log.debug("Request /page/cResult");
	   
	   authUtils.checkLoginPage();
	   return "/page/cResult";
   }
   
   @RequestMapping(value="cConfig")
   public String cConfig(){
	   if(log.isDebugEnabled()) log.debug("Request /page/cConfig");
	   
	   authUtils.checkLoginPage();
	   return "page/cConfig";
   }
	
}
