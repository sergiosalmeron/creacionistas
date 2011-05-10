package mutaciones;

import java.util.ArrayList;
import java.util.Random;

import logica.Cromosoma;

public class Inversion implements Mutacion{
	
	private int pos1;
	private int pos2;
	
	@Override
	public void muta(Cromosoma c) {
		this.inicializaPos(c);
		this.inter(c);
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
	
	private void inter(Cromosoma c){
		ArrayList<Integer> segmento=new ArrayList<Integer>();
		for (int i=pos1;i<=pos2;i++){
			segmento.add(c.getGen(i));
		}
		int j=0;
		for (int i=segmento.size()-1;i>=0;i--){
			c.setGen(pos1+j, segmento.get(i));
			j++;
		}
		
	}
	
	
}
