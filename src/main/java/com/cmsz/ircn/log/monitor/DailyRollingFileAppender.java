package com.cmsz.ircn.log.monitor;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.spi.LoggingEvent;

public class DailyRollingFileAppender extends MyDailyRollingFileAppender {
	@Override
	protected void subAppend(LoggingEvent event) {
		if (this.fileName != null) {
			File currentFile = new File(this.fileName);
			if (!currentFile.exists()) {
				try {
					// If file has been deleted, reset this file.
					this.setFile(this.fileName, false, this.bufferedIO, this.bufferSize);
				} catch (IOException e) {
					this.errorHandler.error("Reset file when original file has been deleted faild.");
				}
			}
		}
		super.subAppend(event);
	}
}
