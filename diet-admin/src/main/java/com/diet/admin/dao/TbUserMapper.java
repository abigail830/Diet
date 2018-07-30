package com.diet.admin.dao;


import com.diet.admin.core.BaseMapper;
import com.diet.admin.entity.TbUser;
import com.diet.admin.core.BaseMapper;
import com.diet.admin.entity.TbUser;

public interface TbUserMapper extends BaseMapper<TbUser> {
    TbUser findByUserName(final String userName);
}