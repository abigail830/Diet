package com.diet.admin.entity;

import com.diet.admin.core.BaseEntity;

import java.util.Date;
import javax.persistence.*;

/**
 * @author LiuYu
 */ 
@Entity
@Table(name = "tb_scaler_standard")
public class ScalerStandard extends BaseEntity {

	/**
	 * ID
	 */ 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	/**
	 * 目标能量
	 */ 
	@Column(name = "targetEnergy")
	private Float targetEnergy;

	/**
	 * 目标蛋白质
	 */ 
	@Column(name = "targetProtein")
	private Float targetProtein;

	/**
	 * 盐
	 */ 
	@Column(name = "salt")
	private Float salt;

	/**
	 * 磷
	 */ 
	@Column(name = "phosphor")
	private Float phosphor;

	/**
	 * 维生素A
	 */ 
	@Column(name = "va")
	private Float va;

	/**
	 * 维生素B
	 */ 
	@Column(name = "vb")
	private Float vb;

	/**
	 * 维生素C
	 */ 
	@Column(name = "vc")
	private Float vc;

	/**
	 * 维生素D
	 */ 
	@Column(name = "vd")
	private Float vd;

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
	 * 牛奶
	 */ 
	@Column(name = "milk")
	private Float milk;

	/**
	 * 鸡蛋
	 */ 
	@Column(name = "egg")
	private Float egg;

	/**
	 * 鸡蛋白
	 */ 
	@Column(name = "eggWhite")
	private Float eggWhite;

	/**
	 * 瘦肉
	 */ 
	@Column(name = "lean")
	private Float lean;

	/**
	 * 豆腐
	 */ 
	@Column(name = "tofu")
	private Float tofu;

	/**
	 * 蔬菜
	 */ 
	@Column(name = "vegetable")
	private Float vegetable;

	/**
	 * 叶类蔬菜
	 */ 
	@Column(name = "leafyVegetable")
	private Float leafyVegetable;

	/**
	 * 瓜茄类蔬菜
	 */ 
	@Column(name = "melon")
	private Float melon;

	/**
	 * 菌藻类（干）
	 */ 
	@Column(name = "homonemeae")
	private Float homonemeae;

	/**
	 * 水果
	 */ 
	@Column(name = "fruit")
	private Float fruit;

	/**
	 * 米／面
	 */ 
	@Column(name = "rice")
	private Float rice;

	/**
	 * 薯类
	 */ 
	@Column(name = "tuber")
	private Float tuber;

	/**
	 * 淀粉类
	 */ 
	@Column(name = "amyloid")
	private Float amyloid;

	/**
	 * 油
	 */ 
	@Column(name = "oil")
	private Float oil;

	/**
	 * 饮用水
	 */ 
	@Column(name = "water")
	private Float water;

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

	public Float getTargetEnergy(){
		return targetEnergy;
	}

	public void setTargetEnergy(Float targetEnergy){
		this.targetEnergy=targetEnergy;
	}

	public Float getTargetProtein(){
		return targetProtein;
	}

	public void setTargetProtein(Float targetProtein){
		this.targetProtein=targetProtein;
	}

	public Float getSalt(){
		return salt;
	}

	public void setSalt(Float salt){
		this.salt=salt;
	}

	public Float getPhosphor(){
		return phosphor;
	}

	public void setPhosphor(Float phosphor){
		this.phosphor=phosphor;
	}

	public Float getVa(){
		return va;
	}

	public void setVa(Float va){
		this.va=va;
	}

	public Float getVb(){
		return vb;
	}

	public void setVb(Float vb){
		this.vb=vb;
	}

	public Float getVc(){
		return vc;
	}

	public void setVc(Float vc){
		this.vc=vc;
	}

	public Float getVd(){
		return vd;
	}

	public void setVd(Float vd){
		this.vd=vd;
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

	public Float getMilk(){
		return milk;
	}

	public void setMilk(Float milk){
		this.milk=milk;
	}

	public Float getEgg(){
		return egg;
	}

	public void setEgg(Float egg){
		this.egg=egg;
	}

	public Float getEggWhite(){
		return eggWhite;
	}

	public void setEggWhite(Float eggWhite){
		this.eggWhite=eggWhite;
	}

	public Float getLean(){
		return lean;
	}

	public void setLean(Float lean){
		this.lean=lean;
	}

	public Float getTofu(){
		return tofu;
	}

	public void setTofu(Float tofu){
		this.tofu=tofu;
	}

	public Float getVegetable(){
		return vegetable;
	}

	public void setVegetable(Float vegetable){
		this.vegetable=vegetable;
	}

	public Float getLeafyVegetable(){
		return leafyVegetable;
	}

	public void setLeafyVegetable(Float leafyVegetable){
		this.leafyVegetable=leafyVegetable;
	}

	public Float getMelon(){
		return melon;
	}

	public void setMelon(Float melon){
		this.melon=melon;
	}

	public Float getHomonemeae(){
		return homonemeae;
	}

	public void setHomonemeae(Float homonemeae){
		this.homonemeae=homonemeae;
	}

	public Float getFruit(){
		return fruit;
	}

	public void setFruit(Float fruit){
		this.fruit=fruit;
	}

	public Float getRice(){
		return rice;
	}

	public void setRice(Float rice){
		this.rice=rice;
	}

	public Float getTuber(){
		return tuber;
	}

	public void setTuber(Float tuber){
		this.tuber=tuber;
	}

	public Float getAmyloid(){
		return amyloid;
	}

	public void setAmyloid(Float amyloid){
		this.amyloid=amyloid;
	}

	public Float getOil(){
		return oil;
	}

	public void setOil(Float oil){
		this.oil=oil;
	}

	public Float getWater(){
		return water;
	}

	public void setWater(Float water){
		this.water=water;
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

