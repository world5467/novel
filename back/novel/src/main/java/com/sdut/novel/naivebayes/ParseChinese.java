package com.sdut.novel.naivebayes;

import java.util.ArrayList;
import java.util.List;

import org.apdplat.word.WordSegmenter;
import org.apdplat.word.segmentation.Word;
import org.springframework.stereotype.Service;

@Service
public class ParseChinese {
	
	/**
	 * 通过word分词将传递过来的文本进行分词，去除符号和常见停用词
	 * @param text
	 * @return
	 */
	public List<String> parseWord(String text){
		if(text==null) {
			return null;
		}
		List<Word> words=WordSegmenter.seg(text);
		List<String> resultWords=new ArrayList<String>();
		for(Word word:words) {
			String wordtext=word.getText().replaceAll("[^a-zA-Z\\s\u4e00-\u9fa5]", " ");//去除非汉字或单词
			if(wordtext.contains(" ")) {
				continue;
			}else {
				resultWords.add(wordtext);
			}
		}
		return resultWords;
	}
	
	public static void main(String[] args) {
		String text="";
		ParseChinese p=new ParseChinese();
		for(int i=0;i<100;i++) {
			System.out.println(i+" "+p.parseWord(text));
		}
	}
}
