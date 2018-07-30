package com.diet.admin.service.impl;

import com.diet.admin.core.impl.BaseServiceImpl;
import com.diet.admin.dao.RecipeInfoMapper;
import com.diet.admin.entity.RecipeInfo;
import com.diet.admin.service.IRecipeInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * @author LiuYu
 */
@Service
public class RecipeInfoServiceImpl extends BaseServiceImpl<RecipeInfo> implements IRecipeInfoService {

    @Autowired
    private RecipeInfoMapper recipeInfoMapper;

    @Override
    public RecipeInfo getRecomdRecipe(Integer cofId, Integer cofInfoType, Integer bloodFat, Integer bloodSugar,
                                      Integer bloodPress, Integer uricAcid, Integer kidney, float maxEng,
                                      int mealTime, String nutritionTags, Iterable<Integer> ids) {
        if (cofInfoType != null) {
            return getRecipeByCofType(cofInfoType, bloodFat, bloodSugar, bloodPress, uricAcid, kidney,
                    maxEng, mealTime, nutritionTags, ids);
        } else {
            return getRecipeByCofId(cofId, bloodFat, bloodSugar, bloodPress, uricAcid, kidney,
                    maxEng, mealTime, nutritionTags, ids);
        }
    }

    private RecipeInfo getRecipeByCofId(Integer cofId, Integer bloodFat, Integer bloodSugar,
                                        Integer bloodPress, Integer uricAcid, Integer kidney,
                                        float maxEng, int mealTime, String nutritionTags, Iterable<Integer> ids) {
        Example example = new Example(RecipeInfo.class);
        Example.Criteria criteria = example.createCriteria();
        if (ids != null && ids.iterator().hasNext()) {
            criteria.andNotIn("id", ids);
        }

        if (cofId != null) {
            criteria.andLike("cofIds", "%|" + cofId + "|%");
        }

        if (bloodFat != null) {
            criteria.andEqualTo("bloodFat", bloodFat);
        }

        if (bloodSugar != null) {
            criteria.andEqualTo("bloodSugar", bloodSugar);
        }

        if (bloodPress != null) {
            criteria.andEqualTo("bloodPress", bloodPress);
        }

        if (uricAcid != null) {
            criteria.andEqualTo("uricAcid", uricAcid);
        }

        if (kidney != null) {
            criteria.andEqualTo("kidney", kidney);
        }

        if (mealTime > 0) {
            criteria.andLike("mealTime", "%|" + mealTime + "|%");
        }

        if (StringUtils.isBlank(nutritionTags)) {
            criteria.andLike("nutritionTags", "%|" + nutritionTags + "|%");
        }
        criteria.andLessThanOrEqualTo("totalEnergies", maxEng);

        List<RecipeInfo> recipeInfos = selectByExample(example);
        if(recipeInfos != null && !recipeInfos.isEmpty()) {
            int random = new Random().nextInt(recipeInfos.size());
            return recipeInfos.get(random);
        }
        return null;
    }

    private RecipeInfo getRecipeByCofType(Integer cofInfoType, Integer bloodFat, Integer bloodSugar,
                                          Integer bloodPress, Integer uricAcid, Integer kidney,
                                          float maxEng, int mealTime, String nutritionTags, Iterable<Integer> ids) {
        List<RecipeInfo> recipeInfos = recipeInfoMapper.getRecipeByCofType(cofInfoType, bloodFat, bloodSugar,
                bloodPress, uricAcid, kidney, maxEng, "%|" + mealTime + "|%", "%|" + nutritionTags + "|%", ids);
        int random = new Random().nextInt(recipeInfos.size());
        return recipeInfos.get(random);
    }
}

