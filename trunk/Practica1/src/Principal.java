
public class Principal {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		AGenetico AG=new AGenetico();
		AG.inicializa();
		for (int i=0; i<100; i++){
		AG.evaluarPoblacion();
		AG.seleccionRuleta();
		AG.reproduccion();}

		int j=5;
		j++;
	}

}
