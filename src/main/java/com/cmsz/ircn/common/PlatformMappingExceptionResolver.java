package com.cmsz.ircn.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

public class PlatformMappingExceptionResolver extends SimpleMappingExceptionResolver {
	protected static final Logger logger = LoggerFactory
			.getLogger(PlatformMappingExceptionResolver.class);
	private String defaultErrorView;
	
	@Override
	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		logger.error("",ex);
		return getModelAndView(defaultErrorView, ex, request);
		
	}

	public String getDefaultErrorView() {
		return defaultErrorView;
	}

	public void setDefaultErrorView(String defaultErrorView) {
		this.defaultErrorView = defaultErrorView;
	}

	
}