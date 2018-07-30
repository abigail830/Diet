package com.diet.admin.core;

import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 通用接口
 *
 * @author LiuYu
 */
public interface BaseService<T> {

    /**
     * 保存一个实体，null的属性也会保存，不会使用数据库默认值
     *
     * @param entity 实体
     * @return
     */
    int insert(T entity);

    /**
     * 保存一个实体，null的属性不会保存，会使用数据库默认值
     *
     * @param entity 实体
     * @return
     */
    int insertSelective(T entity);

    /**
     * 批量插入，支持批量插入的数据库可以使用，例如MySQL,H2等，另外该接口限制实体包含`id`属性并且必须为自增列
     *
     * @param recordList
     * @return
     */
    int insertList(List<T> recordList);

    /**
     * 根据主键更新实体全部字段，null值会被更新
     *
     * @param entity 实体
     * @return
     */
    int updateByPrimaryKey(T entity);

    /**
     * 根据主键更新属性不为null的值
     *
     * @param entity 实体
     * @return
     */
    int updateByPrimaryKeySelective(T entity);

    /**
     * 根据实体属性作为条件进行删除
     *
     * @param entity 实体
     * @return
     */
    int delete(T entity);

    /**
     * 根据主键字段进行删除
     *
     * @param key 主键
     * @return
     */
    int deleteByPrimaryKey(Object key);

    /**
     * 根据主键字符串进行删除，类中只有存在一个带有@Id注解的字段
     *
     * @param ids 如 "1,2,3,4"
     * @return
     */
    int deleteByIds(String ids);

    /**
     * 根据主键字段进行查询，方法参数必须包含完整的主键属性
     *
     * @param key 主键
     * @return
     */
    T selectByPrimaryKey(Object key);

    /**
     * 根据主键字符串进行查询，类中只有存在一个带有@Id注解的字段
     *
     * @param ids 如 "1,2,3,4"
     * @return
     */
    List<T> selectByIds(String ids);

    /**
     * 根据实体中的属性值进行查询
     *
     * @param entity 实体
     * @return
     */
    List<T> select(T entity);

    /**
     * 根据实体中的属性进行查询，只能有一个返回值，有多个结果是抛出异常
     *
     * @param entity 实体
     * @return
     */
    T selectOne(T entity);

    /**
     * 根据实体中的属性查询总数，查询条件使用等号
     *
     * @param entity 实体
     * @return
     */
    int selectCount(T entity);

    /**
     * 根据Example条件进行查询总数
     *
     * @param example 查询条件
     * @return
     */
    int selectCountByExample(Object example);

    /**
     * 根据Example条件进行查询
     *
     * @param example 查询条件
     * @return
     */
    List<T> selectByExample(Object example);

    /**
     * 根据Example条件进行分页查询
     *
     * @param example  查询条件
     * @param pageNum  默认为1
     * @param pageSize 默认为10
     * @return
     */
    PageInfo<T> selectByExample(Object example, int pageNum, int pageSize);

    /**
     * 查询全部结果
     *
     * @return
     */
    List<T> selectAll();

    /**
     * 是否已存在
     *
     * @param key
     * @return
     */
    boolean existsWithPrimaryKey(Object key);
}
