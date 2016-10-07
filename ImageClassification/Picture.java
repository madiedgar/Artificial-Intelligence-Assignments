package a4;

import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Picture {

	private int type;
	//type values
		// 1 - hobo bags
		// 2 - clutch bags
		// 3 - pumps
		// 4 - flats
	private String name;
	private final static int RESCALE_WIDTH = 32;
	private final static int RESCALE_HEIGHT = 32;
	private final static int HISTOGRAM_SIZE = 8;
	private int[] tinyImage;
	private double[][][] histogram;
	private BufferedImage fullPic;
	private BufferedImage smallPic;
	
	public Picture(String picLocation) throws IOException{

		File picFile = new File(picLocation);
		fullPic = ImageIO.read(picFile);
		this.name = picFile.getName();
		this.createTinyImage();
		//this.displayFullPicture();
		//this.displaySmallPicture();
		this.createHistogram();
		
	}
	
	public String getName(){
		return name;
	}
	
	public void setType(int type){

		this.type = type;
	}
	
	public int getType(){

		return type;
	}
	
	///////////////////// TINY IMAGE METHODS /////////////////////
	
	public int [] getTinyImage(){

		return tinyImage;
	}

	public double[] getTinyImageDouble(){
		double[] doub = new double[tinyImage.length];
		for(int i = 0; i < tinyImage.length; i++){
			doub[i] = (double) tinyImage[i];
		}
		return doub;
	}
	
	public void printTinyImage(){
		for(int i = 0; i < RESCALE_WIDTH*RESCALE_HEIGHT*3; i++){
			System.out.print(i + ": " + tinyImage[i] + "   ");
		}
		System.out.println();
	}

	private void createTinyImage(){


		//Resize the image to smaller image
		smallPic = resize(fullPic,RESCALE_WIDTH,RESCALE_HEIGHT);
		//get RGB Values of this small image
		
		//set up tinyImage array
		//the first 3 entries represent rgb for the pixel at 0,0
		//the next 3 entries represent rgb for pixel at 0,1 and so on
		tinyImage = new int[RESCALE_WIDTH*RESCALE_HEIGHT*3];
		
		int[] rgb;
		for(int i = 0; i < RESCALE_WIDTH; i++){
			for(int j = 0; j < RESCALE_HEIGHT; j++){
				rgb = getPixelData(smallPic, i,j);
				for(int k = 0; k < 3; k++){
					//take current r, g or b value and put it in tinyImage
					tinyImage[96*i+3*j+k] = rgb[k];
					//System.out.print(rgb[k] + " ");
				}
				
				//System.out.println();
			}
		}
	}
	
	public static BufferedImage resize(BufferedImage img, int newW, int newH) {
		//resizes image to desired height and width
	    Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
	    //can use SCALE_SMOOTH, SCALE_DEFAULT or other pre-defined scales
	    BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

	    Graphics2D g2d = dimg.createGraphics();
	    g2d.drawImage(tmp, 0, 0, null);
	    g2d.dispose();

	    return dimg;
	}  
	
	
	///////////////// HISTOGRAM METHODS   /////////////
	
	public double[][][] getHistogram(){
		return histogram;
	}
	
	public double[] getArrayHistogram(){
		
		double[] toReturn = new double[HISTOGRAM_SIZE*HISTOGRAM_SIZE*HISTOGRAM_SIZE];
		for(int i = 0; i < HISTOGRAM_SIZE; i++){
			for(int j = 0; j < HISTOGRAM_SIZE; j++){
				for(int k = 0; k < HISTOGRAM_SIZE; k++){
					toReturn[i*HISTOGRAM_SIZE*HISTOGRAM_SIZE+j*HISTOGRAM_SIZE+k] = histogram[i][j][k];
				}
			}
		}
		return toReturn;
	}
	
	public void printArrayHistogram(){
		double[] arrHist = this.getArrayHistogram();
		for(int i = 0; i < arrHist.length; i++){
			System.out.print(arrHist[i] + "  ");
		}
		System.out.println();
	}
	
	public void createHistogram(){

		//keep track of number of pixels in each bin with histogram Bins
		int[][][] histogramBins = new int[HISTOGRAM_SIZE][HISTOGRAM_SIZE][HISTOGRAM_SIZE];
		
		
		int[] rgb;
		int rBin;
		int gBin;
		int bBin;
		for(int i = 0; i < fullPic.getWidth(); i++){
			for(int j = 0; j < fullPic.getHeight(); j++){
				rgb = getPixelData(fullPic, i,j);
				rBin = (rgb[0]*HISTOGRAM_SIZE)/256;
				gBin = (rgb[1]*HISTOGRAM_SIZE)/256;
				bBin = (rgb[2]*HISTOGRAM_SIZE)/256;
				//System.out.print(rgb[0] + " " );
				//System.out.print(rgb[1] + " ");
				//System.out.println(rgb[2] + " ");
				histogramBins[rBin][gBin][bBin]+=1;
			}
		}
		//Normalize the bin count to percentages
		//create histogram array
		histogram = new double[HISTOGRAM_SIZE][HISTOGRAM_SIZE][HISTOGRAM_SIZE];
		
		int totalPixels = fullPic.getHeight()*fullPic.getWidth();
		//System.out.println("totalpixels" + totalPixels);
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				for(int k = 0; k < 8; k++){
					histogram[i][j][k] = (double) histogramBins[i][j][k]/totalPixels;
					//System.out.println(histogramBins[i][j][k]);
					if(histogram[i][j][k]>0.01){
					//	System.out.println("Bin " + (i*64+j*8+k) + ": " + histogram[i][j][k]+"%");
					}
				}
			}
		}
	}
	
	
	///////////// OTHER METHODS ///////////////
	
	private static int[] getPixelData(BufferedImage img, int x, int y) {

		//used to pull the RGB values from a pixel at x,y
		int argb = img.getRGB(x, y);

		//binary math
		int rgb[] = new int[] {
		    (argb >> 16) & 0xff, //red
		    (argb >>  8) & 0xff, //green
		    (argb      ) & 0xff  //blue
		};
		return rgb;
	}
	
	/////////////////////// DISPLAY METHODS  ///////////////////////
	public void displayFullPicture(){

		//displays original image
		display(fullPic);
	}
	
	public void displaySmallPicture(){

		//displays smaller image
		display(smallPic);
	}
	
	private void display(BufferedImage toDisplay){

		Frame frame = new JFrame();
		((JFrame) frame).getContentPane().setLayout(new FlowLayout());
		((JFrame) frame).getContentPane().add(new JLabel(new ImageIcon(toDisplay)));
		frame.pack();
		frame.setVisible(true);
	}
}
