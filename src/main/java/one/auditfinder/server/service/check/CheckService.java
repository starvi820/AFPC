package one.auditfinder.server.service.check;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import one.auditfinder.server.common.Page;
import one.auditfinder.server.comps.IAuthUtils;
import one.auditfinder.server.mapper.check.CheckMapper;
import one.auditfinder.server.vo.check.CheckResultVo;
import one.auditfinder.server.vo.check.ScheduleVo;

@Service
public class CheckService {
	
	@Autowired
	private CheckMapper checkMapper;
	
	@Autowired
	private IAuthUtils authUtils;
	
	public Page<CheckResultVo> getCheckResult(int num, int particle){
		int total = checkMapper.getCheckResultCnt();
		
		Page<CheckResultVo> page = new Page<CheckResultVo>(total, num, particle);
		page.setResult(checkMapper.getCheckResult(particle, page.getOffset()));
		return page;
	}
	
	public Page<CheckResultVo> searchCheckResult(Map<String, Object> map, int num, int particle){
		int total = checkMapper.searchCheckListCnt(map);
		
		Page<CheckResultVo> page = new Page<CheckResultVo>(total, num, particle);
		map.put("num", particle);
		map.put("offset", page.getOffset());
		page.setResult(checkMapper.searchCheckResult(map));
		return page;
	}
	
	public List<ScheduleVo> getSchedule(){
		return checkMapper.getSchedule();
	}
	
	public void insertSchedule(ScheduleVo vo) {
		vo.setRegUser(authUtils.getUserInfo().getId());
		checkMapper.insertSchedule(vo);
	}
	
	public void deleteSehedule(int seq) {
		checkMapper.deleteSchedule(seq);
	}
	
	public void updateSchedule(ScheduleVo vo) {
		vo.setRegUser(authUtils.getUserInfo().getId());
		checkMapper.updateSchedule(vo);
	}
	
	public List<CheckResultVo> getCheckScore(){
		return checkMapper.getCheckScore();
	}

}
