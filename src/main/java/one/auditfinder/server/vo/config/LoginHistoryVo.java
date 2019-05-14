package one.auditfinder.server.vo.config;

public class LoginHistoryVo {

	private int seq;
	private String af_admin_id;
	private String accessIp;
	private String accessTime;
	private int loginYn;
	
	public LoginHistoryVo() {
		
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getAf_admin_id() {
		return af_admin_id;
	}

	public void setAf_admin_id(String af_admin_id) {
		this.af_admin_id = af_admin_id;
	}

	public String getAccessIp() {
		return accessIp;
	}

	public void setAccessIp(String accessIp) {
		this.accessIp = accessIp;
	}

	public String getAccessTime() {
		return accessTime;
	}

	public void setAccessTime(String accessTime) {
		this.accessTime = accessTime;
	}

	public int getLoginYn() {
		return loginYn;
	}

	public void setLoginYn(int loginYn) {
		this.loginYn = loginYn;
	}
	
}
