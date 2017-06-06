package com.cmsz.ircn.interceptor;

import java.util.concurrent.Callable;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.async.CallableProcessingInterceptor;

import com.cmsz.ircn.log.monitor.MonitorableLog;

@Component
public class MyCallableInterceptor implements CallableProcessingInterceptor {
	@Resource
	private MonitorableLog log;

	@Override
	public <T> void afterCompletion(NativeWebRequest request, Callable<T> task) throws Exception {
	}

	@Override
	public <T> void beforeConcurrentHandling(NativeWebRequest request, Callable<T> task) throws Exception {
	}

	@Override
	public <T> Object handleTimeout(NativeWebRequest request, Callable<T> task) throws Exception {
		HttpServletResponse servletResponse = request.getNativeResponse(HttpServletResponse.class);
		if (!servletResponse.isCommitted()) {
			servletResponse.setContentType("text/plain;charset=utf-8");
			servletResponse.getWriter().write("服务超时");
			servletResponse.getWriter().close();
		}
		log.debug("处理超时");
		return null;
	}

	@Override
	public <T> void postProcess(NativeWebRequest request, Callable<T> task, Object obj) throws Exception {
		log.debug("应答内容：{0}", obj);
	}

	@Override
	public <T> void preProcess(NativeWebRequest request, Callable<T> task) throws Exception {
	}

}
