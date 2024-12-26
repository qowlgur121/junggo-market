package com.junggomarket.controller;

import com.junggomarket.dao.ProductDAO;
import com.junggomarket.dto.ProductDTO;
import com.junggomarket.dto.PurchaseRequestDTO;
import com.junggomarket.utils.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// @WebServlet("/purchaseRequestList"): 구매 요청 목록 요청 처리 URL 매핑
@WebServlet("/purchaseRequestList")
public class PurchaseRequestListServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false); // 세션 객체 가져오기 (세션 없으면 null)
		if (session != null && session.getAttribute("userId") != null) { // 세션에 사용자 ID가 있다면
			int userId = (int) session.getAttribute("userId"); // 세션에서 사용자 ID 가져오기
			ProductDAO productDAO = new ProductDAO(); // DAO 객체 생성
			try {
				List<ProductDTO> productList = productDAO.selectProductList(); // 데이터베이스에서 상품 목록 조회
				List<PurchaseRequestDTO> purchaseRequestList = new ArrayList<>(); // 구매 요청 목록 생성
				for (ProductDTO product : productList) { // 해당 사용자가 올린 상품에 대한 모든 구매 요청 리스트를 만듭니다.
					if (product.getUser_id() == userId) {
						List<PurchaseRequestDTO> requests = productDAO
								.selectPurchaseRequestsByProductId(product.getProduct_id());
						purchaseRequestList.addAll(requests); // 해당 상품에 대한 구매 요청 추가
					}
				}
				request.setAttribute("purchaseRequestList", purchaseRequestList); // 구매 요청 목록을 request 객체에 저장
				request.getRequestDispatcher("purchaseRequestList.jsp").forward(request, response); // 구매 요청 목록 페이지로 이동
			} catch (SQLException e) {
				e.printStackTrace(); // 오류 메시지 출력 (로그 기록)
				response.sendRedirect("index.jsp"); // 오류 발생 시 메인 페이지로 이동
			}
		} else {
			// 세션이 없으면 index.jsp로 이동
			response.sendRedirect("index.jsp");
		}
	}

	// selectPurchaseRequestsByProductId(): 상품 ID로 구매 요청 정보를 데이터베이스에서 조회하는 메서드
	public List<PurchaseRequestDTO> selectPurchaseRequestsByProductId(int productId) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<PurchaseRequestDTO> purchaseRequestList = new ArrayList<>();

		try {
			conn = DBConnection.getConnection();
			String sql = "SELECT pr.*, u.username, p.title FROM purchase_requests pr JOIN users u ON pr.user_id = u.user_id JOIN products p ON pr.product_id = p.product_id WHERE pr.product_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, productId);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				PurchaseRequestDTO purchaseRequest = new PurchaseRequestDTO();
				purchaseRequest.setRequest_id(rs.getInt("request_id"));
				purchaseRequest.setProduct_id(rs.getInt("product_id"));
				purchaseRequest.setUser_id(rs.getInt("user_id"));
				purchaseRequest.setPrice_offer(rs.getInt("price_offer"));
				purchaseRequest.setPhone(rs.getString("phone"));
				purchaseRequest.setCreated_at(rs.getString("created_at"));
				purchaseRequest.setUsername(rs.getString("username"));
				purchaseRequest.setProductTitle(rs.getString("title"));
				purchaseRequestList.add(purchaseRequest);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (rs != null)
				rs.close();
			if (pstmt != null)
				pstmt.close();
			DBConnection.close(conn);
		}
		return purchaseRequestList;
	}
}