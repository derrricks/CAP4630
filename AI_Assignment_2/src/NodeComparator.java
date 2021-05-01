import java.util.Comparator;

/**
 * The NodeComparator class is responsible for keeping the nodes ordered when entering
 * them into the open priority queue. The order is based on each nodes u-score and assigns
 * the highest priority to the node with the highest u-score. The purpose is to have the 
 * node with the highest u-score removed first.
 * 
 * @author derrick
 * @version 2-24-2021
 */
public class NodeComparator implements Comparator<Node> {
	
	public int compare(Node o1, Node o2) {
		return o1.u < o2.u ? 1 : -1;
	}
}