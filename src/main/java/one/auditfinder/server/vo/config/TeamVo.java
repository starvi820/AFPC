package one.auditfinder.server.vo.config;

public class TeamVo {

	private int seq;
	private String name;
	private int af_company_seq;
	private int af_department_seq;
	
	public TeamVo() {
		
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAf_company_seq() {
		return af_company_seq;
	}

	public void setAf_company_seq(int af_company_seq) {
		this.af_company_seq = af_company_seq;
	}

	public int getAf_department_seq() {
		return af_department_seq;
	}

	public void setAf_department_seq(int af_department_seq) {
		this.af_department_seq = af_department_seq;
	}

	
}
