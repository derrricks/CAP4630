/**
 * This class is responsible for creating a Node object. Each node acts as a corner for each of the polygonal obstacles
 * that appear in the A* Algorithm jPanel window or "maze." Each Node is passed in with an (x,y) value, which is an exact 
 * match of each of the hard coded polygonal obstacle (x,y) corners. There is also a start and goal node. (35 nodes total). 
 * Each node also contains a label (name), f-score, g-score, h-score, and a list of nodes, which contains all of the current nodes 
 * neighbors.
 * 
 * @author derrick
 * @version 2-14-2021
 */
import java.util.LinkedList;
	
public class Node {
	
	public String label;	// A, B, C
		
	public int x; // (x,y) points on graph
	public int y;
		
	public double f; // scores needed for the A* Algorithm
	public double g;
	public double h;
	public LinkedList<Node> neighbors;  // used for collision detection (hard-coded neighbors used)	
	

	public Node(String label, int x, int y) {
		
		this.label = label;
		this.x = x;
		this.y = y;

		f = 0;
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
	
	public double getF() {
		return f;
	}

	public void setF(double f) {
		this.f = f;
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
}