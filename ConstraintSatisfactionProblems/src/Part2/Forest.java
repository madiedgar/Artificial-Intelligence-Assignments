package Part2;

import java.util.ArrayList;

public class Forest {
	boolean[][] trees;
	int size;
	int[] positions;
	private int moveCount = 0;
	
	public Forest(boolean[][] trees){
		this.trees = trees;
		size = trees.length;
		//randomly place friends, one per column in a space with no tree
		positions = new int[size];
		for(int i=0; i < size; i++){
			int row = (int)(Math.random() * size);
			while(trees[i][row]){
				row = (row + 1)%size;
			}
			positions[i] = row;
		}
	}
	
	public int[] localSearch(){
		int toMove = -1;
		//local search move friends around until there are no conflicts
		while(totalConflicts() != 0){
			toMove = mostConflictsExcluding(toMove);
			moveToBestRow(toMove);
			moveCount++;
		}
		return positions;
	}
	
	//moves a friend in column x (0-indexed) to the row with least conflicts
	private void moveToBestRow(int x){
		//find row with minimum number of conflicts for the column
		int min = Integer.MAX_VALUE;
		for(int i = 0; i < size; i++){
			if(!trees[x][i]){
				positions[x] = i;
				int conflicts = conflicts(x);
				if(conflicts < min){
					min = conflicts;
				}
			}
		}
		//'rows' contains all rows with least conflicts
		ArrayList<Integer> rows = new ArrayList<Integer>();
		for(int i =0; i < size; i++){
			if(!trees[x][i]){
				positions[x] = i;
				if(conflicts(x) == min)
					rows.add(i);
			}
		}
		//randomly select a row
		int rand = (int)(Math.random() * rows.size());
		positions[x] = rows.get(rand);
	}
	
	//returns the total conflicts for the entire forest
	private int totalConflicts(){
		int count = 0;
		for(int i = 0; i < size; i++){
			count += conflicts(i);
		}
		return count;
	}
	
	//returns the column with the most conflicts excluding the parameter passed in
	//pass in -1 to include all columns
	private int mostConflictsExcluding(int excluding){
		//find max conflicts in a column
		int max = 0;
		for(int i = 0; i < size; i++){
			if(i != excluding){
				int conflicts = conflicts(i);
				if(conflicts > max){
					max = conflicts;
				}
			}
		}
		//get all columns that contain the max number of conflicts
		ArrayList<Integer> columns = new ArrayList<Integer>();
		for(int i =0; i < size; i++){
			if(conflicts(i) == max)
				columns.add(i);
		}
		//randomly choose a column from 'columns'
		int rand = (int)(Math.random() * columns.size());
		return columns.get(rand);
	}
	
	//returns the number of conflicts for a friend in a given column 'x'
	private int conflicts(int x){
		int count = 0;
		for(int i = 0; i < size; i++){
			if(i !=x){
				if(canSee(x, i))
					count++;
			}
		}
		return count;
	}
	
	//returns true if the friends in x1 and x2 can see each other
	private boolean canSee(int x1, int x2){
		if(x1 == x2)
			return false;
		int low = Math.min(x1, x2);
		int high = Math.max(x1, x2);
		//if in the same row check if there is a tree between x1 and x2
		if(positions[x1] == positions[x2]){
			int y = positions[x1];
			boolean canSee = true;
			for(int i = low+1; i < high; i++){
				if(trees[i][y] == true)
					canSee = false;
			}
			return canSee;
		}
		//if in the same diagnoal, check if there is a tree on the diagonal between x1 and x2
		if(Math.abs(x1-x2) == Math.abs(positions[x1]-positions[x2])){
			int y = positions[low];
			int increment = (int)((x2-x1)/(positions[x2]-positions[x1]));
			boolean canSee = true;
			for(int i = low+1; i < high; i++){
				y += increment;
				if(trees[i][y] == true)
					canSee = false;
			}
			return canSee;
		}
		return false;
	}
	
	//print forest with T for tree, F for friend, and . for an empty space
	public void printForest(){
		for(int i = 0; i < size; i++){
			//columns
			for (int j = 0; j < size; j++){
				//rows
				if(trees[i][j] == true){
					System.out.print("T");
				}
				else if(positions[i] == j){
					System.out.print("F");
				}
				else{
					System.out.print(".");
				}
			}
			System.out.println();
		}
		System.out.println("There were " + moveCount + " iterations");
	}
}
