package com.huamengtong.wms.app.filter;

import com.huamengtong.wms.constants.GlobalConstants;
import com.huamengtong.wms.inner.SystemConfigUtil;
import com.huamengtong.wms.utils.StringPool;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Pattern;

/**
 * Created by Edwin on 2016/12/2.
 */
public class SessionFilter implements Filter {

    private static final Logger _log = LoggerFactory.getLogger(SessionFilter.class);
    private Pattern exceptUrlPattern; //过滤的URL
    private String ignoreUrlPattern; //过滤静态文件

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        _log.info(SessionFilter.class.getName() + " init .");
        String exceptUrlRegex = filterConfig.getInitParameter("exceptUrlRegex");
        if (StringUtils.isNotBlank(exceptUrlRegex)) {
            exceptUrlPattern = Pattern.compile(exceptUrlRegex);
        }
        ignoreUrlPattern = filterConfig.getInitParameter("ignoreUrlPattern");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse res = (HttpServletResponse)response;
        //基于http协议的servlet
        String requestURI = req.getRequestURI();
        String path = req.getContextPath() + "/";

        //过滤不需要拦截资源文件和请求路径
        if (requestURI.endsWith(path)|| exceptUrlPattern.matcher(requestURI).matches()) {
            filterChain.doFilter(req, res);
            return;
        }
        if(isRequestUrlExcluded(req)){
            filterChain.doFilter(req, res);
            return;
        }
        //如果session为空,跳转到登录首页
        Session session = SecurityUtils.getSubject().getSession();
        if(session.getAttribute(GlobalConstants.SESSION_KEY) == null){
            SecurityUtils.getSubject().logout();
            String redirect = SystemConfigUtil.get("local.basePath") + "/login" ;
            res.sendRedirect(redirect);
            return;
        }
        filterChain.doFilter(req, res);
    }

    @Override
    public void destroy() {

    }

    /***
     * 静态资源过滤
     * @param request
     * @return
     */
    protected boolean isRequestUrlExcluded(final HttpServletRequest request) {
        boolean flag = false;
        String requestPath = request.getRequestURI();
        PathMatcher matcher = new AntPathMatcher();
        //需要过滤的目录
        String[] ignoreArray = ignoreUrlPattern.split(StringPool.COMMA);
        for(String ignoreUrlPattern : ignoreArray){
            if(matcher.match(ignoreUrlPattern, requestPath)){
                flag = true;
                break;
            }
        }
        return flag;
    }
}
