package one.auditfinder.server.mapper.config;

import java.util.Map;

public class HistoryMapperProvider {
	
	public String searchLoginHistCnt(Map<String, Object> map) {
		StringBuilder stb = new StringBuilder();
		
		stb.append("select count(seq) cnt from af_admin_login_history ");
		
		if(map.get("type").equals("1"))
			stb.append("where af_admin_id like #{text} ");
		else if(map.get("type").equals("2"))
			stb.append("where accessIp like #{text} ");
		
		return stb.toString();
	}
	
	public String searchLoginHist(Map<String, Object> map) {
		StringBuilder stb = new StringBuilder();
		
		stb.append("select seq, af_admin_id, accessIp, date_format(accessTime, '%Y-%m-%d %H:%m:%s') accessTime, loginYn from af_admin_login_history ");
		
		if(map.get("type").equals("1"))
			stb.append("where af_admin_id like #{text} ");
		else if(map.get("type").equals("2"))
			stb.append("where accessIp like #{text} ");
		
		stb.append("order by seq desc limit #{num} offset #{offset} ");
		
		return stb.toString();
	}
	
	public String searchPageHistCnt(Map<String, Object> map) {
		StringBuilder stb = new StringBuilder();
		
		stb.append("select count(seq) cnt from af_admin_page_history ");
		
		if(map.get("type").equals("1"))
			stb.append("where af_admin_id like #{text} ");
		else if(map.get("type").equals("2"))
			stb.append("where accessIp like #{text} ");
		else if(map.get("type").equals("3"))
			stb.append("where accessMenu like #{text} ");
		else if(map.get("type").equals("4"))
			stb.append("where accessId like #{text} ");
		else if(map.get("type").equals("5"))
			stb.append("where action like #{text} ");
		
		return stb.toString();
	}
	
	public String searchPageHist(Map<String, Object> map) {
		StringBuilder stb = new StringBuilder();
		
		stb.append("select seq, af_admin_id, accessIp, accessMenu, date_format(accessTime, '%Y-%m-%d %H:%m:%s') accessTime, accessTable, action, accessId from af_admin_page_history ");
		
		if(map.get("type").equals("1"))
			stb.append("where af_admin_id like #{text} ");
		else if(map.get("type").equals("2"))
			stb.append("where accessIp like #{text} ");
		else if(map.get("type").equals("3"))
			stb.append("where accessMenu like #{text} ");
		else if(map.get("type").equals("4"))
			stb.append("where accessId like #{text} ");
		else if(map.get("type").equals("5"))
			stb.append("where action like #{text} ");
		
		stb.append("order by seq desc limit #{num} offset #{offset} ");
		
		return stb.toString();
	}

}
