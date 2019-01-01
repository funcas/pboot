
package com.funcas.pboot.common;

import com.funcas.pboot.common.enumeration.FieldType;
import com.funcas.pboot.common.util.ConvertUtils;
import com.funcas.pboot.common.util.ServletUtils;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 属性过滤器工具类
 * 
 * @author funcas
 *
 */
public class PropertyFilters {

	private static final String SEPARATOR = "_";
	public static final String DEFAULT_FILTER = "filter";
	
	/**
	 * 通过表达式和对比值创建属性过滤器
	 * 
	 * @param expression 表达式
	 * @param matchValue 对比值
	 * 
	 * @return
	 */
	@SuppressWarnings("static-access")
	public static Map<String,Object> get(String expression,String matchValue) {
		
		Assert.hasText(expression, "表达式不能为空");
		
		String classType = StringUtils.substringBefore(expression, SEPARATOR);
		FieldType fieldType = null;
		try {
			fieldType = FieldType.valueOf(classType);

		} catch (Exception e) {
			throw new IllegalAccessError("[" + expression + "]表达式找不到相应的属性类型,获取的值为:" + classType);
		}

		String propertyName = StringUtils.substringAfter(expression,  SEPARATOR);
		Map<String,Object> filterMap = Maps.newHashMap();
		filterMap.put(propertyName, ConvertUtils.convertToObject(matchValue, fieldType.getValue()));
		
		return filterMap;
	}
	
	/**
	 * 从HttpRequest参数中创建PropertyFilter列表, 默认Filter属性名前缀为filter.
	 * @param request HttpServletRequest
	 */
	public static Map<String,Object> get(HttpServletRequest request) {
		return get(request, DEFAULT_FILTER);
	}
	
	/**
	 * 从HttpRequest参数中创建PropertyFilter列表
	 * 
	 * @param request HttpServletRequest
	 * @param filterPrefix 用于识别是propertyfilter参数的前准
	 * 
	 * @return List
	 */
	public static Map<String,Object> get(HttpServletRequest request,String filterPrefix) {
		return get(request, filterPrefix,false);
	}
	
	/**
	 * 从HttpRequest参数中创建PropertyFilter列表
	 * @param request HttpServletRequest
	 * @param ignoreEmptyValue true表示当存在""值时忽略该PropertyFilter
	 * 
	 * @return List
	 */
	public static Map<String,Object> get(HttpServletRequest request,boolean ignoreEmptyValue) {
		return get(request, DEFAULT_FILTER,ignoreEmptyValue);
	}

	/**
	 * 从HttpRequest参数中创建PropertyFilter列表
	 * 
	 * @param request HttpServletRequest
	 * @param filterPrefix 用于识别是propertyfilter参数的前准
	 * @param ignoreEmptyValue true表示当存在""值时忽略该PropertyFilter
	 * 
	 * @return List
	 */
	public static Map<String,Object> get(HttpServletRequest request,String filterPrefix,boolean ignoreEmptyValue) {

		// 从request中获取含属性前缀名的参数,构造去除前缀名后的参数Map.
		Map<String, Object> filterParamMap = ServletUtils.getParametersStartingWith(request, filterPrefix + SEPARATOR);

		return get(filterParamMap,ignoreEmptyValue);
	}
	
	/**
	 * 从Map中创建PropertyFilter列表
	 *
	 * 
	 * @param filters 过滤器信息
	 * 
	 */
	public static Map<String,Object> get(Map<String, Object> filters) {
		
		return get(filters,false);
	}
	
	/**
	 * 从Map中创建PropertyFilter列表
	 *
	 *
	 * @param filters 过滤器信息
	 * @param ignoreEmptyValue true表示当存在 null或者""值时忽略该PropertyFilter
	 * 
	 */
	public static Map<String,Object> get(Map<String, Object> filters,boolean ignoreEmptyValue) {
		Map<String,Object> filterMap = Maps.newHashMap();
		// 分析参数Map,构造PropertyFilter列表
		for (Map.Entry<String, Object> entry : filters.entrySet()) {
			String expression = entry.getKey();
			Object value = entry.getValue();
			//如果ignoreEmptyValue为true忽略null或""的值
			if (ignoreEmptyValue) {
				if ((value == null || "".equals(value.toString().trim()))){
					continue;
				}
			}
			//如果ignoreEmptyValue为true忽略null或""的值
			filterMap.putAll(get(expression, value.toString()));

		}
		return filterMap;
	}

	
}
