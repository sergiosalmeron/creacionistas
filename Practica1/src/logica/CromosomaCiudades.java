package logica;

import java.util.ArrayList;

public class CromosomaCiudades extends Cromosoma{
	
	private int longitudCromosoma=27;
	
	public CromosomaCiudades(){
	
		ArrayList<Integer> aux= new ArrayList<Integer>();
		for (int i=1; i<=27; i++){
			aux.add(i);
		}
		
		genes= new int[longitudCromosoma];
		int i=0;
		int numero;
		while (aux.size()>0){
			numero=r.nextInt(aux.size());
			genes[i]=aux.get(numero);
			aux.remove(numero);
			i++;
		}
		
	}

	@Override
	public int calculaLongCromosoma() {
		return longitudCromosoma;
	}

	@Override
	public Object clone() {
		CromosomaCiudades clon=new CromosomaCiudades();
		clon.aptitud=aptitud;
		clon.genes = new int[longitudCromosoma];
		//clon.fenotipo = new int[longitudCromosoma];
		for (int i=0; i<longitudCromosoma; i++){
			clon.genes[i]= genes[i];
			//clon.fenotipo[i]=genes[i];
		}
		clon.setPuntAcum(this.getPuntAcum());
		clon.setPuntuacion(this.getPuntuacion());
		
		
		return clon;
	}

	@Override
	public double evalua() {
		int valor=Distancias.getDist(0, genes[0]);
		for (int i=1; i<genes.length; i++){
			valor=valor+Distancias.getDist(genes[i-1], genes[i]);
		}
		valor=Distancias.getDist(genes[genes.length-1], 0);
		
		aptitud=valor;
		return valor;
	}

	@Override
	public int[] valorFenotipo() {
		return genes;
	}

}
