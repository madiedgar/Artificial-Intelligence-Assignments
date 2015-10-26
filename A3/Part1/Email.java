package test1;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;

public class Email {
	protected String[] allStrings;
	public Email(String fileLocation) throws IOException{
		allStrings = null;
		this.readInEmail(fileLocation);
	}
	
	public void readInEmail(String fileLocation) throws IOException{
		File file = new File(fileLocation);
		//System.out.println(file.getName());
		if(file.isFile()&&file.getName().endsWith(".txt")){
			List<String> lines = Files.readAllLines(file.toPath(), Charset.defaultCharset());
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
	public String[] getWords(){
		return allStrings;
	}
	public String[] concat(String[] s1, String[] s2) {
	      String[] erg = new String[s1.length + s2.length];

	      System.arraycopy(s1, 0, erg, 0, s1.length);
	      System.arraycopy(s2, 0, erg, s1.length, s2.length);

	      return erg;
	}
}
