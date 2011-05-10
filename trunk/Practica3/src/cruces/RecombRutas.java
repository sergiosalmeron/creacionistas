package cruces;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import logica.Cromosoma;

public class RecombRutas implements Cruce{
	
	//private ArrayList[] tablaAdy;
	private HashMap<Integer, ArrayList<Integer>> valoresAdy;
	
	private boolean finalizado;
	private Random r;
	
	public RecombRutas(){
		r=new Random();
	}

	@Override
	public void cruza(Cromosoma padre, Cromosoma madre) {
		int[] hijo= new int[padre.getLongCromosoma()];
		int[] hija= new int[padre.getLongCromosoma()];
		for(int i=0;i<padre.getLongCromosoma();i++){
			hijo[i]=-1;
			hija[i]=-1;
		}
		creaTablaAdy(padre, madre);
		//muestraTabla();
		generaDescendencia(hijo, madre.getGen(0));
		generaDescendencia(hija, padre.getGen(0));
		finaliza(padre,madre,hijo,hija);
		
	}
	
	private void creaTablaAdy(Cromosoma padre, Cromosoma madre) {
		valoresAdy=new HashMap<Integer, ArrayList<Integer>>();
		rellenaTablaAdy(padre, madre);
	}
	
	private void rellenaTablaAdy(Cromosoma padre, Cromosoma madre) {
		int longitud=padre.getLongCromosoma();
		for (int i=0;i<longitud;i++){
			ArrayList<Integer> aux=new ArrayList<Integer>();
			insertaVecinos(aux, padre, i);
			//int posMadre=madre.lastIndexOf(padre.getGen(i));
		//	if (posMadre>=0)
			//	insertaVecinos(aux, madre, posMadre);
			valoresAdy.put(padre.getGen(i), aux);
		}
		
		for (int i=0;i<longitud;i++){
			ArrayList<Integer> aux=valoresAdy.get(madre.getGen(i));
			insertaVecinos(aux, madre, i);
		//	int posMadre=madre.lastIndexOf(padre.getGen(i));
			//if (posMadre>=0)
			//	insertaVecinos(aux, madre, posMadre);
		//	valoresAdy.put(padre.getGen(i), aux);
		}
		
	}
	
	private void insertaVecinos(ArrayList<Integer> vecinos, Cromosoma progenitor, int i){
		int aux=getVecinoDcha(i, progenitor);
		if (!vecinos.contains(aux))
			vecinos.add(aux);
		aux=getVecinoIzq(i, progenitor);
		if (!vecinos.contains(aux))
			vecinos.add(aux);
	}
	
	private int getVecinoIzq(int i, Cromosoma progenitor){
		int lon=progenitor.getLongCromosoma();
		if (i<=0)
			return progenitor.getGen(lon-1);
		else
			return progenitor.getGen(i-1);
	}
	
	private int getVecinoDcha(int i, Cromosoma progenitor){
		int lon=progenitor.getLongCromosoma();
		if (i>=lon-1)
			return progenitor.getGen(0);
		else
			return progenitor.getGen(i+1);
	}
	
	private void generaDescendencia(int[] descendiente, int primerElem){
		descendiente[0]=primerElem;
		finalizado=false;
		setRecorrido(descendiente, 1);
	}
	
	private void setRecorrido(int[] desc,int pos){
		if (pos==desc.length){
			finalizado=true;
			return;
		}
		int ultimoVisitado=desc[pos-1];
		ArrayList<Integer> destinosPosibles=new ArrayList<Integer>(valoresAdy.get(ultimoVisitado));
		while ((destinosPosibles.size()>0)&&(!finalizado)){
				int siguienteDestino=eligeSiguienteDestino(destinosPosibles);
				if (destinoValido(desc, pos, siguienteDestino)){
					desc[pos]=siguienteDestino;
					setRecorrido(desc,pos+1);
					if (!finalizado)
						desc[pos]=-1;
				}
		}
	}
	/*
	private int eligeSiguienteDestino(ArrayList<Integer> opciones){
		int menorNumVecinos=100000;
		int numVecinos;
		int posResultado=-1;
		for (int i=0;i<opciones.size();i++){
			numVecinos=valoresAdy.get(opciones.get(i)).size();
			if (menorNumVecinos>numVecinos){
				menorNumVecinos=numVecinos;
				posResultado=i;
			}
			else{
				if (menorNumVecinos==numVecinos){
					if (r.nextInt(2)>0){
						menorNumVecinos=numVecinos;
						posResultado=i;
					}
				}
				
			}
		}
		int resultado=-1;
		if (posResultado>=0){
			resultado=opciones.get(posResultado);
			opciones.remove(posResultado);
		}
		return resultado;
	}*/
	
	private int eligeSiguienteDestino(ArrayList<Integer> opciones){
		int menorNumVecinos=100000;
		int numVecinos;
		//int posResultado=-1;
		ArrayList<Integer> posicionesResultado=new ArrayList<Integer>();
		for (int i=0;i<opciones.size();i++){
			numVecinos=valoresAdy.get(opciones.get(i)).size();
			if (menorNumVecinos>numVecinos){
				menorNumVecinos=numVecinos;
				posicionesResultado.clear();
				posicionesResultado.add(i);
				//posResultado=i;
				
			}
			else{
				if (menorNumVecinos==numVecinos){
					posicionesResultado.add(i);
				}
				
			}
		}
		int resultado=-1;
		if (posicionesResultado.size()>0){
			int posGanadora=r.nextInt(posicionesResultado.size());
			int ganador=posicionesResultado.get(posGanadora);
			resultado=opciones.get(ganador);
			opciones.remove(ganador);
		}
		return resultado;
	}
	
	private boolean destinoValido(int[] recorr,int pos, int destino){
		boolean valido=true;
		int i=0;
		while ((valido)&&(i<pos)){
			if (recorr[i]==destino)
				valido=false;
			i++;
		}
		return valido;
	}
	
	
	private void finaliza(Cromosoma padre, Cromosoma madre, int[] hijo,
			int[] hija) {
		padre.setGenes(hijo);
		madre.setGenes(hija);
		
	}
	
//	public void muestraTabla(){
//		for (int i=1;i<=27;i++){
//			System.out.print( String.format("%3d",i) + ": ");
//			ArrayList<Integer> a=valoresAdy.get(i);
//			for (int j=0;j<a.size();j++){
//				int b=a.get(j);
//				System.out.print( String.format("%3d",b) + ", ");
//			}
//			System.out.println();
//		}
//	}
	
	/*private void creaTablaAdy(Cromosoma padre, Cromosoma madre) {
	int longitud=padre.getLongCromosoma();
	tablaAdy=new ArrayList[longitud];
	for (int i=0;i<longitud;i++){
		tablaAdy[i]=new ArrayList<Integer>();
	}
	
}*/
}
