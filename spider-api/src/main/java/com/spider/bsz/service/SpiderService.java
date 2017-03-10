package com.spider.bsz.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.axe.annotation.ioc.Autowired;
import org.axe.annotation.ioc.Service;
import org.eclipse.jetty.util.StringUtil;

import com.spider.bsz.dao.HouseBuyDao;
import com.spider.bsz.entity.HouseBuy;
import com.spider.common.util.HtmlUtil;
import com.spider.common.util.PinYinUtil;
import com.spider.common.util.RegUtil;

@Service
public class SpiderService {
	
	@Autowired
	private HouseBuyDao houseBuyDao;
	
	public void clean(){
		long maxId = houseBuyDao.maxId();
		maxId = maxId-10000000;//保存1千万条数据
		houseBuyDao.clean(maxId);
	}

	public void analyzeZjgzf(int page) {
		String url = "http://www.zjgzf.cn/list_search.asp?typeto=1&Page="+page;
		
		String htmlList = HtmlUtil.analyze(url,"gb2312", "置顶结束，正文开始", "class=\"hotxiaoqu\"");
		//分析每个a标签
		List<String> regAll = RegUtil.getRegAll("href=\"([0-9a-zA-Z/\\._]+)\"", htmlList);
		StringBuilder keywordsBuf = new StringBuilder();
		StringBuilder keywordsPinYinBuf = new StringBuilder();
		StringBuilder xueQuBuf = new StringBuilder();
		for(String uri : regAll){
			try {
//				String html = HtmlUtil.analyze("http://www.zjgzf.cn"+uri, "gb2312", "div class=\"infoMain", " ");
				String html = HtmlUtil.analyze("http://www.zjgzf.cn"+uri, "gb2312", "div class=\"infoMain", "_blank\">同小区出售房源</a");
				html  = html.replaceAll("&#160;", " ");
				html = html.replaceAll("&nbsp;", " ");
				html = html.replaceAll("�", " ");
				
				do{
					HouseBuy house = new HouseBuy();
					house.setLaiYuanWangZhan("张家港房产网");
					String biaoTi = RegUtil.getRegOne("pLR10\"><h1>([^<]+)", html);
					if(StringUtil.isNotBlank(biaoTi)){
						house.setBiaoTi(biaoTi);
					}else{
						break;
					}
					String laiYuanWangZhanFangYuanHao = RegUtil.getRegOne("房源编号：([0-9]+)", html);
					if(StringUtil.isNotBlank(laiYuanWangZhanFangYuanHao)){
						//如果房源号已存在，就不需要再次解析和存储了
						int count = houseBuyDao.getHouseByLaiYuanWangZhanFangYuanHao(laiYuanWangZhanFangYuanHao, "张家港房产网");
						if(count > 0) break;
						
						house.setLaiYuanWangZhanFangYuanHao(laiYuanWangZhanFangYuanHao);
					}
					String faBuShiJian = RegUtil.getRegOne("发布时间：([0-9]{4}-[0-9]{1,2}-[0-9]{1,2} [0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2})", html);
					if(StringUtil.isNotBlank(faBuShiJian)){
						try {
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							Date time = sdf.parse(faBuShiJian);
							house.setFaBuShiJian(time);
						} catch (Exception e) {}
					}
					String jiaGe = RegUtil.getRegOne(">([0-9\\.]+)</b>万元", html);
					if(StringUtil.isNotBlank(jiaGe)){
						try {
							house.setJiaGe(Double.valueOf(jiaGe));
						} catch (Exception e) {}
					}
					String huXing = RegUtil.getRegOne("户型：([^<]+)", html);
					if(StringUtil.isNotBlank(huXing)){
						String[] split = huXing.split("-");
						if(split.length > 0){
							house.setHuXing(split[0].trim());
						}
						if(split.length > 1){
							try {
								String mianJi = split[1];
								if(mianJi.contains("平米")){
									mianJi = mianJi.substring(0, mianJi.indexOf("平米"));
								}
								house.setMianJi(Double.valueOf(mianJi.trim()));
							} catch (Exception e) {}
						}
					}
					String zhuangXiu = RegUtil.getRegOne("装修：([^<]+)", html);
					if(StringUtil.isNotBlank(zhuangXiu)){
						house.setZhuangXiu(zhuangXiu);
					}
					String louCeng = RegUtil.getRegOne("楼层：([^<]+)", html);
					if(StringUtil.isNotBlank(louCeng)){
						house.setLouCeng(louCeng);
					}
					String xiaoQu = RegUtil.getRegOne("title=\"([^\"]+)小区", html);
					if(StringUtil.isNotBlank(xiaoQu)){
						house.setXiaoQu(xiaoQu);
					}
					String lianXiFangShi = RegUtil.getRegOne("<li class=\"tel\"><p><b>([^<]+)", html);
					if(StringUtil.isNotBlank(lianXiFangShi)){
						house.setLianXiFangShi(lianXiFangShi);
					}
					String lianXiRen = RegUtil.getRegOne("姓名：<strong>([^<]+)", html);
					if(StringUtil.isNotBlank(lianXiRen)){
						house.setLianXiRen(lianXiRen);
					}
					
					//取房源详情描述
					if(html.contains("class=\"FangyuanCon")){
						//学区
						String refFlag = "[^>|^<|^=|^,|^\\.]{2,}";
						String youErYuan = RegUtil.getRegOne("("+refFlag+"幼儿园)", html);
						String xiaoXue = RegUtil.getRegOne("("+refFlag+"小学)", html);
						String chuZhong = RegUtil.getRegOne("("+refFlag+"初中)", html);
						String zhongXue = RegUtil.getRegOne("("+refFlag+"中学)", html);
						String xueXiao = RegUtil.getRegOne("("+refFlag+"学校)", html);
						String[] xueQus = {youErYuan,xiaoXue,chuZhong,zhongXue,xueXiao};
						xueQuBuf.delete(0, xueQuBuf.length());
						for(String xueQu:xueQus){
							if(StringUtil.isNotBlank(xueQu)){
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
					if(StringUtil.isNotBlank(biaoTi)){
						keywordsBuf.append(biaoTi);
					}
					if(StringUtil.isNotBlank(xiaoQu)){
						//小区转拼音
						keywordsPinYinBuf.append(PinYinUtil.getPingYin(xiaoQu));
						keywordsBuf.append(xiaoQu);
					}
					if(StringUtil.isNotBlank(house.getXueQu())){
						//学区转拼音
						keywordsPinYinBuf.append(PinYinUtil.getPingYin(house.getXueQu()));
						keywordsBuf.append(house.getXueQu());
					}
					if(StringUtil.isNotBlank(huXing)){
						//户型转拼音
						keywordsPinYinBuf.append(PinYinUtil.getPingYin(huXing));
						keywordsBuf.append(huXing);
					}
					if(StringUtil.isNotBlank(zhuangXiu)){
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
