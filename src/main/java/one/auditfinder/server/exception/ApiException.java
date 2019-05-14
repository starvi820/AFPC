package one.auditfinder.server.exception;

public class ApiException extends RuntimeException {
	
	private static final long serialVersionUID = 9147815767321775718L;
	
	private final String url;
	private final int code;
	
	public ApiException() {
		super();
		url = null;
		code = 0;
	}
	
	public ApiException( String msg) {
		super(msg);
		url = null;
		code = 0;
	}
	
	public ApiException(String msg, int code) {
		super(msg);
		this.url = null;
		this.code = code;
	}
	
	public ApiException(String msg, int code , String url) {
		super(msg);
		this.url = url;
		this.code = code;
	}
	
	public ApiException(String msg, Throwable cause) {
		super(msg, cause);
		url = null;
		code = 0;
	}
	
	public ApiException(String msg, int code , String url, Throwable cause) {
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
