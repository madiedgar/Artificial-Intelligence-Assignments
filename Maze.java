package assignment1;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.PriorityQueue;

public class Maze {
	int[][] maze;
	int nodesExpanded;
	Position start;
	Position endPosition;
	ArrayList<String> inputs;
	ArrayList<Position> cheeseList;
	Position[][] cheeseMatrix;
	
	public Maze(ArrayList<String> inputs){
		this.inputs = inputs;
		readMaze();
	}
	public int getNodesExpanded(){
		return nodesExpanded;
	}
	
	//Turns the graphical maze into an array
	//1 = wall
	//2 = goal
	//3 = start
	//4 = cheese
	//0 = blank space
	public void readMaze(){
		int width = inputs.get(0).length();
		int height = inputs.size();
		maze = new int[height][width];
		cheeseMatrix = new Position[height][width];
		cheeseList = new ArrayList<Position>();
		
		for(int i = 0; i < height; i++){
			String currentLine = inputs.get(i);
			for(int j = 0; j < width; j++){				
				if(currentLine.charAt(j) == '%'){
					maze[i][j] = 1;
				} else if(currentLine.charAt(j) == 'G'){
					maze[i][j] = 2;
					endPosition = new Position(j,i,null);
				} else if(currentLine.charAt(j) == 'S'){
					start = new Position(j, i, null);
					maze[i][j] = 3;
				} else if(currentLine.charAt(j) == '.'){
					Position cheese = new Position(j, i, null);
					cheeseList.add(cheese);
					cheeseMatrix[i][j] = cheese;
					maze[i][j] = 4;
				} else{
					maze[i][j] = 0;
				}
			}
		}
	}
	
	//does the opposite of read maze Only difference is if it is a cheese maze,
	//numbers are converted into ascii characters by adding 83.
	public void printMaze(){
		int steps = 0;
		for(int i = 0; i < maze.length; i++){
			for(int j = 0; j < maze[0].length; j++){
				if(maze[i][j] == 0){
					System.out.print(" ");
				} else if(maze[i][j] == 1){
					System.out.print("%");
				} else if(maze[i][j] == 2){
					System.out.print("G");
				} else if(maze[i][j] == 3){
					System.out.print("S");
				} else if(maze[i][j] == 4){
					System.out.print(".");
					steps++;
				} else if(maze[i][j] > 4){
					if(maze[i][j] <14){
						System.out.print(maze[i][j]-4);
					} else{						
						System.out.print(Character.toChars(maze[i][j]+83));
					}
				}
			}
			System.out.println();
			
		}
		System.out.println("Total Steps for Completion: " + steps);
		System.out.println("Nodes Expanded: " + this.getNodesExpanded());
		System.out.println();
	}
	
	public void reset(){
		//resets any "." in maze to blank spaces.
		//so we can run search on maze, 
		//then print it then run another without having previous dots
		for(int i = 0; i < maze.length; i++){
			for(int j = 0; j < maze[0].length; j++){
				if(maze[i][j] == 4){
					maze[i][j] = 0;
				}
			}
		}
	}
	
	public void dfs(){
		System.out.println("Depth First Search");
		this.reset();
		nodesExpanded = 0;
		//matrix of already visited nodes
		boolean[][] visited = new boolean[maze.length][maze[0].length];
		Position end = new Position(0, 0, null);
		Stack<Position> stack = new Stack<Position>();
		stack.add(start);
		//while there are nodes in the stack, keep popping off the one on the top until the end node is found
		while(stack.peek() != null){
			Position position = stack.pop();
			int y = position.y();
			int x = position.x();
			//The following adds any blank spaces that are in any of the four cardinal directions and not visited already to the stack
			//down
			if(y <= maze.length-2){
				if(maze[y+1][x] == 0 && !visited[y+1][x]){
					visited[y+1][x] = true;
					stack.push(new Position(x, y+1, position));
					nodesExpanded++;
				}
				if(maze[y+1][x] == 2){
					end = new Position(x, y+1, position);
					nodesExpanded++;
					break;
				}
			}
			//left
			if(x >= 1){
				if(maze[y][x-1] == 0 && !visited[y][x-1]){
					visited[y][x-1] = true;
					stack.push(new Position(x-1, y, position));
					nodesExpanded++;
				}
				if(maze[y][x-1] == 2){
					end = new Position(x-1, y, position);
					nodesExpanded++;
					break;
				}
			}
			//right
			if(y <= maze[0].length-2){
				if(maze[y][x+1] == 0 && !visited[y][x+1]){
					visited[y][x+1] = true;
					stack.push(new Position(x+1, y, position));
					nodesExpanded++;
				}
				if(maze[y][x+1] == 2){
					end = new Position(x+1, y, position);
					nodesExpanded++;
					break;
				}
			}
			//up
			if(y >= 1){
				if(maze[y-1][x] == 0 && !visited[y-1][x]){
					visited[y-1][x] = true;
					stack.push(new Position(x, y-1, position));
					nodesExpanded++;
				}
				if(maze[y-1][x] == 2){
					end = new Position(x, y-1, position);
					nodesExpanded++;
					break;
				}
			}
		}
		
		//works backwards from the end once it's found to update the maze matrix, so that when it is printed, it shows the path.
		while(end.parent() != null){
			int value = maze[end.y()][end.x()];
			if(!(value == 3 || value == 2)){
				maze[end.y()][end.x()] = 4;
			}
			end = end.parent();
		}
		
		printMaze();
	}
	
	public void bfs(){
		//Runs queue search with a normal FIFO queue
		System.out.println("Breadth First Search");
		Queue<Position> queue = new LinkedList<Position>();
		queueSearch(queue);
	}
	
	public void AStar(){
		//Runs queue search with a priority queue with the A* heuristic
		System.out.println("AStar Search");
		Comparator<Position> comparator = new PositionComparator(endPosition, true);
		PriorityQueue<Position> pQueue = new PriorityQueue<Position>(1, comparator);
		queueSearch(pQueue);
	}
	
	public void GreedyFirst(){
		//Runs queue search with a priority queue with the Greedy First heuristic
		System.out.println("Greedy First Search");
		Comparator<Position> comparator = new PositionComparator(endPosition, false);
		PriorityQueue<Position> pQueue = new PriorityQueue<Position>(1, comparator);
		queueSearch(pQueue);
	}
	
	public void queueSearch(Queue<Position> queue){
		this.reset();
		nodesExpanded = 0;
		boolean[][] visited = new boolean[maze.length][maze[0].length];
		Position end = new Position(0, 0, null);
		queue.add(start);
		//while there are nodes in the queue, keep polling them until the end node is found
		while(queue.peek() != null){
			Position position = queue.poll();
			int y = position.y();
			int x = position.x();
			//The following adds any blank spaces that are in any of the four cardinal directions and not visited already to the queue
			//down
			if(y <= maze.length-2){
				if(maze[y+1][x] == 0 && !visited[y+1][x]){
					visited[y+1][x] = true;					
					queue.add(new Position(x, y+1, position));
					nodesExpanded++;
				}
				if(maze[y+1][x] == 2){
					end = new Position(x, y+1, position);
					nodesExpanded++;
					break;
				}
			}
			//left
			if(x >= 1){
				if(maze[y][x-1] == 0 && !visited[y][x-1]){
					visited[y][x-1] = true;
					queue.add(new Position(x-1, y, position));
					nodesExpanded++;
				}
				if(maze[y][x-1] == 2){
					end = new Position(x-1, y, position);
					nodesExpanded++;
					break;
				}
			}
			//right
			if(y <= maze[0].length-2){
				if(maze[y][x+1] == 0 && !visited[y][x+1]){
					visited[y][x+1] = true;
					queue.add(new Position(x+1, y, position));
					nodesExpanded++;
				}
				if(maze[y][x+1] == 2){
					end = new Position(x+1, y, position);
					nodesExpanded++;
					break;
				}
			}
			//up
			if(y >= 1){
				if(maze[y-1][x] == 0 && !visited[y-1][x]){
					visited[y-1][x] = true;
					queue.add(new Position(x, y-1, position));
					nodesExpanded++;
				}
				if(maze[y-1][x] == 2){
					end = new Position(x, y-1, position);
					nodesExpanded++;
					break;
				}
			}
		}
		
		//works backwards from the end once it's found to update the maze matrix, so that when it is printed, it shows the path.
		while(end.parent() != null){
			int value = maze[end.y()][end.x()];
			if(!(value == 3 || value == 2)){
				maze[end.y()][end.x()] = 4;
			}
			end = end.parent();
		}
		
		printMaze();
	}
	
	//runs a cheese search to find the best order to collect all of the cheeses
	public void cheeseSearch(){
		nodesExpanded = 0;
		ArrayList<Position> solution = new ArrayList<Position>();
		Comparator<Position> comparator = new CheeseComparator(cheeseList, solution);
		List<Position> frontier = new ArrayList<Position>();
		boolean[][] visited = new boolean[maze.length][maze[0].length];
		frontier.add(start);
		
		while(frontier.size()>0 && cheeseList.size() > 0){
			//Sort the frontier lowest to highest. Reheapify won't work because the positions won't update unless you reheapify
			Collections.sort(frontier, comparator);
			Position position = frontier.get(0);
			frontier.remove(0);			
			int y = position.y();
			int x = position.x();
			//if cheese, add it to the solution and remove it from the cheese list of remaining cheeses to find
			if(maze[y][x] == 4){
				cheeseList.remove(cheeseMatrix[y][x]);
				solution.add(position);
			}
			//The following adds any blank spaces or cheese that are
			//in any of the four cardinal directions and not visited already to the frontier
			//down
			if(y <= maze.length-2){
				if((maze[y+1][x] == 0 || maze[y+1][x] == 4) && !visited[y+1][x]){
					visited[y+1][x] = true;
					frontier.add(new Position(x, y+1, position));
					nodesExpanded++;
				}
			}
			//left
			if(x >= 1){
				if((maze[y][x-1] == 0 || maze[y][x-1] == 4) && !visited[y][x-1]){
					visited[y][x-1] = true;
					frontier.add(new Position(x-1, y, position));
					nodesExpanded++;
				}
			}
			//right
			if(y <= maze[0].length-2){
				if((maze[y][x+1] == 0 || maze[y][x+1] == 4) && !visited[y][x+1]){
					visited[y][x+1] = true;
					frontier.add(new Position(x+1, y, position));
					nodesExpanded++;
				}
			}
			//up
			if(y >= 1){
				if((maze[y-1][x] == 0 || maze[y-1][x] == 4) && !visited[y-1][x]){
					visited[y-1][x] = true;
					frontier.add(new Position(x, y-1, position));
					nodesExpanded++;
				}
			}
		}
		
		//updates the maze matrix with the order of visiting nodes starting from 5 (1-4 are reserved for other symbols)
		for(int i = 0; i < solution.size(); i++){
			Position cheese = solution.get(i);
			maze[cheese.y()][cheese.x()] = i+5;
		}
		printMaze();
	}
}
