/**
 * 
 */
package com.yeelim.learn.javaee6.servlet;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 * 
 */
@WebServlet("/servlet/upload")
@MultipartConfig
public class FileUploadServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		Part part = req.getPart("upload");
		//格式如：form-data; name="upload"; filename="YNote.exe"
		String disposition = part.getHeader("content-disposition");
		System.out.println(disposition);
		String fileName = disposition.substring(disposition.lastIndexOf("=")+2, disposition.length()-1);
		String fileType = part.getContentType();
		long fileSize = part.getSize();
		System.out.println("fileName: " + fileName);
		System.out.println("fileType: " + fileType);
		System.out.println("fileSize: " + fileSize);
		String uploadPath = req.getServletContext().getRealPath("/upload");
		System.out.println("uploadPath" + uploadPath);
		part.write(uploadPath + File.separator +fileName);
	}
	
}
