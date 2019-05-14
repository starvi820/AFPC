package one.auditfinder.server.vo.info;

public class InfoVo {
	
	private int seq;
	private String af_admin_id;
	private String title;
	private String regDate;
	private String content;
	
	public InfoVo() {
		
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}
