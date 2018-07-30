package com.diet.admin.service.impl;

import com.diet.admin.core.impl.BaseServiceImpl;
import com.diet.admin.entity.TbUser;
import com.diet.admin.service.TbUserService;
import org.springframework.stereotype.Service;

/**
 * @author LiuYu
 */
@Service
public class TbUserServiceImpl extends BaseServiceImpl<TbUser> implements TbUserService {

    @Override
    public TbUser findByUserName(String userName) {
        TbUser tbUser = new TbUser();
        tbUser.setUserName(userName);
        return selectOne(tbUser);
    }
}

