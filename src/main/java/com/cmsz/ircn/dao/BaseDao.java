package com.cmsz.ircn.dao;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cmsz.ircn.bean.Admin;
import com.cmsz.ircn.utils.GenericsUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

@Repository
public class BaseDao<T, PK extends Serializable> extends SqlSessionDaoSupport implements Serializable {

	private static final long serialVersionUID = 7623507504198633838L;

	private final String DOT = ".";

	private final String POSTFIX = "Dao";
	
	@Autowired
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		super.setSqlSessionFactory(sqlSessionFactory);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getNameSpace() {
		String packageName = this.getClass().getPackage().getName();
		Class<T> clazz = (Class) GenericsUtils.getSuperClassGenricType(this.getClass());
		String className = clazz.getSimpleName() + POSTFIX;
		String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
		return packageName + DOT + className + DOT + methodName;
	}

	public int insert(T entity) {
		return getSqlSession().insert(this.getNameSpace(), entity);
	}

	public int insertSelective(T record) {
		return getSqlSession().insert(this.getNameSpace(), record);
	}

	public T selectByPrimaryKey(PK id) {
		return getSqlSession().selectOne(this.getNameSpace(), id);
	}

	public int updateByPrimaryKey(T record) {
		return getSqlSession().update(this.getNameSpace(), record);
	}

	public int updateByPrimaryKeySelective(T record) {
		return getSqlSession().update(this.getNameSpace(), record);
	}

	public int deleteByPrimaryKey(PK id) {
		return getSqlSession().delete(this.getNameSpace(), id);
	}
	
    @SuppressWarnings({ "rawtypes", "unchecked" })  
    public List<T> findTop(int top, String statementKey, Object parameter) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {  
        Map params = new HashMap();  
        if (parameter != null) {  
            if (parameter instanceof Map) {  
                params.putAll((Map) parameter);  
            } else {  
                Map parameterObject = PropertyUtils.describe(parameter);  
                params.putAll(parameterObject);  
            }  
        }  
        List<T> list = getSqlSession().selectList(statementKey, params, new RowBounds(0, top));  
        return list;  
    }  
  
    public T findTopOne(String statementKey, Object parameter) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {  
        List<T> list = findTop(1, statementKey, parameter);  
        return CollectionUtils.isEmpty(list) ? null : list.get(0);  
    }  
      
    @SuppressWarnings({ "rawtypes", "unchecked" })  
    public <M> PageInfo<M> findPageModel(String statementKey, Page page, Object parameter) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {  
        Map params = new HashMap();  
        if (parameter != null) {  
            if (parameter instanceof Map) {  
                params.putAll((Map) parameter);  
            } else {  
                Map parameterObject = PropertyUtils.describe(parameter);  
                params.putAll(parameterObject);  
            }  
        }  
        PageHelper.startPage(page.getPageNum(), page.getPageSize());  
        List<M> list = getSqlSession().selectList(statementKey, params);  
        PageInfo<M> pageInfo = new PageInfo(list);  
        return pageInfo;  
    } 

}