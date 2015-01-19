package e;

public class Model {
	static double L = 1;
	public static int maxX = 400;
	public static int maxY = 400;
	Object particles[];

	public Model(int antalPartiklar) {
		//skapa massa partiklar
		particles = new Object[antalPartiklar];
		for(int i=0; i<antalPartiklar; i++) {
			particles[i] = new Particle();
		}
	}
	
	/**Sätter ett värde på L*/
	public void setL(double L) {
		this.L = L;
	}
	/**retunerar värdet på L*/
	public double getL() {
		return L;
	}
	
	/** retunerar alla partiklars positioner*/
	public int[] getPos() {
		int positioner[] = new int[particles.length*2];
		
		
		int i=0 ,j=0;
		while (i<particles.length) {
			int temp[] = ((Particle) particles[i]).getPos(); //TYPECASTING!!!!
			positioner[j] = temp[0];
			positioner[j+1] = temp[1];
			j+=2;
			i++;
		}
		return positioner;
	}
	
	/**uppdaterar positionen för alla partiklar*/ 
	public void update() {
		for (Object o: particles) {
			((Particle) o).updatePos();
		}
	}
	
	
	class Particle {
		double x;
		double y;
		
		Particle() {
			this(maxX*Math.random(),maxY*Math.random());   //400 är JFramens storlek
		}
		
		Particle(double x, double y) {
			this.x = x;
			this.y = y;
		}
		
		int[] getPos() {
			int a[] = new int[2];
			a[0] = (int)x; a[1] = (int)y;
			return a;
		}
		
		
		public void updatePos() {
			double fi = 2*Math.PI*Math.random();
			x = x + L*Math.cos(fi); 
			y = y + L*Math.sin(fi); 
		}
	}

}
