package one.auditfinder.sever.vo.version;

public class VersionVo {
	
	private String version_name;
	private String version_date;
	private String version;
	private String version_size;
	private String version_app;
	private String version_browser;
	private String version_platform;
	
	
	public VersionVo() {

	this.version_name = version_name ;
	this.version_date = version_date ;
	this.version = version ;
	this.version_size = version_size ;
	this.version_app = version_app ;
	this.version_browser = version_browser ;
	this.version_platform = version_platform;
	
	
	}


	public String getVersion_name() {
		return version_name;
	}


	public void setVersion_name(String version_name) {
		this.version_name = version_name;
	}


	public String getVersion_date() {
		return version_date;
	}


	public void setVersion_date(String version_date) {
		this.version_date = version_date;
	}


	public String getVersion() {
		return version;
	}


	public void setVersion(String version) {
		this.version = version;
	}


	public String getVersion_size() {
		return version_size;
	}


	public void setVersion_size(String version_size) {
		this.version_size = version_size;
	}


	public String getVersion_app() {
		return version_app;
	}


	public void setVersion_app(String version_app) {
		this.version_app = version_app;
	}


	public String getVersion_browser() {
		return version_browser;
	}


	public void setVersion_browser(String version_browser) {
		this.version_browser = version_browser;
	}


	public String getVersion_platform() {
		return version_platform;
	}


	public void setVersion_platform(String version_platform) {
		this.version_platform = version_platform;
	}


	

}
