package assignment1;
import java.util.ArrayList;
import java.util.Comparator;

public class CheeseComparator implements Comparator<Position>
{
	ArrayList<Position> cheeseList;
	ArrayList<Position> solution;
	public CheeseComparator(ArrayList<Position> cheeseList, ArrayList<Position> solution){
		this.cheeseList = cheeseList;
		this.solution = solution;
	}
	
    @Override
    public int compare(Position A, Position B)
    {
    	
    	//h(x) = length of minimum spanning tree of remaining cheeses not already in the path +
    	//distance from closest remaining cheese to the last cheese added to the path
    	int distanceA = totalDistance(A);
    	distanceA += A.getTotalCost();
    	
    	int distanceB = totalDistance(B);
    	distanceB += B.getTotalCost();
    	
    	if(solution.size() > 0){
    		Position closest = findClosest(solution.get(solution.size()-1), cheeseList);
    		distanceA += A.getDistanceFrom(closest);
    		distanceB += B.getDistanceFrom(closest);
    	}
    	
    	if (distanceA < distanceB)
        {
            return -1;
        }
        if (distanceA > distanceB)
        {
            return 1;
        }
        return 0;
    }
    
    //returns total distance of all edges of an MST over the remaining cheeses
    //MST is estimated with Manhattan Distance
    private int totalDistance(Position start){
    	ArrayList<Position> inTree = new ArrayList<Position>();
    	ArrayList<Position> toBeAdded = new ArrayList<Position>();
    	for(Position cheese : cheeseList){
    		toBeAdded.add(cheese);
    	}
    	int totalDistance = 0;
    	inTree.add(start);
    	//Creates MST with Prim's algorithm
    	while(toBeAdded.size() > 0){
    		Position closest = null;
    		int min = Integer.MAX_VALUE;
    		for(Position cheese : inTree){
    			Position temp = findClosest(cheese, toBeAdded);
    			int tempDist = cheese.getDistanceFrom(temp);
    			if(closest == null || tempDist < min){
    				min = tempDist;
    				closest = temp;
    			}
    		}
    		toBeAdded.remove(closest);
    		inTree.add(closest);
    		totalDistance += min;
    	}
    	//returns total distance of MST
    	return totalDistance;
    }
    
    //finds closest point in the list to Position A and returns it
    private Position findClosest(Position A, ArrayList<Position> list){
    	Position closest = null;
    	int min = Integer.MAX_VALUE;
    	for(Position cheese : list){
    		int distance = A.getDistanceFrom(cheese);
    		if(distance < min){
    			min = distance;
    			closest = cheese;
    		}
    	}
    	return closest;
    }
}
