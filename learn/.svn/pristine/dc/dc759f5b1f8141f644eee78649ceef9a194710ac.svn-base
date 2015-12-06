/**
 * 
 */
package com.yeelim.learn.jdk;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author elim
 * @date 2014-12-7
 * @time 上午11:23:47 
 *
 */
public class BufferTest {

	@Test
	public void test() {
		ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
		buffer.put((byte)1);//1 byte
		Assert.assertTrue(buffer.position()==1);
		buffer.putShort((short)1);//2 byte
		Assert.assertTrue(buffer.position()==3);
		buffer.putChar((char)1);//2 byte
		Assert.assertTrue(buffer.position()==5);
		buffer.putInt(1);//4 byte
		Assert.assertTrue(buffer.position()==9);
		buffer.putLong(1);//8 byte
		Assert.assertTrue(buffer.position()==17);
		buffer.putFloat(1.0f);// 4byte
		Assert.assertTrue(buffer.position()==21);
		buffer.putDouble(1.0);// 8byte
		Assert.assertTrue(buffer.position()==29);
		buffer.flip();//重置limit为当前position，然后将position置为0，准备开始读。
		Assert.assertTrue(buffer.get()==1);
		Assert.assertTrue(buffer.getShort()==1);
		Assert.assertTrue(buffer.getChar()==1);
		Assert.assertTrue(buffer.getInt()==1);
		buffer.mark();//标记一下，对应位置为9
		Assert.assertTrue(buffer.getLong()==1);
		Assert.assertTrue(buffer.getFloat()==1);
		Assert.assertTrue(buffer.getDouble()==1);
		Assert.assertTrue(buffer.position()==29);
		buffer.reset();
		Assert.assertTrue(buffer.position()==9);
		Assert.assertTrue(buffer.getLong()==1);
		Assert.assertTrue(buffer.getFloat()==1);
		Assert.assertTrue(buffer.getDouble()==1);
		Assert.assertTrue(buffer.position()==29);
	}
	
	@Test
	public void test2() throws UnsupportedEncodingException {
		ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
		//UTF-8一个中文占3个字节
		byteBuffer.put("abcdefghijklmnopqrstuvwxyz*%中国".getBytes("UTF-8"));//一个byte可以存放一个字母
		Assert.assertTrue(byteBuffer.position()==34);
		
		ByteBuffer byteBuffer2 = byteBuffer.slice();
		//复用byteBuffer当前position到limit的空间，但是不会复用position和limit，所以byteBuffer2的position和limit应为0和1024-34=990.
		Assert.assertTrue(byteBuffer2.limit()==990);
		
		byteBuffer2.putInt(128);	//此时byteBuffer的34、35、36、37这四个字节将存放整数128，而byteBuffer2的0、1、2、3这四个字节将共享128
		
		Assert.assertTrue(byteBuffer.getInt(34)==128);
		Assert.assertTrue(byteBuffer.getInt(34)==byteBuffer2.getInt(0));
		
		//将复制一个全新的byteBuffer。将共享数据，但是对于的limit、position等信息是独立的，不过在复制的时候使用的是原ByteBuffer的position等信息。
		ByteBuffer byteBuffer3 = byteBuffer.duplicate();	
		Assert.assertTrue(byteBuffer.position()==byteBuffer3.position());
		Assert.assertTrue(byteBuffer.limit()==byteBuffer3.limit());
		Assert.assertTrue(byteBuffer.capacity()==byteBuffer3.capacity());
		
		byteBuffer3.putInt(128);
		Assert.assertTrue(byteBuffer.position()!=byteBuffer3.position());
		Assert.assertTrue(byteBuffer3.getInt(byteBuffer.position())==128);
		Assert.assertTrue(byteBuffer.getInt(byteBuffer.position())==128);
		
		byteBuffer.asReadOnlyBuffer();
		
	}
	
}
