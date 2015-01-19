package delE;

import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Controler extends JPanel implements ChangeListener{
	JSlider js1;
	JSlider js2;
	
	Model model;
	View view;
	
	public Controler(Model model, View view){
		this.model = model;
		this.view = view;
		
		js1 = new JSlider();
		js2 = new JSlider();
		
		js1.addChangeListener(this);
		js2.addChangeListener(this);
		
		add(js1);
		add(js2);
		
	}
	@Override
	public void stateChanged(ChangeEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
