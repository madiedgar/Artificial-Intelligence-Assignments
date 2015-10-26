package test1;

import java.util.HashMap;

public class EmailTester {
	private Lexicon spamLexicon;
	private Lexicon hamLexicon;
	private String[] emailWords;
	private boolean isHam;
	private int totalHam; // total ham emails
	private int totalSpam; // total spam emails
	
	public EmailTester(Lexicon ham, Lexicon spam, Email email, int totalHam, int totalSpam){
		this.totalHam = totalHam;
		this.totalSpam = totalSpam;
		this.spamLexicon = spam;
		this.hamLexicon = ham;
		this.emailWords = email.getWords();
		
		isHamOrSpam();
		//TODO  include probability of email being spam vs ham
	}
	
	private void isHamOrSpam(){
		double probHam = Math.log((double) totalHam/(totalHam+totalSpam));
		double probSpam = Math.log((double) totalSpam/(totalHam+totalSpam));
		for(int i = 0; i < emailWords.length; i++){
			probHam += Math.log(hamLexicon.probOf(emailWords[i]));
			probSpam += Math.log(spamLexicon.probOf(emailWords[i]));
		}
		//System.out.println(" probHam: " + probHam);
		//System.out.println("probSpam: " + probSpam);
		if(probSpam < probHam){
			this.isHam = true;
		}
		else{
			this.isHam = false;
		}
	}
	
	public boolean isHam(){
		if(isHam){
			return true;
		}
		return false;
	}
}
