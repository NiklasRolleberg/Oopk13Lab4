package blandat;

/* Detta är view-klassen för MVC-versionen av studsbollskoden som vi 
 * skrev tillsammans på övningen den 27/11 2013.
 *
 * Jag har lagt till lite kommentarer, i övrigt är koden 
 * oförändrad från övningen.
 *
 *     - Christian
 */

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;


public class View extends JPanel{

    private Model myModel;
    
    public View(Model modelIn){

	myModel = modelIn;
	setPreferredSize(new Dimension(500,500));
    }





	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		int[] xy = myModel.getPos();
		g2d.clearRect(0, 0, 500, 500);
		for(int i=0; i<xy.length; i+=2) {
			g2d.fill(new Ellipse2D.Double(xy[i],xy[i+1],2,2));
		}
	}
}