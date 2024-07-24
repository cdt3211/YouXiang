package com.cupk.Interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class UserInterceptor implements HandlerInterceptor {

    @Override //用来在请求处理之前进行调用，只有返回true才会继续向下执行，返回false取消当前请求
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null && request.getSession().getAttribute("loggedInUser") != null) {
            modelAndView.addObject("loggedInUser", request.getSession().getAttribute("loggedInUser"));
        }
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取请求的URL
        String requestURI = request.getRequestURI();
        // 检查用户是否已登录，且请求的不是登录页面
        if (request.getSession().getAttribute("loggedInUser") == null) {
            // 用户未登录，移除用户信息
            request.getSession().removeAttribute("loggedInUser");
            // 用户未登录，且不是请求登录页面，重定向到登录页面
            response.sendRedirect("/pleaseLogin");
            return false; // 阻止请求继续处理
        }
        return true; // 用户已登录，或请求的是登录页面，继续处理请求
    }

}