package logica;

/**
 * @author Aleix Garrido Oberink, Sergio Salmerón Majadas.
 * G07
 */
import java.util.Arrays;
import java.util.Random;


import mutaciones.MutaEnum;
import mutaciones.Mutacion;
import mutaciones.MutacionArbol;
import mutaciones.MutacionFuncion;
import mutaciones.MutacionTerminal;
import cruces.cruceArbol;

import utils.ComparadorCromos;

public class AGenetico {

	private Cromosoma[] pob;
	private int tamPob;
	private int numMaxGen;
	private Cromosoma elMejor;
	private int posMejor;
	private double probCruce;
	private double probMutacion;
	//private double tolerancia;
	// private int generacionActual = 0;
	private double mediaPoblacion = 0;
	
	//propiedades del árbol
	private int tamArbol;
	private int tamCruce;
	private int numIter;


	
	
	private Seleccion seleccion;
	private boolean maximizar;
	private int torneoAux;
	private double beta;
	// elite
	private int tamElite = 0;
	private Cromosoma[] elite;
	private ComparadorCromos comp;
	
	
	//mutaciones
	private MutaEnum mutacion;
	private Mutacion mutacionElegida;
	//mejoras
	private EnumMejoras mejora;
	private int limiteContractividad;

	// Constructora //////////////////////////////////////////////////////

	public AGenetico() {
		this.tamPob = 30;
		this.numMaxGen = 80;
		this.probCruce = 0.4;
		this.probMutacion = 0.075;
		this.tamElite = 0;
		torneoAux = 3;
		beta=1;
		pob = new Cromosoma[tamPob];
		comp = new ComparadorCromos();
		limiteContractividad=10;
		maximizar=true;
		
		tamArbol=3;
		tamCruce=5;
		numIter=4;
		Arbol.setValores(tamArbol, tamCruce, numIter);
	}

	// Inicialización //////////////////////////////////////////////////

	public void inicializa() {
		Casos casos=new Casos();
		elMejor = null;
		posMejor = -1;
		for (int i = 0; i < tamPob; i++) {
			pob[i]= new CromosomaArboles();
			pob[i].evalua();
		}

	}

	// Evaluación población//////////////////////////////////////////////
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


	private void evaluarPoblacionMinimizar() {
		double puntAcum = 0;
		double aptitudMejor = 0;
		double sumaAptitud = 0;
		double aptitudMasAlta = 0;
		mediaPoblacion = 0;

		for (int i = 0; i < pob.length; i++) {
			if (pob[i].getAptitud() > aptitudMasAlta) {
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

	// Selección /////////////////////////////////////////////////////

	public void seleccion() {
		switch (seleccion) {
		case Ruleta:
			seleccionRuleta();
			break;
		case Torneo:
			seleccionTorneoDet();
			break;
		case Ranking:
			seleccionRanking();
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

		// creamos nueva población
		Cromosoma[] nuevaPob = new Cromosoma[tamPob];
		for (int i = 0; i < tamPob; i++) {
			nuevaPob[i] = (Cromosoma) pob[elegidos[i]].clone();
		}

		// la población seleccionada pasa a ser la nueva
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
					if (maximizar) {
						if (aux.getAptitud() > seleccionado.getAptitud())
							seleccionado = aux;
					} else {
						if (aux.getAptitud() < seleccionado.getAptitud())
							seleccionado = aux;
					}

				}
			}
			nuevaPob[i] = (Cromosoma) seleccionado.clone();
		}
		pob = nuevaPob;
	}
	
	private void seleccionRanking(){
		//ordenamos la pob
		quickSort(pob, 0, tamPob-1);
		
		//creamos pob. nueva y clonamos dos primeros cromosomas
		Cromosoma[] nuevaPob = new Cromosoma[tamPob];
		nuevaPob[0] = (Cromosoma) pob[0].clone();
		nuevaPob[1] = (Cromosoma) pob[1].clone();
        int numPadres = 2;

        double[] fitnessSegments = rankPopulation();
        double entireSegment = fitnessSegments[fitnessSegments.length - 1];

        while (numPadres < tamPob) {

                double x = (double) (Math.random() * entireSegment);
                if (x <= fitnessSegments[0]) {

                        // El primer individuo fue seleccionado
                		nuevaPob[numPadres] = (Cromosoma) pob[0].clone();
                        numPadres++;
                } else
                        for (int i = 1; i < tamPob; i++)
                                if (x > fitnessSegments[i - 1] && x <= fitnessSegments[i]) {

                                        // El i-esimo individuo fue seleccionado
                                		nuevaPob[numPadres] = (Cromosoma) pob[i]
                                                        .clone();
                                        numPadres++;
                                }
        }

        pob = nuevaPob;

		
	}
	

	private double[] rankPopulation() {
		double[] fitnessSegments = new double[tamPob];

        for (int i = 0; i < fitnessSegments.length; i++) {
                double probIEsimo = (double) i / tamPob;
                probIEsimo = probIEsimo * 2 * (beta - 1);
                probIEsimo = beta - probIEsimo;
                probIEsimo = (double) probIEsimo * ((double) 1 / tamPob);
                if (i != 0)
                	fitnessSegments[i] = fitnessSegments[i - 1] + probIEsimo;
                else
                	fitnessSegments[i] = probIEsimo;
        }
        return fitnessSegments;

	}

	private void quickSort(Cromosoma[] poblacion, int izq, int dch) {
		if (dch <= izq)
            return;
		int i = particion(poblacion, izq, dch);
		quickSort(poblacion, izq, i - 1);
		quickSort(poblacion, i + 1, dch);

		
	}

	private int particion(Cromosoma[] poblacion, int izq, int dch) {
		int i = izq - 1;
        int j = dch;
        while (true) {

                while (menor(poblacion[++i], poblacion[dch]))
                	//Elem a intercambiar por la izq
                        ; 
                while (menor(poblacion[dch], poblacion[--j]))
                        // Elem a intercambiar por la dch
                        if (j == izq)
                                break; // ¿nos salimos del array?
                if (i >= j)
                        break; // ¿se han cruzado los indices?

                intercambio(poblacion, i, j);
        }

        intercambio(poblacion, i, dch);

        return i;

	}

	private boolean menor(Cromosoma cromosoma, Cromosoma cromosoma2) {
		return (cromosoma.getAptitud() < cromosoma2.getAptitud());
	}

	private void intercambio(Cromosoma[] poblacion, int i, int j) {
		Cromosoma intercamb = poblacion[i];
		poblacion[i] = poblacion[j];
		poblacion[j] = intercamb;

		
	}

	public void apartaElementosElite() {
		// en el array "resultado", a menor indice del elemento, menor
		// puntuación de este
		elite = new Cromosoma[tamElite];
		for (int i = 0; i < tamElite; i++) {
			elite[i] = (Cromosoma) pob[i].clone();
		}

		Arrays.sort(elite, comp);

		for (int i = tamElite; i < tamPob; i++) {
			seleccionaElite(pob[i]);
		}
	
	}

	private void seleccionaElite(Cromosoma c) {
		if (elite == null)
			return;
	
		if (elite[0] == null) {
			elite[0] = (Cromosoma) c.clone();
			return;
		}
		if (c.getPuntuacion() > elite[0].getPuntuacion()) {
			int i = 1;
			boolean colocado = false;
			Cromosoma aux = (Cromosoma) c.clone();
			elite[0] = aux;
			while ((i < elite.length) && (elite[i] != null) && (!colocado)) {
				if (c.getPuntuacion() > elite[i].getPuntuacion()) {
					elite[i - 1] = elite[i];
					i++;
				} else {
					elite[i - 1] = aux;
					colocado = true;
				}
			}
			if (i == elite.length)
				elite[i - 1] = aux;
		}
	}

	public void reinsertaElementosElite() {
		for (int i = 0; i < elite.length; i++) {
			if (elite[i] != null) {
				pob[i] = elite[i];
				elite[i] = null;
			}
		}
	}

	// Reproducción //////////////////////////////////////////////////

	public void reproduccion() {
		int[] elegidos = new int[tamPob];
		int numCruce = 0;
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
		
		//System.out.println("cruzan: "+numCruce);

		if ((numCruce % 2) == 1)
			numCruce--;

		for (int i = 0; i < numCruce; i = i + 2)
			cruce(pob[elegidos[i]], pob[elegidos[i + 1]]);

	}

	private void cruce(Cromosoma padre, Cromosoma madre) {
		cruceArbol cruce=new cruceArbol();
//System.out.println("el metodo \"cruce\" de AGenetico.java tiene una linea que hay que borrar: cruceElegido=new cruceArbol();");
		cruce.cruza(padre, madre);
		
		/*boolean[] hijo = new boolean[padre.getLongCromosoma()];
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
		madre.setGenes(hija);*/
		padre.evalua();
		madre.evalua();
	}

	// Mutación ///////////////////////////////////////////////////////

	public void mutacion() {
		Random r = new Random();
		double probabilidad;
		for (int i = 0; i < tamPob; i++) {
			boolean mutado = false;
			probabilidad = r.nextDouble();
			if (probabilidad < probMutacion) {
				mutacionElegida.muta(pob[i]);
				mutado = true;
			}
			/*for (int j = 0; j < pob[i].getLongCromosoma(); j++) {
				probabilidad = r.nextDouble();
				if (probabilidad < probMutacion) {
					//pob[i].mutaGen(j);
					mutacionElegida.muta(pob[i]);
					mutado = true;
				}
			}*/
			if (mutado)
				pob[i].evalua();
		}
	}

	// Getters y Setters ///////////////////////////////////////////////
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



	public double getElite() {
		return tamElite;
	}

	public void setElite(double tamElite) {
		this.tamElite = (int) Math.round(tamElite * tamPob);
	}

	public void setSeleccion(Seleccion seleccion) {
		this.seleccion = seleccion;
	}

	public Seleccion getSeleccion() {
		return seleccion;
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

	public boolean usaElite() {
		return tamElite > 0;
	}

	public Cromosoma getCromosomaMejor() {
		return elMejor;
	}

	public void setBeta(double beta) {
		this.beta = beta;
	}

	public double getBeta() {
		return beta;
	}
	
	public void setMutacion(MutaEnum mutacion) {
		switch (mutacion){
		case Terminal: mutacionElegida=new MutacionTerminal();break;
		case Función: mutacionElegida=new MutacionFuncion();break;
		case Árbol: mutacionElegida=new MutacionArbol();break;
		}
		
		this.mutacion = mutacion;
	}

	public MutaEnum getMutacion() {
		return mutacion;
	}
	
	public void setMejora(EnumMejoras mejora) {
		this.mejora = mejora;
	}

	public EnumMejoras getMejora() {
		return mejora;
	}
	
	public void setLimiteContractividad(int limite) {
		this.limiteContractividad = limite;
	}

	public int getLimiteContractividad() {
		return limiteContractividad;
	}
	
	public int getTamArbol() {
		return tamArbol;
	}

	public void setTamArbol(int tamArbol) {
		this.tamArbol = tamArbol;
		Arbol.setValores(tamArbol, tamCruce, numIter);
	}

	public int getTamCruce() {
		return tamCruce;
	}

	public void setTamCruce(int tamCruce) {
		this.tamCruce = tamCruce;
		Arbol.setValores(tamArbol, tamCruce, numIter);
	}

	public int getNumIter() {
		return numIter;
	}

	public void setNumIter(int numIter) {
		this.numIter=numIter;
		Arbol.setValores(tamArbol, tamCruce, numIter);
	}

}
