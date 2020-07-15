package com.sdut.novel.dao;

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

public class SelectAllBook {

	public String selectAllBook(Map<?,?> map){
		
		SQL sql=new SQL();
		sql.SELECT("*").FROM("books");
		if(!map.get("type").equals("全部")) {
			sql.WHERE(" type= '"+map.get("type")+"'");
		}
		if(!map.get("amount").equals("全部")) {
			if(map.get("amount").equals("50万以下")) {
				sql.WHERE(" amount<500000");
			}
			if(map.get("amount").equals("50-100万字")) {
				sql.WHERE(" amount>=500000 and amount<1000000");
			}
			if(map.get("amount").equals("100-200万字")) {
				sql.WHERE(" amount>=1000000 and amount<2000000");
			}
			if(map.get("amount").equals("200万字以上")) {
				sql.WHERE(" amount>=2000000");
			}
		}
		if(!map.get("time").equals("全部")) {
			if(map.get("time").equals("七日内")) {
				sql.WHERE(" DATE_SUB(CURDATE(), INTERVAL 7 DAY) <= renewalTime");
			}
			if(map.get("time").equals("一月内")) {
				sql.WHERE(" DATE_SUB(CURDATE(), INTERVAL 30 DAY) <= renewalTime");
			}
			if(map.get("time").equals("六月内")) {
				sql.WHERE(" DATE_SUB(CURDATE(), INTERVAL 180 DAY) <= renewalTime");
			}
		}
		if(!map.get("state").equals("全部")) {
			if(map.get("state").equals("已完本")) {
				sql.WHERE(" state='已完本'");
			}
			if(map.get("state").equals("连载中")) {
				sql.WHERE(" state='连载中'");
			}
		}
		return sql.toString();
	}
}
