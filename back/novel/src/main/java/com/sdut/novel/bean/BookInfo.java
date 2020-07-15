package com.sdut.novel.bean;

public class BookInfo {

	private String account;
	private String password;
	private int id;
	private String bookName;
	private String writer;
	private String bookDescription;
	private String state;
	private String cover;
	private String type;
	private String renewalTime;
	private int heat;
	private Double score;
	private int amount;
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
	public String getBookDescription() {
		return bookDescription;
	}
	public void setBookDescription(String bookDescription) {
		this.bookDescription = bookDescription;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCover() {
		return cover;
	}
	public void setCover(String cover) {
		this.cover = cover;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getRenewalTime() {
		return renewalTime;
	}
	public void setRenewalTime(String renewalTime) {
		this.renewalTime = renewalTime;
	}
	public int getHeat() {
		return heat;
	}
	public void setHeat(int heat) {
		this.heat = heat;
	}
	public Double getScore() {
		return score;
	}
	public void setScore(Double score) {
		this.score = score;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getChapterTable() {
		return chapterTable;
	}
	public void setChapterTable(String chapterTable) {
		this.chapterTable = chapterTable;
	}
	@Override
	public String toString() {
		return "BookInfo [account=" + account + ", password=" + password + ", id=" + id + ", bookName=" + bookName
				+ ", writer=" + writer + ", bookDescription=" + bookDescription + ", state=" + state + ", cover="
				+ cover + ", type=" + type + ", renewalTime=" + renewalTime + ", heat=" + heat + ", score=" + score
				+ ", amount=" + amount + ", chapterTable=" + chapterTable + "]";
	}
}
