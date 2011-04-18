package mutaciones;

import java.util.Random;

import logica.Cromosoma;

public class Insercion implements Mutacion{

	private final int numInserciones=1;
	private Random r;
	
	public Insercion(){
		r=new Random();
	}
	@Override
	
	public void muta(Cromosoma c) {
		for (int i=0;i<numInserciones;i++){
			inserta(c);
		}
		
	}
	private void inserta(Cromosoma c) {
		int longi=c.getLongCromosoma();
		if (longi==1)
			return;
		
		int min=r.nextInt(longi);
		int max=r.nextInt(longi);
		while(min==max)
			max=r.nextInt(longi);
		if (max<min){
			int auxi=max;
			max=min;
			min=auxi;
		}
		//System.out.println("el elemento de la pos "+max+" va a la pos "+min);
		int aux=c.getGen(max);
		for (int i=max;i>min;i--){
			c.setGen(i, c.getGen(i-1));
		}
		c.setGen(min, aux);
		
		
	}

}
