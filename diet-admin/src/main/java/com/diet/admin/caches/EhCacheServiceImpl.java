package com.diet.admin.caches;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import net.sf.ehcache.search.Attribute;
import net.sf.ehcache.search.Query;
import net.sf.ehcache.search.Result;
import net.sf.ehcache.search.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LiuYu
 * @date 2018/1/7
 */
@Component
@ConditionalOnProperty(name = "spring.cache.type", havingValue = "ehcache")
public class EhCacheServiceImpl implements BaseCacheService {

    @Autowired
    private EhCacheCacheManager ehCacheCacheManager;

    @Override
    public Object get(String key) {
        Cache cache = ehCacheCacheManager.getCacheManager().getCache(DEFAULT_CACHE);
        if (cache != null) {
            Element element = cache.get(key);
            if (element != null) {
                return element.getObjectValue();
            }
        }
        return null;
    }

    @Override
    public <T> T get(String key, Class<T> clazz) {
        Cache cache = ehCacheCacheManager.getCacheManager().getCache(DEFAULT_CACHE);
        if (cache != null) {
            Element element = cache.get(key);
            if (element != null) {
                Object value = element.getObjectValue();
                return JSON.parseObject(JSON.toJSONString(value), clazz);
            }
        }
        return null;
    }

    @Override
    public <T> List<T> getListByKeyPattern(String pattern, Class<T> clazz) {
        Cache cache = ehCacheCacheManager.getCacheManager().getCache(DEFAULT_CACHE);
        if (cache != null) {
            Query query = cache.createQuery();
            query.includeKeys();
            query.includeValues();
            query.addCriteria(Query.KEY.ilike(pattern));
            Results results = query.execute();
            // 获取Results中包含的所有的Result对象
            List<Result> resultList = results.all();
            if(!resultList.isEmpty()) {
                List<T> list = new ArrayList();
                for (Result result : resultList) {
                    list.add(JSON.parseObject(String.valueOf(result.getValue()), clazz));
                }
                return list;
            }
        }
        return new ArrayList<>();
    }

    @Override
    public void put(String key, Object value) {
        Cache cache = ehCacheCacheManager.getCacheManager().getCache(DEFAULT_CACHE);
        if (cache != null) {
            cache.put(new Element(key, value, 0, 2 * 60 * 60));
        }
    }

    @Override
    public void put(String key, Object value, int timeToLiveSeconds) {
        Cache cache = ehCacheCacheManager.getCacheManager().getCache(DEFAULT_CACHE);
        if (cache != null) {
            cache.put(new Element(key, value, 0, timeToLiveSeconds));
        }
    }

    @Override
    public boolean remove(String key) {
        Cache cache = ehCacheCacheManager.getCacheManager().getCache(DEFAULT_CACHE);
        if (cache != null) {
            return cache.remove(key);
        }
        return false;
    }
}
