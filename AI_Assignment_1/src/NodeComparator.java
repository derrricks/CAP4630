/**
 * The NodeComparator class is responsible for keeping the nodes ordered when entering
 * them into the openSet priority queue, which is used for the A* algorithm. The order is based
 * on each nodes f-score and is meant to assign the highest priority to the node with the lowest 
 * f-score. The purpose is to have the node with the lowest f-score removed first.
 * 
 * @author derrick
 * @version 2-14-2021
 */
import java.util.Comparator;

public class NodeComparator implements Comparator<Node> {
	
	public int compare(Node o1, Node o2) {
		return o1.f > o2.f ? 1 : -1;
	}
}