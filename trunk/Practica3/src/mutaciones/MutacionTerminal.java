package mutaciones;

import logica.Cromosoma;
import logica.CromosomaArboles;

public class MutacionTerminal implements Mutacion {

	@Override
	public void muta(Cromosoma c) {
		if (c instanceof CromosomaArboles){
			c.getGen().mutacionTerminal();
		}

	}

}
