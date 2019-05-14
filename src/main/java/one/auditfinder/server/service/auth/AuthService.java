package one.auditfinder.server.service.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import one.auditfinder.server.comps.IAuthUtils;
import one.auditfinder.server.mapper.auth.DefaultAuth;
import one.auditfinder.server.vo.auth.LoginHistoryVo;
import one.auditfinder.server.vo.auth.LoginUserVo;
import one.auditfinder.server.vo.config.PageHistoryVo;

@Service
public class AuthService {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private DefaultAuth defaultAuth;
	
	@Autowired
	private IAuthUtils authUtils;
	
	@Transactional(readOnly = true)
	public LoginUserVo getCheckLogin(String id, String pw){
		LoginUserVo luser = defaultAuth.getCheckLogin(id);
		log.debug("pw value {}", pw);
		
		if(luser != null){
			if(pw.equals(luser.getPw())){
				authUtils.resetSession();
				authUtils.setUserInfo(luser);
				return luser;
			}
		}
		return null;
	}
	
	public void insertLoginHistory(LoginHistoryVo vo){
		defaultAuth.insertLoginHistory(vo);
	}

	public void insertPageHistory(PageHistoryVo vo) {
		defaultAuth.insertPageHistory(vo);
	}
	
	
}
