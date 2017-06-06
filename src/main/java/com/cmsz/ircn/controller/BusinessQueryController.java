package com.cmsz.ircn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cmsz.ircn.common.Message;
import com.cmsz.ircn.common.Page;

/**
 * Controller - 角色
 * 
 */
@Controller
@RequestMapping("/admin/businessQuery")
public class BusinessQueryController extends BaseController {

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Page page, ModelMap model) {
		return "/business_query/list";
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody Message delete(Long[] ids) {
		// if (ids != null) {
		// for (Long id : ids) {
		// Role role = roleService.find(id);
		// if (role != null && (role.getIsSystem() || (role.getAdmins() != null
		// && !role.getAdmins().isEmpty()))) {
		// return Message.error("admin.role.deleteExistNotAllowed",
		// role.getName());
		// }
		// }
		// roleService.delete(ids);
		// }
		return SUCCESS_MESSAGE;
	}

}