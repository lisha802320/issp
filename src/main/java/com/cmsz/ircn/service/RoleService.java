package com.cmsz.ircn.service;

import java.io.Serializable;
import java.util.List;

import com.cmsz.ircn.bean.Role;
import com.cmsz.ircn.common.Page;
import com.github.pagehelper.PageInfo;

/**
 * Service - 角色
 * 
 */
public interface RoleService extends BaseService<Role, Serializable> {

	public List<Role> findAll();

	public PageInfo<Role> findAll(Page page);

}