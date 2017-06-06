package com.cmsz.ircn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cmsz.ircn.common.Message;
import com.cmsz.ircn.common.Page;

/**
 * Controller - 业务受理
 * 
 */
@Controller
@RequestMapping("/admin/businessAccepted")
public class BusinessAcceptedController extends BaseController {

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Page page, ModelMap model) {
		return "/business_accepted/list";
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody Message delete(Long[] ids) {
		return SUCCESS_MESSAGE;
	}

}