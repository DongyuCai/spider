package com.spider.main;

import java.util.List;

import com.spider.common.util.HtmlUtil;
import com.spider.common.util.RegUtil;

public class Test {

	public static void main(String[] args) {
		try {
			String url = "http://zjg.haofang007.com/esf/3575-no-no-no-no-no-no/0/1?zxcd=97";

			String htmlList = HtmlUtil.analyze(url,"UTF-8", "data-list", "p-bar");
			//分析每个a标签
			List<String> regAll = RegUtil.getRegAll("href=\"(http://zjg.haofang007.com/esf/detail/[0-9a-zA-Z/\\._]+)\" target=\"_blank\">[毛坯^<]+", htmlList);
			for(String href:regAll){
				String imgUl = HtmlUtil.analyze(href,"UTF-8", "class=\"s-item\"", "class=\"s-right\"");
				List<String> regAll2 = RegUtil.getRegAll("alt=\"室内图\"src=\"([^\"]+)", imgUl);
				for(String imgUrl:regAll2){
					System.out.println(imgUrl);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
