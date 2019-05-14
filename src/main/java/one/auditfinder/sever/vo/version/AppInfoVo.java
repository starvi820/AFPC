package one.auditfinder.sever.vo.version;


public class AppInfoVo {
	
	private int seq;
	private int appType;
	private String recentVersion;
	private String updateDate;
	private String platformName;
	private String appDescription;
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public int getAppType() {
		return appType;
	}
	public void setAppType(int appType) {
		this.appType = appType;
	}
	public String getRecentVersion() {
		return recentVersion;
	}
	public void setRecentVersion(String recentVersion) {
		this.recentVersion = recentVersion;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	public String getPlatformName() {
		return platformName;
	}
	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}
	public String getAppDescription() {
		return appDescription;
	}
	public void setAppDescription(String appDescription) {
		this.appDescription = appDescription;
	}

	
	
	
	
}
