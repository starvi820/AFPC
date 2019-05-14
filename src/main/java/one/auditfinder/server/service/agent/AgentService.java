package one.auditfinder.server.service.agent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import one.auditfinder.server.common.Page;
import one.auditfinder.server.common.Triple;
import one.auditfinder.server.comps.IAuthUtils;
import one.auditfinder.server.mapper.agent.AgentMapper;
import one.auditfinder.server.service.auth.AuthService;
import one.auditfinder.server.vo.agent.AgentVo;
import one.auditfinder.server.vo.common.AgentExcelData;
import one.auditfinder.server.vo.config.PageHistoryVo;

@Service
public class AgentService {
	
	@Autowired
	private AgentMapper agentMapper;
	
	@Autowired
	private AuthService authService;
	
	@Autowired
	private IAuthUtils authUtils;
	
	public Page<AgentVo> getAgent(int num, int particle){
		int total = agentMapper.getAgentCnt();
		
		Page<AgentVo> page = new Page<AgentVo>(total, num, particle);
		page.setResult(agentMapper.getAgent(particle, page.getOffset()));
		return page;
	}
	
	public Page<AgentVo> searchAgent(Map<String, Object> map, int num, int particle){
		int total = agentMapper.searchAgentCnt(map);
		
		Page<AgentVo> page = new Page<AgentVo>(total, num, particle);
		map.put("num", particle);
		map.put("offset", page.getOffset());
		page.setResult(agentMapper.searchAgent(map));
		return page;
	}
	
	public AgentVo getAgentBySeq(int seq) {
		
		AgentVo avo = agentMapper.getAgentBySeq(seq);
		
		int mappingSeq = avo.getAf_group_mapping_seq();
		
		avo.setCompanySeq(agentMapper.getCompanyName(mappingSeq).getSeq());
		avo.setCompanyName(agentMapper.getCompanyName(mappingSeq).getName());
		
		avo.setDepartSeq(agentMapper.getDepartmentName(mappingSeq).getSeq());
		avo.setDepartName(agentMapper.getDepartmentName(mappingSeq).getName());
		
		avo.setTeamSeq(agentMapper.getTeamName(mappingSeq).getSeq());
		avo.setTeamName(agentMapper.getTeamName(mappingSeq).getName());
		
		return avo;
	}
	
	
	@Transactional
	public void insertAgent(AgentVo vo) {
		
		StringBuilder stb = new StringBuilder();
		String agentId = stb.append("AF_PC").append(agentMapper.getLastAgentSeq()).toString();
		vo.setAgentId(agentId);
		vo.setAgentAccountCode(UUID.randomUUID().toString());
		vo.setAf_group_mapping_seq(agentMapper.getGroupMappingSeq(vo.getCompanySeq(), vo.getDepartSeq(), vo.getTeamSeq()));
		agentMapper.insertAgent(vo);
	}
	
		
	@Transactional
	public List<Triple<String, String, Integer>> insertAgentExcel(List<AgentExcelData> lst) {
		
		PageHistoryVo pVo = new PageHistoryVo();
		
		pVo.setAf_admin_id(authUtils.getUserInfo().getId());
		
		HttpServletRequest req = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
        String ip = req.getHeader("WL-Proxy-Client-IP");
        if (ip == null)
        	pVo.setAccessIp(req.getRemoteAddr());
        
        pVo.setAccessMenu("점검대상관리/Agent관리");
        pVo.setAccessTable("af_agent");    
        pVo.setAction("I");
        
		List<Triple<String, String, Integer>> result = new ArrayList<Triple<String, String, Integer>>();
		
		for(AgentExcelData data : lst) {
			AgentVo vo = new AgentVo();
			vo.setAgentAccountCode(UUID.randomUUID().toString());
			
			StringBuilder stb = new StringBuilder();
			String agentId = stb.append("AF_PC").append(agentMapper.getLastAgentSeq()).toString();
			vo.setAgentId(agentId);
			
			
			vo.setAgentUser(data.getsVal1());
			vo.setAgentEmail(data.getsVal2());
			vo.setAgentIp(data.getsVal3());      
			
			int cSeq = 0;
			int dSeq = 0;
			int tSeq = 0;
			
			int cChk = agentMapper.getCompanyChk(data.getsVal4());
			if(cChk > 0) {
				cSeq = agentMapper.getCompanySeq(data.getsVal4());
			}
			
			int dChk = agentMapper.getDepartmentChk(data.getsVal5());
			if(dChk > 0) {
				dSeq = agentMapper.getDepartmentSeq(data.getsVal5());
			}
			
			int tChk = agentMapper.getTeamChk(data.getsVal6());
			if(tChk > 0) {
				tSeq = agentMapper.getTeamSeq(data.getsVal6());
			}
			
			Triple<String, String, Integer> triple = new Triple<String, String, Integer>();
			triple.setFirst(agentId);
			triple.setSecond(data.getsVal1()); 
			
			if(cChk > 0 && dChk > 0 && tChk > 0) {
				vo.setAf_group_mapping_seq(agentMapper.getGroupMappingSeq(cSeq, dSeq, tSeq));
				agentMapper.insertAgent(vo);
				triple.setThird(1);
				pVo.setAccessId(agentId);
				authService.insertPageHistory(pVo);
			}else {
				triple.setThird(0);
			}
			result.add(triple);
		}
		return result;
	}
	
	
	@Transactional
	public void updateAgent(AgentVo vo) {
		vo.setAf_group_mapping_seq(agentMapper.getGroupMappingSeq(vo.getCompanySeq(), vo.getDepartSeq(), vo.getTeamSeq()));
		agentMapper.updateAgent(vo);
	}
	
	@Transactional
	public void deleteAgent(int seq) {
		agentMapper.deleteAgent(seq);
	}
	
	@Transactional
	public int getAgentCnt() {
		return agentMapper.getAgentCnt();
	}

}
