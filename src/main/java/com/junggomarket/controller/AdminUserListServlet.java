package com.junggomarket.controller;

import com.junggomarket.dao.UserDAO;
import com.junggomarket.dto.UserDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

// @WebServlet("/adminUserList"): 관리자 회원 목록 요청 처리 URL 매핑
@WebServlet("/adminUserList")
public class AdminUserListServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false); // 세션 객체 가져오기
		String userRole = (String) session.getAttribute("userRole"); // 세션에서 사용자 역할 가져오기

		if ("admin".equals(userRole)) { // 사용자 역할이 admin인지 확인
			UserDAO userDAO = new UserDAO(); // DAO 객체 생성
			try {
				List<UserDTO> userList = userDAO.selectUserList(); // 데이터베이스에서 사용자 정보 조회
				request.setAttribute("userList", userList); // 사용자 목록을 request 객체에 저장
				request.getRequestDispatcher("adminUserList.jsp").forward(request, response); // adminUserList.jsp로 이동
			} catch (SQLException e) {
				e.printStackTrace(); // 오류 메시지 출력 (로그 기록)
				response.sendRedirect("index.jsp"); // 오류 발생 시 메인 페이지로 이동
			}
		} else {
			// 관리자 권한이 없을 경우
			response.sendRedirect("accessDenied.jsp"); // 권한 없음을 나타내는 페이지로 리다이렉트
		}
	}
}