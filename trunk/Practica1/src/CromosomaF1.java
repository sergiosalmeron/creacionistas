import java.util.Random;


public class CromosomaF1 extends Cromosoma{

	private int xMin=0;
	private int xMax=32;
	
	private int longitudCromosoma;

	public double evalua(){
		return 0;
	}
	
	public double fenotipo(){
		return 0;
	}
	
	public CromosomaF1(int longitud){
		longitudCromosoma=longitud;
		genes = new boolean[longitudCromosoma];
		for (int i=0; i<longitudCromosoma; i++){
			genes[i]= iniciaGen();
		}
	}
	
	public CromosomaF1() {
	}

	public int calculaLongCromosoma(double tolerancia){
		double valor=1+(xMax-xMin)/tolerancia;
		valor=java.lang.Math.log10(valor)/java.lang.Math.log10(2);
		return (int)java.lang.Math.ceil(valor);
	}
	
}
