package com.sdut.novel.bean;

public class Bookrack {

	private int id;
	private int bookId;
	private int userId;
	private int chapterId;
	private String chapterTable;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getBookId() {
		return bookId;
	}
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getChapterId() {
		return chapterId;
	}
	public void setChapterId(int chapterId) {
		this.chapterId = chapterId;
	}
	public String getChapterTable() {
		return chapterTable;
	}
	public void setChapterTable(String chapterTable) {
		this.chapterTable = chapterTable;
	}
	@Override
	public String toString() {
		return "Bookrack [id=" + id + ", bookId=" + bookId + ", userId=" + userId + ", chapterId=" + chapterId
				+ ", chapterTable=" + chapterTable + "]";
	}
	
}
