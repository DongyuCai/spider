package com.spider.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.axe.util.CollectionUtil;

public class RegUtil {
	public static List<String> getRegAll(String reg,String str){
		Pattern compile = Pattern.compile(reg);
		Matcher matcher = compile.matcher(str);
		List<String> result = new ArrayList<>();
		while(matcher.find() && matcher.groupCount() > 0){
			result.add(matcher.group(1));
		}
		return result;
	}
	
	public static String getRegOne(String reg,String str){
		List<String> all = getRegAll(reg, str);
		return CollectionUtil.isEmpty(all)?null:all.get(0);
	}
}
