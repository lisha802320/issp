package com.cmsz.ircn.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class DateUtils {

	private DateUtils() {
	}

	private static ThreadLocal threadLocal = new ThreadLocal();

	public static SimpleDateFormat getDateFormat(String pattern) {

		HashMap<String, SimpleDateFormat> dfMap = (HashMap<String, SimpleDateFormat>) threadLocal.get();
		if (dfMap == null) {
			dfMap = new HashMap<String, SimpleDateFormat>();
			threadLocal.set(dfMap);
		}
		SimpleDateFormat df = dfMap.get(pattern);
		if (df == null) {
			df = new SimpleDateFormat(pattern);
		}
		return df;
	}

	/** 日期格式配比 */
	public static final String[] DATE_PATTERNS = new String[] { "yyyy", "yyyy-MM", "yyyyMM", "yyyy/MM", "yyyy-MM-dd", "yyyyMMdd", "yyyy/MM/dd", "yyyy-MM-dd HH:mm:ss", "yyyyMMddHHmmss", "yyyy/MM/dd HH:mm:ss" };
	
	public final static String Format_yyyyMMddHHmmss = "yyyyMMddHHmmss";

	public final static String Format_yyyyMMdd = "yyyyMMdd";

	public final static String Format_yyyyMM = "yyyyMM";
	
	private final static String Format_yyyy_MM_dd = "yyyy-MM-dd";

	/**
	 * 年月日时分秒
	 * 
	 * @param date
	 * @return 年月日时分秒 YYYYMMddHHmmss
	 */
	public static String formatDateToYYYYMMddHHmmss(Date date) {
		return getDateFormat(Format_yyyyMMddHHmmss).format(date);
	}

	/**
	 * 年月日
	 * 
	 * @param date
	 * @return 年月日 YYYYMMdd
	 */
	public static String formatDateToYYYYMMdd(Date date) {
		return getDateFormat(Format_yyyyMMdd).format(date);
	}

	/**
	 * 年月
	 * 
	 * @param date
	 * @return 年月 YYYYMM
	 */
	public static String formatDateToYYYYMM(Date date) {
		return getDateFormat(Format_yyyyMM).format(date);
	}

	/**
	 * 年月日时分秒
	 * 
	 * @param date
	 * @return 年月日时分秒 yyyyMMddHHmmss
	 * @throws ParseException
	 * @throws Exception
	 */
	public static Date parseDateYYYYMMddHHmmss(String date) throws ParseException {
		return getDateFormat(Format_yyyyMMddHHmmss).parse(date);
	}

	/**
	 * 年月日
	 * 
	 * @param date
	 * @return 年月日 yyyyMMdd
	 * @throws ParseException
	 * @throws Exception
	 */
	public static Date parseDateYYYYMMdd(String date) throws ParseException {
		return getDateFormat(Format_yyyyMMdd).parse(date);
	}

	/**
	 * 年月
	 * 
	 * @param date
	 * @return 年月 yyyyMM
	 * @throws ParseException
	 * @throws Exception
	 */
	public static Date parseDateYYYYMM(String date) throws ParseException {
		return getDateFormat(Format_yyyyMM).parse(date);
	}

	/**
	 * 指定日期加上指定天数得到新日期
	 * 
	 * @param date
	 *            日期
	 * @param day
	 *            加上指定天数
	 * @return 新日期
	 * @throws ParseException
	 *             异常
	 */
	public static Date addDate(Date date, long day) throws ParseException {
		long time = date.getTime(); // 得到指定日期的毫秒数
		day = day * 24 * 60 * 60 * 1000; // 要加上的天数转换成毫秒数
		time += day; // 相加得到新的毫秒数
		return new Date(time); // 将毫秒数转换成日期
	}

	/**
	 * 秒数加减法 比如：加一秒
	 * 
	 * @param date
	 *            日期
	 * @param amount
	 *            值 可以为负数
	 * @return
	 * @throws Exception
	 */
	public static Date addSeconds(Date date, int amount) throws Exception {
		try {
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.add(Calendar.SECOND, amount);
			return c.getTime();
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 日期加减法 比如：加一天 20160920 +1 = 20160921
	 * 
	 * @param date
	 * @param amount
	 *            值 可以为负数
	 * @return
	 * @throws Exception
	 */
	public static Date addDay(Date date, int amount) throws Exception {
		try {
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.add(Calendar.DAY_OF_MONTH, amount);
			return c.getTime();
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
     * 按照所需格式将日期转换为字符串类型
     * @param date 日期
     * @param type 转换类型
     * @return  转换后日期的字符串表达形式
     */
	public static String dateToString(Date date,String type) {
		if (date == null) {
			return null;
		}

		java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat(type);

		return dateFormat.format(date);
	}
	/**
	 * 将日期转换为10位日期字符串(格式为 yyyy-MM-dd).
	 */
	public static String dateTo10String(Date date) {
		if (date == null) {
			return null;
		}
	
		return dateToString(date, Format_yyyy_MM_dd);
	}
	/**
	 * 将当前日期转换为10位日期字符串(格式为 yyyy-MM-dd).
	 */
	public static String dateCurrentTo10String() {
		Date date= new Date();
		return dateToString(date, Format_yyyy_MM_dd);
	}
	/**
	 * 将 10位的字符串(格式为yyyy-MM-dd)转换为日期.
	 */	
	public static Date string10ToDate(String input) {
		if (isEmpty(input)) {
			return null;
		}

		if (input.length() != 10) {
			return null;
		}

		java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat(
				"yyyy-MM-dd");

		try {
			return dateFormat.parse(input);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}
	
	/**
	 * 将 19 位的字符串(格式为yyyy-MM-dd HH:mm:ss)转换为日期.
	 */	
	public static Date string19ToDate(String input) {
		if (isEmpty(input)) {
			return null;
		}

		if (input.length() != 19) {
			return null;
		}

		java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");

		try {
			return dateFormat.parse(input);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}
	public static boolean isEmpty(String input) {
		return (input == null || input.length() == 0);
	}
    /**
     * 判断两个时间的天数差值 **/
     public static int daysBetween(Date smdate,Date bdate) throws Exception    
        {    
         if(smdate==null||bdate==null){
             return 0;
         }   
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
            smdate=sdf.parse(sdf.format(smdate));  
            bdate=sdf.parse(sdf.format(bdate));  
            Calendar cal = Calendar.getInstance();    
            cal.setTime(smdate);    
            long time1 = cal.getTimeInMillis();                 
            cal.setTime(bdate);    
            long time2 = cal.getTimeInMillis();         
            long between_days=(time2-time1)/(1000*3600*24);  
           return Integer.parseInt(String.valueOf(between_days));           
        }    
     
     /**
      * 给一个时间类型，判断当前是星期几
      **/ 
     public static int dayForWeek(Date date) throws Exception {  
         Calendar c = Calendar.getInstance();  
         c.setTime(date);
         int dayForWeek = 0;  
         if(c.get(Calendar.DAY_OF_WEEK) == 1){  
          dayForWeek = 7;  
         }else{  
          dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;  
         }  
         return dayForWeek;  
        } 
     
     /**
      * 一个时间，判断星期
      **/
     public static String getWeekOfDate(Date dt) {
           String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
           Calendar cal = Calendar.getInstance();
           cal.setTime(dt);

           int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
           if (w < 0)
               w = 0;

           return weekDays[w];
       }
     
     /**返回日期，并且星期几**/
     public static  String  getDateandWeekOf(Date date){
         java.text.DateFormat format1 = new java.text.SimpleDateFormat( "yyyy年 MM月 dd日 ");  
         String returndate = format1.format(date);  
         returndate=returndate+getWeekOfDate(date);
         return returndate;
     }
     
     /**
      * 返回两个时间之间的间隔年数
      **/
     public static Double getBetweenYear(Date last,Date now){
         Date lastdate,nowdate;
         if(last.before(now)){
             lastdate=last;
             nowdate=now;
         }else{
             lastdate=now;
             nowdate=last; 
         }
         Calendar lastcal = Calendar.getInstance();
         lastcal.setTime(lastdate);
         Calendar nowcal = Calendar.getInstance();
         nowcal.setTime(nowdate);
         int[]  p1 = {  lastcal.get(Calendar.YEAR), lastcal.get(Calendar.MONTH), lastcal.get(Calendar.DAY_OF_MONTH)}; 
         int[]  p2 = {  nowcal.get(Calendar.YEAR), nowcal.get(Calendar.MONTH), nowcal.get(Calendar.DAY_OF_MONTH)};
         
         int p3=0;
         if(p1[2]>p2[2]){
             p3= p2[0]*12+p2[1]-p1[0]*12-p1[1];  
         }else{
             p3= p2[0]*12+p2[1]-p1[0]*12-p1[1]-1;  
         }
         Double dou =(double) p3/12;
         return dou;
     }
     public static  int[] getDateInfo(Date date){
         Calendar dainfo = Calendar.getInstance();
         dainfo.setTime(date);
         int[]  p1 = {  dainfo.get(Calendar.YEAR), dainfo.get(Calendar.MONTH)+1, dainfo.get(Calendar.DAY_OF_MONTH),dainfo.get(Calendar.HOUR_OF_DAY) ,dainfo.get(Calendar.MINUTE)};     
         return p1;
     }
     
     
    /**两个时间相差多少分钟**/
     public static double getMinute(Date start, Date end){       
         long cha = end.getTime() - start.getTime();
         double result = cha * 1.0 / (1000 * 60);
         return result;
     }
     //时间往前加多少分钟
     public static Date dateAddMinute(Date date,int minute){
         Calendar lastcal = Calendar.getInstance();
         lastcal.setTime( date);  
         lastcal.add(Calendar.MINUTE,minute);
      return lastcal.getTime();   
     }
     //时间减去多少分钟 minus
     public static Date dateMinusMinute(Date date,int minute){
         Calendar lastcal = Calendar.getInstance();
         lastcal.setTime( date);  
         lastcal.set(Calendar.MINUTE, lastcal.get(Calendar.MINUTE) - minute);
      return lastcal.getTime();   
     }
     //时间减去多少天
     public static Date dateMinusDay(Date date,int day){
    	 Calendar lastcal = Calendar.getInstance();
    	 lastcal.setTime( date);  
    	 lastcal.set(Calendar.DATE, lastcal.get(Calendar.DATE) - day);
    	 return lastcal.getTime();   
     }
     
     public  static String getYearMou(Date date){
         java.text.DateFormat format = new java.text.SimpleDateFormat( "yyyyMM");  
         String returndate = format.format(date); 
         return returndate;
     }
     public  static String getYearMonDay(Date date){
         java.text.DateFormat format = new java.text.SimpleDateFormat( "yyyyMMdd");  
         String returndate = format.format(date); 
         return returndate;
     }
     public static String getStringate(Date date){
         if(date==null){
             return "";
         }
         java.text.DateFormat   format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
         String returndate = format.format(date); 
         return returndate;
     }
     public static String getStringateByDay(Date date){
         if(date==null){
             return "";
         }
         java.text.DateFormat   format = new SimpleDateFormat("yyyy-MM-dd");
         String returndate = format.format(date); 
         return returndate;
     }
     /**
      * 根据时间戳返回时间
      * @param timestamp
      * @return
      */
     public static Date getDateByTimestamp(long timestamp){
    	 SimpleDateFormat format = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
    	 String d = format.format(timestamp);
    	 Date date;
		try {
			date = format.parse(d);
			return date;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	 return null;
     }

}
