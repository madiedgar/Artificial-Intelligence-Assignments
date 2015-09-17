package assignment1;
import java.util.Comparator;

public class PositionComparator implements Comparator<Position>
{
	Position end;
	boolean aStar;
	
	public PositionComparator(Position end, boolean aStar){
		this.end = end;
		this.aStar = aStar;
	}
    @Override
    public int compare(Position A, Position B)
    {
        //Manhatten distance of each point from the end
    	//This is h(x), the heuristic
    	int aValue = A.getDistanceFrom(end);
    	int bValue = B.getDistanceFrom(end);
    	
    	//This is g(x), the distance from the start.
    	//Only included with A* not greedy
    	if(aStar){
    		aValue += A.getTotalCost();
    		bValue += B.getTotalCost();
    	}
    	
        if (aValue > bValue)
        {
            return 1;
        }
        if (bValue > aValue)
        {
            return -1;
        }
        return 0;
    }
}
