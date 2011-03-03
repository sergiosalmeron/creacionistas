import java.util.Random;


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
	
	
	//Constructoras //////////////////////////////////////////////////////
	
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
		this.tolerancia=0.1;
		pob = new Cromosoma[tamPob];
	}
	
	
	// Inicialización //////////////////////////////////////////////////
	
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
	
	
	// Evaluación población//////////////////////////////////////////////
	
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
	
	// Selección /////////////////////////////////////////////////////
	
	public void seleccionRuleta(){
		int[] elegidos= new int[tamPob];
		double probabilidad;
		int posicionElegido;
		
		Random aleatorio = new Random();
		
		//Seleccionamos mediante ruleta
		for (int i=0; i<tamPob; i++){
			probabilidad=aleatorio.nextDouble();
			posicionElegido=0;
			
			while ((probabilidad > pob[posicionElegido].getPuntAcum())&& (posicionElegido < tamPob))
				posicionElegido++;
			
			elegidos[i] = posicionElegido;	
		}
		
		//creamos nueva población
		Cromosoma[] nuevaPob = new Cromosoma[tamPob];
        for (int i=0; i<tamPob; i++) {
                nuevaPob[i]=(Cromosoma)pob[elegidos[i]].clone();
        }

        //la población seleccionada pasa a ser la nueva
        pob=nuevaPob;

	}
	
	// Reproducción //////////////////////////////////////////////////
	
	public void reproduccion(){
		int[] elegidos= new int[tamPob];
		int numCruce=0;
		int puntCruce;
		double probabilidad;
		Random aleatorio = new Random();
		
		// vemos cuantos elementos se van a reproducir
		for (int i=0; i<tamPob; i++){
			probabilidad=aleatorio.nextDouble();
			if (probabilidad<probCruce){
				elegidos[numCruce]=i;
				numCruce++;
			}
		}
		
		if ((numCruce % 2) == 1)
			numCruce--;
		
		//determinamos el punto de cruce
		puntCruce=aleatorio.nextInt(pob[0].getLongCromosoma());
		for (int i=0; i<numCruce; i=i+2)
			cruce(pob[elegidos[i]],pob[elegidos[i+1]],puntCruce);
		
	}
	
	private void cruce(Cromosoma padre, Cromosoma madre, int puntCruce){
		
		boolean[] hijo=new boolean[padre.getLongCromosoma()];
		boolean[] hija=new boolean[madre.getLongCromosoma()];
		
		for (int i=0; i<padre.getLongCromosoma(); i++){
			if (i<puntCruce){
				hijo[i]=padre.getGen(i);
				hija[i]=madre.getGen(i);
			}
			else{
				hijo[i]=madre.getGen(i);
				hija[i]=padre.getGen(i);
			}
				
		}
		
		padre.setGenes(hijo);
		madre.setGenes(hija);
		padre.evalua();
		madre.evalua();
	}
	


}
