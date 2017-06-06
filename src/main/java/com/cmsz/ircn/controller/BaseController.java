package com.cmsz.ircn.controller;

import java.util.Date;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cmsz.ircn.common.DateEditor;
import com.cmsz.ircn.common.Message;
import com.cmsz.ircn.template.directive.FlashMessageDirective;
import com.cmsz.ircn.utils.SpringUtils;
import com.github.pagehelper.Page;

/**
 * Controller - 基类
 * 
 */
public class BaseController {

	/** 错误视图 */
	protected static final String ERROR_VIEW = "/common/error";

	/** 错误消息 */
	public static Message ERROR_MESSAGE = null;

	/** 成功消息 */
	public static Message SUCCESS_MESSAGE = null;

	/**
	 * 数据绑定
	 * 
	 * @param binder
	 *            WebDataBinder
	 */
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
		binder.registerCustomEditor(Date.class, new DateEditor(true));
	}


	/**
	 * 获取国际化消息
	 * 
	 * @param code
	 *            代码
	 * @param args
	 *            参数
	 * @return 国际化消息
	 */
	protected String message(String code, Object... args) {
		return SpringUtils.getMessage(code, args);
	}

	/**
	 * 添加瞬时消息
	 * 
	 * @param redirectAttributes
	 *            RedirectAttributes
	 * @param message
	 *            消息
	 */
	protected void addFlashMessage(RedirectAttributes redirectAttributes, Message message) {
		if (redirectAttributes != null && message != null) {
			redirectAttributes.addFlashAttribute(FlashMessageDirective.FLASH_MESSAGE_ATTRIBUTE_NAME, message);
		}
	}


}