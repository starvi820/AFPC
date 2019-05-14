package one.auditfinder.server.mapper.chart;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import one.auditfinder.server.common.Pair;

public interface ChartMapper {
	
	//점검 형태 비율
	
	final String CNT_CHECK_NONAUTO ="select count(*) from af_check_list_result where checktype=1 ";
	
	final String CNT_CHECK_AUTO = "select count(*) from af_check_list_result where checktype=0  ";
	
	final String CNT_CHECK = "select count(*) from af_check_list_result where checktype = #{type}  ";
	
	final String CNT_CHECK_SEARCH = "select count(*) from af_check_list_result where checktype = #{type} and checkDate >= #{startDate} and checkDate <= #{endDate} ";
	
	@Select(CNT_CHECK_NONAUTO)
	int checkNonAuto();
	
	@Select(CNT_CHECK_AUTO)
	int checkAuto();
	
	@Select(CNT_CHECK)
	int checkCnt(@Param("type") int type);
	
	@Select(CNT_CHECK_SEARCH)
	int checkCntSearch(@Param("type") int type, @Param("sDate") String sDate, @Param("eDate") String eDate);
		
	// 점검 수행일 별 agent 수
	
	final String CNT_CHECKDATE_AGENT = "select count(seq) from af_check_list_result where checkDate = #{checkDate}";
	
	final String GET_CHECKDATE = "select date_format(checkDate,'%Y-%m')checkDate from af_check_list_result group by checkDate";
	
	final String CNT_AGENT_CHECK_SEARCH = "select count(seq) from af_check_list_result where checkDate >= #{sDate} and checkDate <= #{eDate}"; 
	
	@Select(CNT_AGENT_CHECK_SEARCH)
	int AgentCheckSearch(@Param("sDate") String sDate , @Param("eDate") String eDate);
	
	@Select(CNT_CHECKDATE_AGENT)
	int cntCheckDateAgent();
	
	@Select(GET_CHECKDATE)
	List<String> getCheckDate();  
	
	//점검 수행일 별 결과
	
	final String GET_CHECKDATE_RESULT = "select result from af_check_list_result where date_format(checkDate,'%Y-%m')checkDate = #{checkDate}";
	
	final String CNT_RESULT_SAFTY = "select count(result) from af_check_list_result where result = 0 ";
	
	final String CNT_RESULT_DANGER = "select count(result) from af_check_list_result where result = 1 ";
	
	final String CNT_RESULT = "select  count(a.seq) cnt  from af_check_list_result a, af_agent b "+ 
			"where a.af_agent_seq = b.seq "+ 
			"and b.af_group_mapping_seq = 2 and b.agentUseYn = 1 "+ 
			"and a.result = #{status}";
	
	
	@Select(CNT_RESULT)
	Pair<Integer, Integer> cntResultAll();
	
	@Select(CNT_RESULT_SAFTY)
	int cntResultSafty();
	
	@Select(CNT_RESULT_DANGER)
	int cntResultDanger();
	
	
	// 부서별 차트
	final String GET_TEAM_NAME = "select name from af_team";
	
	@Select(GET_TEAM_NAME)
	List<String> getTeam();
	
	final String CNT_TEAM_AGENT = "select count(c.result) from af_team a,af_group_mapping b , af_check_list_result c where a.seq = b.af_team_seq and c.result =1 ";
	
	final String CNT_TEAM_SAFTY = "select count(c.result) from af_team a,af_group_mapping b , af_check_list_result c where a.seq = b.af_team_seq and c.result =0 ";
	
	@Select(CNT_TEAM_SAFTY)
	List<Integer> cntTeamSafty();
	
	@Select(CNT_TEAM_AGENT)
	List<Integer> cntTeamAgent();
	
	
	final String GET_RESULT = "select  count(a.seq) cnt  from af_check_list_result a, af_agent b "+ 
								"where a.af_agent_seq = b.seq "+ 
								"and b.af_group_mapping_seq = 2 and b.agentUseYn = 1 "+ 
								"and a.result = #{status}";
	
	@Select(GET_RESULT)
	int getResult(@Param("status") int status);
	
	
	
	

}
