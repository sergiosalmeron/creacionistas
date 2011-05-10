package mutaciones;

import java.util.Random;

import logica.Cromosoma;

public class CambiaInicios implements Mutacion {
	
	Random r;

	@Override
	public void muta(Cromosoma c) {
		r= new Random();
		int pos=r.nextInt(c.getLongCromosoma());
		int[] aux=new int[27];
		
		for (int i=0; i<c.getLongCromosoma(); i++){
			aux[i]=c.getGen(pos);
			pos++;
			if (pos==c.getLongCromosoma())
				pos=0;
		}
		c.setGenes(aux);
	}

}
