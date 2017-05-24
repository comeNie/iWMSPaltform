package com.huamengtong.wms.core.context;

import com.huamengtong.wms.core.formwork.db.domain.CurrentUserEntity;

import javax.servlet.http.HttpSession;


public class CoreContext {

    private CurrentUserEntity currentUser;
    private HttpSession session;
    private String clientIp;

    public HttpSession getSession() {
        return session;
    }

    public void setSession(HttpSession session) {
        this.session = session;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public CurrentUserEntity getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(CurrentUserEntity currentUser) {
        this.currentUser = currentUser;
    }
}
