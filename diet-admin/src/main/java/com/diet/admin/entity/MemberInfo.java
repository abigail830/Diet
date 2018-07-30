package com.diet.admin.entity;

import com.diet.admin.core.BaseEntity;

import java.util.Date;
import javax.persistence.*;

/**
 * @author LiuYu
 */ 
@Entity
@Table(name = "tb_member_info")
public class MemberInfo extends BaseEntity {

	/**
	 * 
	 */ 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	/**
	 * 推荐人ID
	 */ 
	@Column(name = "wxUserExtId")
	private Integer wxUserExtId;

	/**
	 * 微信信息ID
	 */ 
	@Column(name = "wxUserId")
	private Integer wxUserId;

	/**
	 * 姓名
	 */ 
	@Column(name = "name")
	private String name;

	/**
	 * 性别：1、男性，2、女性
	 */ 
	@Column(name = "sex")
	private Integer sex;

	/**
	 * 出生日期：yyyy-MM-dd
	 */ 
	@Column(name = "birthDate")
	private String birthDate;

	/**
	 * 身高，单位cm
	 */ 
	@Column(name = "height")
	private Float height;

	/**
	 * 体重，单位kg
	 */ 
	@Column(name = "weight")
	private Float weight;

	/**
	 * 职业
	 */ 
	@Column(name = "profession")
	private Integer profession;

	/**
	 * 地区
	 */ 
	@Column(name = "region")
	private String region;

	/**
	 * 省份
	 */ 
	@Column(name = "province")
	private String province;

	/**
	 * 城市
	 */ 
	@Column(name = "city")
	private String city;

	/**
	 * 区
	 */ 
	@Column(name = "area")
	private String area;

	/**
	 * 体力活动
	 */ 
	@Column(name = "workType")
	private Integer workType;

	/**
	 * 血脂：1、正常，2、高血脂，3、不详
	 */ 
	@Column(name = "bloodFat")
	private Integer bloodFat;

	/**
	 * 血糖：1、正常，2、高血糖，3、不详
	 */ 
	@Column(name = "bloodSugar")
	private Integer bloodSugar;

	/**
	 * 血压：1、正常，2、高血压，3、不详
	 */ 
	@Column(name = "bloodPress")
	private Integer bloodPress;

	/**
	 * 尿酸：1、正常，2、高尿酸，3、不详
	 */ 
	@Column(name = "uricAcid")
	private Integer uricAcid;

	/**
	 * 肾脏：1、正常，2、尿毒症，3、不详
	 */ 
	@Column(name = "kidney")
	private Integer kidney;

	/**
	 * 
	 */ 
	@Column(name = "headImg")
	private String headImg;

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
	private boolean checked = false;

	public Integer getId(){
		return id;
	}

	public void setId(Integer id){
		this.id=id;
	}

	public Integer getWxUserExtId() {
		return wxUserExtId;
	}

	public void setWxUserExtId(Integer wxUserExtId) {
		this.wxUserExtId = wxUserExtId;
	}

	public Integer getWxUserId(){
		return wxUserId;
	}

	public void setWxUserId(Integer wxUserId){
		this.wxUserId=wxUserId;
	}

	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name=name;
	}

	public Integer getSex(){
		return sex;
	}

	public void setSex(Integer sex){
		this.sex=sex;
	}

	public String getBirthDate(){
		return birthDate;
	}

	public void setBirthDate(String birthDate){
		this.birthDate=birthDate;
	}

	public Float getHeight(){
		return height;
	}

	public void setHeight(Float height){
		this.height=height;
	}

	public Float getWeight(){
		return weight;
	}

	public void setWeight(Float weight){
		this.weight=weight;
	}

	public Integer getProfession(){
		return profession;
	}

	public void setProfession(Integer profession){
		this.profession=profession;
	}

	public String getRegion(){
		return region;
	}

	public void setRegion(String region){
		this.region=region;
	}

	public String getProvince(){
		return province;
	}

	public void setProvince(String province){
		this.province=province;
	}

	public String getCity(){
		return city;
	}

	public void setCity(String city){
		this.city=city;
	}

	public String getArea(){
		return area;
	}

	public void setArea(String area){
		this.area=area;
	}

	public Integer getWorkType(){
		return workType;
	}

	public void setWorkType(Integer workType){
		this.workType=workType;
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

	public String getHeadImg(){
		return headImg;
	}

	public void setHeadImg(String headImg){
		this.headImg=headImg;
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

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}
}

