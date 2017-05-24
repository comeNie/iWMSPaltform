package com.huamengtong.wms.app.authorization.shiro;

import com.huamengtong.wms.app.cache.RedisSessionDao;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Edwin on 2016/11/30.
 */
public class ShiroSessionListener implements SessionListener {

    private static final Logger logger = LoggerFactory.getLogger(ShiroSessionListener.class);

    @Autowired
    RedisSessionDao redisSessionDao;

    @Override
    public void onStart(Session session) {
        // 会话创建时触发
        logger.info("ShiroSessionListener session {} 被监听", session.getId());
    }

    @Override
    public void onStop(Session session) {
        redisSessionDao.delete(session);
        // 会话被停止时触发
        logger.info("ShiroSessionListener session {} 被销毁", session.getId());
    }

    @Override
    public void onExpiration(Session session) {
        redisSessionDao.delete(session);
        //会话过期时触发
        logger.info("ShiroSessionListener session {} 过期", session.getId());
    }
}
