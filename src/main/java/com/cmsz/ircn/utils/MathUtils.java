package com.cmsz.ircn.utils;

import java.util.Arrays;
import java.util.Random;

public class MathUtils {
	/**
	 * 生成六位随机数字 getRandomSix() ()
	 * 
	 * @return 六位随机数字
	 */
	public static String getRandomSix() {
		Random random = new Random();
		int x = random.nextInt(899999);
		x = x + 100000;
		return String.valueOf(x);
	}
	
	/**
	 * 随机数字 
	 * @param len 位数
	 * @return
	 */
	public static String getRandomSix(int len) {
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		sb.append("8");
		int minx = 1;
		for(int i=1;i<len;i++){
			sb.append("9");
			minx = minx*10;
		}
		int maxI = Integer.parseInt(sb.toString());
		int x = random.nextInt(maxI);
		x = x + minx;		
		return String.valueOf(x);
	}
	
	 /**查找出现串的内容**/
    /**
     * getStringCheck
     * @param content
     *  查找的内容
     * @param start
     * 开始位置
     * @param end
     * 结束位置
     * @return   
     */
   public   static String getStringCheck(String content,String start,String end){
       int sta=content.indexOf(start);
       int ed=content.indexOf(end);
       return content.substring(sta+1, ed);
   }
   //数组返回String
   public static String getString(Object[] args){
     if(args==null){
         return null;
     }
       String str = Arrays.toString(args).replaceAll(", ", ",");
       str = str.substring(1, (str.length()-1));
       return str;
   }
}
