package com.sdut.novel.bean;

public class ManageParameter {

	private String account;
	private String password;
	private int id;
	private String content;
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Override
	public String toString() {
		return "ManageParameter [account=" + account + ", password=" + password + ", id=" + id + ", content=" + content
				+ "]";
	}
	
}
