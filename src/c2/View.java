package c2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;

import javax.swing.JPanel;
import javax.swing.Timer;

public class View extends JPanel{
	
	Model model;
	
	public View(Model model) {
		this.model = model;
		this.setPreferredSize(new Dimension(model.maxS,model.maxS));
		
	}
	
	/**Draws a dot for every particle*/
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		double[] xy = model.getPos();
		g2d.clearRect(0, 0, model.maxS+20, model.maxS+20);
		g.setColor(Color.BLUE);
		g.fillRect(0, 0, model.maxS, model.maxS);
		for(int i=0; i<xy.length; i+=3) {
			if (xy[i+2] ==1) {
				g.setColor(Color.RED);
			}else {
				g.setColor(Color.WHITE);
			}
			g2d.fill(new Ellipse2D.Double(xy[i],xy[i+1],2,2));
		}
		
	}
}
