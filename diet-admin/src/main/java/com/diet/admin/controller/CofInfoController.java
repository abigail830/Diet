package com.diet.admin.controller;

import com.alibaba.fastjson.JSONObject;
import com.diet.admin.core.BaseController;
import com.diet.admin.entity.CofInfo;
import com.diet.admin.entity.MemberInfo;
import com.diet.admin.message.ResponseMsg;
import com.diet.admin.service.ICofInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author LiuYu
 */
@RestController
@RequestMapping(BaseController.API + "/cofInfo")
public class CofInfoController extends BaseController {

    @Autowired
    private ICofInfoService cofInfoService;

    @PostMapping("/list")
    public ResponseMsg list(@RequestBody JSONObject request) {
        ResponseMsg responseMsg = new ResponseMsg();

        Example example = new Example(CofInfo.class);
        Example.Criteria criteria = example.createCriteria();
        if (request.containsKey("name")) {
            criteria.andLike("name", "%" + request.getString("name") + "%");
        }

        List<CofInfo> result = cofInfoService.selectByExample(example);
        responseMsg.setData(result);
        return responseMsg;
    }

    @PostMapping("/listByName")
    public ResponseMsg listByName(@RequestBody JSONObject request) {
        ResponseMsg responseMsg = new ResponseMsg();

        if (request.containsKey("name")) {
            List<CofInfo> result = cacheService.getListByKeyPattern("cofInfo_*"+request.getString("name")+"*", CofInfo.class);
            responseMsg.setData(result);
        }
        return responseMsg;
    }
}

