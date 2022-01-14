package com.tigerlogistics.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserInfoDTO implements Serializable {

	private Integer userID;
	private String userName;
	private String password;
	private boolean firstLogin;
	private String accessIP;
	private List<String> role = new ArrayList<>();
	private Integer repositoryBelong;
	public Integer getUserID() {
		return userID;
	}
	public void setUserID(Integer userID) {
		this.userID = userID;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isFirstLogin() {
		return firstLogin;
	}
	public void setFirstLogin(boolean firstLogin) {
		this.firstLogin = firstLogin;
	}
	public String getAccessIP() {
		return accessIP;
	}
	public void setAccessIP(String accessIP) {
		this.accessIP = accessIP;
	}
	public List<String> getRole() {
		return role;
	}
	public void setRole(List<String> role) {
		this.role = role;
	}
	public Integer getRepositoryBelong() {
		return repositoryBelong;
	}
	public void setRepositoryBelong(Integer repositoryBelong) {
		this.repositoryBelong = repositoryBelong;
	}
	@Override
	public String toString() {
		return "UserInfoDTO [userID=" + userID + ", userName=" + userName + ", password=" + password + ", firstLogin="
				+ firstLogin + ", accessIP=" + accessIP + ", role=" + role + ", repositoryBelong=" + repositoryBelong
				+ "]";
	}

}
