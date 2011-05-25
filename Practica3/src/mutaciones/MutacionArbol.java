package mutaciones;

import logica.Cromosoma;
import logica.CromosomaArboles;

public class MutacionArbol implements Mutacion {

	@Override
	public void muta(Cromosoma c) {
		if (c instanceof CromosomaArboles){
			c.getGen().mutacionArbol();
		}

	}

}
