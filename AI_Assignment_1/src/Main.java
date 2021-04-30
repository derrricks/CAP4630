/**
 * This class is responsible for creating each of the nodes, assembling each of their 
 * neighbors which lay in their line of sight, and then runs the actual A* algorithm on 
 * the nodes. There is also the PolygonJPanel class which is responsible for drawing 
 * the start node, goal node, final path and each of the polygons contained in the "maze." 
 * 
 * There are methods which pertain to the A* Algorithm such as reconstruchPath and heuristic:
 * 
 * The reconstuctPath method is responsible for taking in the cameFrom map and current node to produce 
 * the goal path, which is then used for representation in the PolygonJPanel class. 
 * 
 * The heuristic method is responsible for calculating the euclidean distance 
 * (straight line distance) between two nodes, which can be any two nodes passed through
 * 
 * @author derrick
 * @version 2-14-2021
 */
import java.awt.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.concurrent.TimeUnit;

import javax.swing.*;

public class Main extends JPanel {

	public static JFrame frame = new JFrame( "A* Algorithm" );
	public static Node[] finalPath;

	public static void main( String args[] ) throws InterruptedException {

		
		shapeEnvironment polygonsJPanel = new shapeEnvironment();
		frame.add( polygonsJPanel ); // add polygonsJPanel to frame
		frame.setSize(600, 600); // set frame size
		frame.setVisible(true); // display frame
		frame.setResizable(false); 
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

		
		//start node
    	Node Start = new Node("Start", 25, 380);
		
		//first shape
		Node A1 = new Node("1", 50,350);
		Node A2 = new Node("2", 50,280);
		Node A3 = new Node("3", 300,280);
		Node A4 = new Node("4", 300,350);
		
		//second shape
		Node A5 = new Node("5", 450,300);
		Node A6 = new Node("6", 450,250);
		Node A7 = new Node("7", 490,230);
		Node A8 = new Node("8", 530,250);
		Node A9 = new Node("9", 530,300);
		Node A10 = new Node("10", 490,330);

		//third shape
		Node A11 = new Node("11", 50, 200);
		Node A12 = new Node("12", 125, 240);
		Node A13 = new Node("13", 200, 180);
		Node A14 = new Node("14", 120, 100);
		Node A15 = new Node("15", 40, 150);

		//fourth shape
		Node A16 = new Node("16", 210, 240);
		Node A17 = new Node("17", 270, 240);
		Node A18 = new Node("18", 240, 150);

		//fifth shape 
		Node A19 = new Node("19", 320, 200);
		Node A20 = new Node("20", 360, 325);
		Node A21 = new Node("21", 420, 290);
		
		//sixth shape
		Node A22 = new Node("22", 270, 180);
		Node A23 = new Node("23", 270, 80);
		Node A24 = new Node("24", 330, 60);
		Node A25 = new Node("25", 380, 120);
		
		//seventh shape
		Node A26 = new Node("26", 400, 80);
		Node A27 = new Node("27", 480, 80);
		Node A28 = new Node("28", 480, 220);
		Node A29 = new Node("29", 400, 220);

		//eighth shape
		Node A30 = new Node("30", 500, 100);
		Node A31 = new Node("31", 530, 70);
		Node A32 = new Node("32", 560, 110);
		Node A33 = new Node("33", 540, 230);
		
		//goal node
		Node Goal = new Node("Goal", 560, 75);
		
		//hard coding the neighboring nodes
    	Start.neighbors.add(A1);
    	Start.neighbors.add(A2);
    	Start.neighbors.add(A4);
    	Start.neighbors.add(A11);
    	Start.neighbors.add(A15);
    
    	A1.neighbors.add(Start);
    	A1.neighbors.add(A2);
    	A1.neighbors.add(A4);
    	
    	A2.neighbors.add(A1);
    	A2.neighbors.add(A15);
    	A2.neighbors.add(A11);
    	A2.neighbors.add(A12);
    	A2.neighbors.add(A16);
    	A2.neighbors.add(A17);
    	A2.neighbors.add(A3);
    	
    	A3.neighbors.add(A2);
    	A3.neighbors.add(A4);
    	A3.neighbors.add(A12);
    	A3.neighbors.add(A16);
    	A3.neighbors.add(A17);
    	A3.neighbors.add(A19);
    	A3.neighbors.add(A20);
    	A3.neighbors.add(A22);
    	
    	A4.neighbors.add(A1);
    	A4.neighbors.add(A3);
    	A4.neighbors.add(A5);
    	A4.neighbors.add(A19);
    	A4.neighbors.add(A20);
    	A4.neighbors.add(A10);
    	
    	A5.neighbors.add(A10);
    	A5.neighbors.add(A6);
    	A5.neighbors.add(A19);
    	A5.neighbors.add(A20);
    	A5.neighbors.add(A21);
    	A5.neighbors.add(A4);
    	A5.neighbors.add(A29);
    	
    	A6.neighbors.add(A5);
    	A6.neighbors.add(A7);
    	A6.neighbors.add(A28);
    	A6.neighbors.add(A29);
    	A6.neighbors.add(A19);
    	A6.neighbors.add(A21);
    	
    	A7.neighbors.add(A6);
    	A7.neighbors.add(A8);
    	A7.neighbors.add(A33);
    	A7.neighbors.add(A27);
    	A7.neighbors.add(A28);
    	A7.neighbors.add(A29);
    	A7.neighbors.add(A30);

    	A8.neighbors.add(A33);
    	A8.neighbors.add(A7);
    	A8.neighbors.add(A9);
    	A8.neighbors.add(A27);
    	A8.neighbors.add(A30);
    	
    	A9.neighbors.add(A8);
    	A9.neighbors.add(A10);
    	A9.neighbors.add(A32);
    	A9.neighbors.add(A33);
    	
    	A10.neighbors.add(A9);
    	A10.neighbors.add(A5);
    	A10.neighbors.add(A21);
    	A10.neighbors.add(A20);
    	A10.neighbors.add(A4);

    	A11.neighbors.add(A2);
    	A11.neighbors.add(A12);
    	A11.neighbors.add(A15);

    	A12.neighbors.add(A11);
    	A12.neighbors.add(A13);
    	A12.neighbors.add(A2);
    	A12.neighbors.add(A3);
    	A12.neighbors.add(A16);
    	
    	A13.neighbors.add(A12);
    	A13.neighbors.add(A14);
    	A13.neighbors.add(A18);
    	A13.neighbors.add(A16);

    	A14.neighbors.add(A13);
    	A14.neighbors.add(A15);
    	A14.neighbors.add(A18);
    	A14.neighbors.add(A23);
    	A14.neighbors.add(A24);
    	
    	A15.neighbors.add(A2);
    	A15.neighbors.add(A11);
    	A15.neighbors.add(A14);
    	
    	A16.neighbors.add(A12);
    	A16.neighbors.add(A2);
    	A16.neighbors.add(A17);
    	A16.neighbors.add(A18);
    	A16.neighbors.add(A13);
    	A16.neighbors.add(A3);
    	
    	A17.neighbors.add(A16);
    	A17.neighbors.add(A18);
    	A17.neighbors.add(A22);
    	A17.neighbors.add(A19);
    	A17.neighbors.add(A20);
    	A17.neighbors.add(A2);
    	A17.neighbors.add(A3);
    	A17.neighbors.add(A25);
    	
    	A18.neighbors.add(A16);
    	A18.neighbors.add(A17);
    	A18.neighbors.add(A13);
    	A18.neighbors.add(A14);
    	A18.neighbors.add(A23);
    	A18.neighbors.add(A22);
    	A18.neighbors.add(A3);
    	
    	A19.neighbors.add(A4);
    	A19.neighbors.add(A3);
    	A19.neighbors.add(A17);
    	A19.neighbors.add(A22);
    	A19.neighbors.add(A25);
    	A19.neighbors.add(A29);
    	A19.neighbors.add(A6);
    	A19.neighbors.add(A5);
    	A19.neighbors.add(A20);
    	A19.neighbors.add(A21);
    	
    	A20.neighbors.add(A4);
    	A20.neighbors.add(A3);
    	A20.neighbors.add(A17);
    	A20.neighbors.add(A22);
    	A20.neighbors.add(A19);
    	A20.neighbors.add(A21);
    	A20.neighbors.add(A5);
    	A20.neighbors.add(A10);
    	
    	A21.neighbors.add(A19);
    	A21.neighbors.add(A20);
    	A21.neighbors.add(A5);
    	A21.neighbors.add(A10);
    	A21.neighbors.add(A6);
    	A21.neighbors.add(A29);
    	
    	A22.neighbors.add(A3);
    	A22.neighbors.add(A17);
    	A22.neighbors.add(A18);
    	A22.neighbors.add(A23);
    	A22.neighbors.add(A25);
    	A22.neighbors.add(A19);
    	A22.neighbors.add(A29);
    	A22.neighbors.add(A20);

    	A23.neighbors.add(A22);
    	A23.neighbors.add(A24);
    	A23.neighbors.add(A18);
    	A23.neighbors.add(A13);
    	A23.neighbors.add(A14);
    	
    	A24.neighbors.add(A23);
    	A24.neighbors.add(A25);
    	A24.neighbors.add(A26);
    	A24.neighbors.add(A27);
    	A24.neighbors.add(A31);
    	A24.neighbors.add(A14);

    	A25.neighbors.add(A22);
    	A25.neighbors.add(A24);
    	A25.neighbors.add(A26);
    	A25.neighbors.add(A29);
    	A25.neighbors.add(A19);
    	A25.neighbors.add(A17);
    	
    	A26.neighbors.add(A29);
    	A26.neighbors.add(A27);
    	A26.neighbors.add(A24);
    	A26.neighbors.add(A25);

    	A27.neighbors.add(A26);
    	A27.neighbors.add(A28);
    	A27.neighbors.add(A30);
    	A27.neighbors.add(A31);

    	A28.neighbors.add(A27);
    	A28.neighbors.add(A29);
    	A28.neighbors.add(A7);
    	A28.neighbors.add(A6);
    	A28.neighbors.add(A33);
    	A28.neighbors.add(A30);
    	
    	A29.neighbors.add(A26);
    	A29.neighbors.add(A28);
    	A29.neighbors.add(A25);
    	A29.neighbors.add(A22);
    	A29.neighbors.add(A19);
    	A29.neighbors.add(A21);
    	A29.neighbors.add(A5);
    	A29.neighbors.add(A6);
    	A29.neighbors.add(A7);
    	
    	A30.neighbors.add(A31);
    	A30.neighbors.add(A33);
    	A30.neighbors.add(A27);
    	A30.neighbors.add(A28);
    	A30.neighbors.add(A7);    	
    	A30.neighbors.add(A8);

    	A31.neighbors.add(A30);
    	A31.neighbors.add(A32);
    	A31.neighbors.add(Goal);
    	A31.neighbors.add(A26);
    	A31.neighbors.add(A27);
    	
    	A32.neighbors.add(A31);
    	A32.neighbors.add(A33);
    	A32.neighbors.add(Goal);
    	
    	A33.neighbors.add(A30);
    	A33.neighbors.add(A32);
    	A33.neighbors.add(A8);
    	A33.neighbors.add(A9);
    	A33.neighbors.add(A7);
    	A33.neighbors.add(A28);

    	Goal.neighbors.add(A31);
    	Goal.neighbors.add(A32);

    	
    	finalPath = aStarAlgorithm(Start, Goal);
    	
    	for(int i = 0; i< finalPath.length; i++) {
    		System.out.println(finalPath[i].label);
    	}
    	 
	}	
	
	/**
	 * This method implements the A* algorithm
	 * Source: https://en.wikipedia.org/wiki/A*_search_algorithm#:~:text=A*%20is%20an%20informed%20search,shortest%20time%2C%20etc.).
	 * @param start given start node
	 * @param goal given end node
	 * @return
	 */
	public static Node[] aStarAlgorithm(Node start, Node goal) throws InterruptedException {
		
		PriorityQueue<Node> openSet = new PriorityQueue<Node>((new NodeComparator()));
		openSet.add(start);
		
        HashMap<Node, Node> cameFrom = new HashMap<Node, Node>();
        
        HashMap<Node, Double> gScore = new HashMap<Node, Double>();
        gScore.put(start, 0.0);

        HashMap<Node, Double> fScore = new HashMap<Node, Double>();
        fScore.put(start, heuristic(start, goal));      
     
        
		Node current;
		while(!openSet.isEmpty()) {
			current = openSet.element();
			
			if(current.label.equals("Goal")) {
				System.out.println("Path Found!");
				Node[] path = reconstructPath(cameFrom, current);
				return path;
			}

			openSet.remove(current);
			LinkedList<Node> neighborList = current.neighbors;
				
			for(Node neighbor: neighborList) {
				double totalG = gScore.get(current) + heuristic(current, neighbor);
				
				if((gScore.containsKey(neighbor) == false) || totalG < gScore.getOrDefault(neighbor, Double.MIN_VALUE)){
					
					cameFrom.put(neighbor, current);
					gScore.put(neighbor, totalG);
					fScore.put(neighbor, gScore.get(neighbor) + heuristic(neighbor, goal));	
					
					neighbor.f = fScore.get(neighbor);
					if(!openSet.contains(neighbor)) {
						openSet.add(neighbor);
					}
				}	
			}
		}
		System.out.println("No Path Found!");
		return null;
	}
	
	
	/**
	 * Euclidean distance function implemented
	 * @param start given start node
	 * @param end given end node
	 * @return distance between two given nodes
	 */
	public static double heuristic(Node a, Node goal) {
		return Math.sqrt((goal.x - a.x) * (goal.x - a.x) + (goal.y - a.y) * (goal.y - a.y));
	}
	
	
	/**
	 * This method is used to reconstruct the final path (or goal path) found by the A* Algorithm
	 * @param cameFrom a map of nodes tracking each neighbor
	 * @param current acts as the goal node when passed through
	 * @return goalPath
	 */
    public static Node[] reconstructPath(HashMap<Node, Node> cameFrom, Node current){
       
        LinkedList<Node> path = new LinkedList<Node>();
        Node [] goalPath;
        
        path.add(current);
        for(int i = 0; i < cameFrom.size(); i++){
            current = cameFrom.get(current);
            if(current != null) {
            	path.add(current);
            }
        }
        goalPath = new Node[path.size()];
        return path.toArray(goalPath);
    }
}