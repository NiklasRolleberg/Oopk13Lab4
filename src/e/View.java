package e;

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
	static public int dt = 500;
	public View(Model model) {
		this.model = model;
		this.setPreferredSize(new Dimension(model.maxX,model.maxY));
	}
	
	/**Ritar en prick för varje partikel*/
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		int[] xy = model.getPos();
		g2d.clearRect(0, 0, model.maxX, model.maxY);
		for(int i=0; i<xy.length; i+=2) {
			g2d.fill(new Ellipse2D.Double(xy[i],xy[i+1],2,2));
		}
	}
}
