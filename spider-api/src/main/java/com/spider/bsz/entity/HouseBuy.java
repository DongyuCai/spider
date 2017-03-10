package com.spider.bsz.entity;

import java.util.Date;

import org.axe.annotation.persistence.ColumnDefine;
import org.axe.annotation.persistence.Comment;
import org.axe.annotation.persistence.Table;

@Table("house_buy")
public class HouseBuy extends IdEntity{
	
	@Comment("标题")
	private String biaoTi;
	
	@Comment("发布时间")
	private Date faBuShiJian;

	@Comment("小区")
	private String xiaoQu;
	
	@Comment("学区")
	private String xueQu;
	
	@Comment("面积")
	private Double mianJi;
	
	@Comment("价格")
	private Double jiaGe;
	
	@Comment("户型")
	private String huXing;
	
	@Comment("装修")
	private String zhuangXiu;
	
	@Comment("楼层")
	private String louCeng;
	
	@Comment("联系人")
	private String lianXiRen;
	
	@Comment("联系方式")
	private String lianXiFangShi;

	@Comment("来源网站")
	private String laiYuanWangZhan;
	
	@Comment("来源网站的标示：房源号")
	private String laiYuanWangZhanFangYuanHao;

	@ColumnDefine("varchar(1000) DEFAULT NULL COMMENT '查询关键字'")
	private String keywords;
	
	private Date createTime;

	public String getBiaoTi() {
		return biaoTi;
	}

	public void setBiaoTi(String biaoTi) {
		biaoTi = biaoTi.length() > 250?biaoTi.substring(0, 250):biaoTi;
		this.biaoTi = biaoTi;
	}

	public Date getFaBuShiJian() {
		return faBuShiJian;
	}

	public void setFaBuShiJian(Date faBuShiJian) {
		this.faBuShiJian = faBuShiJian;
	}

	public String getXiaoQu() {
		return xiaoQu;
	}

	public void setXiaoQu(String xiaoQu) {
		xiaoQu = xiaoQu.length() > 250?xiaoQu.substring(0, 250):xiaoQu;
		this.xiaoQu = xiaoQu;
	}

	public Double getMianJi() {
		return mianJi;
	}

	public void setMianJi(Double mianJi) {
		this.mianJi = mianJi;
	}

	public Double getJiaGe() {
		return jiaGe;
	}

	public void setJiaGe(Double jiaGe) {
		this.jiaGe = jiaGe;
	}

	public String getHuXing() {
		return huXing;
	}

	public void setHuXing(String huXing) {
		huXing = huXing.length() > 250?huXing.substring(0, 250):huXing;
		this.huXing = huXing;
	}

	public String getZhuangXiu() {
		return zhuangXiu;
	}

	public void setZhuangXiu(String zhuangXiu) {
		zhuangXiu = zhuangXiu.length() > 250?zhuangXiu.substring(0, 250):zhuangXiu;
		this.zhuangXiu = zhuangXiu;
	}

	public String getLouCeng() {
		return louCeng;
	}

	public void setLouCeng(String louCeng) {
		louCeng = louCeng.length() > 250?louCeng.substring(0, 250):louCeng;
		this.louCeng = louCeng;
	}

	public String getLianXiRen() {
		return lianXiRen;
	}

	public void setLianXiRen(String lianXiRen) {
		lianXiRen = lianXiRen.length() > 250?lianXiRen.substring(0, 250):lianXiRen;
		this.lianXiRen = lianXiRen;
	}

	public String getLianXiFangShi() {
		return lianXiFangShi;
	}

	public void setLianXiFangShi(String lianXiFangShi) {
		lianXiFangShi = lianXiFangShi.length() > 250?lianXiFangShi.substring(0, 250):lianXiFangShi;
		this.lianXiFangShi = lianXiFangShi;
	}

	public String getLaiYuanWangZhan() {
		return laiYuanWangZhan;
	}

	public void setLaiYuanWangZhan(String laiYuanWangZhan) {
		this.laiYuanWangZhan = laiYuanWangZhan;
	}


	public String getLaiYuanWangZhanFangYuanHao() {
		return laiYuanWangZhanFangYuanHao;
	}

	public void setLaiYuanWangZhanFangYuanHao(String laiYuanWangZhanFangYuanHao) {
		laiYuanWangZhanFangYuanHao = laiYuanWangZhanFangYuanHao.length() > 250?laiYuanWangZhanFangYuanHao.substring(0, 250):laiYuanWangZhanFangYuanHao;
		this.laiYuanWangZhanFangYuanHao = laiYuanWangZhanFangYuanHao;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		keywords = keywords.length() > 1000?keywords.substring(0, 1000):keywords;
		this.keywords = keywords;
	}

	public String getXueQu() {
		return xueQu;
	}

	public void setXueQu(String xueQu) {
		xueQu = xueQu.length() > 250?xueQu.substring(0, 250):xueQu;
		this.xueQu = xueQu;
	}
	
}
