package com.diet.admin.entity;

import com.diet.admin.core.BaseEntity;

import javax.persistence.*;

/**
 * @author LiuYu
 */ 
@Entity
@Table(name = "tb_cookbook")
public class Cookbook extends BaseEntity {

	/**
	 * 
	 */ 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	/**
	 * 名字
	 */ 
	@Column(name = "name")
	private String name;

	/**
	 * 工艺
	 */ 
	@Column(name = "technique")
	private String technique;

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
	 * 菜系
	 */ 
	@Column(name = "cookProvs")
	private String cookProvs;

	/**
	 * 标签
	 */ 
	@Column(name = "tag")
	private String tag;

	/**
	 * 人数
	 */ 
	@Column(name = "numPeop")
	private String numPeop;

	/**
	 * 难度
	 */ 
	@Column(name = "cookDiff")
	private String cookDiff;

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
	 * 图片
	 */ 
	@Column(name = "imgUrl")
	private String imgUrl;

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

	public String getTechnique(){
		return technique;
	}

	public void setTechnique(String technique){
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

	public String getCookProvs(){
		return cookProvs;
	}

	public void setCookProvs(String cookProvs){
		this.cookProvs=cookProvs;
	}

	public String getTag(){
		return tag;
	}

	public void setTag(String tag){
		this.tag=tag;
	}

	public String getNumPeop(){
		return numPeop;
	}

	public void setNumPeop(String numPeop){
		this.numPeop=numPeop;
	}

	public String getCookDiff(){
		return cookDiff;
	}

	public void setCookDiff(String cookDiff){
		this.cookDiff=cookDiff;
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

	public String getImgUrl(){
		return imgUrl;
	}

	public void setImgUrl(String imgUrl){
		this.imgUrl=imgUrl;
	}

}

