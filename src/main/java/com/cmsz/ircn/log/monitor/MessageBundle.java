package com.cmsz.ircn.log.monitor;

import java.text.MessageFormat;

import org.apache.commons.lang3.ArrayUtils;

public class MessageBundle {
	private final static String LOG_TEMPLATE = "BL##{0}#{1}#{2}#{3}#{4}#{5}###120001000000####msg_cont:{6}##LB";

	public static String getMessage(Object... args) {
		String message = null;
		if (!ArrayUtils.isEmpty(args)) {
			message = MessageFormat.format(LOG_TEMPLATE, args);
		}
		return message;
	}
}
