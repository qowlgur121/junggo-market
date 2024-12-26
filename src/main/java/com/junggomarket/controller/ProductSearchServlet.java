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
import java.util.List;

// @WebServlet("/productSearch"): 상품 검색 요청 처리 URL 매핑
@WebServlet("/productSearch")
public class ProductSearchServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String keyword = request.getParameter("keyword"); // 폼 데이터 받기 (검색어)
		String address = request.getParameter("address"); // 폼 데이터 받기 (주소)
		ProductDAO productDAO = new ProductDAO(); // DAO 객체 생성

		try {
			List<ProductDTO> productList = productDAO.selectProductByKeyword(keyword, address); // 검색어로 상품 목록 조회
			request.setAttribute("productList", productList); // 상품 목록을 request 객체에 저장
			request.getRequestDispatcher("productSearchResult.jsp").forward(request, response); // 검색 결과 페이지로 이동
		} catch (SQLException e) {
			e.printStackTrace(); // 오류 메시지 출력 (로그 기록)
			response.sendRedirect("index.jsp"); // 오류 발생 시 메인 페이지로 이동
		}
	}
}
