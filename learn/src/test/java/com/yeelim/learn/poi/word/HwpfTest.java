/**
 * 
 */
package com.yeelim.learn.poi.word;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.hpsf.DocumentSummaryInformation;
import org.apache.poi.hpsf.SummaryInformation;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.hwpf.usermodel.Bookmark;
import org.apache.poi.hwpf.usermodel.Bookmarks;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.hwpf.usermodel.Section;
import org.apache.poi.hwpf.usermodel.Table;
import org.apache.poi.hwpf.usermodel.TableCell;
import org.apache.poi.hwpf.usermodel.TableCellDescriptor;
import org.apache.poi.hwpf.usermodel.TableIterator;
import org.apache.poi.hwpf.usermodel.TableRow;
import org.junit.Test;

/**
 * @author Yeelim
 * @date 2014-2-12
 * @time 下午9:35:12 
 *
 */
public class HwpfTest {

	@SuppressWarnings("deprecation")
	@Test
	public void testReadByExtractor() throws Exception {
		InputStream is = new FileInputStream("D:\\test.doc");
		WordExtractor extractor = new WordExtractor(is);
		//输出word文档所有的文本
		System.out.println(extractor.getText());
		System.out.println(extractor.getTextFromPieces());
		//输出页眉的内容
		System.out.println("页眉：" + extractor.getHeaderText());
		//输出页脚的内容
		System.out.println("页脚：" + extractor.getFooterText());
		//输出当前word文档的元数据信息，包括作者、文档的修改时间等。
		System.out.println(extractor.getMetadataTextExtractor().getText());
		//获取各个段落的文本
		String paraTexts[] = extractor.getParagraphText();
		for (int i=0; i<paraTexts.length; i++) {
			System.out.println("Paragraph " + (i+1) + " : " + paraTexts[i]);
		}
		//输出当前word的一些信息
		printInfo(extractor.getSummaryInformation());
		//输出当前word的一些信息
		this.printInfo(extractor.getDocSummaryInformation());
		this.closeStream(is);
	}
	
	/**
	 * 输出SummaryInfomation
	 * @param info
	 */
	private void printInfo(SummaryInformation info) {
		//作者
		System.out.println(info.getAuthor());
		//字符统计
		System.out.println(info.getCharCount());
		//页数
		System.out.println(info.getPageCount());
		//标题
		System.out.println(info.getTitle());
		//主题
		System.out.println(info.getSubject());
	}
	
	/**
	 * 输出DocumentSummaryInfomation
	 * @param info
	 */
	private void printInfo(DocumentSummaryInformation info) {
		//分类
		System.out.println(info.getCategory());
		//公司
		System.out.println(info.getCompany());
	}
	
	/**
	 * 关闭输入流
	 * @param is
	 */
	private void closeStream(InputStream is) {
		if (is != null) {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 关闭输出流
	 * @param os
	 */
	private void closeStream(OutputStream os) {
		if (os != null) {
			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Test
	public void testReadByDoc() throws Exception {
		InputStream is = new FileInputStream("D:\\test.doc");
		HWPFDocument doc = new HWPFDocument(is);
		//输出书签信息
		this.printInfo(doc.getBookmarks());
		//输出文本
		System.out.println(doc.getDocumentText());
		Range range = doc.getRange();
//		this.insertInfo(range);
		this.printInfo(range);
		//读表格
		this.readTable(range);
		//读列表
		this.readList(range);
		//删除range
		Range r = new Range(2, 5, doc);
		r.delete();//在内存中进行删除，如果需要保存到文件中需要再把它写回文件
		//把当前HWPFDocument写到输出流中
		doc.write(new FileOutputStream("D:\\test.doc"));
		this.closeStream(is);
	}
	
	/**
	 * 输出书签信息
	 * @param bookmarks
	 */
	private void printInfo(Bookmarks bookmarks) {
		int count = bookmarks.getBookmarksCount();
		System.out.println("书签数量：" + count);
		Bookmark bookmark;
		for (int i=0; i<count; i++) {
			bookmark = bookmarks.getBookmark(i);
			System.out.println("书签" + (i+1) + "的名称是：" + bookmark.getName());
			System.out.println("开始位置：" + bookmark.getStart());
			System.out.println("结束位置：" + bookmark.getEnd());
		}
	}
	
	/**
	 * 读表格
	 * 每一个回车符代表一个段落，所以对于表格而言，每一个单元格至少包含一个段落，每行结束都是一个段落。
	 * @param range
	 */
	private void readTable(Range range) {
		//遍历range范围内的table。
		TableIterator tableIter = new TableIterator(range);
		Table table;
		TableRow row;
		TableCell cell;
		while (tableIter.hasNext()) {
			table = tableIter.next();
			int rowNum = table.numRows();
			for (int j=0; j<rowNum; j++) {
				row = table.getRow(j);
				int cellNum = row.numCells();
				for (int k=0; k<cellNum; k++) {
					cell = row.getCell(k);
					//输出单元格的文本
					System.out.println(cell.text().trim());
				}
			}
		}
	}
	
	/**
	 * 读列表
	 * @param range
	 */
	private void readList(Range range) {
		int num = range.numParagraphs();
		Paragraph para;
		for (int i=0; i<num; i++) {
			para = range.getParagraph(i);
			if (para.isInList()) {
				System.out.println("list: " + para.text());
			}
		}
	}
	
	/**
	 * 输出Range
	 * @param range
	 */
	private void printInfo(Range range) {
		//获取段落数
		int paraNum = range.numParagraphs();
		System.out.println(paraNum);
		for (int i=0; i<paraNum; i++) {
//			this.insertInfo(range.getParagraph(i));
			System.out.println("段落" + (i+1) + "：" + range.getParagraph(i).text());
			if (i == (paraNum-1)) {
				this.insertInfo(range.getParagraph(i));
			}
		}
		int secNum = range.numSections();
		System.out.println(secNum);
		Section section;
		for (int i=0; i<secNum; i++) {
			section = range.getSection(i);
			System.out.println(section.getMarginLeft());
			System.out.println(section.getMarginRight());
			System.out.println(section.getMarginTop());
			System.out.println(section.getMarginBottom());
			System.out.println(section.getPageHeight());
			System.out.println(section.text());
		}
	}
	
	/**
	 * 插入内容到Range，这里只会写到内存中
	 * @param range
	 */
	private void insertInfo(Range range) {
		range.insertAfter("Hello");
		Table table = range.insertTableBefore((short)5, 3);
		TableRow row;
		TableCell cell;
		for (int i=0; i<3; i++) {
			row = table.getRow(i);
//			row.setRowHeight(1);
			for (int j=0; j<5; j++) {
				cell = row.getCell(j);
				TableCellDescriptor descriptor = cell.getDescriptor();
				descriptor.setFNoWrap(true);
				descriptor.setFtsWidth((byte)20);
				descriptor.setWWidth((short)200);
				cell.getDescriptor().setFFitText(true);
				System.out.println(cell.getWidth());;
				cell.getDescriptor().setFtsWidth((byte)100);//.setWWidth((short)100);
				cell.insertBefore("第" + (i+1) + "行，第" + (j+1) + "列");
			}
		}
	}
	
	@Test
	public void testWrite() throws Exception {
		String templatePath = "D:\\word\\template.doc";
		InputStream is = new FileInputStream(templatePath);
		HWPFDocument doc = new HWPFDocument(is);
		Range range = doc.getRange();
		//把range范围内的${reportDate}替换为当前的日期
		range.replaceText("${reportDate}", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		range.replaceText("${appleAmt}", "100.00");
		range.replaceText("${bananaAmt}", "200.00");
		range.replaceText("${totalAmt}", "300.00");
		OutputStream os = new FileOutputStream("D:\\word\\write.doc");
		//把doc输出到输出流中
		doc.write(os);
		this.closeStream(os);
		this.closeStream(is);
	}
	
	/**
	 * 换行
	 * @throws Exception 
	 */
	@Test
	public void newLine() throws Exception {
		String templatePath = "D:\\word\\newLine.doc";
		InputStream is = new FileInputStream(templatePath);
		HWPFDocument doc = new HWPFDocument(is);
		Range range = doc.getRange();
		//在表格内使用“\r”是不能换行的
		range.replaceText("${param1}", "参数1的内容\r换行");
		//(char)11就代表一个换行符可以用在表格中
		range.replaceText("${param2}", "参数2的内容"+(char)11+"换行");
		//非表格内使用“\r”是可以换行的
		range.replaceText("${param3}", "参数3的内容\r换行");
		//非表格内使用“(char)11”也是可以换行的
		range.replaceText("${param4}", "参数4的内容"+(char)11+"换行");
		OutputStream os = new FileOutputStream("D:\\word\\newLine2.doc");
		doc.write(os);
		this.closeStream(os);
		this.closeStream(is);
	}
	
}
