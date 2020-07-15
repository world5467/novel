package com.sdut.novel.bean;

import java.util.Date;

public class CommentShow {

	private int id;
	private int userId;
	private int bookId;
	private String commentContent;
	private double score;
	private Date time;
	private String username;
	private String headPortrait;
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
	public String getUsername() {
		return username;
	}
	public void setUsername(String userName) {
		this.username = userName;
	}
	public String getHeadPortrait() {
		return headPortrait;
	}
	public void setHeadPortrait(String headPortrait) {
		this.headPortrait = headPortrait;
	}
	@Override
	public String toString() {
		return "CommentShow [id=" + id + ", userId=" + userId + ", bookId=" + bookId + ", commentContent="
				+ commentContent + ", score=" + score + ", time=" + time + ", username=" + username + ", headPortrait="
				+ headPortrait + "]";
	}
}
