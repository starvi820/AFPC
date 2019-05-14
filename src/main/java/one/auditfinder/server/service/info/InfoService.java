package one.auditfinder.server.service.info;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import one.auditfinder.server.common.Page;
import one.auditfinder.server.mapper.info.InfoMapper;
import one.auditfinder.server.vo.info.InfoVo;

@Service
public class InfoService {
	
	@Autowired
	private InfoMapper infoMapper;
	
	public Page<InfoVo> getInfo(int num, int particle){
		int total = infoMapper.selectInfoCnt();
		
		Page<InfoVo> page = new Page<InfoVo>(total, num, particle);
		page.setResult(infoMapper.selectInfo(particle, page.getOffset()));
		return page;
	}
	
	public Page<InfoVo> searchInfo(Map<String, Object> map, int pno, int particle){
		int total = infoMapper.infoSearchCnt(map);
		
		Page<InfoVo> page = new Page<InfoVo>(total, pno, particle);
		map.put("num", particle);
		map.put("offset", page.getOffset());
		page.setResult(infoMapper.searchInfo(map));
		return page;
	}
	
	@Transactional
	public void insertInfo(InfoVo vo) {
		infoMapper.insertInfo(vo);
	}
	
	@Transactional
	public void updateInfo(InfoVo vo) {
		infoMapper.updateInfo(vo);
	}
	
	@Transactional
	public void deleteInfo(int seq) {
		infoMapper.deleteInfo(seq);
	}

}
