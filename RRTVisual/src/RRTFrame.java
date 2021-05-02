import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.*;
import java.util.concurrent.TimeUnit;

import javax.swing.*;
import javax.swing.Timer;

/**
 * This class is responsible for implementing the rapidly-exploring random tree (RRT). It makes use of an RRT object
 * to build the tree, which also calls on all the methods needed to generate the RRT. This class makes uses of 3 predetermined 
 * environment.
 * 
 * @author derrick
 * @version 5-2-21
 */
public class RRTFrame extends JPanel {

	public static LinkedList<Node> nodes = new LinkedList<Node>();	// stores all the nodes used in the algorithm.
	public static ArrayList<Rectangle2D> obstacles = new ArrayList<Rectangle2D>(); // stores all the obstacles in the different environments.
	public static Node finalPath[]; // used to draw the final path between goal and start nodes.
	public static Node start;
	public static Node goal;
	
	public static void main(String[] args) throws InterruptedException {
		
		System.out.println("RRT Path Finding Algorithm");
		System.out.println("Blue Cicle -> Starting Point");
		System.out.println("Red Circle -> Goal Point");
		environmentSelection();
	}
	
	public static void environmentSelection() throws InterruptedException {

		Scanner in = new Scanner(System.in);		
		RRTFrame rrt;
		
		String environmentSelect; // Environment 1, 2, or 3
		int maxVerts; // max number of vertices allowed in RRT
		int windowWidth; // window size width
		int windowHeight; // window height width
		int deltaQ; // step distance between nodes
		
		System.out.println("Please Select an Enviroment Using 1, 2, or 3");
		environmentSelect = in.nextLine();
		
		switch(environmentSelect) {
		
		case "1":
			maxVerts = 5000;
			windowWidth = 300;
			windowHeight = 300;
			deltaQ = 5;
			rrt = new RRTFrame(maxVerts, windowWidth, windowHeight, deltaQ, environmentSelect);
			break;
			
		case "2":
			maxVerts = 5000;
			windowWidth = 1000;
			windowHeight = 400;
			deltaQ = 5;
			rrt = new RRTFrame(maxVerts, windowWidth, windowHeight, deltaQ, environmentSelect);
			break;
			
		case "3":
			maxVerts = 5000;
			windowWidth = 600;
			windowHeight = 600;
			deltaQ = 5;
			rrt = new RRTFrame(maxVerts, windowWidth, windowHeight, deltaQ, environmentSelect);
			break;
			
		default:
			environmentSelection();
		}		
	}
		

	/**
	 * used to paint on the JPanel
	 */
	public void paint(Graphics g) {

		super.paintComponent(g);
		
		//start node
		g.setColor(Color.BLUE);
		g.fillOval(start.getX(), start.getY(), 5, 5);
		//goal node
		g.setColor(Color.RED);
		g.fillOval(goal.getX(), goal.getY(), 5, 5);
		
		//draws obstacles
		g.setColor(Color.DARK_GRAY);
		if(!(obstacles == null)) {
			for(Rectangle2D poly: obstacles) {
				g.drawRect((int)poly.getX(), (int)poly.getY(), (int)poly.getWidth(), (int)poly.getHeight());
			}
		}
					
		//draws connections between nodes and parent nodes
		g.setColor(Color.DARK_GRAY);
		if(!(nodes == null)) {
			for(Node node: nodes) {
				if(node.getParent() == null) continue;
				
				g.drawLine(node.getX(), node.getY(), node.getParent().getX(), node.getParent().getY());
			}
		}
		
		//draws final path if one exist
		if(!(finalPath == null)) {
			g.setColor(Color.ORANGE);
			for(int i = 0; i < finalPath.length - 1; i++) {
				if(finalPath[i] == null) continue;
				
				g.drawLine(finalPath[i].getX(), finalPath[i].getY(), finalPath[i].getParent().getX(), finalPath[i].getParent().getY());			
			}
		}
	}
						

	/**
	 * The RRT constructor which is responsible for building and implementing the RRT algorithm
	 * @throws InterruptedException 
	 */
	public RRTFrame(int maxVerts, int windowWidth, int windowHeight, int deltaQ, String environmentSelect) throws InterruptedException {

		JFrame frame = new JFrame("RRT Visualization");
		frame.add(this);
		frame.setSize(windowWidth + 2, windowHeight + 30); // set frame size
		frame.setVisible(true); // display frame
		frame.setResizable(false);
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		
		// we begin by generating obstacles on the frame
		generateObstacles(windowWidth, windowHeight, environmentSelect);
	
		// generate a random start node, that does not lay within an obstacle. Then add the node to the tree as the root.
		start = randomNode(windowWidth, windowHeight);
		while(checkCollisionForStartAndGoal(start) == true) {
			start = randomNode(windowWidth, windowHeight);
		}
		start.setStartNode();
		nodes.add(start);

		// generate a random goal node, that does not lay within an obstacle. 
		goal = randomNode(windowWidth, windowHeight);
		while(checkCollisionForStartAndGoal(goal) == true) {
			goal = randomNode(windowWidth, windowHeight);
		}
		goal.setGoalNode();
		

		System.out.println("Maximum Number of Vertices: " + maxVerts);
		
		// START THE ALGORITHM
		int i;
		for(i = 0; i < maxVerts; i ++) {
			
			Node qRand = randomNode(windowWidth, windowHeight);
			Node qNear = nearestVertex(start, qRand);
			Node qNew = new_config(qNear, qRand, deltaQ);
			
			if(checkCollisionWithObjects(qNear,qNew) == true) {
				i--;
				continue; //i-- to skip iteration and ignore count altogether
			}
			
			nodes.add(qNew);
			addEdge(qNear, qNew);
			
			frame.repaint();
			TimeUnit.MILLISECONDS.sleep(10);
	
			// used to find position relative to the goal
			if(Math.sqrt((qNew.getX() - goal.getX()) * (qNew.getX() - goal.getX()) + 
						(qNew.getY() - goal.getY()) * (qNew.getY() - goal.getY())) <= deltaQ){
				
				System.out.println("Goal Reached on Iteration..." + i);
				
				finalPath = reconstructPath(goal);
				nodes.add(goal);
				addEdge(qNew, goal);
				
				frame.repaint();
				TimeUnit.MILLISECONDS.sleep(10);
				break;
			} 
		}
		
		if(i == maxVerts) {
			System.out.println("No Goal Located...");
		}
		
		nodes.add(goal);
		frame.repaint();
		TimeUnit.MILLISECONDS.sleep(10);
	}
	
	
	/**
	 * This method generates a node with a random set of (x,y) points
	 * @param windowWidth frame width
	 * @param windowHeight frame height
	 * @return new random node
	 */
	public Node randomNode(int windowWidth, int windowHeight) {
		
		int x , y;
		x = (int) (Math.random() * (windowWidth) + 0);
		y = (int) (Math.random() * (windowHeight) + 0);
		
		while(checkCollisionWithNodes(x, y)) {
			
			x = (int) (Math.random() * (windowWidth) + 0);
			y = (int) (Math.random() * (windowHeight) + 0);

		}
		return new Node(x, y);
	}
	

	/**
	 * This method checks collision between any two nodes in the RRT
	 * Ensure no two nodes overlap
	 */
	public Boolean checkCollisionWithNodes(int x, int y) {
		
		for(Node nVerts: nodes) {
			
			if( (Math.sqrt((x - nVerts.getX()) * (x - nVerts.getX()) + (y - nVerts.getY()) * (y - nVerts.getY())) <= nVerts.getRadius())) {
				return true;
			}
		}
		return false;
	}	
	
	
	/**
	 * This method checks collision for the start and goal nodes between the obstacles generated.
	 * Ensures a start and goal node do not generate within an obstacle
	 */
	public Boolean checkCollisionForStartAndGoal(Node x) {
	
		for(Rectangle2D poly: obstacles) {
			if(poly.contains(x.x, x.y)) {
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * This method checks collision between the nodes and obstacles.
	 * Ensures no nodes cross into an obstacles boundaries
	 */
	public Boolean checkCollisionWithObjects(Node qNear, Node qNew) {

		Line2D crossTest = new Line2D.Double(qNear.x, qNear.y, qNew.x, qNew.y);
		
		for(Rectangle2D poly: obstacles) {
			if(crossTest.intersects(poly)) {
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * This method generates a new node based on the node generated with a random (x,y) coordinate.
	 * @param qNear nearest neighboring node
	 * @param qRand node generated with random (x,y)
	 * @param deltaQ step distance
	 * @return
	 */
	public Node new_config(Node qNear, Node qRand, int deltaQ) {
		double distance = euclideanDistance(qNear, qRand);
		
		if(distance <= deltaQ) {
		
			return new Node(qRand.getX(), qRand.getY());
			
		}else {
			
			double theta = Math.atan2(qRand.getY() - qNear.getY() ,  qRand.getX() - qNear.getX());
			return new Node(qNear.getX() + deltaQ * Math.cos(theta), qNear.getY() + deltaQ * Math.sin(theta));		
		}
	}

	
	/**
	 * This method is used in the RRT algorithm and finds the nearest vertex of a randomly generated node on the search space
	 * @param start the start node
	 * @param qRand any randomly generated node
	 * @return nearest Node
	 */
	public Node nearestVertex(Node start, Node qRand) {
	
		double distance = euclideanDistance(start, qRand);
		double possibleCloserDistance;
		Node nearestVert = start;
		
		for(Node vert : nodes) {
				
			possibleCloserDistance = euclideanDistance(vert, qRand);
			
			if(possibleCloserDistance < distance) {
					
				distance = possibleCloserDistance;
				nearestVert = vert;				
			}			
		}		
		return nearestVert;
	}

	
	/**
	 * This method calculates and returns the straight line distance between any two nodes
	 * @param start any start node
	 * @param end any end node
	 * @return the distance
	 */
	public double euclideanDistance(Node start, Node end) {
		return Math.sqrt((end.getX() - start.getX()) * (end.getX() - start.getX()) + (end.getY() - start.getY()) * (end.getY() - start.getY()));	
	}
	
	
	/**
	 * Creates a connection between any two nodes passed through
	 */
	public void addEdge(Node qNear, Node qNew) {
		qNew.setParent(qNear);
		qNear.setChild(qNew);	
	}
	
	
	/**
	 * This method is responsible for creating the final path between the start and goal node
	 * @return goalPath
	 */
	public static Node[] reconstructPath(Node goal) {

		LinkedList<Node> path = new LinkedList<Node>();
		Node [] goalPath;
		
		Node current = nodes.removeLast();
		path.add(goal);
		
		while(current != null) {
			
			path.add(current);
			current = current.getParent();
		}	
		
	    goalPath = new Node[path.size()];
	    return path.toArray(goalPath);
	}

	
	/* This method is responsible for generating obstacles on the screen based on the environment selected.
	 * It takes into consideration the window size, and obstacles are then hard-coded in based on the
	 * selected environment.
	 */
	public void generateObstacles(int windowWidth, int windowHeight, String environmentSelect) {
		
		if(environmentSelect.contentEquals("1")) {
			
			Rectangle2D LeftEye = new Rectangle2D.Double(30, 50, 65, 65);
			obstacles.add(LeftEye);
			
			Rectangle2D RightEye = new Rectangle2D.Double(190, 50, 65, 65);
			obstacles.add(RightEye);
			
			Rectangle2D Nose = new Rectangle2D.Double(105, 125, 85, 100);
			obstacles.add(Nose);

			Rectangle2D LNose = new Rectangle2D.Double(65, 170, 40, 90);
			obstacles.add(LNose);		
			
			Rectangle2D RNose = new Rectangle2D.Double(190, 170, 40, 90);
			obstacles.add(RNose);
			
		}else if(environmentSelect.contentEquals("2")) {
			
			Rectangle2D BottomBigRectangle = new Rectangle2D.Double(10, 40, 350, 25);
			obstacles.add(BottomBigRectangle);
			
			Rectangle2D LongRectangle = new Rectangle2D.Double(50, 100, 350, 25);
			obstacles.add(LongRectangle);
	
			Rectangle2D SmallRectangle = new Rectangle2D.Double(90, 160, 350, 25);
			obstacles.add(SmallRectangle);
	
			Rectangle2D SmallerRectangle = new Rectangle2D.Double(130, 220, 350, 25);
			obstacles.add(SmallerRectangle);
	
			Rectangle2D Rectangle = new Rectangle2D.Double(170, 280, 620, 25);
			obstacles.add(Rectangle);
	
			Rectangle2D AnotherRectangle = new Rectangle2D.Double(640, 40, 350, 25);
			obstacles.add(AnotherRectangle);
			
			Rectangle2D AnotherRectangle1 = new Rectangle2D.Double(600, 100, 350, 25);
			obstacles.add(AnotherRectangle1);
			
			Rectangle2D AnotherRectangle2 = new Rectangle2D.Double(560, 160, 350, 25);
			obstacles.add(AnotherRectangle2);
			
			Rectangle2D AnotherRectangle3 = new Rectangle2D.Double(520, 220, 350, 25);
			obstacles.add(AnotherRectangle3);
			
			Rectangle2D LastOne = new Rectangle2D.Double(300, 340, 360, 25);
			obstacles.add(LastOne);
			
		}else if(environmentSelect.contentEquals("3")) {
			
			Rectangle2D BottomBigRectangle = new Rectangle2D.Double(100, 450, 200, 100);
			obstacles.add(BottomBigRectangle);
			
			Rectangle2D LongRectangle = new Rectangle2D.Double(60, 300, 350, 50);
			obstacles.add(LongRectangle);
	
			Rectangle2D SmallRectangle = new Rectangle2D.Double(50, 100, 200, 150);
			obstacles.add(SmallRectangle);
	
			Rectangle2D SmallerRectangle = new Rectangle2D.Double(300, 50, 100, 100);
			obstacles.add(SmallerRectangle);
	
			Rectangle2D Rectangle = new Rectangle2D.Double(450, 175, 100, 100);
			obstacles.add(Rectangle);
	
			Rectangle2D AnotherRectangle = new Rectangle2D.Double(355, 390, 175, 175);
			obstacles.add(AnotherRectangle);
		}	
	}
}