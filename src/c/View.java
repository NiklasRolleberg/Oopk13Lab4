package c;

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
	//ritar en ruta för varje grupp
	public void debugg(Graphics2D g) {
		for (Object gr:model.grupper) {
			
			g.drawRect(((Grupp)gr).xMin, ((Grupp)gr).yMin, ((Grupp)gr).xMax, ((Grupp)gr).yMax);
			//String s = "Id: "+ ((Grupp)gr).id+ " Grupper runtom: "+((Grupp)gr).grupperRunt.toString();
			g.setColor(Color.GREEN);
			int xpos = ((Grupp)gr).xMin+(((Grupp)gr).xMax -((Grupp)gr).xMin)/6;
			int ypos = ((Grupp)gr).yMin+(((Grupp)gr).yMax -((Grupp)gr).yMin)/2;
			
			Object[] around = ((Grupp) gr).getGrupperRunt();
			
			String s ="Grupp "+((Grupp)gr).id;
			String S ="Runt: ";
			if (around != null) {
				for (Object o:around) {
					S+=((Grupp)o).id+", ";
				}
			}
			g.drawChars(s.toCharArray(), 0,s.length(), xpos, ypos-10);
			g.drawChars(S.toCharArray(), 0,S.length(), xpos, ypos+10);
		}
	}
	
	/**Ritar en prick fÃ¶r varje partikel*/
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		int[] xy = model.getPos();
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
			//g2d.drawChars(Integer.toString(xy[i+2]).toCharArray(), 0, Integer.toString(xy[i+2]).toCharArray().length, xy[i],xy[i+1]);
		}
		//debugg(g2d);
	}
}
