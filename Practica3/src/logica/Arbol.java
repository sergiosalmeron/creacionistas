package logica;

import java.util.ArrayList;

import com.sun.java.swing.plaf.motif.resources.motif;

public class Arbol {
	public static int MAX_PROFUNDIDAD = 10;
	public static boolean INICIALIZACION_COMPLETA = false;
	public static double PROB_BAJAR_NODO = 0.6;
	
	private final Character[] universal={'U','N','I','V','E','R','S','A','L'};
	
	private static double[] profundidadesGauss;
	private static final int limiteIteracionesDU=10;
	
	private Tipo tipo;
	private int profundidad;
	private Arbol padre;
	private Arbol[] hijos;
	
	private boolean valorBooleanoTerminal=true;
	
	
	//(Math.random() < Tipo.PROB_TERMINAL) 
	// devuelve un (nuevo) arbol aleatorio con el valor de profundidad traspasado
	// asi la profundidad verdadera del arbol es: (MAX_PROFUNDIDAD - profundidad)
	public static Arbol getRandArbol(int profundidad, Tipo padre) {
		if ( profundidad == MAX_PROFUNDIDAD )
			return getRandHoja(profundidad);  // terminal
		

		if (padre!=null){
			if ((padre==Tipo.MP)||(padre==Tipo.MM))
				return getRandHoja(profundidad);
			else{
				if (profundidad == MAX_PROFUNDIDAD-1){
					Tipo tipo;
					if (Math.random()<0.5)
						tipo=Tipo.MM;
					else
						tipo=Tipo.MP;
					return getArbFunc(profundidad, tipo);
				}
				else
					return getRandFunc(profundidad);
			}
		}
		
		if (Math.random() < Tipo.PROB_TERMINAL) 
			return getRandHoja(profundidad);
		
		
		
		return getRandFunc(profundidad);
	}
	
	public static Arbol getRandHoja(int profundidad) {
		return new Arbol(Tipo.getRandTerminal(), null, profundidad);
	}
	
	public static Arbol getRandFunc(int profundidad) {
		return getArbFunc(profundidad, Tipo.getRandFuncion());
	}
	
	public static Arbol getArbFunc(int profundidad, Tipo tipo) {
		Arbol retVal= new Arbol();
		Arbol[] hijos;
		
		retVal.setProfundidad(profundidad);
		retVal.setTipo(tipo);
		hijos = new Arbol[Tipo.getCantHijosDeTipo(tipo)];
		
		for (int i = 0; i < hijos.length; i++) {
			hijos[i] = getRandArbol(profundidad + 1, tipo);
		}
		retVal.setHijos(hijos);
		
		return retVal;
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
	
	
	public void muta(){
		switch (tipo){
		case MP:
			setTipo(Tipo.MM);
			break;
		case MM:
			setTipo(Tipo.MP);
			break;
		case DU:
			setTipo(Tipo.EQ);
			break;
		case NOT:
			break;
		case EQ:
			setTipo(Tipo.DU);
			break;
		case CP:
		case BS:
		case SN:
			Tipo tipoNuevo;
			do {
				   tipoNuevo = Tipo.getRandTerminal();
				} while (this.tipo == tipoNuevo);
			setTipo(tipoNuevo);
			break;
		}
	}
	
	public void mutacionTerminal(){
		int a=getNumElementos();
		double b=Math.random()*a;
		mutaTerminal(0, b);
	}
	
	public void mutacionFuncional(){
		int a=getNumElementos()-1;
		if (a>=1){
			double b=Math.random()*a;
			mutaFuncional(0, b);
		}
	}
	
	public void mutacionArbol(){
		int a=getNumElementos();
		if (a>=1){
			double b=Math.random()*a;
			mutaArbol(0, b, null);
		}
	}
	
	private int mutaTerminal(int a, double p){
		if (a>=0){
			if ((hijos==null)||(hijos.length==0)){
				if(a+1>=p){
					muta();
					//System.out.println("muto en "+(a+1));
					return -1;
				}
				else
					return a+1;
			}	
			else{
				int b=a+1;
				int i=0;
				while ((b>=0)&&(i<hijos.length)){
					b=hijos[i].mutaTerminal(b,p);
					i++;
				}
				return b;
			}
		}
		else 
			return -1;
		
	}
	
	private int mutaFuncional(int a, double p){
		if (a>=0){
			if ((hijos==null)||(hijos.length==0)){
					return a+1;
			}	
			else{
				int b=a+1;
				if (b>=p){
					//System.out.println("muto en "+b);
					muta();
					return -1;
				}
				else{
					int i=0;
					while ((b>=0)&&(i<hijos.length)){
						b=hijos[i].mutaFuncional(b,p);
						i++;
					}
					return b;
				}
				
			}
		}
		else 
			return -1;
		
	}
	
	private int mutaArbol(int a, double p, Tipo padre){
		if (a>=0){
			if ((hijos==null)||(hijos.length==0)){
				if(a+1>p){
					Arbol yo=getRandArbol(this.profundidad, padre);
					hijos=yo.hijos;
					tipo=yo.tipo;
					System.out.println("muto en "+(a+1));
					//muta();
					return -1;
				}
				else
					return a+1;
			}	
			else{
				int b=a+1;
				if (b>=p){
					Arbol yo=getRandArbol(this.profundidad, padre);
					hijos=yo.hijos;
					tipo=yo.tipo;
					System.out.println("muto en "+(a+1));
					return -1;
				}
				else{
					int i=0;
					while ((b>=0)&&(i<hijos.length)){
						b=hijos[i].mutaArbol(b,p,tipo);
						i++;
					}
					return b;
				}
				
			}
		}
		else 
			return -1;
		
	}
	
	public int getNumElementos(){
		return getNumElementos(0);
	}
	
	private int getNumElementos(int a){
		if ((hijos==null)||(hijos.length==0))
			return a+1;
		else{
			int b=a;
			for (int i=0;i<hijos.length;i++){
				b=hijos[i].getNumElementos(b);
			}
			return b+1;
		}
		
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
			hijosNuevos[i] = getRandArbol(profundidad + 1, tipoNuevo);
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
	
	public int getEvaluacion(){
		int eval=0;
		String caso;
		for (int i=0; i<Casos.getNumCasos(); i++){
			caso=Casos.getCaso(i);
			if (evaluaCadena(caso))
				eval++;
		}
		return eval;
	}
	
	public boolean evaluaCadena(String cadena){
		ArrayList<Character> mesa=new ArrayList<Character>();
		ArrayList<Character> pila=new ArrayList<Character>();
		boolean limite=false;
		for (int i=0;i<cadena.length()-1;i++){
			char a=cadena.charAt(i);
			if (a=='*')
				limite=true;
			else{
				if(a!=' '){
					//Orden invertido!!!!
					if (!limite)
						pila.add(a);
					else
						mesa.add(a);
				}
				
			}
		}
	//	System.out.println("Pila antes: "+ pila);
	//	System.out.println("Mesa antes: "+mesa);
		evalua(pila, mesa);
	//	System.out.println("Pila después: "+ pila);
	//	System.out.println("Mesa después: "+mesa);
		boolean resultado=true;
		if (pila.size()==universal.length)
			for (int i=0;i<universal.length;i++){
				if (universal[i]!=pila.get(i))
					resultado=false;
			}
		else
			resultado=false;
		
		
		
		return resultado;
	}
	
	private boolean evalua(ArrayList<Character> pila, ArrayList<Character> mesa){
		boolean resultado=false;
		switch (tipo){
		case MP:
			resultado=procesaMP(pila, mesa);
			break;
		case MM:
			resultado=procesaMM(pila, mesa);
			break;
		case DU:
			resultado=procesaDU(pila, mesa);
			break;
		case NOT:
			resultado=procesaNOT(pila, mesa);
			break;
		case EQ:
			resultado=procesaEQ(pila, mesa);
			break;
		case CP:
			if (getCP(pila)!=null)
				resultado=valorBooleanoTerminal;
			else
				resultado=false;
			break;
		case BS:
			if (getBS(pila)!=null)
				resultado=valorBooleanoTerminal;
			else
				resultado=false;
			break;
		case SN:
			if (getSN(pila)!=null)
				resultado=valorBooleanoTerminal;
			else
				resultado=false;
			break;
		}
		return resultado;
	}
	
	private boolean procesaMP(ArrayList<Character> pila, ArrayList<Character> mesa){
		boolean resultado=false;
		if ( (hijos.length==1) && (hijos[0].tipo==Tipo.SN) ){
			Character sn=getSN(pila);
			if (mesa.contains(sn)){
				pila.add(sn);
				mesa.remove(sn);
				resultado=true;
			}
		}
		return resultado;
	}
	
	private boolean procesaMM(ArrayList<Character> pila, ArrayList<Character> mesa){
		boolean resultado=false;	
		if ( (hijos.length==1)){
			boolean mover=false;
			switch (hijos[0].tipo){
			case SN:
				Character a=getSN(pila);
				if ((a!=null)&&(pila.contains(a)));
					mover=true;
				break;
			case CP:
				Character b=getCP(pila);
				if ((b!=null)&&(pila.contains(b)));
					mover=true;
				break;
			case BS:
				Character c=getBS(pila);
				if ((c!=null)&&(pila.contains(c)));
					mover=true;
				break;
			}
			if ((mover)&&(pila.size()>0)){
				mesa.add(getCP(pila));
				pila.remove(pila.size()-1);
				resultado=true;
			}
		}
		return resultado;
	}
	
	private boolean procesaDU(ArrayList<Character> pila, ArrayList<Character> mesa){
		boolean resultado=false;
		int iteraciones=0;
		if (hijos.length==2){
			if (Tipo.isFuncion(hijos[0].getTipo())){
				hijos[0].evalua(pila, mesa);
				iteraciones++;
			}
			resultado=hijos[1].evalua(pila, mesa);
			while ((!resultado)&&(iteraciones<limiteIteracionesDU)){
				if (Tipo.isFuncion(hijos[0].getTipo()))
					hijos[0].evalua(pila, mesa);
				iteraciones++;
				resultado=hijos[1].evalua(pila, mesa);
			}
			return resultado;
		}
		
		
		return resultado;
	}
	
	private boolean procesaNOT(ArrayList<Character> pila, ArrayList<Character> mesa){	
		if ( (hijos.length==1)){
			return !hijos[0].evalua(pila, mesa);
		}
		else
			return false;
	}
	
	private boolean procesaEQ(ArrayList<Character> pila, ArrayList<Character> mesa){
		if ( (hijos.length==2)){
			return hijos[0].evalua(pila, mesa)==hijos[1].evalua(pila, mesa);
		}
		else
			return false;
	}
	

	private Character getCP(ArrayList<Character> pila){
		if (pila.size()>0)
			return pila.get(pila.size()-1);
		else
			return null;
	}
	
	private Character getBS(ArrayList<Character> pila){
		int posRes=getPosBS(pila);
		if (posRes<0)
			return null;
		else
			return universal[posRes];
	}
	
	private int getPosBS(ArrayList<Character> pila){
		if (pila.size()<=0)
			return -1;
		else{
			int posRes=-1;
			boolean limite=false;
			for (int i=0;i<pila.size();i++){
				if ((universal[i]!=pila.get(i))&&(posRes==-1)){
					posRes=i;
					limite=true;
				}
			}
			if (posRes<=0){
				if (limite==true)
					return -1;
				else
					return pila.size()-1;
			}
			else
				return posRes-1;
		}
	}
	
	private Character getSN(ArrayList<Character> pila){
		Character a=getBS(pila);
		if (a!=null){
			int posRes=getOrdinalUniversal(a);
			posRes++;
			if (posRes>=universal.length)
				return null;
			else
				return universal[posRes];
		}
		else{
			if (pila.contains(universal[0]))
				return null;
			else
				return universal[0];
		}
			
	}
	
	private int getOrdinalUniversal(char a){
		boolean encontrado=false;
		int i=0;
		while((!encontrado)&&(i<universal.length)){
			if (a==universal[i])
				encontrado=true;
			else
				i++;
		}
		if (encontrado)
			return i;
		else
			return -1;
	}
	
	

}
