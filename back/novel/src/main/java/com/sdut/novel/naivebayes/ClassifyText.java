package com.sdut.novel.naivebayes;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.List;

import org.springframework.stereotype.Service;
@Service
public class ClassifyText {

	WordListProbability wp=new WordListProbability();
	ParseChinese pc=new ParseChinese();
	public ClassifyText() {
		try {
			ObjectInputStream objectInputStream=new ObjectInputStream(new FileInputStream("classProbability.txt"));
			wp=(WordListProbability) objectInputStream.readObject();
			objectInputStream.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String classify(String text) {
		TrainingDataManager trainingDataManager=new TrainingDataManager();
		WordInfo wordInfo=new WordInfo();
		wordInfo.setWordList(pc.parseWord(text));
		List<Integer> vector=trainingDataManager.createVector(wp.getWordList(), wordInfo);
		double[] probabilitys=new double[wp.getClassProbability().size()];
		for(int i=0;i<wp.getClassProbability().size();i++) {
			probabilitys[i]=trainingDataManager.probabilityByClass(wp.getClassProbability().get(i).getWordProbabi(), vector)+Math.log(wp.getClassProbability().get(i).getClassProbability());
			System.out.println(wp.getClassProbability().get(i).getClassification()+"   "+probabilitys[i]);
		}
		int n=0;
		double maxProba=probabilitys[0];
		for(int i=1;i<probabilitys.length;i++) {
			if(maxProba<probabilitys[i]) {
				n=i;
				maxProba=probabilitys[i];
			}
		}
		return wp.getClassProbability().get(n).getClassification();
	}
	public static void main(String[] args) {
		
		String text=new TrainingDataManager().getText("F:\\学习\\毕业设计\\psbys7\\武侠\\重生之独步江湖.txt");
//		System.out.println(text.length());
		text=text.substring(0, 200000);
		String classifi=new ClassifyText().classify(text);
		System.out.println(classifi);
//		System.out.println(text.substring(10000,20000).length());
	}
}
