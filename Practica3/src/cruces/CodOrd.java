package cruces;

import java.util.ArrayList;
import java.util.Random;

import logica.Cromosoma;

public class CodOrd implements Cruce{
	
	private ArrayList<Integer> listaOrdenada;
	private ArrayList<Integer> ordenPadre;
	private ArrayList<Integer> ordenMadre;
	private int puntoCorte;

	@Override
	public void cruza(Cromosoma padre, Cromosoma madre) {
		listaOrdenada=new ArrayList<Integer>();
		ordenPadre=new ArrayList<Integer>();
		ordenMadre=new ArrayList<Integer>();
		dameOrden(padre, ordenPadre);
		dameOrden(madre, ordenMadre);
		calculaCorte(padre);
		combinaOrdenes(padre);
		ordena(padre,ordenPadre);
		ordena(madre,ordenMadre);
		
		System.out.println("punto C: "+ puntoCorte);
		
		
	}

	private void ordena(Cromosoma c, ArrayList<Integer> orden) {
		ordenaLista(c);
		int o;
		int num;
		int indice;
		for(int i=0; i<c.getLongCromosoma(); i++){
			o=orden.get(i);
			num=listaOrdenada.get(o);
			indice=listaOrdenada.indexOf(num);
			c.setGen(i, num);
			listaOrdenada.remove(indice);
		}
		
	}

	private void combinaOrdenes(Cromosoma c) {
		int i=puntoCorte;
		int aux;
		for (i=puntoCorte; i<c.getLongCromosoma(); i++){
			aux=ordenPadre.get(i);
			ordenPadre.remove(i);
			ordenPadre.add(i, ordenMadre.get(i));
			ordenMadre.remove(i);
			ordenMadre.add(i,aux);
		}
		
	}

	private void calculaCorte(Cromosoma c) {
		Random r=new Random();
		puntoCorte=r.nextInt(c.getLongCromosoma()+1);
		
	}

	private void dameOrden(Cromosoma c, ArrayList<Integer> orden) {
		ordenaLista(c);
		int aux;
		for (int i=0; i<c.getLongCromosoma(); i++){
			aux=listaOrdenada.indexOf(c.getGen(i));
			orden.add(aux);
			listaOrdenada.remove(aux);
		}
		
	}
	
	private void ordenaLista(Cromosoma c){
		listaOrdenada.clear();
		for (int i=0; i<c.getLongCromosoma();i++){
			listaOrdenada.add(i+1);
		}
	}

}
