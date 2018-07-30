package com.diet.admin.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.diet.admin.core.BaseController;
import com.diet.admin.entity.*;
import com.diet.admin.message.ResponseMsg;
import com.diet.admin.service.*;
import com.diet.admin.utils.RandomGeneratorUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

/**
 * @author LiuYu
 */
@RestController
@RequestMapping(BaseController.API + "/recipeInfo")
public class RecipeInfoController extends BaseController {

    @Value("${diet.dailyenergy.allot}")
    private String[] allot;

    @Autowired
    private IRecipeInfoService recipeInfoService;

    @Autowired
    private ICofInfoService cofInfoService;

    @Autowired
    private IMemberRecipeService memberRecipeService;

    @Autowired
    private IMemberInfoService memberInfoService;

    @Autowired
    private INutritionRecomdPlanService recomdPlanService;

    @PostMapping("/recomdRecipe")
    public ResponseMsg list(@RequestBody JSONObject request) {
        ResponseMsg responseMsg = new ResponseMsg();

        MemberInfo memberInfo = JSON.parseObject(request.getJSONObject("user").toJSONString(), MemberInfo.class);
        ScalerStandard standard = JSON.parseObject(request.getJSONObject("cofStandard").toJSONString(), ScalerStandard.class);
        float totalEnergies = request.getFloatValue("totalEnergies");
        responseMsg.setData(createRecomdRecipe(memberInfo, standard, totalEnergies));
        return responseMsg;
    }

    private List<MemberRecipe> createRecomdRecipe(MemberInfo memberInfo, ScalerStandard standard, float totalEnergies){
        Example example = new Example(MemberRecipe.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("memberId", memberInfo.getId());

        Calendar todayStart = Calendar.getInstance();
        todayStart.set(Calendar.HOUR_OF_DAY, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);

        criteria.andBetween("createTime", todayStart.getTime(), new Date());

        List<MemberRecipe> memberRecipeList = memberRecipeService.selectByExample(example);
        if (memberRecipeList != null && !memberRecipeList.isEmpty()) {
            for (MemberRecipe memberRecipe : memberRecipeList) {
                Example recipeExample = new Example(RecipeInfo.class);
                Example.Criteria recipeCriteria = recipeExample.createCriteria();
                recipeCriteria.andIn("id", Arrays.asList(memberRecipe.getRecipeIds().split(",")));

                memberRecipe.setRecipeInfos(recipeInfoService.selectByExample(recipeExample));
            }
            return memberRecipeList;
        }

        float breakfastEng = totalEnergies * Float.valueOf(allot[0]);
        float lunchEng = totalEnergies * Float.valueOf(allot[1]);
        float dinnerEng = totalEnergies - breakfastEng - lunchEng;

        List<RecipeInfo> recipeInfos1 = getBreakfastRecipe(memberInfo, breakfastEng, standard);
        List<RecipeInfo> recipeInfos2 = getRecomdRecipe(memberInfo, lunchEng, 2, standard);
        List<RecipeInfo> recipeInfos3 = getRecomdRecipe(memberInfo, dinnerEng, 3, standard);

        long batchId = Long.valueOf(System.currentTimeMillis() + RandomGeneratorUtil.generateNum(4));

        List<MemberRecipe> memberRecipes = new ArrayList<>();
        memberRecipes.add(addMemberRecipe(batchId, 1, breakfastEng, memberInfo.getId(), recipeInfos1));
        memberRecipes.add(addMemberRecipe(batchId, 2, lunchEng, memberInfo.getId(), recipeInfos2));
        memberRecipes.add(addMemberRecipe(batchId, 3, dinnerEng, memberInfo.getId(), recipeInfos3));

        return memberRecipes;
    }

    @PostMapping("/listByOpenId")
    public ResponseMsg listByOpenId(@RequestBody JSONObject request) {
        ResponseMsg responseMsg = new ResponseMsg();

        String openId = request.getString("openId");
        MemberInfo memberInfo = memberInfoService.selectByOpenId(openId);
        if(memberInfo == null){
            return new ResponseMsg(-2, "请先保存个人基本信息");
        }

        Example example = new Example(NutritionRecomdPlan.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("memberId", memberInfo.getId());
        example.setOrderByClause(" id desc");

        List<NutritionRecomdPlan> plans = recomdPlanService.selectByExample(example);

        if (plans == null || plans.isEmpty()) {
            return responseMsg;
        }
        ScalerStandard standard = new ScalerStandard();
        BeanUtils.copyProperties(plans.get(0), standard, "id");

        responseMsg.setData(createRecomdRecipe(memberInfo, standard, standard.getTargetEnergy()));
        return responseMsg;
    }

    /**
     * 获取早餐食谱
     *
     * @param leftEnergy
     * @param standard
     * @return
     */
    private List<RecipeInfo> getBreakfastRecipe(MemberInfo memberInfo, float leftEnergy, ScalerStandard standard) {
        List<RecipeInfo> recipeInfos = new ArrayList<>();
        Set<Integer> existIds = new HashSet<>();
        if (standard.getMilk() != null && standard.getMilk() > 0) {
            RecipeInfo recipeInfo = recipeInfoService.getRecomdRecipe(null, null, memberInfo.getBloodFat(),
                    memberInfo.getBloodSugar(), memberInfo.getBloodPress(), memberInfo.getUricAcid(),
                    memberInfo.getKidney(), leftEnergy, 1, "milk", existIds);
            if (recipeInfo != null) {
                leftEnergy -= recipeInfo.getTotalEnergies();
                recipeInfo.setCofInfos(cofInfoService.getCofinfos(recipeInfo));
                recipeInfos.add(recipeInfo);
                existIds.add(recipeInfo.getId());
            }
        }

        if (leftEnergy > 0 && standard.getEgg() != null && standard.getEgg() > 0) {
            RecipeInfo recipeInfo = recipeInfoService.getRecomdRecipe(null, null, memberInfo.getBloodFat(),
                    memberInfo.getBloodSugar(), memberInfo.getBloodPress(), memberInfo.getUricAcid(),
                    memberInfo.getKidney(), leftEnergy, 1, "egg", existIds);
            if (recipeInfo != null) {
                leftEnergy -= recipeInfo.getTotalEnergies();
                recipeInfo.setCofInfos(cofInfoService.getCofinfos(recipeInfo));
                recipeInfos.add(recipeInfo);
                existIds.add(recipeInfo.getId());
            }
        }

        if (leftEnergy > 0) {
            Example example = new Example(RecipeInfo.class);
            Example.Criteria criteria = example.createCriteria();
            if(!existIds.isEmpty()) {
                criteria.andNotIn("id", existIds);
            }
            if (memberInfo.getBloodFat() != null) {
                criteria.andEqualTo("bloodFat", memberInfo.getBloodFat());
            }

            if (memberInfo.getBloodSugar() != null) {
                criteria.andEqualTo("bloodSugar", memberInfo.getBloodSugar());
            }

            if (memberInfo.getBloodPress() != null) {
                criteria.andEqualTo("bloodPress", memberInfo.getBloodPress());
            }

            if (memberInfo.getUricAcid() != null) {
                criteria.andEqualTo("uricAcid", memberInfo.getUricAcid());
            }

            if (memberInfo.getKidney() != null) {
                criteria.andEqualTo("kidney", memberInfo.getKidney());
            }
            criteria.andLike("mealTime", "%|1|%");
            criteria.andLessThanOrEqualTo("totalEnergies", leftEnergy);
            criteria.andLike("nutritionTags", "%other%");
            example.setOrderByClause(" totalEnergies desc");

            List<RecipeInfo> result = recipeInfoService.selectByExample(example);
            if(result != null && !result.isEmpty()) {
                RecipeInfo recipeInfo = result.get(0);
                recipeInfo.setCofInfos(cofInfoService.getCofinfos(recipeInfo));
                recipeInfos.add(recipeInfo);
            }
        }
        return recipeInfos;
    }

    /**
     * 获取午餐、晚餐食谱
     *
     * @param leftEnergy
     * @param mealTime
     * @param standard
     * @return
     */
    private List<RecipeInfo> getRecomdRecipe(MemberInfo memberInfo, float leftEnergy, int mealTime, ScalerStandard standard) {
        List<RecipeInfo> recipeInfos = new ArrayList<>();
        Set<Integer> existIds = new HashSet<>();
        if (standard.getLean() != null && standard.getLean() > 0) {
            RecipeInfo recipeInfo = recipeInfoService.getRecomdRecipe(null, null, memberInfo.getBloodFat(),
                    memberInfo.getBloodSugar(), memberInfo.getBloodPress(), memberInfo.getUricAcid(),
                    memberInfo.getKidney(), leftEnergy, mealTime, "lean", existIds);
            if (recipeInfo != null) {
                recipeInfo.setCofInfos(cofInfoService.getCofinfos(recipeInfo));
                leftEnergy -= recipeInfo.getTotalEnergies();
                recipeInfos.add(recipeInfo);
                existIds.add(recipeInfo.getId());
            }
        }

        if (leftEnergy > 0 && standard.getMelon() != null && standard.getMelon() > 0) {
            RecipeInfo recipeInfo = recipeInfoService.getRecomdRecipe(null, null, memberInfo.getBloodFat(),
                    memberInfo.getBloodSugar(), memberInfo.getBloodPress(), memberInfo.getUricAcid(),
                    memberInfo.getKidney(), leftEnergy, mealTime, "melon", existIds);
            if (recipeInfo != null) {
                recipeInfo.setCofInfos(cofInfoService.getCofinfos(recipeInfo));
                leftEnergy -= recipeInfo.getTotalEnergies();
                recipeInfos.add(recipeInfo);
                existIds.add(recipeInfo.getId());
            }
        }

        if (leftEnergy > 0 && standard.getLeafyVegetable() != null && standard.getLeafyVegetable() > 0) {
            RecipeInfo recipeInfo = recipeInfoService.getRecomdRecipe(null, null, memberInfo.getBloodFat(),
                    memberInfo.getBloodSugar(), memberInfo.getBloodPress(), memberInfo.getUricAcid(),
                    memberInfo.getKidney(), leftEnergy, mealTime, "leafyVegetable", existIds);
            if (recipeInfo != null) {
                recipeInfo.setCofInfos(cofInfoService.getCofinfos(recipeInfo));
                leftEnergy -= recipeInfo.getTotalEnergies();
                recipeInfos.add(recipeInfo);
                existIds.add(recipeInfo.getId());
            }
        }

        if (leftEnergy > 0) {
            Example example = new Example(RecipeInfo.class);
            Example.Criteria criteria = example.createCriteria();
            if (!existIds.isEmpty()) {
                criteria.andNotIn("id", existIds);
            }

            if (memberInfo.getBloodFat() != null) {
                criteria.andEqualTo("bloodFat", memberInfo.getBloodFat());
            }

            if (memberInfo.getBloodSugar() != null) {
                criteria.andEqualTo("bloodSugar", memberInfo.getBloodSugar());
            }

            if (memberInfo.getBloodPress() != null) {
                criteria.andEqualTo("bloodPress", memberInfo.getBloodPress());
            }

            if (memberInfo.getUricAcid() != null) {
                criteria.andEqualTo("uricAcid", memberInfo.getUricAcid());
            }

            if (memberInfo.getKidney() != null) {
                criteria.andEqualTo("kidney", memberInfo.getKidney());
            }
            criteria.andLike("mealTime", "%|" + mealTime + "|%");
            criteria.andLessThanOrEqualTo("totalEnergies", leftEnergy);
            example.setOrderByClause(" totalEnergies desc");

            List<RecipeInfo> result = recipeInfoService.selectByExample(example);
            if(result != null && !result.isEmpty()) {
                RecipeInfo recipeInfo = result.get(0);
                recipeInfo.setCofInfos(cofInfoService.getCofinfos(recipeInfo));
                recipeInfos.add(recipeInfo);
            }
        }
        return recipeInfos;
    }

    private MemberRecipe addMemberRecipe(long batchId, int mealTime, float totalEnergy,
                                         int memberId, List<RecipeInfo> recipeInfos) {
        Set<Integer> recipeIds = new HashSet<>();
        for (RecipeInfo recipeInfo : recipeInfos) {
            recipeIds.add(recipeInfo.getId());
        }

        String mealName = "";
        if (mealTime == 1) {
            mealName = "早餐";
        } else if (mealTime == 2) {
            mealName = "午餐";
        } else if (mealTime == 3) {
            mealName = "晚餐";
        }

        MemberRecipe memberRecipe = new MemberRecipe();
        memberRecipe.setBatchId(batchId);
        memberRecipe.setMealName(mealName);
        memberRecipe.setMealTime(mealTime);
        memberRecipe.setMemberId(memberId);
        memberRecipe.setTotalEnergy(totalEnergy);
        memberRecipe.setRecipeIds(StringUtils.join(recipeIds, ","));
        memberRecipe.setCreateTime(new Date());
        memberRecipe.setUpdateTime(new Date());

        memberRecipeService.insert(memberRecipe);
        memberRecipe.setRecipeInfos(recipeInfos);
        return memberRecipe;
    }

}

