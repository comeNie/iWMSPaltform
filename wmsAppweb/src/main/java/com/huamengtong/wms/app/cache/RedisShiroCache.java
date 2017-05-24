package com.huamengtong.wms.app.cache;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.huamengtong.wms.core.redis.RedisTemplate;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.Set;

/**
 * Created by Edwin on 2016/12/1.
 */
public class RedisShiroCache <K, V> implements Cache<K, V> {

    RedisTemplate redisTemplate;

    private static final String keyPrefix = "shiro_redis_cache:";

    /***
     * session 在Cache过期时间是28分钟28*60,本地Cache过期时间需要小于redis过期时间
     */
    private static int expireTime = 1680;

    private String name;

    public RedisShiroCache(String name, RedisTemplate redisTemplate) {
        this.name = name;
        this.redisTemplate = redisTemplate;
    }

    public String getName() {
        if (StringUtils.isEmpty(name))
            return "";
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public V get(K key) throws CacheException {
        String kString = redisTemplate.get(serializationCache(getCacheKey(key)));
        if(StringUtils.isNotEmpty(kString)){
            return (V) deserializationCache(kString);
        }
        return null;
    }

    @Override
    public V put(K key, V value) throws CacheException {
        V previos = get(key);
        redisTemplate.set(serializationCache(getCacheKey(key)),serializationCache(value),expireTime);
        return previos;
    }

    @Override
    public V remove(K key) throws CacheException {
        V previos = get(key);
        redisTemplate.del(serializationCache(getCacheKey(key)));
        return previos;
    }

    @Override
    public void clear() throws CacheException {

    }

    @Override
    public int size() {
        if (keys() == null)
            return 0;
        return keys().size();
    }

    @Override
    public Set<K> keys() {
        return null;
    }

    @Override
    public Collection<V> values() {
        return null;
    }

    private String getCacheKey(Object key) {
        return  keyPrefix + getName() + ":" + key;
    }

    private String serializationCache(Object object){
        Kryo kryo = new Kryo();
        kryo.setReferences(false);
        kryo.setRegistrationRequired(false);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Output output = new Output(outputStream);
        kryo.writeObject(output, object);
        byte[] bytes = output.toBytes();
        output.flush();
        output.close();
        String kString = "";
        try {
            kString= new String(bytes ,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return kString;
    }

    private K deserializationCache(String kString){
        Kryo kryo = new Kryo();
        kryo.setReferences(false);
        byte[] bytes = new byte[0];
        try {
            bytes = kString.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        Input input = new Input(inputStream);
        K k = (K) kryo.readClassAndObject(input);
        input.close();
        return k;
    }
}