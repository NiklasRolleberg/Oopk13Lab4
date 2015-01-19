package delE;

/**Contains Nst particles*/
public class Model {
	
	/*Number of particles*/
	private int N;
	private Object[] particles;
	/*the distance particles move*/
	static double L;
	
	public Model(int N) {
		this.N=N;
		particles = new Object[N];
		
		for (int i=0; i<N ;i++) {
			particles[i] = new Particle();
		}
	}
	
	/**Update the position for all particles*/
	public void update() {
		for (Object o: particles){
			((Particle) o).move();
		}
	}
	
	/**Returns the postions for all the particles*/
	public double[] getPos() {
		double[] pos = new double[N*2];
		int j = 0;
		int i=0;
		while (i < N)
		{
			double[] t = ((Particle) particles [i]).getPos();
			pos[j] = t[0];
			pos[j+1] = t[1];
			i++;
			j+=2;
		}
		
		return pos;
	}
	
	public void setL(double newL) {
		L = newL;
	}
	
	public static double getL() {
		return L;
	}


	class Particle {
		private double x;
		private double y;
		
		/**Gives the particle a random location*/
		Particle() {
			this(Math.random()*400,Math.random()*400);
		}
		
		/**Gives the particle the position x,y*/
		Particle(double x, double y){
			this.x=x;
			this.y=y;
		}
		
		/**Moves the particle*/
		public void move(){
			double th = Math.random()*2*Math.PI;
			x = x + Model.getL()*Math.cos(th);
			y = y + Model.getL()*Math.sin(th);
		}
		
		public double[] getPos(){
			return new double[] {x,y};
		}
	}
}
