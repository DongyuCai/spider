package com.spider.bsz.service;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.axe.annotation.ioc.Autowired;
import org.axe.annotation.ioc.Service;
import org.axe.util.CollectionUtil;
import org.axe.util.PropsUtil;
import org.axe.util.StringUtil;

import com.spider.bsz.dao.HouseBuyDao;
import com.spider.bsz.dao.SpiderTaskDao;
import com.spider.bsz.entity.SpiderTask;

@Service
public class SpiderTaskService {

	private String imgDir = "";
	{
		Properties CONFIG_PROPS = PropsUtil.loadProps("setting.properties");
		imgDir = PropsUtil.getString(CONFIG_PROPS, "img_dir");
	}
	
	@Autowired
	private SpiderTaskDao spiderTaskDao;
	@Autowired
	private HouseBuyDao houseBuyDao;
	
	public void clean(){
		long maxId = houseBuyDao.maxId();
		maxId = maxId-100000;//保存10万条数据，168301条数据默认就表满了
		List<String> pictures = houseBuyDao.getPictures(maxId);
		if(CollectionUtil.isNotEmpty(pictures)){
			for(String pic:pictures){
				if(StringUtil.isNotEmpty(pic)){
					String[] split = pic.split(",");
					if(split != null && split.length > 0){
						for(String p:split){
							if(StringUtil.isNotEmpty(p)){
								try {
									File file = new File(imgDir+p);
									if(file.exists()){
										file.delete();
									}
								} catch (Exception e) {
								}
							}
						}
					}
				}
			}
		}
		houseBuyDao.clean(maxId);
	}
	
	public boolean checkIfRunning(String id){
		SpiderTask st = new SpiderTask();
		st.setId(id);
		st = spiderTaskDao.getEntity(st);
		do{
			if(st == null){
				st = new SpiderTask();
				st.setId(id);
				st.setCreateTime(new Date());
				st = spiderTaskDao.saveEntity(st);
				break;
			}
			if(st.getStatus() == -1) break;
			/*
			//如果超过10分钟，就可以继续
			long t1 = st.getUpdateTime().getTime();
			long t2 = new Date().getTime();
			long du = 10*60*1000;
			if(t2-t1 > du){
				break;
			}*/
			
			return true;//正在执行
		}while(false);
		return false;
	}
	
	public void stop(String id){
		SpiderTask st = new SpiderTask();
		st.setId(id);
		st = spiderTaskDao.getEntity(st);
		do{
			if(st == null) break;
			
			st.setStatus(-1);
			st.setUpdateTime(new Date());
			spiderTaskDao.saveEntity(st);
		}while(false);
	}
}
