package exempel;
/* Detta är modell-klassen för MVC-versionen av studsbollskoden som vi 
 * skrev tillsammans på övningen den 27/11 2013.
 *
 * Jag har lagt till lite kommentarer, i övrigt är koden 
 * oförändrad från övningen.
 *
 *     - Christian
 */

public class Model{

    private double x, y, xv, yv, g, dt;
    private int xMin, xMax, yMin, yMax;

    /* Konstruktorn sätter startvärden på alla variabler.
     * Detta skulle kunna göras i fältdeklarationerna i stället
     */
    public Model(){
	x = 100;
	y = 100;
	xMin = 0;
	xMax = 500;
	yMin = 0;
	yMax = 500;
	g = 98.2;
	dt = 0.01;
	xv = 20;
	yv = 0;
    }

    /* Publika getters för alla fält som behövs i View.
     * Man skulle kunna lägga in konverteringen till int här
     * i stället för i View.
     */
    public double getX(){
	return x;
    }

    public double getY(){
	return y;
    }

    public double getXMax(){
	return xMax;
    }

    public double getYMax(){
	return yMax;
    }

    public double getDT(){
	return dt;
    }


    /* Denna metod anropas av Controller, och innehåller diff-ekvationerna
     * för boll-fysiken.
     */
    public void executeTimeStep(){
	x += xv*dt; 
	y += yv*dt;
	yv += g*dt;
	
	if(x < xMin || x > xMax){
	    xv = -xv;
	}
	if(y < yMin || y > yMax){
	    yv = -yv;
	}
    }


}