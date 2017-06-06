package com.cmsz.ircn.common;

public class Page {

	private int pageNum;
	
	private int pageSize;
	
	public Page(){
		this.pageNum = 1;
		this.pageSize = 50;
	}
	
	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	
}
