/**
 * 
 */
package com.yeelim.learn.ehcache.model;

/**
 * @author Yeelim
 * @date 2014-4-1
 * @time 下午8:36:59
 * 
 */
public class Unit {

	private Integer id;
	private String unitNo;
	private String unitName;
	private Unit parent;
	
	public Unit() {}
	
	public Unit(String unitNo, String unitName) {
		this.unitNo = unitNo;
		this.unitName = unitName;
	}

	/**
	 * 返回属性id
	 * 
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * 给属性id赋值
	 * 
	 * @param id
	 *            the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * 返回属性unitNo
	 * 
	 * @return the unitNo
	 */
	public String getUnitNo() {
		return unitNo;
	}

	/**
	 * 给属性unitNo赋值
	 * 
	 * @param unitNo
	 *            the unitNo to set
	 */
	public void setUnitNo(String unitNo) {
		this.unitNo = unitNo;
	}

	/**
	 * 返回属性unitName
	 * 
	 * @return the unitName
	 */
	public String getUnitName() {
		return unitName;
	}

	/**
	 * 给属性unitName赋值
	 * 
	 * @param unitName
	 *            the unitName to set
	 */
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	/**
	 * 返回属性parent
	 * 
	 * @return the parent
	 */
	public Unit getParent() {
		return parent;
	}

	/**
	 * 给属性parent赋值
	 * 
	 * @param parent
	 *            the parent to set
	 */
	public void setParent(Unit parent) {
		this.parent = parent;
	}

}
