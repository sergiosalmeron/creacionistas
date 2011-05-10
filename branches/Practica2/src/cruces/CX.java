package cruces;

import java.util.ArrayList;

import logica.Cromosoma;

public class CX implements Cruce{
	
	private int[] hijo;
	private int[] hija;
	
	private ArrayList<Integer> padreAux;
	

	@Override
	public void cruza(Cromosoma padre, Cromosoma madre) {
		hijo=new int[padre.getLongCromosoma()];
		hija=new int[padre.getLongCromosoma()];
		creaPadreAux(padre);
		
		sigueCiclo(padre,madre);
		restantes(padre,madre);
		finaliza(padre,madre);
		
	}


	private void creaPadreAux(Cromosoma padre) {
		padreAux=new ArrayList<Integer>();
		for (int i=0; i<padre.getLongCromosoma(); i++)
			padreAux.add(padre.getGen(i));
		
		
	}


	private void sigueCiclo(Cromosoma padre, Cromosoma madre) {
		boolean completo=false;
		int pos=0;
		
		while (!completo){
			if (hijo[pos]==0){
				hijo[pos]=padre.getGen(pos);
				hija[pos]=madre.getGen(pos);
				pos=padreAux.indexOf(madre.getGen(pos));
			}
			else
				completo=true;
		}
		
	}
	
	private void restantes(Cromosoma padre, Cromosoma madre) {
		for(int i=0; i<padre.getLongCromosoma(); i++){
			if(hijo[i]==0){
				hijo[i]=madre.getGen(i);
				hija[i]=padre.getGen(i);
			}
		}
		
	}
	
	private void finaliza(Cromosoma padre, Cromosoma madre) {
		padre.setGenes(hijo);
		madre.setGenes(hija);
	}

}
