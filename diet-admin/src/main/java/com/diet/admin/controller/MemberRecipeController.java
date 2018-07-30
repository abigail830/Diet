package com.diet.admin.controller;

import com.alibaba.fastjson.JSONObject;
import com.diet.admin.core.BaseController;
import com.diet.admin.entity.MemberInfo;
import com.diet.admin.entity.MemberRecipe;
import com.diet.admin.entity.RecipeInfo;
import com.diet.admin.message.MsgCode;
import com.diet.admin.message.ResponseMsg;
import com.diet.admin.service.ICofInfoService;
import com.diet.admin.service.IMemberInfoService;
import com.diet.admin.service.IMemberRecipeService;
import com.diet.admin.service.IRecipeInfoService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping(BaseController.API + "/memberRecipe")
public class MemberRecipeController extends BaseController {

    @Autowired
    private IMemberRecipeService memberRecipeService;

    @Autowired
    private IRecipeInfoService recipeInfoService;

    @Autowired
    private ICofInfoService cofInfoService;

    @Autowired
    private IMemberInfoService memberInfoService;

    /**
     * 刷新单个菜谱
     *
     * @param request
     * @return
     */
    @PostMapping("/refreshRecipe")
    public ResponseMsg refreshRecipe(@RequestBody JSONObject request) {
        ResponseMsg responseMsg = new ResponseMsg();

        Integer recipeId = request.getInteger("recipeId");
        Integer memberRecipeId = request.getInteger("memberRecipeId");
        if (recipeId == null || memberRecipeId == null) {
            return new ResponseMsg(MsgCode.Param_Error);
        }

        MemberRecipe memberRecipe = memberRecipeService.selectByPrimaryKey(memberRecipeId);
        if (memberRecipe == null) {
            return new ResponseMsg(MsgCode.Param_Error);
        }

        List<RecipeInfo> recipeInfos = getRecipeInfosByIds(memberRecipe.getRecipeIds());
        List<RecipeInfo> newRecipeInfos = new ArrayList<>();
        Set<Integer> existIds = new HashSet<>();
        Set<Integer> recipeIds = new HashSet<>();
        float leftEnergy = memberRecipe.getTotalEnergy();
        for (RecipeInfo recipeInfo : recipeInfos) {
            if (recipeInfo.getId().intValue() != recipeId.intValue()) {
                newRecipeInfos.add(recipeInfo);
                recipeIds.add(recipeInfo.getId());
            }
            leftEnergy -= recipeInfo.getTotalEnergies();
            existIds.add(recipeInfo.getId());
        }

        MemberInfo memberInfo = memberInfoService.selectByPrimaryKey(memberRecipe.getMemberId());
        Example example = new Example(RecipeInfo.class);
        Example.Criteria criteria = example.createCriteria();
        if (!existIds.isEmpty()) {
            criteria.andNotIn("id", existIds);
        }
        if (memberInfo != null && memberInfo.getBloodFat() != null) {
            criteria.andEqualTo("bloodFat", memberInfo.getBloodFat());
        }

        if (memberInfo != null && memberInfo.getBloodSugar() != null) {
            criteria.andEqualTo("bloodSugar", memberInfo.getBloodSugar());
        }

        if (memberInfo != null && memberInfo.getBloodPress() != null) {
            criteria.andEqualTo("bloodPress", memberInfo.getBloodPress());
        }

        if (memberInfo != null && memberInfo.getUricAcid() != null) {
            criteria.andEqualTo("uricAcid", memberInfo.getUricAcid());
        }

        if (memberInfo != null && memberInfo.getKidney() != null) {
            criteria.andEqualTo("kidney", memberInfo.getKidney());
        }
        criteria.andLike("mealTime", "%|" + memberRecipe.getMealTime() + "|%");
        criteria.andLessThanOrEqualTo("totalEnergies", leftEnergy);
        example.setOrderByClause(" totalEnergies desc");

        List<RecipeInfo> result = recipeInfoService.selectByExample(example);
        if (result != null && !result.isEmpty()) {
            int random = new Random().nextInt(result.size());
            RecipeInfo recipeInfo = result.get(random);
            recipeInfo.setCofInfos(cofInfoService.getCofinfos(recipeInfo));
            newRecipeInfos.add(recipeInfo);
            recipeIds.add(recipeInfo.getId());
        }

        memberRecipe.setRecipeIds(StringUtils.join(recipeIds, ","));
        memberRecipe.setUpdateTime(new Date());
        memberRecipeService.updateByPrimaryKeySelective(memberRecipe);

        JSONObject jsonObject = new JSONObject();
        memberRecipe.setRecipeInfos(newRecipeInfos);
        jsonObject.put("memberRecipe", memberRecipe);
        jsonObject.put("recipeInfoList", recipeInfos(memberRecipe));
        responseMsg.setData(jsonObject);

        return responseMsg;
    }

    private List<RecipeInfo> getRecipeInfosByIds(String idsStr) {
        if (StringUtils.isEmpty(idsStr)) {
            return new ArrayList<>();
        }
        List ids = Arrays.asList(idsStr.split(","));
        Example example = new Example(RecipeInfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("id", ids);
        return recipeInfoService.selectByExample(example);
    }

    /**
     * 删除菜谱
     *
     * @param request
     * @return
     */
    @PostMapping("/removeRecipe")
    public ResponseMsg removeRecipe(@RequestBody JSONObject request) {
        ResponseMsg responseMsg = new ResponseMsg();

        Integer recipeId = request.getInteger("recipeId");
        Integer memberRecipeId = request.getInteger("memberRecipeId");
        if (recipeId == null || memberRecipeId == null) {
            return new ResponseMsg(MsgCode.Param_Error);
        }

        MemberRecipe memberRecipe = memberRecipeService.selectByPrimaryKey(memberRecipeId);
        if (memberRecipe == null) {
            return new ResponseMsg(MsgCode.Param_Error);
        }

        List<RecipeInfo> recipeInfos = getRecipeInfosByIds(memberRecipe.getRecipeIds());
        List<RecipeInfo> newRecipeInfos = new ArrayList<>();
        Set<Integer> recipeIds = new HashSet<>();
        for (RecipeInfo recipeInfo : recipeInfos) {
            if (recipeInfo.getId().intValue() != recipeId.intValue()) {
                newRecipeInfos.add(recipeInfo);
                recipeIds.add(recipeInfo.getId());
            }
        }

        memberRecipe.setRecipeIds(StringUtils.join(recipeIds, ","));
        memberRecipe.setUpdateTime(new Date());
        memberRecipeService.updateByPrimaryKeySelective(memberRecipe);

        JSONObject jsonObject = new JSONObject();
        memberRecipe.setRecipeInfos(newRecipeInfos);
        jsonObject.put("memberRecipe", memberRecipe);
        jsonObject.put("recipeInfoList", recipeInfos(memberRecipe));
        responseMsg.setData(jsonObject);

        return responseMsg;
    }

    /**
     * 可选菜谱
     *
     * @param request
     * @return
     */
    @PostMapping("/listRecipe")
    public ResponseMsg listRecipe(@RequestBody JSONObject request) {
        ResponseMsg responseMsg = new ResponseMsg();

        Integer memberRecipeId = request.getInteger("memberRecipeId");
        if (memberRecipeId == null) {
            return new ResponseMsg(MsgCode.Param_Error);
        }

        MemberRecipe memberRecipe = memberRecipeService.selectByPrimaryKey(memberRecipeId);

        responseMsg.setData(recipeInfos(memberRecipe));
        return responseMsg;
    }

    /**
     * 菜谱查询
     *
     * @param memberRecipe
     * @return
     */
    private List<RecipeInfo> recipeInfos(MemberRecipe memberRecipe) {
        if (memberRecipe == null) {
            return null;
        }

        List<RecipeInfo> recipeInfos = getRecipeInfosByIds(memberRecipe.getRecipeIds());
        Set<Integer> existIds = new HashSet<>();
        float leftEnergy = memberRecipe.getTotalEnergy();
        for (RecipeInfo recipeInfo : recipeInfos) {
            leftEnergy -= recipeInfo.getTotalEnergies();
            existIds.add(recipeInfo.getId());
        }

        MemberInfo memberInfo = memberInfoService.selectByPrimaryKey(memberRecipe.getMemberId());
        Example example = new Example(RecipeInfo.class);
        Example.Criteria criteria = example.createCriteria();
        if (!existIds.isEmpty()) {
            criteria.andNotIn("id", existIds);
        }
        if (memberInfo != null && memberInfo.getBloodFat() != null) {
            criteria.andEqualTo("bloodFat", memberInfo.getBloodFat());
        }

        if (memberInfo != null && memberInfo.getBloodSugar() != null) {
            criteria.andEqualTo("bloodSugar", memberInfo.getBloodSugar());
        }

        if (memberInfo != null && memberInfo.getBloodPress() != null) {
            criteria.andEqualTo("bloodPress", memberInfo.getBloodPress());
        }

        if (memberInfo != null && memberInfo.getUricAcid() != null) {
            criteria.andEqualTo("uricAcid", memberInfo.getUricAcid());
        }

        if (memberInfo != null && memberInfo.getKidney() != null) {
            criteria.andEqualTo("kidney", memberInfo.getKidney());
        }
        criteria.andLike("mealTime", "%|" + memberRecipe.getMealTime() + "|%");
        criteria.andLessThanOrEqualTo("totalEnergies", leftEnergy);
        example.setOrderByClause(" totalEnergies desc");

        return recipeInfoService.selectByExample(example);
    }

    /**
     * 添加单个菜谱
     *
     * @param request
     * @return
     */
    @PostMapping("/addRecipe")
    public ResponseMsg addRecipe(@RequestBody JSONObject request) {
        ResponseMsg responseMsg = new ResponseMsg();

        Integer recipeId = request.getInteger("recipeId");
        Integer memberRecipeId = request.getInteger("memberRecipeId");
        if (recipeId == null || memberRecipeId == null) {
            return new ResponseMsg(MsgCode.Param_Error);
        }

        MemberRecipe memberRecipe = memberRecipeService.selectByPrimaryKey(memberRecipeId);
        if (memberRecipe == null) {
            return new ResponseMsg(MsgCode.Param_Error);
        }

        List<RecipeInfo> recipeInfos = getRecipeInfosByIds(memberRecipe.getRecipeIds());
        Set<Integer> recipeIds = new HashSet<>();
        for (RecipeInfo recipeInfo : recipeInfos) {
            recipeIds.add(recipeInfo.getId());
        }

        RecipeInfo recipeInfo = recipeInfoService.selectByPrimaryKey(recipeId);
        if (recipeInfo != null) {
            recipeInfos.add(recipeInfo);
            recipeIds.add(recipeInfo.getId());
        }

        memberRecipe.setRecipeIds(StringUtils.join(recipeIds, ","));
        memberRecipe.setUpdateTime(new Date());
        memberRecipeService.updateByPrimaryKeySelective(memberRecipe);

        JSONObject jsonObject = new JSONObject();
        memberRecipe.setRecipeInfos(recipeInfos);
        jsonObject.put("memberRecipe", memberRecipe);
        jsonObject.put("recipeInfoList", recipeInfos(memberRecipe));
        responseMsg.setData(jsonObject);

        return responseMsg;
    }
}

