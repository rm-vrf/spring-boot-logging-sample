package com.mydomain.app;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

public class MyInterceptor implements HandlerInterceptor {
	
	private static final Logger LOG = LoggerFactory.getLogger(MyInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		// Add userName in log
		HttpSession session = request.getSession();
		if (session != null) {
			Object obj = session.getAttribute("SPRING_SECURITY_CONTEXT");
			if (obj != null) {
				SecurityContext context = (SecurityContext)obj;
				String userName = context.getAuthentication().getName();
				LOG.info("login user name: {}", userName);
				
				MDC.put("userName", userName);
			}
		}
		
		// Generate 'x-request-id' in HTTP header, and put it in log
		String requestId = request.getHeader("x-request-id");
		if (StringUtils.isEmpty(requestId)) {
			requestId = UUID.randomUUID().toString();
		}
		response.addHeader("x-request-id", requestId);
		MDC.put("x-request-id", requestId);

		return true;
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
		MDC.remove("userName");
		MDC.remove("x-request-id");
	}
}
