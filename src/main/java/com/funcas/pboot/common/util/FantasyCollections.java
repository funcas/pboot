package com.funcas.pboot.common.util;


import com.funcas.pboot.common.exception.ArgumentErrorException;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.*;

/**
 * @author funcas(fss_34@163.com)
 * @version 1.0
 * @date 2016年07月25日
 */
public class FantasyCollections {

    protected final static Map EMPTY_MAP = map();
    protected final static List EMPTY_LIST = list();

    public static <T> Set<T> newHashSet(T... arrays) {
        Set<T> sets = new HashSet<T>(arrays.length);
        for (T t : arrays) {
            sets.add(t);
        }
        return sets;
    }

    public static <K,V>  Map<K,V> map(Object... arrays) {
        Map<K,V> maps = Maps.newHashMap();
        if (arrays.length % 2 != 0) throw new ArgumentErrorException("arrays 长度 必须为偶数");
        for (int i = 0; i < arrays.length; i++) {
            maps.put((K)arrays[i], (V)arrays[++i]);
        }
        return maps;
    }


    public static <T> List<T> list(T... arrays) {
        return Lists.newArrayList(arrays);
    }

    public static <T> Set<T> hashSet(Object[] array) {
        Set sets = new HashSet();
        for (Object obj : array) {
            sets.add(obj);
        }
        return sets;
    }

    public static <K,V> Map<K,V> selectMap(Map map, K... keys) {
        Map<K,V> temp = Maps.newHashMap();
        for (K key : keys) {
            temp.put(key, (V)map.get(key));
        }
        return temp;
    }

    public static <T> List<T> projectionColumn(List<Map> maps, String column) {
        List<T> lists = new ArrayList<T>(maps.size());
        for (Map temp : maps) {
            lists.add((T) temp.get(column));
        }
        return lists;
    }


    public static <T> List<T> asList(T... a){
        return Arrays.asList(a);
    }

}
