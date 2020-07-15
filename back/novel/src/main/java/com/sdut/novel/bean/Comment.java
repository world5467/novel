package com.sdut.novel.bean;

import java.util.Date;

public class Comment {

	private int id;
	private int userId;
	private int bookId;
	private String commentContent;
	private double score;
	private Date time;
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
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	@Override
	public String toString() {
		return "Comment [id=" + id + ", userId=" + userId + ", bookId=" + bookId + ", commentContent=" + commentContent
				+ ", score=" + score + ", time=" + time + "]";
	}
	
}
