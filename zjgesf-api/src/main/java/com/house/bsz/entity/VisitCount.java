package com.house.bsz.entity;

import java.util.Date;

import org.axe.annotation.persistence.Id;
import org.axe.annotation.persistence.Table;
import org.axe.constant.IdGenerateWay;

@Table("visit_count")
public class VisitCount{

	@Id(idGenerateWay=IdGenerateWay.NONE)
	private String code;
	
	private long count;
	
	private Date createTime;
	
	private Date updateTime;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
}
