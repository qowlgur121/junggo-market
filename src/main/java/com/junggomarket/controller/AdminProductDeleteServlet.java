package com.junggomarket.controller;

import com.junggomarket.dao.ProductDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

// @WebServlet("/adminProductDelete"): 관리자 상품 삭제 요청 처리 URL 매핑
@WebServlet("/adminProductDelete")
public class AdminProductDeleteServlet extends HttpServlet {
	private ProductDAO productDAO = new ProductDAO(); // DAO 객체 생성

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		String userRole = (String) session.getAttribute("userRole");
		int productId = Integer.parseInt(request.getParameter("productId"));
		if ("admin".equals(userRole)) {
			try {
				int result = productDAO.deleteProductById(productId);
				if (result > 0) {
					response.sendRedirect("adminReportList");
				} else {
					response.sendRedirect("productDeleteFail.jsp");
				}
			} catch (SQLException e) {
				e.printStackTrace();
				response.sendRedirect("productDeleteFail.jsp");
			}
		} else {
			// 관리자 권한이 없을 경우
			response.sendRedirect("accessDenied.jsp");
		}
	}
}