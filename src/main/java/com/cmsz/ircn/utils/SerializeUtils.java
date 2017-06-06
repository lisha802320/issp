package com.cmsz.ircn.utils;

import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;

/**
 * msgpack序列化
 * 
 * @author liluoliluoli
 *
 */
public class SerializeUtils {
	
	private static SerializeConfig serializeConfig = new SerializeConfig();
	private static String dateFormat = "yyyy-MM-dd HH:mm:ss";
	private static SimpleDateFormatSerializer dateSerializer = new SimpleDateFormatSerializer(dateFormat);
	
//	/**
//	 * 序列化
//	 * 
//	 * @param object
//	 * @return
//	 */
//	public static byte[] serialize(Object object) {
//		MessagePack pack = new MessagePack();
//		try {
//			byte[] bytes = pack.write(object);
//			return bytes;
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//	/**
//	 * 反序列化
//	 * 
//	 * @param bytes
//	 * @return
//	 */
//	public static <T> T unserialize(byte[] bytes, Class<T> cls) {
//		MessagePack pack = new MessagePack();
//		try {
//			T t = pack.read(bytes, cls);
//			return t;
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
	
	static {
		serializeConfig.put(Date.class, dateSerializer);
	}
	
	/**
	 * 序列化json
	 * @param object
	 * @return
	 */
	public static String serializeJson(Object object) {
		return JSON.toJSONString(object, serializeConfig, SerializerFeature.WriteMapNullValue);
	}
	
	/**
	 * 反序列化json
	 * @param json
	 * @param cls
	 * @return
	 */
	public static <T> T unserializeJson(String json, Class<T> cls) {
		return JSON.parseObject(json, cls);
	}
	

}
