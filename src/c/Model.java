package c;

import java.util.ArrayList;

public class Model {
	static double L = 1,dt =1;
	//public static int maxX = 400;
	//public static int maxY = 400;
	public static int maxS = 400; //största x/y värde
	Object particles[];
	Object grupper[];
	int[] pariclePositons; // Kanske snabbar upp lite sÃ¥ man slipper skpa en ny;
	int antalgrupper = 8; //i x och y led, totalt antal = antalgrupper^2 
	
	public Model(int antalPartiklar) {
		//skapa massa partiklar
		 // antal grupper måste vara x*4
		grupper = new Object[antalgrupper*antalgrupper];
		for(int i=0; i<antalgrupper*antalgrupper; i++) {
			grupper[i] = new Grupp(i);
		}
		
		//ställa in gränser för grupperna
		int steg = maxS/antalgrupper;
		int index = 0;
		for (int i=0;i < antalgrupper; i++) { //X-led
			for (int j=0;j < antalgrupper; j++) { //y-led
				int xMax = (j+1)*steg;
				int yMax = (i+1)*steg;
				
				int xMin = j*steg;
				int yMin = i*steg;
				
				((Grupp)grupper[index]).setLimits(xMin, xMax,yMin,yMax);
				index++;
			}	
		}
		
		index--;
		
		//ser till att grupperna vet vilka som finns runt om
		for (int i=0; i<=index;i++) {
			int left = i-1;
			int right = i+1;
			int up = i+antalgrupper;
			int down = i- antalgrupper;
			/*
			int upRight = up+1;
			int upLeft = up-1;
			int downRight = down+1;
			int downLeft = down-1;
			*/
			
			ArrayList<Object> runt = new ArrayList<Object>();
			if (left >= 0){
				runt.add(grupper[left]);
			}
			if (right <= index){
				runt.add(grupper[right]);
			}
			
			if (down >= 0){
				runt.add(grupper[down]);
			}
			
			if (up <= index){
				runt.add(grupper[up]);
			}
			/*
			if (downLeft >= 0){
				runt.add(grupper[downLeft]);
			}
			if (downRight >= 0){
				runt.add(grupper[downRight]);
			}
			
			if (upLeft <= index){
				runt.add(grupper[upLeft]);
			}
			
			if (upRight <= index){
				runt.add(grupper[upRight]);
			}
			*/
			((Grupp)grupper[i]).setGrupperRunt(runt.toArray());
		}
		
		particles = new Object[antalPartiklar];
		for(int i=0; i<antalPartiklar; i++) {
			particles[i] = new Particle();
		}
		pariclePositons = new int[antalPartiklar*3]; //[X,Y,color]
		delaupp(); //delar upp partiklarna i grupper
	}
	
	/**SÃ¤tter ett vÃ¤rde pÃ¥ L*/
	public void setL(double L) {
		this.L = L;
	}
	
	/**retunerar vÃ¤rdet pÃ¥ L*/
	public double getL() {
		return L;
	}
	
	/** retunerar alla partiklars positioner i en array*/
	public int[] getPos() {
		//int positioner[] = new int[particles.length*2];
		
		int i=0 ,j=0;
		while (i<particles.length) {
			int temp[] = ((Particle) particles[i]).getPos(); //TYPECASTING!!!!
			pariclePositons[j] = temp[0];
			pariclePositons[j+1] = temp[1];
			pariclePositons[j+2] = temp[2];
			j+=3;
			i++;
		}
		return pariclePositons;
	}
	
	/**retunerar en array med positioner utan att kolla var patiklarna Ã¤r just nu*/
	public int[] getPosFast() {
		return pariclePositons;
	}
	
	/**uppdaterar positionen fÃ¶r alla partiklar*/ 
	public void update() {
		for (Object o: particles) {
			((Particle) o).updatePos();	//TYPECASTING!!!
		}
	}
	/**bara för att kolla om grupperna fyllts på rätt*/
	public void kollaantal() {
		
		for (Object o:grupper) {
			System.out.println(((Grupp)o).freeParticles.size() + ((Grupp)o).stuckParticles.size());
		}
		
	}
	
	/**delar upp partiklarna i grupper*/
	public void delaupp() {
		for (Object o: particles) {
			int[] pos = ((Particle)o).getPos();
			
			int i = (int)(pos[0]/(maxS/antalgrupper));
			//i += (int) antalgrupper * pos[1]/(maxS/antalgrupper-1);
			i += ((int)(pos[1]/(maxS/antalgrupper)))*antalgrupper;
			if (i >= antalgrupper*antalgrupper){
				i = antalgrupper*antalgrupper-1;
			}
			
			
			((Grupp) grupper[i]).add(o);
		}
		//kollaantal();
	}
	/**anropar funktioner i grupperna som
	 * kollar ifall en partikel ska fastna
	 */
	public void compare() {
		for (Object o:grupper) {
			((Grupp)o).jamfor(); //TYPECASTING!!!!!
			((Grupp)o).kolla();  //TYPECASTING!!!!!
		}
	}
	
	/**Ger en partikel en ny grupp*/
	public void nyplats(Object k) {
		int[] pos = ((Particle)k).getPos();
		
		int i = (int)(pos[0]/(maxS/antalgrupper));
		i += ((int)(pos[1]/(maxS/antalgrupper)))*antalgrupper;
		
		if (i >= antalgrupper*antalgrupper){
			i = antalgrupper*antalgrupper-1;
		} else if (i < 0){
			i = 0;
		}
		//System.out.println(i);
		((Grupp) grupper[i]).add(k);
	}
	
	/**Plockar bort de pariklar som finns i fel grupp och lägger dem i en ny*/
	public void fixagrupperna() {
		for (Object o: grupper) {
			for (Object p:((Grupp)o).felplats()) {
				nyplats(p);
				((Grupp)o).remove(p);
			}
		}
	}
	
	class Particle {
		double x;
		double y;
		boolean sitterfast = false;
		int gruppnr=-1;
		
		Particle() {
			this(maxS*Math.random(),maxS*Math.random());   //400 Ã¤r JFramens storlek
		}

		Particle(double x, double y) {
			this.x = x;
			this.y = y;
		}

		/**get en array med [x,y,1 eller 2 om den sitter fast eller ej]*/
		int[] getPos() {
			int a[] = new int[3];
			a[0] = (int)x; a[1] = (int)y;
			if (sitterfast) {
				a[2] = 1;
			}
			else{
				a[2] = 0;
			}
			//a[2] = gruppnr;   //För att se vilken grupp den tillhör i view.debug()
			return a;
		}
		
		/** om true så ska partikeln sitta fast*/
		public boolean getSitterfast(){
			return sitterfast;
		}
		/**gör så att patikeln fastnar*/
		public void setSitterfast(boolean b){
			sitterfast = b;
		}
		
		/**Uppdaterar positionen om partikeln inte sitter fast*/
		public void updatePos() {
			if (!sitterfast){
				double fi = 2*Math.PI*Math.random();
				x = x + L*Math.cos(fi); 
				y = y + L*Math.sin(fi); 
			}
			if (x <= 0 || x >= maxS || y <= 0 || y >= maxS || sitterfast){
				sitterfast = true;
			}
			
			if (Math.sqrt(Math.pow((x-(maxS/2)),2)+Math.pow((y-(maxS/2)),2)) < 100 &&
				Math.sqrt(Math.pow((x-(maxS/2)),2)+Math.pow((y-(maxS/2)),2)) > 99 || sitterfast)
			{
				sitterfast = true;
			}
		}
	} 

}
