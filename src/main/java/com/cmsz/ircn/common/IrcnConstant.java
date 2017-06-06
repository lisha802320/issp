package com.cmsz.ircn.common;

import java.io.File;

public class IrcnConstant {

	public final static String MCB_HOME = "MCB_HOME";

	public final static String MCB_APPID = "MCB_APPID";
	
	public final static String APP_CODE = "APP_CODE";
	
	public final static String appHome = System.getenv(IrcnConstant.MCB_HOME);

	public final static String appID = System.getenv(IrcnConstant.MCB_APPID);

	public final static String appPath = appHome + File.separator + appID;

}	
