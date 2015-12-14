package assignment1;


public class Position {
	private int x;
	private int y;
	private Position parent;
	//Total distance from start so far
	private int totalCost;
	
	public Position(int x, int y, Position parent){
		this.x = x;
		this.y = y;
		this.parent = parent;
		
		if(parent != null)
			this.totalCost = parent.getTotalCost() + 1;
	}
	
	public int x(){
		return x;
	}
	
	public int y(){
		return y;
	}
	
	public int getTotalCost(){
		return totalCost;
	}

	public Position parent(){
		return parent;
	}
	
	//Manhattan Distance
	public int getDistanceFrom(Position p){
		return Math.abs(this.x() - p.x()) + Math.abs(this.y() - p.y());
	}
	
	public String toString(){
		return "(" + x + ", " + y + ")";
	}
	
	public boolean equals(Position p){
		return (this.x() == p.x() && this.y() == p.y());
	}

}
