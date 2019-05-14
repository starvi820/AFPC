package one.auditfinder.server.service.main;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sun.mail.handlers.handler_base;

import one.auditfinder.server.mapper.main.MainMapper;
import one.auditfinder.server.vo.main.HeaderInfoVo;
import one.auditfinder.sever.vo.version.AcrobatVo;
import one.auditfinder.sever.vo.version.AppInfoVo;
import one.auditfinder.sever.vo.version.FlashPlayerVo;
import one.auditfinder.sever.vo.version.HotfixVo;
import one.auditfinder.sever.vo.version.JavaVo;

@Service
public class MainService {
	
	@Autowired
	private MainMapper mainMapper;
	
	
	public HeaderInfoVo getUserInfo(String id){
		return mainMapper.getUserInfo(id);
	}
	
	public List<HotfixVo> getHotfixInfo(){
		return mainMapper.getHotfixInfo();
	}
	public List<HotfixVo> hotfix_info_detail(String bulletin_id){
		return mainMapper.hotfix_info_detail(bulletin_id);
	}
	public List<HotfixVo> hotfix_info_detailAll(){
		return mainMapper.hotfix_info_detailAll();
	}
	public List<HotfixVo> get_hotfix_id(){
		return mainMapper.getHotfixInfo();
	}
	
	public List<AcrobatVo> getActobatInfo(){
		return mainMapper.getAcrobatInfo();
	}
	
	public List<FlashPlayerVo> getFlashInfo(){
		return mainMapper.getFlashInfo();
	}
	
	public List<JavaVo> getJavaInfo(){
		return mainMapper.getJavaInfo();
	}
	
	//af_app_table 
	public void insertAppInfo (AppInfoVo vo) {
		mainMapper.insertAppInfo(vo);
	}
	
	
}
