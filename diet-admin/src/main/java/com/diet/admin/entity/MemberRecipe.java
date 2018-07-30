package com.diet.admin.entity;

import com.diet.admin.core.BaseEntity;

import java.util.Date;
import java.util.List;
import javax.persistence.*;

/**
 * @author LiuYu
 */ 
@Entity
@Table(name = "tb_member_recipe")
public class MemberRecipe extends BaseEntity {

	/**
	 * 
	 */ 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	/**
	 * 批次ID
	 */ 
	@Column(name = "batchId")
	private Long batchId;

	/**
	 * 
	 */ 
	@Column(name = "mealName")
	private String mealName;

	/**
	 * 
	 */ 
	@Column(name = "mealTime")
	private Integer mealTime;

	/**
	 * 
	 */ 
	@Column(name = "totalEnergy")
	private Float totalEnergy;

	/**
	 * 成员ID
	 */ 
	@Column(name = "memberId")
	private Integer memberId;

	/**
	 * 菜谱ID
	 */ 
	@Column(name = "recipeIds")
	private String recipeIds;

	/**
	 * 
	 */ 
	@Column(name = "createTime")
	private Date createTime;

	/**
	 * 
	 */ 
	@Column(name = "updateTime")
	private Date updateTime;

	@Transient
	private List recipeInfos;

	public Integer getId(){
		return id;
	}

	public void setId(Integer id){
		this.id=id;
	}

	public Long getBatchId(){
		return batchId;
	}

	public void setBatchId(Long batchId){
		this.batchId=batchId;
	}

	public String getMealName(){
		return mealName;
	}

	public void setMealName(String mealName){
		this.mealName=mealName;
	}

	public Integer getMealTime(){
		return mealTime;
	}

	public void setMealTime(Integer mealTime){
		this.mealTime=mealTime;
	}

	public Float getTotalEnergy(){
		return totalEnergy;
	}

	public void setTotalEnergy(Float totalEnergy){
		this.totalEnergy=totalEnergy;
	}

	public Integer getMemberId(){
		return memberId;
	}

	public void setMemberId(Integer memberId){
		this.memberId=memberId;
	}

	public String getRecipeIds(){
		return recipeIds;
	}

	public void setRecipeIds(String recipeIds){
		this.recipeIds=recipeIds;
	}

	public Date getCreateTime(){
		return createTime;
	}

	public void setCreateTime(Date createTime){
		this.createTime=createTime;
	}

	public Date getUpdateTime(){
		return updateTime;
	}

	public void setUpdateTime(Date updateTime){
		this.updateTime=updateTime;
	}

	public List getRecipeInfos() {
		return recipeInfos;
	}

	public void setRecipeInfos(List recipeInfos) {
		this.recipeInfos = recipeInfos;
	}
}

