import cruces.Cruce;
import cruces.OX;
import logica.Cromosoma;
import logica.CromosomaCiudades;


public class pruebas {

	
	public static void main (String[] args){
		Cromosoma a=new CromosomaCiudades();
		Cromosoma b=new CromosomaCiudades();
		System.out.println("A:");
		for (int i=0;i<a.getLongCromosoma();i++){
			System.out.print(a.getGen(i)+",");
		}
		System.out.println();
		System.out.println("B:");
		for (int i=0;i<a.getLongCromosoma();i++){
			System.out.print(b.getGen(i)+",");
		}
		System.out.println();
		
		Cruce c=new OX();
		
		c.cruza(a, b);
		
		
		System.out.println("A:");
		for (int i=0;i<a.getLongCromosoma();i++){
			System.out.print(a.getGen(i)+",");
		}
		System.out.println();
		System.out.println("B:");
		for (int i=0;i<a.getLongCromosoma();i++){
			System.out.print(b.getGen(i)+",");
		}
	}
	
}
