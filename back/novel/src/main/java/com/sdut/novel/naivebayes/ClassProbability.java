package com.sdut.novel.naivebayes;

import java.io.Serializable;
import java.util.List;

public class ClassProbability  implements Serializable{

	private static final long serialVersionUID = 1L;
	private String classification;
	private double classProbability;
	private List<Double> wordProbabi;
	public String getClassification() {
		return classification;
	}
	public void setClassification(String classification) {
		this.classification = classification;
	}
	public double getClassProbability() {
		return classProbability;
	}
	public void setClassProbability(double classProbability) {
		this.classProbability = classProbability;
	}
	public List<Double> getWordProbabi() {
		return wordProbabi;
	}
	public void setWordProbabi(List<Double> wordProbabi) {
		this.wordProbabi = wordProbabi;
	}
	@Override
	public String toString() {
		return "ClassProbability [classification=" + classification + ", classProbability=" + classProbability
				+ ", wordProbabi=" + wordProbabi + "]";
	}
}
