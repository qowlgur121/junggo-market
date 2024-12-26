package com.junggomarket.controller;

import com.junggomarket.dao.ProductDAO;
import com.junggomarket.dto.ProductDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.UUID;

// @WebServlet("/productUpdate"): 상품 수정 요청 처리 URL 매핑
@WebServlet("/productUpdate")
// @MultipartConfig: 파일 업로드 관련 설정
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 10)
public class ProductUpdateServlet extends HttpServlet {
	private static final String UPLOAD_DIR = "images";
	private ProductDAO productDAO = new ProductDAO(); // DAO 객체 생성

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int productId = Integer.parseInt(request.getParameter("productId")); // 상품 ID 파라미터에서 가져오기
		try {
			ProductDTO product = productDAO.selectProductById(productId); // 데이터베이스에서 상품 정보 조회
			HttpSession session = request.getSession(false);
			if (session != null && session.getAttribute("userId") != null
					&& (int) session.getAttribute("userId") == product.getUser_id()) { // 상품 작성자와 로그인 사용자 일치 확인
				request.setAttribute("product", product); // 상품 정보를 request 객체에 저장
				request.getRequestDispatcher("productUpdateForm.jsp").forward(request, response); // 상품 수정 페이지로 이동
			} else {
				// 로그인하지 않은 사용자 혹은 작성자가 아닌 사용자
				response.sendRedirect("productList"); // 권한 없는 사용자 처리
			}
		} catch (SQLException e) {
			e.printStackTrace(); // 오류 메시지 출력 (로그 기록)
			response.sendRedirect("productList"); // 오류 발생 시 상품 목록 페이지로 이동
		}
	}

	// doPost(): HTTP POST 요청 처리 (상품 수정 폼 데이터 처리)
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		int productId = Integer.parseInt(request.getParameter("productId")); // 폼 데이터 받기 (상품 ID)
		String title = request.getParameter("title"); // 폼 데이터 받기 (상품 제목)
		String description = request.getParameter("description"); // 폼 데이터 받기 (상품 설명)
		int price = Integer.parseInt(request.getParameter("price")); // 폼 데이터 받기 (상품 가격)
		String address = request.getParameter("address"); // 폼 데이터 받기 (상품 주소)

		// 파일 업로드 처리
		String applicationPath = request.getServletContext().getRealPath("");
		String uploadPath = applicationPath + UPLOAD_DIR;

		File fileUploadDirectory = new File(uploadPath);
		if (!fileUploadDirectory.exists()) {
			fileUploadDirectory.mkdirs();
		}
		String mainImageName = null;

		ProductDTO existingProduct = null;
		try {
			existingProduct = productDAO.selectProductById(productId); // 데이터베이스에서 기존 상품 정보 조회
			// 대표 이미지 파일 업로드 처리 (선택 사항)
			Part mainImagePart = request.getPart("main_image");
			if (mainImagePart != null && mainImagePart.getSize() > 0) {
				mainImageName = uploadFile(mainImagePart, uploadPath);
			} else {
				mainImageName = existingProduct.getMain_image().replace("images/", "");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		ProductDTO product = new ProductDTO(); // DTO 객체 생성
		product.setProduct_id(productId); // 폼 데이터로 DTO 객체 설정
		product.setTitle(title);
		product.setDescription(description);
		product.setPrice(price);
		product.setMain_image(UPLOAD_DIR + "/" + mainImageName);
		product.setAddress(address);

		try {
			int result = productDAO.updateProduct(product); // DAO를 사용하여 데이터베이스에 상품 정보 수정
			if (result > 0) {
				response.sendRedirect("productUpdateSuccess.jsp"); // 상품 수정 성공 시 productUpdateSuccess.jsp로 이동
			} else {
				response.sendRedirect("productUpdateFail.jsp"); // 상품 수정 실패 시 productUpdateFail.jsp로 이동
			}
		} catch (SQLException e) {
			e.printStackTrace(); // 오류 메시지 출력 (로그 기록)
			response.sendRedirect("productUpdateFail.jsp"); // 오류 발생 시 productUpdateFail.jsp로 이동
		}
	}

	// 파일 이름 변경 및 업로드 처리
	private String uploadFile(Part part, String uploadPath) throws IOException {
		String fileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
		if (fileName == null || fileName.isEmpty()) {
			return null;
		}
		String extension = fileName.substring(fileName.lastIndexOf("."));
		String uniqueFileName = UUID.randomUUID().toString() + extension;
		part.write(uploadPath + File.separator + uniqueFileName);
		return uniqueFileName;
	}
}