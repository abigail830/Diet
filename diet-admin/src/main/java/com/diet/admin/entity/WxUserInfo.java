package com.diet.admin.entity;

import com.diet.admin.core.BaseEntity;

import java.util.Date;
import javax.persistence.*;

/**
 * @author LiuYu
 */ 
@Entity
@Table(name = "tb_wx_user_info")
public class WxUserInfo extends BaseEntity {

	/**
	 * 
	 */ 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	/**
	 * 手机号码
	 */ 
	@Column(name = "phone")
	private String phone;

	/**
	 * 用户昵称
	 */ 
	@Column(name = "nickName")
	private String nickName;

	/**
	 * 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
	 */ 
	@Column(name = "gender")
	private String gender;

	/**
	 * 
	 */ 
	@Column(name = "unionId")
	private String unionId;

	/**
	 * 用户所在城市
	 */ 
	@Column(name = "city")
	private String city;

	/**
	 * 用户所在省份
	 */ 
	@Column(name = "province")
	private String province;

	/**
	 * 用户所在国家
	 */ 
	@Column(name = "country")
	private String country;

	/**
	 * 用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表132*132正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。
	 */ 
	@Column(name = "avatarUrl")
	private String avatarUrl;

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

	public String getPhone(){
		return phone;
	}

	public void setPhone(String phone){
		this.phone=phone;
	}

	public String getNickName(){
		return nickName;
	}

	public void setNickName(String nickName){
		this.nickName=nickName;
	}

	public String getGender(){
		return gender;
	}

	public void setGender(String gender){
		this.gender=gender;
	}

	public String getUnionId(){
		return unionId;
	}

	public void setUnionId(String unionId){
		this.unionId=unionId;
	}

	public String getCity(){
		return city;
	}

	public void setCity(String city){
		this.city=city;
	}

	public String getProvince(){
		return province;
	}

	public void setProvince(String province){
		this.province=province;
	}

	public String getCountry(){
		return country;
	}

	public void setCountry(String country){
		this.country=country;
	}

	public String getAvatarUrl(){
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl){
		this.avatarUrl=avatarUrl;
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

