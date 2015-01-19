package exempel;

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

    /* View måste ha tillgång till information från modell.
     * I denna version tar vi in en referens till modellen i 
     * konstruktorn, men vi skulle också kunna tänka oss att 
     * göra View till en observatör, och få in referensen som
     * argument från notify()-metoden
     */
    private Model myModel;
    
    public View(Model modelIn){

	myModel = modelIn;
	setPreferredSize(new Dimension((int)(myModel.getXMax()),
				       (int)(myModel.getYMax())));


	/* Här skapar vi en JFrame och lägger in panelen i.
	 */
	JFrame myFrame = new JFrame();
	myFrame.add(this);
	myFrame.pack();
	myFrame.setVisible(true);

    }





    public void paint(Graphics g){

	Graphics2D g2D = (Graphics2D) g;

	/* clearRect() tömmer en rektangel av angiven storlek.
	 * Här sätter vi storleken till att vara hela panelen,
	 * och tömmer alltså helea rit-ytan i varje uppdatering.
	 */
	g2D.clearRect(0, 0, (int)(myModel.getXMax()), (int)(myModel.getYMax()));
	g2D.fill(new Ellipse2D.Double(myModel.getX(),myModel.getY(),10,10));

    }




}