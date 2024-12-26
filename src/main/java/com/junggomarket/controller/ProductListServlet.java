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

// @WebServlet("/productList"): 상품 목록 요청 처리 URL 매핑
@WebServlet("/productList")
public class ProductListServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ProductDAO productDAO = new ProductDAO(); // DAO 객체 생성
		try {
			int page = 1; // 기본 페이지 번호
			int pageSize = 10; // 한 페이지에 표시할 상품 수
			// 페이지 번호가 파라미터로 넘어오면 해당 값으로 설정
			if (request.getParameter("page") != null) {
				page = Integer.parseInt(request.getParameter("page"));
			}
			List<ProductDTO> productList = productDAO.selectProductList(page, pageSize); // 데이터베이스에서 상품 목록 조회 (페이징 적용)
			int productCount = productDAO.getProductCount(); // 데이터베이스에서 상품 총 개수 조회
			int totalPages = (int) Math.ceil((double) productCount / pageSize); // 총 페이지 수 계산
			request.setAttribute("productList", productList); // 상품 목록을 request 객체에 저장
			request.setAttribute("currentPage", page); // 현재 페이지를 request 객체에 저장
			request.setAttribute("totalPages", totalPages); // 총 페이지 수를 request 객체에 저장
			request.getRequestDispatcher("productList.jsp").forward(request, response); // productList.jsp로 이동 (데이터와 함께)
		} catch (SQLException e) {
			e.printStackTrace(); // 오류 메시지 출력 (로그 기록)
			response.sendRedirect("index.jsp"); // 오류 발생 시 메인 페이지로 이동
		}
	}
}