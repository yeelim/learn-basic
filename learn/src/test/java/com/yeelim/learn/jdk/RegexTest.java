/**
 * 
 */
package com.yeelim.learn.jdk;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

/**
 * @author elim
 * @date 2015-1-1
 * @time 下午11:41:12 
 *
 */
public class RegexTest {

	@Test
	public void test() {
		String s = "hel123456alj";
		Pattern pattern = Pattern.compile("^[a-z]{3}(?<abc>.*)alj$");
		Matcher matcher = pattern.matcher(s);
		System.out.println(matcher.matches());//必须先通过matches方法进行匹配后才能进行后续的group等操作。
		if (matcher.matches()) {
			System.out.println(matcher.group());
			System.out.println(matcher.group("abc"));
		}
	}
	
	@Test
	public void test2() {
		String s = "helloChinaChinahello";
		Pattern pattern = Pattern.compile("^(?<a1>\\w+)(?<a2>\\w+)\\2\\1$");
		Matcher matcher = pattern.matcher(s);
		System.out.println(matcher.matches());
		if (matcher.matches()) {
			System.out.println(matcher.group("a1"));
			System.out.println(matcher.group("a2"));
			System.out.println(matcher.group());
			System.out.println(matcher.group(0));
			System.out.println(matcher.group(1));
			System.out.println(matcher.group(2));
		}
	}
	
}
