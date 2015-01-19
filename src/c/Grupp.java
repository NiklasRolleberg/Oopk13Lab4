package c;

import java.util.ArrayList;
import c.Model.Particle;
/**
 * 
 * Kvar att gÃ¶ra
 * 
 * fÃ¥ partiklarna att byta grupp ibland
 * 
 * 
 */
public class Grupp {
	ArrayList<Object> freeParticles;
	ArrayList<Object> stuckParticles;
	ArrayList<Object> felGrupp;
	
	Object[] grupperRunt; //länkar till grupperna ovan, under och på sidorna så att en partikel kan jämföras med dem också
	
	int id;
	
	int xMin = 0, yMin = 0; 
	int xMax = 400,yMax = 400;
	
	Grupp(int index) {
		id = index;
		freeParticles = new ArrayList<Object>();
		stuckParticles = new ArrayList<Object>();
		felGrupp = new ArrayList<Object>();
	}
	
	public void setGrupperRunt(Object[] runt) {
		grupperRunt = runt;
	}
	
	public Object[] getGrupperRunt() {
		return grupperRunt;
	}
	
	
	public void setLimits(int minx,int maxx, int miny,int maxy) {
		xMax = maxx;
		xMin = minx;
		yMax = maxy;
		yMin = miny;
		//System.out.println("Grupp: X-max: "+Integer.toString(xMax)+" Y-max: "+Integer.toString(yMax)+" X-min: "+Integer.toString(xMin)+" Y-min: "+Integer.toString(yMin));
	}
	
	public void remove(Object o) {
		freeParticles.remove(o);
		felGrupp.remove(o);
	}
	
	public Object[] felplats() {
		return felGrupp.toArray();
	}
	
	public void add(Object in) {
		((Particle)in).gruppnr = id;
		if (((Particle)in).getSitterfast()) {
			stuckParticles.add(in);
		}
		if (!((Particle)in).getSitterfast()) {
			freeParticles.add(in);
		}
	}
	
	public void kolla() {
		ArrayList<Object> plockabort = new ArrayList();
		for (Object o: freeParticles) {
			if (((Particle) o).getSitterfast()) {
				stuckParticles.add(o);
				plockabort.add(o);
			}
			
		}
		for (Object o:plockabort) {
			freeParticles.remove(o);
		}
		
		for (Object o:freeParticles) {
			int[] pos = ((Particle)o).getPos();
			if (pos[0] < xMin || pos[0] > xMax || pos[0] < yMin || pos[0] > yMax) {
				//System.out.println("Något är på fel plats addar i felGrupp arraylisten");
				felGrupp.add(o);
			}
		}
	}
	
	public boolean jamforEn(Object in) {
		for (Object s: stuckParticles) {
			int[] pos1 = ((Particle)in).getPos();
			int[] pos2 = ((Particle)s).getPos();
			if (Math.abs(pos1[0]-pos2[0]) <= 1 && Math.abs(pos1[1]-pos2[1]) <= 1 ) { 
				return true;
			}	
		}
		return false;
	}
	
	/**kollar ifal en partikel i gruppen ska sitta fast*/
	public void jamfor() {
		for (Object o: freeParticles) {
			for (Object s: stuckParticles) {
				int[] pos1 = ((Particle)o).getPos();
				int[] pos2 = ((Particle)s).getPos();
				if (Math.abs(pos1[0]-pos2[0]) <= 1 && Math.abs(pos1[1]-pos2[1]) <= 1 ) { 
					((Particle)o).setSitterfast(true);
				}
				else if (pos1[0] <= xMin+3 || pos1[0] >= xMax-3 || pos1[1] <= yMin+3 || pos1[1] >= yMax-3){
					if (grupperRunt != null){
						for (Object gr: grupperRunt) {
							if (((Grupp)gr).jamforEn(o)) {
								((Particle)o).setSitterfast(true);
							}
						}
					}
				}
			}
		}
	}
}
