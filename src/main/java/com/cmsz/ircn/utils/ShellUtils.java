package com.cmsz.ircn.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.cmsz.ircn.log.monitor.Monitor;
import com.cmsz.ircn.log.monitor.MonitorableLog;

public class ShellUtils {
	public static MonitorableLog log;
	static{
		Monitor monitor = new Monitor();
		log = new MonitorableLog();
		log.setMonitor(monitor);
	}
	
	/**
	 * 执行shell命令返回结果
	 * @param cmd
	 * @return
	 */
	public static String exec(String cmd) {
		try {
			log.info("命令执行："+cmd);
			Process ps = Runtime.getRuntime().exec(cmd);
			BufferedReader br = new BufferedReader(new InputStreamReader(ps.getInputStream()));
			StringBuffer sb = new StringBuffer();
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line).append("\n");
				log.info("命令执行结果line："+sb);
			}
			log.info("命令执行结果："+sb.toString());
			return sb.toString();
		} catch (IOException e) {
			log.error(e, "命令执行异常");
		}
		return null;
	}
	
	public static void main(String[] args) {
		System.out.println(exec("cd .."));
	}
}
