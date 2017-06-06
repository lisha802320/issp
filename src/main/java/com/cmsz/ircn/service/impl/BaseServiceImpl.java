package com.cmsz.ircn.service.impl;

import java.io.Serializable;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmsz.ircn.dao.BaseDao;
import com.cmsz.ircn.log.monitor.MonitorableLog;
import com.cmsz.ircn.service.BaseService;

@Service
public abstract class BaseServiceImpl<T> implements BaseService<T, Serializable> {
	@Resource
	private MonitorableLog log;
	
	@Autowired
	private BaseDao<T, Serializable> baseDao;
	
	public BaseDao<T, Serializable> getBaseDao() {  
        return baseDao;  
    }  
	
    public void setBaseDao(BaseDao<T, Serializable> baseDao) {  
        this.baseDao = baseDao;  
    } 

	@Override
	public int delete(Long id) {
		return baseDao.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(T record) {
		return baseDao.insert(record);
	}

	@Override
	public int insertSelective(T record) {
		return baseDao.insertSelective(record);
	}

	@Override
	public T find(Long id) {
		return baseDao.selectByPrimaryKey(id);
	}

	@Override
	public int updateSelective(T record) {
		return baseDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int update(T record) {
		return baseDao.updateByPrimaryKey(record);
	}
	


}
