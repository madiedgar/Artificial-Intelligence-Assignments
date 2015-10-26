package test1;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.lang.Object;


public class EmailSet {
	
	private int totalEmails;
	private String[] allStrings;
	public EmailSet(String folderLocation) throws IOException {
		
		this.allStrings = null;
		
		this.readInFolder(folderLocation);
		
	}

	public void readInFolder(String folderLocation) throws IOException{
		File folder = new File(folderLocation);
		File[] listOfFiles = folder.listFiles();
		totalEmails = 0;
		for (File file : listOfFiles) {
		    if (file.isFile()&&file.getName().endsWith(".txt")) {
		      //  System.out.println(file.getName());
		        totalEmails++;
		        //have collection of each line of the email
		        //break the line into individual strings
		        
		        List<String> lines = Files.readAllLines(file.toPath(), Charset.defaultCharset());
		        //sometimes we get an error here -- need to investigate why
		        //only appears on spamtraining and spamtesting, about 5 emails
		        String[] arr = lines.toArray(new String[lines.size()]);
		       
		        for(int i = 0; i < arr.length; i++){
		        	String[] indWords = arr[i].split(" ");
		        	if(allStrings == null){
		        		allStrings = indWords;
		        	}
		        	else{
		        		allStrings = concat(allStrings, indWords);
		        	}
		        }
		    }
		    
		}
	}
	public HashMap<String, Integer> wordFrequency(){
		//return hashMap that says how many times each string occurs
		HashMap<String, Integer> wordFrequency = new HashMap<String, Integer>();
		for(int i = 0; i < allStrings.length; i++){
			String currWord = allStrings[i];
			if(wordFrequency.get(currWord) == null){
				wordFrequency.put(currWord, 1);
			}
			else{
				wordFrequency.put(currWord, wordFrequency.get(currWord)+1);
			}
		}
		return wordFrequency;
	}
	public String[] concat(String[] s1, String[] s2) {
	      String[] erg = new String[s1.length + s2.length];

	      System.arraycopy(s1, 0, erg, 0, s1.length);
	      System.arraycopy(s2, 0, erg, s1.length, s2.length);

	      return erg;
	}
	
	public int totalEmails(){
		return totalEmails;
	}
	public void displayInfo(){
		System.out.println("total emails: " + totalEmails);
		System.out.println("total words: " + allStrings.length);
		
		//for(int i = 0; i < allStrings.length; i++) System.out.println(allStrings[i]);
	
	}
}
