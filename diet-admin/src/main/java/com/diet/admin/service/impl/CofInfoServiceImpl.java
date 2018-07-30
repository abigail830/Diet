package com.diet.admin.service.impl;

import com.diet.admin.core.impl.BaseServiceImpl;
import com.diet.admin.entity.CofInfo;
import com.diet.admin.entity.RecipeInfo;
import com.diet.admin.service.ICofInfoService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author LiuYu
 */ 
@Service
public class CofInfoServiceImpl extends BaseServiceImpl<CofInfo> implements ICofInfoService {

    @Override
    public List<CofInfo> getCofinfos(RecipeInfo recipeInfo) {
        String[] cofIds = recipeInfo.getCofIds().split("\\|");
        List ids = new ArrayList(Arrays.asList(cofIds));
        if(ids.size() > 0){
            ids.remove(0);
        }
        Example example = new Example(CofInfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("id", ids);

        return selectByExample(example);
    }
}

