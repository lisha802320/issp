package com.cmsz.ircn.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author cmt
 *
 */
public class SerialUtils {
	private static final Object serial = new Object();
	private static final int MAX = 9999;
	private static final int NUMLEN = 4;

	private static SerialUtils instance = null;
	private String lastTime = "";
	private int lastNo = 0;
	private int baseNum = 10000;

	// 生成序列长度
	private static final int serialLen = 30;

	public SerialUtils() {

	}

	private static String getSerialStringByNum(int base, int value) {
		String num = new Integer(base + value).toString();

		return num.substring(1);
	}

	/**
	 * 根据当前时间产生新的序列号
	 * 
	 * @param sourceId
	 * @return
	 */
	public static String genSerialNo(String sourceId) {
		synchronized (serial) {
			Date curTime = new Date();
			SimpleDateFormat s = new SimpleDateFormat("yyyyMMddHHmmss");

			if (instance == null) {
				instance = new SerialUtils();
				instance.lastNo = 0;
				instance.baseNum = (int) Math.pow(10, NUMLEN);
				instance.lastTime = s.format(curTime);
			}

			String now = s.format(curTime);
			if (now.compareTo(instance.lastTime) > 0) {
				// 当前时间大于上一次记录时间，表示可以�?始新的计�?
				instance.lastNo = 1;
				instance.lastTime = now;
				return sourceId + now + getSerialStringByNum(instance.baseNum, instance.lastNo);
			}

			if (now.compareTo(instance.lastTime) < 0) {
				// 当前时间小于上一次的记录时间，表示上�?秒有超过10000个流水号生成
				now = instance.lastTime;
			}

			int serialNo = instance.lastNo + 1;
			if (serialNo <= MAX) {
				// 当前没有超过�?大允许流水号，返回上�?个流水号+1作为新的流水�?
				instance.lastNo = serialNo;
				return sourceId + now + getSerialStringByNum(instance.baseNum, instance.lastNo);
			}

			try {
				Date last = s.parse(now);
				Calendar cal = Calendar.getInstance();
				cal.setTime(last);
				cal.add(Calendar.SECOND, 1);
				Date endTime = cal.getTime();
				String endStr = s.format(endTime);
				instance.lastNo = 1;
				instance.lastTime = endStr;
				return sourceId + endStr + getSerialStringByNum(instance.baseNum, instance.lastNo);
			} catch (ParseException e) {
			}

			return null;
		}
	}

	public static void main(String[] args) {
	}
}
