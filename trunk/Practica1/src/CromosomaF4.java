
public class CromosomaF4 extends Cromosoma{
	
	private int xMin=0;
	private int xMax=100;
	private int longitudCromosoma;
	private int longCromo;
	private int n;
	
	public CromosomaF4(int nn, double tolerancia){
		this.n=nn;
		longitudCromosoma=this.calculaLongCromosoma(tolerancia);
		genes = new boolean[longitudCromosoma];
		for (int i=0; i<longitudCromosoma; i++){
			genes[i]= iniciaGen();
		}
	}
	
	public CromosomaF4(int nn){
		this.n=nn;
	}
	
	@Override
	public int calculaLongCromosoma(double tolerancia) {
		double valor=1+(xMax-xMin)/tolerancia;
		valor=Math.log10(valor)/Math.log10(2);
		longCromo=(int)java.lang.Math.ceil(valor);
		return (int)(java.lang.Math.ceil(longCromo)*n);
	}
	@Override
	public Object clone() {
		CromosomaF4 clon = new CromosomaF4(n);
		clon.aptitud=aptitud;
		clon.fenotipo=new double[n];
		for (int i=0;i<n;i++)
			clon.fenotipo[i]=fenotipo[i];
		clon.longitudCromosoma=longitudCromosoma;
		clon.longCromo=longCromo;
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
			puntoInicio=cromo*longCromo;
			puntoFin=puntoInicio+longCromo;
			fenotipo[cromo] = xMin+(xMax - xMin)*binDec(puntoInicio,puntoFin)/(Math.pow(2,longCromo)-1);
		}
		return fenotipo;
	}

}
