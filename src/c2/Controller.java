package c2;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JToolBar;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Controller extends JPanel implements ChangeListener, ActionListener {
	
	JSlider lSlider;
	JSlider deltaSlider;
	Timer timer;
	Model model;
	View view;
	
	/*things to the matlab part*/ // http://stackoverflow.com/questions/2885173/java-how-to-create-and-write-to-a-file 
	File file;
	PrintWriter writer;
	boolean canWrite = false; //writes to the file if (true)
	double time = 0; //time since start
	int sparat = 1;
	int max = 500;
	JButton button;
	/**Creates the controller
	 * @param model the Model Object
	 * @param view	the View Object 
	 */
	public Controller(Model model, View view) {
		this.model = model;
		this.view = view;
		
		timer = new Timer(500,this);
		timer.start();
		lSlider = new JSlider();
		deltaSlider = new JSlider();
		lSlider.addChangeListener(this);
		deltaSlider.addChangeListener(this);
		button = new JButton("Start recording");
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if (canWrite) {
					canWrite = false;
					button.setText("Start recording");
				}
				else{
					canWrite = true;
					sparat = 1;
					button.setText("Stop recording");
				}
				
			}
		});
		
		super.setLayout(new BorderLayout());
		add(deltaSlider, BorderLayout.NORTH);
		add(button, BorderLayout.CENTER);
		add(lSlider, BorderLayout.SOUTH);
		setPreferredSize(new Dimension(model.maxS,60));	
		
		try {
			writer = new PrintWriter("outfile.txt", "UTF-8");
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFound");
			canWrite = false;
		} catch (UnsupportedEncodingException e) {
			System.out.println("Fel kodning");
			canWrite = false;
		}
	}
	
	/**writes the particles positions to the file*/
	public void writePositions() {
		StringBuilder sb = new StringBuilder();
		double[] pos = model.getPosFast();
		
		/*Writes the time*/
		sb.append(Double.toString(time));
		sb.append(",\t");
		
		/*writes the positions*/
		for (int i=0; i < pos.length-1; i+=2) {	
			sb.append(Double.toString(pos[i]));
			sb.append(",\t");
			sb.append(Double.toString(pos[i+1]));
			sb.append(",\t");
 		}
		writer.println(sb.toString());
		writer.flush();
	}
	
		
	@Override
	public void stateChanged(ChangeEvent e) {
		if(e.getSource() == lSlider) {
			model.setL(lSlider.getValue()/10);
			repaint();
		}
		if(e.getSource() == deltaSlider) {
			timer.setDelay(deltaSlider.getValue()*10);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		model.update();
		view.repaint();
		if (canWrite){
			writePositions();
		}
		time += (double)timer.getDelay()/1000;
		sparat++;
		if (sparat > max) {
			canWrite = false;
		}
	}
}
