package com.sdut.novel.bean;

public class Heat {

	private int id;
	private String ip;
	private int bookId;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getBookId() {
		return bookId;
	}
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
	@Override
	public String toString() {
		return "Heat [id=" + id + ", ip=" + ip + ", bookId=" + bookId + "]";
	}
}
