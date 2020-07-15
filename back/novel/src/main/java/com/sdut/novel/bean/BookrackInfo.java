package com.sdut.novel.bean;

public class BookrackInfo {

	private String account;
	private String password;
	private int bookId;
	private int userId;
	private int chapterId;
	private String chapterTable;
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
		return "BookrackInfo [account=" + account + ", password=" + password + ", bookId=" + bookId + ", userId="
				+ userId + ", chapterId=" + chapterId + ", chapterTable=" + chapterTable + "]";
	}
}
