package utils;

import java.util.Comparator;

import logica.Cromosoma;

public class ComparadorCromos implements Comparator<Cromosoma> {

	@Override
	public int compare(Cromosoma o1, Cromosoma o2) {
		double res=o1.getPuntuacion()-o2.getPuntuacion();
		if (res<0)
			return-1;
		if (res>0)
			return 1;
		return 0;
	}

}
