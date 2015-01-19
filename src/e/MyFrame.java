package e;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class MyFrame extends JFrame {
	Model model;
	View view;
	Controller controller;
	
	Timer timer;
	
	public MyFrame() {
		model = new Model(10000);
		view = new View(model);
		controller = new Controller(model,view);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		JPanel container = new JPanel();			//http://stackoverflow.com/questions/6325384/adding-multiple-jpanels-to-jframe
		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
		
		container.add(view);
		container.add(controller);
		setPreferredSize(new Dimension(model.maxX, model.maxY+60));
		add(container);
		pack();
		setVisible(true);	
	}
	
	public static void main(String[] args) {
		MyFrame myframe = new MyFrame();
	}
}
