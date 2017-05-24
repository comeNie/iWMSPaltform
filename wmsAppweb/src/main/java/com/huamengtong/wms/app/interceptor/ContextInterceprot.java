package com.huamengtong.wms.app.interceptor;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/***
 * spring拦截器,表单重复提交及长时间请求
 */
public class ContextInterceprot extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
		response.setContentType("application/json;charset=UTF-8");
		return tokenInterceprot(request, response, handler);
	}

	@Override
	public void postHandle(HttpServletRequest request,HttpServletResponse response, Object handler,
             ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		if (!request.getMethod().equalsIgnoreCase("get")) {
			request.getSession(false).removeAttribute("token");
		}
	}

	boolean tokenInterceprot(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {

		//拦截token用来处理请求重复提交
		if (!request.getMethod().equalsIgnoreCase("get")) {
			if (null != request.getSession(false).getAttribute("token")) {
				return false;
			}
			request.getSession(false).setAttribute("token", UUID.randomUUID().toString());
		}
		return super.preHandle(request,response,handler);
	}
}
