package one.auditfinder.server.mapper.check;

import java.util.Map;

public class CheckMapperProvider {

	public String searchCheckResultCnt(Map<String, Object> map) {
		StringBuilder stb = new StringBuilder();

		stb.append("select count(b.seq) cnt " 
					+ "from af_group_mapping a "
					+ "left outer join af_agent b on a.seq = b.af_group_mapping_seq "
					+ "inner join af_check_list_result c on b.seq = c.af_agent_seq "
					+ "left outer join af_company d on a.af_company_seq = d.seq "
					+ "left outer join af_department e on a.af_department_seq = e.seq "
					+ "left outer join af_team f on a.af_team_seq = f.seq ");

		if (map.get("type").equals("1"))
			stb.append("where b.agentId like #{text} ");
		else if (map.get("type").equals("2"))
			stb.append("where b.agentUser like #{text} ");
		else if (map.get("type").equals("3"))
			stb.append("where d.name like #{text} or e.name like #{text} or f.name like #{text} ");

		return stb.toString();
	}

	public String searchCheckResult(Map<String, Object> map) {
		StringBuilder stb = new StringBuilder();

		stb.append("select b.seq seq, agentId, agentUser, result, score, checkType, date_format(checkDate, '%Y-%m-%d %H:%m:%s') checkDate, d.name companyName, e.name departName, f.name teamName " 
					+ "from af_group_mapping a "
					+ "left outer join af_agent b on a.seq = b.af_group_mapping_seq "
					+ "inner join af_check_list_result c on b.seq = c.af_agent_seq "
					+ "left outer join af_company d on a.af_company_seq = d.seq "
					+ "left outer join af_department e on a.af_department_seq = e.seq "
					+ "left outer join af_team f on a.af_team_seq = f.seq ");

		if (map.get("type").equals("1"))
			stb.append("where b.agentId like #{text} ");
		else if (map.get("type").equals("2"))
			stb.append("where b.agentUser like #{text} ");
		else if (map.get("type").equals("3"))
			stb.append("where d.name like #{text} or e.name like #{text} or f.name like #{text} ");
		
		stb.append("order by checkDate desc limit #{num} offset #{offset} ");

		return stb.toString();
	}

}
