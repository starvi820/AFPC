package one.auditfinder.server.vo.list;

public class ListVo {
	
	private int seq;
	private String listName;
	private int listScore;
	private int listLevel;
	private String listContent;
	private String listSolution;
	private int listUseYn;
	
	public ListVo() {
		
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getListName() {
		return listName;
	}

	public void setListName(String listName) {
		this.listName = listName;
	}

	public int getListScore() {
		return listScore;
	}

	public void setListScore(int listScore) {
		this.listScore = listScore;
	}

	public int getListLevel() {
		return listLevel;
	}

	public void setListLevel(int listLevel) {
		this.listLevel = listLevel;
	}

	public String getListContent() {
		return listContent;
	}

	public void setListContent(String listContent) {
		this.listContent = listContent;
	}

	public String getListSolution() {
		return listSolution;
	}

	public void setListSolution(String listSolution) {
		this.listSolution = listSolution;
	}

	public int getListUseYn() {
		return listUseYn;
	}

	public void setListUseYn(int listUseYn) {
		this.listUseYn = listUseYn;
	}
	
}
