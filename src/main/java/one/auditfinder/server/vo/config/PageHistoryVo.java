package one.auditfinder.server.vo.config;

public class PageHistoryVo {
	
	private int seq;
	private String af_admin_id;
	private String accessIp;
	private String accessPage;
	private String accessMenu;
	private String accessTable;
	private String accessTime;
	private String accessId;
	private String action;
	
	public PageHistoryVo() {
		
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

	public String getAccessPage() {
		return accessPage;
	}

	public void setAccessPage(String accessPage) {
		this.accessPage = accessPage;
	}

	public String getAccessMenu() {
		return accessMenu;
	}

	public void setAccessMenu(String accessMenu) {
		this.accessMenu = accessMenu;
	}

	public String getAccessTable() {
		return accessTable;
	}

	public void setAccessTable(String accessTable) {
		this.accessTable = accessTable;
	}

	public String getAccessTime() {
		return accessTime;
	}

	public void setAccessTime(String accessTime) {
		this.accessTime = accessTime;
	}

	public String getAccessId() {
		return accessId;
	}

	public void setAccessId(String accessId) {
		this.accessId = accessId;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
	
}
