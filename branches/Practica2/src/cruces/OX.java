package cruces;

import java.util.ArrayList;
import java.util.Random;

import logica.Cromosoma;

public class OX implements Cruce {

	private int pos1;
	private int pos2;
	private ArrayList<Integer> padreAux;
	private ArrayList<Integer> madreAux;
	private int[] hijo;
	private int[] hija;
	
	@Override
	public  void cruza(Cromosoma padre, Cromosoma madre) {
		this.inicializaPos(padre);
		this.generaAux(padre, madre);
		this.intercambia(padre, madre);
		this.rellena();
		this.finaliza(padre, madre);
	}
	
	private void finaliza(Cromosoma padre, Cromosoma madre) {
		padre.setGenes(hijo);
		madre.setGenes(hija);
	}


	private void inicializaPos(Cromosoma padre){
		Random r = new Random();
		pos1=r.nextInt(padre.getLongCromosoma());
		pos2=r.nextInt(padre.getLongCromosoma());
		while (pos2==pos1)
			pos2=r.nextInt(padre.getLongCromosoma());
		if (pos2<pos1){
			int aux=pos1;
			pos1=pos2;
			pos2=aux;
		}
	}
	
	private void generaAux(Cromosoma padre, Cromosoma madre){
		padreAux=new ArrayList<Integer>();
		madreAux=new ArrayList<Integer>();
		int aux;

		for (int i=0;i<padre.getLongCromosoma();i++){
			aux=(pos2+i+1)%padre.getLongCromosoma();
			padreAux.add(padre.getGen(aux));
			madreAux.add(madre.getGen(aux));
		}
		
	}
	
	private void intercambia(Cromosoma padre, Cromosoma madre){
		hijo=new int[padre.getLongCromosoma()];
		hija=new int[padre.getLongCromosoma()];
		for (int i=pos1+1; i<=pos2; i++){
			hijo[i]=madre.getGen(i);
			hija[i]=padre.getGen(i);
		}
	}
	
	private void rellena(){
		int aux=(pos2+1)%hijo.length;
		while (aux!=(pos1+1)){
			boolean puesto=false;
			while (!puesto){
				if (!contiene(madreAux.get(0), hija)){
					hija[aux]=madreAux.get(0);
					puesto=true;
				}
				//try{
					madreAux.remove(0);
			/*	}
				catch(Exception e){
					int erer=0;
				}*/
				
			}
			
			puesto=false;
			while (!puesto){
				if (!contiene(padreAux.get(0), hijo)){
					hijo[aux]=padreAux.get(0);
					puesto=true;
				}
				padreAux.remove(0);
			}
			
			
			aux=(aux+1)%hijo.length;
		}
		
		
	}
	
	private boolean contiene (int valor, int[] perso){
		for (int i=0;i<perso.length;i++){
			if (perso[i]==valor)
				return true;
		}
		return false;
	}

}
