package com.diet.admin.caches;

import java.util.List;

/**
 * @author LiuYu
 * @date 2018/1/7
 */
public interface BaseCacheService {
    String DEFAULT_CACHE = "defaultCache";

    /**
     * 获取缓存信息
     * @param key
     * @return
     */
    Object get(String key);

    /**
     * 获取缓存信息，返回指定类型对象
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    <T> T get(String key, Class<T> clazz);

    /**
     * 模糊查询
     * @param key
     * @return
     */
    <T> List<T> getListByKeyPattern(String key, Class<T> clazz);

    /**
     *  设置缓存信息, 默认两小时
     * @param key
     * @param value
     */
    void put(String key, Object value);

    /**
     *  设置缓存信息，超时
     * @param key
     * @param value
     * @param timeToLiveSeconds 存活时间，单位：秒
     */
    void put(String key, Object value, int timeToLiveSeconds);

    /**
     * 移除缓存信息
     * @param key
     * @return
     */
    boolean remove(String key);
}
