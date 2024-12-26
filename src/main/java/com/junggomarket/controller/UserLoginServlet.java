package com.junggomarket.controller;

import com.junggomarket.dao.UserDAO;
import com.junggomarket.dto.UserDTO;
import java.io.IOException;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class UserLoginServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		String username = request.getParameter("username"); // 폼 데이터 받기
		String password = request.getParameter("password"); // 폼 데이터 받기

		UserDAO userDAO = new UserDAO(); // DAO 객체 생성
		try {
			UserDTO user = userDAO.selectUserByUsername(username); // 데이터베이스에서 회원 정보 조회
			if (user != null && user.getPassword().equals(password)) { // 아이디와 비밀번호 일치 확인
				HttpSession session = request.getSession(); // 세션 객체 생성 및 사용자 정보 저장
				session.setAttribute("userId", user.getUserId()); // 세션에 사용자 ID 저장
				session.setAttribute("username", user.getUsername()); // 세션에 사용자 이름 저장
				session.setAttribute("userRole", user.getRole()); // 세션에 사용자 역할 저장 //추가됨
				response.sendRedirect("loginSuccess.jsp"); // 로그인 성공 시 loginSuccess.jsp로 이동
			} else {
				response.sendRedirect("loginFail.jsp"); // 로그인 실패 시 loginFail.jsp로 이동
			}
		} catch (SQLException e) {
			e.printStackTrace(); // 오류 메시지 출력 (로그 기록)
			response.sendRedirect("loginFail.jsp"); // 오류 발생 시 loginFail.jsp로 이동
		}
	}
}