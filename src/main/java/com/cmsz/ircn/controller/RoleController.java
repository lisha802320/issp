package com.cmsz.ircn.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cmsz.ircn.bean.Role;
import com.cmsz.ircn.common.Message;
import com.cmsz.ircn.common.Page;
import com.cmsz.ircn.service.RoleService;

/**
 * Controller - 角色
 * 
 */
@Controller
@RequestMapping("/admin/role")
public class RoleController extends BaseController {

	@Resource(name = "roleServiceImpl")
	private RoleService roleService;

	/**
	 * 添加
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add() {
		return "/role/add";
	}

	/**
	 * 保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(Role role, RedirectAttributes redirectAttributes) {
		role.setIsSystem(false);
		// role.setAdmins(null);
		// roleService.save(role);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.do";
	}

	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {
		model.addAttribute("role", roleService.find(id));
		return "/role/edit";
	}

	/**
	 * 更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(Role role, RedirectAttributes redirectAttributes) {
		Role pRole = roleService.find(role.getId());
		if (pRole == null || pRole.getIsSystem()) {
			return ERROR_VIEW;
		}
		// roleService.update(role, "isSystem", "admins");
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.do";
	}

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Page page, ModelMap model) {
		model.addAttribute("page", roleService.findAll(page));
		return "/role/list";
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