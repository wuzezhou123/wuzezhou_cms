package com.wzz.cms.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
/**
 * 
 * @ClassName: UserInterceptor 
 * @Description: 个人中心的拦截器
 * @author: charles
 * @date: 2020年3月13日 上午8:39:32
 */
public class AdminInterceptor extends HandlerInterceptorAdapter {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		//如果注册用户已经登录，则不拦截
		HttpSession session = request.getSession();
		Object obj = session.getAttribute("admin");
		if(null!=obj)
		 return true;//不拦截
		  //response.sendRedirect("/passport/login");
		request.setAttribute("msg", "请登录后重试");
		request.getRequestDispatcher("/WEB-INF/view/passport/adminLogin.jsp").forward(request, response);
		return false;//拦截
	}
	
}
