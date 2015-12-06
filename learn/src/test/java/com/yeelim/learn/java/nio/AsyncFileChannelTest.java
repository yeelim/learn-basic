/**
 * 
 */
package com.yeelim.learn.java.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.junit.Test;

/**
 * @author elim
 * @date 2015-1-1
 * @time 下午3:34:07
 * 
 */
public class AsyncFileChannelTest {

	@Test
	public void test() {
		for (int i=0; i<100; i++) {
			final int index = i;
			new Thread() {

				@Override
				public void run() {
					write(index);
				}
				
			}.start();
		}
	}
	
	private void write(int index) {
		try (AsynchronousFileChannel channel = AsynchronousFileChannel.open(
				Paths.get("D:\\test\\asynchronousFileChannel.txt"),
				StandardOpenOption.WRITE, StandardOpenOption.CREATE);) {
			ByteBuffer buffer = ByteBuffer.allocate(1024);
			for (int i = 0; i < 256; i++) {
				buffer.putInt(index * 100000 + i);
			}
			buffer.flip();
			channel.write(buffer, index * 1024); // 第二个参数表示要写在文件的什么位置。
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testRead() {
		try (AsynchronousFileChannel channel = AsynchronousFileChannel.open(
				Paths.get("D:\\test\\asynchronousFileChannel.txt"),
				StandardOpenOption.READ);) {
			ByteBuffer buffer = ByteBuffer.allocate(32 * 1024);
			Future<Integer> future = channel.read(buffer, 0);
			System.out.println("--------------准备读取数据：" + System.currentTimeMillis());
			int size = future.get();
			System.out.println(System.currentTimeMillis() + "--------------Size: " + size);
			buffer.flip();
			for (int i=0; i<256; i++) {
				System.out.println(buffer.getInt());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

}
