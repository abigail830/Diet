package com.diet.admin.caches;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author LiuYu
 * @date 2018/5/9
 */
@Component
@ConditionalOnProperty(name = "spring.cache.type", havingValue = "redis")
public class RedisServiceImpl implements BaseCacheService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public Object get(String key) {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        return valueOperations.get(key);
    }

    @Override
    public <T> T get(String key, Class<T> clazz) {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        return JSON.parseObject(JSON.toJSONString(valueOperations.get(key)), clazz);
    }

    @Override
    public <T> List<T> getListByKeyPattern(String pattern, Class<T> clazz) {
        Set<String> sets = redisTemplate.keys(pattern);
        List<T> result = new ArrayList<>();
        if (!sets.isEmpty()) {
            for (String str : sets) {
                result.add(get(str, clazz));
            }
        }
        return result;
    }

    @Override
    public void put(String key, Object value) {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, value, 2 * 60 * 60, TimeUnit.SECONDS);
    }

    @Override
    public void put(String key, Object value, int timeToLiveSeconds) {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, value, timeToLiveSeconds, TimeUnit.SECONDS);
    }

    @Override
    public boolean remove(String key) {
        return redisTemplate.delete(key);
    }
}
