package delE;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;

import javax.swing.JPanel;
import javax.swing.Timer;

public class View extends JPanel implements ActionListener{
	
	private Timer timer;
	private Model model;
	
	public View(Model model) {
		this.model = model;
		timer = new Timer(200, this);
		timer.start();
	}
	
	public void paint(Graphics g){
		
		double[] pos = model.getPos();
		Graphics2D g2D = (Graphics2D) g;
		g2D.clearRect(0, 0, 400, 400); 
		
		for (int i = 0; i < pos.length-1; i++)
		{
			g2D.fill(new Ellipse2D.Double(pos[i],pos[i+1],1,1));
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		model.update();
		repaint();
		//System.out.println("adasd");
	}
}