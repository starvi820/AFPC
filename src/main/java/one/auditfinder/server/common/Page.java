package one.auditfinder.server.common;

import java.util.List;


public class Page<T> {
	
	public static final int NUM_PER_PAGE = 5;
	public static final int NUM_PAGELIST = 10;
	
	private int totalPage;
	private int totalArticle;
	private int perArticle;
	private int currentPage;
	private int startPage;
	private int endPage;
	private int offset;
	private int startArticleNo;
	private int exseq;	
	private String txt;
	
	private List<T> result;
		
	public Page() {
		exseq = 0;
		totalPage = 1;
		totalArticle = 0;
		perArticle = NUM_PER_PAGE;
		currentPage = 1;
		offset = 0;
		startPage = 1;
		endPage = 1;
		startArticleNo = 1;
		result = null;
		txt = null;
	}
	
	public Page(int seq, int total, int cpage, int particle) {
		exseq = seq;
		totalArticle = total;
		currentPage = cpage;
		perArticle = particle;
		result = null;
		set();
	}
	
	public Page(int total, int cpage, int particle) {
		totalArticle = total;
		currentPage = cpage;
		perArticle = particle;
		result = null;
		set();
	}
	
	private void set() {
		if(currentPage == 0) currentPage = 1;
		if( (totalArticle % perArticle) == 0 && totalArticle > 0)
			totalPage = (int)(totalArticle / perArticle);
		else 
			totalPage = (int)(totalArticle / perArticle) + 1;
		if( totalPage < currentPage) currentPage = totalPage;
		offset = ( perArticle * (currentPage - 1));
		startArticleNo = totalArticle - offset;
		startPage = (int)((currentPage-1) / NUM_PAGELIST) * NUM_PAGELIST + 1;
		endPage = startPage + NUM_PAGELIST - 1;
		if( endPage > totalPage) endPage = totalPage;
	}

	public int getTotalArticle() {
		return totalArticle;
	}

	public void setPage(int totalArticle , int currentPage, int particle) {
		this.totalArticle = totalArticle;
		this.currentPage = currentPage;
		this.perArticle = particle;
		set();
	}

	public int getPerArticle() {
		return perArticle;
	}

	public void setPerArticle(int perArticle) {
		if( perArticle == 0) return;
		this.perArticle = perArticle;
		set();
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public List<T> getResult() {
		return result;
	}

	public void setResult(List<T> result) {
		this.result = result;
	}
	
	public int getOffset() {
		return offset;
	}

	public int getStartPage() {
		return startPage;
	}

	public int getEndPage() {
		return endPage;
	}
	
	public int[] getPagesArray() { 
	    int[] ar_page = new int[endPage - startPage + 1];
	    for(int i = 0; (i + startPage) <= endPage ; i++ )
	        ar_page[i] = i + startPage;
	    return ar_page;  
	}

	public int getStartArticleNo() {
		return startArticleNo;
	}

	public final int getExseq() {
		return exseq;
	}

	public final void setExseq(int exseq) {
		this.exseq = exseq;
	}

	public String getTxt() {
		return txt;
	}

	public void setTxt(String txt) {
		this.txt = txt;
	}
	
}

