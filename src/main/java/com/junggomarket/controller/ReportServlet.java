package com.junggomarket.controller;

import com.junggomarket.dao.ProductDAO;
import com.junggomarket.dto.ReportDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

// @WebServlet("/report"): 상품 신고 요청 처리 URL 매핑
@WebServlet("/report")
public class ReportServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8"); // 요청 데이터 인코딩 설정

		int productId = Integer.parseInt(request.getParameter("productId")); // 폼 데이터 받기 (상품 ID)
		String reason = request.getParameter("reason"); // 폼 데이터 받기 (신고 이유)

		HttpSession session = request.getSession(); // 세션 객체 가져오기
		int reporterId = (int) session.getAttribute("userId"); // 세션에서 사용자 ID 가져오기

		ReportDTO report = new ReportDTO(); // DTO 객체 생성
		report.setProductId(productId); // 폼 데이터로 DTO 객체 설정
		report.setReason(reason); // 폼 데이터로 DTO 객체 설정

		ProductDAO productDAO = new ProductDAO(); // DAO 객체 생성
		try {
			int result = productDAO.insertReport(report, reporterId); // DAO를 사용하여 데이터베이스에 신고 정보 삽입
			if (result > 0) {
				response.sendRedirect("reportSuccess.jsp"); // 신고 성공 시 reportSuccess.jsp로 이동
			} else {
				response.sendRedirect("reportFail.jsp"); // 신고 실패 시 reportFail.jsp로 이동
			}
		} catch (SQLException e) {
			e.printStackTrace(); // 오류 메시지 출력 (로그 기록)
			response.sendRedirect("reportFail.jsp"); // 오류 발생 시 reportFail.jsp로 이동
		}
	}
}