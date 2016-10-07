package Part2;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int n = scan.nextInt();
		int k = scan.nextInt();
		boolean[][] trees = new boolean[n][n];
		for(int i=0; i < k; i++){
			int y = scan.nextInt();
			int x = scan.nextInt();
			trees[x-1][y-1] = true;
		}
		scan.close();
		Forest forest = new Forest(trees);
		int[] solution = forest.localSearch();
		for(int i = 0; i < solution.length; i++){
			System.out.println((i+1) + " " + (solution[i]+1));
		}
		forest.printForest();
	}

}
