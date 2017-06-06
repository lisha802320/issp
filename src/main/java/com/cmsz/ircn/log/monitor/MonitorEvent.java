package com.cmsz.ircn.log.monitor;

import java.util.Date;

import com.cmsz.ircn.common.CallerStackTrace;

public class MonitorEvent {
	public static enum Level {
		INFO("INFO"), WARNING("WARNING"), ERROR("SERIOUS"), FATAL("SERIOUS"), 
		DEBUG("DEBUG");

		private String name;

		private Level(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return this.name;
		}
	};

	private Level level;

	private CallerStackTrace callerStackTrace;

	private String message;

	private Date eventTime;

	public MonitorEvent(Level level, CallerStackTrace callerStackTrace, String message) {
		this.level = level;
		this.callerStackTrace = callerStackTrace;
		this.message = message;
		this.eventTime = new Date();
	}

	public CallerStackTrace getCallerStackTrace() {
		return callerStackTrace;
	}

	public String getMessage() {
		return message;
	}

	public Date getEventTime() {
		return eventTime;
	}

	public Level getLevel() {
		return level;
	}
}
