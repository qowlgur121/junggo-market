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

// @WebServlet("/productRegister"): 상품 등록 요청 처리 URL 매핑
@WebServlet("/productRegister")
// @MultipartConfig: 파일 업로드 관련 설정 (파일 크기 제한 등)
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 10)
public class ProductRegisterServlet extends HttpServlet {
	private static final String UPLOAD_DIR = "images";

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		String title = request.getParameter("title");
		String description = request.getParameter("description");
		int price = Integer.parseInt(request.getParameter("price"));
		String address = request.getParameter("address");

		// 파일 업로드 처리
		String applicationPath = request.getServletContext().getRealPath("");
		String uploadPath = applicationPath + UPLOAD_DIR;

		File fileUploadDirectory = new File(uploadPath);
		if (!fileUploadDirectory.exists()) {
			fileUploadDirectory.mkdirs();
		}
		String mainImageName = null;

		try {
			Part mainImagePart = request.getPart("main_image");
			if (mainImagePart != null && mainImagePart.getSize() > 0) {
				mainImageName = uploadFile(mainImagePart, uploadPath);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		HttpSession session = request.getSession();
		int userId = (int) session.getAttribute("userId");
		ProductDTO product = new ProductDTO();
		product.setTitle(title);
		product.setDescription(description);
		product.setPrice(price);
		product.setMain_image(UPLOAD_DIR + "/" + mainImageName);
		product.setAddress(address);
		ProductDAO productDAO = new ProductDAO();
		try {
			int result = productDAO.insertProduct(product, userId);
			if (result > 0) {
				response.sendRedirect("productSuccess.jsp");
			} else {
				response.sendRedirect("productFail.jsp");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			response.sendRedirect("productFail.jsp");
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