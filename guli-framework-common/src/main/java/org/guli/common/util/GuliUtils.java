package org.guli.common.util;

import org.springframework.beans.BeanUtils;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GuliUtils {

    public static <S, T> T copyPropertiesPlus(S source, T target) {
        BeanUtils.copyProperties(source, target);
        return target;
    }

    /**
     * bean dto map.
     *
     * @param bean
     * @param <T>
     * @return
     */
    public static <T> Map<String, Object> beanToMap(T bean) {

        Assert.notNull(bean, "参数不能为空.");

        Map<String, Object> map = new HashMap<>();
        BeanMap beanMap = BeanMap.create(bean);
        beanMap.keySet().forEach(key -> {
            map.put(key + "", beanMap.get(key));
        });
        return map;
    }

    /**
     * map dto bean.
     *
     * @param map
     * @param bean
     * @param <T>
     * @return
     */
    public static <T> T mapToBean(Map<String, Object> map, T bean) {

        BeanMap.create(bean).putAll(map);
        return bean;
    }

    /**
     * List<T> dto List<Map<String, Object>>
     *
     * @param objList
     * @param <T>
     * @return
     */
    public static <T> List<Map<String, Object>> objectsToMaps(List<T> objList) {

        Assert.notEmpty(objList, "参数不能为null.");

        List<Map<String, Object>> list = new ArrayList<>();
        objList.forEach(obj -> list.add(beanToMap(obj)));
        return list;
    }

    /**
     * 将List<Map<String,Object>>转换为List<T>
     *
     * @param maps
     * @param clazz
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @author xiehdsoo
     */
    public static <T> List<T> mapsToObjects(List<Map<String, Object>> maps, Class<T> clazz)
            throws InstantiationException, IllegalAccessException {
        List<T> list = new ArrayList<>();
        if (maps != null && maps.size() > 0) {
            Map<String, Object> map = null;
            T bean = null;
            for (int i = 0, size = maps.size(); i < size; i++) {
                map = maps.get(i);
                bean = clazz.newInstance();
                mapToBean(map, bean);
                list.add(bean);
            }
        }
        return list;
    }

}
