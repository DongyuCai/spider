package test;

import java.util.List;

import org.axe.Axe;
import org.axe.helper.ioc.BeanHelper;
import org.axe.util.StringUtil;

import com.spider.bsz.dao.HouseBuyDao;
import com.spider.bsz.entity.HouseBuy;
import com.spider.common.util.RegUtil;

public class Test {
	public static void main(String[] args) {

		try {
			Axe.init();
			HouseBuyDao dao = BeanHelper.getBean(HouseBuyDao.class);
			List<String> pictures = dao.getPictures(10000);
			for(String pic:pictures){
				System.out.println(pic);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		/*String picturesHtml = RegUtil.getRegOne("<ul id=\"fyzp\"([[^<][^/][^u][^l][^>]]+)","<ul id=\"fyzp\"><li><img src=dfsaf></ul>asdfa");
		System.out.println(picturesHtml);
		*/
//		String laiYuanWangZhanFangYuanHao = RegUtil.getRegOne("房源编号：([0-9]+)", "房源编号：1234 aasdf 123");
//		String youErYuan = RegUtil.getRegOne("([^>|^<]+幼儿园)", "> abd > 镜像幼儿园<  adsf幼儿园");
//		String faBuShiJian = RegUtil.getRegOne("发布时间：([0-9]{4}-[0-9]{1,2}-[0-9]{1,2} [0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2})", "发布时间：2013-18-23 12:23:11(");
//		String huXing = RegUtil.getRegOne("户型：([^<]+)", "户型：1是凉亭<span>sbdfsd<span>");
//		String zhuangXiu = RegUtil.getRegOne("装修：([^<]+)", "装修：京珠那个</li><li>楼层啊时代发生地方装修：京珠那个</li><li>楼层");
//		String xiaoQu = RegUtil.getRegOne("title=\"([^\"]+)小区", "title=\"新乘花苑小区实景小区小区\" 小区");
//		String dian = RegUtil.getRegOne("<div class=\"name\">[^<]+<br/><span>([^<]+)", "436598.jpg\"/></a></div><div class=\"name\">李木子<br/><span>李木子房产城北新村店</span>");

		/*String html = "安静,视野好。学区:梁丰幼儿园、实验小学(东区)、东渡实验学校";
		StringBuilder xueQuBuf = new StringBuilder();
		String refFlag = "[\u4E00-\u9FFF]{2,}";
		String youErYuan = RegUtil.getRegOne("("+refFlag+"幼儿园)", html);
		String xiaoXue = RegUtil.getRegOne("("+refFlag+"小学)", html);
		String chuZhong = RegUtil.getRegOne("("+refFlag+"初中)", html);
		String zhongXue = RegUtil.getRegOne("("+refFlag+"中学)", html);
		String xueXiao = RegUtil.getRegOne("("+refFlag+"学校)", html);
		String[] xueQus = {youErYuan,xiaoXue,chuZhong,zhongXue,xueXiao};
		xueQuBuf.delete(0, xueQuBuf.length());
		for(String xueQu:xueQus){
			if(StringUtil.isNotEmpty(xueQu)){
				if(xueQuBuf.length() > 0){
					xueQuBuf.append(",");
				}
				xueQuBuf.append(xueQu);
			}
		}
		
		
		System.out.println(xueQuBuf.toString());*/
		
//		SimpleDateFormat sdf = new SimpleDateFormat();
//		System.out.println(sdf.format(new Date(1489081791000l)));
	}
}
