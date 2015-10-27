package test1;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Mainish {
	public static void main(String args[]) throws IOException{
		
		EmailSet spamTrain = new EmailSet("/Users/otisskipper/Desktop/A3Emails/spamtraining");
		EmailSet hamTrain = new EmailSet("/Users/otisskipper/Desktop/A3Emails/hamtraining");
		
	
		int totalHam;
		int totalSpam;
		File spamFolder = new File("/Users/otisskipper/Desktop/A3Emails/spamtesting");
		File[] listOfSpam = spamFolder.listFiles();
		File hamFolder = new File("/Users/otisskipper/Desktop/A3Emails/hamtesting");
		File[] listOfHam = hamFolder.listFiles();
		
		
		for(int m = 3; m < 4; m++){
			for(int k = 3; k<4; k++){
				System.out.println();
				System.out.println("m: " + m + " -- k: " + k);

				HashMap<String, Integer> hamHash = hamTrain.wordFrequency();
				HashMap<String, Integer> spamHash = spamTrain.wordFrequency();
				
				Lexicon hamLex = new Lexicon(hamHash,k,m);
				Lexicon spamLex = new Lexicon(spamHash,k,m);
				
				//System.out.println("testing on spam");
				totalSpam = 0;
				totalHam = 0;
				for (File file : listOfSpam) {
				    if (file.isFile()&&file.getName().endsWith(".txt")) {
				    	Email email = new Email(file.getPath());
				    	EmailTester Etester = new EmailTester(hamLex, spamLex, email,hamTrain.totalEmails(),spamTrain.totalEmails());
						if(Etester.isHam()){
							//System.out.println("Email was ham");
							totalHam++;
						}
						else{
							totalSpam++;
							//System.out.println("Email was spam");
						}
				    }
				}
				System.out.println("Spam Accuracy: " + (double)totalSpam/(totalHam + totalSpam) + "%");
				
				//System.out.println("testing on ham");
				totalSpam = 0;
				totalHam = 0;
				for (File file : listOfHam) {
				    if (file.isFile()&&file.getName().endsWith(".txt")) {
				    	Email email = new Email(file.getPath());
				    	EmailTester Etester = new EmailTester(hamLex, spamLex, email,hamTrain.totalEmails(),spamTrain.totalEmails());
						if(Etester.isHam()){
							//System.out.println("Email was ham");
							totalHam++;
						}
						else{
							totalSpam++;
							//System.out.println("Email was spam");
						}
				    }
				}
				System.out.println("Ham Accuracy: " + (double) totalHam/(totalHam + totalSpam) + "%");
			}
		}
	}
}
