package com.diet.admin.entity;

import com.diet.admin.core.BaseEntity;

import java.util.Date;
import javax.persistence.*;

/**
 * @author LiuYu
 */ 
@Entity
@Table(name = "tb_wx_user_info_ext")
public class WxUserInfoExt extends BaseEntity {

	/**
	 * 主键
	 */ 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	/**
	 * 微信主表ID
	 */ 
	@Column(name = "wxUserId")
	private Integer wxUserId;

	/**
	 * openId
	 */ 
	@Column(name = "openId")
	private String openId;

	/**
	 * 
	 */ 
	@Column(name = "createTime")
	private Date createTime;

	public Integer getId(){
		return id;
	}

	public void setId(Integer id){
		this.id=id;
	}

	public Integer getWxUserId(){
		return wxUserId;
	}

	public void setWxUserId(Integer wxUserId){
		this.wxUserId=wxUserId;
	}

	public String getOpenId(){
		return openId;
	}

	public void setOpenId(String openId){
		this.openId=openId;
	}

	public Date getCreateTime(){
		return createTime;
	}

	public void setCreateTime(Date createTime){
		this.createTime=createTime;
	}

}

