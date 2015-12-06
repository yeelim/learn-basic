/**
 * 
 */
package com.yeelim.learn.java.concurrent;

import java.util.concurrent.atomic.AtomicReference;

import org.junit.Test;

/**
 * @author elim
 * @date 2015-1-11
 * @time 下午11:11:54 
 *
 */
public class AtomicReferenceTest {

	private static class User {
		
		int id = 1;
		
		public String toString() {
			return "**************AtomicReference**************" + super.toString();
		}
		
	}
	
	@Test
	public void test() {
		User user = new AtomicReferenceTest.User();
		AtomicReference<User> ref = new AtomicReference<User>(user);
		System.out.println(ref.get());
		System.out.println(ref.get().id);
		User user2 = new AtomicReferenceTest.User();
		ref.compareAndSet(user, user2);
		System.out.println(ref.get());
	}
	
	@Test
	public void test2() {
		Double d1 = new Double(1.0);
		AtomicReference<Double> ref = new AtomicReference<Double>(d1);
		System.out.println(d1 + "------------" + ref.get());
		Double d2 = new Double(1.0);
		System.out.println(ref.compareAndSet(d1, d2));
		System.out.println(ref.get());
		System.out.println(d1 == ref.get());
	}
	
}
