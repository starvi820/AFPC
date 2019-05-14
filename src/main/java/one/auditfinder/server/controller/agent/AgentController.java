package one.auditfinder.server.controller.agent;

import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

import one.auditfinder.server.common.ExcelUtils;
import one.auditfinder.server.common.Page;
import one.auditfinder.server.common.Triple;
import one.auditfinder.server.comps.IAuthUtils;
import one.auditfinder.server.exception.ApiException;
import one.auditfinder.server.mapper.agent.AgentMapper;
import one.auditfinder.server.service.agent.AgentService;
import one.auditfinder.server.statics.Strs;
import one.auditfinder.server.statics.Values;
import one.auditfinder.server.vo.agent.AgentVo;
import one.auditfinder.server.vo.common.AgentExcelData;
import one.auditfinder.server.vo.common.CommonResult;

@Controller
public class AgentController {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private IAuthUtils authUtils;
	
	@Autowired
	private AgentService agentService;
	
	@Value("#{fileConf['fileConf.agentExcelDir']}")
	private String agentExcelDir;
	
	@RequestMapping(value="/cAgent/getAgent/{num}/{particle}", produces="application/json")
	@ResponseBody
	public Page<AgentVo> getAgent(@PathVariable("num") int num, @PathVariable("particle") int particle){
		if(log.isDebugEnabled()){
			log.debug("Get Agent List");
		}
		
		authUtils.checkLoginApi();
		
		if(num < 1) num = 1;
		return agentService.getAgent(num, particle);
	}
	
	@RequestMapping(value="/cAgent/searchAgent/{type}/{text}/{num}/{particle}", produces="application/json")
	@ResponseBody
	public Page<AgentVo> searchAgent(@PathVariable("type") String type, @PathVariable("text") String text, @PathVariable("num") int num, @PathVariable("particle") int particle){
		if(log.isDebugEnabled()) {
			log.debug("Search Agent");
		}
		
		if(num < 1) num = 1;
		
	
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", type);
		map.put("text", new StringBuilder().append("%").append(text).append("%").toString());
		return agentService.searchAgent(map, num, particle);
	}
	
	@RequestMapping(value="/cAgent/getAgent/{seq}", produces="application/json")
	@ResponseBody
	public AgentVo getAgentBySeq(@PathVariable("seq") int seq) {
		if(log.isDebugEnabled()) {
			log.debug("Get Agent : {}", seq);
		}
		
		return agentService.getAgentBySeq(seq);
	}
	
	@RequestMapping(value="/cAgent/regitAgent", method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public void insertAgent(@RequestBody AgentVo vo) {
		if(log.isDebugEnabled()) {
			log.debug("Insert Agent");
		}
		
		agentService.insertAgent(vo);
	}
	
	@RequestMapping(value="/cAgent/updateAgent", method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public void updateAgent(@RequestBody AgentVo vo) {
		if(log.isDebugEnabled()) {
			log.debug("Update Agent {}", vo.getSeq());
						
		}
		
		agentService.updateAgent(vo);
	}
	
	@RequestMapping(value="/cAgent/deleteAgent/{seq}", method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public void deleteAgent(@PathVariable("seq") int seq) {
		if(log.isDebugEnabled()) {
			log.debug("Delete Agent {}", seq);
		}
		
		agentService.deleteAgent(seq);
	}
	
	@RequestMapping(value="/cAgent/download.do")
	public String agentExcelDownload(Model model) {
		if(log.isDebugEnabled()) {
			log.debug("Agent Regit Excel Template Download");
		}
		
		File f = new File(agentExcelDir+Strs.ID_AGENT_REGIT_EXCEL);
		model.addAttribute("downloadFile", f);
		model.addAttribute("fileName", "Agent등록양식.xlsx");
		return "downloadView";
	}
	
	@RequestMapping(value="/cAgent/regitAgentExcel", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public CommonResult regitAgentExcel(@RequestParam("excelfile") List<MultipartFile> files) throws Exception {
		if(log.isDebugEnabled()) {
			log.debug("Regit Agent Excel");
		}
		
		CommonResult cr = new CommonResult();
		
		String msg = "정확한 파일을 업로드해주세요.";
		
		MultipartFile excelFile = files.get(0);
		
		try {
			String fileName = excelFile.getOriginalFilename();
			String fileType = fileName.substring(fileName.lastIndexOf(".")+1, fileName.length());
			if((!fileType.equals("xls")) && (!fileType.equals("xlsx"))){
				throw new ApiException(msg, -20);
			}
			
			String[] properties = {"sVal1", "sVal2", "sVal3", "sVal4", "sVal5", "sVal6", "sVal7"};
			List<AgentExcelData> alst = ExcelUtils.readExcel(AgentExcelData.class, excelFile.getInputStream(), properties, 0,6);
			
			if(alst != null) {
				
				int rowNo = 7;
				
				for(AgentExcelData data : alst) {
					if(data.getsVal1() == null || data.getsVal1().trim().length() == 0) {
						msg = rowNo+"행의 User를 입력하세요.";
						throw new ApiException(msg, -20);
					}
					if(data.getsVal2() == null || data.getsVal2().trim().length() == 0) {
						msg = rowNo+"행의 User Email을 입력하세요.";
						throw new ApiException(msg, -20);
					}
					if(data.getsVal3() == null || data.getsVal3().trim().length() == 0) {
						msg = rowNo+"행의 Agent IP를 입력하세요.";
						throw new ApiException(msg, -20);
					}
					if(data.getsVal4() == null || data.getsVal4().trim().length() == 0) {
						msg = rowNo+"행의 회사명을 입력하세요.";
						throw new ApiException(msg, -20);
					}
					if(data.getsVal5() == null || data.getsVal5().trim().length() == 0) {
						msg = rowNo+"행의 부서명을 입력하세요.";
						throw new ApiException(msg, -20);
					}
					if(data.getsVal6() == null || data.getsVal6().trim().length() == 0) {
						msg = rowNo+"행의 팀명 입력하세요.";
						throw new ApiException(msg, -20);
					}
				}
				
				List<Triple<String, String, Integer>> result = agentService.insertAgentExcel(alst);
				
				cr.setCode(Values.DEFAULT_SUCCESS);
				cr.setMessage(Strs.STR_OK);
				cr.setData(result);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			cr.setCode(Values.DEFAULT_ERROR);
			cr.setMessage(msg);
			return cr;
		}
		
		return cr;
	}
	
	
	@RequestMapping(value="/cAgent/getAgentCnt",produces="application/json")
	@ResponseBody
	public int getAgentCnt() {
		return agentService.getAgentCnt();
	}
	

}
