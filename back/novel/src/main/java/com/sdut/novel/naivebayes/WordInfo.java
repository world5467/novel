package com.sdut.novel.naivebayes;

import java.util.List;

public class WordInfo {

	private String flog;
	private List<String> wordList;
	public String getFlog() {
		return flog;
	}
	public void setFlog(String flog) {
		this.flog = flog;
	}
	public List<String> getWordList() {
		return wordList;
	}
	public void setWordList(List<String> wordList) {
		this.wordList = wordList;
	}
	@Override
	public String toString() {
		return "WordInfo [flog=" + flog + ", wordList=" + wordList + "]";
	}
}
