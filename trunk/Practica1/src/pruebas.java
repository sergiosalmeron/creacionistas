import cruces.CX;
import cruces.Cruce;
import cruces.OX;
import cruces.OXorden;
import cruces.PMX;
import logica.Cromosoma;
import logica.CromosomaCiudades;
import mutaciones.Inversion;
import mutaciones.Mutacion;


public class pruebas {

	
	public static void main (String[] args){
		Cromosoma a=new CromosomaCiudades();
		Cromosoma b=new CromosomaCiudades();
		
		
		System.out.println("A:");
		for (int i=0;i<a.getLongCromosoma();i++){
			System.out.print(String.format("%3d", a.getGen(i))+",");
		}
		System.out.println();
		System.out.println("B:");
		for (int i=0;i<a.getLongCromosoma();i++){
			System.out.print(String.format("%3d", b.getGen(i))+",");
		}
		System.out.println();
		
		Cruce c=new OXorden();
		c.cruza(a,b);
		//c.muta(b);
		

		
		System.out.println("A:");
		for (int i=0;i<a.getLongCromosoma();i++){
			System.out.print(String.format("%3d", a.getGen(i))+",");
		}
		System.out.println();
		System.out.println("B:");
		for (int i=0;i<a.getLongCromosoma();i++){
			System.out.print(String.format("%3d", b.getGen(i))+",");
		}
		//String.format("%3d", perm[i]);
	}
	

	
}
