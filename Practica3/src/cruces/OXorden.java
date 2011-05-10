package cruces;

import java.util.ArrayList;
import java.util.Random;

import logica.Cromosoma;

public class OXorden implements Cruce{
	
	
	private ArrayList<Integer> posiciones;
	private ArrayList<Integer> hayEnCromosoma;
	private ArrayList<Integer> posicionContrario;

	@Override
	public void cruza(Cromosoma padre, Cromosoma madre) {
		int[] hijo= new int[padre.getLongCromosoma()];
		int[] hija= new int[padre.getLongCromosoma()];
		inicializaPosiciones(padre);
		creaHijo(hijo,padre,madre);
		creaHijo(hija,madre,padre);
		finaliza(padre,madre,hijo,hija);
		
	}
	
	private void inicializaPosiciones(Cromosoma padre) {
		Random r=new Random();
		posiciones=new ArrayList<Integer>();
		ArrayList<Integer> posicionesLibres=new ArrayList<Integer>();
		for(int i=0; i<padre.getLongCromosoma();i++)
			posicionesLibres.add(i);
		
		int aux;
		int numeroPos=r.nextInt(padre.getLongCromosoma())+1;
		
		for (int i=0; i<numeroPos; i++){
			aux=r.nextInt(posicionesLibres.size());
			posiciones.add(posicionesLibres.get(aux));
			posicionesLibres.remove(aux);
		}
		
	}


	private void creaHijo(int[] hijo, Cromosoma padre, Cromosoma madre) {
		hayEnCromosoma=new ArrayList<Integer>();
		posicionContrario=new ArrayList<Integer>();
		trataCromosoma(padre,madre);
		int aux;
		for (int i=0;i<padre.calculaLongCromosoma();i++){
			aux=posicionContrario.indexOf(i);
			if (aux!=-1){
				hijo[i]=hayEnCromosoma.get(0);
				hayEnCromosoma.remove(0);
			}
			else
				hijo[i]=madre.getGen(i);
		}
		
	}

	private void trataCromosoma(Cromosoma padre, Cromosoma madre) {
		ArrayList<Integer> aux=new ArrayList<Integer>();
		for(int i=0; i<madre.calculaLongCromosoma();i++)
			aux.add(madre.getGen(i));

		//propio de esta clase
		for (int i=0; i<posiciones.size();i++){
			//qué hay en el propio cromosoma
			hayEnCromosoma.add(padre.getGen(posiciones.get(i)));
			//dónde se aloja en el cromosoma contrario
			posicionContrario.add(aux.indexOf(hayEnCromosoma.get(i)));
		}
		
	}

	
	private void finaliza(Cromosoma padre, Cromosoma madre, int[] hijo,
			int[] hija) {
		padre.setGenes(hijo);
		madre.setGenes(hija);
		
	}
	

}
