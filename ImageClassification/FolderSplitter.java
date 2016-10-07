package a4;

import libsvm.*;

public class ImageSVM {

	private svm_parameter par;
	private svm_problem problem;
	private PictureCollection trainPics;
	private boolean isLinear;
	private svm_model model;
	private double C;
	private double g;
	private boolean tinyImage;
	//determines if we use tinyImage or histogram representation
	
	private int trainType;
	//we will train based on teh type
	// 1 - hobo bags
	// 2 - clutch bags
	// 3 - pumps
	// 4 - flats
	
	public ImageSVM(PictureCollection trainPics, int trainType, double C, double g, boolean tinyImage, boolean isLinear){
		this.trainPics = trainPics;
		this.trainType = trainType;
		this.isLinear = isLinear;
		this.C = C;
		this.g = g;
		this.tinyImage = tinyImage;
		this.initializePar();
		this.initializeProblem();
		this.buildProblem();
		this.model = svm.svm_train(this.problem, this.par);
		
	}
	
	
	public double testPics(Picture[] testPics){


		//returns accuracy of tested pictures
		int totalRight = 0;
		int totalWrong = 0;
		
		for(int i = 0; i < testPics.length; i++){
			double[] doubImg;
			if(tinyImage){
				//determine if we use tiny image or histogram representation
				doubImg = testPics[i].getTinyImageDouble();
			}
			else{
				doubImg = testPics[i].getArrayHistogram();
			}
			
			double evalVal = evaluate(doubImg, model, testPics[i].getType());
			if((testPics[i].getType()==trainType) && (evalVal == 1)){
				totalRight++;
			}
			else if(testPics[i].getType()!=trainType &&(evalVal ==0)){
				totalRight++;
			}
			else{
				totalWrong++;
			}
		}
		System.out.println("Total Right: " + totalRight);
		return (double) totalRight/(totalRight+totalWrong);
	}
	public svm_model getModel(){
		return this.model;
	}
	
	private void buildProblem(){



		Picture[] pics = trainPics.allPics;
		for(int i =0; i < pics.length; i++){
			
			Picture currPic = pics[i];
			if(currPic.getType()==this.trainType){
				problem.y[i] = 1;
			}
			else{
				problem.y[i] = 0;
			}
			
			double[] doubImg;
			if(tinyImage){
				//determine if we use tiny image or histogram representation
				doubImg = currPic.getTinyImageDouble();
				//currPic.printTinyImage();
			}
			else{
				doubImg = currPic.getArrayHistogram();
				currPic.printArrayHistogram();
			}
			
			problem.x[i] = new svm_node[doubImg.length];
			for(int j = 0; j < doubImg.length; j++){
				svm_node node = new svm_node();
				node.index = j;
				node.value = doubImg[j];
				problem.x[i][j] = node;
				
			}
			System.out.println();
		}
	}
	
	private void initializeProblem(){

		problem = new svm_problem();
		int totalSize = trainPics.totalPics;
		problem.l = totalSize;
		//System.out.println("Total Size: " + totalSize);
		problem.y = new double[totalSize];
		problem.x = new svm_node[totalSize][];
	}
	private void initializePar(){

		par = new svm_parameter();
		if(isLinear){
		par.kernel_type = svm_parameter.LINEAR;
		}
		else{
			par.kernel_type = svm_parameter.RBF;
		}
		
		par.svm_type = svm_parameter.C_SVC;
		par.C = this.C;
		//default C = .0001;
	
		par.probability = 1;
		par.gamma = this.g;
		//default gamma = .5
		par.nu = 0.5;
		par.cache_size = 20000;
		par.eps =.001;
		
	}
	
	public static double evaluate(double[] features, svm_model model, int type) 
	{
	
	    svm_node[] nodes = new svm_node[features.length];
	    for (int i = 0; i < features.length; i++)
	    {
	        svm_node node = new svm_node();
	        node.index = i;
	        node.value = features[i];

	        nodes[i] = node;
	    }
	   // System.out.println(nodes[1290].value);
	    
	    int totalClasses = 2;       
	    int[] labels = new int[totalClasses];
	    svm.svm_get_labels(model,labels);
	    
	    double[] prob_estimates = new double[totalClasses];
	    double v = svm.svm_predict_probability(model, nodes, prob_estimates);
	    
	    for (int i = 0; i < totalClasses; i++){
	        System.out.print("Label: " + labels[i] + ", prob: " + prob_estimates[i] + "  ");
	    }
	    System.out.println(" ");
	    System.out.println("Actual:" + type + " Prediction:" + v);  
	    System.out.println(" ");
	    return v;
	}
}
