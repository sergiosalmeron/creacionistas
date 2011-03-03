
public class Principal {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		AGenetico AG=new AGenetico();
		AG.inicializa();
		for (int i=0; i<50; i++){
		AG.evaluarPoblacion();
		AG.seleccionRuleta();
		AG.reproduccion();
		AG.mutacion();
		}
	}

}
