package one.auditfinder.server.vo.report;

public class ReportVo {
	
	private int seq;
	private int reportId;
	private String reportName;
	private String reportDir;
	private String regDate;
	private String regAgent;
	
	public ReportVo() {
	}
	
	
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public int getReportId() {
		return reportId;
	}
	public void setReportId(int reportId) {
		this.reportId = reportId;
	}
	public String getReportName() {
		return reportName;
	}
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	public String getReportDir() {
		return reportDir;
	}
	public void setReportDir(String reportDir) {
		this.reportDir = reportDir;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getRegAgent() {
		return regAgent;
	}
	public void setRegAgent(String regAgent) {
		this.regAgent = regAgent;
	}
	
	
	
	

}
