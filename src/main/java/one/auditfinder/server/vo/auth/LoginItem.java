package one.auditfinder.server.vo.auth;

public class LoginItem {
	
	private String id;
	private String pw;
	
	public LoginItem() {
		id = null;
		pw = null;
	}

	public final String getId() {
		return id;
	}

	public final void setId(String id) {
		this.id = id;
	}

	public final String getPw() {
		return pw;
	}

	public final void setPw(String pw) {
		this.pw = pw;
	}
	
}
