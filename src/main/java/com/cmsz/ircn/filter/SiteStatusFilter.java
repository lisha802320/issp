/*
 * Copyright 2005-2013 CMSZ. All rights reserved.
 * Support: http://www.CMSZ
 * License: http://www.CMSZ/license
 */
package com.cmsz.ircn.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import com.cmsz.ircn.bean.Setting;

/**
 * Filter - 网站状态
 * 
 * @author teamsun++ Team
 * @version 3.0
 */
@Component("siteStatusFilter")
public class SiteStatusFilter extends OncePerRequestFilter {

	/** 默认忽略URL */
	private static final String[] DEFAULT_IGNORE_URL_PATTERNS = new String[] { "/admin/**" };

	/** 默认重定向URL */
	private static final String DEFAULT_REDIRECT_URL = "/common/site_close.do";

	/** antPathMatcher */
	private static AntPathMatcher antPathMatcher = new AntPathMatcher();

	/** 忽略URL */
	private String[] ignoreUrlPatterns = DEFAULT_IGNORE_URL_PATTERNS;

	/** 重定向URL */
	private String redirectUrl = DEFAULT_REDIRECT_URL;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		if (true) {
			filterChain.doFilter(request, response);
		} else {
			String path = request.getServletPath();
			if (path.equals(redirectUrl)) {
				filterChain.doFilter(request, response);
				return;
			}
			if (ignoreUrlPatterns != null) {
				for (String ignoreUrlPattern : ignoreUrlPatterns) {
					if (antPathMatcher.match(ignoreUrlPattern, path)) {
						filterChain.doFilter(request, response);
						return;
					}
				}
			}
			response.sendRedirect(request.getContextPath() + redirectUrl);
		}
	}

	/**
	 * 获取忽略URL
	 * 
	 * @return 忽略URL
	 */
	public String[] getIgnoreUrlPatterns() {
		return ignoreUrlPatterns;
	}

	/**
	 * 设置忽略URL
	 * 
	 * @param ignoreUrlPatterns
	 *            忽略URL
	 */
	public void setIgnoreUrlPatterns(String[] ignoreUrlPatterns) {
		this.ignoreUrlPatterns = ignoreUrlPatterns;
	}

	/**
	 * 获取重定向URL
	 * 
	 * @return 重定向URL
	 */
	public String getRedirectUrl() {
		return redirectUrl;
	}

	/**
	 * 设置重定向URL
	 * 
	 * @param redirectUrl
	 *            重定向URL
	 */
	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

}