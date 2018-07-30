package com.diet.admin.model;

import com.diet.admin.core.BaseEntity;
import com.diet.admin.entity.CofInfo;

import java.util.List;

/**
 * @author LiuYu
 * @date 2018/7/16
 */
public class FeedBackDetailModel extends BaseEntity {
    private String mealTime;
    private List<Integer> cofIds;
    private List<String> cofVals;
    private List<CofInfo> cofInfos;

    public String getMealTime() {
        return mealTime;
    }

    public void setMealTime(String mealTime) {
        this.mealTime = mealTime;
    }

    public List<Integer> getCofIds() {
        return cofIds;
    }

    public void setCofIds(List<Integer> cofIds) {
        this.cofIds = cofIds;
    }

    public List<String> getCofVals() {
        return cofVals;
    }

    public void setCofVals(List<String> cofVals) {
        this.cofVals = cofVals;
    }

    public List<CofInfo> getCofInfos() {
        return cofInfos;
    }

    public void setCofInfos(List<CofInfo> cofInfos) {
        this.cofInfos = cofInfos;
    }
}
