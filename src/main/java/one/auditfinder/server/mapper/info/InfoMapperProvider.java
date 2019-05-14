package one.auditfinder.server.mapper.info;

import java.util.Map;

public class InfoMapperProvider {
	
	public String infoSearchCnt(Map<String, Object> map) {
		
		StringBuilder stb = new StringBuilder();
		
		stb.append("select count(*) cnt from af_info ");
		
		if(map.get("type").equals("1"))
			stb.append("where title like #{text} ");
		else if(map.get("type").equals("2"))
			stb.append("where af_admin_id like #{text} ");
		
		return stb.toString();
	}
	
	public String infoSearch(Map<String, Object> map) {
		
		StringBuilder stb = new StringBuilder();
		
		stb.append("select seq, af_admin_id, title, date_format(regDate, '%Y-%m-%d %H:%m:%s') regDate, content from af_info ");
		
		if(map.get("type").equals("1"))
			stb.append("where title like #{text} ");
		else if(map.get("type").equals("2"))
			stb.append("where af_admin_id like #{text} ");
		
		stb.append("order by seq desc limit #{num} offset #{offset} ");
		
		return stb.toString();
	}

}
