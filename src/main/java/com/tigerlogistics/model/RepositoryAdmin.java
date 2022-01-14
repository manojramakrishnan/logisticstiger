package com.tigerlogistics.model;

import java.io.Serializable;
import java.sql.Date;

public class RepositoryAdmin implements Serializable {
	
	private Integer id;
	private String name;
	private String sex;
	private String tel;
	private String address;
	private Date birth;
	private Integer repositoryBelongID;
	
	
	@Override
	public String toString() {
		return "RepositoryAdmin [id=" + id + ", name=" + name + ", sex=" + sex + ", tel=" + tel + ", address=" + address
				+ ", birth=" + birth + ", repositoryBelongID=" + repositoryBelongID + "]";
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Date getBirth() {
		return birth;
	}
	public void setBirth(Date birth) {
		this.birth = birth;
	}
	public Integer getRepositoryBelongID() {
		return repositoryBelongID;
	}
	public void setRepositoryBelongID(Integer repositoryBelongID) {
		this.repositoryBelongID = repositoryBelongID;
	}

}
