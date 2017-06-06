package com.cmsz.ircn.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.cmsz.ircn.bean.Role;
import com.cmsz.ircn.common.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Repository
public class RoleDao extends BaseDao<Role, Serializable> {
	private static final long serialVersionUID = 1L;

	public List<Role> selectRolesByAdmin(Long id) {
		return getSqlSession().selectList(this.getNameSpace(), id);
	}

	public List<String> selectAuthsByRole(Long id) {
		return getSqlSession().selectList(this.getNameSpace(), id);
	}

	public List<Role> selectAll() {
		return getSqlSession().selectList(this.getNameSpace());
	}

	/**
	 * 分页查找所有
	 * 
	 * @param page
	 * @return
	 */
	public PageInfo<Role> selectAll(Page page) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		List<Role> list = getSqlSession().selectList(getNameSpace());
		PageInfo<Role> pageInfo = new PageInfo<Role>(list);
		return pageInfo;
	}
}