package com.diet.admin.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.diet.admin.core.impl.BaseServiceImpl;
import com.diet.admin.dao.WxUserInfoMapper;
import com.diet.admin.entity.WxUserInfo;
import com.diet.admin.service.IWxUserInfoService;
import com.diet.admin.utils.HttpUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @author LiuYu
 */ 
@Service
public class WxUserInfoServiceImpl extends BaseServiceImpl<WxUserInfo> implements IWxUserInfoService {

    @Value("${wx.sns.jscode2session}")
    private String jscode2sessionUrl;

    @Autowired
    private WxUserInfoMapper wxUserInfoMapper;

    @Override
    public JSONObject wxLogin(String code) {
        String url = jscode2sessionUrl + code;
        String result = HttpUtil.post(url, new JSONObject().toJSONString());
        logger.debug("wxLogin result{}", result);
        return JSON.parseObject(result);
    }

    @Override
    public WxUserInfo selectByOpenId(String openId){
        if (StringUtils.isBlank(openId)) {
            return null;
        }
        return wxUserInfoMapper.selectByOpenId(openId);
    }
}

