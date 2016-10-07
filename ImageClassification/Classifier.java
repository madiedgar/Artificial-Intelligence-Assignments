package a4;

public class Classifier {
	ImageSVM hobo, clutch, pumps, flats;
	public Classifier(ImageSVM hobo, ImageSVM clutch, ImageSVM pumps, ImageSVM flats){
		this.hobo = hobo;
		this.clutch = clutch;
		this.pumps = pumps;
		this.flats = flats;
	}
	
	public int classify(Picture picture){
		double hoboAcc = hobo.getAccuracy(picture);
		double clutchAcc = clutch.getAccuracy(picture);
		double pumpsAcc = pumps.getAccuracy(picture);
		double flatsAcc = flats.getAccuracy(picture);
		
		int type;
		
		if(hoboAcc> clutchAcc && hoboAcc > pumpsAcc && hoboAcc > flatsAcc){
			type = 1;
		} else if(clutchAcc> hoboAcc && clutchAcc > pumpsAcc && clutchAcc > flatsAcc){
			type = 2;
		} else if(pumpsAcc> clutchAcc && pumpsAcc > hoboAcc && pumpsAcc > flatsAcc){
			type = 3;
		} else{
			type = 4;
		}
		
		return type;
	}
	
	
	
	public int[][] confusionMatrix(Picture[] allPics){
		int[][] matrix = new int[4][4];
		for(int i = 0; i < allPics.length; i++){
			Picture curr = allPics[i];
			int classification = classify(curr);
			if(classification != curr.getType()){
				matrix[curr.getType()-1][classification-1] += 1;
			} else{
				System.out.println(curr.getName());
			}
		}
		return matrix;
	}
}
