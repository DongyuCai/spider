package com.house.bsz.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.axe.annotation.ioc.Autowired;
import org.axe.annotation.ioc.Service;

import com.house.bsz.dao.VisitCountDao;
import com.house.bsz.entity.VisitCount;

@Service
public class VisitCountService {

	@Autowired
	private VisitCountDao visitCountDao;
	
	public void visit(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String code = sdf.format(new Date());
		VisitCount vc = new VisitCount();
		vc.setCode(code);
		
		vc = visitCountDao.getEntity(vc);
		if(vc == null){
			vc = new VisitCount();
			vc.setCode(code);
			vc.setCreateTime(new Date());
		}
		
		vc.setCount(vc.getCount()+1);
		vc.setUpdateTime(new Date());
		
		visitCountDao.saveEntity(vc);
	}
}
