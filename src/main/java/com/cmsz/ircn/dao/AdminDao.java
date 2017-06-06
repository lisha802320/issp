package com.cmsz.ircn.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.cmsz.ircn.bean.Admin;
import com.cmsz.ircn.common.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Repository
public class AdminDao extends BaseDao<Admin, Serializable> {
	private static final long serialVersionUID = 1L;

	/**
	 * 判断用户名是否存在
	 * 
	 * @param username
	 *            用户名(忽略大小写)
	 * @return 用户名是否存在
	 */
	public boolean usernameExists(String username) {
		Admin admin = getSqlSession().selectOne(getNameSpace(), username);
		return admin == null ? false : true;
	}

	/**
	 * 根据用户名查找管理员
	 * 
	 * @param username
	 *            用户名(忽略大小写)
	 * @return 管理员，若不存在则返回null
	 */
	public Admin findByUsername(String username) {
		return getSqlSession().selectOne(getNameSpace(), username);
	}

	/**
	 * 分页查找所有
	 * 
	 * @param page
	 * @return
	 */
	public PageInfo<Admin> findAll(Page page) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		List<Admin> list = getSqlSession().selectList(getNameSpace());
		PageInfo<Admin> pageInfo = new PageInfo<Admin>(list);
		return pageInfo;
	}
}