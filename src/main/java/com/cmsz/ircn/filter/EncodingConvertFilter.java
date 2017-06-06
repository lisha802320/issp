package com.cmsz.ircn.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

public class EncodingConvertFilter extends OncePerRequestFilter {
	/** 原编码格式：与web.xml文件中对应设置 */
	private String fromEncoding = "ISO-8859-1";
	/** 目标编码格式 ：与web.xml文件中对应设置 */
	private String toEncoding = "UTF-8";

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		for (Iterator<String[]> iterator = request.getParameterMap().values().iterator(); iterator.hasNext();) {
			String[] parames = iterator.next();
			for (int i = 0; i < parames.length; i++) {
				try {
					String encodeStr = new String(parames[i].getBytes(fromEncoding), toEncoding);
					parames[i] = encodeStr;
					System.out.println(encodeStr);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}
		filterChain.doFilter(request, response);
	}

	/**
	 * 获取原编码格式
	 * 
	 * @return 原编码格式
	 */
	public String getFromEncoding() {
		return fromEncoding;
	}

	/**
	 * 设置原编码格式
	 * 
	 * @param fromEncoding
	 *            原编码格式
	 */
	public void setFromEncoding(String fromEncoding) {
		this.fromEncoding = fromEncoding;
	}

	/**
	 * 获取目标编码格式
	 * 
	 * @return 目标编码格式
	 */
	public String getToEncoding() {
		return toEncoding;
	}

	/**
	 * 设置目标编码格式
	 * 
	 * @param toEncoding
	 *            目标编码格式
	 */
	public void setToEncoding(String toEncoding) {
		this.toEncoding = toEncoding;
	}
}
