package com.junggomarket.dto;

public class PurchaseRequestDTO {
	private int request_id;
	private int product_id;
	private int user_id;
	private int price_offer;
	private String phone;
	private String created_at;
	private String username; // 추가
	private String productTitle; // 추가

	public int getRequest_id() {
		return request_id;
	}

	public void setRequest_id(int request_id) {
		this.request_id = request_id;
	}

	public int getProduct_id() {
		return product_id;
	}

	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getPrice_offer() {
		return price_offer;
	}

	public void setPrice_offer(int price_offer) {
		this.price_offer = price_offer;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public String getUsername() { // 추가
		return username;
	}

	public void setUsername(String username) { // 추가
		this.username = username;
	}

	public String getProductTitle() {
		return productTitle;
	}

	public void setProductTitle(String productTitle) {
		this.productTitle = productTitle;
	}
}