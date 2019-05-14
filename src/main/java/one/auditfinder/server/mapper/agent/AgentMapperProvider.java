package one.auditfinder.server.mapper.agent;

import java.util.Map;

public class AgentMapperProvider {

	public String searchAgentCnt(Map<String, Object> map) {
		StringBuilder stb = new StringBuilder();
		
		stb.append("select count(*) from af_agent ");
		
		if(map.get("type").equals("1"))
			stb.append("where agentId like #{text} ");
		else if(map.get("type").equals("2"))
			stb.append("where agentIp like #{text} ");
		else if((map.get("type").equals("3")))
			stb.append("where agentUser like #{text} ");
		
		stb.append("and agentUseYn = 1");
		
		return stb.toString();
	}
	
	public String searchAgent(Map<String, Object> map) {
		StringBuilder stb = new StringBuilder();
		
		stb.append("select a.seq as seq, agentId, date_format(agentRegTime, '%Y-%m-%d %H:%m:%s') agentRegTime, agentIp, agentStatus, agentEmail, agentUser, agentAccountCode, agentUseYn , company companyName, department departName, team teamName  from af_agent a " + 
					"	  left join (select agm.seq as seq, ac.name as company, ad.name as department, at.name as team " + 
					"     from af_group_mapping agm, af_team at, af_department ad, af_company ac " + 
					"     where agm.af_company_seq = ac.seq and agm.af_department_seq = ad.seq and agm.af_team_seq = at.seq) b "  + 
					"	  on a.af_group_mapping_seq = b.seq ");
		
		if(map.get("type").equals("1"))
			stb.append("where agentId like #{text} ");
		else if(map.get("type").equals("2"))
			stb.append("where agentIp like #{text} ");
		else if((map.get("type").equals("3")))
			stb.append("where agentUser like #{text} ");
			
		stb.append("and agentUseYn = 1 order by seq desc limit #{num} offset #{offset} ");
		return stb.toString();
	}

}
