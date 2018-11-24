package com.spider.main;

import java.awt.image.BufferedImage;
import java.util.List;

import org.axe.util.HttpUtil;

import com.spider.common.util.HtmlUtil;
import com.spider.common.util.RegUtil;
import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

public class GetHousePicture {

	public static void main(String[] args) {
		for(int i=1;i<10;i++){
			System.out.println(i);
			getPicture(i);
		}
		System.exit(0);
	}
	
	public static void getPicture(int page) {
		try {
			String url = "http://zjg.haofang007.com/esf/no-no-no-no-no-no-no/0/"+page+"?zxcd=97";

			String htmlList = HtmlUtil.analyze(url,"UTF-8", "data-list", "p-bar");
			//分析每个a标签
			List<String> regAll = RegUtil.getRegAll("href=\"(http://zjg.haofang007.com/esf/detail/[0-9a-zA-Z/\\._]+)\" target=\"_blank\">[毛坯^<]+", htmlList);
			for(String href:regAll){
				String imgUl = HtmlUtil.analyze(href,"UTF-8", "class=\"s-item\"", "class=\"s-right\"");
				List<String> regAll2 = RegUtil.getRegAll("alt=\"室内图\"src=\"([^\"]+)", imgUl);
				for(String imgUrl:regAll2){
					System.out.println(imgUrl);
					String fileName = imgUrl.substring(imgUrl.lastIndexOf("/")+1);
					
					try {
						byte[] pictureData = HttpUtil.downloadGet(imgUrl);
						ByteInputStream in = new ByteInputStream(pictureData, 0, pictureData.length);
						BufferedImage bimg = Thumbnails.of(in).scale(1).asBufferedImage();
						int width = bimg.getWidth();
						int height = bimg.getHeight()-80;
						
						Thumbnails.of(bimg).sourceRegion(Positions.TOP_LEFT, width, height).scale(1.0).toFile("C:\\Users\\Administrator\\Desktop\\装修\\"+fileName);
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				Thread.sleep(1000);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
