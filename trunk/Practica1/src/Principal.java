
public class Principal {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		AGenetico AG=new AGenetico();
		AG.inicializa();
		for (int i=0; i<1000; i++){
		AG.evaluarPoblacion();
		AG.seleccionRuleta();
		AG.reproduccion();
		AG.mutacion();
		}
		System.out.println(AG.elMejor.aptitud);
		System.out.println(AG.elMejor.fenotipo[0]);
		System.out.println(AG.elMejor.fenotipo[1]);
	}

}
