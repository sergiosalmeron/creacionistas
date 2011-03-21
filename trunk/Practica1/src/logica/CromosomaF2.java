package logica;

public class CromosomaF2 extends Cromosoma{
	
	private double xMin=-3;
	private double xMax=12.1;
	private double yMin=4.1;
	private double yMax=5.8;
	
	private int longitudCromosoma;
	private int puntoCorte;
	
	public CromosomaF2() {
	}
	
	public CromosomaF2(double tolerancia){
		longitudCromosoma=this.calculaLongCromosoma(tolerancia);
		genes = new boolean[longitudCromosoma];
		for (int i=0; i<longitudCromosoma; i++){
			genes[i]= iniciaGen();
		}
	}

	@Override
	public int calculaLongCromosoma(double tolerancia) {
		double valor=1+(xMax-xMin)/tolerancia;
		double valor2=1+(yMax-yMin)/tolerancia;
		valor=Math.log10(valor)/Math.log10(2);
		valor2=Math.log10(valor2)/Math.log10(2);
		puntoCorte=(int)java.lang.Math.ceil(valor);
		return (int)java.lang.Math.ceil(puntoCorte+valor2);
	}

	@Override
	public Object clone() {
		CromosomaF2 clon = new CromosomaF2();
		clon.aptitud=aptitud;
		clon.fenotipo=new double[2];
		clon.fenotipo[0]=fenotipo[0];
		clon.fenotipo[1]=fenotipo[1];
		clon.longitudCromosoma=longitudCromosoma;
		clon.puntoCorte=puntoCorte;
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
		double x=fen[0];
		double y=fen[1];
		aptitud= 21.5 + x*Math.sin(4*Math.PI*x) + y*Math.sin(20*Math.PI*y);
		return aptitud;
	}

	@Override
	public double[] valorFenotipo() {
		fenotipo= new double[2];
		fenotipo[0] = xMin+(xMax - xMin)*binDec(0,puntoCorte)/(Math.pow(2,puntoCorte)-1);
		fenotipo[1] = yMin+(yMax - yMin)*binDec(puntoCorte,longitudCromosoma)/(Math.pow(2,longitudCromosoma-puntoCorte)-1);
		return fenotipo;
	}

}
