package exempel;
/* Detta är controller-klassen för MVC-versionen av studsbollskoden som vi 
 * skrev tillsammans på övningen den 27/11 2013.
 *
 * Jag har lagt till lite kommentarer, i övrigt är koden 
 * oförändrad från övningen.
 *
 *     - Christian
 */

import javax.swing.*;
import java.awt.event.*;

public class Controller implements ActionListener{
    

    /* Controller måste känna till model och view, och det
     * lättaste är att låta controller skapa de andra.
     */
    private Timer myTimer;
    private Model myModel;
    private View myView;

    /* Eftersom controller skapar de andra blir detta en naturlig
     * ingång till programmet, alltså hamnar main() här.
     */
    public static void main(String[] args){
	Controller myController = new Controller();
    }


    /* Controller skapar de andra objekten och kopplar ihop 
     * strukturen.
     */
    public Controller(){
	myModel = new Model();
	myView = new View(myModel);
	myTimer = new Timer((int)(myModel.getDT()*1000),this);
	myTimer.start();

    }

    /* Denna metod anropas regelbundet av Timer:n, och Controller 
     * styr vad som ska göras i varje tidssteg.
     */
    public void actionPerformed(ActionEvent e){
	myModel.executeTimeStep();
	myView.repaint();
    }

}