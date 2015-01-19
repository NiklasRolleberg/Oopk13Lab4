package blandat;
/* Detta är controller-klassen för MVC-versionen av studsbollskoden som vi 
 * skrev tillsammans på övningen den 27/11 2013.
 *
 * Jag har lagt till lite kommentarer, i övrigt är koden 
 * oförändrad från övningen.
 *
 *     - Christian
 */

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.TextField;
import java.awt.event.*;

public class Controller extends JPanel implements ActionListener,ChangeListener{
    

    /* Controller måste känna till model och view, och det
     * lättaste är att låta controller skapa de andra.
     */
    private Timer myTimer;
    private Model myModel;
    private View myView;
    private JSlider lSlider;
	private JSlider deltaSlider;


    public Controller(Model model,View view){
	myModel = model;
	myView = view;
	myTimer = new Timer(1000,this);
	myTimer.start();

	lSlider = new JSlider();
	deltaSlider = new JSlider();
	lSlider.addChangeListener(this);
	deltaSlider.addChangeListener(this);
	add(lSlider);
	add(deltaSlider);

    }
    /* Denna metod anropas regelbundet av Timer:n, och Controller 
     * styr vad som ska göras i varje tidssteg.
     */
    public void actionPerformed(ActionEvent e){
	myModel.update();
	myView.repaint();
    }
	@Override
	public void stateChanged(ChangeEvent e) {
		if(e.getSource() == lSlider) {
			//System.out.println(lSlider.getValue());
			myModel.setL(lSlider.getValue()/10);
		}
		if(e.getSource() == deltaSlider) {
			//System.out.println(deltaSlider.getValue());
			//myView.dt = deltaSlider.getValue()*10+50;
			myTimer.setDelay(deltaSlider.getValue()*10+10);
			myTimer.restart();
			//myView.repaint();
		}
		
	}

}