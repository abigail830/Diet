package com.diet.admin.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.diet.admin.core.BaseController;
import com.diet.admin.entity.MemberInfo;
import com.diet.admin.message.ResponseMsg;
import com.diet.admin.service.IMemberInfoService;
import com.diet.admin.service.IWxUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * @author LiuYu
 */
@RestController
@RequestMapping(BaseController.API + "/memberInfo")
public class MemberInfoController extends BaseController {

    @Autowired
    private IMemberInfoService memberInfoService;

    @PostMapping("/insert")
    public ResponseMsg insert(@RequestBody JSONObject request) {
        ResponseMsg responseMsg = new ResponseMsg();

        MemberInfo memberInfo = JSON.parseObject(request.getJSONObject("user").toJSONString(), MemberInfo.class);
        JSONArray regionArr = JSONArray.parseArray(memberInfo.getRegion());
        memberInfo.setProvince(regionArr.getString(0));
        memberInfo.setCity(regionArr.getString(1));
        memberInfo.setArea(regionArr.getString(2));
        memberInfo.setCreateTime(new Date());
        memberInfo.setUpdateTime(new Date());

        memberInfoService.insert(memberInfo);
        responseMsg.setData(request);
        return responseMsg;
    }

    @PostMapping("/save")
    public ResponseMsg save(@RequestBody JSONObject request) {
        ResponseMsg responseMsg = new ResponseMsg();

        MemberInfo memberInfo = JSON.parseObject(request.getJSONObject("user").toJSONString(), MemberInfo.class);
        JSONArray regionArr = JSONArray.parseArray(memberInfo.getRegion());
        memberInfo.setProvince(regionArr.getString(0));
        memberInfo.setCity(regionArr.getString(1));
        memberInfo.setArea(regionArr.getString(2));
        memberInfo.setUpdateTime(new Date());

        if (memberInfo.getId() != null) {
            memberInfoService.updateByPrimaryKey(memberInfo);
        } else {
            memberInfo.setCreateTime(new Date());
            memberInfoService.insert(memberInfo);
        }
        responseMsg.setData(memberInfo);
        return responseMsg;
    }

    @PostMapping("/list")
    public ResponseMsg list(@RequestBody JSONObject request) {
        ResponseMsg responseMsg = new ResponseMsg();

        Example example = new Example(MemberInfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", request.getInteger("userId"));

        List<MemberInfo> result = memberInfoService.selectByExample(example);
        responseMsg.setData(result);
        return responseMsg;
    }

    @PostMapping("/listByOpenId")
    public ResponseMsg listByOpenId(@RequestBody JSONObject request) {
        ResponseMsg responseMsg = new ResponseMsg();

        String openId = request.getString("openId");
        MemberInfo memberInfo = memberInfoService.selectByOpenId(openId);
        if (memberInfo == null) {
            return responseMsg;
        }

        responseMsg.setData(memberInfo);
        return responseMsg;
    }
}

