package logica;

public class Arbol {
	public static int MAX_PROFUNDIDAD = 3;
	public static boolean INICIALIZACION_COMPLETA = false;
	public static double PROB_BAJAR_NODO = 0.6;
	
	private static double[] profundidadesGauss;
	
	private Tipo tipo;
	private int profundidad;
	private Arbol padre;
	private Arbol[] hijos;
	
	
	
	// devuelve un (nuevo) arbol aleatorio con el valor de profundidad traspasado
	// asi la profundidad verdadera del arbol es: (MAX_PROFUNDIDAD - profundidad)
	public static Arbol getRandArbol(int profundidad) {
		if (( !INICIALIZACION_COMPLETA && (Math.random() < Tipo.PROB_TERMINAL) )
				|| profundidad == MAX_PROFUNDIDAD)
			return getRandHoja(profundidad);  // terminal
		
		// else funcion:
		Arbol retVal = new Arbol();
		Arbol[] hijos;
		Tipo tipo = Tipo.getRandFuncion();
		
		retVal.setProfundidad(profundidad);
		retVal.setTipo(tipo);
		hijos = new Arbol[Tipo.getCantHijosDeTipo(tipo)];
		
		for (int i = 0; i < hijos.length; i++) {
			hijos[i] = getRandArbol(profundidad + 1);
		}
		retVal.setHijos(hijos);
		
		return retVal;
	}
	
	public static Arbol getRandHoja(int profundidad) {
		return new Arbol(Tipo.getRandTerminal(), null, profundidad);
	}
	
	
	// sustituye el arbol viejo por el arbol nuevo
	public static void sustituyeArbol(Arbol viejo, Arbol nuevo) {
		viejo.setTipo(nuevo.getTipo());
		viejo.setHijos(nuevo.getHijos());
		if (viejo.getProfundidad() != nuevo.getProfundidad())
			viejo.poda();
	}
	
	public static void intercambiarArboles(Arbol arbol1, Arbol arbol2) {
		// si uno de los arboles es subarbol del otro no hacemos nada 
		for (Arbol tmpArbol = arbol2; tmpArbol != null; tmpArbol = tmpArbol.getPadre())
			if (arbol1.equals(tmpArbol)) return;
		for (Arbol tmpArbol = arbol1; tmpArbol != null; tmpArbol = tmpArbol.getPadre())
			if (arbol2.equals(tmpArbol)) return;

		// intercambiamos el tipo y los hijos de ambos árboles
		Tipo tmpTipo = arbol1.getTipo();
		Arbol[] tmpHijos = arbol1.getHijos();
		
		arbol1.setTipo(arbol2.getTipo());
		arbol1.setHijos(arbol2.getHijos());
		arbol1.poda();
		
		arbol2.setTipo(tmpTipo);
		arbol2.setHijos(tmpHijos);
		arbol2.poda();
	}
	
	
	public void cambiaFuncion() {
		Tipo tipoNuevo;
		do {
		   tipoNuevo = Tipo.getRandFuncion();
		} while (this.tipo == tipoNuevo);

		Arbol[] hijosNuevos = new Arbol[Tipo.getCantHijosDeTipo(tipoNuevo)];
		
		int cantHijosCopiados = Math.min(
									hijosNuevos.length,
									this.getCantHijos()
								);
		// copiar hijos viejos
		int i = 0;
		for (; i < cantHijosCopiados; i++) {
			hijosNuevos[i] = hijos[i];
		}
		// anade hijos nuevos (si hay demasiado poco viejos)
		for (; i < hijosNuevos.length; i++) {
			hijosNuevos[i] = getRandArbol(profundidad + 1);
		}
		
		setTipo(tipoNuevo);
		setHijos(hijosNuevos);			
	}
	
	// asegura q no esta la misma hoja que antes
	public void setNuevaHoja(){
		Tipo hojaNueva;
		do {
			hojaNueva = Tipo.getRandTerminal();
		} while (tipo == hojaNueva);
		setTipo(hojaNueva);
	}
	
	public Arbol(Tipo tipo, Arbol[] hijos, int profundidad) {
		if (Tipo.isTerminal(tipo) && hijos != null && hijos.length != 0)
			Tipo.printErr("Terminales no tienen hijos.");
		if ((tipo == Tipo.DU || tipo == Tipo.EQ) && (hijos == null || hijos.length != 2))
			Tipo.printErr("SIC y PROGN2 tienen que tener dos hijos.");
		if (tipo == Tipo.MP || tipo == Tipo.MM || tipo == Tipo.NOT && (hijos == null || hijos.length != 3))
			Tipo.printErr("PROGN3 tiene que tener tres hijos.");
		
		setTipo(tipo);
		setHijos(hijos);
		setProfundidad(profundidad);
	}
	
	private Arbol() {}
	
	@Override
	public Arbol clone() {
		Arbol a = new Arbol();

		a.setProfundidad(profundidad);
		a.setTipo(tipo);
		
		if (Tipo.isTerminal(tipo)) return a;
		
		a.hijos = new Arbol[hijos.length];
		for (int i = 0; i < hijos.length; i++) {
			a.setHijo(i, hijos[i].clone());
		}
		
		return a; // el padre de a es "null"
	}
	
	@Override
	public String toString() {
		return toString("");
	}
	
	private String toString(String blancos) {
		String retVal = blancos + tipo.name() + " (" + super.toString() + ")";
		if (hijos == null) return retVal;
		for (int i = 0; i < hijos.length; i++) {
			retVal += "\n" + hijos[i].toString(blancos + "  ");
		}
		return retVal;
	}
	
	// devuelve una hoja del Arbol aleatorio
	public Arbol getRandSubHoja() {
		if (Tipo.isTerminal(tipo)) return this;
		
		return hijos[(int)(Math.random() * hijos.length)].getRandSubHoja();
	}
	
	// devuelve una funcion del Arbol aleatorio
	public Arbol getRandSubFuncion() {
		if (Tipo.isTerminal(tipo)) return null; // solo en el caso de que el arbol no tiene hijos
		
		if (Math.random() < 1 - PROB_BAJAR_NODO) return this;
		
		int[] posibilidadesParaBajar = new int[3];
		int cantidadPosibilidades = 0;
		for (int i = 0; i < getCantHijos(); i++) {
			if (Tipo.isFuncion(getHijo(i).getTipo())) {
				posibilidadesParaBajar[cantidadPosibilidades] = i;
				cantidadPosibilidades++;
			}
		}
		
		if (cantidadPosibilidades == 0) return this;
		
		return hijos[posibilidadesParaBajar[(int)(Math.random() * cantidadPosibilidades)]].getRandSubFuncion();
	}
	
	public Arbol getRandSubArbol() {
		return (Math.random() < 0.5 || MAX_PROFUNDIDAD <= 1)
				? getRandSubArbolClasico()
				: getRandSubArbolGauss()
				;
	}
	
	public Arbol getRandSubArbolClasico() {
		if (Tipo.isTerminal(tipo)) return this;
		
		if (Math.random() < 1 - PROB_BAJAR_NODO) return this;
		
		return hijos[(int)(Math.random() * hijos.length)].getRandSubArbol();
		
	}
	
	/**
	 * Distribucion gaussiana que pretende establecer cero como probabilidad de elegir como
	 * sub-árbol el árbol completo o una hoja, y concentrar la probabilidad en los nodos intermedios
	 */
	public Arbol getRandSubArbolGauss() {
		if (profundidadesGauss == null
				|| profundidadesGauss.length != (MAX_PROFUNDIDAD - 1)) {
			// profundidadesGauss todavia no fue procesado con esta MAX_PROFUNDIDAD
			profundidadesGauss = new double[MAX_PROFUNDIDAD - 1];
			
			for (int i = 0; i < profundidadesGauss.length; i++) {
			    profundidadesGauss[i] = (MAX_PROFUNDIDAD - (i+1)) * (i+1) * 6.0
			    / (MAX_PROFUNDIDAD * (MAX_PROFUNDIDAD+1) * (MAX_PROFUNDIDAD-1))
			    + ((i==0)?0:profundidadesGauss[i-1]);
			}
		}
		  
		double rand = Math.random();
		int i;
		for ( i = 0; i < profundidadesGauss.length - 1; i++)
			if (rand < profundidadesGauss[i]) break;
		
		return getRandSubArbolDeProfundidad(i+1);
	}
	
	public Arbol getRandSubArbolDeProfundidad(int profundidad) {
		if (Tipo.isTerminal(tipo) || this.profundidad == profundidad) return this;
		
		return hijos[(int)(Math.random() * hijos.length)].getRandSubArbolDeProfundidad(profundidad);
	}
	
	/**
	 * Establece la profundidad recursivamente a los hijos, empezando por el Arbol actual
	 * y poda las ramas demasiado largas.
	 */
	public void poda(int profundidad) {
		setProfundidad(profundidad);

		if (Tipo.isTerminal(tipo)) return; // no hay hijos
		
		if (profundidad == MAX_PROFUNDIDAD) { // poda  la rama sustituyendola por una hoja
			setTipo(Tipo.getRandTerminal());
			setHijos(null);
			return;
		}
		
		for (int i = 0; i < hijos.length; i++) { // continua podando los hijos. 
			hijos[i].poda(profundidad + 1);
		}
	}
	
	public void poda() {
		poda(getProfundidad());
	}
	
	
	public Arbol getPadre() {
		return padre;
	}
	public void setPadre(Arbol padre) {
		this.padre = padre;
	}
	public Arbol[] getHijos() {
		return hijos;
	}

	// Establece los hijos para el objeto Arbol
	public void setHijos(Arbol[] hijos) {
		this.hijos = hijos;
		if (hijos == null) return;
		// Establece el padre para cada hijo
		for (int i = 0; i < hijos.length; i++) {
			hijos[i].setPadre(this);
		}
	}
	public Arbol getHijo(int index) {
		return hijos[index];
	}
	// Establece el hijo index para el objeto Arbol
	public void setHijo(int index, Arbol hijo) {
		this.hijos[index] = hijo;
		// Establece el padre para cada hijo
		hijo.setPadre(this);
	}
	public int getCantHijos() {
		return (hijos == null)? 0 : hijos.length;
	}
	public Tipo getTipo() {
		return tipo;
	}
	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}
	public int getProfundidad() {
		return profundidad;
	}
	public void setProfundidad(int profundidad) {
		this.profundidad = profundidad;
	}
	public boolean isMaxProfundidad() {
		return (profundidad == MAX_PROFUNDIDAD);
	}
	public void setProfundidadRecursivo(int profundidad) {
		this.profundidad = profundidad;
		for (int i = 0; i < getCantHijos(); i++) {
			hijos[i].setProfundidadRecursivo(profundidad + 1);
		}
	}

}
