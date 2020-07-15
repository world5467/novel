package com.sdut.novel.bean;

public class CommentInfoResult {

	private String account;
	private String password;
	private int id;
	private int userId;
	private int bookId;
	private String commentContent;
	private double score;
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
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getBookId() {
		return bookId;
	}
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
	public String getCommentContent() {
		return commentContent;
	}
	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	@Override
	public String toString() {
		return "CommentInfoResult [account=" + account + ", password=" + password + ", id=" + id + ", userId=" + userId
				+ ", bookId=" + bookId + ", commentContent=" + commentContent + ", score=" + score + "]";
	}
}
