package com.junggomarket.dao;

import com.junggomarket.dto.ProductDTO;
import com.junggomarket.dto.PurchaseRequestDTO;
import com.junggomarket.dto.ReportDTO;
import com.junggomarket.utils.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
	public int insertProduct(ProductDTO product, int userId) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		try {
			conn = DBConnection.getConnection();
			String sql = "INSERT INTO products (user_id, title, description, price, main_image, address)"
					+ " VALUES (?, ?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userId);
			pstmt.setString(2, product.getTitle());
			pstmt.setString(3, product.getDescription());
			pstmt.setInt(4, product.getPrice());
			pstmt.setString(5, product.getMain_image());
			pstmt.setString(6, product.getAddress());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (pstmt != null)
				pstmt.close();
			DBConnection.close(conn);
		}
		return result;
	}

	public List<ProductDTO> selectProductList() throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<ProductDTO> productList = new ArrayList<>();

		try {
			conn = DBConnection.getConnection();
			String sql = "SELECT p.*, u.username FROM products p JOIN users u ON p.user_id = u.user_id";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				ProductDTO product = new ProductDTO();
				product.setProduct_id(rs.getInt("product_id"));
				product.setUser_id(rs.getInt("user_id"));
				product.setTitle(rs.getString("title"));
				product.setDescription(rs.getString("description"));
				product.setPrice(rs.getInt("price"));
				product.setMain_image(rs.getString("main_image"));
				product.setAddress(rs.getString("address"));
				product.setUsername(rs.getString("username"));
				product.setView_count(rs.getInt("view_count"));
				productList.add(product);
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
		return productList;
	}

	public ProductDTO selectProductById(int productId) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ProductDTO product = null;

		try {
			conn = DBConnection.getConnection();
			String sql = "SELECT p.*, u.username FROM products p JOIN users u ON p.user_id = u.user_id WHERE product_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, productId);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				product = new ProductDTO();
				product.setProduct_id(rs.getInt("product_id"));
				product.setUser_id(rs.getInt("user_id"));
				product.setTitle(rs.getString("title"));
				product.setDescription(rs.getString("description"));
				product.setPrice(rs.getInt("price"));
				product.setMain_image(rs.getString("main_image"));
				product.setAddress(rs.getString("address"));
				product.setUsername(rs.getString("username"));
				product.setView_count(rs.getInt("view_count"));
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
		return product;
	}

	public int updateProduct(ProductDTO product) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;

		try {
			conn = DBConnection.getConnection();
			String sql = "UPDATE products SET title = ?, description = ?, price = ?, main_image = ?, address = ? WHERE product_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, product.getTitle());
			pstmt.setString(2, product.getDescription());
			pstmt.setInt(3, product.getPrice());
			pstmt.setString(4, product.getMain_image());
			pstmt.setString(5, product.getAddress());
			pstmt.setInt(6, product.getProduct_id());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (pstmt != null)
				pstmt.close();
			DBConnection.close(conn);
		}
		return result;
	}

	public int deleteProductById(int productId) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;

		try {
			conn = DBConnection.getConnection();
			String sql = "DELETE FROM purchase_requests WHERE product_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, productId);
			pstmt.executeUpdate();
			pstmt.close();

			sql = "DELETE FROM reports WHERE product_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, productId);
			pstmt.executeUpdate();
			pstmt.close();

			sql = "DELETE FROM products WHERE product_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, productId);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (pstmt != null)
				pstmt.close();
			DBConnection.close(conn);
		}
		return result;
	}

	public List<ProductDTO> selectProductByKeyword(String keyword, String address) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<ProductDTO> productList = new ArrayList<>();

		try {
			conn = DBConnection.getConnection();
			StringBuilder sqlBuilder = new StringBuilder(
					"SELECT p.*, u.username FROM products p JOIN users u ON p.user_id = u.user_id WHERE 1=1");

			if (keyword != null && !keyword.isEmpty()) {
				sqlBuilder.append(" AND title LIKE ?");
			}
			if (address != null && !address.isEmpty()) {
				sqlBuilder.append(" AND address LIKE ?");
			}
			pstmt = conn.prepareStatement(sqlBuilder.toString());

			int parameterIndex = 1;
			if (keyword != null && !keyword.isEmpty()) {
				pstmt.setString(parameterIndex++, "%" + keyword + "%");
			}
			if (address != null && !address.isEmpty()) {
				pstmt.setString(parameterIndex, "%" + address + "%");
			}

			rs = pstmt.executeQuery();

			while (rs.next()) {
				ProductDTO product = new ProductDTO();
				product.setProduct_id(rs.getInt("product_id"));
				product.setUser_id(rs.getInt("user_id"));
				product.setTitle(rs.getString("title"));
				product.setDescription(rs.getString("description"));
				product.setPrice(rs.getInt("price"));
				product.setMain_image(rs.getString("main_image"));
				product.setAddress(rs.getString("address"));
				product.setUsername(rs.getString("username"));
				productList.add(product);
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
		return productList;
	}

	public List<ProductDTO> selectProductList(int page, int pageSize) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<ProductDTO> productList = new ArrayList<>();

		try {
			conn = DBConnection.getConnection();
			String sql = "SELECT p.*, u.username FROM products p JOIN users u ON p.user_id = u.user_id ORDER BY product_id DESC LIMIT ?, ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, (page - 1) * pageSize);
			pstmt.setInt(2, pageSize);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				ProductDTO product = new ProductDTO();
				product.setProduct_id(rs.getInt("product_id"));
				product.setUser_id(rs.getInt("user_id"));
				product.setTitle(rs.getString("title"));
				product.setDescription(rs.getString("description"));
				product.setPrice(rs.getInt("price"));
				product.setMain_image(rs.getString("main_image"));
				product.setAddress(rs.getString("address"));
				product.setUsername(rs.getString("username"));
				product.setView_count(rs.getInt("view_count"));
				productList.add(product);
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
		return productList;
	}

	public int getProductCount() throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count = 0;

		try {
			conn = DBConnection.getConnection();
			String sql = "SELECT COUNT(*) FROM products";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				count = rs.getInt(1);
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
		return count;
	}

	public int insertPurchaseRequest(PurchaseRequestDTO purchaseRequest) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;

		try {
			conn = DBConnection.getConnection();
			String sql = "INSERT INTO purchase_requests (product_id, user_id, price_offer, phone) VALUES (?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, purchaseRequest.getProduct_id());
			pstmt.setInt(2, purchaseRequest.getUser_id());
			pstmt.setInt(3, purchaseRequest.getPrice_offer());
			pstmt.setString(4, purchaseRequest.getPhone());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (pstmt != null)
				pstmt.close();
			DBConnection.close(conn);
		}
		return result;
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

	// increaseViewCount(): 상품 조회수를 1 증가시키는 메서드
	public void increaseViewCount(int productId) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = DBConnection.getConnection();
			String sql = "UPDATE products SET view_count = view_count + 1 WHERE product_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, productId);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (pstmt != null)
				pstmt.close();
			DBConnection.close(conn);
		}
	}

	// insertReport(): 신고를 데이터베이스에 삽입하는 메서드
	public int insertReport(ReportDTO report, int reporterId) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;

		try {
			conn = DBConnection.getConnection();
			String sql = "INSERT INTO reports (product_id, reporter_id, reason) VALUES (?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, report.getProductId());
			pstmt.setInt(2, reporterId);
			pstmt.setString(3, report.getReason());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (pstmt != null)
				pstmt.close();
			DBConnection.close(conn);
		}
		return result;
	}

	// selectReportList(): 신고 목록을 데이터베이스에서 조회하는 메서드
	public List<ReportDTO> selectReportList() throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<ReportDTO> reportList = new ArrayList<>();

		try {
			conn = DBConnection.getConnection();
			String sql = "SELECT r.*, u.username, p.title FROM reports r JOIN users u ON r.reporter_id = u.user_id JOIN products p ON r.product_id = p.product_id";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				ReportDTO report = new ReportDTO();
				report.setReportId(rs.getInt("report_id"));
				report.setProductId(rs.getInt("product_id"));
				report.setReporterId(rs.getInt("reporter_id"));
				report.setReason(rs.getString("reason"));
				report.setCreated_at(rs.getString("created_at"));
				report.setUsername(rs.getString("username"));
				report.setProductTitle(rs.getString("title"));
				reportList.add(report);
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
		return reportList;
	}
}