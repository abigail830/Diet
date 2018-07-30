package com.diet.admin.entity;

import com.diet.admin.core.BaseEntity;
import com.diet.admin.model.FeedBackDetailModel;

import java.util.Date;
import java.util.List;
import javax.persistence.*;

/**
 * @author LiuYu
 */
@Entity
@Table(name = "tb_feedback")
public class Feedback extends BaseEntity {

	/**
	 *
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	/**
	 * 用户ID
	 */
	@Column(name = "memberId")
	private Integer memberId;

	/**
	 * 膳食描述
	 */
	@Column(name = "mealDesc")
	private String mealDesc;

	/**
	 * 膳食日期
	 */
	@Column(name = "mealDate")
	private String mealDate;

	/**
	 * 膳食明细
	 */
	@Column(name = "mealDetails")
	private String mealDetails;

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
	private List<FeedBackDetailModel> mealList;

	public Integer getId(){
		return id;
	}

	public void setId(Integer id){
		this.id=id;
	}

	public Integer getMemberId(){
		return memberId;
	}

	public void setMemberId(Integer memberId){
		this.memberId=memberId;
	}

	public String getMealDesc(){
		return mealDesc;
	}

	public void setMealDesc(String mealDesc){
		this.mealDesc=mealDesc;
	}

	public String getMealDate(){
		return mealDate;
	}

	public void setMealDate(String mealDate){
		this.mealDate=mealDate;
	}

	public String getMealDetails() {
		return mealDetails;
	}

	public void setMealDetails(String mealDetails) {
		this.mealDetails = mealDetails;
	}

	public List<FeedBackDetailModel> getMealList() {
		return mealList;
	}

	public void setMealList(List<FeedBackDetailModel> mealList) {
		this.mealList = mealList;
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

