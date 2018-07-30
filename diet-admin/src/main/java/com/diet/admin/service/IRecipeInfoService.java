package com.diet.admin.service;

import com.diet.admin.core.BaseService;
import com.diet.admin.entity.RecipeInfo;

import java.util.Iterator;
import java.util.List;

/**
 * @author LiuYu
 */ 
public interface IRecipeInfoService extends BaseService<RecipeInfo> {
    RecipeInfo getRecomdRecipe(Integer cofId, Integer cofInfoType, Integer bloodFat, Integer bloodSugar,
                               Integer bloodPress, Integer uricAcid, Integer kidney, float maxEng,
                               int mealTime, String nutritionTags, Iterable<Integer> ids);
}

