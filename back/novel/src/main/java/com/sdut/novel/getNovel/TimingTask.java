package com.sdut.novel.getNovel;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.sdut.novel.bean.Book;
import com.sdut.novel.dao.NovelDao;
import com.sdut.novel.naivebayes.ClassifyText;

@Component
@EnableAsync        // 开启多线程
public class TimingTask {

	@Autowired
	BookDao dao;
	@Autowired
	SelectBook selectBook;
	@Autowired
	ClassifyText classifyText;
	@Autowired
	NovelDao novelDao;
	
	//更新小说
//	@Scheduled(fixedDelay = 1000*60*60*12)
	@Async
	@Scheduled(cron="0 0 0 * * *")
	void checkUpdate() {
		List<String> allList=dao.selectAllBook();
		for(String bookName:allList) {
			System.out.println("书名: "+bookName);
			selectBook.selectbook(bookName);
			
		}
	}
	
	//删除IP表内容
	@Async
	@Scheduled(cron="0 0 0 * * *")
	void deleteIpTable() {
		novelDao.deleteIP();
	}
	
	//该小说分类
	@Async
//	@Scheduled(fixedDelay = 1000*60*60*12)
	@Scheduled(cron="0 0 2 * * *")
	void getClassification() {
		List<Book> noClassBook=dao.selectNoClassBook();
		for(Book book:noClassBook) {
			try {
				List<String> chapterList = dao.getChapter(book.getChapterTable());
				StringBuilder bookContent = new StringBuilder();
				for (String cha : chapterList) {
					bookContent.append(cha);
				}
//				System.out.println(book.getBookName() + "开始分类！！" );
				String classification = classifyText.classify(bookContent.toString());
//				System.out.println(book.getBookName() + "   " + classification);
				book.setType(classification);
				dao.updateType(book);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
