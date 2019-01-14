package com.funcas.pboot.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * date:  2016/5/2 20:32
 *
 * @author funcas
 * @version 1.0
 */
public class FastJsonUtil {

    private static Logger log = LoggerFactory.getLogger(FastJsonUtil.class);

    /**
     * 用fastjson 将json字符串解析为一个 JavaBean
     *
     * @param jsonString
     * @param cls
     * @return
     */
    public static <T> T getBean(String jsonString, Class<T> cls) {
        T t = null;
        try {
            t = JSON.parseObject(jsonString, cls);
        } catch (Exception e) {
            log.error("",e);
        }
        return t;
    }

    /**
     * 用fastjson 将json字符串 解析成为一个 List<JavaBean> 及 List<String>
     *
     * @param jsonString
     * @param cls
     * @return
     */
    public static <T> List<T> getListBeans(String jsonString, Class<T> cls) {
        List<T> list = new ArrayList<>();
        try {
            list = JSON.parseArray(jsonString, cls);
        } catch (Exception e) {
            log.error(null,e);
        }
        return list;
    }

    /**
     * 用fastjson 将jsonString 解析成 List<Map<String,Object>>
     *
     * @param jsonString
     * @return
     */
    public static List<Map<String, Object>> getListMap(String jsonString) {
        List<Map<String, Object>> list = new ArrayList<>();
        try {
            // 两种写法
            // list = JSON.parseObject(jsonString, new
            // TypeReference<List<Map<String, Object>>>(){}.getType());
            list = JSON.parseObject(jsonString,
                    new TypeReference<List<Map<String, Object>>>() {
                    });
        } catch (Exception e) {
           log.error(null,e);
        }
        return list;
    }

    /**
     * object 转 json字符串
     * @param o
     * @return
     */
    public static String toJson(Object o) {
        return JSON.toJSONString(o);
    }
}
