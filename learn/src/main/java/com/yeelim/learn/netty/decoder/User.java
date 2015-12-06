/**
 * 
 */
package com.yeelim.learn.netty.decoder;

import java.io.Serializable;

/**
 * @author elim
 * @date 2015-3-28
 * @time 下午1:10:12
 * 
 */
public class User implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -7153993343309972030L;
	private int id;
	private String name;

	public User(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("User [id=").append(id).append(", name=").append(name)
				.append("]");
		return builder.toString();
	}

	/**
	 * 返回属性id
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * 给属性id赋值
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * 返回属性name
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * 给属性name赋值
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

}
