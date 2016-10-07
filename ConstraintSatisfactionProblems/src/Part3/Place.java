package Part3;

public class Place {

	private int color;
	//0 - not assigned
	//1 = blue
	//2 = green
	
	private int value;
	
	public Place(int value, int color){
		this.value = value;
		this.color = color;
	}
	
	public int getColor(){
		return color;
	}
	
	public int getValue(){
		return value;
	}
	
	public void setColor(int newColor){
		this.color = newColor;
	}
	
	public String toString(){
		String output = "";
		if(color == 0)
			output += "N";
		if(color == 1)
			output += "B";
		if(color ==2)
			output += "G";
		output += "/";
		output += value;
		if(value < 10)
			output += " ";
		return output;
	}
}
