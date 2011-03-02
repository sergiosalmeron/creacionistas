
public class AGenetico {
	
	private Cromosoma[] pob;
	private int tamPob;
	private int numMaxGen;
	private Cromosoma elMejor;
	private int posMejor;
	private double probCruce;
	private double probMutacion;
	private double tolerancia;
	
	public AGenetico(int tamPob, int numMaxGen, double probCruce, double probMutacion, double tolerancia){
		this.tamPob=tamPob;
		this.numMaxGen=numMaxGen;
		this.probCruce=probCruce;
		this.probMutacion=probMutacion;
		this.tolerancia=tolerancia;
	}
	
	public AGenetico(){
		this.tamPob=100;
		this.numMaxGen=20;
		this.probCruce=0.3;
		this.probMutacion=0.05;
		this.tolerancia=1;
	}
	
	private double calculaLongCromosomaF1(){
		CromosomaF1 aux=new CromosomaF1();
		return aux.calculaLongCromosoma(tolerancia);
	}

}
