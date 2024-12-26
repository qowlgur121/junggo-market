package com.junggomarket.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/logout")
public class UserLogoutServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       HttpSession session = request.getSession(false); // 세션 객체 가져오기 (없으면 null 반환)
     if (session != null) {
         session.invalidate(); // 세션 무효화
   }
    response.sendRedirect("index.jsp"); // 로그아웃 후 index.jsp로 이동
 }
}