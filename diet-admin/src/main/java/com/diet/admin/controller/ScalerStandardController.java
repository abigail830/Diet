package com.diet.admin.controller;

import com.alibaba.fastjson.JSONObject;
import com.diet.admin.core.BaseController;
import com.diet.admin.entity.MemberInfo;
import com.diet.admin.entity.ScalerStandard;
import com.diet.admin.message.ResponseMsg;
import com.diet.admin.service.IScalerStandardService;
import com.diet.admin.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

/**
 * @author LiuYu
 */
@RestController
@RequestMapping(BaseController.API + "/scalerStandard")
public class ScalerStandardController extends BaseController {

    @Autowired
    private IScalerStandardService scalerStandardService;

    @PostMapping("/calculateEnergy")
    public ResponseMsg calculateEnergy(@RequestBody JSONObject request) {
        ResponseMsg responseMsg = new ResponseMsg();

        List<MemberInfo> users = request.getJSONArray("users").toJavaList(MemberInfo.class);
        int totalEnergies = 0, totalProteins = 0;
        int qu = 35;
        for (MemberInfo memberInfo : users) {
            int sw = Double.valueOf((memberInfo.getHeight() - 105) * 0.9).intValue();
            //计算能量系数
            int age = LocalDate.now().getYear() - DateUtil.formatDate("yyyy-MM-dd", memberInfo.getBirthDate()).getYear();
            if (age >= 65) {
                qu = 30;
            }
            totalEnergies += sw * qu;

            //蛋白质系数
            int ckd = 1;
            totalProteins += sw * ckd;
        }

        List<ScalerStandard> standards = scalerStandardService.selectAll();
        int random = new Random().nextInt(standards.size());
        ScalerStandard standard = standards.get(random);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("totalEnergies", totalEnergies);
        jsonObject.put("totalProteins", totalProteins);
        jsonObject.put("standard", standard);
        jsonObject.put("ratio", (float) (Math.round((totalEnergies / standard.getTargetEnergy()) * 100)) / 100);

        responseMsg.setData(jsonObject);
        return responseMsg;
    }


}

