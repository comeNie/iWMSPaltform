package com.huamengtong.wms.app.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;

/**
 * Created by Edwin on 2016/12/2.
 */
public interface ShiroCacheManager {

    <K, V> Cache<K, V> getCache(String name) throws CacheException ;

}
