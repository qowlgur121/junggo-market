package com.junggomarket.dto;

public class ReportDTO {
	private int reportId;
	private int productId;
	private int reporterId;
	private String reason;
	private String created_at;
	private String username;
	private String productTitle; // 추가

	public int getReportId() {
		return reportId;
	}

	public void setReportId(int reportId) {
		this.reportId = reportId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getReporterId() {
		return reporterId;
	}

	public void setReporterId(int reporterId) {
		this.reporterId = reporterId;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getProductTitle() { // 추가
		return productTitle;
	}

	public void setProductTitle(String productTitle) { // 추가
		this.productTitle = productTitle;
	}
}