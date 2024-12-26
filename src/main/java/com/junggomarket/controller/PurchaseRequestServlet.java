package com.junggomarket.controller;

import com.junggomarket.dao.ProductDAO;
import com.junggomarket.dto.PurchaseRequestDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

// @WebServlet("/purchaseRequest"): 구매 요청 처리 URL 매핑
@WebServlet("/purchaseRequest")
public class PurchaseRequestServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		int productId = Integer.parseInt(request.getParameter("productId")); // 폼 데이터 받기 (상품 ID)
		int priceOffer = Integer.parseInt(request.getParameter("priceOffer")); // 폼 데이터 받기 (제안 가격)
		String phone = request.getParameter("phone"); // 폼 데이터 받기 (연락처)

		HttpSession session = request.getSession(); // 세션 객체 가져오기
		int userId = (int) session.getAttribute("userId"); // 세션에서 사용자 ID 가져오기

		PurchaseRequestDTO purchaseRequest = new PurchaseRequestDTO(); // DTO 객체 생성
		purchaseRequest.setProduct_id(productId);
		purchaseRequest.setUser_id(userId);
		purchaseRequest.setPrice_offer(priceOffer);
		purchaseRequest.setPhone(phone);

		ProductDAO productDAO = new ProductDAO(); // DAO 객체 생성
		try {
			int result = productDAO.insertPurchaseRequest(purchaseRequest); // 데이터베이스에 구매 요청 정보 저장
			if (result > 0) {
				response.sendRedirect("purchaseRequestSuccess.jsp"); // 구매 요청 성공 시 purchaseRequestSuccess.jsp로 이동
			} else {
				response.sendRedirect("purchaseRequestFail.jsp"); // 구매 요청 실패 시 purchaseRequestFail.jsp로 이동
			}
		} catch (SQLException e) {
			e.printStackTrace(); // 오류 메시지 출력 (로그 기록)
			response.sendRedirect("purchaseRequestFail.jsp"); // 오류 발생 시 purchaseRequestFail.jsp로 이동
		}
	}
}