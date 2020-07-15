package com.sdut.novel.getNovel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sdut.novel.bean.*;

import net.sourceforge.pinyin4j.PinyinHelper;
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
public class BookContent implements PageProcessor{

	@Autowired
	BookDao dao;
	static List<String> chapterNameList=new ArrayList<String>();
	static String tableName;
	static Book bookAmount;
	static List<String> oldChapterNameList=new ArrayList<String>();
	
	@Override
	public void process(Page page) {
		Html html=page.getHtml();
		List<Selectable> list=html.css("div#list dl dd a").nodes();
		//章节内容
		if(list.size()==0||list==null) {
			Chapter chapter=new Chapter();
			String content=html.css("div#content").toString();
			
			//将章节内容净化
			content=content.replaceAll("^<div.*>","").replace("</div>", "").replace("&nbsp;", "").replace("<br>", "").replace(" ", "").replace("\n\n", "\n");
			//章节名
			String chapterName=html.css("div.bookname h1","text").toString();
			System.out.println("\n\n"+chapterName);
//			System.out.println(content);
			int k=0;
			for(String cname:oldChapterNameList) {   //判断是否已存在
				if(cname.equals(chapterName)){
					k=1;
					break;
				}
			}
			if(k==0) {
				for(int i=0;i<chapterNameList.size();i++) {
					if(chapterNameList.get(i).equals(chapterName)) {
						chapter.setId(i);
						chapter.setChapterName(chapterName);
						chapter.setChapterContent(content);
						dao.addChapter(tableName, chapter);
						int amount=content.length();
						dao.updateAmount(bookAmount, amount);//小说字数
						break;
					}
				}
			}
			
		}else {
			Book book=new Book();
			chapterNameList=html.css("div#list dl dd a","text").all();
			book.setBookName(html.css("div#info h1","text").toString());
			List<String> bookInfo=html.css("div#info p","text").all();
			book.setWriter(bookInfo.get(0).replace("作    者：",""));
			if(dao.selectBook(book.getBookName(),book.getWriter())==0) {	//判断是否存在该书
				book.setRenewalTime(bookInfo.get(2).replace("最后更新：",""));
				book.setBookDescription(html.css("div#intro","text").toString());
				String state=html.css("#info > p:nth-child(3)","text").toString();
				System.out.println(state);
				if(state.contains("连载中")) {
					book.setState("连载中");
				} else {
					System.out.println(state);
					book.setState("已完本");
				}
				String coverUrl=html.css("div#sidebar div#fmimg img","src").toString();
				System.out.println(coverUrl);
				book.setCover(saveImg(coverUrl));
				System.out.println(book.getBookName()+"\n"+book.getWriter()+"\n"+book.getState()+"\n"+book.getCover()+"\n"+book.getBookDescription()+"\n"+book.getRenewalTime());
//				String hanzi=book.getBookName();
				tableName=UUID.randomUUID().toString().replace("-", "");
				if(tableName.length()>32) {
					tableName=tableName.substring(0, 32);
				}
				try{
					dao.addChapterTable(tableName);
					book.setChapterTable(tableName);
				}catch(Exception e) {
					try{
						tableName=UUID.randomUUID().toString().replace("-", "");
						if(tableName.length()>32) {
						tableName=tableName.substring(0, 32);
						}
						dao.addChapterTable(tableName);
						book.setChapterTable(tableName);
					}catch(Exception ex) {
						ex.printStackTrace();
					}
				}
				dao.addBook(book);
				bookAmount=book;
				//加入队列
				for(Selectable selectable:list) {
					String chapterUrl=selectable.links().toString();
					page.addTargetRequest(chapterUrl);
				}
			}else {
				System.out.println("《"+book.getBookName()+"》已存在！！！");
				Book oldBook=dao.selectBookName(book.getBookName(),book.getWriter());
				//取出数据库中的章节
				oldChapterNameList=dao.selectBookCatalog(oldBook.getChapterTable());
				tableName=oldBook.getChapterTable();
				bookAmount=oldBook;
//				chapterNameList.removeAll(oldChapterNameList);
				for(Selectable selectable:list.subList(oldChapterNameList.size()-1, list.size())) {
					String chapterUrl=selectable.links().toString();
					page.addTargetRequest(chapterUrl);
				}
			}
		}
	}
	public String pinyin(String hanzi) {
		String pinyin="";
		String h[];
		for(int i=0;i<hanzi.length();i++) {
			if(String.valueOf(hanzi.charAt(i)).matches("[\u4e00-\u9fa5]")){
				h=PinyinHelper.toHanyuPinyinStringArray(hanzi.charAt(i));
				pinyin=pinyin+h[0].substring(0, h[0].length()-1);
			}else if(hanzi.charAt(i)=='（'){
				break;
			}
		}
		return pinyin;
	}
	public String saveImg(String coverUrl) {
		String imgName = null;
		try {
			URL imgUrl=new URL(coverUrl);
			URLConnection con = imgUrl.openConnection();
			InputStream inStream = con.getInputStream();
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			byte[] b = new byte[1024];
			int len = 0;
			while((len = inStream.read(b)) != -1){
				outStream.write(b,0,len);
			}
			String imgSuffix=coverUrl.substring(coverUrl.lastIndexOf('.'));
			imgName=UUID.randomUUID().toString()+imgSuffix;
			File file = new File("D:\\Project\\WebProject\\book\\cover\\"+imgName);	//图片下载地址
			FileOutputStream op = new FileOutputStream(file);
			op.write(outStream.toByteArray());
			inStream.close();
			outStream.close();
			op.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return imgName;
	}
	Site site=Site.me()
			.setCharset("GBK")
			.setRetryTimes(3)
			.setCycleRetryTimes(3)
			.setSleepTime(1000)
			.setTimeOut(10000);
	@Override
	public Site getSite() {
		// TODO Auto-generated method stub
		return site;
	}
	@Autowired
	BookContent bookContent;
	public void start(String url) {
		HttpClientDownloader httpClientDownloader=new HttpClientDownloader();
		httpClientDownloader.setProxyProvider(SimpleProxyProvider.from(new Proxy("47.100.120.52",3128)));
		Spider.create(bookContent)
				.addUrl(url)
				.thread(20)
//				.setDownloader(httpClientDownloader)
				.run();
	}
}
