package com.memo.Interceptor;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component // spring bean
public class Permissionlnterceptor implements HandlerInterceptor{
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws IOException {
		
		// 요청 URL PATH를 꺼낸다.
		String uri = request.getRequestURI();
		log.info("[@@@@@@@@@@@@@@@@ preHandle] uri:{}", uri);
		
		// 로그인 여부를 꺼낸다.
		HttpSession session =  request.getSession();
		Integer userId = (Integer)session.getAttribute("userId");
		
		// 만약 비로그인인데 && post로 시작하는 요청이 들어왔다? => 로그인 페이지로 이동, 컨트롤러 수행방지.
		if(userId == null && uri.startsWith("/post")) {
			response.sendRedirect("/user/sign-in-view");
			return false; // 원래 주소에 대한 컨트롤러 수행 하지 않음 X
		}
		
		// 로그인 상태인데 또 로그인으로 가려고 한다? => 넌 글 목록으로 이동해라! /user는 못가게 막아버리는 것임, 컨트롤러 수행방지.
		if(userId != null && uri.startsWith("/user")) {
			response.sendRedirect("/post/post-list-view");
			return false; // 원래 주소에 대한 컨트롤러 수행하지 않음 X
		}
		
		return true; // 컨트롤러 수행 true
	}
	
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView mav) {
		
		// view, model 객체가 있다는 건 html이 해석되기 전
		log.info("[$$$$$$$$$$$$$$$ postHandle]");
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			Exception ex) {
		
		// html이 완성된 상태
		log.info("[###### afterCompletion]");
	}
}
