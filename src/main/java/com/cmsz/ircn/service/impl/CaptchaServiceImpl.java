package com.cmsz.ircn.service.impl;

import java.awt.image.BufferedImage;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.cmsz.ircn.bean.Setting;
import com.cmsz.ircn.bean.Setting.CaptchaType;
import com.cmsz.ircn.service.CaptchaService;

/**
 * Service - 验证码
 */
@Service("captchaServiceImpl")
public class CaptchaServiceImpl implements CaptchaService {

	@Resource(name = "imageCaptchaService")
	private com.octo.captcha.service.CaptchaService imageCaptchaService;
	@Resource
	private Setting setting;

	public BufferedImage buildImage(String captchaId) {
		return (BufferedImage) imageCaptchaService.getChallengeForID(captchaId);
	}

	public boolean isValid(CaptchaType captchaType, String captchaId, String captcha) {
		if (StringUtils.isNotEmpty(captchaId) && StringUtils.isNotEmpty(captcha)) {
			try {
				return imageCaptchaService.validateResponseForID(captchaId, captcha.toUpperCase());
			} catch (Exception e) {
				return false;
			}
		} else {
			return false;
		}
	}

}