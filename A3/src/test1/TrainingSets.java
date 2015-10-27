package test1;

import java.io.IOException;
import java.util.HashMap;

public class TrainingSets {
	
	private EmailSet spamTrain;
	private EmailSet hamTrain;
	private int m;
	private int k;
	
	public TrainingSets(String hamTrainingFolder, String spamTrainingFolder, int k, int m) throws IOException{
		this.k = k;
		this.m = m;
		this.hamTrain = new EmailSet(hamTrainingFolder);
		this.spamTrain = new EmailSet(spamTrainingFolder);
	}
	
	public EmailSet getSpamTrain(){
		return this.spamTrain;
	}
	
	public EmailSet getHamTrain(){
		return this.hamTrain;
	}
	
	public int testAccuracy(Email email, boolean isHam){
		//returns 1 if email is sorted correctly
		//returns 0 if email is not sorted correctly
		HashMap<String, Integer> hamHash = hamTrain.wordFrequency();
		HashMap<String, Integer> spamHash = spamTrain.wordFrequency();
		Lexicon hamLex = new Lexicon(hamHash, k, m);
		Lexicon spamLex = new Lexicon(spamHash, k, m);
		
		EmailTester Etester = new EmailTester(hamLex, spamLex, email,hamTrain.totalEmails(),spamTrain.totalEmails());
		if(Etester.isHam()){
			if(isHam){
				//we were right
				return 1;
			}
			else{
				//we were wrong
				return 0;
			}
		}
		else{
			if(!isHam){
				//we were right
				return 1;
			}
			else{
				return 0;
			}
		}
	}
	
	public double testAccuracy(EmailSet emailSet, boolean isHam){
		int totalRight = 0;
		Email[] allEmails = emailSet.getAllEmails();
		
		for(int i = 0; i < allEmails.length; i++){
			totalRight+=testAccuracy(allEmails[i],isHam);
		}
		double accuracy = (double) totalRight/(allEmails.length);
		System.out.println("Accuracy was " + accuracy + "%");
		return accuracy;
	}
}
