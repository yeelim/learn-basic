/**
 * 
 */
package com.yeelim.learn.jdk;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;

/**
 * @author elim
 * @date 2015-1-7
 * @time 下午10:46:31 
 *
 */
public class ClassloaderTest {

	static {
		System.out.println("---------------ClassLoaderTest...........");
	}
	
	private static class MyClassLoader extends ClassLoader {

		@Override
		public Class<?> loadClass(String name) throws ClassNotFoundException {
			if (name.equals("com.elim.learn.TestClass")) {
				try {
					byte[] bytes = Files.readAllBytes(Paths.get("D:\\test\\classes\\com.elim.learn.TestClass.class"));
					return this.defineClass(name, bytes, 0, bytes.length);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return super.loadClass(name);
		}

		@Override
		protected Class<?> loadClass(String name, boolean resolve)
				throws ClassNotFoundException {
			return super.loadClass(name, resolve);
		}

		@Override
		protected Class<?> findClass(String name) throws ClassNotFoundException {
			return super.findClass(name);
		}
		
	}
	
	@Test
	public void test() throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		MyClassLoader classLoader = new MyClassLoader();
		Class<?> testClass = classLoader.loadClass("com.elim.learn.TestClass");
		Object test = testClass.newInstance();//自定义加载的类的对象。
		System.out.println(testClass.newInstance().toString());
//		test.sayHello();
		testClass.getMethod("sayHello").invoke(test);//通过反射来调用其中的方法。
		System.out.println(test.getClass());
		System.out.println(test.getClass().getClassLoader());
	}
	
	@Test
	public void test2() throws ClassNotFoundException {
		String name = "com.yeelim.learn.jdk.ClassAndClassLoader";
		this.getClass().getClassLoader().loadClass(name);
		
		Class.forName(name);
	}
	
}
