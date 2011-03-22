package logica;

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
	private int generacionActual = 0;
	private double mediaPoblacion = 0;
	private double elite = 0;
	private Funcion funcion;
	private Seleccion seleccion;
	private boolean maximizar;
	private int f4Aux;
	private int torneoAux;

	// Constructora //////////////////////////////////////////////////////

	public AGenetico() {
		this.tamPob = 100;
		this.numMaxGen = 100;
		this.probCruce = 0.4;
		this.probMutacion = 0.005;
		this.tolerancia = 0.0001;
		this.elite = 0;
		this.f4Aux = 1;
		torneoAux=3;
		pob = new Cromosoma[tamPob];
	}

	// Inicializaci�n //////////////////////////////////////////////////

	public void inicializa() {
		elMejor = null;
		posMejor = -1;
		// int longitud=calculaLongCromosomaF1();
		for (int i = 0; i < tamPob; i++) {
			switch (funcion) {
			case F1:
				pob[i] = new CromosomaF1(tolerancia);
				maximizar = true;
				break;
			case F2:
				pob[i] = new CromosomaF2(tolerancia);
				maximizar = true;
				break;
			case F3:
				pob[i] = new CromosomaF3(tolerancia);
				maximizar = false;
				break;
			case F4:
				pob[i] = new CromosomaF4(f4Aux, tolerancia);
				maximizar = false;
				break;
			case F5:
				pob[i] = new CromosomaF5(tolerancia);
				maximizar = false;
				break;
			}
			pob[i].evalua();
		}

	}

	/*
	 * private int calculaLongCromosomaF1(){ CromosomaF1 aux=new CromosomaF1();
	 * return aux.calculaLongCromosoma(tolerancia); }
	 */

	// Evaluaci�n poblaci�n//////////////////////////////////////////////
	public void evaluarPoblacion() {
		if (maximizar)
			evaluarPoblacionMaximizar();
		else
			evaluarPoblacionMinimizar();
	}

	private void evaluarPoblacionMaximizar() {
		double puntAcum = 0;
		double aptitudMejor = 0;
		double sumaAptitud = 0;
		mediaPoblacion = 0;

		for (int i = 0; i < pob.length; i++) {
			sumaAptitud = sumaAptitud + pob[i].getAptitud();
			mediaPoblacion = mediaPoblacion + pob[i].getAptitud();
			if (pob[i].getAptitud() > aptitudMejor) {
				this.posMejor = i;
				aptitudMejor = pob[i].getAptitud();
			}
		}

		mediaPoblacion = mediaPoblacion / pob.length;

		for (int i = 0; i < pob.length; i++) {
			pob[i].setPuntuacion(pob[i].getAptitud() / sumaAptitud);
			puntAcum = puntAcum + pob[i].getPuntuacion();
			pob[i].setPuntAcum(puntAcum);
		}

		if ((elMejor == null) || (aptitudMejor > elMejor.getAptitud())) {
			elMejor = (Cromosoma) pob[posMejor].clone();
		}
	}

	// */
	private void evaluarPoblacionMinimizar() {
		double puntAcum = 0;
		double aptitudMejor = 0;
		double sumaAptitud = 0;
		double aptitudMasAlta = 0;// */
		mediaPoblacion = 0;

		for (int i = 0; i < pob.length; i++) {
			// sumaAptitud=sumaAptitud+pob[i].getAptitud();
			// mediaPoblacion=mediaPoblacion+pob[i].getAptitud();
			if (pob[i].getAptitud() > aptitudMasAlta) {
				// this.posMejor=i;
				aptitudMasAlta = pob[i].getAptitud();
			}
		}

		for (int i = 0; i < pob.length; i++) {
			sumaAptitud = sumaAptitud + aptitudMasAlta - pob[i].getAptitud();
			mediaPoblacion = mediaPoblacion + pob[i].getAptitud();
			if (aptitudMasAlta - pob[i].getAptitud() > aptitudMejor) {
				this.posMejor = i;
				aptitudMejor = aptitudMasAlta - pob[i].getAptitud();
			}
		}

		mediaPoblacion = mediaPoblacion / pob.length;

		for (int i = 0; i < pob.length; i++) {
			pob[i].setPuntuacion((aptitudMasAlta - pob[i].getAptitud())
					/ sumaAptitud);
			puntAcum = puntAcum + pob[i].getPuntuacion();
			pob[i].setPuntAcum(puntAcum);
		}

		if ((elMejor == null)
				|| (aptitudMejor > aptitudMasAlta - elMejor.getAptitud())) {
			elMejor = (Cromosoma) pob[posMejor].clone();
		}
	}

	public double getMediaPoblacion() {
		return mediaPoblacion;
	}

	public double getMejorLocal() {
		return pob[this.posMejor].aptitud;
	}

	// Selecci�n /////////////////////////////////////////////////////

	public void seleccion() {
		switch (seleccion) {
		case Ruleta:
			seleccionRuleta();
			break;
		case Torneo:
			seleccionTorneoDet();
			break;
		}

	}

	private void seleccionRuleta() {
		int[] elegidos = new int[tamPob];
		double probabilidad;
		int posicionElegido;

		Random aleatorio = new Random();

		// Seleccionamos mediante ruleta
		for (int i = 0; i < tamPob; i++) {
			probabilidad = aleatorio.nextDouble();
			posicionElegido = 0;

			while ((probabilidad > pob[posicionElegido].getPuntAcum())
					&& (posicionElegido < tamPob))
				posicionElegido++;

			elegidos[i] = posicionElegido;
		}

		// creamos nueva poblaci�n
		Cromosoma[] nuevaPob = new Cromosoma[tamPob];
		for (int i = 0; i < tamPob; i++) {
			nuevaPob[i] = (Cromosoma) pob[elegidos[i]].clone();
		}

		// la poblaci�n seleccionada pasa a ser la nueva
		pob = nuevaPob;

	}

	private void seleccionTorneoDet() {
		Cromosoma[] nuevaPob = new Cromosoma[tamPob];
		Random aleatorio = new Random();
		Cromosoma seleccionado = null;

		for (int i = 0; i < tamPob; i++) {
			for (int j = 0; j < torneoAux; j++) {
				if (j == 0)
					seleccionado = pob[aleatorio.nextInt(tamPob)];
				else {
					Cromosoma aux = pob[aleatorio.nextInt(tamPob)];
					if (maximizar){
						if (aux.getAptitud() > seleccionado.getAptitud())
							seleccionado = aux;
					}
					else{
						if (aux.getAptitud() < seleccionado.getAptitud())
							seleccionado = aux;
					}
					
				}
			}
			nuevaPob[i] = (Cromosoma) seleccionado.clone();
		}
		pob = nuevaPob;
	}

	private Cromosoma[] getElementosElite() {
		// Cromosoma[] resultado= new Cromosoma[elite];
		/*
		 * for (int i=0; i<elite;i++){ resultado[i]=pob[i]; }
		 * 
		 * for (int i=elite; i<tamPob;i++){
		 *  }
		 */
		return null;
	}

	// Reproducci�n //////////////////////////////////////////////////

	public void reproduccion() {
		int[] elegidos = new int[tamPob];
		int numCruce = 0;
		int puntCruce;
		double probabilidad;
		Random aleatorio = new Random();

		// vemos cuantos elementos se van a reproducir
		for (int i = 0; i < tamPob; i++) {
			probabilidad = aleatorio.nextDouble();
			if (probabilidad < probCruce) {
				elegidos[numCruce] = i;
				numCruce++;
			}
		}

		if ((numCruce % 2) == 1)
			numCruce--;

		// determinamos el punto de cruce
		puntCruce = aleatorio.nextInt(pob[0].getLongCromosoma());
		for (int i = 0; i < numCruce; i = i + 2)
			cruce(pob[elegidos[i]], pob[elegidos[i + 1]], puntCruce);

	}

	private void cruce(Cromosoma padre, Cromosoma madre, int puntCruce) {

		boolean[] hijo = new boolean[padre.getLongCromosoma()];
		boolean[] hija = new boolean[madre.getLongCromosoma()];

		for (int i = 0; i < padre.getLongCromosoma(); i++) {
			if (i < puntCruce) {
				hijo[i] = padre.getGen(i);
				hija[i] = madre.getGen(i);
			} else {
				hijo[i] = madre.getGen(i);
				hija[i] = padre.getGen(i);
			}

		}

		padre.setGenes(hijo);
		madre.setGenes(hija);
		padre.evalua();
		madre.evalua();
	}

	// Mutaci�n ///////////////////////////////////////////////////////

	public void mutacion() {
		Random r = new Random();
		double probabilidad;
		for (int i = 0; i < tamPob; i++) {
			boolean mutado = false;
			for (int j = 0; j < pob[i].getLongCromosoma(); j++) {
				probabilidad = r.nextDouble();
				if (probabilidad < probMutacion) {
					pob[i].mutaGen(j);
					mutado = true;
				}
			}
			if (mutado)
				pob[i].evalua();
		}
	}

	// Getters y Setters
	public int getTamPob() {
		return tamPob;
	}

	public void setTamPob(int tamPob) {
		this.tamPob = tamPob;
		pob = new Cromosoma[tamPob];
	}

	public int getNumMaxGen() {
		return numMaxGen;
	}

	public void setNumMaxGen(int numMaxGen) {
		this.numMaxGen = numMaxGen;
	}

	public double getProbCruce() {
		return probCruce;
	}

	public void setProbCruce(double probCruce) {
		this.probCruce = probCruce;
	}

	public double getProbMutacion() {
		return probMutacion;
	}

	public void setProbMutacion(double probMutacion) {
		this.probMutacion = probMutacion;
	}

	public double getTolerancia() {
		return tolerancia;
	}

	public void setTolerancia(double tolerancia) {
		this.tolerancia = tolerancia;
	}

	public double getElite() {
		return elite;
	}

	public void setElite(double elite) {
		this.elite = elite;
	}

	public void setFuncion(Funcion funcion) {
		this.funcion = funcion;
	}

	public Funcion getFuncion() {
		return funcion;
	}

	public void setSeleccion(Seleccion seleccion) {
		this.seleccion = seleccion;
	}

	public Seleccion getSeleccion() {
		return seleccion;
	}

	public void setF4Aux(int f4Aux) {
		this.f4Aux = f4Aux;
	}

	public int getF4Aux() {
		return f4Aux;
	}

	public void setTorneoAux(int torneoAux) {
		this.torneoAux = torneoAux;
	}

	public int getTorneoAux() {
		return torneoAux;
	}

	public double getElMejor() {
		return elMejor.aptitud;
	}




}
