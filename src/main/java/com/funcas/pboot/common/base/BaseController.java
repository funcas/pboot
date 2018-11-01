package com.funcas.pboot.common.base;

import com.funcas.pboot.common.ApiResult;
import com.funcas.pboot.common.enumeration.ApiResultEnum;
import com.funcas.pboot.common.enumeration.ValueEnum;
import com.funcas.pboot.common.util.FantasyCollections;
import com.funcas.pboot.common.util.FastJsonUtil;
import org.springframework.beans.BeanUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * 基础Controller,提供多数常用方法,谁用谁知道
 * @author funcas(fss_34@163.com)
 * @version 1.0
 * @date 2016年07月25日
 */
public abstract class BaseController {

    /**
     * 快速构造HashMap
     * @param arrays
     * @param <K>
     * @param <V>
     * @return
     */
    public <K,V> Map<K,V> map(Object... arrays) {
        return FantasyCollections.map(arrays);
    }

    /**
     * 快速构造List
     * @param arrays
     * @param <T>
     * @return
     */
    public <T> List<T> list(T... arrays) {
        return FantasyCollections.list(arrays);
    }

    /**
     * 快速新建HashSet
     * @param arrays
     * @param <T>
     * @return
     */
    public <T> Set<T> newHashSet(T... arrays) {
        return FantasyCollections.newHashSet(arrays);
    }

    /**
     * Array转成HashSet
     * @param array
     * @param <T>
     * @return
     */
    public <T> Set<T> hashSet(T[] array) {
        return FantasyCollections.hashSet(array);
    }

    /**
     * Array转成ArrayList
     * @param array
     * @param <T>
     * @return
     */
    public <T> List<T> asList(T[] array){
        return FantasyCollections.asList(array);
    }

    public Pattern regEx(String reg) {
        return Pattern.compile(reg);
    }

    /**
     * 渲染json
     * @return
     */
    public ApiResult render(String code, String message, Object result){
        return ApiResult.builder().retCode(code).retMessage(message).result(result).build();
    }

    /**
     * 使用枚举传入retCode与retMessage
     * @param valueEnum
     * @param result
     * @return
     */
    public ApiResult render(ValueEnum valueEnum, Object result){
        return ApiResult.builder().apiResultEnum(valueEnum).result(result).build();
    }

    /**
     * 简单渲染成功信息
     * @param result
     * @return
     */
    public ApiResult success(Object result){
        return ApiResult.builder().apiResultEnum(ApiResultEnum.SUCCESS).result(result).build();
    }

    /**
     * 简单渲染通用失败信息
     * @param result
     * @return
     */
    public ApiResult failure(Object result){
        return ApiResult.builder().apiResultEnum(ApiResultEnum.SUCCESS).result(result).build();
    }

    /**
     * 设置session
     * @param request
     * @param key
     * @param value
     */
    public void session(HttpServletRequest request,String key,Object value){
        request.getSession(true).setAttribute(key,value);
    }

    /**
     * 获取session
     * @param request
     * @param key
     * @return
     */
    public Object session(HttpServletRequest request,String key){
        return request.getSession().getAttribute(key);
    }

    /**
     * json字条串转换成java bean列表
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> List<T> parseJson(String json,Class<T> clazz){
        return FastJsonUtil.getListBeans(json,clazz);
    }

    /**
     * json字符串转换成 map列表
     * @param json
     * @return
     */
    public List<Map<String,Object>> parseJson(String json){
        return FastJsonUtil.getListMap(json);
    }

    /**
     * json字符串转换成java bean
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T parseJsonMap(String json,Class<T> clazz){
        return FastJsonUtil.getBean(json,clazz);
    }

    /**
     * 获取当前时间
     * @return
     */
    public Date now(){
        return new Date();
    }

    /**
     * 获取当前时间戳
     * @return
     */
    public long currentTimeStamp(){
        return System.currentTimeMillis();
    }

    /**
     * 获取当前短时间戳(微信接口)
     * @return
     */
    public long currentTimeStampShort(){
        return System.currentTimeMillis()/1000;
    }

    /**
     * 判断是不是在微信浏览器中打开
     * @param request
     * @return
     */
    protected boolean isWeixinBrowser(HttpServletRequest request){
        String ua = request.getHeader("user-agent").toLowerCase();
        if (ua.indexOf("micromessenger") > 0) {// 是微信浏览器
            return true;
        }
        return false;
    }

    /**
     * 合并两个java bean
     * @param dest
     * @param origin
     */
    protected void merge(Object dest, Object origin) {
        BeanUtils.copyProperties(dest, origin);
    }
}
