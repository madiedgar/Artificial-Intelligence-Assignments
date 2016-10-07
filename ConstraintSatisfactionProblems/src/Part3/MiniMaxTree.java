package Part3;

import java.util.ArrayList;

public class MiniMaxTree {
	private Board root;
	private int height;
	private int turn;
	private ArrayList<MiniMaxTree> children;

	public MiniMaxTree(Board root, int height, int turn){
		this.root = root;
		this.height = height;
		this.turn = turn;

		this.children = new ArrayList<MiniMaxTree>();
	}
	
	public Board root(){return root;}

	public void buildTree(){
		//base case if the tree is only height one, it won't have any children
		if(height <=1)
			return;
		//add a child to the tree for each possible move.
		//Blue is odd turns and green is even
		//recursively add children to each child added to the root
		for(int i =0; i < root.rows(); i++){
			for(int j=0; j < root.columns(); j++){
				if(root.getPlace(i,j).getColor() == 0){
					Board newChild = new Board(root);
					if(turn%2 == 1){
						newChild.placeBlue(i, j);
					} else{
						newChild.placeGreen(i, j);
					}
					MiniMaxTree childTree = new MiniMaxTree(newChild, height-1, turn+1);
					childTree.buildTree();
					children.add(childTree);
				}
			}
		}
	}
	
	public void buildABTree(){
		//alpha=-infinity, beta = infitinity
		buildABTree(Integer.MIN_VALUE, Integer.MAX_VALUE);
	}
	
	public void buildABTree(int alpha, int beta){
		//base case
		if(height<=1)
			return;
		
		for(int i =0; i < root.rows(); i++){
			for(int j=0; j < root.columns(); j++){
				if(root.getPlace(i,j).getColor() == 0){
					Board newChild = new Board(root);
					
					if(turn%2 == 1){
						newChild.placeBlue(i, j);
					} else{
						newChild.placeGreen(i, j);
					}
					MiniMaxTree childTree = new MiniMaxTree(newChild, height-1, turn+1);
					childTree.buildABTree(alpha, beta);
					
					//if it is blue's turn, update alpha to the max value of green's best move
					//if it is green's turn, update alpha to the min value of blue's best move
					if(turn%2 ==1){
						MiniMaxTree tmp = childTree.getMinTree();
						alpha = Math.max(tmp.score(), alpha);
					} else{
						MiniMaxTree tmp = childTree.getMaxTree();
						beta = Math.min(tmp.score(), beta);
					}
					children.add(childTree);
					
					//prune the tree if beta <= alpha
					if(beta <= alpha)
						return;
				}
			}
		}
	}
	
	//public methods that change the MiniMaxTree object to a board object
	public Board getMax(){
		return getMaxTree().root();
	}
	
	public Board getMin(){
		return getMinTree().root();
	}

	private MiniMaxTree getMaxTree(){
		int max = Integer.MIN_VALUE;
		int numChildren = children.size();
		MiniMaxTree nextMax = this;

		for(int i = 0; i < numChildren; i++){
			//recursively find the min value of each of the children
			MiniMaxTree tmp = this.children.get(i).getMinTree();
			int score = tmp.score();

			//find the max min value
			if(score > max){
				nextMax = this.children.get(i);
				max = score;
			}
		}
		return nextMax;
	}

	private MiniMaxTree getMinTree(){
		int min = Integer.MAX_VALUE;
		int numChildren = children.size();
		MiniMaxTree nextMin = this;

		for(int i = 0; i < numChildren; i++){
			//recursively find the max value of each of the children
			MiniMaxTree tmp = this.children.get(i).getMaxTree();
			int score = tmp.score();
			
			//find the min max value
			if(score < min){
				nextMin = this.children.get(i);
				min = score;
			}
		}
		return nextMin;
	}	

	private int score(){
		return root.score();
	}
}
