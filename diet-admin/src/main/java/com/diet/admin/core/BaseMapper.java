package com.diet.admin.core;

import tk.mybatis.mapper.annotation.RegisterMapper;
import tk.mybatis.mapper.common.ConditionMapper;
import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @author LiuYu
 * @date 2017/12/20
 */
@RegisterMapper
public interface BaseMapper<T> extends Mapper<T>, ConditionMapper<T>, MySqlMapper<T>, IdsMapper<T> {
    // 特别注意，该接口不能被扫描到，否则会出错
}
