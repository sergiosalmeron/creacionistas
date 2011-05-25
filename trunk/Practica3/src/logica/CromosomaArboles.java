package logica;

import java.util.ArrayList;

public class CromosomaArboles extends Cromosoma{
	
	private int longitudCromosoma=27;
	
	public CromosomaArboles(){
		
		gen= Arbol.getRandArbol();
		
	}

	@Override
	public int calculaLongCromosoma() {
		return longitudCromosoma;
	}

	@Override
	public Object clone() {
		CromosomaArboles clon=new CromosomaArboles();
		clon.aptitud=aptitud;
		clon.gen = gen.clone();

		clon.setPuntAcum(this.getPuntAcum());
		clon.setPuntuacion(this.getPuntuacion());
		
		
		return clon;
	}

	@Override
	public double evalua() {
		int valor=gen.getEvaluacion();
		
		aptitud=valor;
		return valor;
	}

	@Override
	public String valorFenotipo() {
		return gen.toString();
	}

}
