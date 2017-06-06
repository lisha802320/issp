package com.cmsz.ircn.log.monitor;

import java.net.InetAddress;

import org.apache.log4j.or.ObjectRenderer;

import com.cmsz.ircn.utils.DateUtils;

public class MonitorEventRenderer implements ObjectRenderer {
	public String doRender(Object o) {
		if (o instanceof MonitorEvent) {
			MonitorEvent m = (MonitorEvent) o;
			String hostName = "localhost";
			try {
				hostName = InetAddress.getLocalHost().getHostName();
			} catch (Exception e1) {
				try {
					hostName = InetAddress.getLocalHost().getHostAddress();
				} catch (Exception e2) {
					hostName = "localhost";
				}
			}
			//BL\#\#{0}\#{1}\#{2}\#{3}\#{4}\#interface.jar\#\#\#120001000000\#\#\#\#msg_cont\:{7}\#\#LB
			// BL##INFO#20091203115509#PC-200908122254#FMS##IR_ANTIFRAUD#ANTIFRAUD##120013010600#13010600###msg_cont:欺诈检测系统开始运行...##LB
			String currentTime = DateUtils.formatDateToYYYYMMddHHmmss(m.getEventTime());
			return MessageBundle.getMessage(m.getLevel(), currentTime, hostName, m.getCallerStackTrace().getSystem(),
					m.getCallerStackTrace().getSubSystem(), m.getCallerStackTrace().getModule(), m.getMessage());
		} else {
			return o.toString();
		}
	}
}
