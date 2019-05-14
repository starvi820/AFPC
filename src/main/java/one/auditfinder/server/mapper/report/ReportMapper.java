package one.auditfinder.server.mapper.report;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import one.auditfinder.server.vo.report.ReportVo;

public interface ReportMapper {
	
	final String GET_REPORT ="select seq,reportId,reportName,reportDir,reportDate,regAgent from af_report";
	
	@Select(GET_REPORT)
	List<ReportVo> getReport();
	
	
	final String INSERT_REPORT = "insert into af_report(seq,reportId,reportName,reportDir,regDate,regAgent) values(#{seq},#{reportId},#{reportName},#{reportDir},#{regDate},#{regAgent})";
	
	@Insert(INSERT_REPORT)
	void insertReport(ReportVo vo);
	
	final String DELETE_REPORT = "delete from af_report where seq = #{seq}";
	
	@Delete(DELETE_REPORT)
	void deleteReport();
	
	final String UPDATE_REPORT = "update af_report set reportId=#{reportId},reportName=#{reportName},reportDir=#{reportDir},regAgent=#{regAgent}";
	
	@Update(UPDATE_REPORT)
	void updateReport(ReportVo vo);
	
	
	

}
