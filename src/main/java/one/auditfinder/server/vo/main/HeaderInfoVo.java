package one.auditfinder.server.vo.main;

public class HeaderInfoVo {
	
	private String id;
	private String name;
	private String accessTime;
	
	public HeaderInfoVo() {
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccessTime() {
		return accessTime;
	}

	public void setAccessTime(String accessTime) {
		this.accessTime = accessTime;
	}

}
