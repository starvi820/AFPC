package one.auditfinder.server.vo.files;

public class FileItem {
	
	private int seq;
	private String fname;
	private String forginame;
	private String dirname;
	private long fsize;
	private String mime;
	private int kind;
	private int regi;
	private long tm;
	
	public FileItem() {
		seq = 0;
		fname = null;
		forginame = null;
		fsize = 0;
		mime = null;
		kind = 0;
		regi = 0;
		tm = 0L;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getForginame() {
		return forginame;
	}

	public void setForginame(String forginame) {
		this.forginame = forginame;
	}

	public String getDirname() {
		return dirname;
	}

	public void setDirname(String dirname) {
		this.dirname = dirname;
	}

	public long getFsize() {
		return fsize;
	}

	public void setFsize(long fsize) {
		this.fsize = fsize;
	}

	public String getMime() {
		return mime;
	}

	public void setMime(String mime) {
		this.mime = mime;
	}

	public int getKind() {
		return kind;
	}

	public void setKind(int kind) {
		this.kind = kind;
	}

	public int getRegi() {
		return regi;
	}

	public void setRegi(int regi) {
		this.regi = regi;
	}

	public long getTm() {
		return tm;
	}

	public void setTm(long tm) {
		this.tm = tm;
	}

}
