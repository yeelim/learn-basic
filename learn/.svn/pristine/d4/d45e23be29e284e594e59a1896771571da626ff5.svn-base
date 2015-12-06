/**
 * 
 */
package com.yeelim.learn.jdk;

import java.io.IOException;
import java.nio.file.Paths;

import org.junit.Test;

/**
 * @author elim
 * @date 2015-1-1
 * @time 下午5:41:08 
 *
 */
public class ProcessBuilderTest {

	@Test
	public void test() throws IOException {
		
		ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", "ping1 www.baidu.com2 -n 10");
		pb.redirectOutput(Paths.get("D:\\test\\ping_1.txt").toFile());
		pb.redirectError(Paths.get("D:\\test\\ping_2.txt").toFile());
		pb.start();
	}
	
	@Test
	public void test2() throws IOException {
		
		ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", "ping", "www.baidu.com", "-n", "20");
		pb.redirectOutput(Paths.get("D:\\test\\ping_baidu_1.txt").toFile());
		pb.redirectError(Paths.get("D:\\test\\ping_baidu_2.txt").toFile());
		pb.start();
	}
	
}
