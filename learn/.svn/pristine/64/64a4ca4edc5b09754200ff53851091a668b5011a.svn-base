/**
 * 
 */
package com.yeelim.learn.ehcache.sample;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.MemoryUnit;
import net.sf.ehcache.config.SearchAttribute;
import net.sf.ehcache.config.Searchable;
import net.sf.ehcache.search.Attribute;
import net.sf.ehcache.search.Direction;
import net.sf.ehcache.search.Query;
import net.sf.ehcache.search.Result;
import net.sf.ehcache.search.Results;
import net.sf.ehcache.search.attribute.DynamicAttributesExtractor;
import net.sf.ehcache.search.expression.And;
import net.sf.ehcache.search.expression.Not;
import net.sf.ehcache.search.expression.Or;

import org.junit.Test;

import bsh.EvalError;
import bsh.Interpreter;

import com.yeelim.learn.ehcache.model.Unit;
import com.yeelim.learn.ehcache.model.User;

/**
 * 
 * 对Cache进行搜索
 * @author Yeelim
 * @date 2014-3-18
 * @time 下午8:58:35 
 *
 */
public class CacheSearchTest {

	@SuppressWarnings("rawtypes")
	@Test
	public void listSearchAttr() {
		CacheManager cacheManager = CacheManager.create();
		Cache userCache = cacheManager.getCache("userCache");
		Set<Attribute> attrSet = userCache.getSearchAttributes();
		for (Attribute attr : attrSet) {
			System.out.println(attr);
		}
	}
	
	@Test
	public void search() {
		CacheManager cacheManager = CacheManager.create();
		Cache userCache = cacheManager.getCache("userCache");
		User user;
//		User2 user2;
		for (int i=0; i<10; i++) {
			user = new User(i, "name"+(i%2), 30+i);
//			user2 = new User2(i+100, "name"+(i%2), 40+i);
			userCache.put(new Element(user.getId(), user));
//			userCache.put(new Element(user2.getId(), user2));
		}
		this.listSearchableAttrs(userCache);
		//获取用于搜索的属性name
		Attribute<String> name = userCache.getSearchAttribute("name");
		//也可以如下使用搜索的属性name
//		Attribute<String> name = new Attribute<String>("name");
		Attribute<Integer> age = new Attribute<Integer>("age");
		//创建查询并指定筛选条件为当前Cache的可搜索属性name的值等于name1
		Query query = userCache.createQuery().addCriteria(name.eq("name1"));
//		Query query = userCache.createQuery().addCriteria(name.eq("name1").and(age.gt(35).not()));
		//三种逻辑关系：或、与、非。也可以使用Criteria.and()、or()、not()。
		new Or(null, null);
		new And(null, null);
		new Not(null);
		//name以“name”开始的，“*”代表任意多个字符，“?”代表任意一个字符
//		name.ilike("name*");
		//指定查询结果包含key，如果不指定的话，在查询结果中试图获取key时将抛出异常。
		query.includeKeys();
		//指定查询结果包含value和属性age
		query.includeValues().includeAttribute(age);
		//查询结果按age进行升序排列
		query.addOrderBy(age, Direction.ASCENDING);
		query.includeAggregator(age.max());
		Results results = query.execute();
		List<Result> resultList = results.all();
		for (Result result : resultList) {
			user = (User)result.getValue();
			Integer ageVal = result.getAttribute(age);
			Object maxAge = result.getAggregatorResults().get(0);
			System.out.println("maxAge is : " + maxAge);
			System.out.println(result.getKey() + "---" + ageVal + "----" + user);
		}
		//释放资源
		results.discard();
	}
	
	@Test
	public void search4NonCriteria() {
		CacheManager cacheManager = CacheManager.create();
		Cache userCache = cacheManager.getCache("userCache");
		User user;
		for (int i=0; i<10; i++) {
			user = new User(i, "name"+(i%2), 30+i);
			userCache.put(new Element(user.getId(), user));
		}
		//获取名称为name的可查询属性Attribute对象
		Attribute<String> name = userCache.getSearchAttribute("name");
		//创建一个用于查询的Query对象
		Query query = userCache.createQuery();
		//给当前query添加一个筛选条件——可查询属性name的值等于“name1”
		query.addCriteria(name.eq("name1"));
		query.includeKeys().includeValues();
		Results results = query.execute();
		for (Result result : results.all()) {
			System.out.println(result);
		}
	}
	
	public void search2() {
		CacheManager cacheManager = CacheManager.create();
		Cache userCache = cacheManager.getCache("userCache");
		User user;
		for (int i=0; i<10; i++) {
			user = new User(i, "name"+(i%2), 30+i);
			user.setMobile("13"+i+"12123443");
			user.setSex(i%2);
			user.setTel("8712789"+i);
			user.setUnit(new Unit("10010" + i, "UnitName: " + i));
			userCache.put(new Element(user.getId(), user));
		}
		//获取名称为name的可查询属性Attribute对象
		Attribute<String> name = userCache.getSearchAttribute("name");
		//创建一个用于查询的Query对象
		Query query = userCache.createQuery();
		//给当前query添加一个筛选条件——可查询属性name的值等于“name1”
		query.addCriteria(name.eq("name1"));
	}
	
	@Test
	public void search3() {
		CacheManager cacheManager = CacheManager.create();
		Cache userCache = cacheManager.getCache("userCache");
		User user;
		for (int i=0; i<10; i++) {
			user = new User(i, "name"+(i%2), 25+i);
			user.setMobile("13"+i+"12123443");
			user.setSex(i%2);
			user.setTel("8712789"+i);
			user.setUnit(new Unit("00" + i%4, "UnitName: " + i));
			userCache.put(new Element(user.getId(), user));
		}
	//创建一个用于查询的Query对象
	Query query = userCache.createQuery();
	Attribute<String> unitNo = userCache.getSearchAttribute("unitNo");
	Attribute<Integer> age = userCache.getSearchAttribute("age");
	//对单位编号进行分组
	query.addGroupBy(unitNo);
	//各单位年龄的平均值、最大值以及人数。
	query.includeAggregator(age.average(), age.max(), age.count());
	//查询结果中还包含单位编码
	query.includeAttribute(unitNo);
	query.end();
	Results results = query.execute();
//	results.discard()
	
	List<Result> resultList = results.all();
	if (resultList != null && !resultList.isEmpty()) {
		for (Result result : resultList) {
			String unitNoVal = result.getAttribute(unitNo); 
			//多个统计信息将会组成一个List进行返回
			List<Object> aggregatorResults = result.getAggregatorResults();
			Number averageAge = (Number)aggregatorResults.get(0);
			Integer maxAge = (Integer)aggregatorResults.get(1);
			Integer count = (Integer)aggregatorResults.get(2);
			System.out.println("单位编号：" + unitNoVal + "---" + averageAge + "，" + maxAge + "，" + count);
		}
	}
	System.out.println(resultList);
	
/*	//执行查询操作，返回查询结果Results
	Results results = query.execute();
	//获取Results中包含的所有的Result对象
	List<Result> resultList = results.all();
	if (resultList != null && !resultList.isEmpty()) {
		for (Result result : resultList) {
			//结果中包含key时可以获取key
			if (results.hasKeys()) {
				result.getKey();
			}
			//结果中包含value时可以获取value
			if (results.hasValues()) {
				result.getValue();
			}
			//结果中包含属性时可以获取某个属性的值
			if (results.hasAttributes()) {
				Attribute<String> attribute = userCache.getSearchAttribute("name");
				result.getAttribute(attribute);
			}
			//结果中包含统计信息时可以获取统计信息组成的List
			if (results.hasAggregators()) {
				result.getAggregatorResults();
			}
		}
	}*/
	
	//或者使用两次addCriteria
//	query.addCriteria(age.between(25, 35)).addCriteria(unitNo.eq("002"));
	}
	
	@Test
	public void test() {
		CacheManager cacheManager = CacheManager.create();
		CacheConfiguration cacheConfig = new CacheConfiguration();
		cacheConfig.name("cache1").maxBytesLocalHeap(100, MemoryUnit.MEGABYTES);
		Searchable searchable = new Searchable();
		//指定Cache的Searchable对象。
		cacheConfig.searchable(searchable);
		//如下指定也行
//		cacheConfig.addSearchable(searchable);
		Cache cache1 = new Cache(cacheConfig);
		cacheManager.addCache(cache1);
	}
	
	@Test
	public void dynamicExtractor() {
		CacheManager cacheManager = CacheManager.create();
		Cache userCache = cacheManager.getCache("userCache");
		userCache.registerDynamicAttributesExtractor(new DynamicAttributesExtractor() {

			@Override
			public Map<String, Object> attributesFor(Element element) {
				Map<String, Object> attrMap = new HashMap<String, Object>();
				attrMap.put("hitCount", element.getHitCount());
				return attrMap;
			}
			
		});
		userCache.registerDynamicAttributesExtractor(new DynamicAttributesExtractor() {

			@Override
			public Map<String, Object> attributesFor(Element element) {
				Map<String, Object> attrMap = new HashMap<String, Object>();
				attrMap.put("attr2", "attr2");
				attrMap.put("attr3", "attr3");
				return attrMap;
			}
			
		});
		this.listSearchableAttrs(userCache);	//key、value和name
		userCache.put(new Element("1", new User()));
		this.listSearchableAttrs(userCache);	//key、value、name和hitCount
	}
	
	/**
	 * 输出当前Ehcache中可查询的属性
	 * @param cache
	 */
	@SuppressWarnings("rawtypes")
	private void listSearchableAttrs(Ehcache cache) {
		Set<Attribute> attrSet = cache.getSearchAttributes();
		for (Attribute attr : attrSet) {
			System.out.println(attr.getAttributeName());
		}
	}
	
	@Test
	public void setSearchAttrInProgram() {
		CacheManager cacheManager = CacheManager.create();
		CacheConfiguration cacheConfig = new CacheConfiguration();
		cacheConfig.name("cacheName").maxBytesLocalHeap(100, MemoryUnit.MEGABYTES);
		//新建一个Searchable对象
		Searchable searchable = new Searchable();
		//给Cache配置Searchable对象，表明该Cache是一个可查询的Cache
		cacheConfig.searchable(searchable);
		//新建一个查询属性
		SearchAttribute searchAttribute = new SearchAttribute();
		//指定查询属性的名称和属性提取器的类名
		searchAttribute.name("查询属性名称");
		//searchAttribute.className("属性提取器的类名");
		//Searchalbe对象添加查询属性
		searchable.addSearchAttribute(searchAttribute);
		//使用CacheConfig创建Cache对象
		Cache cache = new Cache(cacheConfig);
		//把Cache对象纳入CacheManager的管理中
		cacheManager.addCache(cache);
		this.listSearchableAttrs(cache);
	}
	
	@Test
	public void beanShell() throws EvalError {
		CacheManager cacheManager = CacheManager.create();
		Cache userCache = cacheManager.getCache("userCache");
		User user;
		for (int i=0; i<10; i++) {
			user = new User(i, "name"+(i%2), 25+i);
			userCache.put(new Element(user.getId(), user));
		}
		//BeanShell解释器，需引入BeanShell相关jar包
		Interpreter interpreter = new Interpreter();
		Query query = userCache.createQuery().includeValues();
		//Interpreter进行计算的字符串中出现的变量都需要放入Interpreter的环境中
		interpreter.set("query", query);//把query放入Interpreter环境中
		//把age放入Interpreter环境中
		interpreter.set("age", userCache.getSearchAttribute("age"));
		String queryStr = "query.addCriteria(age.lt(30)).execute();";
		//BeanShell执行字符串表达式对userCache进行查询，并返回Results
		Results results = (Results)interpreter.eval(queryStr);
		for (Result result : results.all()) {
			System.out.println(result);
		}
		results.discard();
	}
	
}
