package com.sdut.novel.bean;

import java.util.Date;

public class UserInfoResult {

	private String account;
	private String password;
	private int id;
	private String username;
	private String email;
	private String sex;
	private Date birthday;
	private String headPortrait;
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
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getHeadPortrait() {
		return headPortrait;
	}
	public void setHeadPortrait(String headPortrait) {
		this.headPortrait = headPortrait;
	}
	@Override
	public String toString() {
		return "UserInfoResult [account=" + account + ", password=" + password + ", id=" + id + ", username=" + username
				+ ", email=" + email + ", sex=" + sex + ", birthday=" + birthday + ", headPortrait=" + headPortrait
				+ "]";
	}
}
