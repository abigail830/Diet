package com.diet.admin.entity;

import com.diet.admin.core.BaseEntity;

import java.util.Date;
import javax.persistence.*;

/**
 * @author LiuYu
 */ 
@Entity
@Table(name = "tb_cof_info")
public class CofInfo extends BaseEntity {

	/**
	 * 食材ID
	 */ 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	/**
	 * 食物名
	 */ 
	@Column(name = "name")
	private String name;

	/**
	 * 大类
	 */ 
	@Column(name = "type")
	private Integer type;

	/**
	 * 地区
	 */ 
	@Column(name = "region")
	private String region;

	/**
	 * 可食部分
	 */ 
	@Column(name = "canEatePart")
	private Float canEatePart;

	/**
	 * 能量
	 */ 
	@Column(name = "energy")
	private Float energy;

	/**
	 * 水分
	 */ 
	@Column(name = "water")
	private Float water;

	/**
	 * 蛋白质
	 */ 
	@Column(name = "protein")
	private Float protein;

	/**
	 * 脂肪
	 */ 
	@Column(name = "fat")
	private Float fat;

	/**
	 * 膳食纤维
	 */ 
	@Column(name = "fibre")
	private Float fibre;

	/**
	 * 碳水化物
	 */ 
	@Column(name = "carbohydrate")
	private Float carbohydrate;

	/**
	 * 视黄醇当量
	 */ 
	@Column(name = "retinolEq")
	private Float retinolEq;

	/**
	 * 硫胺素
	 */ 
	@Column(name = "vb1")
	private Float vb1;

	/**
	 * 核黄素
	 */ 
	@Column(name = "vb2")
	private Float vb2;

	/**
	 * 尼克酸、烟酸
	 */ 
	@Column(name = "vpp")
	private Float vpp;

	/**
	 * 维生素E
	 */ 
	@Column(name = "ve")
	private Float ve;

	/**
	 * 钠
	 */ 
	@Column(name = "na")
	private Float na;

	/**
	 * 钙
	 */ 
	@Column(name = "ca")
	private Float ca;

	/**
	 * 铁
	 */ 
	@Column(name = "fe")
	private Float fe;

	/**
	 * 抗坏血酸(VC)
	 */ 
	@Column(name = "vc")
	private Float vc;

	/**
	 * 胆固醇
	 */ 
	@Column(name = "cholesterol")
	private Float cholesterol;

	/**
	 * 量 (克/毫升)
	 */ 
	@Column(name = "quantity")
	private Float quantity;

	/**
	 * 是否早餐：1、是，2、否
	 */ 
	@Column(name = "isBreakFast")
	private Integer isBreakFast;

	/**
	 * 是否午餐：1、是，2、否
	 */ 
	@Column(name = "isLunch")
	private Integer isLunch;

	/**
	 * 是否晚餐：1、是，2、否
	 */ 
	@Column(name = "isDinner")
	private Integer isDinner;

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
	 * 
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

	public Integer getType(){
		return type;
	}

	public void setType(Integer type){
		this.type=type;
	}

	public String getRegion(){
		return region;
	}

	public void setRegion(String region){
		this.region=region;
	}

	public Float getCanEatePart(){
		return canEatePart;
	}

	public void setCanEatePart(Float canEatePart){
		this.canEatePart=canEatePart;
	}

	public Float getEnergy(){
		return energy;
	}

	public void setEnergy(Float energy){
		this.energy=energy;
	}

	public Float getWater(){
		return water;
	}

	public void setWater(Float water){
		this.water=water;
	}

	public Float getProtein(){
		return protein;
	}

	public void setProtein(Float protein){
		this.protein=protein;
	}

	public Float getFat(){
		return fat;
	}

	public void setFat(Float fat){
		this.fat=fat;
	}

	public Float getFibre(){
		return fibre;
	}

	public void setFibre(Float fibre){
		this.fibre=fibre;
	}

	public Float getCarbohydrate(){
		return carbohydrate;
	}

	public void setCarbohydrate(Float carbohydrate){
		this.carbohydrate=carbohydrate;
	}

	public Float getRetinolEq(){
		return retinolEq;
	}

	public void setRetinolEq(Float retinolEq){
		this.retinolEq=retinolEq;
	}

	public Float getVb1(){
		return vb1;
	}

	public void setVb1(Float vb1){
		this.vb1=vb1;
	}

	public Float getVb2(){
		return vb2;
	}

	public void setVb2(Float vb2){
		this.vb2=vb2;
	}

	public Float getVpp(){
		return vpp;
	}

	public void setVpp(Float vpp){
		this.vpp=vpp;
	}

	public Float getVe(){
		return ve;
	}

	public void setVe(Float ve){
		this.ve=ve;
	}

	public Float getNa(){
		return na;
	}

	public void setNa(Float na){
		this.na=na;
	}

	public Float getCa(){
		return ca;
	}

	public void setCa(Float ca){
		this.ca=ca;
	}

	public Float getFe(){
		return fe;
	}

	public void setFe(Float fe){
		this.fe=fe;
	}

	public Float getVc(){
		return vc;
	}

	public void setVc(Float vc){
		this.vc=vc;
	}

	public Float getCholesterol(){
		return cholesterol;
	}

	public void setCholesterol(Float cholesterol){
		this.cholesterol=cholesterol;
	}

	public Float getQuantity(){
		return quantity;
	}

	public void setQuantity(Float quantity){
		this.quantity=quantity;
	}

	public Integer getIsBreakFast(){
		return isBreakFast;
	}

	public void setIsBreakFast(Integer isBreakFast){
		this.isBreakFast=isBreakFast;
	}

	public Integer getIsLunch(){
		return isLunch;
	}

	public void setIsLunch(Integer isLunch){
		this.isLunch=isLunch;
	}

	public Integer getIsDinner(){
		return isDinner;
	}

	public void setIsDinner(Integer isDinner){
		this.isDinner=isDinner;
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

}

