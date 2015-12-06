/**
 * 
 */
package com.yeelim.learn.ehcache.extractor.attribute;

import net.sf.ehcache.Element;
import net.sf.ehcache.search.attribute.AttributeExtractor;
import net.sf.ehcache.search.attribute.AttributeExtractorException;

import com.yeelim.learn.ehcache.model.User;

/**
 * @author Yeelim
 * @date 2014-3-22
 * @time 下午11:10:27 
 *
 */
public class UserAttributeExtractor implements AttributeExtractor {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Object attributeFor(Element element, String attributeName)
			throws AttributeExtractorException {
		User user = (User) element.getObjectValue();
		return user.getName();
	}

}
