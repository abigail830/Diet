package com.diet.admin.dao;

import com.diet.admin.core.BaseMapper;
import com.diet.admin.entity.MemberInfo;
import org.apache.ibatis.annotations.Param;

/**
 * @author LiuYu
 */ 
public interface MemberInfoMapper extends BaseMapper<MemberInfo> {
    MemberInfo selectByOpenId(@Param("openId") String openId);
}

