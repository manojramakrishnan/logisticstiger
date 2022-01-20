package com.tigerlogistics.model;

import java.io.Serializable;

public class RolePermissionDO implements Serializable{

	private String name;
	private String url;
	private String role;
	
	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public String getRole() {
		return role;
	}


	public void setRole(String role) {
		this.role = role;
	}


	@Override
	public String toString() {
		return "RolePermissionDO [name=" + name + ", url=" + url + ", role=" + role + "]";
	}
	
}
