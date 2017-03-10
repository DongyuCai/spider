package com.spider.bsz.service;

import java.util.Date;

import org.axe.annotation.ioc.Autowired;
import org.axe.annotation.ioc.Service;

import com.spider.bsz.dao.HouseBuyDao;
import com.spider.bsz.dao.SpiderTaskDao;
import com.spider.bsz.entity.SpiderTask;

@Service
public class SpiderTaskService {

	@Autowired
	private SpiderTaskDao spiderTaskDao;
	@Autowired
	private HouseBuyDao houseBuyDao;
	
	public void clean(){
		long maxId = houseBuyDao.maxId();
		maxId = maxId-10000000;//保存1千万条数据
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
			
			//如果超过10分钟，就可以继续
			long t1 = st.getUpdateTime().getTime();
			long t2 = new Date().getTime();
			long du = 10*60*1000;
			if(t2-t1 > du){
				break;
			}
			
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
