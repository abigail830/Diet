package com.diet.admin.service;

import com.alibaba.fastjson.JSONObject;
import com.diet.admin.core.BaseService;
import com.diet.admin.entity.WxUserInfo;

/**
 * @author LiuYu
 */ 
public interface IWxUserInfoService extends BaseService<WxUserInfo> {

    /**
     * 根据code获取openid
     * @param code
     * @return
     */
    JSONObject wxLogin(String code);

    WxUserInfo selectByOpenId(String openId);
}

