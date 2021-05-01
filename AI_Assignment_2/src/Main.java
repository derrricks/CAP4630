import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Scanner;

import javax.swing.*;

/**
 * This class is responsible for creating each of the nodes, and establishing each of their 
 * neighbors which lay in their line of sight. The the Potential Search Algorithm is than implemented 
 * on the Nodes.
 * 
 * There are methods used which pertain to the Potential Search Algorithm such as reconstruchPath and heuristic:
 * 
 * The reconstuctPath method is responsible for creating the final path or goal path of the algorithm
 * 
 * The heuristic method is responsible for calculating the euclidean distance 
 * (straight line distance) between two nodes, which can be any two nodes passed through
 * 
 * Algorithm Source: https://escholarship.org/uc/item/4ct236k1
 * 
 * @author derrick
 * @version 2-24-2021
 */

public class Main extends JPanel {
	
	public static Node[] finalPath;
	
	public static void main( String args[] ) {
		
		Scanner in = new Scanner(System.in);
		
		JFrame environment;
		String envChoice = "";
		int budget; // used for potential search algorithm
		boolean stopFlag = false;

		
		System.out.println("Welcome to the Potential Search Algorithm Visualization");

		while(stopFlag == false) {
			
			System.out.println("Select Enviroment [1] or [2]");
			System.out.println("[End] To Terminate"); 
			envChoice = in.nextLine();
			
			switch(envChoice) {
			case "1":
				
				budget = getUsersBudget();
				buildEnvOne(budget);
				
				environment = new JFrame( "Potential Search Enviroment I" );
				PathEnvironment_1 env1 = new PathEnvironment_1();
				environment.add( env1 ); // add polygonsJPanel to frame
				environment.setSize(600, 600); // set frame size
				environment.setVisible(true); // display frame
				environment.setResizable(false); 		
				break;			
					
			case "2":
				
				budget = getUsersBudget();
				buildEnvTwo(budget);
				
				environment = new JFrame( "Potential Search Enviroment II" );
				PathEnvironment_2 env2 = new PathEnvironment_2();
				environment.add(env2);			
				environment.setSize(600, 600); // set frame size
				environment.setVisible(true); // display frame
				environment.setResizable(false); 	
				break;
			
			default:
				
				if(envChoice.equalsIgnoreCase("End")) { 
					System.exit(0); // used to terminate program
				}
			}
		}
	}
	
	
	/**
	 * Source: https://escholarship.org/uc/item/4ct236k1
	 * @param start node at start position
	 * @param goal node at end position
	 * @param totalCost budget used for algorithm 
	 */
	public static Node[] potentialSearchAlgo(Node start, Node goal, int totalCost) {
		
		int budget = totalCost;
		
		PriorityQueue<Node> open = new PriorityQueue<Node>((new NodeComparator()));
		open.add(start);
		
		ArrayList<Node> closed = new ArrayList<Node>();
	     
        HashMap<Node, Double> gScore = new HashMap<Node, Double>();
        gScore.put(start, 0.0);
        
        double totalG;
        
		Node current;
		while(!open.isEmpty()) {
			current = open.poll();
			closed.add(current);

			LinkedList<Node> neighborList = current.neighbors;
				
			for(Node neighbor: neighborList) {
				
				totalG = gScore.get(current) + heuristic(current, neighbor);
				gScore.put(neighbor, totalG);
				
				
				if((open.contains(neighbor) || closed.contains(neighbor))
						&& gScore.get(neighbor) <= gScore.get(current) + heuristic(current, neighbor)) {
						continue;
				}
					
				gScore.put(neighbor,  gScore.get(current)+ heuristic(current, neighbor));
				
				
				if(gScore.get(neighbor) + heuristic(neighbor, goal) >= budget) {
					continue;
				}
					
				if(neighbor.label.equals("Goal")) {
					System.out.println("Path Found");
					closed.add(neighbor);
					Node[] path = reconstructPath(closed);
					return path;
				}
				
				if(open.contains(neighbor)) {
					neighbor.setU(gScore.get(neighbor));
				}else {
					open.add(neighbor);
				}
			}
		}
		System.out.println("No Path Found! Try Again!");
		return null;
	}

	
	/**
	 * This method returns the user's desired budget or path cost used in the Potential Search Algorithm.
	 * Does not allow for cost < 0
	 * @return cost 
	 */
	public static int getUsersBudget() {
		
		Scanner in = new Scanner(System.in);
		int cost;
		
		do {	
			System.out.println("Enter Maximum Path Cost: ");
			cost = in.nextInt();
			
		} while(cost <= 0);
	
		return cost;
	}
	

	/**
	 * This method reconstructs the final path found by the Potential Search Algorithm
	 * @param closed List used in algorithm of explored nodes
	 * @return goalPath
	 */
    public static Node[] reconstructPath(ArrayList<Node> closed){
        
	    Node current;
	    LinkedList<Node> path = new LinkedList<Node>();
	    Node [] goalPath;
	        
        for(int i = 0; i < closed.size(); i++){
        	
        	current = closed.get(i);
  
            if(current != null) {	
            	path.add(current);
            }
        }
        goalPath = new Node[path.size()];
        return path.toArray(goalPath);  
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
	 * Build environment 1
	 * @param budget algorithm maximum path cost
	 */
	public static void buildEnvOne(int pathCost) {
		
		int budget = pathCost;
		
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
	   	
		finalPath = potentialSearchAlgo(Start, Goal, budget); //700 is weird
	}
	
	
	/**
	 * Build environment 2
	 * @param budget algorithm maximum path cost
	 */
	public static void buildEnvTwo(int budget) {
		
		int pathCost = budget;
		
		Node Start = new Node("Start", 50, 500);
		
		Node B1 = new Node("B1", 100, 450);
		Node B2 = new Node("B2", 100, 550);
		Node B3 = new Node("B3", 300, 550);
		Node B4 = new Node("B4", 300, 450);
		
		Node B5 = new Node("B5", 20, 400);
		Node B6 = new Node("B6", 110, 140);
		Node B7 = new Node("B7", 200, 400);
		
		Node B8 = new Node("B8", 270, 380);
		Node B9 = new Node("B9", 270, 80);
		Node B10 = new Node("B10", 360, 40);
		Node B11 = new Node("B11", 450, 120);
		
		Node B12 = new Node("B12", 350, 400);
		Node B13 = new Node("B13", 450, 240);
		Node B14 = new Node("B14", 550, 400);
		
		Node B15 = new Node("B15", 465, 220);
		Node B16 = new Node("B16", 550, 360);
		Node B17 = new Node("B17", 550, 100);
		
		Node B18 = new Node("B18", 50, 100);
		Node B19 = new Node("B19", 50, 50);
		Node B20 = new Node("B20", 200, 50);
		Node B21 = new Node("B21", 200, 100);
		
		Node Goal = new Node("Goal",500, 50);
		
		Start.neighbors.add(B1);
		Start.neighbors.add(B2);
		Start.neighbors.add(B5);
		
		B1.neighbors.add(Start);
		B1.neighbors.add(B2);
		B1.neighbors.add(B4);
		B1.neighbors.add(B5);
		B1.neighbors.add(B7);
		B1.neighbors.add(B8);
		B1.neighbors.add(B12);
		B1.neighbors.add(B14);
		
		B2.neighbors.add(Start);
		B2.neighbors.add(B1);
		B2.neighbors.add(B3);
		B2.neighbors.add(B5);
	
		B3.neighbors.add(B2);
		B3.neighbors.add(B4);
		B3.neighbors.add(B12);
		B3.neighbors.add(B14);
	
		B4.neighbors.add(B1);
		B4.neighbors.add(B3);
		B4.neighbors.add(B5);
		B4.neighbors.add(B6);
		B4.neighbors.add(B7);
		B4.neighbors.add(B8);
		B4.neighbors.add(B12);
		B4.neighbors.add(B14);
		B4.neighbors.add(B11);
		B4.neighbors.add(Goal);
		
		B5.neighbors.add(Start);
		B5.neighbors.add(B1);
		B5.neighbors.add(B2);
		B5.neighbors.add(B6);
		B5.neighbors.add(B7);
		B5.neighbors.add(B4);
	
		B6.neighbors.add(B5);
		B6.neighbors.add(B7);
		B6.neighbors.add(B18);
		B6.neighbors.add(B21);
		B6.neighbors.add(B8);
		B6.neighbors.add(B9);
		B6.neighbors.add(B4);
		
		B7.neighbors.add(B5);
		B7.neighbors.add(B6);
		B7.neighbors.add(B1);
		B7.neighbors.add(B4);
		B7.neighbors.add(B8);
		B7.neighbors.add(B12);
		B7.neighbors.add(B21);
		B7.neighbors.add(B9);
		
		B8.neighbors.add(B9);
		B8.neighbors.add(B11);
		B8.neighbors.add(B1);
		B8.neighbors.add(B7);
		B8.neighbors.add(B6);
		B8.neighbors.add(B20);
		B8.neighbors.add(B21);
		B8.neighbors.add(B4);
		B8.neighbors.add(B12);
		B8.neighbors.add(B13);
		B8.neighbors.add(B15);
		B8.neighbors.add(B17);
	
		B9.neighbors.add(B8);
		B9.neighbors.add(B10);
		B9.neighbors.add(B7);
		B9.neighbors.add(B6);
		B9.neighbors.add(B21);
		B9.neighbors.add(B20);
	
		B10.neighbors.add(B9);
		B10.neighbors.add(B11);
		B10.neighbors.add(B20);
		B10.neighbors.add(Goal);
		B10.neighbors.add(B17);
	
		B11.neighbors.add(B8);
		B11.neighbors.add(B10);
		B11.neighbors.add(Goal);
		B11.neighbors.add(B17);
		B11.neighbors.add(B15);
		B11.neighbors.add(B13);
		B11.neighbors.add(B12);
		B11.neighbors.add(B4);
	
		B12.neighbors.add(B13);
		B12.neighbors.add(B14);
		B12.neighbors.add(B1);
		B12.neighbors.add(B3);
		B12.neighbors.add(B4);
		B12.neighbors.add(B7);
		B12.neighbors.add(B8);
		B12.neighbors.add(B11);
		B12.neighbors.add(Goal);
		
		B13.neighbors.add(B12);
		B13.neighbors.add(B14);
		B13.neighbors.add(B8);
		B13.neighbors.add(B11);
		B13.neighbors.add(Goal);
		B13.neighbors.add(B15);
		B13.neighbors.add(B16);
	
		B14.neighbors.add(B12);
		B14.neighbors.add(B13);
		B14.neighbors.add(B15);
		B14.neighbors.add(B16);
		B14.neighbors.add(B1);
		B14.neighbors.add(B4);
		B14.neighbors.add(B3);
		
		B15.neighbors.add(B16);
		B15.neighbors.add(B17);
		B15.neighbors.add(B14);
		B15.neighbors.add(B13);
		B15.neighbors.add(Goal);
		B15.neighbors.add(B11);
		B15.neighbors.add(B8);
		
		B16.neighbors.add(B15);
		B16.neighbors.add(B17);
		B16.neighbors.add(B13);
		B16.neighbors.add(B14);
	
		B17.neighbors.add(Goal);
		B17.neighbors.add(B15);
		B17.neighbors.add(B16);
		B17.neighbors.add(B10);
		B17.neighbors.add(B11);
		B17.neighbors.add(B8);
		
		B18.neighbors.add(B19);
		B18.neighbors.add(B21);
		B18.neighbors.add(B6);
		B18.neighbors.add(B5);
	
		B19.neighbors.add(B18);
		B19.neighbors.add(B20);
		B19.neighbors.add(B5);
	
		B20.neighbors.add(B19);
		B20.neighbors.add(B21);
		B20.neighbors.add(B9);
		B20.neighbors.add(B10);
		B20.neighbors.add(B8);
	
		B21.neighbors.add(B18);
		B21.neighbors.add(B20);
		B21.neighbors.add(B9);
		B21.neighbors.add(B8);
		B21.neighbors.add(B6);
		B21.neighbors.add(B7);
	
		Goal.neighbors.add(B10);
		Goal.neighbors.add(B11);
		Goal.neighbors.add(B12);
		Goal.neighbors.add(B13);
		Goal.neighbors.add(B15);
		Goal.neighbors.add(B17);
		Goal.neighbors.add(B4);
	
		finalPath = potentialSearchAlgo(Start, Goal, pathCost);
	}			
}