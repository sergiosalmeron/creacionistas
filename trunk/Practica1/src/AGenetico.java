
public class AGenetico {
	
	private Cromosoma[] pob;
	private int tamPob;
	private int numMaxGen;
	private Cromosoma elMejor;
	private int posMejor;
	private double probCruce;
	private double probMutacion;
	private double tolerancia;
	private int generacionActual=0;
	
	public AGenetico(int tamPob, int numMaxGen, double probCruce, double probMutacion, double tolerancia){
		this.tamPob=tamPob;
		this.numMaxGen=numMaxGen;
		this.probCruce=probCruce;
		this.probMutacion=probMutacion;
		this.tolerancia=tolerancia;
		pob = new Cromosoma[tamPob];
	}
	
	public AGenetico(){
		this.tamPob=100;
		this.numMaxGen=20;
		this.probCruce=0.3;
		this.probMutacion=0.05;
		this.tolerancia=1;
		pob = new Cromosoma[tamPob];
	}
	
	
	public void inicializa(){
		int longitud=calculaLongCromosomaF1();
		for (int i=0; i<tamPob; i++){
			pob[i]=new CromosomaF1(longitud);
			pob[i].evalua();
		}
		
	}
	
	private int calculaLongCromosomaF1(){
		CromosomaF1 aux=new CromosomaF1();
		return aux.calculaLongCromosoma(tolerancia);
	}
	
	public void evaluarPoblacion(){
		double puntAcum=0;
		double aptitudMejor=0;
		double sumaAptitud=0;
		
		for (int i=0; i<pob.length; i++){
			sumaAptitud=sumaAptitud+pob[i].getAptitud();
			if (pob[i].getAptitud()>aptitudMejor){
				this.posMejor=i;
				aptitudMejor=pob[i].getAptitud();
			}	
		}
		
		for (int i=0; i<pob.length; i++){
			pob[i].setPuntuacion(pob[i].getAptitud()/sumaAptitud);
			puntAcum=puntAcum+pob[i].getPuntuacion();
			pob[i].setPuntAcum(puntAcum);
		}
		
		if ((elMejor==null) || (aptitudMejor>elMejor.getAptitud())) {
            elMejor = (Cromosoma)pob[posMejor].clone();
    }

	}
	


}
