package com.example.Flower.session;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class SessionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 세션을 확인하고 관련 정보를 모델에 추가
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("userId") != null) {
            request.setAttribute("loggedIn", true);
            request.setAttribute("loggedInUserId", (String) session.getAttribute("userId"));
        } else {
            request.setAttribute("loggedIn", false);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 후처리 작업이 필요하지 않을 경우 빈 구현
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 후처리 작업이 필요하지 않을 경우 빈 구현
    }
}