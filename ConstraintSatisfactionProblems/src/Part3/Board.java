package Part3;

public class Board {
	private Place[][] places;
	private int rows;
	private int columns;
	
	public Board(int[][] readInBoard, Board parent){

		rows = readInBoard.length;
		columns = readInBoard[0].length;
		places = new Place[rows][columns];
		for(int i = 0; i < rows; i++){
			for(int j = 0; j < columns; j++){
				this.places[i][j] = new Place(readInBoard[i][j], 0);
			}
		}
	}
	
	public Board(Board oldBoard){
		//constructs a copy of oldBoard
		this.rows = oldBoard.rows;
		this.columns = oldBoard.columns;
		this.places = new Place[this.rows][this.columns];
		for(int i = 0; i < rows; i ++){
			for(int j = 0; j < columns; j++){
				int val = oldBoard.getPlace(i, j).getValue();
				int col = oldBoard.getPlace(i, j).getColor();
				this.places[i][j] = new Place(val, col);
			}
		}
	}
	
	public void placeBlue(int row, int column){
		//assume that we are placing on a blank space
		placeColor(row, column, 1);
	}
	
	public void placeGreen(int row, int column){
		//assume that we are placing on a blank space
		placeColor(row, column, 2);
	}
	
	public void placeColor(int row, int column, int color){
		//opp is the opposite color
		int opp = 1;
		if(color ==1)
			opp = 2;
		
		//checks if it has a neighbor of a matching color and if so captures all adjacent opponent spaces
		places[row][column].setColor(color);
		if(hasMatchingNeighbor(row, column)){
			if(row-1 >= 0 && places[row-1][column].getColor() == opp)
				places[row-1][column].setColor(color);
			if(row+1 <= (places.length-1) && places[row+1][column].getColor() == opp)
				places[row+1][column].setColor(color);
			if(column-1 >= 0 && places[row][column-1].getColor() == opp)
				places[row][column-1].setColor(color);
			if(column+1 <= (places[0].length-1) && places[row][column+1].getColor() == opp)
				places[row][column+1].setColor(color);
		}
	}
	
	//returns true if the location has a neighbor of the same color
	public boolean hasMatchingNeighbor(int row, int column){
		int color = places[row][column].getColor();
		if(row-1 >= 0 && places[row-1][column].getColor() == color)
			return true;
		if(row+1 <= (places.length-1) && places[row+1][column].getColor() == color)
			return true;
		if(column-1 >= 0 && places[row][column-1].getColor() == color)
			return true;
		if(column+1 <= (places[0].length-1) && places[row][column+1].getColor() == color)
			return true;
		return false;
	}
	
	public int score(){
		//returns blue's score - green's score
		//higher "score" value is more optimal for blue
		int blueScore = 0;
		int greenScore = 0;
		for(int i = 0; i < rows; i++){
			for(int j = 0; j < columns; j++){
				if(places[i][j].getColor() == 1){
					blueScore+= places[i][j].getValue();
				}
				else if(places[i][j].getColor() == 2){
					greenScore+= places[i][j].getValue();
				}
			}
		}
		return blueScore - greenScore;
	}
	
	public Place getPlace( int row, int column){
		return places[row][column];
	}
	
	public void printBoard(){
		for(int i = 0; i < rows; i++){
			for(int j = 0; j < columns; j++){
				System.out.print("  " + places[i][j].getValue());
				if(places[i][j].getValue() < 10)
					System.out.print(" ");
				if(places[i][j].getColor() == 1){
					System.out.print("B");
				}
				else if (places[i][j].getColor() == 2){
				System.out.print("G");
				}
				else{
					System.out.print("n");
				}
			}
			System.out.println();
		}
		System.out.println();
	}
	
	//set whole board to no color
	public void reset(){
		for(int i = 0; i < rows; i++){
			for(int j = 0; j < columns; j++){
				places[i][j].setColor(0);
			}
		}
	}
	
	public int rows(){return rows;}
	public int columns(){return columns;}
	
	public String toString(){
		String output = "";
		for(int i = 0; i < rows; i++){
			for(int j = 0; j < columns; j++){
				output = output + places[i][j] + " ";
			}
			output += "\n";
		}
		return output;
	}
}
