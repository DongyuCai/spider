package com.spider.bsz.service;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.axe.annotation.ioc.Autowired;
import org.axe.annotation.ioc.Service;
import org.axe.util.CollectionUtil;
import org.axe.util.HttpUtil;
import org.axe.util.PropsUtil;
import org.axe.util.StringUtil;

import com.spider.bsz.dao.HouseBuyDao;
import com.spider.bsz.entity.HouseBuy;
import com.spider.common.util.HtmlUtil;
import com.spider.common.util.PinYinUtil;
import com.spider.common.util.RegUtil;

@Service
public class HouseBuyZjgzfService {
	private String imgDir = "";
	{
		Properties CONFIG_PROPS = PropsUtil.loadProps("setting.properties");
		imgDir = PropsUtil.getString(CONFIG_PROPS, "img_dir");
	}
	
	@Autowired
	private HouseBuyDao houseBuyDao;

	public void spider(int page) {
		String url = "http://www.zjgzf.cn/list_search.asp?typeto=1&Page="+page;
		
		String htmlList = HtmlUtil.analyze(url,"gb2312", "imageField322", "class=\"hotxiaoqu\"");
		//分析每个a标签
		List<String> regAll = RegUtil.getRegAll("href=\"([0-9a-zA-Z/\\._]+)\"", htmlList);
		StringBuilder keywordsBuf = new StringBuilder();
		StringBuilder keywordsPinYinBuf = new StringBuilder();
		StringBuilder xueQuBuf = new StringBuilder();
		Set<String> uriSet = new HashSet<>();
		for(String uri : regAll){
			if(!uri.contains("_sale.html")){
				continue;
			}
			if(uriSet.contains(uri)){
				continue;
			}
			try {
				uriSet.add(uri);
				String houseUrl = "http://www.zjgzf.cn"+uri;
				String html = HtmlUtil.analyze(houseUrl, "gb2312", "div class=\"infoMain", "_blank\">同小区出售房源</a");
				html  = html.replaceAll("&#160;", " ");
				html = html.replaceAll("&nbsp;", " ");
				html = html.replaceAll("�", " ");
				
				do{
					HouseBuy house = new HouseBuy();
					house.setLaiYuanWangZhanUrl(houseUrl);
					house.setLaiYuanWangZhan("张家港房产网");
					String biaoTi = RegUtil.getRegOne("pLR10\"><h1>([^<]+)", html);
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
					String faBuShiJian = RegUtil.getRegOne("发布时间：([0-9]{4}-[0-9]{1,2}-[0-9]{1,2} [0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2})", html);
					if(StringUtil.isNotEmpty(faBuShiJian)){
						try {
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							Date time = sdf.parse(faBuShiJian);
							house.setFaBuShiJian(time);
						} catch (Exception e) {}
					}
					String jiaGe = RegUtil.getRegOne(">([0-9\\.]+)</b>万元", html);
					if(StringUtil.isNotEmpty(jiaGe)){
						try {
							house.setJiaGe(Double.valueOf(jiaGe));
						} catch (Exception e) {}
					}
					String huXing = RegUtil.getRegOne("户型: ([^<]+)", html);
					if(StringUtil.isNotEmpty(huXing)){
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
					String zhuangXiu = RegUtil.getRegOne("装修: ([^<]+)", html);
					if(StringUtil.isNotEmpty(zhuangXiu)){
						house.setZhuangXiu(zhuangXiu);
					}
					String louCeng = RegUtil.getRegOne("楼层: ([^<]+)", html);
					if(StringUtil.isNotEmpty(louCeng)){
						house.setLouCeng(louCeng);
					}
					String xiaoQu = RegUtil.getRegOne("title=\"([^\"]+)小区", html);
					if(StringUtil.isEmpty(xiaoQu))
						xiaoQu = RegUtil.getRegOne("em class=\"act\">([^\"]+)小区简介", html);
					if(StringUtil.isNotEmpty(xiaoQu)){
						house.setXiaoQu(xiaoQu);
					}
					String lianXiFangShi = RegUtil.getRegOne("<li class=\"tel\"><p><b>([^<]+)", html);
					if(StringUtil.isNotEmpty(lianXiFangShi)){
						house.setLianXiFangShi(lianXiFangShi);
					}
					String lianXiRen = RegUtil.getRegOne("姓名：<strong>([^<]+)", html);
					if(StringUtil.isNotEmpty(lianXiRen)){
						house.setLianXiRen(lianXiRen);
					}
					
					//取房源详情描述
					if(html.contains("FangyuanCon")){
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
						StringBuilder picturesBuf = new StringBuilder();
						List<String> imgUrlList = RegUtil.getRegAll("src=\"(http://img.zjgzf.cn/attached/image/[0-9]+/[0-9_]+\\.[a-zA-Z]+)\"", html);
						if(CollectionUtil.isEmpty(imgUrlList)){
							imgUrlList = RegUtil.getRegAll("src=\"(/fuwu/xiaoqu/uploads/[0-9]+\\.[a-zA-Z]+)\"", html);
						}
						if(CollectionUtil.isEmpty(imgUrlList)){
							imgUrlList = RegUtil.getRegAll("src=\"(http://www.zjgzf.cn/fuwu/xiaoqu/uploads/[0-9]+\\.[a-zA-Z]+)\"", html);
						}
						Set<String> imgUrlSet = new HashSet<>();
						for(String imgUrl:imgUrlList){
							if(imgUrl.startsWith("/")){
								imgUrl = "http://www.zjgzf.cn"+imgUrl;
							}
							if(imgUrlSet.contains(imgUrl)){
								continue;
							}
							FileOutputStream out = null;
							try {
								for(int i=0;i<3;i++){
									String fileName = (System.currentTimeMillis()+Integer.parseInt(StringUtil.getRandomString(3, "123456789")))+imgUrl.substring(imgUrl.lastIndexOf("."));
									if(picturesBuf.length()+fileName.length() > 250){
										break;
									}
									byte[] pictureData = HttpUtil.downloadGet(imgUrl);
									File file = new File(imgDir+fileName);
									if(file.exists()) continue;
									
									out = new FileOutputStream(file);
									out.write(pictureData, 0, pictureData.length);
									out.close();
									
									imgUrlSet.add(imgUrl);
									if(picturesBuf.length() > 0){
										picturesBuf.append(",");
									}
									picturesBuf.append(fileName);
									break;
								}
							} catch (Exception e) {
								System.out.println("url: "+houseUrl+" 图片有问题");
							} finally {
								if(out != null){
									try {
										out.close();
									} catch (Exception e2) {}
								}
							}
						}
						house.setPictures(picturesBuf.toString());
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
