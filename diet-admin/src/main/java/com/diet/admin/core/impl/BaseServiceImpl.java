package com.diet.admin.core.impl;

import com.diet.admin.caches.BaseCacheService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.diet.admin.core.BaseMapper;
import com.diet.admin.core.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * @author LiuYu
 */
@Service
public abstract class BaseServiceImpl<T> implements BaseService<T> {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected BaseCacheService cacheService;

    @Autowired
    protected BaseMapper<T> baseMapper;

    /**
     * 保存一个实体，null的属性也会保存，不会使用数据库默认值
     *
     * @param entity 实体
     * @return
     */
    @Override
    public int insert(T entity) {
        return baseMapper.insert(entity);
    }

    /**
     * 保存一个实体，null的属性不会保存，会使用数据库默认值
     *
     * @param entity 实体
     * @return
     */
    @Override
    public int insertSelective(T entity) {
        return baseMapper.insertSelective(entity);
    }

    /**
     * 批量插入，支持批量插入的数据库可以使用，例如MySQL,H2等，另外该接口限制实体包含`id`属性并且必须为自增列
     *
     * @param recordList
     * @return
     */
    @Override
    public int insertList(List<T> recordList) {
        return baseMapper.insertList(recordList);
    }

    /**
     * 根据主键更新实体全部字段，null值会被更新
     *
     * @param entity 实体
     * @return
     */
    @Override
    public int updateByPrimaryKey(T entity) {
        return baseMapper.updateByPrimaryKey(entity);
    }

    /**
     * 根据主键更新属性不为null的值
     *
     * @param entity 实体
     * @return
     */
    @Override
    public int updateByPrimaryKeySelective(T entity) {
        return baseMapper.updateByPrimaryKeySelective(entity);
    }

    /**
     * 根据实体属性作为条件进行删除，查询条件使用等号
     *
     * @param entity 实体
     * @return
     */
    @Override
    public int delete(T entity) {
        return baseMapper.delete(entity);
    }

    /**
     * 根据主键字段进行删除，方法参数必须包含完整的主键属性
     *
     * @param key 主键
     * @return
     */
    @Override
    public int deleteByPrimaryKey(Object key) {
        return baseMapper.deleteByPrimaryKey(key);
    }

    /**
     * 根据主键字符串进行删除，类中只有存在一个带有@Id注解的字段
     *
     * @param ids 如 "1,2,3,4"
     * @return
     */
    @Override
    public int deleteByIds(String ids) {
        return baseMapper.deleteByIds(ids);
    }

    /**
     * 根据主键字段进行查询，方法参数必须包含完整的主键属性，查询条件使用等号
     *
     * @param key 主键
     * @return
     */
    @Override
    public T selectByPrimaryKey(Object key) {
        return baseMapper.selectByPrimaryKey(key);
    }

    /**
     * 根据主键字符串进行查询，类中只有存在一个带有@Id注解的字段
     *
     * @param ids 如 "1,2,3,4"
     * @return
     */
    @Override
    public List<T> selectByIds(String ids) {
        return baseMapper.selectByIds(ids);
    }

    /**
     * 根据实体中的属性值进行查询，查询条件使用等号
     *
     * @param entity 实体
     * @return
     */
    @Override
    public List<T> select(T entity) {
        return baseMapper.select(entity);
    }

    /**
     * 根据实体中的属性进行查询，只能有一个返回值，有多个结果是抛出异常，查询条件使用等号
     *
     * @param entity 实体
     * @return
     */
    @Override
    public T selectOne(T entity) {
        return baseMapper.selectOne(entity);
    }

    /**
     * 根据实体中的属性查询总数，查询条件使用等号
     *
     * @param entity 实体
     * @return
     */
    @Override
    public int selectCount(T entity) {
        return baseMapper.selectCount(entity);
    }

    ;

    /**
     * 根据Example条件进行查询总数
     *
     * @param example 查询条件
     * @return
     */
    @Override
    public int selectCountByExample(Object example) {
        return baseMapper.selectCountByExample(example);
    }

    /**
     * 根据Example条件进行查询
     *
     * @param example 查询条件
     * @return
     */
    @Override
    public List<T> selectByExample(Object example) {
        return baseMapper.selectByExample(example);
    }

    /**
     * 根据Example条件进行分页查询
     *
     * @param example  查询条件
     * @param pageNum  默认为1
     * @param pageSize 默认为10
     * @return
     */
    @Override
    public PageInfo<T> selectByExample(Object example, int pageNum, int pageSize) {
        //分页查询
        if (pageNum < 1) {
            pageNum = 1;
        }
        if (pageSize < 1) {
            pageSize = 10;
        }
        PageHelper.startPage(pageNum, pageSize);
        if (example == null) {
            example = new Example(entityClass());
        }
        List<T> list = baseMapper.selectByExample(example);
        PageInfo<T> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    /**
     * 查询全部结果
     *
     * @return
     */
    @Override
    public List<T> selectAll() {
        return baseMapper.selectAll();
    }

    private Class entityClass() {
        Type genType = getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        return (Class) params[0];
    }

    @Override
    public boolean existsWithPrimaryKey(Object key) {
        return baseMapper.existsWithPrimaryKey(key);
    }
}
