package com.huamengtong.wms.app.cache;

import com.huamengtong.wms.core.redis.RedisTemplate;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Edwin on 2016/11/29.
 */
public class RedisSessionDao extends EnterpriseCacheSessionDAO {

    private static Logger logger = LoggerFactory.getLogger(RedisSessionDao.class);

    private RedisTemplate redisTemplate;

    /**
     * The Redis key prefix for the sessions
     */
    private static final String keyPrefix = "shiro_redis_session:";

    /***
     * session 在redis过期时间是30分钟30*60
     */
    private static int expireTime = 1800;

    /***
     * 创建Session
     * @param session
     * @return
     */
    @Override
    protected Serializable doCreate(Session session) {
        // 先保存到缓存中
        Serializable sessionId = super.doCreate(session);
        redisTemplate.set(getKey(sessionId),serialize(session),expireTime);
        logger.info("sessionId {} name {} 被创建.", sessionId, session.getClass().getName());
        return sessionId;
    }



    /***
     * 获取session
     * @param sessionId
     * @return
     */
    @Override
    protected Session doReadSession(Serializable sessionId) {
        // 先从缓存中获取session，如果没有再去redis中获取
        Session session = super.doReadSession(sessionId);
        if(session == null){
            byte[] bytes = redisTemplate.get(getKey(sessionId));
            if(ArrayUtils.isNotEmpty(bytes)) {
                session = deserialize(bytes);
            }
        }
        return session;
    }

    @Override
    public Session readSession(Serializable sessionId) throws UnknownSessionException {
        //从缓存中获取session
        Session session = this.doReadSession(sessionId);
        if (session == null) {
            logger.info("There is no session with id [" + sessionId + "]");
            throw new UnknownSessionException("There is no session with id [" + sessionId + "]");
        }
        return session;
    }

    /***
     * 更新session的最后一次访问时间
     * @param session
     * @throws UnknownSessionException
     */
    @Override
    public void update(Session session) throws UnknownSessionException {
        //如果会话过期/停止不需要更新
        if (session instanceof ValidatingSession && !((ValidatingSession) session).isValid()) {
            return;
        }
        super.doUpdate(session);
        redisTemplate.set(getKey(session.getId()),serialize(session),expireTime);
    }

    /***
     * 删除session,当会话过期/会话停止（如用户退出时）会调用
     * @param session
     */
    @Override
    public void delete(Session session) {
        if (session == null) {
            logger.error("session can not be null,delete failed.");
            return;
        }
        super.doDelete(session);
        redisTemplate.del(getKey(session.getId()));
        logger.info("sessionId {} name {} 被删除.", session, session.getClass().getName());
    }

    /***
     * 获取当前所有活跃用户
     * @return
     */
    @Override
    public Collection<Session> getActiveSessions() {
        Set<Session> sessions = new HashSet<Session>();
        Set<String> keys = redisTemplate.getAllKeys(keyPrefix + "*");
        if(CollectionUtils.isNotEmpty(keys)){
            for(String key: keys ){
                Session session = null;
                byte[] bytes = redisTemplate.get(getKey(key));
                session = deserialize(bytes);
                sessions.add(session);
            }
        }
        return sessions;
    }

    public RedisTemplate getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    private byte[] getKey(Serializable sessionId) {
        return (keyPrefix + sessionId.toString()).getBytes();
    }

    private byte[] serialize(Session session){
        return sessionToBytes(session);
    }

    private Session deserialize(byte[] bytes){
        return bytesToSession(bytes);
    }

    // 把session对象转化为byte保存到redis中
    private byte[] sessionToBytes(Session session){
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        byte[] bytes = null;
        try {
            ObjectOutputStream oo = new ObjectOutputStream(bo);
            oo.writeObject(session);
            bytes = bo.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    // 把byte还原为session
    private Session bytesToSession(byte[] bytes){
        ByteArrayInputStream bi = new ByteArrayInputStream(bytes);
        ObjectInputStream in;
        SimpleSession session = null;
        try {
            in = new ObjectInputStream(bi);
            session = (SimpleSession) in.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return session;
    }

}
