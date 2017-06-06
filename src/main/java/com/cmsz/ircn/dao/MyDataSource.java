package com.cmsz.ircn.dao;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.commons.lang3.StringUtils;

import com.cmsz.ircn.log.monitor.Monitor;
import com.cmsz.ircn.log.monitor.MonitorableLog;

public class MyDataSource {

	public static MonitorableLog log;
	static {
		Monitor monitor = new Monitor();
		log = new MonitorableLog();
		log.setMonitor(monitor);
	}

	private String password;
	private String shellpath;

	public MyDataSource(String password, String shellpath) {
		this.password = password;
		this.shellpath = shellpath;
		log.debug("脚本：{0}", this.shellpath);
		if (StringUtils.isNotEmpty(this.shellpath)) {
			getdbpwd(this.shellpath);
		}
	}

	protected void getdbpwd(String cmd) {
		log.info("运行脚本[{0}]获取数据库密码开始", cmd);
		log.info("调用getdbpwd");
		if (StringUtils.isEmpty(cmd)) {
			return;
		}

		try {
			log.info("执行cmd" + cmd);
			Process process = Runtime.getRuntime().exec(cmd);
			BufferedReader buffered = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String newpassword = buffered.readLine();
			password = newpassword;
			log.info("运行脚本[{0}]获取数据库密码结束", cmd);
		} catch (Exception e) {
			log.error("运行脚本[{0}]获取数据库密码时异常", cmd);
			// log.error("", e);
			log.info("运行脚本" + cmd + "获取数据库密码时异常" + e);
		}
	}

	public final String getPassword() {
		return password;
	}

	public final String getShellpath() {
		return shellpath;
	}

	public final void setPassword(String password) {
		this.password = password;
	}

	public final void setShellpath(String shellpath) {
		this.shellpath = shellpath;
	}

}
