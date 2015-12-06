/**
 * 
 */
package com.yeelim.learn.jdk;

import java.util.Objects;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author elim
 * @date 2015-1-1
 * @time 下午11:22:59 
 *
 */
public class ObjectsTest {

	@Test
	public void testEqual() {
		String a = "Hello";
		String b = "Hello";
		Assert.assertEquals(a, b);
		Assert.assertTrue(Objects.equals(a, b));
		Assert.assertNotNull(a);
	}
	
	@Test
	public void testDeepEqual() {
		int a[] = new int[] {1, 3, 7};
		int b[] = new int[] {1, 3, 7};
		Assert.assertFalse(Objects.equals(a, b));
		Assert.assertTrue(Objects.deepEquals(a, b));
	}
	
	@Test
	public void testHash() {
		Integer a = 1000;
		System.out.println(a.hashCode());
		System.out.println(Objects.hashCode(a));
		System.out.println(Objects.hash(a));
		Assert.assertTrue(a.hashCode() == Objects.hashCode(a));
	}
}
