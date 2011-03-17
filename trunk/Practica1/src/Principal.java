
public class Principal {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		AGenetico AG=new AGenetico();
		AG.inicializa();
		for (int i=0; i<100; i++){
			AG.evaluarPoblacion(false);
			AG.seleccionRuleta();
			//AG.seleccionTorneoDet(3);
			AG.reproduccion();
			AG.mutacion();
			System.out.println(AG.elMejor.aptitud+" "+AG.getMediaPoblacion());
		}
		System.out.println(AG.elMejor.aptitud);
		System.out.println(AG.elMejor.fenotipo[0]);
		//System.out.println(AG.elMejor.fenotipo[1]);
	}

}
