package Part3;

import java.util.Scanner;

public class Main {
	public static void main(String args[]){

		Scanner scan = new Scanner(System.in);
		int[][] readInBoard = new int[6][6];

		for(int i = 0; i < 6; i++){
			for(int j = 0; j < 6; j++){
				readInBoard[i][j] = scan.nextInt();
			}
		}
		scan.close();


		Board board = new Board(readInBoard, null);

		for(int i =0; i < 36; i++){
			MiniMaxTree minimax = new MiniMaxTree(board, 5, i+1);
			minimax.buildABTree();
//			minimax.buildTree();
			Board best;
			if((i+1)%2 == 1){
				best = minimax.getMax();
			} else{
				best = minimax.getMin();
			}
			board = best;
			
			System.out.println("Turn " + i);
			board.printBoard();
		}
		System.out.println("Score: " + board.score());
		if(board.score() > 0){
			System.out.println("Blue wins");
		} else if(board.score() < 0){
			System.out.println("Green wins");
		} else{
			System.out.println("Tie");
		}
	}
}