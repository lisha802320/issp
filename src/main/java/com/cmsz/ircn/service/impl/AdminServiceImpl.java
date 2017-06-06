package com.cmsz.ircn.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cmsz.ircn.bean.Admin;
import com.cmsz.ircn.bean.AdminRole;
import com.cmsz.ircn.bean.Role;
import com.cmsz.ircn.common.Page;
import com.cmsz.ircn.common.Principal;
import com.cmsz.ircn.dao.AdminDao;
import com.cmsz.ircn.dao.AdminRoleDao;
import com.cmsz.ircn.dao.RoleDao;
import com.cmsz.ircn.log.monitor.MonitorableLog;
import com.cmsz.ircn.service.AdminService;
import com.github.pagehelper.PageInfo;

@Service
public class AdminServiceImpl extends BaseServiceImpl<Admin> implements AdminService {

	@Resource
	private AdminDao adminDao;

	@Resource
	private RoleDao roleDao;
	
	@Resource
	private AdminRoleDao adminRoleDao;

	@Resource
	private MonitorableLog log;

	@Transactional(readOnly = true)
	public boolean usernameExists(String username) {
		return adminDao.usernameExists(username);
	}

	@Transactional(readOnly = true)
	public Admin findByUsername(String username) {
		return adminDao.findByUsername(username);
	}

	@Transactional(readOnly = true)
	public List<String> findAuthorities(Long id) {
		List<String> authorities = new ArrayList<String>();
		Admin admin = adminDao.selectByPrimaryKey(id);
		if (admin != null) {
			for (Role role : roleDao.selectRolesByAdmin(admin.getId())) {
				authorities.addAll(roleDao.selectAuthsByRole(role.getId()));
			}
		}
		return authorities;
	}

	@Transactional(readOnly = true)
	public boolean isAuthenticated() {
		Subject subject = SecurityUtils.getSubject();
		if (subject != null) {
			return subject.isAuthenticated();
		}
		return false;
	}

	@Transactional(readOnly = true)
	public Admin getCurrent() {
		Subject subject = SecurityUtils.getSubject();
		if (subject != null) {
			Principal principal = (Principal) subject.getPrincipal();
			if (principal != null) {
				return adminDao.selectByPrimaryKey(principal.getId());
			}
		}
		return null;
	}

	@Transactional(readOnly = true)
	public String getCurrentUsername() {
		Subject subject = SecurityUtils.getSubject();
		if (subject != null) {
			Principal principal = (Principal) subject.getPrincipal();
			if (principal != null) {
				return principal.getUsername();
			}
		}
		return null;
	}

	@Override
	public PageInfo<Admin> findAll(Page page) {
		return adminDao.findAll(page);
	}

	@Override
	public void insertRoles(Long id, Long[] roleIds) {
		AdminRole adminRole = new AdminRole();
		adminRole.setAdmins(id);
		for(Long roleId : roleIds){
			adminRole.setRoles(roleId);
			adminRoleDao.insert(adminRole);
		}
	}

}
