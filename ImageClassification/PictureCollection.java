package a4;

import java.io.File;
import java.io.IOException;
import java.util.Random;



public class PictureCollection {
//collection of many pictures
//given a folder and a certain tag, reads in all images in certain folder
	//type values
	// 0 - undefined
	// 1 - hobo bags
	// 2 - clutch bags
	// 3 - pumps
	// 4 - flats
	int type;
	
	Picture[] allPics;
	
	//2D array to store all tinyImage representation of pictures
	//each row is 3072 integers representing RGB values
	int [][] allTinyImages;
	//know size of tinyImage length from creating pictures
	private static final int TINY_IMAGE_LENGTH = 3072;
	
	//create 4D array to represent the collection of histograms for all images
	//the 1st index represents the image we are looking at
	//the next 3 indices represent the histogram (as buckest of RGB)
	double[][][][] allHistograms;
	//know the bins for rgb values of histogram from creating pictues
	private static final int HISTOGRAM_BINS = 8;
	
	int totalPics;
	
	public PictureCollection(String folderLocation, int type) throws IOException{
		this.type = type;
		
		this.readInPictures(folderLocation);
		this.buildPicArrayRepresentations();
		
		//this.printHistograms();
		//this.printTinyImages();
		
		
	}
	
	public PictureCollection(Picture[] allPics){
		this.allPics = allPics;
		this.totalPics = allPics.length;
		//allPics[0].displayFullPicture();
		this.buildPicArrayRepresentations();
		//System.out.println("Total Pics "+this.totalPics);
		//this.printListOfPicNames();
	}
	
	public PictureCollection join(PictureCollection toJoin){
		Picture[] allNewPics = new Picture[this.totalPics + toJoin.totalPics];
		for(int i = 0; i < toJoin.totalPics; i++){
			allNewPics[i] = toJoin.allPics[i];
		}
		for(int i = 0; i < this.totalPics; i++){
			allNewPics[toJoin.totalPics + i] = this.allPics[i];
		}
		PictureCollection toReturn = new PictureCollection(allNewPics);
		return toReturn;
	}
	
	/*
	public PictureCollection getTrainingSet(){
		return super PictureCollection(trainingSet);
	}*/
	



	public int[] getTypes(){
		//used for libSVM package
		//is array of types that match the images
		int[] toReturn = new int[totalPics];
		for(int i = 0; i < totalPics; i++){
			toReturn[i] = type;
		}
		return toReturn;
	}
	
	public int[][] getTinyImages(){
		return allTinyImages;
	}
	
	public double[][][][] getHistograms(){
		return allHistograms;
	}
	
	/*private void separateTrainingAndTesting(){
		//divides pictures in allPics into the training and testing arrays
		
		//need to randomize and shit
		int trainingSetSize = (int) (totalPics*0.7);
		
		int testingSetSize = totalPics - trainingSetSize;
		
		
		trainingSet = new Picture[trainingSetSize];
		testingSet = new Picture[testingSetSize];
		int curPosInTraining = 0;
		int curPosInTesting = 0;
		for(int i = 0; i < totalPics; i++){
			Picture curPic = allPics[i];
			Random rn = new Random();
			int answer = rn.nextInt(10);
			
			if(curPosInTraining == (trainingSetSize)){
				//training array is full
				//fill the rest of testing with rest of pics
				testingSet[curPosInTesting] = curPic;
				curPosInTesting++;
			}
			else if (curPosInTesting == (testingSetSize)){
				//testing array is full
				//fill the rest of training with the rest of pics
				trainingSet[curPosInTraining] = curPic;
				curPosInTraining++;
			}
			else if(answer < 7){
				//put in training set
				trainingSet[curPosInTraining] = curPic;
				curPosInTraining++;
			}
			else{
				testingSet[curPosInTesting] = curPic;
				curPosInTesting++;
			}
		}
	}*/
	
	private void readInPictures(String folderLocation) throws IOException{


		//reads in all pictures to a single array collection
		//set up structure to read in all pictures
		File folder = new File(folderLocation);
		File[] listOfFiles = folder.listFiles();
		Picture[] tmpAllPics = new Picture[listOfFiles.length];
		
		totalPics = 0;
		
		for (File file : listOfFiles) {
		    if (file.isFile()&&file.getName().endsWith(".jpg")){
		    	//System.out.println(file.getName());
		    	
		    	Picture curPic = new Picture(file.getPath());
		    	curPic.setType(type);
		    	//curPic.displayFullPicture();
		    	tmpAllPics[totalPics] = curPic;
		    	totalPics++;
		    }
		    
		    //resize allPics -- DSSTORE makes the array too large
		    //any random file not jpg will make it too large
		    allPics = new Picture[totalPics];
		    for(int i = 0; i < totalPics; i++){
		    	allPics[i] = tmpAllPics[i];
		    }
		}
	}
	
	private void buildPicArrayRepresentations(){


		//builds the allTinyImages and allHistograms representations
		allTinyImages = new int[totalPics][TINY_IMAGE_LENGTH];
		allHistograms = new double[totalPics][HISTOGRAM_BINS][HISTOGRAM_BINS][HISTOGRAM_BINS];
		for(int i = 0; i < totalPics; i++){
			Picture curPic = allPics[i];
			//build the allTinyImages array
			
			int[] tinyImage = curPic.getTinyImage();
			for(int j = 0; j < TINY_IMAGE_LENGTH; j++){
				allTinyImages[i][j] = tinyImage[j];
			}
			
			//build the allHistograms representations
			//4D Array holding all histograms
			double[][][] currHistogram = curPic.getHistogram();
			
			for(int j = 0; j < HISTOGRAM_BINS; j++){
				for(int k = 0; k < HISTOGRAM_BINS; k++){
					for(int l = 0; l < HISTOGRAM_BINS; l++){
						allHistograms[i][j][k][l] = currHistogram[j][k][l];
					}
				}
			}
		}
	}

	public void printTinyImages(){
		System.out.println("Tiny Image Representation");
		for(int i = 0; i < allTinyImages.length; i++){
			for(int j = 0; j < allTinyImages[0].length; j++){
				System.out.print(allTinyImages[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	public void printHistograms(){


		System.out.println("Histogram Representation");
		for(int i = 0; i < allHistograms.length; i++){
			System.out.print(allPics[i].getName() + " ");
			for(int j = 0; j < allHistograms[0].length; j++){
				for(int k = 0; k < allHistograms[0][0].length; k++){
					for(int l = 0; l < allHistograms[0][0][0].length; l++){
						if(allHistograms[i][j][k][l] > 0){
							int currBin = j*64+k*8+l;
							System.out.print("Bin: " + currBin + " ");
							System.out.print(allHistograms[i][j][k][l] + " ");
						}
					}
				}
			}
			System.out.println();
		}
	}
	public void printListOfPicNames(){
		System.out.println("All Pics In Collection");
		for(int i = 0; i < allHistograms.length; i++){
			System.out.println(allPics[i].getName() + " ");
		}
		
	}
}
