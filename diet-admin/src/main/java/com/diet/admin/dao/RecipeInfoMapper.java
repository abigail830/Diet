package com.diet.admin.dao;

import com.diet.admin.core.BaseMapper;
import com.diet.admin.entity.RecipeInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author LiuYu
 */
public interface RecipeInfoMapper extends BaseMapper<RecipeInfo> {
    List<RecipeInfo> getRecipeByCofType(@Param("cofInfoType") Integer cofInfoType, @Param("bloodFat") Integer bloodFat,
                                        @Param("bloodSugar") Integer bloodSugar, @Param("bloodPress") Integer bloodPress,
                                        @Param("uricAcid") Integer uricAcid, @Param("kidney") Integer kidney, @Param("maxEng") float maxEng,
                                        @Param("mealTime") String mealTime, @Param("nutritionTags") String nutritionTags,
                                        @Param("ids") Iterable<Integer> ids);
}

