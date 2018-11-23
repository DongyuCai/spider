package com.spider.bsz.rest;

import org.axe.annotation.ioc.Autowired;
import org.axe.annotation.ioc.Controller;
import org.axe.annotation.mvc.Request;
import org.axe.annotation.mvc.RequestParam;
import org.axe.constant.RequestMethod;

import com.spider.bsz.service.HouseBuy5khouseService;
import com.spider.bsz.service.HouseBuyZjgzfService;
import com.spider.bsz.service.SpiderTaskService;
import com.spider.common.annotation.Required;

@Controller(basePath="/spider/house_buy",title="二手房蜘蛛")
public class HouseBuySpiderRest {

	@Autowired
	private HouseBuyZjgzfService zjgzfService;
	@Autowired
	private HouseBuy5khouseService _5khouseService;
	@Autowired
	private SpiderTaskService spiderTaskService;
	
	@Request(title="清除",value="/clean",method=RequestMethod.GET)
	public void delete(){
		spiderTaskService.clean();
	}
	
	@Request(title="张家港找房网",value="/zjgzf",method=RequestMethod.GET)
	public String zjgzf(
			@Required
			@RequestParam("page")Integer page,
			@Required
			@RequestParam("id")String id
			){
		boolean running = spiderTaskService.checkIfRunning(id);
		if(!running){
			zjgzfService.spider(page);
			spiderTaskService.stop(id);
		}
		return id;
	}
	
	@Request(title="看房网",value="/5khouse",method=RequestMethod.GET)
	public String _5khouse(
			@Required
			@RequestParam("page")Integer page,
			@Required
			@RequestParam("id")String id
			){
		boolean running = spiderTaskService.checkIfRunning(id);
		if(!running){
			_5khouseService.spider(page);
			spiderTaskService.stop(id);
		}
		return id;
	}
	
}
