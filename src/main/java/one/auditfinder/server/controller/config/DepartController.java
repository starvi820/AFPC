package one.auditfinder.server.controller.config;

import java.util.List;

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

import one.auditfinder.server.service.config.DepartService;
import one.auditfinder.server.vo.config.CompanyVo;
import one.auditfinder.server.vo.config.DepartmentVo;
import one.auditfinder.server.vo.config.TeamVo;

@Controller
public class DepartController {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private DepartService departService;
	
	@RequestMapping(value="/cDepartMgt/getCompany", produces="application/json")
	@ResponseBody
	public List<CompanyVo> getCompany(){
		if(log.isDebugEnabled()) {
			log.debug("Get Company");
		}
		
		return departService.getCompany();
	}
	
	@RequestMapping(value="cDepartMgt/getDepart/{seq}", produces="application/json")
	@ResponseBody
	public List<DepartmentVo> getDepart(@PathVariable("seq") int seq){		
		if(log.isDebugEnabled()) {
			log.debug("Get Department");
		}
		
		return departService.getDepart(seq);
	}
	
	@RequestMapping(value="/cDepartMgt/getTeam/{seq}/{departSeq}", produces="application/json")
	@ResponseBody
	public List<TeamVo> getTeam(@PathVariable("seq") int seq, @PathVariable("departSeq") int departSeq){
		if(log.isDebugEnabled()) {
			log.debug("Get Team");
		}
		
		return departService.getTeam(seq, departSeq);
	}
	
	@RequestMapping(value="/cDepartMgt/insertCompany", method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public void insertCompany(@RequestBody CompanyVo vo) {
		if(log.isDebugEnabled()) {
			log.debug("Insert Company: {}", vo.getName());
		}
		
		departService.insertCompany(vo);
	}
	
	@RequestMapping(value="/cDepartMgt/updateCompany", method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)         
	public void updateCompany(@RequestBody CompanyVo vo) {
		if(log.isDebugEnabled()) {
			log.debug("Update Company : {}", vo.getName());
		}
		
		departService.updateCompany(vo);
	}
	
	@RequestMapping(value="/cDepartMgt/deleteCompany", method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public void deleteCompany(@RequestBody CompanyVo vo) {
		if(log.isDebugEnabled()) {
			log.debug("Delete Company : {}", vo.getSeq());
		}
		
		departService.deleteCompany(vo);
	}
	
	@RequestMapping(value="/cDepartMgt/insertDepart", method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public void insertDepart(@RequestBody DepartmentVo vo) {
		if(log.isDebugEnabled()) {
			log.debug("Insert Department: {}", vo.getName());
		}
		
		departService.insertDepart(vo);
	}
	
	@RequestMapping(value="/cDepartMgt/updateDepart", method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public void updateDepart(@RequestBody DepartmentVo vo) {
		if(log.isDebugEnabled()) {
			log.debug("Update Department : {}", vo.getName());
		}
		
		departService.updateDepeart(vo);
	}
	
	@RequestMapping(value="/cDepartMgt/deleteDepart", method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public void deleteDepart(@RequestBody DepartmentVo vo) {
		if(log.isDebugEnabled()) {
			log.debug("Delete Depart : {}", vo.getSeq());
		}
		
		departService.deleteDepart(vo);
	}
	
	@RequestMapping(value="/cDepartMgt/insertTeam", method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public void insertTeam(@RequestBody TeamVo vo) {
		if(log.isDebugEnabled()) {
			log.debug("Insert Team: {}", vo.getName());
		}
		
		departService.insertTeam(vo);
	}
	
	@RequestMapping(value="/cDepartMgt/updateTeam", method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public void updateTeam(@RequestBody TeamVo vo) {
		if(log.isDebugEnabled()) {
			log.debug("Update Team : {}", vo.getName());
		}
		
		departService.updateTeam(vo);
	}
	
	@RequestMapping(value="/cDepartMgt/deleteTeam", method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public void deleteTeam(@RequestBody TeamVo vo) {
		if(log.isDebugEnabled()) {
			log.debug("Delete Team : {}", vo.getSeq());
		}
		
		departService.deleteTeam(vo);
	}
	
}
