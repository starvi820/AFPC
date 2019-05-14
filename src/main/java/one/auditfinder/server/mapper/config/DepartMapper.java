package one.auditfinder.server.mapper.config;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import one.auditfinder.server.vo.config.CompanyVo;
import one.auditfinder.server.vo.config.DepartmentVo;
import one.auditfinder.server.vo.config.TeamVo;

public interface DepartMapper {
	
	//회사 
	
	final String GET_COMPANY = "select seq, name from af_company";
	
	final String INSERT_COMPANY = "insert into af_company(name) values(#{name})";
	
	final String UPDATE_COMPANY = "update af_company set name = #{name} where seq = #{seq}";
	
	final String DELETE_COMPANY = "delete from af_company where seq = #{seq}";
	
	final String DELETE_COMPANY_MAPPING = "delete from af_group_mapping where af_company_seq = #{af_company_seq}";
	
	@Select(GET_COMPANY)
	List<CompanyVo> getCompany();
	
	@Insert(INSERT_COMPANY)
	void insertCompany(@Param("name") String name);
	
	@Update(UPDATE_COMPANY)
	void updateCompany(@Param("name") String name, @Param("seq") int seq);
	
	@Delete(DELETE_COMPANY)
	void deleteCompany(@Param("seq") int seq);
	
	@Delete(DELETE_COMPANY_MAPPING)
	void deleteCompanyMapping(@Param("af_company_seq") int af_company_seq);
	
	//부서
	
	final String GET_DEPART = "select ad.seq, ad.name, agm.af_company_seq from af_department ad, af_group_mapping agm where ad.seq = agm.af_department_seq and af_company_seq = #{seq} group by seq";
	
	final String GET_DEPART_SEQ = "select seq from af_department order by seq desc limit 1";
	
	final String GET_DEPART_NAME = "select seq, name from af_department where name = #{name}";
	
	final String INSERT_DEPART = "insert into af_department(name) values (#{name})";
	
	final String INSERT_DEPART_MAPPING = "insert into af_group_mapping(af_company_seq, af_department_seq, af_team_seq) values(#{af_company_seq}, #{af_department_seq}, 0)";
	
	final String UPDATE_DEPART = "update af_department set name = #{name} where seq = #{seq}";
	
	final String DELETE_DEPART = "delete from af_department where seq = #{seq}";
	
	final String DELETE_DEPART_MAPPING = "delete from af_group_mapping where af_company_seq = #{af_company_seq} and af_department_seq = #{af_department_seq}";
	
	@Select(GET_DEPART)
	List<DepartmentVo> getDepart(@Param("seq") int seq);
	
	@Select(GET_DEPART_SEQ)
	int getDepartSeq();
	
	@Select(GET_DEPART_NAME)
	DepartmentVo getDepartName(@Param("name") String name);
	
	@Insert(INSERT_DEPART)
	void insertDepart(@Param("name") String name);
	
	@Insert(INSERT_DEPART_MAPPING)
	void insertDepartMapping(@Param("af_company_seq") int af_company_seq, @Param("af_department_seq") int af_department_seq);
	
	@Update(UPDATE_DEPART)
	void updateDepart(@Param("name") String name, @Param("seq") int seq);
	
	@Delete(DELETE_DEPART)
	void deleteDepart(@Param("seq") int seq);
	
	@Delete(DELETE_DEPART_MAPPING)
	void deleteDepartMapping(@Param("af_company_seq") int af_company_seq, @Param("af_department_seq") int af_department_seq);
	
	
	//팀
	
	final String GET_TEAM = "select at.seq, at.name, agm.af_company_seq, agm.af_department_seq from af_team at, af_group_mapping agm where at.seq = agm.af_team_seq and af_company_seq = #{seq} and af_department_seq = #{departSeq} group by seq";
	
	final String GET_TEAM_SEQ = "select seq from af_team order by seq desc limit 1";
	
	final String GET_TEAM_NAME = "select seq, name from af_team where name = #{name}";
	
	final String INSERT_TEAM = "insert into af_team(name) values(#{name})";
	
	final String TEAM_MAPPING_CHECK = "select count(*) from af_group_mapping where af_company_seq = #{af_company_seq} and af_department_seq = #{af_department_seq} and af_team_seq = 0";
	
	final String TEAM_MAPPING_CHECK_DELETE = "delete from af_group_mapping where af_company_seq = #{af_company_seq} and af_department_seq = #{af_department_seq} and af_team_seq = 0";
	
	final String INSERT_TEAM_MAPPING = "insert into af_group_mapping(af_company_seq, af_department_seq, af_team_seq) values (#{af_company_seq}, #{af_department_seq}, #{af_team_seq})";
	
	final String UPDATE_TEAM = "update af_team set name = #{name} where seq = #{seq}";
	
	final String DELETE_TEAM = "delete from af_team where seq = #{seq}";
	
	final String DELETE_TEAM_MAPPING = "delete from af_group_mapping where af_company_seq = #{af_company_seq} and af_department_seq = #{af_department_seq} and af_team_seq = #{af_team_seq}";
	
	@Select(GET_TEAM)
	List<TeamVo> getTeam(@Param("seq") int seq, @Param("departSeq") int departSeq);
	
	@Select(GET_TEAM_SEQ)
	int getTeamSeq();
	
	@Select(GET_TEAM_NAME)
	TeamVo getTeamName(@Param("name") String name);
	
	@Insert(INSERT_TEAM)
	void insertTeam(@Param("name") String name);
	
	@Select(TEAM_MAPPING_CHECK)
	int selectTeamMappingCheck(@Param("af_company_seq") int af_company_seq, @Param("af_department_seq") int af_department_seq);
	
	@Delete(TEAM_MAPPING_CHECK_DELETE)
	void deleteTeamMAppingCheck(@Param("af_company_seq") int af_company_seq, @Param("af_department_seq") int af_department_seq);
	
	@Insert(INSERT_TEAM_MAPPING)
	void insertTeamMapping(@Param("af_company_seq") int af_company_seq, @Param("af_department_seq") int af_department_seq, @Param("af_team_seq") int af_team_seq);
	
	@Update(UPDATE_TEAM)
	void updateTeam(@Param("name") String name, @Param("seq") int seq);
	
	@Delete(DELETE_TEAM)
	void deleteTeam(@Param("seq") int seq);
	
	@Delete(DELETE_TEAM_MAPPING)
	void deleteTeamMapping(@Param("af_company_seq") int af_company_seq, @Param("af_department_seq") int af_department_seq, @Param("af_team_seq") int af_team_seq);
	

}
