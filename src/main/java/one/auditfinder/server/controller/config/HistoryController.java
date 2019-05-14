package one.auditfinder.server.controller.config;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import one.auditfinder.server.common.Page;
import one.auditfinder.server.comps.IAuthUtils;
import one.auditfinder.server.service.config.HistoryService;
import one.auditfinder.server.vo.config.LoginHistoryVo;
import one.auditfinder.server.vo.config.PageHistoryVo;

@Controller
public class HistoryController {
	
	@Autowired
	private IAuthUtils authUtils;
	
	@Autowired
	private HistoryService historyService;
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	
	@RequestMapping(value="/cHistoryMgt/getLoginHistory/{num}/{particle}", produces="application/json")
	@ResponseBody
	public Page<LoginHistoryVo> getLoginHistory(@PathVariable("num") int num, @PathVariable("particle") int particle){
		if(log.isDebugEnabled()) {
			log.debug("Get Login History");
		}
		
		authUtils.checkLoginApi();
		
		if(num < 1) num = 1;
		return historyService.getLoginHistory(num, particle);
	}
	
	@RequestMapping(value="/cHistoryMgt/searchLoginHistory/{type}/{text}/{num}/{particle}", produces="application/json")
	@ResponseBody
	public Page<LoginHistoryVo> searchLoginHistory(@PathVariable("type") String type, @PathVariable("text") String text, @PathVariable("num") int num, @PathVariable("particle") int particle){
		if(log.isDebugEnabled()) {
			log.debug("Search Admin");
		}
		
		if (num < 1) num = 1;
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", type);
		map.put("text", new StringBuilder().append("%").append(text).append("%").toString());
		
		return historyService.searchLoginHistory(map, num, particle);
	}
	
	@RequestMapping(value="/cHistoryMgt/getPageHistory/{num}/{particle}", produces="application/json")
	@ResponseBody
	public Page<PageHistoryVo> getPageHistory(@PathVariable("num") int num, @PathVariable("particle") int particle){
		if(log.isDebugEnabled()) {
			log.debug("Get Page History");
		}
		
		authUtils.checkLoginApi();
		
		if(num < 1) num = 1;
		return historyService.getPageHistory(num, particle);
	}
	
	@RequestMapping(value="/cHistoryMgt/searchPageHistory/{type}/{text}/{num}/{particle}", produces="application/json")
	@ResponseBody
	public Page<PageHistoryVo> searchPageHistory(@PathVariable("type") String type, @PathVariable("text") String text, @PathVariable("num") int num, @PathVariable("particle") int particle){
		if(log.isDebugEnabled()) {
			log.debug("Search Admin");
		}
		
		if (num < 1) num = 1;
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", type);
		
		return historyService.searchPageHistory(map, text, num, particle);
	}
	
	
}
