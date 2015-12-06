/**
 * 
 */
package com.yeelim.learn.jdk;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

/**
 * 
 * 对于HashSet和HashMap而言，判断其中的元素是否相同需要对应元素的hashCode相同，
 * 且调用equals()的返回结果为true。具体可以参看源代码。
 * 
 * @author elim
 * @date 2015-2-3
 * @time 下午4:39:54
 * 
 */
public class HashSetTest {

	private final static Logger logger = Logger.getLogger(HashSetTest.class);
	
	@Test
	public void testCar() {
		Set<Car> carSet = new HashSet<>();
		for (int i=0; i<10; ) {
			carSet.add(new Car(i++));
		}
		for (int i=0; i<10; ) {
			logger.info("carSet.contains(new Car(i++))：" + carSet.contains(new Car(i++)));
		}
		for (int i=0; i<10; ) {
			carSet.add(new Car(i++));
		}
		Assert.assertTrue(carSet.size() == 20);
	}

	@Test
	public void testCat() {
		Set<Cat> catSet = new HashSet<>();
		for (int i=0; i<10; ) {
			catSet.add(new Cat(i++));
		}
		for (int i=0; i<10; ) {
			logger.info("carSet.contains(new Car(i++))：" + catSet.contains(new Cat(i++)));
		}
		for (int i=0; i<10; ) {
			catSet.add(new Cat(i++));
		}
		Assert.assertTrue(catSet.size() == 10);
	}
	
	/**
	 * 使用原始的hashCode方法
	 * @author elim
	 * @date 2015-2-3
	 * @time 下午5:07:59 
	 *
	 */
	private final class Car {

		private int id;

		public Car(int id) {
			this.id = id;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Car other = (Car) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (id != other.id)
				return false;
			return true;
		}

		private HashSetTest getOuterType() {
			return HashSetTest.this;
		}

	}

	/**
	 * 利用id重写了hashCode方法
	 * @author elim
	 * @date 2015-2-3
	 * @time 下午5:07:42 
	 *
	 */
	private final class Cat {

		private int id;

		public Cat(int id) {
			this.id = id;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + id;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Cat other = (Cat) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (id != other.id)
				return false;
			return true;
		}

		private HashSetTest getOuterType() {
			return HashSetTest.this;
		}

	}
	
}
