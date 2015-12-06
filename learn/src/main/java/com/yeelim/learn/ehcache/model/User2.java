/**
 * 
 */
package com.yeelim.learn.ehcache.model;

/**
 * @author Yeelim
 * @date 2014-3-18
 * @time 下午9:05:22 
 *
 */
public class User2 {
	
	private Integer id;
	
	private String name;
	
	private Integer age;

	public User2() {
		
	}
	
	public User2(Integer id, String name, Integer age) {
		this.id = id;
		this.name = name;
		this.age = age;
	}

	/**
	 * 返回属性id
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * 给属性id赋值
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * 返回字段name
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * 给name赋值
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 返回属性age
	 * @return the age
	 */
	public Integer getAge() {
		return age;
	}

	/**
	 * 给age赋值
	 * @param age the age to set
	 */
	public void setAge(Integer age) {
		this.age = age;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("User [id=").append(id).append(", name=").append(name)
				.append(", age=").append(age).append("]");
		return builder.toString();
	}
	
}
