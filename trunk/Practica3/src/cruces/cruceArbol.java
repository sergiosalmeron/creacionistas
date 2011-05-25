package cruces;

import logica.Cromosoma;
import logica.CromosomaArboles;

public class cruceArbol implements Cruce {

	@Override
	public void cruza(Cromosoma padre, Cromosoma madre) {
		if ((padre instanceof CromosomaArboles)&&(madre instanceof CromosomaArboles)){
			padre.getGen().cruza(madre.getGen());
		}

	}

}
