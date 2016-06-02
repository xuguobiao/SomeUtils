package com.kido.someutils;

import java.lang.reflect.Constructor;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 对象操作工厂类
 *
 * @author Kido
 * @email everlastxgb@gmail.com
 * @create_time 2016/5/31 11:13
 */

public class ClassFactory {

    public static Map<Class<?>, Object> sInstanceMap = Collections.synchronizedMap(new HashMap<Class<?>, Object>());

    /**
     * Just new instance without saving into static map
     *
     * @return return null if failed to new instance
     */
    public static <T> T newInstance(Class<T> clazz) {
        try {
            Constructor<?> ctr = clazz.getDeclaredConstructor();
            ctr.setAccessible(true);
            return (T) ctr.newInstance(); // 根据无参数构造函数新建对象
        } catch (Exception e) {
        }

        return null;

    }

    /**
     * 若map中已有该clazz对应的实例则返回，否则新建对象保存到map并返回该对象。<br>
     * 注：当前锁的粒度是clazz级别，请避免在同一个类的类成员变量或构造函数中调用该方法去创建该类，否则会造成死锁。<br>
     * Bad usage: <br>
     * class A{
     * <br>
     * A a = ServiceFactory.create(A.class);
     * <br>
     * }
     * <br>
     */
    public static <T> T create(Class<T> clazz) {
        synchronized (clazz) {
            if (!sInstanceMap.containsKey(clazz)) {
                T object = newInstance(clazz);
                if (object != null) {
                    sInstanceMap.put(clazz, object);
                }
            }
        }
        return (T) sInstanceMap.get(clazz);
    }

//    public synchronized static <T> T create(Class<T> clazz, boolean withSaving) {
//        T object = newInstance(clazz);
//        if (withSaving) {
//            sInstanceMap.put(clazz, object);
//        }
//
//        return object;
//
//    }

    /**
     * Removes element from static map by clazz
     */
    public static <T> T remove(Class<T> clazz) {
        return (T) sInstanceMap.remove(clazz);

    }

    /**
     * Removes all elements from the static Map, leaving it empty.
     */
    public static void clear() {
        sInstanceMap.clear();
    }


}
