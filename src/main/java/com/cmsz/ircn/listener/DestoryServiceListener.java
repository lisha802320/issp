package com.cmsz.ircn.listener;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Component;

@Component
public class DestoryServiceListener implements DisposableBean {

	@Override
	public void destroy() throws Exception {
		System.out.println("contextDestroyed shutdownHook");
		System.out.println("contextDestroyed shutdownHook completed");
	}

}
