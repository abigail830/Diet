package com.diet.admin.dao;

import com.diet.admin.core.BaseMapper;
import com.diet.admin.entity.WxUserInfo;
import org.apache.ibatis.annotations.Param;

/**
 * @author LiuYu
 */ 
public interface WxUserInfoMapper extends BaseMapper<WxUserInfo> {
    WxUserInfo selectByOpenId(@Param("openId") String openId);
}

