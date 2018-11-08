package com.funcas.pboot.module.util;

import com.funcas.pboot.common.enumeration.FieldType;
import com.funcas.pboot.common.enumeration.ValueEnum;
import com.funcas.pboot.module.sys.entity.DataDictionary;
import com.funcas.pboot.module.sys.service.ISystemVariableService;
import com.funcas.pboot.module.upms.entity.BaseUserDetail;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;

/**
 * 变量工具类，该类封装对系统变量的一些静态获取方法
 *
 */
@Component
@DependsOn("systemVariableService")
public class VariableUtils {

    /**
     * 默认字典值的值名称
     */
    public static final String DEFAULT_VALUE_NAME = "name";
    /**
     * 默认字典值的键名称
     */
    public static final String DEFAULT_KEY_NAME = "value";

    static public String DEFAULT_DICTIONARY_VALUE = "无";

    /**
     * 系统变量业务逻辑
     */
    private static ISystemVariableService systemVariableService;

    /**
     * 设置系统变量业务逻辑
     *
     * @param systemVariableService 系统变量业务逻辑类
     */
    @Autowired
    private void setSystemVariableService(ISystemVariableService systemVariableService) {
        VariableUtils.systemVariableService = systemVariableService;
    }

    /**
     * 通过{@link ValueEnum} 接口子类 class 获取数据字典集合
     *
     * @param enumClass 枚举 class
     * @param ignore    要忽略的值
     *
     * @return key value 数据字典 Map 集合
     */
    public static List<Map<String, Object>> get(Class<? extends Enum<? extends ValueEnum<?>>> enumClass, Object... ignore) {

        List<Map<String, Object>> result = Lists.newArrayList();
        Enum<? extends ValueEnum<?>>[] values = enumClass.getEnumConstants();

        for (Enum<? extends ValueEnum<?>> o : values) {
            ValueEnum<?> ve = (ValueEnum<?>) o;
            Object value = ve.getValue();

            if (!ArrayUtils.contains(ignore, value)) {
                Map<String, Object> dictionary = Maps.newHashMap();

                dictionary.put(DEFAULT_VALUE_NAME, value);
                dictionary.put(DEFAULT_KEY_NAME, ve.getName());

                result.add(dictionary);
            }

        }

        return result;
    }

    /**
     * 通过字典类别获取数据字典集合
     *
     * @param code   字典类别代码
     * @param ignore 要忽略的值
     *
     * @return key value 数据字典 Map 集合
     */
    public static List<Map<String,Object>> get(String code, Object... ignore) {
        List<Map<String,Object>> result = Lists.newArrayList();
        List<DataDictionary> dataDictionaries = systemVariableService.getDataDictionaries(code);

        for (DataDictionary data : dataDictionaries) {
            String value = data.getValue();
            if (ArrayUtils.contains(ignore, value)) {
                continue;
            }
            String type = data.getType();

            Map<String, Object> dictionary = Maps.newHashMap();

            dictionary.put(DEFAULT_VALUE_NAME, data.getName());
            dictionary.put(DEFAULT_KEY_NAME, cast(value, type));
            result.add(dictionary);

        }

        return result;
    }

    public static DataDictionary getDataDictionary(String code) {
        return systemVariableService.getDataDictionary(code);
    }

    /**
     * 通过字典枚举获取字典名称
     *
     * @param enumClass 字典枚举class
     * @param value 值
     *
     * @return String
     */
    public static String getName(Class<? extends Enum<? extends ValueEnum<?>>> enumClass,Object value) {

        if (value == null || enumClass == null) {
            return DEFAULT_DICTIONARY_VALUE;
        }

        if (value instanceof String && StringUtils.isEmpty(value.toString())) {
            return DEFAULT_DICTIONARY_VALUE;
        }

        Enum<?>[] values = enumClass.getEnumConstants();

        for (Enum<?> o : values) {
            ValueEnum<?> ve = (ValueEnum<?>) o;

            if (StringUtils.equals(ve.getValue().toString(), value.toString())) {
                return ve.getName();
            }

        }

        return DEFAULT_DICTIONARY_VALUE;
    }

    /**
     * 获取数据字典名称
     *
     * @param code 类别代码
     * @param value 值
     *
     * @return String
     */
    public static String getName(String code,Object value) {

        if (value == null || code == null) {
            return "";
        }

        if (value instanceof String && StringUtils.isEmpty(value.toString())) {
            return "";
        }

        List<DataDictionary> dataDictionaries = systemVariableService.getDataDictionaries(code);

        for (DataDictionary dataDictionary : dataDictionaries) {
            if (StringUtils.equals(dataDictionary.getValue(), value.toString())) {
                return dataDictionary.getName();
            }
        }
        return "";
    }

    /**
     * 将值转换为对应的类型
     *
     * @param value 值
     *
     * @return 转换好的值
     */
    public static <T> T cast(Object value) {
        return value == null ? null : (T) value;
    }

    /**
     * 将值转换为对应的类型
     *
     * @param value     值
     * @param typeClass 类型 Class
     *
     * @return 转换好的值
     */
    public static <T> T cast(Object value, Class<T> typeClass) {
        return (T) (value == null ? null : ConvertUtils.convert(value, typeClass));
    }

    /**
     * 将 String 类型的值转换为对应的类型
     *
     * @param value     值
     * @param className 类名称,参考{@link FieldType}
     *
     * @return 转换好的值
     */
    public static <T> T cast(String value, String className) {
        Class<?> type = FieldType.valueOf(className).getValue();
        return (T) cast(value, type);
    }


    public static BaseUserDetail getPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Assert.notNull(authentication, "未认证");
        return (BaseUserDetail) authentication.getPrincipal();
    }

    /**
     * 获取Accesstoken
     * @return
     */
    public static String getAccessToken(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Assert.notNull(authentication, "未认证");
        return ((OAuth2AuthenticationDetails)authentication.getDetails()).getTokenValue();
    }

}
