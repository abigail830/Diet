package com.diet.admin.service;

import com.diet.admin.core.BaseService;
import com.diet.admin.entity.CofInfo;
import com.diet.admin.entity.RecipeInfo;

import java.util.List;

/**
 * @author LiuYu
 */ 
public interface ICofInfoService extends BaseService<CofInfo> {

    /**
     * 根据菜谱获取食材
     *
     * @param recipeInfo
     * @return
     */
    List<CofInfo> getCofinfos(RecipeInfo recipeInfo);
}

