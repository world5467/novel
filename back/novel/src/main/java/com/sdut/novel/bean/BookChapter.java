package com.sdut.novel.bean;

public class BookChapter {

	private int id;
	private String chapterName;
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
	@Override
	public String toString() {
		return "Chapter [id=" + id + ", chapterName=" + chapterName +"]";
	}
	
}
