package logica;
/**
 * @author Aleix Garrido Oberink, Sergio Salmer�n Majadas.
 * G07
 */

public class CromosomaF1 extends Cromosoma{

	private int xMin=0;
	private int xMax=32;
	
	private int longitudCromosoma;

	
	public CromosomaF1(double tolerancia){
		longitudCromosoma=this.calculaLongCromosoma(tolerancia);
		genes = new boolean[longitudCromosoma];
		for (int i=0; i<longitudCromosoma; i++){
			genes[i]= iniciaGen();
		}
	}
	
	public CromosomaF1() {
	}

	@Override
	public int calculaLongCromosoma(double tolerancia){
		double valor=1+(xMax-xMin)/tolerancia;
		valor=Math.log10(valor)/Math.log10(2);
		return (int)java.lang.Math.ceil(valor);
	}

	@Override
	public double[] valorFenotipo() {
		fenotipo= new double[1];
		fenotipo[0] = xMin+(xMax - xMin)*binDec(0,longitudCromosoma)/(Math.pow(2,longitudCromosoma)-1);
		return fenotipo;
	}

	@Override
	public double evalua() {
		double x=valorFenotipo()[0];
		aptitud= 20 + Math.E - 20*Math.pow(Math.E, -0.2*Math.abs(x)) - Math.pow(Math.E, Math.cos(2*Math.PI*x));
		return aptitud;
	}

	@Override
	public Object clone() {
		CromosomaF1 clon = new CromosomaF1();
		clon.aptitud=aptitud;
		clon.fenotipo=fenotipo;
		clon.longitudCromosoma=longitudCromosoma;
		clon.setPuntAcum(this.getPuntAcum());
		clon.setPuntuacion(this.getPuntuacion());
		
		clon.genes = new boolean[longitudCromosoma];
		for (int i=0; i<longitudCromosoma; i++){
			clon.genes[i]= genes[i];
		}
		
		return clon;
	}
	

	
	
}