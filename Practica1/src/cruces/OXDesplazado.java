package cruces;

import java.util.ArrayList;
import java.util.Random;

import logica.Cromosoma;

public class OXDesplazado implements Cruce{

	private int pos1;
	private int pos2;
	private int segmento;
	private ArrayList<Integer> padreAux;
	private ArrayList<Integer> madreAux;
	private int[] hijo= new int[27];
	private int[] hija= new int[27];
	
	@Override
	public void cruza(Cromosoma padre, Cromosoma madre) {
		iniciaPosiciones(padre.calculaLongCromosoma());
		iniciaAuxiliares(padre, madre);
		meteSegmentos(padre, madre);
		rellena();
		finaliza(padre,madre);
		
	}

	private void rellena(){
		int aux=(pos1+segmento)%hijo.length;
		while (aux!=(pos1)){
			boolean puesto=false;
			while (!puesto){
				if (!contiene(padreAux.get(0), hijo)){
					hijo[aux]=padreAux.get(0);
					puesto=true;
				}
					padreAux.remove(0);	
			}
			
			
			aux=(aux+1)%hijo.length;
		}
		
		aux=(pos2+segmento)%hija.length;
		while (aux!=(pos2)){
			boolean puesto=false;
			while (!puesto){
				try {
					if (!contiene(madreAux.get(0), hija)){
						hija[aux]=madreAux.get(0);
						puesto=true;
					}
				} catch (Exception e) {
					System.out.println("error");
				}
					madreAux.remove(0);
				
			}
			aux=(aux+1)%hija.length;
		}
		
		
	}
	
	private boolean contiene (int valor, int[] perso){
		for (int i=0;i<perso.length;i++){
			if (perso[i]==valor)
				return true;
		}
		return false;
	}

	private void finaliza(Cromosoma padre, Cromosoma madre) {
		if (segmento>0){
		padre.setGenes(hijo);
		madre.setGenes(hija);
		}
	}


	private void meteSegmentos(Cromosoma padre, Cromosoma madre) {
		int posA=pos1;
		int posB=pos2;
		hijo=new int[27];
		hija=new int[27];
		
		for (int i=0; i<segmento; i++){
			hijo[posA+i]=madre.getGen(posB+i);
			hija[posB+i]=padre.getGen(posA+i);
		}
		
	}



	private void iniciaAuxiliares(Cromosoma padre, Cromosoma madre) {
		padreAux=new ArrayList<Integer>();
		madreAux=new ArrayList<Integer>();
		int inicioA= pos1+segmento;
		int inicioB=pos2+segmento;
		
		for (int i=0; i<padre.getLongCromosoma(); i++){
			padreAux.add(padre.getGen(inicioA));
			madreAux.add(madre.getGen(inicioB));
			inicioA++;
			inicioB++;
			if (inicioA>=padre.getLongCromosoma()) inicioA=0;
			if (inicioB>=padre.getLongCromosoma()) inicioB=0;
		}
		
	}





	private void iniciaPosiciones(int longitud) {
		Random r=new Random();
		segmento=r.nextInt(longitud);
		int espacioRestante=longitud-segmento;
		pos1=r.nextInt(espacioRestante);
		pos2=r.nextInt(espacioRestante);
		if (segmento==0)
			longitud=4;
	}

}
