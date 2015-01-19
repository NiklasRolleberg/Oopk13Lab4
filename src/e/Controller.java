package e;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Controller extends JPanel implements ChangeListener, ActionListener {
	
	JSlider lSlider;
	JSlider deltaSlider;
	Timer timer;
	Model myModel;
	View myView;
	
	public Controller(Model model, View view) {
		this.myModel = model;
		this.myView = view;
		
		timer = new Timer(500,this);
		timer.start();
		lSlider = new JSlider();
		deltaSlider = new JSlider();
		lSlider.addChangeListener(this);
		deltaSlider.addChangeListener(this);
		
		add(lSlider);
		add(deltaSlider);
		//setPreferredSize(new Dimension(model.maxX,60));	
	}
		
	@Override
	public void stateChanged(ChangeEvent e) {
		if(e.getSource() == lSlider) {
			//System.out.println(lSlider.getValue());
			myModel.setL(lSlider.getValue()/10);
		}
		if(e.getSource() == deltaSlider) {
			//System.out.println(deltaSlider.getValue());
			myView.dt = deltaSlider.getValue()*10+50;
			myView.repaint();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		myModel.update();
		myView.repaint();
	}
}
