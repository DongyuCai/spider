package com.spider.bsz.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.axe.annotation.ioc.Autowired;
import org.axe.annotation.ioc.Service;
import org.axe.util.StringUtil;

import com.spider.bsz.dao.HouseBuyDao;
import com.spider.bsz.entity.HouseBuy;
import com.spider.common.util.HtmlUtil;
import com.spider.common.util.PinYinUtil;
import com.spider.common.util.RegUtil;

@Service
public class HouseBuy5khouseService {
	
	@Autowired
	private HouseBuyDao houseBuyDao;

	public void spider(int page) {
		String url = "http://zjg.5khouse.com/sell/0-0/-b-c-d-f-g-h-i"+page+"-j-k-l.aspx";
		
		String htmlList = HtmlUtil.analyze(url,"utf-8", "<ul class=\"house\"", "<ul id=\"page\"");
		//分析每个a标签
		List<String> regAll = RegUtil.getRegAll("href=\"(http://[0-9a-zA-Z/\\._]+)\"", htmlList);
		StringBuilder keywordsBuf = new StringBuilder();
		StringBuilder keywordsPinYinBuf = new StringBuilder();
		StringBuilder xueQuBuf = new StringBuilder();
		Set<String> uriSet = new HashSet<>();
		for(String uri : regAll){
			if(!uri.contains("/sell/")){
				continue;
			}
			if(uriSet.contains(uri)){
				continue;
			}
			try {
				uriSet.add(uri);
				String html = HtmlUtil.analyze(uri, "utf-8", "<div class=\"top\">", "<a name=\"jianjie\"></a>");
				html  = html.replaceAll("&#160;", " ");
				html = html.replaceAll("&nbsp;", " ");
				html = html.replaceAll("�", " ");
				
				do{
					HouseBuy house = new HouseBuy();
					house.setLaiYuanWangZhan("张家港看房网");
					String biaoTi = RegUtil.getRegOne("class=\"l\"><h1>([^<]+)", html);
					if(StringUtil.isNotEmpty(biaoTi)){
						house.setBiaoTi(biaoTi);
					}else{
						break;
					}
					String laiYuanWangZhanFangYuanHao = RegUtil.getRegOne("房源编号：([0-9]+)", html);
					if(StringUtil.isNotEmpty(laiYuanWangZhanFangYuanHao)){
						//如果房源号已存在，就不需要再次解析和存储了
						int count = houseBuyDao.getHouseByLaiYuanWangZhanFangYuanHao(laiYuanWangZhanFangYuanHao, "张家港房产网");
						if(count > 0) break;
						
						house.setLaiYuanWangZhanFangYuanHao(laiYuanWangZhanFangYuanHao);
					}
					String faBuShiJian = RegUtil.getRegOne("更新时间：([0-9]{4}/[0-9]{1,2}/[0-9]{1,2} [0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2})", html);
					if(StringUtil.isNotEmpty(faBuShiJian)){
						try {
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
							Date time = sdf.parse(faBuShiJian);
							house.setFaBuShiJian(time);
						} catch (Exception e) {}
					}
					String jiaGe = RegUtil.getRegOne("id=\"zjia\">([0-9\\.]+)", html);
					if(StringUtil.isNotEmpty(jiaGe)){
						try {
							house.setJiaGe(Double.valueOf(jiaGe));
						} catch (Exception e) {}
					}
					String huXing = RegUtil.getRegOne("户<span></span>型：<s>([^<]+)", html);
					if(StringUtil.isNotEmpty(huXing)){
						house.setHuXing(huXing.trim());
					}
					String mianJi = RegUtil.getRegOne("建筑面积：<s>([0-9\\.]+)", html);
					if(StringUtil.isNotEmpty(mianJi)){
						try {
							house.setMianJi(Double.valueOf(mianJi));
						} catch (Exception e) {}
					}
					String zhuangXiu = RegUtil.getRegOne("装修情况：<s>([^<]+)", html);
					if(StringUtil.isNotEmpty(zhuangXiu)){
						house.setZhuangXiu(zhuangXiu);
					}
					String louCeng = RegUtil.getRegOne("所在楼层：<s>([^<]+)", html);
					if(StringUtil.isNotEmpty(louCeng)){
						house.setLouCeng(louCeng);
					}
					String xiaoQu = RegUtil.getRegOne("<dt>小区：([^<]+)", html);
					if(StringUtil.isEmpty(xiaoQu)){
						xiaoQu = RegUtil.getRegOne("title=\"([^\"]+)小区", html);
					}
					if(StringUtil.isNotEmpty(xiaoQu)){
						house.setXiaoQu(xiaoQu);
					}
					String lianXiFangShi = RegUtil.getRegOne("<span class=\"lx\"><b>([^<]+)", html);
					if(StringUtil.isNotEmpty(lianXiFangShi)){
						lianXiFangShi = lianXiFangShi.replaceAll(" ", "");
						house.setLianXiFangShi(lianXiFangShi);
					}
					String lianXiRen = RegUtil.getRegOne("<div class=\"name\">([^<]+)", html);
					if(StringUtil.isNotEmpty(lianXiRen)){
						String dian = RegUtil.getRegOne("<div class=\"name\">[^<]+<br/><span>([^<]+)", html);
						if(StringUtil.isNotEmpty(dian)){
							lianXiRen = lianXiRen+"("+dian+")";
						}
						house.setLianXiRen(lianXiRen);
					}
					
					//取房源详情描述
					if(html.contains("class=\"FangyuanCon")){
						//学区
						String refFlag = "[\u4E00-\u9FFF]{2,}";
						String youErYuan = RegUtil.getRegOne("("+refFlag+"幼儿园)", html);
						String xiaoXue = RegUtil.getRegOne("("+refFlag+"小学)", html);
						String chuZhong = RegUtil.getRegOne("("+refFlag+"初中)", html);
						String zhongXue = RegUtil.getRegOne("("+refFlag+"中学)", html);
						String xueXiao = RegUtil.getRegOne("("+refFlag+"学校)", html);
						String[] xueQus = {youErYuan,xiaoXue,chuZhong,zhongXue,xueXiao};
						xueQuBuf.delete(0, xueQuBuf.length());
						for(String xueQu:xueQus){
							if(StringUtil.isNotEmpty(xueQu)){
								if(xueQuBuf.length() > 0){
									xueQuBuf.append(",");
								}
								xueQuBuf.append(xueQu);
							}
						}
						
						if(xueQuBuf.length() > 0){
							house.setXueQu(xueQuBuf.toString());
						}
						
						//图片
						
					}
					
					keywordsBuf.delete(0, keywordsBuf.length());
					keywordsPinYinBuf.delete(0, keywordsPinYinBuf.length());
					if(StringUtil.isNotEmpty(biaoTi)){
						keywordsBuf.append(biaoTi);
					}
					if(StringUtil.isNotEmpty(xiaoQu)){
						//小区转拼音
						keywordsPinYinBuf.append(PinYinUtil.getPingYin(xiaoQu));
						keywordsBuf.append(xiaoQu);
					}
					if(StringUtil.isNotEmpty(house.getXueQu())){
						//学区转拼音
						keywordsPinYinBuf.append(PinYinUtil.getPingYin(house.getXueQu()));
						keywordsBuf.append(house.getXueQu());
					}
					if(StringUtil.isNotEmpty(huXing)){
						//户型转拼音
						keywordsPinYinBuf.append(PinYinUtil.getPingYin(huXing));
						keywordsBuf.append(huXing);
					}
					if(StringUtil.isNotEmpty(zhuangXiu)){
						//装修转拼音
						keywordsPinYinBuf.append(PinYinUtil.getPingYin(zhuangXiu));
						keywordsBuf.append(zhuangXiu);
					}
					
					keywordsPinYinBuf.append(keywordsBuf.toString());
					house.setKeywords(keywordsPinYinBuf.toString());
					
					house.setCreateTime(new Date());
					if(house.getFaBuShiJian() == null){
						house.setFaBuShiJian(house.getCreateTime());
					}
					
					houseBuyDao.saveEntity(house);
					
				}while(false);
			} catch (Exception e) {}
		}
	}
	
	
	
}
