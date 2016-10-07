package a4;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import libsvm.*;

public class Main {
	
	public static void main(String[] args) throws IOException{
		
		//runs off of assumption that we have 8 folders for training and testing each set of images
		
		//set up clutch sets
		System.out.println("making clutch pictColl");
		String clutchTrainLocation = "/Users/otisskipper/Desktop/COMP560A4Images/Clutch/Training/tmpTraining";
		PictureCollection clutchTrain = new PictureCollection(clutchTrainLocation, 2);
		String clutchTestLocation = "/Users/otisskipper/Desktop/COMP560A4Images/Clutch/Training/tmpTesting";
		PictureCollection clutchTest = new PictureCollection(clutchTestLocation, 2);
		
		
		//set up flat sets
		System.out.println("making flats pictColl");
		String flatsTrainLocation = "/Users/otisskipper/Desktop/COMP560A4Images/Flats/Training/tmpTraining";
		PictureCollection flatsTrain = new PictureCollection(flatsTrainLocation, 4);
		String flatsTestLocation = "/Users/otisskipper/Desktop/COMP560A4Images/Flats/Training/tmpTesting";
		PictureCollection flatsTest = new PictureCollection(flatsTestLocation, 4);
		
		//setup hobo sets
		System.out.println("making hobo pictColl");
		String hoboTrainLocation = "/Users/otisskipper/Desktop/COMP560A4Images/Hobo/Training/tmpTraining";
		PictureCollection hoboTrain = new PictureCollection(hoboTrainLocation, 1);
		String hoboTestLocation = "/Users/otisskipper/Desktop/COMP560A4Images/Hobo/Training/tmpTesting";
		PictureCollection hoboTest = new PictureCollection(hoboTestLocation, 1);
		
		//setup tmp pumps sets
		System.out.println("making pumps pictColl");
		String pumpsTrainLocation = "/Users/otisskipper/Desktop/COMP560A4Images/Pumps/Training/tmpTraining";
		PictureCollection pumpsTrain = new PictureCollection(pumpsTrainLocation, 3);
		String pumpsTestLocation = "/Users/otisskipper/Desktop/COMP560A4Images/Pumps/Training/tmpTesting";
		PictureCollection pumpsTest = new PictureCollection(pumpsTestLocation, 3);
		
		//combine all training pics into 1 PictureCollection
		//We couldn't do this initially because we had to read them in with different types
		//this way we can now distinguish between clutch, flats, etc...
		PictureCollection allTrainingPics = clutchTrain.join(hoboTrain).join(flatsTrain).join(pumpsTrain);
		clutchTrain = flatsTrain = hoboTrain = pumpsTrain = null;
		
		//do the same for all the testing images
//		PictureCollection allTest = clutchTest.join(flatsTest).join(hoboTest).join(pumpsTest);
		
		//build 4 SVMs based on each type of images and their types
		double C = 0.0001;
		System.out.println(C);
		double g = .5;
		
		boolean useTinyImage = false;
		boolean isLinear = true;
		
		System.out.println("making hobo SVM");
		ImageSVM HoboSVM = new ImageSVM(allTrainingPics, 1, C, g, useTinyImage, isLinear);
		double hoboVal = HoboSVM.testPics(hoboTest.allPics);
		HoboSVM = null;
		
		System.out.println("making clutch SVM");
		ImageSVM ClutchSVM = new ImageSVM(allTrainingPics, 2, C, g, useTinyImage, isLinear);
		double clutchVal = ClutchSVM.testPics(clutchTest.allPics);
		ClutchSVM = null;
		
		System.out.println("making pumps SVM");
		ImageSVM PumpsSVM = new ImageSVM(allTrainingPics, 3, C, g, useTinyImage, isLinear);
		double pumpsVal = PumpsSVM.testPics(pumpsTest.allPics);
		PumpsSVM = null;
		
		System.out.println("making flats SVM");
		ImageSVM FlatsSVM = new ImageSVM(allTrainingPics, 4, C, g, useTinyImage, isLinear);
		double flatsVal = FlatsSVM.testPics(flatsTest.allPics);
		FlatsSVM = null;
		
		System.out.println("Clutch SVM Accuracy: " + clutchVal);
		System.out.println("Hobo SVM Accuracy: " + hoboVal);
		System.out.println("Pumps SVM Accuracy: " + pumpsVal);
		System.out.println("Flats SVM Accuracy: " + flatsVal);
		System.out.println(C);
	}
	
}
