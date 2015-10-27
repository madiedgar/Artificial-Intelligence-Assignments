package test1;

import java.io.IOException;

public class Main {
	public static void main(String[] args) throws IOException{
		String hamString = "/Users/otisskipper/Desktop/A3Emails/hamtraining";
		String spamString = "/Users/otisskipper/Desktop/A3Emails/spamtraining";
		EmailSet testSpam = new EmailSet("/Users/otisskipper/Desktop/A3Emails/spamtesting");
		EmailSet testHam = new EmailSet("/Users/otisskipper/Desktop/A3Emails/hamtesting");
		
		TrainingSets hamSpam =  new TrainingSets(hamString, spamString, 3,1);
		System.out.println("Looking at spam testing emails");
		double hamAcc = hamSpam.testAccuracy(testSpam,false);
		System.out.println("Looking at ham testing emails");
		double spamAcc = hamSpam.testAccuracy(testHam, true);
				
	}
}
