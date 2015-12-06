/**
 * 
 */
package com.yeelim.learn.jdk;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PushbackInputStream;
import java.net.URLDecoder;

import org.junit.Test;

/**
 * @author elim
 * @date 2014-12-6
 * @time 下午4:10:15
 * 
 */
public class InputStreamTest {

	@Test
	public void testPushbackInputStream() throws FileNotFoundException, IOException {
		try (InputStream is = new FileInputStream("D:\\test\\testWrite.txt");
				//第二个参数表示可以被放回去的字节数量，不指定时，默认为1。然后底层会维护一个对应大小的字节数组，用于储存放回去的字节，然后下次进行读取时，会优先从该字节数组中获取对应的字节。
				PushbackInputStream pis = new PushbackInputStream(is, is.available()+100);) {
			System.out.println(pis.available());
			pis.unread("测试".getBytes("UTF-8"));
			byte bs[] = new byte[1024];
			int len = pis.read(bs, 0, bs.length);
			System.out.println(len);
			System.out.println(new String(bs, 0, len));
		}
	}

	@Test
	public void testDataOutputStream() {
		try (DataOutputStream dos = new DataOutputStream(new FileOutputStream("D:\\test\\testData.txt"))) {
			for (int i=0; i<100; i++) {
				dos.writeInt(i);
				dos.writeUTF("\n");
			}
			this.testDataInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testDataInputStream() {
		try (DataInputStream dis = new DataInputStream(new FileInputStream("D:\\test\\testData.txt"))) {
			for (int i=0; i<100; i++) {
				System.out.println(dis.readInt());
				System.out.println(dis.readUTF());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String args[]) throws IOException {
		//多线程要使用main方法进行测试。
		new InputStreamTest().testPiped();
	}
	
	@Test
	public void testPiped() throws IOException {
		System.out.println(URLDecoder.decode("%D6%B4%D0%D0%A1%B0%BD%E1%CA%F8%C8%CE%CE%F1%A1%B1%B2%D9%D7%F7%CD%EA%B1%CF%A3%AC%B6%AF%D7%F7%D2%D1%BD%E1%CA%F8%D6%B4%D0%D0%A1%A3", "GB2312"));
		PipedInputStream pis = new PipedInputStream();
		new PipedOutThread(pis).start();
		new PipedInThread(pis).start();
	}
	
	private static class PipedInThread extends Thread {
		private PipedInputStream pis;
		private PipedInThread(PipedInputStream pis) {
			this.pis = pis;
		}
		
		public void run() {
			try {
				int avai = 100;
				for (int i=0; i<avai; i++) {
					System.out.println(pis.read());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	private static class PipedOutThread extends Thread {
		private PipedOutputStream pos;
		private PipedOutThread(PipedInputStream pis) throws IOException {
			this.pos = new PipedOutputStream(pis);
		}
		
		public void run() {
			for (int i=0; i<100; i++) {
				try {
					pos.write(i);
					Thread.sleep(1000);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
}
