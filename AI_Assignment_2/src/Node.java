import java.util.LinkedList;

/**
 * This class is responsible for creating a Node object. Each node acts as a corner for each of the obstacles
 * that appear in the JPanel. Each node is passed in with an (x,y) value, which is an exact 
 * match of each of the hard coded obstacle (x,y) corners. There is also a start and goal node.
 * Each node also contains a label (name), u-score, g-score, h-score, and a list of all of the current node's neighbors.
 * 
 * @author derrick
 * @version 2-24-2021
 */
public class Node {
	
	public String label;	// A, B, C
		
	public int x; // (x,y) points on graph
	public int y;
		
	public double u; 
	public double g;
	public double h;
	public LinkedList<Node> neighbors;
	
	
	public Node(String label, int x, int y) {
		
		this.label = label;
		this.x = x;
		this.y = y;

		u = 0;
		g = 0;
		h = 0;
		neighbors = new LinkedList<Node>();
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public double getU() {
		return u;
	}

	public void setU(double u) {
		this.u = u;
	}

	public double getG() {
		return g;
	}

	public void setG(double g) {
		this.g = g;
	}

	public double getH() {
		return h;
	}

	public void setH(double h) {
		this.h = h;
	}

	public void removeNeighbor(Node neighbor) {
		neighbors.remove(neighbor);
	}
}