package one.auditfinder.server.controller.check;

import java.util.HashMap;
import java.util.List;
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
import one.auditfinder.server.service.check.CheckService;
import one.auditfinder.server.vo.check.CheckResultVo;
import one.auditfinder.server.vo.check.ScheduleVo;

@Controller
public class CheckController {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private IAuthUtils authUtils;
	
	@Autowired
	private CheckService checkService;
	
	@RequestMapping(value="/cCheck/getResult/{num}/{particle}", produces="application/json")
	@ResponseBody
	public Page<CheckResultVo> getCheckListResult(@PathVariable("num") int num, @PathVariable("particle") int particle){
		if(log.isDebugEnabled()) {
			log.debug("Get Check List Result");
		}
		
		authUtils.checkLoginApi();
		if(num < 1) num = 1;
		return checkService.getCheckResult(num, particle);
	}
	
	@RequestMapping(value = "/cCheck/getScore",produces="application/json")
	@ResponseBody
	public List<CheckResultVo> getCheckListResultScore(){
		if(log.isDebugEnabled()) {
			log.debug("get check list result");
		}
		authUtils.checkLoginApi();
		return checkService.getCheckScore();
	}
	
	@RequestMapping(value="/cCheck/searchResult/{type}/{text}/{num}/{particle}", produces="application/json")
	@ResponseBody
	public Page<CheckResultVo> searchCheckResult(@PathVariable("type") String type, @PathVariable("text") String text, @PathVariable("num") int num, @PathVariable("particle") int particle){
		if(log.isDebugEnabled()) {
			log.debug("Search Agent");
		}
		
		if(num < 1) num = 1;
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", type);
		map.put("text", new StringBuilder().append("%").append(text).append("%").toString());
		return checkService.searchCheckResult(map, num, particle);
	}
	
	@RequestMapping(value="/cCheck/getSchedule", produces="application/json")
	@ResponseBody
	public List<ScheduleVo> getSchedule(){
		if(log.isDebugEnabled()) {
			log.debug("Get Schedule");
		}
		
		return checkService.getSchedule();
	}
	
	@RequestMapping(value="/cCheck/regitSchedule", method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public void insertSchedule(@RequestBody ScheduleVo vo) {
		if(log.isDebugEnabled()) {
			log.debug("Insert Check Schedule");
		}
		
		checkService.insertSchedule(vo);
	}
	
	@RequestMapping(value="/cCheck/updateSchedule", method= RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public void updateSchedule(@RequestBody ScheduleVo vo) {
		if(log.isDebugEnabled()) {
			log.debug("Update Check Schedule");
		}
		
		checkService.updateSchedule(vo);
	}
	
	@RequestMapping(value="/cCheck/deleteSchedule/{seq}", method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public void deleteSchedule(@PathVariable("seq") int seq) {
		if(log.isDebugEnabled()) {
			log.debug("Delete Check Sehedule");
		}
		
		checkService.deleteSehedule(seq);
	}

}
