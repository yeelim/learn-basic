/**
 * 
 */
package com.yeelim.learn.jdk;

import java.io.IOException;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.Provider;
import java.security.Provider.Service;
import java.security.Security;
import java.text.MessageFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import org.junit.Test;



/**
 * @author elim
 * @date 2014-10-29
 * @time 下午10:52:30 
 *
 */
public class BasicTest {

	@Test
	public void test() {
		int a = 055;//8进制
		int b = 0b101010;//二进制，或使用前缀0B
		int c = 0x11;//十六进制，或使用前缀0X
		System.out.println(a);//45
		System.out.println(b);//42
		System.out.println(c);//17
	}
	
	@Test
	public void testMessageFormat() {
		String pattern = "hello{0},world{1},welcome{2} to{3} china{4}.{0}";//{n}表示第n个参数。
		String result = MessageFormat.format(pattern, "hello", "world", "welcome", "to", "china");
		System.out.println(result);
		System.out.println(String.format("%1s==========%1s=========%2s===========%3s=======", "hi", "a", "c","d"));
	}
	
	@Test
	public void testMethodHandle() throws Throwable {
		MethodHandles.Lookup lookUp = MethodHandles.lookup();
		MethodType type = MethodType.methodType(String.class, int.class, int.class);
		MethodHandle methodHandle = lookUp.findVirtual(String.class, "substring", type);
		String result = (String)methodHandle.invokeExact("hello", 1, 3);
		System.out.println(result);
	}
	
	@Test
	public void testFileWrite() throws IOException {
		String userDir = System.getProperty("user.dir");
		System.out.println(userDir);
		try (FileChannel channel = FileChannel.open(Paths.get(userDir+"/test.txt"), StandardOpenOption.CREATE, StandardOpenOption.WRITE);) {
			ByteBuffer src = ByteBuffer.allocate(1024);
			src.put((new Date() + " hello world!\r\n").getBytes());
			src.flip();
			channel.write(src, 0);
		}
		
	}
	
	@Test
	public void testIterator() {
		List<Integer> intList = new CopyOnWriteArrayList<Integer>();
		for (int i=0; i<10; i++) {
			intList.add(Integer.valueOf(i));
		}
		Iterator<Integer> iter = intList.iterator();
		for (int i=10; i<20; i++) {
			intList.add(Integer.valueOf(i));
		}
		while (iter.hasNext()) {
			System.out.println(iter.next());
		}
	}
	
	@Test
	public void testProvider() {
		Provider[] providers = Security.getProviders();
		for (Provider provider : providers) {
			Set<Service> services = provider.getServices();
			for (Service service : services) {
				System.out.println(provider.getName() + "----" + provider.getVersion() + "----" + provider.getInfo() + "---========" + service.getAlgorithm() + "---" + service.getClassName() + "----" + service.getType());
			}
		}
		
	}
	
}
