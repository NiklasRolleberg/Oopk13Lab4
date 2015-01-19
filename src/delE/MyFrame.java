package delE;

import java.awt.Dimension;
import javax.swing.JFrame;

public class MyFrame extends JFrame{
	
	private Model model;
	private View view;
	
	public MyFrame() {
		model = new Model(10000);
		model.setL(2);
		view = new View(model);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setPreferredSize( new Dimension(400,400));
		
		add(view);
		
		pack();
		setVisible(true);
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MyFrame myframe = new MyFrame();

	}
}
