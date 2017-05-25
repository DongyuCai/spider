package com.house.bsz.filter;

import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.axe.bean.mvc.FormParam;
import org.axe.bean.mvc.Handler;
import org.axe.bean.mvc.Param;
import org.axe.exception.RestException;
import org.axe.interface_.mvc.Filter;
import org.axe.util.CollectionUtil;
import org.axe.util.StringUtil;

public class B_AntiSqlAttackFilter implements Filter{
	
	@Override
	public boolean doFilter(HttpServletRequest req, HttpServletResponse res, Param p, Handler h)
			throws RestException {
		List<FormParam> list = p.getFormParamList();
		if(CollectionUtil.isNotEmpty(list)){
			for(FormParam fp:list){
				String value = fp.getFieldValue();
				//防止预处理
				if(StringUtil.isNotEmpty(value)){
					value  = value.replaceAll("[?]", "");
					fp.setFieldValue(value);
				}
			}
		}
		
		return true;
	}

	@Override
	public void init() {
		
	}

	@Override
	public int setLevel() {
		return 2;
	}

	@Override
	public Pattern setMapping() {
		return Pattern.compile("^GET:.*$");
	}

	@Override
	public Pattern setNotMapping() {
		return null;
	}

	@Override
	public void doEnd() {}

}
