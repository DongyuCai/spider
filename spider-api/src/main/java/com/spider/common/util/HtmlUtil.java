package com.spider.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class HtmlUtil {

	
	public static String analyze(String address,String charset,String startFlag,String endFlag) {
		StringBuilder houseListHtmlBuf = new StringBuilder();
		BufferedReader buf = null;
        try{
        	URL url = new URL(address);
            URLConnection conn = url.openConnection();
            conn.connect();
            InputStream in = conn.getInputStream();
            InputStreamReader input = new InputStreamReader(in, charset);
            buf = new BufferedReader(input);
            String nextLine = buf.readLine();
            
            boolean start = false;
            while(nextLine != null){
                if(nextLine.contains(startFlag)){
                	//开始查询结果列表
                	start = true;
                } else if (nextLine.contains(endFlag)){
                	//到达列表结束
                	if(start){
                		break;
                	}
                } else {
                	if(start){
                		houseListHtmlBuf.append(nextLine.trim());
                	}
                }
                nextLine = buf.readLine();
            }
        }catch(Exception e){}finally {
			if(buf != null){
				try {
					buf.close();
				} catch (IOException e) {}
			}
		}
        return houseListHtmlBuf.toString();
	}
}
