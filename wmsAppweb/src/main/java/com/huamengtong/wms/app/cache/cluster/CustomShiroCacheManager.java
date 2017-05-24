package com.huamengtong.wms.app.cache.cluster;

import com.huamengtong.wms.app.cache.RedisShiroCacheManager;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;

/**
 * Created by Edwin on 2016/12/2.
 */
public class CustomShiroCacheManager implements CacheManager {

    private RedisShiroCacheManager redisShiroCacheManager;

    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        return redisShiroCacheManager.getCache(name);
    }

    public RedisShiroCacheManager getRedisShiroCacheManager() {
        return redisShiroCacheManager;
    }

    public void setRedisShiroCacheManager(RedisShiroCacheManager redisShiroCacheManager) {
        this.redisShiroCacheManager = redisShiroCacheManager;
    }
}
