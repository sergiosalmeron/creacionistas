package logica;

public class CromosomaF4 extends Cromosoma{
	
	private int xMin=0;
	private int xMax=100;
	private int longitudCromosoma;
	private int longGen;
	private int n=1;
	
	public CromosomaF4(int nn, double tolerancia){
		this.n=nn;
		longitudCromosoma=this.calculaLongCromosoma(tolerancia);
		genes = new boolean[longitudCromosoma];
		for (int i=0; i<longitudCromosoma; i++){
			genes[i]= iniciaGen();
		}
	}
	
	public CromosomaF4(){
	}
	
	@Override
	public int calculaLongCromosoma(double tolerancia) {
		double valor=1+(xMax-xMin)/tolerancia;
		valor=Math.log10(valor)/Math.log10(2);
		longGen=(int)java.lang.Math.ceil(valor);
		return longGen*n;
	}
	@Override
	public Object clone() {
		CromosomaF4 clon = new CromosomaF4();
		clon.n=this.n;
		clon.aptitud=aptitud;
		clon.fenotipo=new double[n];
		for (int i=0;i<n;i++)
			clon.fenotipo[i]=fenotipo[i];
		clon.longitudCromosoma=longitudCromosoma;
		clon.longGen=longGen;
		clon.setPuntAcum(this.getPuntAcum());
		clon.setPuntuacion(this.getPuntuacion());
		
		clon.genes = new boolean[longitudCromosoma];
		for (int i=0; i<longitudCromosoma; i++){
			clon.genes[i]= genes[i];
		}
		
		return clon;
	}
	@Override
	public double evalua() {
		double[] fen=valorFenotipo();
		aptitud=0;
		double incremento;
		for (int i=0;i<n;i++){
			double x=fen[i];
			incremento=-( x*Math.sin(Math.sqrt(Math.abs(x))) );;
			aptitud=aptitud+incremento;
		}
		return aptitud;
	}
	@Override
	public double[] valorFenotipo() {
		fenotipo= new double[n];
		//int gen=0;
		int puntoInicio;
		int puntoFin;
		for (int cromo=0;cromo<n;cromo++){
			puntoInicio=cromo*longGen;
			puntoFin=puntoInicio+longGen;
			fenotipo[cromo] = xMin+(xMax - xMin)*binDec(puntoInicio,puntoFin)/(Math.pow(2,longGen)-1);
		}
		return fenotipo;
	}

}
