package cruces;

import java.util.ArrayList;
import java.util.Random;

import logica.Cromosoma;

public class OXPosciciones implements Cruce {

	/*private int pos1;
	private int pos2;*/
	private int[] pos;
	private ArrayList<Integer> padreAux;
	private ArrayList<Integer> madreAux;
	private int[] hijo;
	private int[] hija;
	
	@Override
	public  void cruza(Cromosoma padre, Cromosoma madre) {
		int maxPos=this.inicializaPos(padre);
		this.generaAux(padre, madre,maxPos);
		this.intercambia(padre, madre);
		this.rellena(maxPos);
		this.finaliza(padre, madre);
	}
	
	private void finaliza(Cromosoma padre, Cromosoma madre) {
		padre.setGenes(hijo);
		madre.setGenes(hija);
	}


	private int inicializaPos(Cromosoma padre){
		Random r = new Random();
		int numPos=r.nextInt(padre.getLongCromosoma())+1;
		pos=new int[numPos];
		int posic;
		int maxPos=0;
		if (numPos==padre.getLongCromosoma())
			numPos--;
		for (int i=0;i<numPos;i++){
			posic=r.nextInt(padre.getLongCromosoma());
			while (contiene(posic, pos))
				posic=r.nextInt(padre.getLongCromosoma());
			pos[i]=posic;
			if (posic>maxPos){
				maxPos=posic;
			}
		}
		return maxPos;
		
		/*
		pos1=r.nextInt(padre.getLongCromosoma());
		pos2=r.nextInt(padre.getLongCromosoma());
		while (pos2==pos1)
			pos2=r.nextInt(padre.getLongCromosoma());
		if (pos2<pos1){
			int aux=pos1;
			pos1=pos2;
			pos2=aux;
		}*/
	}
	
	private void generaAux(Cromosoma padre, Cromosoma madre, int maxPos){
		padreAux=new ArrayList<Integer>();
		madreAux=new ArrayList<Integer>();
		int aux;

		for (int i=0;i<padre.getLongCromosoma();i++){
			aux=(maxPos+i+1)%padre.getLongCromosoma();
			padreAux.add(padre.getGen(aux));
			madreAux.add(madre.getGen(aux));
		}
		
	}
	
	private void intercambia(Cromosoma padre, Cromosoma madre){
		hijo=new int[padre.getLongCromosoma()];
		hija=new int[padre.getLongCromosoma()];
		for (int i=0; i<pos.length; i++){
			int posicion=pos[i];
			hijo[posicion]=madre.getGen(posicion);
			hija[posicion]=padre.getGen(posicion);
		}
	}
	
	private void rellena(int maxPos){
		int numRellenos=0;
		int posAux=(maxPos+1)%hijo.length;
		while(numRellenos<hijo.length-pos.length){
			if (contiene(posAux, pos)){
				posAux=(posAux+1)%hijo.length;
			}
			else{
				
				boolean puesto=false;
				while (!puesto){
					if (!contiene(madreAux.get(0), hija)){
						hija[posAux]=madreAux.get(0);
						puesto=true;
					}
					madreAux.remove(0);					
				}
				
				puesto=false;
				while (!puesto){
					if (!contiene(padreAux.get(0), hijo)){
						hijo[posAux]=padreAux.get(0);
						puesto=true;
					}
					padreAux.remove(0);
				}
				
				
				posAux=(posAux+1)%hijo.length;
				numRellenos++;
			}
		}


		
		
	}
	
	private boolean contiene (int valor, int[] perso){
		for (int i=0;i<perso.length;i++){
			if (perso[i]==valor)
				return true;
		}
		return false;
	}
	
	public void muestraPosiciones(){
		System.out.println("posiciones:");
		for (int i=0;i<pos.length;i++){
			System.out.print(pos[i]+", ");
		}
	}

}
