package com.spider.bsz.rest;

import org.axe.annotation.ioc.Autowired;
import org.axe.annotation.ioc.Controller;
import org.axe.annotation.mvc.Request;
import org.axe.annotation.mvc.RequestParam;
import org.axe.constant.RequestMethod;

import com.spider.bsz.service.Analyzer5khouse;
import com.spider.bsz.service.AnalyzerZjgzf;
import com.spider.bsz.service.SpiderTaskService;
import com.spider.common.annotation.Required;

@Controller(basePath="/spider",title="蜘蛛")
public class SpiderRest {

	@Autowired
	private AnalyzerZjgzf analyzerZjgzf;
	@Autowired
	private Analyzer5khouse analyzer5khouse;
	@Autowired
	private SpiderTaskService spiderTaskService;
	
	@Request(value="/clean",method=RequestMethod.GET)
	public void delete(){
		spiderTaskService.clean();
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
			analyzerZjgzf.analyze(page);
			spiderTaskService.stop(id);
		}
		return id;
	}
	
	@Request(value="/analyze/5khouse",method=RequestMethod.GET)
	public String analyze5khouse(
			@Required
			@RequestParam("page")Integer page,
			@Required
			@RequestParam("id")String id
			){
		boolean running = spiderTaskService.checkIfRunning(id);
		if(!running){
			analyzer5khouse.analyze(page);
			spiderTaskService.stop(id);
		}
		return id;
	}
}
