package com.diet.admin.service;

import com.diet.admin.core.BaseService;
import com.diet.admin.entity.TbUser;

/**
 * @author LiuYu
 */
public interface TbUserService extends BaseService<TbUser> {
    TbUser findByUserName(String userName);
}

