package com.sdut.novel.bean;

public class Chapter {

	private int id;
	private String chapterName;
	private String chapterContent;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getChapterName() {
		return chapterName;
	}
	public void setChapterName(String chapterName) {
		this.chapterName = chapterName;
	}
	public String getChapterContent() {
		return chapterContent;
	}
	public void setChapterContent(String chapterContent) {
		this.chapterContent = chapterContent;
	}
	@Override
	public String toString() {
		return "Chapter [id=" + id + ", chapterName=" + chapterName + ", chapterContent=" + chapterContent + "]";
	}
	
}
