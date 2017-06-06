package com.cmsz.ircn.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cmsz.ircn.bean.Role;
import com.cmsz.ircn.common.Page;
import com.cmsz.ircn.dao.RoleDao;
import com.cmsz.ircn.service.RoleService;
import com.github.pagehelper.PageInfo;

/**
 * Service - 角色
 * 
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {

	@Resource
	private RoleDao roleDao;

	@Override
	@Transactional
	@CacheEvict(value = "authorization", allEntries = true)
	public int insert(Role role) {
		return super.insert(role);
	}

	@Override
	@Transactional
	@CacheEvict(value = "authorization", allEntries = true)
	public int update(Role role) {
		return super.update(role);
	}

	@Override
	@Transactional
	@CacheEvict(value = "authorization", allEntries = true)
	public int delete(Long id) {
		return super.delete(id);
	}

	@Override
	public List<Role> findAll() {
		return roleDao.selectAll();
	}

	@Override
	public PageInfo<Role> findAll(Page page) {
		return roleDao.selectAll(page);
	}

}