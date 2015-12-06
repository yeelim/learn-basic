/**
 * 
 */
package com.yeelim.learn.jdk;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

/**
 * @author elim
 * @date 2015-1-10
 * @time 下午11:50:52 
 *
 */
public class ClassLoaderResourceTest {

	@Test
	public void test1() {
		InputStream is = this.getClass().getClassLoader().getResourceAsStream("test.txt");//相对路径是类的根路径
		this.printContent(is);
	}
	
	@Test
	public void test2() {
		InputStream is = this.getClass().getClassLoader().getResourceAsStream("com/yeelim/learn/jdk/test1.txt");//相对路径是类的根路径
		System.out.println(is);
		this.printContent(is);
	}
	
	@Test
	public void test3() {
		//相对路径是当前类所在的路径，表示从与当前类所在路径的相对路径下获取test1.txt
		InputStream is = this.getClass().getResourceAsStream("test1.txt");
		this.printContent(is);
	}
	
	@Test
	public void test4() {
		//绝对路径是类的根路径，即从类的根路径下的com/yeelim/learn/jdk下获取test1.txt文件
		InputStream is = this.getClass().getResourceAsStream("/com/yeelim/learn/jdk/test1.txt");
		this.printContent(is);
	}
	
	@Test
	public void test5() {
		InputStream is = this.getClass().getResourceAsStream("/test.txt");
		this.printContent(is);
	}
	
	private void printContent(InputStream is) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int len = 0;
		byte []bs = new byte[1024];
		try {
			while ((len=is.read(bs)) != -1) {
				baos.write(bs, 0, len);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(new String(baos.toByteArray()));
	}
	
}
