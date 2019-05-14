package one.auditfinder.server.service.config;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import one.auditfinder.server.common.Page;
import one.auditfinder.server.mapper.config.AdminMapper;
import one.auditfinder.server.vo.config.AdminVo;


@Service
public class AdminService {
	
	@Autowired
	private AdminMapper adminMapper;
	
	public Page<AdminVo> getAdmin(int num, int particle){
		int total = adminMapper.selectAdminCount();
		
		Page<AdminVo> page = new Page<AdminVo>(total, num, particle);
		page.setResult(adminMapper.selectAdmin(particle, page.getOffset()));
		return page;
	}
	
	
	public Page<AdminVo> searchAdmin(Map<String, Object> map, int pno, int particle){
		int total = adminMapper.adminSearchCnt(map);
		
		Page<AdminVo> page = new Page<AdminVo>(total, pno, particle);
		map.put("num", particle);
		map.put("offset", page.getOffset());
		page.setResult(adminMapper.adminSearch(map));
		return page;
	}
	
	public AdminVo selectAdminBySeq(int seq) {
		return adminMapper.selectAdminBySeq(seq);
	}
	
	public int idChk(String id) {
		return adminMapper.idChk(id);
	}
	
	public void insertAdmin(AdminVo vo) {
		adminMapper.insertAdmin(vo);
	}
	
	public void deleteAdmin(int seq) {
		adminMapper.deleteAdmin(seq);
	}
	
	public void updateAdmin(AdminVo vo) {
		adminMapper.updateAdmin(vo);
	}
	
	
}
