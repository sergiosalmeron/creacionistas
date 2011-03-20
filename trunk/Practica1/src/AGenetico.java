import java.util.Random;


public class AGenetico {
	
	private Cromosoma[] pob;
	private int tamPob;
	private int numMaxGen;
	public Cromosoma elMejor; //TODO nunchakus 
	private int posMejor;
	private double probCruce;
	private double probMutacion;
	private double tolerancia;
	private int generacionActual=0;
	private double mediaPoblacion=0;
	private int elite=0;
	
	
	//Constructoras //////////////////////////////////////////////////////



	public AGenetico (int tamPob, int numMaxGen, double probCruce, double probMutacion, double tolerancia, int elite){
		this.tamPob=tamPob;
		this.numMaxGen=numMaxGen;
		this.probCruce=probCruce;
		this.probMutacion=probMutacion;
		this.tolerancia=tolerancia;
		pob = new Cromosoma[tamPob];
		if ((elite>=0)&&(elite<tamPob))
			this.elite=elite;
	}
	
	public AGenetico(){
		this.tamPob=1000;
		this.numMaxGen=100;
		this.probCruce=0.5;
		this.probMutacion=0.005;
		this.tolerancia=0.0001;
		pob = new Cromosoma[tamPob];
	}
	
	
	// Inicialización //////////////////////////////////////////////////
	
	public void inicializa(){
		//int longitud=calculaLongCromosomaF1();
		for (int i=0; i<tamPob; i++){
			pob[i]=new CromosomaF5(tolerancia);
			pob[i].evalua();
		}
		
	}
	/*
	private int calculaLongCromosomaF1(){
		CromosomaF1 aux=new CromosomaF1();
		return aux.calculaLongCromosoma(tolerancia);
	}*/
	
	
	// Evaluación población//////////////////////////////////////////////
	
	public void evaluarPoblacion(boolean maximizar){
		if (maximizar)
			evaluarPoblacionMaximizar();
		else
			evaluarPoblacionMinimizar();
	}
	
	private void evaluarPoblacionMaximizar(){
		double puntAcum=0;
		double aptitudMejor=0;
		double sumaAptitud=0;
		
		for (int i=0; i<pob.length; i++){
			sumaAptitud=sumaAptitud+pob[i].getAptitud();
			mediaPoblacion=mediaPoblacion+pob[i].getAptitud();
			if (pob[i].getAptitud()>aptitudMejor){
				this.posMejor=i;
				aptitudMejor=pob[i].getAptitud();
			}	
		}
		
		
		mediaPoblacion=mediaPoblacion/pob.length;
		
		for (int i=0; i<pob.length; i++){
			pob[i].setPuntuacion(pob[i].getAptitud()/sumaAptitud);
			puntAcum=puntAcum+pob[i].getPuntuacion();
			pob[i].setPuntAcum(puntAcum);
		}
		
		if ((elMejor==null) || (aptitudMejor>elMejor.getAptitud())) {
            elMejor = (Cromosoma)pob[posMejor].clone();
		}	
	}
	//*/
	private void evaluarPoblacionMinimizar(){
		double puntAcum=0;
		double aptitudMejor=0;
		double sumaAptitud=0;
		double aptitudMasAlta=0;//*/
		
		for (int i=0; i<pob.length; i++){
			//sumaAptitud=sumaAptitud+pob[i].getAptitud();
			//mediaPoblacion=mediaPoblacion+pob[i].getAptitud();
			if (pob[i].getAptitud()>aptitudMasAlta){
				//this.posMejor=i;
				aptitudMasAlta=pob[i].getAptitud();
			}	
		}
		
		for (int i=0; i<pob.length; i++){
			sumaAptitud=sumaAptitud+aptitudMasAlta-pob[i].getAptitud();
			mediaPoblacion=mediaPoblacion+aptitudMasAlta-pob[i].getAptitud();
			if (aptitudMasAlta-pob[i].getAptitud()>aptitudMejor){
				this.posMejor=i;
				aptitudMejor=aptitudMasAlta-pob[i].getAptitud();
			}	
		}
		
		
		mediaPoblacion=mediaPoblacion/pob.length;
		
		for (int i=0; i<pob.length; i++){
			pob[i].setPuntuacion((aptitudMasAlta-pob[i].getAptitud())/sumaAptitud);
			puntAcum=puntAcum+pob[i].getPuntuacion();
			pob[i].setPuntAcum(puntAcum);
		}
		
		if ((elMejor==null) || (aptitudMejor>aptitudMasAlta-elMejor.getAptitud())) {
            elMejor = (Cromosoma)pob[posMejor].clone();
		}	
	}
	
	public double getMediaPoblacion(){
		return mediaPoblacion;
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
	
	public void seleccionTorneoDet(int k){
		Cromosoma[] nuevaPob = new Cromosoma[tamPob];
		Random aleatorio = new Random();
		Cromosoma seleccionado = null;
		
		for (int i=0; i<tamPob; i++) {
            for (int j=0; j<k; j++){
            	if (j==0)
            		seleccionado = pob[aleatorio.nextInt(tamPob)];
            	else{
            		Cromosoma aux = pob[aleatorio.nextInt(tamPob)];
            		if (aux.getAptitud()>seleccionado.getAptitud())
            			seleccionado = aux;
            	}
            }
            nuevaPob[i]= (Cromosoma)seleccionado.clone();
		}
		pob=nuevaPob;
	}
	
	private Cromosoma[] getElementosElite(){
		Cromosoma[] resultado= new Cromosoma[elite];
		/*for (int i=0; i<elite;i++){
			resultado[i]=pob[i];
		}
		
		for (int i=elite; i<tamPob;i++){
			
		}*/
		return resultado;
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
	

	// Mutación ///////////////////////////////////////////////////////
	
	public void mutacion(){
		Random r=new Random();
		double probabilidad;
		for (int i=0; i<tamPob; i++){
			boolean mutado=false;
			for (int j=0; j<pob[i].getLongCromosoma(); j++){
				probabilidad=r.nextDouble();
				if (probabilidad<probMutacion){
					pob[i].mutaGen(j);
					mutado=true;
				}
			}
			if (mutado)
			pob[i].evalua();
		}
	}


}
