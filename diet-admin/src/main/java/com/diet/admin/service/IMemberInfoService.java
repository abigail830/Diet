package com.diet.admin.service;

import com.diet.admin.core.BaseService;
import com.diet.admin.entity.MemberInfo;

/**
 * @author LiuYu
 */ 
public interface IMemberInfoService extends BaseService<MemberInfo> {
    MemberInfo selectByOpenId(String openId);
}

