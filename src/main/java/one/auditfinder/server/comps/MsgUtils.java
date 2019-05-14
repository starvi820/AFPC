package one.auditfinder.server.comps;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;

@Component
public class MsgUtils {
	
	@Autowired
	private MessageSourceAccessor messageSourceAccessor;
	
	public String getMsg(String code) {
		return messageSourceAccessor.getMessage(code);
	}
	
	public String getMsg( String code, Object arg1) {
		Object[] objs = new Object[1];
		objs[0] = arg1;
		return messageSourceAccessor.getMessage(code, objs);
	}
	
	public String getMsg( String code, Object arg1, Object arg2) {
		Object[] objs = new Object[2];
		objs[0] = arg1;
		objs[1] = arg2;
		return messageSourceAccessor.getMessage(code, objs);
	}
	
	public String getMsg( String code, Object arg1, Object arg2, Object arg3) {
		Object[] objs = new Object[3];
		objs[0] = arg1;
		objs[1] = arg2;
		objs[2] = arg3;
		return messageSourceAccessor.getMessage(code, objs);
	}
	
	public String getMsg( String code, Object arg1, Object arg2, Object arg3, Object arg4) {
		Object[] objs = new Object[4];
		objs[0] = arg1;
		objs[1] = arg2;
		objs[2] = arg3;
		objs[3] = arg4;
		return messageSourceAccessor.getMessage(code, objs);
	}
		
	public String getMsg( String code , Object[] args) {
		return messageSourceAccessor.getMessage(code, args);
	}
	
}
