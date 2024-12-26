package com.junggomarket.controller;

import com.junggomarket.dao.UserDAO;
import com.junggomarket.dto.UserDTO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

@WebServlet("/register")
public class UserRegisterServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");

		// 사용자 이름이 "admin"인지 확인
		if ("admin".equals(username)) {
			response.sendRedirect("registerFail.jsp?message=adminUsername"); // 오류 메시지와 함께 회원 가입 실패 페이지로 이동
			return;
		}

		UserDTO user = new UserDTO();
		user.setUsername(username);
		user.setPassword(password);
		user.setEmail(email);
		user.setPhone(phone);

		UserDAO userDAO = new UserDAO();

		try {
			int result = userDAO.insertUser(user);
			if (result > 0) {
				response.sendRedirect("registerSuccess.jsp");
			} else {
				response.sendRedirect("registerFail.jsp");
			}

		} catch (SQLIntegrityConstraintViolationException e) {
			e.printStackTrace();
			response.sendRedirect("registerFail.jsp?message=duplicate"); // 중복된 아이디일 때 에러 메시지와 함께 실패 페이지로 이동
		} catch (SQLException e) {
			e.printStackTrace();
			response.sendRedirect("registerFail.jsp");
		}
	}
}