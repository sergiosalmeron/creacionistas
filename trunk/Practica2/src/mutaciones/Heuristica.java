package mutaciones;

import java.util.ArrayList;
import java.util.Random;

import logica.Cromosoma;

public class Heuristica implements Mutacion {

	private final int numMaxPosicionesPermutables=6;
	private int numPosiciones;
	public int getNumPosiciones() {
		return numPosiciones;
	}

	private Random r;
	
	@Override
	public void muta(Cromosoma c) {
		r=new Random();
		numPosiciones=r.nextInt(numMaxPosicionesPermutables-1)+2;
		//numPosiciones=6;
//System.out.println("num de posiciones a permutar: "+numPosiciones);
		ArrayList<Integer> posiciones=eligePosiciones(c.getLongCromosoma());
		Cromosoma solu=perMutacionMejor(c, posiciones);
		guardaSolucion(c,solu, posiciones);
	}
	
	
	
	private void guardaSolucion(Cromosoma c, Cromosoma solu, ArrayList<Integer> posiciones) {
		for (int i=0;i<posiciones.size();i++){
			int posicion=posiciones.get(i);
			c.setGen(posicion, solu.getGen(posicion));
		}
		
	}



	private ArrayList<Integer> eligePosiciones(int posiciones) {
		ArrayList<Integer> resultado=new ArrayList<Integer>();
		int candidato;
		for (int i=0;i<numPosiciones;i++){
			candidato=r.nextInt(posiciones);
			while (resultado.contains(candidato))
				candidato=r.nextInt(posiciones);
			resultado.add(candidato);
		}
//System.out.println("posiciones a tocar: "+resultado);
		return resultado;
	}
	
	private Cromosoma perMutacionMejor(Cromosoma c, ArrayList<Integer> p){
		ArrayList<Integer> a=new ArrayList<Integer>();
		for (int i=0;i<p.size();i++){
			a.add(c.getGen(p.get(i)));
		}
		ArrayList<Integer> b=new ArrayList<Integer>();
		ArrayList<ArrayList<Integer>> d= new ArrayList<ArrayList<Integer>>();
		pe(a,d,b);
		Cromosoma solu=(Cromosoma)c.clone();
		double mejorResultado=99999999;
		int mejorElemento=0;
		for (int i=0;i<d.size();i++){
			for (int j=0;j<p.size();j++){
				solu.setGen(p.get(j), d.get(i).get(j));
			}
//System.out.println();
//for (int ii=0;ii<solu.getLongCromosoma();ii++){
//	System.out.print(String.format("%3d", solu.getGen(ii))+",");
//}
//System.out.println();
//System.out.println(solu.evalua());

			if (solu.evalua()<mejorResultado){
				mejorResultado=solu.getAptitud();
				mejorElemento=i;
			}
		}
		for (int j=0;j<p.size();j++){
			solu.setGen(p.get(j), d.get(mejorElemento).get(j));
		}
//System.out.print("me quedo con el elemento:");
//System.out.println(solu.evalua());
		return solu;
		
	}



	private void pe(ArrayList<Integer> origen, ArrayList<ArrayList<Integer>> destino, ArrayList<Integer> solActual){
		if (origen.size()==0){
			destino.add(new ArrayList<Integer>(solActual));
			//System.out.println(solActual);
		}
		else
			for (int i=0;i<origen.size();i++){
				int aux=origen.get(0);
				solActual.add(aux);
				origen.remove(0);
				pe(origen, destino, solActual);
				solActual.remove(solActual.size()-1);
				origen.add(0, aux);
				rotaArrayList(origen);
			}
	}
	
	private void rotaArrayList(ArrayList<Integer> a){
		int ultimaPos=a.size()-1;
		int aux=a.get(ultimaPos);
		a.remove(ultimaPos);
		a.add(0, aux);
	}

}
