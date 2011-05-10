import java.util.ArrayList;

import com.sun.accessibility.internal.resources.accessibility;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import cruces.OXPosciciones;
import cruces.RecombRutas;

import logica.Cromosoma;
import logica.CromosomaCiudades;
import mutaciones.Heuristica;
import mutaciones.Intercambio;


public class Pruebas2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int i=0;
		boolean erroneo=false;
		while ((i<500)&&(!erroneo)){
			//erroneo=pruebaHeuristica();
			erroneo=pruebaOXPosiciones();
			i++;
		}
		System.out.println("finnnn");
		
		
	}
	
	private static boolean pruebaHeuristica(){
		
		Cromosoma a=new CromosomaCiudades();
		
		System.out.print("Xxxxx:");
		for (int i=0;i<a.getLongCromosoma();i++){
			System.out.print(String.format("%3d", i)+",");
		}
		System.out.println();
		
		System.out.print("Cromo:");
		for (int i=0;i<a.getLongCromosoma();i++){
			System.out.print(String.format("%3d", a.getGen(i))+",");
		}
		System.out.println();
		
		Heuristica b=new Heuristica();
		
		b.muta(a);
		
		System.out.println("Posiciones a permutar: "+b.getNumPosiciones());
		System.out.print("Sol:  ");
		for (int i=0;i<a.getLongCromosoma();i++){
			System.out.print(String.format("%3d", a.getGen(i))+",");
		}
		System.out.println();
		return compruebaRepetidos(a);
		
	}
	
	private static boolean pruebaOXPosiciones(){
			Cromosoma a=new CromosomaCiudades();
			Cromosoma b=new CromosomaCiudades();
			System.out.print("Xxxxx:");
			for (int i=0;i<b.getLongCromosoma();i++){
				System.out.print(String.format("%3d", i)+",");
			}
			System.out.println();
			System.out.print("Padre:");
			for (int i=0;i<a.getLongCromosoma();i++){
				System.out.print(String.format("%3d", a.getGen(i))+",");
			}
			System.out.println();
			System.out.print("Madre:");
			for (int i=0;i<b.getLongCromosoma();i++){
				System.out.print(String.format("%3d", b.getGen(i))+",");
			}
			System.out.println();
			
			OXPosciciones c=new OXPosciciones();
			c.cruza(a, b);
			
			System.out.print(" Hijo:");
			for (int i=0;i<a.getLongCromosoma();i++){
				System.out.print(String.format("%3d", a.getGen(i))+",");
			}
			System.out.println();
			System.out.print(" Hija:");
			for (int i=0;i<b.getLongCromosoma();i++){
				System.out.print(String.format("%3d", b.getGen(i))+",");
			}
			System.out.println();
			c.muestraPosiciones();
			System.out.println();
			System.out.println("----------------------------------------------");
			
			return compruebaRepetidos(a) && compruebaRepetidos(b);
		
		//c.muestraTabla();
		
		
		/*
		Cromosoma a=new CromosomaCiudades();
		Cromosoma b=new CromosomaCiudades();
		OXPosciciones c=new OXPosciciones();
		c.cruza(a, b);*/
		//Heuristica b=new Heuristica();
	//	b.muta(a);
		
	}
	
	private static boolean compruebaRepetidos(Cromosoma a){
		ArrayList<Integer> aa=new ArrayList<Integer>();
		boolean erroneo=false;
		for (int i=0;i<a.getLongCromosoma();i++){
			aa.add(i+1);
		}
		
		for (int i=0;i<a.getLongCromosoma();i++){
			if (aa.contains(a.getGen(i))){
				aa.remove((Object)a.getGen(i));
			}
			else{
				erroneo=true;
				System.err.println("elemento de a repetido: "+a.getGen(i));
			}

		}
		return erroneo;
	}
	
	

}
