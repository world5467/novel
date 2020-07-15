package com.sdut.novel.getNovel;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

@Service
public class SelectBook implements PageProcessor {

	
	@Autowired
	BookContent b;
	@Autowired
	SelectBook selectBook;
	static String searchName;
	static int k=0;
	public void process(Page page) {
		Html html=page.getHtml();
//		List<String> bookNameList=html.css("div h3 a span","text").links().all();
		List<String> bookUrlList=html.css("div h3 a").links().all();
		System.out.println(bookUrlList);
		//判断搜索结果是否为第一页
		if(k==0) {
			k++;
			List<Selectable> pageList=html.css("div div.search-result-page-main a").nodes();
			if(pageList.size()>1) {
//				System.out.println(pageList.get(pageList.size()-1).links().toString());
				String p[]=pageList.get(pageList.size()-1).links().toString().split("=");
				int pageNumber=Integer.parseInt(p[p.length-1]);
				for(int i=2;i<=pageNumber;i++) {
					String url="https://www.zwda.com/search.php?q="+searchName+"&p="+i;
					page.addTargetRequest(url);
				}
			}
		}
		if(bookUrlList.size()==0||bookUrlList==null) {
			System.out.println("未查询到图书！！");
		}else {
			for(String bookName:bookUrlList) {
				b.start(bookName);
			}
		}
		
	}
	Site site=Site.me()
			.setCharset("utf8")
			.setRetryTimes(3)
			.setCycleRetryTimes(3)
			.setSleepTime(1000)
			.setTimeOut(10000);

	public Site getSite() {
		return site;
	}
	
	public void selectbook(String bookName){
		searchName=bookName;
		HttpClientDownloader httpClientDownloader=new HttpClientDownloader();
		httpClientDownloader.setProxyProvider(SimpleProxyProvider.from(new Proxy("47.100.120.52",3128)));
		Spider.create(selectBook)
				.addUrl("https://www.zwda.com/search.php?q="+bookName)
				.thread(5)
//				.setDownloader(httpClientDownloader)
				.run();
	}

}
