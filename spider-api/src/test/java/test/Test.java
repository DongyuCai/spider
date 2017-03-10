package test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {
	public static void main(String[] args) {


//		String laiYuanWangZhanFangYuanHao = RegUtil.getRegOne("房源编号：([0-9]+)", "房源编号：1234 aasdf 123");
//		String youErYuan = RegUtil.getRegOne("([^>|^<]+幼儿园)", "> abd > 镜像幼儿园<  adsf幼儿园");
//		String faBuShiJian = RegUtil.getRegOne("发布时间：([0-9]{4}-[0-9]{1,2}-[0-9]{1,2} [0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2})", "发布时间：2013-18-23 12:23:11(");
//		String huXing = RegUtil.getRegOne("户型：([^<]+)", "户型：1是凉亭<span>sbdfsd<span>");
//		String zhuangXiu = RegUtil.getRegOne("装修：([^<]+)", "装修：京珠那个</li><li>楼层啊时代发生地方装修：京珠那个</li><li>楼层");
//		String xiaoQu = RegUtil.getRegOne("title=\"([^\"]+)小区", "title=\"新乘花苑小区实景小区小区\" 小区");
//		System.out.println(xiaoQu);
		
		SimpleDateFormat sdf = new SimpleDateFormat();
		System.out.println(sdf.format(new Date(1489081791000l)));
	}
}
