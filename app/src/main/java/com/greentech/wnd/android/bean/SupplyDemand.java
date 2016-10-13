package com.greentech.wnd.android.bean;

import java.io.Serializable;
import java.util.Date;

/*
 * 供求信息
 */
public class SupplyDemand implements Serializable{
	private Integer id;
	private String name;
	private String type;
	private String category;
	private String province;
	private String content;
	private Date releaseTime;
	private Date validTime;
	private String contacter;
	private String telephone;
	private String mobile;
	private String qqMsn;
	private String address;
	private Integer productId;
	private Integer userId;
//	private SDProduct product;
//	private NormalUser normalUser;
	
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}
	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}
	/**
	 * @return the province
	 */
	public String getProvince() {
		return province;
	}
	/**
	 * @param province the province to set
	 */
	public void setProvince(String province) {
		this.province = province;
	}
	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}
	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * @return the releaseTime
	 */
	public Date getReleaseTime() {
		return releaseTime;
	}
	/**
	 * @param releaseTime the releaseTime to set
	 */
	public void setReleaseTime(Date releaseTime) {
		this.releaseTime = releaseTime;
	}
	/**
	 * @return the validTime
	 */
	public Date getValidTime() {
		return validTime;
	}
	/**
	 * @param validTime the validTime to set
	 */
	public void setValidTime(Date validTime) {
		this.validTime = validTime;
	}
	/**
	 * @return the contacter
	 */
	public String getContacter() {
		return contacter;
	}
	/**
	 * @param contacter the contacter to set
	 */
	public void setContacter(String contacter) {
		this.contacter = contacter;
	}
	/**
	 * @return the telephone
	 */
	public String getTelephone() {
		return telephone;
	}
	/**
	 * @param telephone the telephone to set
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}
	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**
	 * @return the qqMsn
	 */
	public String getQqMsn() {
		return qqMsn;
	}
	/**
	 * @param qqMsn the qqMsn to set
	 */
	public void setQqMsn(String qqMsn) {
		this.qqMsn = qqMsn;
	}
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return the productId
	 */
	public Integer getProductId() {
		return productId;
	}
	/**
	 * @param productId the productId to set
	 */
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	/**
	 * @return the userId
	 */
	public Integer getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	/**
	 * @return the product
//	 */
//	public SDProduct getProduct() {
//		return product;
//	}
//	/**
//	 * @param product the product to set
//	 */
//	public void setProduct(SDProduct product) {
//		this.product = product;
//	}
//	/**
//	 * @return the normalUser
//	 */
//	public NormalUser getNormalUser() {
//		return normalUser;
//	}
//	/**
//	 * @param normalUser the normalUser to set
//	 */
//	public void setNormalUser(NormalUser normalUser) {
//		this.normalUser = normalUser;
//	}
}
