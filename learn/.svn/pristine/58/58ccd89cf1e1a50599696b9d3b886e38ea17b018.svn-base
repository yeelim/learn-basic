
/**
 * 
 */
package com.yeelim.learn.poi.word.servlet;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.poifs.filesystem.POIFSFileSystem;

/**
 * @author Yeelim
 * @date 2014-3-30
 * @time 下午1:35:51 
 *
 */
@WebServlet("/servlet/word/web/exporter.do")
public class WebWordExporterServlet extends HttpServlet {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String path = "/index.jsp";
		InputStream is = getServletContext().getResource(path).openStream();
		is = new FileInputStream("D:\\123.txt");
		POIFSFileSystem fs = new POIFSFileSystem();
		fs.createDocument(is, "WordDocument");
		String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		OutputStream os = new FileOutputStream("D:\\word\\"+fileName+ ".doc");
		fs.writeFilesystem(os);
		os.close();
		is.close();
		resp.getWriter().write("OK");
	}

}
