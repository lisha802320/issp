package com.cmsz.ircn.listener;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.management.ManagementFactory;
import java.net.URLDecoder;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.lang3.ArrayUtils;

import com.cmsz.ircn.common.IrcnConstant;
import com.cmsz.ircn.log.monitor.Monitor;
import com.cmsz.ircn.log.monitor.MonitorableLog;
import com.cmsz.ircn.utils.ShellUtils;

public class SystenEnvInitListener implements ServletContextListener {
	public static MonitorableLog log;

	private final static String STATUS_ALIVE = "false";
	private final static String STATUS_KILL = "true";

	public void contextDestroyed(ServletContextEvent servletContextEvent) {

	}

	public void contextInitialized(ServletContextEvent servletContextEvent) {
		System.setProperty(IrcnConstant.MCB_HOME, IrcnConstant.appHome);
		System.setProperty(IrcnConstant.MCB_APPID, IrcnConstant.appID);
		String basePath = servletContextEvent.getServletContext().getRealPath("/WEB-INF");
		File basePathFile = new File(basePath);
		String[] appCodeFileNames = basePathFile.list(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return isAppcode(name);
			}
		});
		String applicationContextFile2Path = null;
		String conf2Path = null;
		String appCode = null;
		if (ArrayUtils.isEmpty(appCodeFileNames)) {
			applicationContextFile2Path = "classpath:";
			try {
				conf2Path = URLDecoder.decode(this.getClass().getClassLoader().getResource("").getPath(), "utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		} else {
			String appCodeFileName = appCodeFileNames[0];
			appCode = appCodeFileName.substring(0, appCodeFileName.lastIndexOf("."));
			System.setProperty(IrcnConstant.APP_CODE, appCode);
			applicationContextFile2Path = System.getProperty(IrcnConstant.MCB_HOME) + "/"
					+ System.getProperty(IrcnConstant.MCB_APPID) + "/conf/" + appCode;
			conf2Path = applicationContextFile2Path;
			File applicationContextFile = new File(applicationContextFile2Path);
			if (applicationContextFile.exists() && applicationContextFile.isDirectory()
					&& !ArrayUtils.isEmpty(applicationContextFile.list())) {
				applicationContextFile2Path = "file:" + applicationContextFile2Path;
			} else {
				applicationContextFile2Path = "classpath:";
				try {
					conf2Path = URLDecoder.decode(this.getClass().getClassLoader().getResource("").getPath(), "utf-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}
		System.setProperty("applicationContextFile2Path", applicationContextFile2Path);
		System.setProperty("conf2Path", conf2Path);
		Monitor monitor = new Monitor();
		log = new MonitorableLog();
		log.setMonitor(monitor);
		log.info("应用程序加载appCode:{0}", appCode);
		log.info("应用程序加载配置文件路径applicationContextFile2Path:{0}", applicationContextFile2Path);
		log.info("应用程序加载配置文件路径conf2Path:{0}", conf2Path);

		/** --------kill tomcat修改---------- **/
		String killFilePathName = System.getProperty(IrcnConstant.MCB_HOME) + "/"
				+ System.getProperty(IrcnConstant.MCB_APPID) + "/conf/" + appCode + "kill.conf";
		final File killFilePath = new File(killFilePathName);
		if (!killFilePath.exists()) {
			try {
				killFilePath.createNewFile();
			} catch (IOException e) {
				log.error(e, "创建kill文件失败");
			}
		}
		coverFileContent(killFilePath, STATUS_ALIVE);
		Thread t = new Thread(new Runnable() {
			public void run() {
				while (true) {
					String status = readFileContent(killFilePath).trim();
					if (STATUS_KILL.equalsIgnoreCase(status)) {
						// 执行kill指令
						String cmd = null;
						if(isLocal()){
							cmd = "tskill " + getPid();
						}else{
							cmd = "kill " + getPid();
						}
						ShellUtils.exec(cmd);
						break;
					}
					try {
						Thread.sleep(1000*2);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		t.setDaemon(true);
		t.start();

	}

	public boolean isAppcode(String fileName) {
		if (fileName.toLowerCase().endsWith(".appcode")) {
			return true;
		} else {
			return false;
		}
	}

	public String readFileContent(File file) {
		StringBuffer sb = new StringBuffer();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				sb.append(tempString);
			}
			reader.close();
		} catch (IOException e) {
			log.error(e, "读取文件失败");
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		return sb.toString();
	}

	public void coverFileContent(File file, String content) {
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new FileWriter(file));
			out.write(content);
			out.close();
		} catch (IOException e) {
			log.error(e, "覆盖文件失败");
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e1) {
				}
			}
		}
	}

	public String getPid() {
		String name = ManagementFactory.getRuntimeMXBean().getName();
		String pid = name.split("@")[0];
		return pid;
	}
	
	public boolean isLocal(){
		String os = System.getProperty("os.name");  
		if(os.toLowerCase().startsWith("win")){
			return true;
		}
		return false;
	}
}
