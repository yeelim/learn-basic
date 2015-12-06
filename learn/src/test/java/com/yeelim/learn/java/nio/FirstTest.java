/**
 * 
 */
package com.yeelim.learn.java.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import org.junit.Test;

/**
 * @author Yeelim
 * @date 2014-4-17
 * @time 下午11:24:20 
 *
 */
public class FirstTest {
	
	@Test
	public void test() throws IOException {
		RandomAccessFile file = new RandomAccessFile("D:\\NIO\\test.txt", "rw");
		FileChannel channel = file.getChannel();
		ByteBuffer src = ByteBuffer.wrap("hello world".getBytes());
		channel.write(src);
		src.clear();
		src.put("你".getBytes());
		channel.write(src);
		file.close();
	}
	
	@Test
	public void testCopyFile() throws IOException {
		RandomAccessFile src = new RandomAccessFile("D:\\NIO\\copySrc.txt", "r");
		RandomAccessFile target = new RandomAccessFile("D:\\NIO\\copyTarget.txt", "rw");
		FileChannel srcChannel = src.getChannel();
		FileChannel targetChannel = target.getChannel();
		ByteBuffer buffer = ByteBuffer.allocate(64);
		while (srcChannel.read(buffer) != -1) {
			buffer.flip();
//			buffer.hasRemaining()
			targetChannel.write(buffer);
			buffer.compact();
		}
		src.close();
		target.close();
	}
	
	@Test
	public void copyFileByChannel() throws IOException {
		RandomAccessFile src = new RandomAccessFile("D:\\NIO\\copySrc.txt", "r");
		RandomAccessFile target = new RandomAccessFile("D:\\NIO\\copyTarget.txt", "rw");
		FileChannel srcChannel = src.getChannel();
		FileChannel targetChannel = target.getChannel();
		srcChannel.transferTo(0, src.length(), targetChannel);
//		targetChannel.truncate(1024);
		target.close();
		src.close();
	}
	
	@Test
	public void copyFileByChannel2() throws IOException {
		RandomAccessFile src = new RandomAccessFile("D:\\NIO\\copySrc.txt", "r");
		RandomAccessFile target = new RandomAccessFile("D:\\NIO\\copyTarget.txt", "rw");
		FileChannel srcChannel = src.getChannel();
		FileChannel targetChannel = target.getChannel();
		targetChannel.transferFrom(srcChannel, 0, srcChannel.size());
		target.close();
		src.close();
	}
	
}
