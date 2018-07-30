package com.diet.admin.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.diet.admin.core.BaseController;
import com.diet.admin.entity.MemberInfo;
import com.diet.admin.entity.WxUserInfo;
import com.diet.admin.entity.WxUserInfoExt;
import com.diet.admin.message.ResponseMsg;
import com.diet.admin.service.IMemberInfoService;
import com.diet.admin.service.IWxUserInfoExtService;
import com.diet.admin.service.IWxUserInfoService;
import com.diet.admin.utils.WxDecode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author LiuYu
 */
@RestController
@RequestMapping(BaseController.API + "/wxUserInfo")
public class WxUserInfoController extends BaseController {

    @Autowired
    private IWxUserInfoService wxUserInfoService;

    @Autowired
    private IWxUserInfoExtService wxUserInfoExtService;

    @Autowired
    private IMemberInfoService memberInfoService;

    @PostMapping("/wxLogin")
    public ResponseMsg wxLogin(@RequestBody JSONObject request) {
        ResponseMsg responseMsg = new ResponseMsg();
        String code = request.getString("code");
        JSONObject result = wxUserInfoService.wxLogin(code);
        cacheService.put("session_key", result.getString("session_key"));
        result.remove("session_key");

        WxUserInfo userInfo = wxUserInfoService.selectByOpenId(result.getString("openid"));
        if (userInfo != null) {
            result.put("isWxLogin", true);
            MemberInfo memberInfoReq = new MemberInfo();
            memberInfoReq.setWxUserId(userInfo.getId());

            MemberInfo memberInfo = memberInfoService.selectOne(memberInfoReq);
            result.put("memberInfo", memberInfo);
        } else {
            result.put("isWxLogin", false);
        }

        responseMsg.setData(result);
        return responseMsg;
    }

    @PostMapping("/sendCode")
    public ResponseMsg sendCode(@RequestBody JSONObject request) {
        ResponseMsg responseMsg = new ResponseMsg();
        cacheService.put("sms_code_" + request.getString("openid") + "_" + request.getString("phone"), 123456);
        responseMsg.setData(cacheService.get("sms_code_" + request.getString("openid") + "_" + request.getString("phone")));
        return responseMsg;
    }

    @PostMapping("/bindWxUser")
    public ResponseMsg bindWxUser(@RequestBody JSONObject request) {
        ResponseMsg responseMsg = new ResponseMsg();
        /*String cacheChkCode = String.valueOf(cacheService.get("sms_code_" + request.getString("openid") + "_" + request.getString("phone")));
        String chkCode = request.getString("chkCode");
        if (!cacheChkCode.equals(chkCode)) {
            return new ResponseMsg(-1, "验证码错误");
        }*/
        WxUserInfo userInfo = JSON.parseObject(request.getJSONObject("userInfo").toJSONString(), WxUserInfo.class);
//        userInfo.setPhone(request.getString("phone"));
        userInfo.setCreateTime(new Date());
        userInfo.setUpdateTime(new Date());

        String encryptedData = request.getString("encryptedData");
        String iv = request.getString("iv");
        String session_key = String.valueOf(cacheService.get("session_key"));
        JSONObject data = WxDecode.getUserInfo(encryptedData, session_key, iv);

        String openId = data.getString("openId");
//        userInfo.setOpenId(data.getString("openId"));
//        userInfo.setOpenId("123");
        userInfo.setUnionId(data.getString("unionId"));

//        WxUserInfo userInfoReq = new WxUserInfo();
        WxUserInfo count = wxUserInfoService.selectByOpenId(openId);

        JSONObject result = new JSONObject();
        if(count != null){
            userInfo.setId(count.getId());
            wxUserInfoService.updateByPrimaryKeySelective(userInfo);
        }else {
            wxUserInfoService.insert(userInfo);

            WxUserInfoExt extParams = new WxUserInfoExt();
            extParams.setOpenId(data.getString("openId"));
            extParams.setWxUserId(userInfo.getId());

            WxUserInfoExt ext = wxUserInfoExtService.selectOne(extParams);
            if(ext == null){
                extParams.setCreateTime(new Date());
                wxUserInfoExtService.insert(extParams);
                result.put("wxUserExtId", extParams.getId());
            } else {
                result.put("wxUserExtId", ext.getId());
            }
        }

        result.put("userInfo", userInfo);
        responseMsg.setData(result);
        return responseMsg;
    }

}

