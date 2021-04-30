import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.util.concurrent.TimeUnit;

import javax.swing.JPanel;

/**
 * This class is responsible for drawing on the JPanel. This includes the shapes and final path
 * 
 * @author derrick
 * @version 2-14-2021
 */
public class shapeEnvironment extends JPanel {
			
	public void paintComponent( Graphics g ) {
		super.paintComponent( g ); // call superclass's paintComponent
		
		//start node
		g.setColor(Color.GREEN);
		g.drawOval(25, 380, 5, 5);
		
		g.setColor(Color.DARK_GRAY);
		int rectX[] = { 50, 50, 300, 300 }; 
		int rectY[] = { 350, 280, 280, 350 }; 
		Polygon rectangle = new Polygon( rectX, rectY, 4);
		g.drawPolygon( rectangle ); 
	
		int polyX[] = { 450, 450, 490, 530, 530, 490 }; 
		int polyY[] = { 300, 250, 230, 250, 300, 330 }; 
		Polygon polygon = new Polygon( polyX, polyY, 6);
		g.drawPolygon( polygon ); 
		
		int polyX1[] = { 50, 125, 200, 120, 40 }; 
		int polyY1[] = { 200, 240, 180, 100, 150 }; 
		Polygon polygon1 = new Polygon( polyX1, polyY1, 5);
		g.drawPolygon( polygon1 ); 
		
		int triX[] = { 210, 270, 240 }; 
		int triY[] = { 240, 240, 150 }; 
		Polygon triangle = new Polygon( triX, triY, 3);
		g.drawPolygon( triangle ); 
		
		int triX1[] = { 320, 360, 420 }; 
		int triY2[] = { 200, 325, 290 }; 
		Polygon triangle1 = new Polygon( triX1, triY2, 3);
		g.drawPolygon( triangle1 ); 
		
		int polyX2[] = { 270, 270, 330, 380 }; 
		int polyY2[] = { 180, 80, 60, 120 }; 
		Polygon polygon2 = new Polygon( polyX2, polyY2, 4);
		g.drawPolygon( polygon2 ); 
		
		int rectX1[] = { 400, 480, 480, 400 }; 
		int rectY1[] = { 80, 80, 220, 220 }; 
		Polygon rectangle1 = new Polygon( rectX1, rectY1, 4);
		g.drawPolygon( rectangle1 ); 	
		
		int polyX3[] = { 500, 530, 560, 540 }; 
		int polyY3[] = { 100, 70, 110, 230 }; 
		Polygon polygon3 = new Polygon( polyX3, polyY3, 4);
		g.drawPolygon( polygon3 ); 	

		//goal node
		g.setColor(Color.RED);
		g.drawOval(560, 75, 5, 5);		
		
		g.setColor(Color.MAGENTA);
		if(!(Main.finalPath == null)) {
			for(int i = 0; i < Main.finalPath.length - 1; i++) {
				if(Main.finalPath[i] == null) continue;
				
				g.drawLine(Main.finalPath[i].getX(), Main.finalPath[i].getY(), Main.finalPath[i+1].getX(), Main.finalPath[i+1].getY());
			}
		}
	}
}