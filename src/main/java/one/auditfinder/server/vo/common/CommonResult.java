package one.auditfinder.server.vo.common;

public class CommonResult {
	
	private int code;
	private String message;
	private String exinfo;
	private Object data;
	
	public CommonResult() {
		code = 0;
		message = null;
		exinfo = null;
		data = null;
	}

	public final int getCode() {
		return code;
	}

	public final void setCode(int code) {
		this.code = code;
	}

	public final String getMessage() {
		return message;
	}

	public final void setMessage(String message) {
		this.message = message;
	}

	public final String getExinfo() {
		return exinfo;
	}

	public final void setExinfo(String exinfo) {
		this.exinfo = exinfo;
	}

	public final Object getData() {
		return data;
	}

	public final void setData(Object data) {
		this.data = data;
	}
	
}
