package com.huamengtong.wms.app.cache;

import com.huamengtong.wms.core.redis.RedisTemplate;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Edwin on 2016/12/2.
 */
public class RedisShiroCacheManager implements ShiroCacheManager {

    private static final Logger logger = LoggerFactory.getLogger(RedisShiroCacheManager.class);

    private RedisTemplate redisTemplate;

    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        logger.info(" Get the "+ name +" redisCache instance .");
        return new RedisShiroCache<K, V>(name,redisTemplate);
    }

    public RedisTemplate getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}
