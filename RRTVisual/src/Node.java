import java.awt.*;
/**
 * This class is responsible for holding the node object in the rapidly-exploring random tree (RRT). 
 * Each node has an (x,y) coordinate, radius, node attribute, and a parent/child node. 
 * 
 * The (x,y) coordinates are used for generating potential placements in the RRT. 
 * The radius is used for collision detection among the different nodes as more are being added to the tree.
 * The node attribute is used to distinguish the start node, goal node, and all other nodes from each other. 
 * The parent/child node is used for setting the connections between the nodes as they are added to the tree.
 * 
 * @author derrick
 * @version 5-2-21
 */
public class Node {
	
	int x, y;
	int nodeAttribute;
	int radius;
	Node parent;
	Node child;	
	
	public Node(int x, int y){
		
		this.x = x;
		this.y = y;
		radius = 5; //used for collision detection
		nodeAttribute = 2;
		parent = null;
		child = null;
	}
	
	public Node(double d, double e) {
		this.x = (int) d;
		this.y = (int) e;
		radius = 5; //used for collision detection
		nodeAttribute = 2;	
		parent = null;
		child = null;
	}


	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setStartNode(){
		nodeAttribute = 0;
	}
	
	public void setGoalNode(){
		nodeAttribute = 1;
	}


	public Node getParent() {
		return parent;
	}


	public void setParent(Node parent) {
		this.parent = parent;
	}


	public Node getChild() {
		return child;
	}


	public void setChild(Node child) {
		this.child = child;
	}
	

	public int getRadius() {
		return radius;
	}


	public void setRadius(int radius) {
		this.radius = radius;
	}


	public void paint(Graphics g) {

		switch(nodeAttribute) {
			case 0:
				g.setColor(Color.BLUE); //start node will be blue
				g.fillOval(x, y, 5, 5);
				break;
			case 1:
				g.setColor(Color.RED); //goal node will be red
				g.fillOval(x, y, 5, 5);
				break;
			case 2:
				g.setColor(Color.BLACK); //any randomly generated node will be black
				g.fillOval(x, y, 2, 2);
				break;
		}
	}
}
