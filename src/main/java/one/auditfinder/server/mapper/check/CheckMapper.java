package one.auditfinder.server.mapper.check;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;

import one.auditfinder.server.vo.check.CheckResultVo;
import one.auditfinder.server.vo.check.ScheduleVo;

public interface CheckMapper {
	
	final String GET_CHECK_RESULT_CNT = "select count(b.seq) cnt "+
										"from af_group_mapping a " + 
										"left outer join af_agent b on a.seq = b.af_group_mapping_seq " + 
										"inner join af_check_list_result c on b.seq = c.af_agent_seq " + 
										"left outer join af_company d on a.af_company_seq = d.seq " + 
										"left outer join af_department e on a.af_department_seq = e.seq " + 
										"left outer join af_team f on a.af_team_seq = f.seq ";
	
	final String GET_CHECK_RESULT = "select b.seq seq, agentId, agentUser, result, score, checkType, date_format(checkDate, '%Y-%m-%d %H:%m:%s') checkDate, d.name companyName, e.name departName, f.name teamName "+
									"from af_group_mapping a " + 
									"left outer join af_agent b on a.seq = b.af_group_mapping_seq " + 
									"inner join af_check_list_result c on b.seq = c.af_agent_seq " + 
									"left outer join af_company d on a.af_company_seq = d.seq " + 
									"left outer join af_department e on a.af_department_seq = e.seq " + 
									"left outer join af_team f on a.af_team_seq = f.seq order by checkDate desc limit #{num} offset #{offset}";
	
	final String GET_CHECK_SCORE = "select score from af_check_list_result where af_agent_seq = #{af_agent_seq}";
	
	@Select(GET_CHECK_SCORE)
	List<CheckResultVo> getCheckScore();
	
	@Select(GET_CHECK_RESULT_CNT)
	int getCheckResultCnt();
	
	@Select(GET_CHECK_RESULT)
	List<CheckResultVo> getCheckResult(@Param("num") int num, @Param("offset") int offset);
	
	@SelectProvider(type=one.auditfinder.server.mapper.check.CheckMapperProvider.class, method="searchCheckResultCnt")
	int searchCheckListCnt(Map<String, Object> map);
	
	@SelectProvider(type=one.auditfinder.server.mapper.check.CheckMapperProvider.class, method="searchCheckResult")
	List<CheckResultVo> searchCheckResult(Map<String, Object> map);
	
	
	final String GET_SCHEDULE = "select seq, title, checkDate, year, month, day, regUser, date_format(regDate, '%Y-%m-%d %H:%m:%s') regDate from af_schedule";

	@Select(GET_SCHEDULE)
	List<ScheduleVo> getSchedule();

	final String INSERT_SCHEDULE = "insert into af_schedule(title, checkDate, year, month, day, regUser, regDate) values(#{title}, #{checkDate}, #{year}, #{month}, #{day}, #{regUser}, now())";
	
	final String DELETE_SCHEDULE = "delete from af_schedule where seq = #{seq}";
	
	final String UPDATE_SCHEDULE = "update af_schedule set title = #{title}, checkDate = #{checkDate}, year = #{year}, month = #{month}, day = #{day}, regUser = #{regUser}, regDate = now() where seq = #{seq}";
	
	@Insert(INSERT_SCHEDULE)
	void insertSchedule(ScheduleVo vo);
	
	@Delete(DELETE_SCHEDULE)
	void deleteSchedule(@Param("seq") int seq);
	
	@Update(UPDATE_SCHEDULE)
	void updateSchedule(ScheduleVo vo);
}




