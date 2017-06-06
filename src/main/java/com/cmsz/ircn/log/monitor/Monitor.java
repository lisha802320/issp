package com.cmsz.ircn.log.monitor;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.stereotype.Component;

import com.cmsz.ircn.common.IrcnConstant;
import com.cmsz.ircn.log.monitor.MonitorEvent.Level;

@Component
public class Monitor {
	private Logger sysLogger;

	private Logger monitorLogger;

	public Monitor() {
		try {
			if (StringUtils.isEmpty(System.getProperty(IrcnConstant.APP_CODE))) {
				PropertyConfigurator.configureAndWatch(URLDecoder
						.decode(Monitor.class.getClassLoader().getResource("log4j.properties").getPath(), "utf-8"),1000);
			} else {
				String log4jFilePath = IrcnConstant.appPath + "/conf/"
						+ System.getProperty(IrcnConstant.APP_CODE) + "/log4j.properties";
				File log4jFile = new File(log4jFilePath);
				if(!log4jFile.exists()){
					PropertyConfigurator.configureAndWatch(URLDecoder.decode(Monitor.class.getClassLoader().getResource("log4j.properties").getPath(), "utf-8"),1000);
				}else{
					PropertyConfigurator.configureAndWatch(URLDecoder.decode(log4jFilePath, "utf-8"),1000);
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		this.sysLogger = Logger.getLogger("loader.system");
		this.monitorLogger = Logger.getLogger("loader.monitor");
	}

	public Logger getSysLogger() {
		return sysLogger;
	}

	public void setSysLogger(Logger sysLogger) {
		this.sysLogger = sysLogger;
	}

	public Logger getMonitorLogger() {
		return monitorLogger;
	}

	public void setMonitorLogger(Logger monitorLogger) {
		this.monitorLogger = monitorLogger;
	}

	public void handle(MonitorEvent event) {
		if (Level.DEBUG.equals(event.getLevel())) {
			this.sysLogger.debug(event.getMessage());
			this.monitorLogger.debug(event);
		} else if (Level.INFO.equals(event.getLevel())) {
			this.sysLogger.info(event.getMessage());
			this.monitorLogger.info(event);
		} else if (Level.WARNING.equals(event.getLevel())) {
			this.sysLogger.warn(event.getMessage());
			this.monitorLogger.warn(event);
		} else if (Level.ERROR.equals(event.getLevel())) {
			this.sysLogger.error(event.getMessage());
			this.monitorLogger.error(event);
		} else if (Level.FATAL.equals(event.getLevel())) {
			this.sysLogger.fatal(event.getMessage());
			this.monitorLogger.fatal(event);
		}
	}

	public void handle(MonitorEvent event, Throwable t) {
		if (Level.DEBUG.equals(event.getLevel())) {
			this.sysLogger.debug(event.getMessage(), t);
			this.monitorLogger.debug(event, t);
		} else if (Level.INFO.equals(event.getLevel())) {
			this.sysLogger.info(event.getMessage(), t);
			this.monitorLogger.info(event, t);
		} else if (Level.WARNING.equals(event.getLevel())) {
			this.sysLogger.warn(event.getMessage(), t);
			this.monitorLogger.warn(event, t);
		} else if (Level.ERROR.equals(event.getLevel())) {
			this.sysLogger.error(event.getMessage(), t);
			this.monitorLogger.error(event, t);
		} else if (Level.FATAL.equals(event.getLevel())) {
			this.sysLogger.fatal(event.getMessage(), t);
			this.monitorLogger.fatal(event, t);
		}
	}
}
