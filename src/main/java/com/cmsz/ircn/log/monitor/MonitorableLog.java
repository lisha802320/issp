package com.cmsz.ircn.log.monitor;

import java.text.MessageFormat;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.cmsz.ircn.common.CallerStackTrace;
import com.cmsz.ircn.log.monitor.MonitorEvent.Level;

@Component
public class MonitorableLog {
	private ThreadLocal<String> origCode = new ThreadLocal<String>();

	private ThreadLocal<String> homeCode = new ThreadLocal<String>();

	private ThreadLocal<String> activityCode = new ThreadLocal<String>();

	@Resource
	private Monitor monitor;

	// @Resource(name = "common.oracle.IoSubchgWarnInfoDao")
	// private IoSubchgWarnInfoDao ioSubchgWarnInfoDao;

	public Monitor getMonitor() {
		return monitor;
	}

	public void setMonitor(Monitor monitor) {
		this.monitor = monitor;
	}

	public ThreadLocal<String> getOrigCode() {
		return origCode;
	}

	public void setOrigCode(ThreadLocal<String> origCode) {
		this.origCode = origCode;
	}

	public ThreadLocal<String> getHomeCode() {
		return homeCode;
	}

	public void setHomeCode(ThreadLocal<String> homeCode) {
		this.homeCode = homeCode;
	}

	public ThreadLocal<String> getActivityCode() {
		return activityCode;
	}

	public void setActivityCode(ThreadLocal<String> activityCode) {
		this.activityCode = activityCode;
	}


	public void debug(String message, Object... details) {
		if (monitor == null) {
			monitor = new Monitor();
		}
		message = MessageFormat.format(message, details);
		CallerStackTrace callerStackTrace = getCallerStackTrace();
		message = callerStackTrace.getLine() + message;
		MonitorEvent monitorEvent = new MonitorEvent(Level.DEBUG, callerStackTrace, message);
		this.monitor.handle(monitorEvent);
	}

	public void info(String message, Object... details) {
		if (monitor == null) {
			monitor = new Monitor();
		}
		message = MessageFormat.format(message, details);
		CallerStackTrace callerStackTrace = getCallerStackTrace();
		message = callerStackTrace.getLine() + message;
		MonitorEvent monitorEvent = new MonitorEvent(Level.INFO, callerStackTrace, message);
		this.monitor.handle(monitorEvent);
	}

	public void warn(String message, Object... details) {
		if (monitor == null) {
			monitor = new Monitor();
		}
		message = MessageFormat.format(message, details);
		CallerStackTrace callerStackTrace = getCallerStackTrace();
		message = callerStackTrace.getLine() + message;
		MonitorEvent monitorEvent = new MonitorEvent(Level.WARNING, callerStackTrace, message);
		this.monitor.handle(monitorEvent);
	}

	public void error(String message, Object... details) {
		if (monitor == null) {
			monitor = new Monitor();
		}
		message = MessageFormat.format(message, details);
		CallerStackTrace callerStackTrace = getCallerStackTrace();
		message = callerStackTrace.getLine() + message;
		MonitorEvent monitorEvent = new MonitorEvent(Level.ERROR, callerStackTrace, message);
		this.monitor.handle(monitorEvent);
	}

	public void fatal(String message, Object... details) {
		if (monitor == null) {
			monitor = new Monitor();
		}
		message = MessageFormat.format(message, details);
		CallerStackTrace callerStackTrace = getCallerStackTrace();
		message = callerStackTrace.getLine() + message;
		MonitorEvent monitorEvent = new MonitorEvent(Level.FATAL, callerStackTrace, message);
		this.monitor.handle(monitorEvent);
	}

	public void error(Throwable t, String message, Object... details) {
		if (monitor == null) {
			monitor = new Monitor();
		}
		message = MessageFormat.format(message, details);
		CallerStackTrace callerStackTrace = getCallerStackTrace();
		message = callerStackTrace.getLine() + message;
		MonitorEvent monitorEvent = new MonitorEvent(Level.ERROR, callerStackTrace, message);
		this.monitor.handle(monitorEvent, t);
	}

	public void fatal(Throwable t, String message, Object... details) {
		if (monitor == null) {
			monitor = new Monitor();
		}
		message = MessageFormat.format(message, details);
		CallerStackTrace callerStackTrace = getCallerStackTrace();
		message = callerStackTrace.getLine() + message;
		MonitorEvent monitorEvent = new MonitorEvent(Level.FATAL, callerStackTrace, message);
		this.monitor.handle(monitorEvent, t);
	}

	public CallerStackTrace getCallerStackTrace() {
		StackTraceElement stackTrace[] = Thread.currentThread().getStackTrace();
		CallerStackTrace callerStackTrace = new CallerStackTrace();
		String wholeClassName = stackTrace[3].getClassName();
		String className = wholeClassName.substring(wholeClassName.lastIndexOf(".")+1);
		String fileName = stackTrace[3].getFileName();
		String methodName = stackTrace[3].getMethodName();
		int lineNumber = stackTrace[3].getLineNumber();
		callerStackTrace.setClassName(className);
		callerStackTrace.setFileName(fileName);
		callerStackTrace.setLineNumber(lineNumber);
		callerStackTrace.setMethodName(methodName);
		
		StringBuffer sb = new StringBuffer();
		sb.append(" [");
		sb.append(className);
		sb.append(".");
		sb.append(methodName);
		sb.append("(");
		sb.append(lineNumber);
		sb.append(")] - ");
		callerStackTrace.setLine(sb.toString());
		callerStackTrace.setModule(null);
		callerStackTrace.setSubSystem("IRCN-WEB");
		callerStackTrace.setSystem("IRCN");
		return callerStackTrace;
	}

}
