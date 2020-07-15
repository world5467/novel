package com.sdut.novel.bean;

public class BookrackShow {

	private int id;
	private String bookName;
	private String writer;
	private String type;
	private int chapterId;
	private String chapterName;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getChapterId() {
		return chapterId;
	}
	public void setChapterId(int chapterId) {
		this.chapterId = chapterId;
	}
	public String getChapterName() {
		return chapterName;
	}
	public void setChapterName(String chapterName) {
		this.chapterName = chapterName;
	}
	@Override
	public String toString() {
		return "BookrackShow [id=" + id + ", bookName=" + bookName + ", writer=" + writer + ", type=" + type
				+ ", chapterId=" + chapterId + ", chapterName=" + chapterName + "]";
	}
}
