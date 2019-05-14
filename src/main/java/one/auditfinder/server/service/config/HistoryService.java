package one.auditfinder.server.service.config;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import one.auditfinder.server.common.Page;
import one.auditfinder.server.mapper.config.HistoryMapper;
import one.auditfinder.server.vo.config.LoginHistoryVo;
import one.auditfinder.server.vo.config.PageHistoryVo;

@Service
public class HistoryService {

	@Autowired
	private HistoryMapper historyMapper;
	
	public Page<LoginHistoryVo> getLoginHistory(int num, int particle){
		int total = historyMapper.getLoginHistCnt();
		
		Page<LoginHistoryVo> page = new Page<LoginHistoryVo>(total, num, particle);
		page.setResult(historyMapper.getLoginHist(particle, page.getOffset()));
		return page;
	}
	
	public Page<LoginHistoryVo> searchLoginHistory(Map<String, Object> map, int num, int particle){
		int total = historyMapper.searchLoginHistCnt(map);
		
		Page<LoginHistoryVo> page = new Page<LoginHistoryVo>(total, num, particle);
		map.put("num", particle);
		map.put("offset", page.getOffset());
		page.setResult(historyMapper.searchLoginHist(map));
		return page;
	}
	
	public Page<PageHistoryVo> getPageHistory(int num, int particle){
		int total = historyMapper.getPageHistCnt();
		
		Page<PageHistoryVo> page = new Page<PageHistoryVo>(total, num, particle);
		page.setResult(historyMapper.getPageHist(particle, page.getOffset()));
		return page;
	}
	
	public Page<PageHistoryVo> searchPageHistory(Map<String, Object> map, String text, int num, int particle){
		if(map.get("type").equals("5")) {
			if(text.equals("등록") || text.equals("등") || text.equals("록"))
				map.put("text", "I");
			else if(text.equals("수정") || text.equals("수") || text.equals("정"))
				map.put("text", "U");
			else if(text.equals("삭제") || text.equals("삭") || text.equals("제"))
				map.put("text", "D");
			else
				map.put("text", "");
		}else{
			map.put("text", new StringBuilder().append("%").append(text).append("%").toString());
		}
		
		int total = historyMapper.searchPageHistCnt(map);
		
		Page<PageHistoryVo> page = new Page<PageHistoryVo>(total, num, particle);
		map.put("num", particle);
		map.put("offset", page.getOffset());
		
		page.setResult(historyMapper.searchPageHist(map));
		return page;
	}
}
