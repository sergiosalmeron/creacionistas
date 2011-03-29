package logica;
/**
 * @author Aleix Garrido Oberink, Sergio Salmerón Majadas.
 * G07
 */
public class CromosomaF5 extends Cromosoma{
	
	private int xMin=-10;
	private int xMax=10;
	private int longitudCromosoma;
	private int longGen;
	private int n=2;
	
	public CromosomaF5(double tolerancia){
		/*if ((nn>0)&&(nn<3))
			this.n=nn;*/
		longitudCromosoma=this.calculaLongCromosoma(tolerancia);
		genes = new boolean[longitudCromosoma];
		for (int i=0; i<longitudCromosoma; i++){
			genes[i]= iniciaGen();
		}
	}
	
	public CromosomaF5(){
	}
	
	@Override
	public int calculaLongCromosoma(double tolerancia) {
		double valor=1+(xMax-xMin)/tolerancia;
		valor=Math.log10(valor)/Math.log10(2);
		longGen=(int)java.lang.Math.ceil(valor);
		return (int)(java.lang.Math.ceil(longGen)*n);
	}
	@Override
	public Object clone() {
		CromosomaF5 clon = new CromosomaF5();
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
		double valor1=0;
		double valor2=0;
		for (int i=1;i<6;i++){
			valor1=valor1 + ( i*Math.cos( ((i+1)*fen[0]) + i ) );
			valor2=valor2 + ( i*Math.cos( ((i+1)*fen[1]) + i ) );
		}
		aptitud=valor1*valor2;
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
