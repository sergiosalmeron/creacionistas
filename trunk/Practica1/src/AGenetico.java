
public class AGenetico {
	
	Cromosoma[] pob;
	int tamPob;
	int numMaxGen;
	Cromosoma elMejor;
	int posMejor;
	double probCruce;
	double probMutacion;
	double tolerancia;
	
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

}
