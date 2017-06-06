package com.cmsz.ircn.listener;

import javax.annotation.Resource;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.cmsz.ircn.bean.Setting;
import com.cmsz.ircn.log.monitor.MonitorableLog;

@Component
public class InitServiceListener implements InitializingBean, ApplicationContextAware {

	@Resource
	private MonitorableLog log;
	@Resource
	private Setting setting;

	private ApplicationContext applicationContext;

	private boolean isFirstLoad = true;

	@Override
	public void afterPropertiesSet() throws Exception {

	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	/**
	 * 重新加载初始化参数
	 */
	public void reloadAll() {
		isFirstLoad = false;
		this.setApplicationContext(applicationContext);
	}

}
