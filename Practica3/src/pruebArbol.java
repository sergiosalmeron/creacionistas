import logica.Arbol;
import logica.Casos;


public class pruebArbol {
	public static void main (String[] args){
		Casos casos=new Casos();
		Arbol a=Arbol.getRandArbol(0, null);
		//Arbol b=Arbol.getRandArbol(0);
		System.out.println(a);
		System.out.println("--------");
		//a.evaluaCadena("U V A * E R I N L S *");
		System.out.println(a.getEvaluacion());
		/*System.out.println(b);
		System.out.println("--------");*/
		//ejecuta(a,b);
		/*System.out.println(a);
		System.out.println("--------");
		System.out.println(b);
		System.out.println("--------");*/
	}
	
	private static void ejecuta(Arbol crom1, Arbol crom2) {
		Arbol a = crom1.getRandSubArbol();
		Arbol b = crom2.getRandSubArbol();
		System.out.println(a);
		//System.out.println("--------");
		//System.out.println(b);
		//System.out.println("--------");

		//Arbol.intercambiarArboles(a, b);
		
		a.evaluaCadena("U V A * E R I N L S *");

	}


}
