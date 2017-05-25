package com.spider.bsz.dao;

import java.util.List;

import org.axe.annotation.persistence.Dao;
import org.axe.annotation.persistence.Sql;
import org.axe.interface_.persistence.BaseRepository;

@Dao
public interface HouseBuyDao extends BaseRepository{

	@Sql("select max(id) from HouseBuy")
	public long maxId();

	@Sql("select pictures from HouseBuy where id < ?1")
	public List<String> getPictures(long maxId);
	
	@Sql("delete from HouseBuy where id < ?1")
	public int clean(long maxId);
	
	@Sql("select count(1) from HouseBuy where laiYuanWangZhanFangYuanHao = ?1 and laiYuanWangZhan = ?2")
	public int getHouseByLaiYuanWangZhanFangYuanHao(String laiYuanWangZhanFangYuanHao,String laiYuanWangZhan);
}
