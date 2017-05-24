package com.huamengtong.wms.app.controller.login;

import com.huamengtong.wms.app.cache.RedisSessionDao;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * Created by Edwin on 2016/11/30.
 */
@RequestMapping("/session")
@RestController
public class SessionController {

    @Autowired
    private RedisSessionDao redisSessionDao;

    @RequestMapping(value = "/read", method = RequestMethod.GET)
    public Session readSession(HttpSession session) {
        return redisSessionDao.readSession(session.getId());
    }

}
