package one.auditfinder.server.mapper.config;

import java.util.Map;

public class AdminMapperProvider {
	
	public String adminSearchCnt(Map<String, Object> map) {
		StringBuilder stb = new StringBuilder();
		
		stb.append("select count(*) cnt from af_admin ");
		
		if(map.get("type").equals("1"))
			stb.append("where id like #{text} ");
		else if(map.get("type").equals("2"))
			stb.append("where name like #{text} ");
		else if(map.get("type").equals("3"))
			stb.append("where email like #{text} ");
		else if(map.get("type").equals("4"))
			stb.append("where tel like #{text} ");
		
		return stb.toString();
	}
	
	public String adminSearch(Map<String, Object> map) {
		StringBuilder stb = new StringBuilder();
		
		stb.append("select seq, id, name, email, tel, date_format(regDate, '%Y-%m-%d %H:%m:%s') regDate from af_admin ");
		
		if(map.get("type").equals("1"))
			stb.append("where id like #{text} ");
		else if(map.get("type").equals("2"))
			stb.append("where name like #{text} ");
		else if(map.get("type").equals("3"))
			stb.append("where email like #{text} ");
		else if(map.get("type").equals("4"))
			stb.append("where tel like #{text} ");
		
		stb.append("order by seq desc limit #{num} offset #{offset} ");
		
		return stb.toString();
	}

}
