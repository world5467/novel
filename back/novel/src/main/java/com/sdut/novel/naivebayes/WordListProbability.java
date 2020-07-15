package com.sdut.novel.naivebayes;

import java.io.Serializable;
import java.util.List;

public class WordListProbability implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<String> wordList;
	private List<ClassProbability> classProbability;
	public List<String> getWordList() {
		return wordList;
	}
	public void setWordList(List<String> wordList) {
		this.wordList = wordList;
	}
	public List<ClassProbability> getClassProbability() {
		return classProbability;
	}
	public void setClassProbability(List<ClassProbability> classProbability) {
		this.classProbability = classProbability;
	}
	@Override
	public String toString() {
		return "WordListProbability [wordList=" + this.wordList + ", classProbability=" + this.classProbability + "]";
	}
}
