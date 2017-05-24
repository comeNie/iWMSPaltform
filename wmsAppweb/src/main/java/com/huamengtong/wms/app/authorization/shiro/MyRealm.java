package com.huamengtong.wms.app.authorization.shiro;

import com.huamengtong.wms.constants.GlobalConstants;
import com.huamengtong.wms.core.execption.BusinessException;
import com.huamengtong.wms.core.formwork.db.domain.CurrentUserEntity;
import com.huamengtong.wms.entity.main.TWmsUserEntity;
import com.huamengtong.wms.main.service.IUserService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.InvocationTargetException;

/**
 * 自定义的指定Shiro验证用户登录的类
 * 1.doGetAuthenticationInfo 身份认证
 * 2.doGetAuthorizationInfo 权限认证
 */
public class MyRealm extends AuthorizingRealm {

    private static final Logger log = LoggerFactory.getLogger(MyRealm.class);

    @Autowired
    private IUserService userService;

    /**
     * 授权服务,在配有缓存的情况下，只加载一次
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals){

        String loginName =(String) principals.fromRealm(getName()).iterator().next();
        log.info("user {} login to initialize authorized.",loginName);
        //SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        //authorizationInfo.setRoles(userService.findRoles(loginName));
        //authorizationInfo.setStringPermissions(userService.findPermissions(loginName));
        return null;
    }


    /**
     * 认证服务
     * 获取身份验证信息,验证当前登录的Subject
     * @see :本例中该方法的调用时机为LoginController.login()方法中执行Subject.login()时
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {

        log.info("User Login to initialize authenticationToken......");

        CustomUserToken token = (CustomUserToken)authcToken;
        String userName = token.getPrincipal().toString();

        TWmsUserEntity userEntity = userService.findByUserName(userName);
        if(userEntity == null){
            throw new UnknownAccountException("Did not find the account!");
        }
        if("0".equals(String.valueOf(userEntity.getIsActive()))|| "1".equals(String.valueOf(userEntity.getIsDel()))){
            log.info("Did not find the account or the account is not activated!");
            throw new UnknownAccountException("Did not find the account or the account is not activated!"); //未找到账户或者账户未激活
        }
        if("0".equals(String.valueOf(userEntity.getIsActive()))) {
            log.info("Account is locked!");
            throw new LockedAccountException("Account is locked"); //帐号锁定
        }

        //将用户属性拷贝给currenUserEntity对象
        CurrentUserEntity currenUserEntity = new CurrentUserEntity();
        try {
            BeanUtils.copyProperties(currenUserEntity, userEntity);
        } catch (IllegalAccessException e) {
            throw  new BusinessException("User login authentication failed!" + e );
        } catch (InvocationTargetException e) {
            throw  new BusinessException("User login authentication failed!" + e );
        }
        //生成AuthenticationInfo信息交给间接父类AuthenticatingRealm使用CredentialsMatcher进行密码匹配,可自定义实现
        AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(currenUserEntity.getUserName(), userEntity.getPassword(),this.getName());

        // 把账号信息放到Session中，并更新缓存,用于会话管理
        this.shiroSession(GlobalConstants.SESSION_KEY,currenUserEntity);

        return authcInfo;
    }


    /**
     * 将一些数据放到Session中,以便于其它地方使用
     */
    private void shiroSession(Object key, Object value){
        Session session = SecurityUtils.getSubject().getSession();
        if(null != session){
            session.setAttribute(key, value);
            log.info("sessionId {} name {} update.", session, session.getClass().getName());
        }
        log.info("default session timeout is [" + session.getTimeout() + "]毫秒");
    }


    /**
     * 更新用户授权信息缓存.
     */
    public void clearCachedAuthorizationInfo(String principal) {
        SimplePrincipalCollection principals = new SimplePrincipalCollection(principal, getName());
        clearCachedAuthorizationInfo(principals);
    }

    /**
     * 清除所有用户授权信息缓存.
     */
    public void clearAllCachedAuthorizationInfo() {
        Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
        if (cache != null) {
            for (Object key : cache.keys()) {
                cache.remove(key);
            }
        }
    }

}