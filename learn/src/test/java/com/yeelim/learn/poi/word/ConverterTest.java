/**
 * 
 */
package com.yeelim.learn.poi.word;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.Writer;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.WordToFoConverter;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.converter.WordToTextConverter;
import org.junit.Test;

/**
 * @author Yeelim
 * @date 2014-2-15
 * @time 下午8:30:32 
 *
 */
public class ConverterTest {
	
	/**
	 * Word转Fo
	 * @throws Exception
	 */
	@Test
	public void testWordToFo() throws Exception {
		InputStream is = new FileInputStream("D:\\test.doc");
		HWPFDocument wordDocument = new HWPFDocument(is);
		WordToFoConverter converter = new WordToFoConverter(DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());
		//对HWPFDocument进行转换
		converter.processDocument(wordDocument);
        Writer writer = new FileWriter(new File("D:\\converter.xml"));
	    Transformer transformer = TransformerFactory.newInstance().newTransformer();
	    transformer.setOutputProperty( OutputKeys.ENCODING, "utf-8" );
	    //是否添加空格
        transformer.setOutputProperty( OutputKeys.INDENT, "yes" );
//	    transformer.setOutputProperty( OutputKeys.METHOD, "html" );
	    transformer.transform(
	                new DOMSource(converter.getDocument() ),
	                new StreamResult( writer ) );
	}
	
	/**
	 * Word转换为Html
	 * @throws Exception
	 */
	@Test
	public void testWordToHtml() throws Exception {
		InputStream is = new FileInputStream("D:\\test.doc");
		HWPFDocument wordDocument = new HWPFDocument(is);
		WordToHtmlConverter converter = new WordToHtmlConverter(DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());
		//对HWPFDocument进行转换
		converter.processDocument(wordDocument);
        Writer writer = new FileWriter(new File("D:\\converter.html"));
	    Transformer transformer = TransformerFactory.newInstance().newTransformer();
	    transformer.setOutputProperty( OutputKeys.ENCODING, "utf-8" );
	    //是否添加空格
        transformer.setOutputProperty( OutputKeys.INDENT, "yes" );
	    transformer.setOutputProperty( OutputKeys.METHOD, "html" );
	    transformer.transform(
	                new DOMSource(converter.getDocument() ),
	                new StreamResult( writer ) );
	}
	
	/**
	 * Word转换为Text
	 * @throws Exception
	 */
	@Test
	public void testWordToText() throws Exception {
		InputStream is = new FileInputStream("D:\\test.doc");
		HWPFDocument wordDocument = new HWPFDocument(is);
		WordToTextConverter converter = new WordToTextConverter(DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());
		//对HWPFDocument进行转换
		converter.processDocument(wordDocument);
        Writer writer = new FileWriter(new File("D:\\converter.txt"));
	    Transformer transformer = TransformerFactory.newInstance().newTransformer();
	    transformer.setOutputProperty( OutputKeys.ENCODING, "utf-8" );
	    //是否添加空格
        transformer.setOutputProperty( OutputKeys.INDENT, "yes" );
	    transformer.setOutputProperty( OutputKeys.METHOD, "text" );
	    transformer.transform(
	                new DOMSource(converter.getDocument() ),
	                new StreamResult( writer ) );
	}

}
