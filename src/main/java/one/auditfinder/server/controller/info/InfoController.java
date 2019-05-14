package one.auditfinder.server.controller.info;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import one.auditfinder.server.common.Page;
import one.auditfinder.server.comps.IAuthUtils;
import one.auditfinder.server.service.info.InfoService;
import one.auditfinder.server.vo.info.InfoVo;

@Controller
public class InfoController {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private IAuthUtils authUtils;
	
	@Autowired
	private InfoService infoService;
	
	@RequestMapping(value="/cInfo/getList/{num}/{particle}", produces="application/json")
	@ResponseBody
	public Page<InfoVo> getInfo(@PathVariable("num") int num, @PathVariable("particle") int particle){
		
		if(log.isDebugEnabled()) {
			log.debug("Get Info List");
		}
		
		authUtils.checkLoginApi();
		
		if(num <= 0) num = 1;
		return infoService.getInfo(num, particle);
	}
	
	@RequestMapping(value="/cInfo/searchInfo/{type}/{text}/{num}/{particle}", produces="application/json")
	@ResponseBody
	public Page<InfoVo> searchAdmin(@PathVariable("type") 
			
			
			String type, @PathVariable("text") String text, @PathVariable("num") int num, @PathVariable("particle") int particle){
		if(log.isDebugEnabled()) {
			log.debug("Search Info");
		}
		
		if (num < 1) num = 1;
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", type);
		map.put("text", new StringBuilder().append("%").append(text).append("%").toString());
		
		return infoService.searchInfo(map, num, particle);
	}
	
	@RequestMapping(value="/cInfo/regitInfo", method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public void insertInfo(@RequestBody InfoVo vo) {
		if(log.isDebugEnabled()) {
			log.debug("Insert Info");
		}
		
		vo.setAf_admin_id(authUtils.getUserInfo().getId());
		infoService.insertInfo(vo);
	}
	
	@RequestMapping(value="/cInfo/updateInfo")
	@ResponseStatus(HttpStatus.OK)
	public void updateInfo(@RequestBody InfoVo vo) {
		if(log.isDebugEnabled()) {
			log.debug("Update Info {}", vo.getSeq());
		}
		
		infoService.updateInfo(vo);
	}
	
	@RequestMapping(value="/cInfo/deleteInfo/{seq}", method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public void deleteInfo(@PathVariable("seq") int seq) {
		if(log.isDebugEnabled()) {
			log.debug("Delete Info {}", seq);
		}
		
		infoService.deleteInfo(seq);
	}
	

}
