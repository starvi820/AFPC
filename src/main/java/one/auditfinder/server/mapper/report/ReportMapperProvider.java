package one.auditfinder.server.mapper.report;

import java.util.Map;

public class ReportMapperProvider {
	
	
	public String searchReportCnt (Map<String, Object> map) {
		StringBuilder stb = new StringBuilder();
		
		stb.append("select count(*) from af_report");
		
		if(map.get("type").equals("1"))
			stb.append("where reportId like #{text}");
		else if(map.get("type").equals("2"))
			stb.append("where reportId like #{text}");
		else if(map.get("type").equals("3"))
			stb.append("where regAgent like #{text}");
		
		return stb.toString();
			
	}
	
		
	
	

}
