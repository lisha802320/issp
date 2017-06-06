/*
 * Copyright 2013-2015 upcif.com. All rights reserved.
 * Support: http://www.upcif.com
 * License: http://www.upcif.com/license
 */
package com.cmsz.ircn.service;

import java.awt.image.BufferedImage;

import com.cmsz.ircn.bean.Setting.CaptchaType;

/**
 * Service - 验证码
 * 
 * @author UPCIF Team
 * @version 0.8
 */
public interface CaptchaService {

	/**
	 * 生成验证码图片
	 * 
	 * @param captchaId
	 *            验证ID
	 * @return 验证码图片
	 */
	BufferedImage buildImage(String captchaId);

	/**
	 * 验证码验证
	 * 
	 * @param captchaType
	 *            验证码类型
	 * @param captchaId
	 *            验证ID
	 * @param captcha
	 *            验证码(忽略大小写)
	 * @return 验证码验证是否通过
	 */
	boolean isValid(CaptchaType captchaType, String captchaId, String captcha);
	
}