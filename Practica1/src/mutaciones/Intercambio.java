package mutaciones;

import java.util.Random;

import logica.Cromosoma;

public class Intercambio implements Mutacion{

	private Random r;
	
	public Intercambio(){
		r=new Random();
	}
	@Override
	
	public void muta(Cromosoma c) {
		int longi=c.getLongCromosoma();
		if (longi==1)
			return;
		
		int a=r.nextInt(longi);
		int b=r.nextInt(longi);
		while(a==b)
			b=r.nextInt(longi);
		System.out.println("el elemento de la pos "+a+" va a la pos "+b);
		int aux=c.getGen(a);
		c.setGen(a, c.getGen(b));
		c.setGen(b, aux);
	}

}
