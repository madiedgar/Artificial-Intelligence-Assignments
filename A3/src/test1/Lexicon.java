package test1;

import java.util.HashMap;

public class Lexicon {
	
	private int k; //used to determine how many times a word must appear to be in lexicon
	private int m; //laplace smoothing
	private int totalInitWords;
	private HashMap<String, Integer> map;
	private String[] allStrings; // all strings in lexicon

	public Lexicon(HashMap<String, Integer> inputMap, int k, int m){
		//integer value in hashmap is how many times String occured in email
		this.k = k;
		this.m = m;
		//read in all the strings in inputMap as object array
		Object[] tmp = inputMap.keySet().toArray();
		
		//cast the objects to strings
		this.allStrings = new String[tmp.length];
		totalInitWords = 0;
		for(int i = 0; i <tmp.length; i++){
			allStrings[i] = (String) tmp[i];
			totalInitWords += inputMap.get(allStrings[i]);
		}
		this.map = inputMap;
		//take any string with value less than k and remove it from the map
		this.removeObscureWords();
	}
	
	public int totalWordsInLexicon(){
		int totalWords = 0;
		for(int i = 0; i < allStrings.length; i++){
			totalWords += map.get(allStrings[i]);
		}
		return totalWords;
	}
	
	public double probOf(String word){
		if(map.get(word)!=null){
			return (double)(map.get(word)+m)/(totalInitWords + m*this.totalWordsInLexicon());
		}
		else{
			return (double) m/(totalInitWords + m*this.totalWordsInLexicon());
		}
	}
	
	private void removeObscureWords(){
		//remove words with values less than specified k from map
		int totalStrings = map.size();
		for(int i = 0; i < totalStrings; i++){
			if(map.get(allStrings[i]) < k){
				map.remove(allStrings[i]);
			}
		}
		//update allStrings
		Object[] tmp = map.keySet().toArray();
		//cast the objects to strings
		this.allStrings = new String[tmp.length];
		for(int i = 0; i <tmp.length; i++){
			allStrings[i] = (String) tmp[i];
		}
	}
	
	public void printLexicon(){
		System.out.println("total words in lexicon: " + this.totalWordsInLexicon());
		System.out.println("total init words: " + totalInitWords);
		for(int i = 0; i < allStrings.length; i++){
			System.out.print(allStrings[i]);
			System.out.print(" occurs " + map.get(allStrings[i]) + " times");
			System.out.println("with a probability of " + probOf(allStrings[i]));
			
			}
		}
}
