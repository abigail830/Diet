package com.diet.admin.entity;

import com.diet.admin.core.BaseEntity;

import javax.persistence.*;

/**
 * @author LiuYu
 */ 
@Entity
@Table(name = "tb_tags")
public class Tags extends BaseEntity {

	/**
	 * 
	 */ 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	/**
	 * 
	 */ 
	@Column(name = "tagName")
	private String tagName;

	public Integer getId(){
		return id;
	}

	public void setId(Integer id){
		this.id=id;
	}

	public String getTagName(){
		return tagName;
	}

	public void setTagName(String tagName){
		this.tagName=tagName;
	}

}

