package com.sdut.novel.naivebayes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
@Service
public class TrainingDataManager {

	private String[] trainingFileClassifications;//训练语料分类集合
	private File trainingFileDirectory;//训练语料存放目录
	private static String trainingFilePath="F:\\学习\\毕业设计\\psbys7";//训练语料文件地址
	
	public void getTrainingData() {
		trainingFileDirectory=new File(trainingFilePath);//获取训练语料路径
		if (!trainingFileDirectory.isDirectory()) 
        {
            throw new IllegalArgumentException("训练语料库搜索失败！ [" +trainingFilePath + "]");
        }
        trainingFileClassifications = trainingFileDirectory.list();//将路径下文件名变成集合存放在训练语料分类集合中
	}

	/**
	 * 返回分类，即路径下各文件夹名称
	 * @return 训练文本中的类别
	 */
	public String[] getTrainingFileClassifications() {
		return trainingFileClassifications;
	}

	/**
	 * 根据给的分类名返回该类别下所有文件的地址
	 * @param classfication 分类名
	 * @return 返回该类别下所有文件的地址
	 */
	public String[] getFilePath(String classfication) {
		File classDirectory=new File(trainingFileDirectory.getPath()+File.separator+classfication);
		String[] fileName=classDirectory.list();
		for(int i=0;i<fileName.length;i++) {
			fileName[i]=trainingFileDirectory.getPath()+File.separator+classfication+File.separator+fileName[i];
		}
		return fileName;
	}
	
	/**
	 * 获取文件路径下的文件内容，并返回
	 * @param fileName 文件路径
	 * @return 文件内容（String）
	 */
	public String getText(String fileName) {
		try {
			InputStreamReader iRead=new InputStreamReader(new FileInputStream(fileName));
			BufferedReader bRead=new BufferedReader(iRead);
			StringBuilder sBuilder=new StringBuilder();
			String lineText=null;
			while((lineText=bRead.readLine())!=null) {
				sBuilder.append(lineText);
			}
			iRead.close();
			bRead.close();
			return sBuilder.toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获得训练预料中的所有文件个数
	 * @return 所有文件个数
	 */
	public int getAllFileCount() {
		int count=0;
		for(int i=0;i<trainingFileClassifications.length;i++) {
			count += getClassificationFileCount(trainingFileClassifications[i]);
		}
		return count;
	}
	
	/**
	 * 获得训练语料某一分类下的文件个数
	 * @param classification 分类
	 * @return 该分类文件个数
	 */
	public int getClassificationFileCount(String classification) {
		classification=trainingFileDirectory.getPath()+File.separator+classification;
		File file=new File(classification);
		return file.list().length;
	}
	
	/**
	 * 将所有的训练文本进行分词处理，并存在WordInfo中，记录类别
	 * @return 分词后列表
	 */
	public List<WordInfo> parseWord() {
		ParseChinese pc=new ParseChinese();
		List<WordInfo> wordInfo=new ArrayList<WordInfo>();
		for(int i=0;i<trainingFileClassifications.length;i++) {
			String[] filePath=getFilePath(trainingFileClassifications[i]);
			for(String path:filePath) {
				WordInfo a=new WordInfo();
				a.setFlog(trainingFileClassifications[i]);
//				System.out.println(trainingFileClassifications[i]+"   "+path);
				a.setWordList(pc.parseWord(getText(path)));
				wordInfo.add(a);
				a=null;
			}
		}
		return wordInfo;
	}
	
	/**
	 * 获取某个词出现在某文本中出现的次数
	 * @param word 该词
	 * @param wordList 文本分词
	 * @return 出现的次数
	 */
	public int getwordNumber(String word,WordInfo wordInfo) {
		return Collections.frequency(wordInfo.getWordList(), word);
	}
	
	/**
	 * 获得分词后集合所有单词集
	 * @param wordInfo
	 * @return 单词集
	 */
	public List<String> createWordList(List<WordInfo> wordInfo){
		Set<String> setWords=new HashSet<String>();
		for(WordInfo words:wordInfo) {
			for(String word:words.getWordList()) {
				setWords.add(word);
			}
		}
		List<String> wordList=new ArrayList<String>(setWords);
		quickSort(wordList, 0, wordList.size()-1);
		return wordList;
	}
	
	/**
	 * 将训练文本转化为向量，即各单词出现的次数
	 * @param wordList 单词集
	 * @param wordInfo 文本
	 * @return 向量
	 */
	public List<Integer> trainCreateVector(List<String> wordList,WordInfo wordInfo) {
		List<Integer> wordVector=new ArrayList<Integer>();
		List<String> wordInfoList=wordInfo.getWordList();
		quickSort(wordInfoList, 0, wordInfoList.size()-1);
		int number=0;
		for(String word:wordList) {
			int n=0;
			while(number<wordInfoList.size()&&wordInfoList.get(number).equals(word)) {
				number++;
				n++;
			}
			wordVector.add(n);
		}
		return wordVector;
	}
	
	/**
	 * 将文本转化为向量，即各单词出现的次数
	 * @param wordList 单词集
	 * @param wordInfo 文本
	 * @return 向量
	 */
	public List<Integer> createVector(List<String> wordList,WordInfo wordInfo) {
		List<Integer> wordVector=new ArrayList<Integer>();
//		quickSort(wordInfo.getWordList(), 0, wordInfo.getWordList().size());
		for(int i=0;i<wordList.size();i++) {
			wordVector.add(0);
		}
		for(String word:wordInfo.getWordList()) {
			for(int i=0;i<wordList.size();i++) {
				if(wordList.get(i).equals(word)) {
					wordVector.set(i, wordVector.get(i)+1);
					break;
				}
			}
		}
		return wordVector;
	}
	
	/**
	 * 用次数除以总的单词数得到概率再取对数，可以进行相加
	 * @param wordVector
	 * @param wordCount
	 * @return 概率的对数
	 */
	public List<Double> vectorProbability(List<Integer> wordVector,int wordCount){
		List<Double> vec=new ArrayList<Double>();
		for(int i:wordVector) {
			vec.add(Math.log((double)(i+1)*1.0/wordCount));//相除后取对数
		}
		return vec;
	}
	
	/**
	 * 将两个向量相加
	 * @param list1
	 * @param list2
	 * @return 相加后的向量
	 */
	public List<Integer> addVector(List<Integer> list1,List<Integer> list2){
		if(list1.size()==0)
			return list2;
		if(list2.size()==0)
			return list1;
		List<Integer> list=new ArrayList<Integer>();
		for(int i=0;i<list1.size();i++) {
			list.add(list1.get(i)+list2.get(i));
		}
		return list;
	}
	
	/**
	 * 获取该向量中的总次数
	 * @param list
	 * @return 总次数
	 */
	public int allNumber(List<Integer> list) {
		int num=0;
		for(int n:list) {
			num += n;
		}
		return num;
	}
	
	/**
	 * 将向量中的值乘以对应概率的对数
	 * @param vectorPro
	 * @param vector
	 * @return 对数的和
	 */
	public double probabilityByClass(List<Double> vectorPro,List<Integer> vector){
		double probability=0;
		for(int i=0;i<vector.size();i++) {
			probability += (vectorPro.get(i)*vector.get(i));
		}
		return probability;
	}
	
	/**
	 * 快速排序
	 */
	static void quickSort(List<String> list,int low,int high) {
		LinkedList<Integer> s=new LinkedList<Integer>();
		
		if(low<high) {
			s.push(high);
			s.push(low);
			while(!s.isEmpty()) {
				int l=s.pop();
				int r=s.pop();
				int index=partition(list, l, r);
				if(l<index-1) {
					s.push(index-1);
					s.push(l);
				}
				if(r>index+1) {
					s.push(r);
					s.push(index+1);
				}
			}
		}
	}
	public static int partition(List<String> list,int low,int high){
		int i=low,j=high;
		String temp=list.get(low);
		while(i<j) {
			while(temp.compareTo(list.get(j))<=0&&i<j) {
				j--;
			}
			while(temp.compareTo(list.get(i))>=0&&i<j) {
				i++;
			}
			if(i<j) {
				String t;
				t=list.get(i);
				list.set(i, list.get(j));
				list.set(j, t);
			}
		}
		list.set(low, list.get(i));
		list.set(i, temp);
		return i;
	}
	public WordListProbability trainingDate() {

		getTrainingData();
		List<WordInfo> data=parseWord();//获取分词后的list
		System.out.println("分词完毕");
		List<String> trainingDataWordList=createWordList(data);//将全部文本转化为单词集
		System.out.println("单词集完毕"+trainingDataWordList);
		List<ClassProbability> classProbability=new ArrayList<ClassProbability>();
		int trainingTextCount=getAllFileCount();//全部文本数量
		
		for(String c:trainingFileClassifications) {
			System.out.println(c+"开始");
			ClassProbability classProba=new ClassProbability();
			classProba.setClassification(c);
			classProba.setClassProbability(1.0*getClassificationFileCount(c)/trainingTextCount);//训练集中该分类概率
			int denom=2;//该分类下总次数
			List<Integer> wordCountList=new ArrayList<Integer>();
			for(WordInfo wordInfo:data) {
//				System.out.println(wordInfo.getFlog()+wordInfo.getWordList().size());
				if(wordInfo.getFlog().equals(c)) {
					List<Integer> list=trainCreateVector(trainingDataWordList, wordInfo);
					wordCountList=addVector(wordCountList, list);//将该分类下所有向量加一起
					denom += allNumber(list);
//					list=null;
				}
			}
			classProba.setWordProbabi(vectorProbability(wordCountList, denom));//每个词概率的对数，可以进行加操作
			classProbability.add(classProba);
			System.out.println(classProba.getClassification()+"   "+classProba.getClassProbability()+"    "+classProba.getWordProbabi().size());
//			classProba=null;
		}
		WordListProbability wp = new WordListProbability();
		wp.setWordList(trainingDataWordList);
		wp.setClassProbability(classProbability);
//		System.out.println(classProbability.get(0)+"\n"+classProbability.get(1));
		return wp;
	}
	
	
	public void saveFile() {
		try {
			ObjectOutputStream objectOutputStream=new ObjectOutputStream(new FileOutputStream("classProbability.txt"));
			objectOutputStream.writeObject(trainingDate());
			objectOutputStream.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		TrainingDataManager t = new TrainingDataManager();
		t.saveFile();
	}
	
}