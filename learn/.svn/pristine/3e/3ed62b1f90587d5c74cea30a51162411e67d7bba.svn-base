/**
 * 
 */
package com.yeelim.learn.poi.word.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;

/**
 * @author Yeelim
 * @date 2014-3-28
 * @time 下午5:09:54 
 *
 */
@WebServlet("/servlet/word/export.do")
public class WordExportorServlet extends HttpServlet {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String wordName = "中国人";	//假设这是我们word文档导出的文件名
		//假设这里获取到了word模板文件的相对于ServletContext根的路径
		String path = "/template.doc";
		ServletContext context = getServletContext();
		//获取到作为模板的word文件的输入流
		InputStream is = context.getResourceAsStream(path);
		HWPFDocument doc = new HWPFDocument(is);
		Range range = doc.getRange();
		//替换变量
		range.replaceText("${xx}", "xxx");
		response.setHeader("Content-Disposition", "attachment;filename=" + this.getFileName(wordName) + "");
        OutputStream output = response.getOutputStream();
        doc.write(output);
        output.close();
        is.close();
	}
	
	/**
	 * 把wordName以ISO-8859-1编码，同时加上“.doc”后缀进行返回。
	 * @param wordName 要导出的word文件的名称
	 * @return
	 */
	private String getFileName(String wordName) {
		try {
			wordName = new String(wordName.getBytes("UTF-8"), "ISO-8859-1"); //防中文乱码
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return wordName + ".doc";
	}

}
