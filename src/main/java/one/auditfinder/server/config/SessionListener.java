package one.auditfinder.server.config;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SessionListener implements HttpSessionListener {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public void sessionCreated(HttpSessionEvent event) {
		HttpSession session = event.getSession();
		session.setMaxInactiveInterval(20*60);
		if(logger.isDebugEnabled()) {
			logger.debug("{} Session Created." , session.getId());
		}
	}
	

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		HttpSession session = event.getSession();
		if(logger.isDebugEnabled()) {
			logger.debug("{} Session Destory.", session.getId());
		}
	}

}
