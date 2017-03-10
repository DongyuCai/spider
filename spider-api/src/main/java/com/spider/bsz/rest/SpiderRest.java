package com.spider.bsz.rest;

import org.axe.annotation.ioc.Autowired;
import org.axe.annotation.ioc.Controller;
import org.axe.annotation.mvc.Request;
import org.axe.annotation.mvc.RequestParam;
import org.axe.constant.RequestMethod;

import com.spider.bsz.service.SpiderService;
import com.spider.bsz.service.SpiderTaskService;
import com.spider.common.annotation.Required;

@Controller(basePath="/spider",title="蜘蛛")
public class SpiderRest {

	@Autowired
	private SpiderService spiderService;
	@Autowired
	private SpiderTaskService spiderTaskService;
	
	@Request(value="/clean",method=RequestMethod.GET)
	public void delete(){
		spiderService.clean();
	}
	
	@Request(value="/analyze/zjgzf",method=RequestMethod.GET)
	public String analyzeZjgzf(
			@Required
			@RequestParam("page")Integer page,
			@Required
			@RequestParam("id")String id
			){
		boolean running = spiderTaskService.checkIfRunning(id);
		if(!running){
			spiderService.analyzeZjgzf(page);
			spiderTaskService.stop(id);
		}
		return id;
	}
	
}
