package com.diet.admin.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.diet.admin.core.BaseController;
import com.diet.admin.entity.NutritionRecomdPlan;
import com.diet.admin.entity.ScalerStandard;
import com.diet.admin.message.ResponseMsg;
import com.diet.admin.service.INutritionRecomdPlanService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @author LiuYu
 */ 
@RestController
@RequestMapping(BaseController.API + "/nutritionRecomdPlan")
public class NutritionRecomdPlanController extends BaseController {

    @Autowired
    private INutritionRecomdPlanService recomdPlanService;

    @PostMapping("/saveRecomdPlan")
    public ResponseMsg saveRecomdPlan(@RequestBody JSONObject request){
        ResponseMsg responseMsg = new ResponseMsg();
        ScalerStandard scalerStandard = JSON.parseObject(request.getJSONObject("standard").toJSONString(), ScalerStandard.class);
        Integer memberId = request.getInteger("memberId");
        NutritionRecomdPlan plan = new NutritionRecomdPlan();
        BeanUtils.copyProperties(scalerStandard, plan, "id");
        plan.setMemberId(memberId);
        plan.setCreateTime(new Date());
        plan.setUpdateTime(new Date());
        recomdPlanService.insert(plan);

        responseMsg.setData(plan);
        return responseMsg;
    }

    @PostMapping("/list")
    public ResponseMsg list(){
        ResponseMsg responseMsg = new ResponseMsg();

        List<NutritionRecomdPlan> recomdPlanList = recomdPlanService.selectAll();

        if (recomdPlanList != null && !recomdPlanList.isEmpty()) {
            int random = new Random().nextInt(recomdPlanList.size());
            responseMsg.setData(recomdPlanList.get(random));
        }

        return responseMsg;
    }
}

