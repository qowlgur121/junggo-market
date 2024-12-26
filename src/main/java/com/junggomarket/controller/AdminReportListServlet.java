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
import java.util.List;

// @WebServlet("/adminReportList"): 관리자 신고 목록 요청 처리 URL 매핑
@WebServlet("/adminReportList")
public class AdminReportListServlet extends HttpServlet {
	private ProductDAO productDAO = new ProductDAO(); // DAO 객체 생성

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false); // 세션 객체 가져오기
		String userRole = (String) session.getAttribute("userRole"); // 세션에서 사용자 역할을 가져오기

		if ("admin".equals(userRole)) { // 사용자 역할이 admin인지 확인
			try {
				List<ReportDTO> reportList = productDAO.selectReportList(); // 데이터베이스에서 신고 목록 조회
				request.setAttribute("reportList", reportList); // 신고 목록을 request 객체에 저장
				request.getRequestDispatcher("adminReportList.jsp").forward(request, response); // adminReportList.jsp로
																								// 이동
			} catch (SQLException e) {
				e.printStackTrace(); // 오류 메시지 출력 (로그 기록)
				response.sendRedirect("index.jsp"); // 오류 발생 시 메인 페이지로 이동
			}
		} else {
			// 관리자 권한이 없는 경우
			response.sendRedirect("accessDenied.jsp"); // 권한 없음을 나타내는 페이지로 리다이렉트
		}
	}
}