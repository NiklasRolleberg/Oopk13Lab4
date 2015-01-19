package c2;

public class Model {
	
	static double L = 1,dt =1;
	public static int maxS = 500;
	Object particles[];
	byte[][] matrix;
	double[] pariclePositons; 
	
	/**Creates particles
	 * @param numberOfParticles the number of particles*/
	public Model(int numberOfParticles) {
		matrix = new byte[maxS][maxS];		
		particles = new Object[numberOfParticles];
		for(int i=0; i<numberOfParticles; i++) {
			particles[i] = new Particle();
		}
		pariclePositons = new double[numberOfParticles*3]; //[X,Y,color]
	}
	
	/**Sets a value on L
	 * @param L the new L value*/
	public void setL(double L) {
		this.L = L;
	}
	
	/**Returns the value of L*/
	public double getL() {
		return L;
	}
	
	/**Returns every particle's positions in an array*/
	public double[] getPos() {
		int i=0 ,j=0;
		while (i<particles.length) {
			double temp[] = ((Particle) particles[i]).getPos(); //TYPECASTING!!!!
			pariclePositons[j] = temp[0];
			pariclePositons[j+1] = temp[1];
			pariclePositons[j+2] = temp[2];
			j+=3;
			i++;
		}
		return pariclePositons;
	}
	
	/**Returns an array faster than getPos*/
	public double[] getPosFast() {
		return pariclePositons;
	}
	
	/**Updates positions for every particles*/ 
	public void update() {
		for (Object o: particles) {
			((Particle) o).updatePos();	//TYPECASTING!!!
		}
	}
	
	/**Sets some ones in the matrix
	 * @param X x-position
	 * @param Y y-position*/
	public void addPosAround(double X, double Y) {
		int x = (int)X;
		int y = (int)Y;
		
		try{matrix[x][y] = 1;
		}catch (ArrayIndexOutOfBoundsException A){}
		
		try{matrix[x+1][y] = 1;
		}catch (ArrayIndexOutOfBoundsException A){}
		
		try{matrix[x+1][y] = 1;
		}catch (ArrayIndexOutOfBoundsException A){}
		
		try{matrix[x-1][y] = 1;
		}catch (ArrayIndexOutOfBoundsException A){}
		
		try{matrix[x-1][y] = 1;
		}catch (ArrayIndexOutOfBoundsException A){}
		
		try{matrix[x][y+1] = 1;
		}catch (ArrayIndexOutOfBoundsException A){}
		
		try{matrix[x][y+1] = 1;
		}catch (ArrayIndexOutOfBoundsException A){}
		
		try{matrix[x][y-1] = 1;
		}catch (ArrayIndexOutOfBoundsException A){}
		
		try{matrix[x][y-1] = 1;
		}catch (ArrayIndexOutOfBoundsException A){}
		
		try{matrix[x+1][y+1] = 1;
		}catch (ArrayIndexOutOfBoundsException A){}
		
		try{matrix[x+1][y-1] = 1;
		}catch (ArrayIndexOutOfBoundsException A){}
		
		try{matrix[x-1][y+1] = 1;
		}catch (ArrayIndexOutOfBoundsException A){}
		
		try{matrix[x-1][y-1] = 1;
		}catch (ArrayIndexOutOfBoundsException A){}	
	}
	
	class Particle {
		double x;
		double y;
		boolean isStuck = false;
		
		/**Creates a particle with random position*/
		Particle() {
			this(maxS*Math.random(),maxS*Math.random());  
		}

		/**Creates a particle at the position (x,y)
		 * 
		 * @param x x-position
		 * @param y y-position
		 */
		Particle(double x, double y) {
			this.x = x;
			this.y = y;
		}

		/**Gets an array with [x,y,1] if the particle is stuck,
		 * and [x,y,0] if the particle is free*/
		double[] getPos() {
			double[] a = new double[3];
			a[0] = x; a[1] = y;
			if (isStuck) {
				a[2] = 1;
			}
			else{
				a[2] = 0;
			}
			return a;
		}
		
		/**Updates the position if the particle is free*/
		public void updatePos() {
			if (!isStuck){
				double fi = 2*Math.PI*Math.random();
				x = x + L*Math.cos(fi); 
				y = y + L*Math.sin(fi);
				
				if (x < 0) {x = 0; isStuck = true; addPosAround(x,y);}
				if (x > maxS-2) {x = maxS-2; isStuck = true; addPosAround(x,y);}
				if (y < 0) {y = 0; isStuck = true; addPosAround(x,y);}
				if (y > maxS-2) {y = maxS-2; isStuck = true; addPosAround(x,y);}
				
				try{
					byte b =  matrix[(int)x][(int)y];
					if (b==1) {
						isStuck = true;
						addPosAround(x,y);
					}
				}catch(ArrayIndexOutOfBoundsException A) {
					addPosAround(x,y);
					isStuck = true;
				}
			}
			
			if (Math.sqrt(Math.pow((x-(maxS/2)),2)+Math.pow((y-(maxS/2)),2)) < 100 &&
				Math.sqrt(Math.pow((x-(maxS/2)),2)+Math.pow((y-(maxS/2)),2)) > 99)
			{
				isStuck = true;
				addPosAround(x,y);
				
			}
		}
	} 

}
