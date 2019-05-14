package one.auditfinder.server.service.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import one.auditfinder.server.mapper.config.DepartMapper;
import one.auditfinder.server.vo.config.CompanyVo;
import one.auditfinder.server.vo.config.DepartmentVo;
import one.auditfinder.server.vo.config.TeamVo;

@Service
public class DepartService {
	
	@Autowired
	private DepartMapper departMapper;
	
	//회사
	public List<CompanyVo> getCompany(){
		return departMapper.getCompany();
	}
	
	public void insertCompany(CompanyVo vo){
		departMapper.insertCompany(vo.getName());
	}
	
	public void updateCompany(CompanyVo vo) {
		departMapper.updateCompany(vo.getName(), vo.getSeq());
	}
	
	@Transactional
	public void deleteCompany(CompanyVo vo) {
		departMapper.deleteCompanyMapping(vo.getSeq());
		departMapper.deleteCompany(vo.getSeq());
	}
	
	
	//부서
	public List<DepartmentVo> getDepart(int seq){
		return departMapper.getDepart(seq);
	}
	
	@Transactional
	public void insertDepart(DepartmentVo vo) {
		
		DepartmentVo dVo = departMapper.getDepartName(vo.getName());
		
		if(dVo != null ) {
			departMapper.insertDepartMapping(vo.getAf_company_seq(), dVo.getSeq());
		}else if(dVo == null){
			departMapper.insertDepart(vo.getName());
			departMapper.insertDepartMapping(vo.getAf_company_seq(), departMapper.getDepartSeq());
		}
		
	}
	
	public void updateDepeart(DepartmentVo vo) {
		departMapper.updateDepart(vo.getName(), vo.getSeq());
	}
	
	@Transactional
	public void deleteDepart(DepartmentVo vo) {
		departMapper.deleteDepartMapping(vo.getAf_company_seq(), vo.getSeq());
		departMapper.deleteDepart(vo.getSeq());
		
	}
	
	//팀
	
	public List<TeamVo> getTeam(int seq, int departSeq){
		return departMapper.getTeam(seq, departSeq);
	}
	
	@Transactional
	public void insertTeam(TeamVo vo) {
		
		int idx = departMapper.selectTeamMappingCheck(vo.getAf_company_seq(), vo.getAf_department_seq());
		if(idx > 0) {
			departMapper.deleteDepartMapping(vo.getAf_company_seq(), vo.getAf_department_seq());
		}
		
		TeamVo tVo = departMapper.getTeamName(vo.getName());
		
		if(tVo == null) {
			departMapper.insertTeam(vo.getName());
			departMapper.insertTeamMapping(vo.getAf_company_seq(), vo.getAf_department_seq(), departMapper.getTeamSeq());
		}else if(tVo != null) {
			departMapper.insertTeamMapping(vo.getAf_company_seq(), vo.getAf_department_seq(), tVo.getSeq());
		}
		
		
		
	}
	
	public void updateTeam(TeamVo vo) {
		departMapper.updateTeam(vo.getName(), vo.getSeq());
	}
	
	@Transactional
	public void deleteTeam(TeamVo vo) {
		departMapper.deleteTeamMapping(vo.getAf_company_seq(), vo.getAf_department_seq(), vo.getSeq());
		departMapper.deleteTeam(vo.getSeq());
	}

}
