package com.junggomarket.controller;

import com.junggomarket.dao.ProductDAO;
import com.junggomarket.dto.ProductDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

// @WebServlet("/productDetail"): 상품 상세 정보 요청 처리 URL 매핑
@WebServlet("/productDetail")
public class ProductDetailServlet extends HttpServlet {
	private ProductDAO productDAO = new ProductDAO();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int productId = Integer.parseInt(request.getParameter("productId")); // 상품 ID를 파라미터에서 가져오기

		try {
			productDAO.increaseViewCount(productId); // 조회수 증가
			ProductDTO product = productDAO.selectProductById(productId); // 상품 정보를 가져오는 메서드
			request.setAttribute("product", product); // 상품 정보를 request 객체에 저장
			request.getRequestDispatcher("productDetail.jsp").forward(request, response); // 상품 상세 정보 페이지로 이동
		} catch (SQLException e) {
			e.printStackTrace(); // 오류 메시지 출력 (로그 기록)
			response.sendRedirect("productList"); // 오류 발생 시 상품 목록 페이지로 이동
		}
	}
}