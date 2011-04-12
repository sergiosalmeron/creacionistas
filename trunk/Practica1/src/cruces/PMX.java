package cruces;

import java.util.Random;

import logica.Cromosoma;

public class PMX implements Cruce{

	private int pos1;
	private int pos2;

	private int[] hijo;
	private int[] hija;
	
	
	@Override
	public void cruza(Cromosoma padre, Cromosoma madre) {
		this.inicializaPos(padre);
		this.intercambia(padre, madre);
		this.rellena(padre,madre);
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
		
		System.out.println("pos1= "+pos1);
		System.out.println("pos2= "+pos2);
	}
	
	private void intercambia(Cromosoma padre, Cromosoma madre){
		hijo=new int[padre.getLongCromosoma()];
		hija=new int[padre.getLongCromosoma()];
		for (int i=pos1+1; i<=pos2; i++){
			hijo[i]=madre.getGen(i);
			hija[i]=padre.getGen(i);
		}
	}
	
	private void rellena(Cromosoma padre, Cromosoma madre) {
		for (int i=0; i<padre.getLongCromosoma(); i++){
			if (i<=pos1 || i>pos2){
				if (!contiene(padre.getGen(i),hijo))
					hijo[i]=padre.getGen(i);
				else
					hijo[i]=getEquivalente(padre.getGen(i),madre,padre);
				
				if (!contiene(madre.getGen(i),hija))
					hija[i]=madre.getGen(i);
				else
					hija[i]=getEquivalente(madre.getGen(i),padre,madre);
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
	
	private int getEquivalente(int valor, Cromosoma a, Cromosoma b){
		return b.getGen(getPosicion(valor, a));
	}
	
	private int getPosicion(int valor, Cromosoma c){
		int i=pos1+1;
		boolean encontrado=false;
		while ((!encontrado)&&(i<pos2)){
			if (c.getGen(i)==valor){
				encontrado=true;
			}
			else
				i++;
		}
		
		return i;
	}
	

}
