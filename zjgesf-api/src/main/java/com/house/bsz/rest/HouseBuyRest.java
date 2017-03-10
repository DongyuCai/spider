package com.house.bsz.rest;

import org.axe.annotation.ioc.Autowired;
import org.axe.annotation.ioc.Controller;
import org.axe.annotation.mvc.Request;
import org.axe.annotation.mvc.RequestParam;
import org.axe.bean.persistence.Page;
import org.axe.constant.RequestMethod;

import com.house.bsz.entity.HouseBuy;
import com.house.bsz.service.HouseBuyService;

@Controller(basePath="/houseBuy",title="二手房")
public class HouseBuyRest {

	@Autowired
	private HouseBuyService houseBuyService;

	@Request(value="/{id}",method=RequestMethod.GET)
	public HouseBuy getDetail(
			@RequestParam("id")Long id){
		if(id == null){
			return null;
		}
		return houseBuyService.getDetail(id);
	}
	
	@Request(value="/page",method=RequestMethod.GET)
	public Page<HouseBuy> page(
			@RequestParam("page")Integer page,
			@RequestParam("pageSize")Integer pageSize,
			@RequestParam("keywords")String keywords
			){
		if(page == null || pageSize == null){
			return null;
		}
		return houseBuyService.page(page, pageSize, keywords);
	}
	
}
