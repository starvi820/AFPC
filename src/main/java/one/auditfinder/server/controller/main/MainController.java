package one.auditfinder.server.controller.main;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import one.auditfinder.server.comps.IAuthUtils;
import one.auditfinder.server.service.main.MainService;
import one.auditfinder.server.service.version.VersionService;
import one.auditfinder.server.vo.main.HeaderInfoVo;
import one.auditfinder.sever.vo.version.AcrobatVo;
import one.auditfinder.sever.vo.version.AppInfoVo;
import one.auditfinder.sever.vo.version.FlashPlayerVo;
import one.auditfinder.sever.vo.version.HotfixVo;
import one.auditfinder.sever.vo.version.JavaVo;
import sun.tools.jar.resources.jar;

@Controller
public class MainController {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private MainService mainService;
		
	@Autowired
	private IAuthUtils authUtils;
	
	@RequestMapping(value="/cMain/getUserInfo", produces="application/json")
	@ResponseBody
	public HeaderInfoVo getUserInfo(){
		if(log.isDebugEnabled())
			log.debug("/cMain get User Info");
		
		String id = authUtils.getUserInfo().getId();
		return mainService.getUserInfo(id);
	}
	
	@RequestMapping(value = "/cDashboard/hotfix_info",produces = "application/json")
	@ResponseBody
	public List<HotfixVo> Hotfix_info(){
		if(log.isDebugEnabled())
			log.debug("hotfix");
		
		return mainService.getHotfixInfo();
		
	}
	
	@RequestMapping(value = "/cDashboard/hotfix_info_detail/{bulletin_id}",produces = "application/json")
	@ResponseBody
	public List<HotfixVo> hotfix_info_detail(@PathVariable("bulletin_id") String bulletin_id){
		if(log.isDebugEnabled())
			log.debug("hotfix");
		
		return mainService.hotfix_info_detail(bulletin_id);
	}
	
	@RequestMapping(value = "/cDashboard/hotfix_info_detailAll",produces="application/json")
	@ResponseBody
	public List<HotfixVo> hotfix_info_detailAll(){
		if(log.isDebugEnabled())
			log.debug("hotfix");
		
		return mainService.hotfix_info_detailAll();
	}
	
	@RequestMapping(value = "/cDashboard/get_hotfix_id",produces = "application/json")
	@ResponseBody
	public List<HotfixVo> get_hotfix_id(){
		if(log.isDebugEnabled())
			log.debug("hotfix");
		
		return mainService.get_hotfix_id();
	}
	
	@RequestMapping(value = "/cDashboard/getAcrobatInfo",produces = "application/json")
	@ResponseBody
	public List<AcrobatVo> getAcrobatInfo(){
		if(log.isDebugEnabled())
			log.debug("acrobat");
		return mainService.getActobatInfo();
	}
	
	@RequestMapping(value = "/cDashboard/getFlashInfo",produces = "application/json")
	@ResponseBody
	public List<FlashPlayerVo> getFlashInfo(){
		if(log.isDebugEnabled())
			log.debug("flash player");
		return mainService.getFlashInfo();
	}
	
	
	@RequestMapping(value = "/cDashboard/getJavaInfo",produces = "application/json")
	@ResponseBody
	public List<JavaVo> getJavaInfo(){
		if(log.isDebugEnabled())
			log.debug("Java");
		return mainService.getJavaInfo();
	}
	
	@RequestMapping(value= "/cDashboard/insertAppInfoHotfix",method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public void appInfoHotfix(@RequestBody List<HotfixVo> vo) {
		if(log.isDebugEnabled())
			log.debug("appInfo insert hotfix");
		
		for(HotfixVo lst : vo) {
			AppInfoVo avo = new AppInfoVo();
			avo.setAppType(4);
			avo.setRecentVersion(lst.getBulletin_kb());
			avo.setUpdateDate(lst.getDate_posted());
			avo.setPlatformName(lst.getAffected_product());
			avo.setAppDescription(lst.getTitle());
			
			mainService.insertAppInfo(avo);
		}
		
	}
	
	@RequestMapping(value ="/cDashboard/insertAppInfoFlash",method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public void appInfoFlash(@RequestBody List<FlashPlayerVo> vo) {
		if(log.isDebugEnabled())
			log.debug("appInfo insert flash");
		
		for(FlashPlayerVo list : vo) {
			AppInfoVo avo = new AppInfoVo();
			avo.setAppType(1);
			avo.setRecentVersion(list.getFlashVersion());
			avo.setPlatformName(list.getFlashPlatform());
			avo.setAppDescription(list.getFlashBrowser());
			
			mainService.insertAppInfo(avo);
		}
		
	}
	
	@RequestMapping(value = "/cDashboard/insertAppInfoAcrobatReader" , method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public void appInfoAcrobatReader(@RequestBody List<AcrobatVo> vo) {
		if(log.isDebugEnabled())
			log.debug("appInfo insert acrobat");
		
		for(AcrobatVo abv : vo) {
			AppInfoVo avo = new AppInfoVo();
			avo.setAppType(2);
			avo.setRecentVersion(abv.getAcrobatVersion());
			avo.setUpdateDate(abv.getAcrobatDate());
			avo.setAppDescription(abv.getAcrobatName());
			
			mainService.insertAppInfo(avo);
			
		}
				
	}
	
	@RequestMapping(value = "/cDashboard/insertAppInfoJava" , method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public void appInfoJava(@RequestBody List<JavaVo> vo) {
		if(log.isDebugEnabled())
			log.debug("appInfo insert java");
		
		for(JavaVo jv : vo) {
			AppInfoVo avo = new AppInfoVo();
			avo.setAppType(3);
			avo.setRecentVersion(jv.getJavaVersion());
			avo.setUpdateDate(jv.getJavaDate());
			
			mainService.insertAppInfo(avo);
		}
		
	}
	

}
