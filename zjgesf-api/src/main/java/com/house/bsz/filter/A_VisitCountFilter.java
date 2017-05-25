package com.house.bsz.filter;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.axe.bean.mvc.Handler;
import org.axe.bean.mvc.Param;
import org.axe.exception.RestException;
import org.axe.helper.ioc.BeanHelper;
import org.axe.interface_.mvc.Filter;

import com.house.bsz.service.VisitCountService;

public class A_VisitCountFilter implements Filter{
	
	@Override
	public boolean doFilter(HttpServletRequest req, HttpServletResponse res, Param p, Handler h)
			throws RestException {
		try {
			VisitCountService vcs = BeanHelper.getBean(VisitCountService.class);
			vcs.visit();
		} catch (Exception e) {}
		return true;
	}

	@Override
	public void init() {
		
	}

	@Override
	public int setLevel() {
		return 1;
	}

	@Override
	public Pattern setMapping() {
		return Pattern.compile("^.*$");
	}

	@Override
	public Pattern setNotMapping() {
		return null;
	}

	@Override
	public void doEnd() {}

}
