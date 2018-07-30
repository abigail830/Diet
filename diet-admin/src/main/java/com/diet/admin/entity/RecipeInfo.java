package com.diet.admin.entity;

import com.diet.admin.core.BaseEntity;

import java.util.Date;
import java.util.List;
import javax.persistence.*;

/**
 * @author LiuYu
 */ 
@Entity
@Table(name = "tb_recipe_info")
public class RecipeInfo extends BaseEntity {

	/**
	 * 菜谱ID
	 */ 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	/**
	 * 菜谱名
	 */ 
	@Column(name = "name")
	private String name;

	/**
	 * 工艺
	 */ 
	@Column(name = "technique")
	private Integer technique;

	/**
	 * 口味
	 */ 
	@Column(name = "taste")
	private String taste;

	/**
	 * 准备时间
	 */ 
	@Column(name = "prepTime")
	private String prepTime;

	/**
	 * 烹饪时间
	 */ 
	@Column(name = "cookTime")
	private String cookTime;

	/**
	 * 难度
	 */ 
	@Column(name = "cookDiff")
	private String cookDiff;

	/**
	 * 菜系
	 */ 
	@Column(name = "cookProvs")
	private String cookProvs;

	/**
	 * 食材ID：竖线 | 分隔
	 */ 
	@Column(name = "cofIds")
	private String cofIds;

	/**
	 * 做法
	 */ 
	@Column(name = "workWay")
	private String workWay;

	/**
	 * 主料
	 */ 
	@Column(name = "mainIngr")
	private String mainIngr;

	/**
	 * 辅料
	 */ 
	@Column(name = "assiIngr")
	private String assiIngr;

	/**
	 * 饭点：1、早餐，2、午餐，3、晚餐，多个时竖线 | 分隔
	 */ 
	@Column(name = "mealTime")
	private String mealTime;

	/**
	 * 总能量
	 */ 
	@Column(name = "totalEnergies")
	private Float totalEnergies;

	/**
	 * 总蛋白
	 */ 
	@Column(name = "totalProteins")
	private Float totalProteins;

	/**
	 * 营养大类标签，多个时竖线 | 分隔
	 */ 
	@Column(name = "nutritionTags")
	private String nutritionTags;

	/**
	 * 血脂标签，等级：1、不能吃，2、少吃，3、无影响，4、多吃
	 */ 
	@Column(name = "bloodFat")
	private Integer bloodFat;

	/**
	 * 血糖标签，等级：1、不能吃，2、少吃，3、无影响，4、多吃
	 */ 
	@Column(name = "bloodSugar")
	private Integer bloodSugar;

	/**
	 * 血压标签，等级：1、不能吃，2、少吃，3、无影响，4、多吃
	 */ 
	@Column(name = "bloodPress")
	private Integer bloodPress;

	/**
	 * 尿酸标签，等级：1、不能吃，2、少吃，3、无影响，4、多吃
	 */ 
	@Column(name = "uricAcid")
	private Integer uricAcid;

	/**
	 * 肾脏标签，等级：1、不能吃，2、少吃，3、无影响，4、多吃
	 */ 
	@Column(name = "kidney")
	private Integer kidney;

	/**
	 * 图片路径
	 */ 
	@Column(name = "imgPath")
	private String imgPath;

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
	private List cofInfos;

	public Integer getId(){
		return id;
	}

	public void setId(Integer id){
		this.id=id;
	}

	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name=name;
	}

	public Integer getTechnique(){
		return technique;
	}

	public void setTechnique(Integer technique){
		this.technique=technique;
	}

	public String getTaste(){
		return taste;
	}

	public void setTaste(String taste){
		this.taste=taste;
	}

	public String getPrepTime(){
		return prepTime;
	}

	public void setPrepTime(String prepTime){
		this.prepTime=prepTime;
	}

	public String getCookTime(){
		return cookTime;
	}

	public void setCookTime(String cookTime){
		this.cookTime=cookTime;
	}

	public String getCookDiff(){
		return cookDiff;
	}

	public void setCookDiff(String cookDiff){
		this.cookDiff=cookDiff;
	}

	public String getCookProvs(){
		return cookProvs;
	}

	public void setCookProvs(String cookProvs){
		this.cookProvs=cookProvs;
	}

	public String getCofIds(){
		return cofIds;
	}

	public void setCofIds(String cofIds){
		this.cofIds=cofIds;
	}

	public String getWorkWay(){
		return workWay;
	}

	public void setWorkWay(String workWay){
		this.workWay=workWay;
	}

	public String getMainIngr(){
		return mainIngr;
	}

	public void setMainIngr(String mainIngr){
		this.mainIngr=mainIngr;
	}

	public String getAssiIngr(){
		return assiIngr;
	}

	public void setAssiIngr(String assiIngr){
		this.assiIngr=assiIngr;
	}

	public String getMealTime(){
		return mealTime;
	}

	public void setMealTime(String mealTime){
		this.mealTime=mealTime;
	}

	public Float getTotalEnergies(){
		return totalEnergies;
	}

	public void setTotalEnergies(Float totalEnergies){
		this.totalEnergies=totalEnergies;
	}

	public Float getTotalProteins(){
		return totalProteins;
	}

	public void setTotalProteins(Float totalProteins){
		this.totalProteins=totalProteins;
	}

	public String getNutritionTags(){
		return nutritionTags;
	}

	public void setNutritionTags(String nutritionTags){
		this.nutritionTags=nutritionTags;
	}

	public Integer getBloodFat(){
		return bloodFat;
	}

	public void setBloodFat(Integer bloodFat){
		this.bloodFat=bloodFat;
	}

	public Integer getBloodSugar(){
		return bloodSugar;
	}

	public void setBloodSugar(Integer bloodSugar){
		this.bloodSugar=bloodSugar;
	}

	public Integer getBloodPress(){
		return bloodPress;
	}

	public void setBloodPress(Integer bloodPress){
		this.bloodPress=bloodPress;
	}

	public Integer getUricAcid(){
		return uricAcid;
	}

	public void setUricAcid(Integer uricAcid){
		this.uricAcid=uricAcid;
	}

	public Integer getKidney(){
		return kidney;
	}

	public void setKidney(Integer kidney){
		this.kidney=kidney;
	}

	public String getImgPath(){
		return imgPath;
	}

	public void setImgPath(String imgPath){
		this.imgPath=imgPath;
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

	public List getCofInfos() {
		return cofInfos;
	}

	public void setCofInfos(List cofInfos) {
		this.cofInfos = cofInfos;
	}
}

