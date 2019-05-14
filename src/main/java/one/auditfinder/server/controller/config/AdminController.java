package one.auditfinder.server.controller.config;

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
import one.auditfinder.server.service.config.AdminService;
import one.auditfinder.server.statics.Funcs;
import one.auditfinder.server.vo.common.CommonResult;
import one.auditfinder.server.vo.config.AdminVo;

@Controller
public class AdminController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private IAuthUtils authUtils;
	
	@Autowired
	private AdminService adminService;
	
	@RequestMapping(value="/cAdminMgt/getAdmin/{num}/{particle}", produces="application/json")
	@ResponseBody
	public Page<AdminVo> getAdmin(@PathVariable("num") int num, @PathVariable("particle") int particle){
		if(log.isDebugEnabled()) {
			log.debug("Get Admin List");
		}
		
		authUtils.checkLoginApi();
		
		if(num < 1) num = 1;
		return adminService.getAdmin(num, particle);
	}
	
	@RequestMapping(value="/cAdminMgt/searchAdmin/{type}/{text}/{num}/{particle}", produces="application/json")
	@ResponseBody
	public Page<AdminVo> searchAdmin(@PathVariable("type") String type, @PathVariable("text") String text, @PathVariable("num") int num, @PathVariable("particle") int particle){
		if(log.isDebugEnabled()) {
			log.debug("Search Admin");
		}
		
		if (num < 1) num = 1;
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", type);
		map.put("text", new StringBuilder().append("%").append(text).append("%").toString());
		
		return adminService.searchAdmin(map, num, particle);
	}
	
	@RequestMapping(value="/cAdminMgt/chkId/{id}", produces="application/json")
	@ResponseBody
	public CommonResult chkId(@PathVariable("id") String id) {
		if(log.isDebugEnabled()) {
			log.debug("Chk ID : {}", id);
		}
		CommonResult cr = new CommonResult();
		
		int flag = adminService.idChk(id);
		
		if(flag > 0) cr.setData(0);
		if(flag == 0) cr.setData(1); 
		
		return cr;
	}
	
	@RequestMapping(value="/cAdminMgt/chkPw", produces="application/json")
	@ResponseBody
	public CommonResult chkPw(@RequestBody AdminVo vo) {
		if(log.isDebugEnabled()) {
			log.debug("Chk Pw");
		}
		
		CommonResult cr = new CommonResult();
		
		AdminVo adminVo = adminService.selectAdminBySeq(vo.getSeq());
		
		String dPass = vo.getPw();
		dPass = Funcs.getSha512Str(dPass);
		
		if(dPass.equals(adminVo.getPw())) cr.setData(1);
		else cr.setData(0);
		
		return cr;
	}
	
	@RequestMapping(value="/cAdminMgt/insertAdmin", method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public void insertAdmin(@RequestBody AdminVo vo) {
		if(log.isDebugEnabled()) {
			log.debug("Insert Admin");
		}
		
		String dPass = vo.getPw();
		dPass = Funcs.getSha512Str(dPass);
		vo.setPw(dPass);
		adminService.insertAdmin(vo);
	}
	
	@RequestMapping(value="/cAdminMgt/deleteAdmin", method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public void deleteAdmin(@RequestBody AdminVo vo) {
		if(log.isDebugEnabled()) {
			log.debug("Delete Admin : {}", vo.getSeq());
		}
		
		adminService.deleteAdmin(vo.getSeq());
	}
	
	@RequestMapping(value="/cAdminMgt/updateAdmin", method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public void updateAdmin(@RequestBody AdminVo vo) {
		if(log.isDebugEnabled()) {
			log.debug("Update Admin : {}", vo.getSeq());
		}
		
		String dPass = vo.getPw();
		dPass = Funcs.getSha512Str(dPass);
		vo.setPw(dPass);
		
		adminService.updateAdmin(vo);
	}
}
