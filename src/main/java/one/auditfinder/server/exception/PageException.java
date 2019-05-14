package one.auditfinder.server.exception;

public class PageException extends RuntimeException {
	private static final long serialVersionUID = 9147815767321775715L;
	
	private final String url;
	private final int code;
	
	public PageException() {
		super();
		url = null;
		code = 0;
	}
	
	public PageException( String msg) {
		super(msg);
		url = null;
		code = 0;
	}
	
	public PageException(String msg, int code , String url) {
		super(msg);
		this.url = url;
		this.code = code;
	}
	
	public PageException(String msg, Throwable cause) {
		super(msg, cause);
		url = null;
		code = 0;
	}
	
	public PageException(String msg, int code , String url, Throwable cause) {
		super(msg, cause);
		this.url = url;
		this.code = code;
	}

	public final String getUrl() {
		return url;
	}

	public final int getCode() {
		return code;
	}
}
