package c;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Controller extends JPanel implements ChangeListener, ActionListener {
	
	JSlider lSlider;
	JSlider deltaSlider;
	Timer timer;
	Model model;
	View view;
	
	/*En tråd*/
	Thread1 MyThread;

	
	/*saker som hör till delen med matalb*/ // http://stackoverflow.com/questions/2885173/java-how-to-create-and-write-to-a-file 
	File file;
	PrintWriter writer;
	boolean canWrite = false; //true = den skriver till filen, false, den skriver inte
	double time = 0; //tiden sedan start
	int sparat = 1;
	int max = 500;
	
	public Controller(Model model, View view) {
		this.model = model;
		this.view = view;
		
		timer = new Timer(500,this);
		timer.start();
		lSlider = new JSlider();
		deltaSlider = new JSlider();
		lSlider.addChangeListener(this);
		deltaSlider.addChangeListener(this);
		add(lSlider);
		add(deltaSlider);
		setPreferredSize(new Dimension(model.maxS,60));	
		
		MyThread = new Thread1(model); MyThread.start();
		
		try {
			writer = new PrintWriter("outfile.txt", "UTF-8");
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFound");
			canWrite = false; //så man inte försöker skiva till en fil som inte finns.
		} catch (UnsupportedEncodingException e) {
			System.out.println("Fel kodning");
			canWrite = false;
		}
	}
	
	/**Skriver partiklarnas postioner till filen*/
	public void writePositions() {
		StringBuilder sb = new StringBuilder();
		int[] pos = model.getPosFast();
		
		/*Skriver tiden*/
		sb.append(Double.toString(time));
		sb.append(",\t");
		
		//String s = Double.toString(time) + ",\t"; 
		
		/*skriver positionerna*/
		for (int i=0; i<pos.length; i+=2) {	
			sb.append(Integer.toString(pos[i]));
			sb.append(",\t");
			sb.append(Integer.toString(pos[i+1]));
			sb.append(",\t");
			//s +=  Integer.toString(pos[i]) + ",\t" + Integer.toString(pos[i+1]) + ",\t";
 		}
		writer.println(sb.toString()); //<----Skriver en rad till filen
		//writer.println(s);
		writer.flush(); //<----Fixade så att den skrev ut hela raden!
	}
	
		
	@Override
	public void stateChanged(ChangeEvent e) {
		if(e.getSource() == lSlider) {
			model.setL(lSlider.getValue()/10);
			repaint();
		}
		if(e.getSource() == deltaSlider) {
			timer.setDelay(deltaSlider.getValue()*10+10);
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
