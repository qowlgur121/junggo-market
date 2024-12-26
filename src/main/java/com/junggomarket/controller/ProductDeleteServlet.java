package com.junggomarket.controller;

import com.junggomarket.dao.ProductDAO;
import com.junggomarket.dto.ProductDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

// @WebServlet("/productDelete"): 상품 삭제 요청 처리 URL 매핑
@WebServlet("/productDelete")
public class ProductDeleteServlet extends HttpServlet {
	private ProductDAO productDAO = new ProductDAO(); // DAO 객체 생성

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false); // 세션 객체 가져오기
		String userRole = (String) session.getAttribute("userRole"); // 세션에서 사용자 역할 가져오기
		int productId = Integer.parseInt(request.getParameter("productId")); // 파라미터에서 상품 ID 가져오기
		try {
			ProductDTO product = productDAO.selectProductById(productId); // 데이터베이스에서 상품 정보 조회
			if (session != null && session.getAttribute("userId") != null) { // 로그인 유무 확인
				int userId = (int) session.getAttribute("userId"); // 세션에서 사용자 ID 가져오기
				// 상품 작성자이거나 관리자인 경우에만 삭제 가능하도록 수정
				if ("admin".equals(userRole) || product.getUser_id() == userId) {
					int result = productDAO.deleteProductById(productId); // DAO를 사용하여 상품 삭제
					if (result > 0) {
						response.sendRedirect("productDeleteSuccess.jsp"); // 삭제 성공 시 삭제 성공 페이지로 이동
					} else {
						response.sendRedirect("productDeleteFail.jsp"); // 삭제 실패 시 삭제 실패 페이지로 이동
					}
				} else {
					// 상품 작성자도 아니고 관리자도 아닌 경우
					response.sendRedirect("productList");
				}
			} else {
				response.sendRedirect("loginForm.jsp"); // 로그인 페이지로 이동
			}
		} catch (SQLException e) {
			e.printStackTrace(); // 오류 메시지 출력 (로그 기록)
			response.sendRedirect("productDeleteFail.jsp"); // 오류 발생 시 에러 페이지로 이동
		}
	}
}