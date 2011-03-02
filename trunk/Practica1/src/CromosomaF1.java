
public class CromosomaF1 extends Cromosoma{

	private int xMin=0;
	private int xMax=32;
	
	private double longitudCromosoma;

	public double evalua(){
		return 0;
	}
	
	public double fenotipo(){
		return 0;
	}
	
	public double calculaLongCromosoma(double tolerancia){
		double valor=1+(xMax-xMin)/tolerancia;
		longitudCromosoma=java.lang.Math.log10(valor)/java.lang.Math.log10(2);
		return longitudCromosoma;
	}
	
}
