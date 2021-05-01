import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

import javax.swing.JPanel;

/**
 * This class is responsible for drawing the shapes of environment 1 on the JPanel.
 * It also draws the start node, goal node, and final path
 * 
 * @author derrick
 * @version 2-24-2021
 */
public class PathEnvironment_2 extends JPanel {
	
	public void paintComponent( Graphics x ) {
		super.paintComponent( x ); // call superclass's paintComponent

		//start node
		x.setColor(Color.GREEN);
		x.drawOval(50, 500, 5, 5);
		
		//obstacles
		x.setColor(Color.DARK_GRAY);
		int rectX[] = { 100, 100, 300, 300 }; 
		int rectY[] = { 450, 550, 550, 450 }; 
		Polygon rectangle = new Polygon( rectX, rectY, 4);
		x.drawPolygon( rectangle ); 
		
		int triX[] = { 20, 110, 200 }; 
		int triY[] = { 400, 140, 400 }; 
		Polygon giantTriangle = new Polygon( triX, triY, 3);
		x.drawPolygon( giantTriangle ); 
		
		int polyX2[] = { 270, 270, 360, 450 }; 
		int polyY2[] = { 380, 80, 40, 120 }; 
		Polygon giantPolygon = new Polygon( polyX2, polyY2, 4);
		x.drawPolygon( giantPolygon ); 
		
		int triX1[] = { 350, 450, 550 }; 
		int triY1[] = { 400, 240, 400 }; 
		Polygon smallTriangle = new Polygon( triX1, triY1, 3);
		x.drawPolygon( smallTriangle ); 
		
		int triX2[] = { 465, 550, 550 }; 
		int triY2[] = { 220, 360, 100 }; 
		Polygon flipTriangle = new Polygon( triX2, triY2, 3);
		x.drawPolygon( flipTriangle ); 
		
		int rectX1[] = { 50, 50, 200, 200 }; 
		int rectY1[] = { 100, 50, 50, 100 }; 
		Polygon topRectangle = new Polygon( rectX1, rectY1, 4);
		x.drawPolygon( topRectangle ); 
		
		//goal node
		x.setColor(Color.RED);
		x.drawOval(500, 50, 5, 5);		
		
		//final path
		x.setColor(Color.MAGENTA);
		if(!(Main.finalPath ==  null)) {
			for(int i = Main.finalPath.length - 1; i > 0; i--) {
				x.drawLine(Main.finalPath[i].x, Main.finalPath[i].y, Main.finalPath[i-1].x, Main.finalPath[i-1].y);
			}
		}
	}
}