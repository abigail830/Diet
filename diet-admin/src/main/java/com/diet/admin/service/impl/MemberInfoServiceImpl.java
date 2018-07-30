package com.diet.admin.service.impl;

import com.diet.admin.core.impl.BaseServiceImpl;
import com.diet.admin.dao.MemberInfoMapper;
import com.diet.admin.entity.MemberInfo;
import com.diet.admin.service.IMemberInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author LiuYu
 */ 
@Service
public class MemberInfoServiceImpl extends BaseServiceImpl<MemberInfo> implements IMemberInfoService {

    @Autowired
    private MemberInfoMapper memberInfoMapper;

    @Override
    public MemberInfo selectByOpenId(String openId) {
        if (StringUtils.isBlank(openId)) {
            return null;
        }
        return memberInfoMapper.selectByOpenId(openId);
    }
}

