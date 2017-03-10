package com.house.bsz.service;

import org.axe.annotation.ioc.Autowired;
import org.axe.annotation.ioc.Service;
import org.axe.bean.persistence.Page;
import org.axe.bean.persistence.PageConfig;
import org.axe.util.StringUtil;

import com.house.bsz.dao.HouseBuyDao;
import com.house.bsz.entity.HouseBuy;

@Service
public class HouseBuyService {
	@Autowired
	private HouseBuyDao houseBuyDao;

	public Page<HouseBuy> page(int page,int pageSize,String keywords){
		StringBuilder buf = new StringBuilder();
		if(StringUtil.isNotEmpty(keywords)){
			buf.append("and keywords like '");
			for(int i=0;i<keywords.length();i++){
				buf.append("%").append(keywords.charAt(i)).append("%");
			}
			buf.append("'");
		}
		buf.append(" order by faBuShiJian desc");
		
		PageConfig pc = new PageConfig(page, pageSize);
		
		return houseBuyDao.page(buf.toString(), pc);
	}

	public HouseBuy getDetail(long id) {
		HouseBuy houseBuy = new HouseBuy();
		houseBuy.setId(id);
		return houseBuyDao.getEntity(houseBuy);
	}
}
