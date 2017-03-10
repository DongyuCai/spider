package com.spider.bsz.entity;

import java.util.Date;

import org.axe.annotation.persistence.Id;
import org.axe.annotation.persistence.Table;
import org.axe.constant.IdGenerateWay;

@Table("spider_task")
public class SpiderTask {
	
	@Id(idGenerateWay=IdGenerateWay.NONE)
	private String id;
	
	//0正在执行 -1已停止
	private int status;
	
	private Date createTime;
	
	private Date updateTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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
