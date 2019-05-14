package one.auditfinder.server.vo.agent;

public class AgentVo {
	
	private int seq;
	private int af_group_mapping_seq;
	private String agentId;
	private String agentRegTime;
	private String agentIp;
	private int agentStatus;
	private String agentEmail;
	private String agentUser;
	private String agentAccountCode;
	private int agentUseYn;
	
	private int companySeq;
	private String companyName;
	private int departSeq;
	private String departName;
	private int teamSeq;
	private String teamName;
	
	public AgentVo() {
		
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public int getAf_group_mapping_seq() {
		return af_group_mapping_seq;
	}

	public void setAf_group_mapping_seq(int af_group_mapping_seq) {
		this.af_group_mapping_seq = af_group_mapping_seq;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public String getAgentRegTime() {
		return agentRegTime;
	}

	public void setAgentRegTime(String agentRegTime) {
		this.agentRegTime = agentRegTime;
	}

	public String getAgentIp() {
		return agentIp;
	}

	public void setAgentIp(String agentIp) {
		this.agentIp = agentIp;
	}

	public int getAgentStatus() {
		return agentStatus;
	}

	public void setAgentStatus(int agentStatus) {
		this.agentStatus = agentStatus;
	}

	public String getAgentEmail() {
		return agentEmail;
	}

	public void setAgentEmail(String agentEmail) {
		this.agentEmail = agentEmail;
	}

	public String getAgentUser() {
		return agentUser;
	}

	public void setAgentUser(String agentUser) {
		this.agentUser = agentUser;
	}

	public String getAgentAccountCode() {
		return agentAccountCode;
	}

	public void setAgentAccountCode(String agentAccountCode) {
		this.agentAccountCode = agentAccountCode;
	}

	public int getAgentUseYn() {
		return agentUseYn;
	}

	public void setAgentUseYn(int agentUseYn) {
		this.agentUseYn = agentUseYn;
	}

	public int getCompanySeq() {
		return companySeq;
	}

	public void setCompanySeq(int companySeq) {
		this.companySeq = companySeq;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public int getDepartSeq() {
		return departSeq;
	}

	public void setDepartSeq(int departSeq) {
		this.departSeq = departSeq;
	}

	public String getDepartName() {
		return departName;
	}

	public void setDepartName(String departName) {
		this.departName = departName;
	}

	public int getTeamSeq() {
		return teamSeq;
	}

	public void setTeamSeq(int teamSeq) {
		this.teamSeq = teamSeq;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	
}
