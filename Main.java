package assignment1;

import java.util.Scanner;
import java.util.ArrayList;

public class main {

	public static void main(String[] args) {
		//Scanner takes input and stores each line as a string in an array list
		//Maze object is instantiated with the list of strings.
		Scanner scan = new Scanner(System.in);
		ArrayList<String> inputs = new ArrayList<String>();
		String method = scan.nextLine();
		while(scan.hasNext()){
			String input = scan.nextLine();
			if(input.equals("e"))
				break;
			inputs.add(input);
		}
		Maze maze = new Maze(inputs);
		if(method.equals("g")){
			maze.bfs();
			maze.dfs();
			maze.GreedyFirst();
			maze.AStar();
		}
		if(method.equals("c")){
			maze.cheeseSearch();
		}

	}
}
