package one.auditfinder.server.mapper.agent;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;

import one.auditfinder.server.vo.agent.AgentVo;
import one.auditfinder.server.vo.config.CompanyVo;
import one.auditfinder.server.vo.config.DepartmentVo;
import one.auditfinder.server.vo.config.TeamVo;

public interface AgentMapper {
	
	final String GET_AGENT_COUNT = "select count(*) from af_agent where agentUseYn = 1 ";
	
//	final String GET_AGENT = "select seq, af_group_mapping_seq, agentId, date_format(agentRegTime, '%Y-%m-%d %H:%m:%s') agentRegTime, agentIp, agentStatus, agentEmail, agentUser, agentAccountCode, agentUseYn from af_agent where agentUseYn = 1 order by seq desc limit #{num} offset #{offset}";
	
	final String GET_AGENT = "select a.seq as seq, agentId, date_format(agentRegTime, '%Y-%m-%d %H:%m:%s') agentRegTime, agentIp, agentStatus, agentEmail, agentUser, agentAccountCode, agentUseYn , company companyName, department departName, team teamName  from af_agent a " + 
								"	  left join (select agm.seq as seq, ac.name as company, ad.name as department, at.name as team" + 
								"     from af_group_mapping agm, af_team at, af_department ad, af_company ac" + 
								"     where agm.af_company_seq = ac.seq and agm.af_department_seq = ad.seq and agm.af_team_seq = at.seq) b" + 
								"	  on a.af_group_mapping_seq = b.seq where a.agentUseYn = 1 order by a.seq desc limit #{num} offset #{offset} ";
	
	final String GET_AGENT_BYSEQ = "select seq, af_group_mapping_seq, agentId, date_format(agentRegTime, '%Y-%m-%d %H:%m:%s') agentRegTime, agentIp, agentStatus, agentEmail, agentUser, agentAccountCode, agentUseYn from af_agent where seq = #{seq}";
	
	final String GET_AGENT_BYNAME = "select count(*) cnt from af_agent where agentId = #{agentId}";
	
	@Select(GET_AGENT_COUNT)
	int getAgentCnt();
	
	@Select(GET_AGENT)
	List<AgentVo> getAgent(@Param("num") int num, @Param("offset") int offset);
	
	@SelectProvider(type=one.auditfinder.server.mapper.agent.AgentMapperProvider.class, method="searchAgentCnt")
	int searchAgentCnt(Map<String, Object> map);
	
	@SelectProvider(type=one.auditfinder.server.mapper.agent.AgentMapperProvider.class, method="searchAgent")
	List<AgentVo> searchAgent(Map<String, Object> map);
	
	@Select(GET_AGENT_BYSEQ)
	AgentVo getAgentBySeq(@Param("seq") int seq);
	
	
	@Select(GET_AGENT_BYNAME)
	int getAgentByName(@Param("agentId") String agentId);
	
	final String GET_COMPANY_NAME = "select seq, name from af_company where seq = (select af_company_seq from af_group_mapping where seq = #{seq}) ";
	
	final String GET_DEPARTMENT_NAME = "select seq, name from af_department where seq = (select af_department_seq from af_group_mapping where seq = #{seq})";
	
	final String GET_TEAM_NAME = "select seq, name from af_team where seq = (select af_team_seq from af_group_mapping where seq = #{seq})";
	
	final String GET_COMPANY_CHK = "select count(*) cnt from af_company where name = #{name}";
	
	final String GET_DEPARTMENT_CHK = "select count(*) cnt from af_department where name = #{name}";
	
	final String GET_TEAM_CHK = "select count(*) cnt from af_team where name = #{name}";
	
	final String GET_COMPANY_SEQ = "select seq from af_company where name = #{name}";
	
	final String GET_DEPARTMENT_SEQ = "select seq from af_department where name = #{name}";
	
	final String GET_TEAM_SEQ = "select seq from af_team where name = #{name}";
	
	
	@Select(GET_COMPANY_NAME)
	CompanyVo getCompanyName(@Param("seq") int seq);
	
	@Select(GET_DEPARTMENT_NAME)
	DepartmentVo getDepartmentName(@Param("seq") int seq);
	
	@Select(GET_TEAM_NAME)
	TeamVo getTeamName(@Param("seq") int seq);
	
	@Select(GET_COMPANY_CHK)
	int getCompanyChk(@Param("name") String name);
	
	@Select(GET_DEPARTMENT_CHK)
	int getDepartmentChk(@Param("name") String name);
	
	@Select(GET_TEAM_CHK)
	int getTeamChk(@Param("name") String name);
	
	@Select(GET_COMPANY_SEQ)
	int getCompanySeq(@Param("name") String name);
	
	@Select(GET_DEPARTMENT_SEQ)
	int getDepartmentSeq(@Param("name") String name);
	
	@Select(GET_TEAM_SEQ)
	int getTeamSeq(@Param("name") String name);
	
	final String GET_LAST_SEQ = "select seq+1 seq from af_agent order by seq desc limit 1";
	
	final String INSERT_AGENT = "insert into af_agent(af_group_mapping_seq, agentId, agentRegTime, agentIp, agentStatus, agentAccountCode, agentEmail, agentUser, agentUseYn) values(#{af_group_mapping_seq}, #{agentId}, now(), #{agentIp}, 2, #{agentAccountCode}, #{agentEmail}, #{agentUser}, 1)";
	
	final String UPDATE_AGENT = "update af_agent set af_group_mapping_seq = #{af_group_mapping_seq}, agentIp = #{agentIp}, agentUser = #{agentUser}, agentEmail = #{agentEmail} where seq = #{seq}";
	
	final String DELETE_AGENT = "update af_agent set agentUseYn = 0 where seq = #{seq}";
	
	final String GET_GROUP_MAPPING_SEQ = "select seq from af_group_mapping where af_company_seq = #{companySeq} and af_department_seq = #{departSeq} and af_team_seq = #{teamSeq}";
	
	
	
	@Select(GET_LAST_SEQ)
	int getLastAgentSeq();
	
	@Insert(INSERT_AGENT)
	void insertAgent(AgentVo vo);
	
	@Update(UPDATE_AGENT)
	void updateAgent(AgentVo vo);
	
	@Update(DELETE_AGENT)
	void deleteAgent(@Param("seq") int seq);
	
	@Select(GET_GROUP_MAPPING_SEQ)
	int getGroupMappingSeq(@Param("companySeq") int companySeq, @Param("departSeq") int departSeq, @Param("teamSeq") int teamSeq);
}
