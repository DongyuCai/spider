package com.house.bsz.dao;

import org.axe.annotation.persistence.Dao;
import org.axe.annotation.persistence.Sql;
import org.axe.bean.persistence.Page;
import org.axe.bean.persistence.PageConfig;
import org.axe.interface_.persistence.BaseRepository;

import com.house.bsz.entity.HouseBuy;

@Dao
public interface HouseBuyDao extends BaseRepository{
	
	@Sql("select * from HouseBuy where 1=1 #1")
	public Page<HouseBuy> page(String append,PageConfig pageConfig);
}
