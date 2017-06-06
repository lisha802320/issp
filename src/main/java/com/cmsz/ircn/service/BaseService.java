package com.cmsz.ircn.service;

/**
 * 基类接口
 */
public interface BaseService<T, Serializable> {
	
	int delete(Long id);

	int insert(T record);

	int insertSelective(T record);

	T find(Long id);

	int updateSelective(T record);

	int update(T record);
	
}
