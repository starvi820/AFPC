package one.auditfinder.server.vo.check;

import java.util.Date;

public class CheckResultVo {
	
	private int seq;
	private String agentId;
	private String agentUser;
	private int result;
	private int score;
	private int checkType;
	private String checkDate;
	private String companyName;
	private String departName;
	private String teamName;
	private int type;
	private String sDate;
	private String eDate;
	
	public CheckResultVo() {
		
		this.seq = seq;
		this.agentId = agentId;
		this.agentUser = agentUser;
		this.result = result;
		this.score = score;
		this.checkType = checkType;
		this.checkDate = checkDate;
		this.companyName = companyName;
		this.departName = departName;
		this.teamName = teamName;
		this.type = type;
		this.sDate = sDate;
		this.eDate = eDate;
		
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public String getAgentUser() {
		return agentUser;
	}

	public void setAgentUser(String agentUser) {
		this.agentUser = agentUser;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getCheckType() {
		return checkType;
	}

	public void setCheckType(int checkType) {
		this.checkType = checkType;
	}

	public String getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getDepartName() {
		return departName;
	}

	public void setDepartName(String departName) {
		this.departName = departName;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getsDate() {
		return sDate;
	}

	public void setsDate(String sDate) {
		this.sDate = sDate;
	}

	public String geteDate() {
		return eDate;
	}

	public void seteDate(String eDate) {
		this.eDate = eDate;
	}

	
	
	
	
}
