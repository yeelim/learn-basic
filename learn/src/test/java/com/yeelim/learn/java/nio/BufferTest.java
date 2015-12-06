/**
 * 
 */
package com.yeelim.learn.java.nio;

import java.nio.ByteBuffer;

import org.junit.Test;

/**
 * @author elim
 * @date 2014-11-17
 * @time 下午11:24:49 
 *
 */
public class BufferTest {

	@Test
	public void test01() {
		ByteBuffer buffer = ByteBuffer.wrap("abcdefghijklmnopqrstuvwxyz".getBytes());
		buffer.put("ABC".getBytes());
		System.out.println(buffer.capacity() + "---" + buffer.position() + "----" + buffer.limit() + "----" + buffer.remaining());
		this.printBuffer(buffer);
		buffer.rewind();//将position置为0
		this.printBuffer(buffer);
		buffer.position(0);
		this.printBuffer(buffer);
		buffer.compact();
		System.out.println(buffer.limit() + "----" + buffer.remaining() + "----" + buffer.position());
	}
	
	private void printBuffer(ByteBuffer buffer) {
		StringBuilder builder = new StringBuilder();
		for (int i=0; i<10; i++) {
			builder.append((char)buffer.get());
		}
		System.out.println(builder);
	}
	
}
