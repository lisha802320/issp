package com.cmsz.ircn.bean;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.converters.ArrayConverter;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.cmsz.ircn.common.EnumConverter;
import com.cmsz.ircn.common.IrcnConstant;
import com.cmsz.ircn.common.PropertiesKeyName;
import com.cmsz.ircn.log.monitor.Monitor;
import com.cmsz.ircn.log.monitor.MonitorableLog;

/**
 * 系统设置
 */
@Component("setting")
public class Setting implements Serializable {
	public static final String[] DATE_PATTERNS = new String[] { "yyyy", "yyyy-MM", "yyyyMM", "yyyy/MM", "yyyy-MM-dd",
			"yyyyMMdd", "yyyy/MM/dd", "yyyy-MM-dd HH:mm:ss", "yyyyMMddHHmmss", "yyyy/MM/dd HH:mm:ss" };
	private static final BeanUtilsBean beanUtils;
	static {
		ConvertUtilsBean convertUtilsBean = new ConvertUtilsBean() {
			@Override
			public String convert(Object value) {
				if (value != null) {
					Class<?> type = value.getClass();
					if (type.isEnum() && super.lookup(type) == null) {
						super.register(new EnumConverter(type), type);
					} else if (type.isArray() && type.getComponentType().isEnum()) {
						if (super.lookup(type) == null) {
							ArrayConverter arrayConverter = new ArrayConverter(type,
									new EnumConverter(type.getComponentType()), 0);
							arrayConverter.setOnlyFirstToString(false);
							super.register(arrayConverter, type);
						}
						Converter converter = super.lookup(type);
						return ((String) converter.convert(String.class, value));
					}
				}
				return super.convert(value);
			}

			@SuppressWarnings("rawtypes")
			@Override
			public Object convert(String value, Class clazz) {
				if (clazz.isEnum() && super.lookup(clazz) == null) {
					super.register(new EnumConverter(clazz), clazz);
				}
				return super.convert(value, clazz);
			}

			@SuppressWarnings("rawtypes")
			@Override
			public Object convert(String[] values, Class clazz) {
				if (clazz.isArray() && clazz.getComponentType().isEnum()
						&& super.lookup(clazz.getComponentType()) == null) {
					super.register(new EnumConverter(clazz.getComponentType()), clazz.getComponentType());
				}
				return super.convert(values, clazz);
			}

			@SuppressWarnings("rawtypes")
			@Override
			public Object convert(Object value, Class targetType) {
				if (super.lookup(targetType) == null) {
					if (targetType.isEnum()) {
						super.register(new EnumConverter(targetType), targetType);
					} else if (targetType.isArray() && targetType.getComponentType().isEnum()) {
						ArrayConverter arrayConverter = new ArrayConverter(targetType,
								new EnumConverter(targetType.getComponentType()), 0);
						arrayConverter.setOnlyFirstToString(false);
						super.register(arrayConverter, targetType);
					}
				}
				return super.convert(value, targetType);
			}
		};

		DateConverter dateConverter = new DateConverter();
		dateConverter.setPatterns(DATE_PATTERNS);
		convertUtilsBean.register(dateConverter, Date.class);
		beanUtils = new BeanUtilsBean(convertUtilsBean);
	}

	public static MonitorableLog log;
	public static Setting setting;
	static {
		Monitor monitor = new Monitor();
		log = new MonitorableLog();
		log.setMonitor(monitor);
	}

	private static final long serialVersionUID = -1478999889661796840L;

	public Setting() {
		log.info("公参文件加载properties");
		load();
		log.info("公参文件加载properties完成");
	}

	public void reload() {
		log.info("公参文件重载properties");
		load();
		log.info("公参文件重载properties完成");
	}

	public void load() {
		File[] propertiesFiles = null;
		String conf2Path = System.getProperty("conf2Path");
		if (!StringUtils.isEmpty(conf2Path)) {
			File confDir = new File(conf2Path);
			if (confDir.exists() && confDir.isDirectory()) {
				propertiesFiles = confDir.listFiles(new FileFilter() {
					@Override
					public boolean accept(File file) {
						return isPropertiesFile(file);
					}
				});
			}
		}
		if (!ArrayUtils.isEmpty(propertiesFiles)) {
			for (File propertiesFile : propertiesFiles) {
				log.info("公参文件加载，加载properties，文件名：{0}", propertiesFile.getName());
				Properties config = new Properties();
				InputStream in = null;
				try {
					in = new BufferedInputStream(new FileInputStream(propertiesFile));
					config.load(in);
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					IOUtils.closeQuietly(in);
				}
				Enumeration propertyNameEnum = config.propertyNames();
				while (propertyNameEnum.hasMoreElements()) {
					String propertiesName = propertyNameEnum.nextElement().toString();
					String propertiesValue = config.getProperty(propertiesName);
					try {
						String fieldName = findFieldName(propertiesName);
						if (!StringUtils.isEmpty(fieldName)) {
							beanUtils.setProperty(this, fieldName, propertiesValue);
							log.debug("公参文件加载，propertiesKey：{0}，filedName：{1}，value：{2}", propertiesName, fieldName,
									propertiesValue);
						}
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
				}
			}
		}
		setting = this;
	}
	
	public static Setting get(){
		return setting;
	}

	/** 线程池大小 */
	@PropertiesKeyName(keyName = "task.core_pool_size")
	private String taskCorePoolSize;

	/** 线程池最大大小 */
	@PropertiesKeyName(keyName = "task.max_pool_size")
	private String taskMaxPoolSize;

	/** 线程池队列大小 */
	@PropertiesKeyName(keyName = "task.queue_capacity")
	private String taskQueueCapacity;

	/** 线程池线程存活时间 */
	@PropertiesKeyName(keyName = "task.keep_alive_seconds")
	private String taskKeepAliveSeconds;
	
	/** 账号锁定分钟 */
	@PropertiesKeyName(keyName = "account.lockTime")
	private Integer accountLockTime;
	
	/** 账号锁定次数 */
	@PropertiesKeyName(keyName = "account.lockCount")
	private Integer accountLockCount;
	

	public boolean isPropertiesFile(File file) {
		if (file.getName().toLowerCase().endsWith(".properties")) {
			return true;
		} else {
			return false;
		}
	}

	public String findFieldName(String propertiesKeyName) {
		Field[] fields = this.getClass().getDeclaredFields();
		for (Field field : fields) {
			PropertiesKeyName _propertiesKeyName = field.getAnnotation(PropertiesKeyName.class);
			if (_propertiesKeyName != null && propertiesKeyName.equals(_propertiesKeyName.keyName())) {
				return field.getName();
			}
		}
		return null;
	}

	public MonitorableLog getLog() {
		return log;
	}

	public void setLog(MonitorableLog log) {
		this.log = log;
	}

	public String getTaskCorePoolSize() {
		return taskCorePoolSize;
	}

	public void setTaskCorePoolSize(String taskCorePoolSize) {
		this.taskCorePoolSize = taskCorePoolSize;
	}

	public String getTaskMaxPoolSize() {
		return taskMaxPoolSize;
	}

	public void setTaskMaxPoolSize(String taskMaxPoolSize) {
		this.taskMaxPoolSize = taskMaxPoolSize;
	}

	public String getTaskQueueCapacity() {
		return taskQueueCapacity;
	}

	public void setTaskQueueCapacity(String taskQueueCapacity) {
		this.taskQueueCapacity = taskQueueCapacity;
	}

	public String getTaskKeepAliveSeconds() {
		return taskKeepAliveSeconds;
	}

	public void setTaskKeepAliveSeconds(String taskKeepAliveSeconds) {
		this.taskKeepAliveSeconds = taskKeepAliveSeconds;
	}
	
	public Integer getAccountLockTime() {
		return accountLockTime;
	}

	public void setAccountLockTime(Integer accountLockTime) {
		this.accountLockTime = accountLockTime;
	}

	public Integer getAccountLockCount() {
		return accountLockCount;
	}

	public void setAccountLockCount(Integer accountLockCount) {
		this.accountLockCount = accountLockCount;
	}

	public static String[] getDatePatterns() {
		return DATE_PATTERNS;
	}

	public static BeanUtilsBean getBeanutils() {
		return beanUtils;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * 验证码类型
	 */
	public enum CaptchaType {

		/** 后台登录 */
		adminLogin
	}
	
	/**
	 * 账号锁定类型
	 */
	public enum AccountLockType {

		/** 管理员 */
		admin
	}


}