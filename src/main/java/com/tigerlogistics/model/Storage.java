package com.tigerlogistics.model;

public class Storage {

	private Integer goodsID;
	private String goodsName;
	private String goodsSize;
	private String goodsType;
	private Double goodsValue;
	private Integer repositoryId;
	private Long number;

	public Integer getGoodsID() {
		return goodsID;
	}

	public void setGoodsID(Integer goodsID) {
		this.goodsID = goodsID;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getGoodsSize() {
		return goodsSize;
	}

	public void setGoodsSize(String goodsSize) {
		this.goodsSize = goodsSize;
	}

	public String getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}

	public Double getGoodsValue() {
		return goodsValue;
	}

	public void setGoodsValue(Double goodsValue) {
		this.goodsValue = goodsValue;
	}

	public Integer getRepositoryId() {
		return repositoryId;
	}

	public void setRepositoryID(Integer repositoryId) {
		this.repositoryId = repositoryId;
	}

	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

	@Override
	public String toString() {
		return "Storage [goodsID=" + goodsID + ", goodsName=" + goodsName + ", goodsSize=" + goodsSize + ", goodsType="
				+ goodsType + ", goodsValue=" + goodsValue + ", repositoryId=" + repositoryId + ", number=" + number
				+ "]";
	}

}
