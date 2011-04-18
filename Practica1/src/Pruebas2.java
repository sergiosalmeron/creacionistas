import java.util.ArrayList;

import com.sun.accessibility.internal.resources.accessibility;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

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
		/*ArrayList<Integer> a= new ArrayList<Integer>();
		a.add(1);
		a.add(2);
		a.add(3);
		a.add(4);
		int b=3;
		if (a.contains(b)){
			System.out.println("contiene el 3: perfecto");
		}
		else{
			System.out.println("esto no tira");
		}*/
		
		
		/*
		Cromosoma a=new CromosomaCiudades();
		Cromosoma b=new CromosomaCiudades();
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
		
		RecombRutas c=new RecombRutas();
		c.cruza(a, b);
		
		System.out.print("Hijo:");
		for (int i=0;i<a.getLongCromosoma();i++){
			System.out.print(String.format("%3d", a.getGen(i))+",");
		}
		System.out.println();
		System.out.print("Hija:");
		for (int i=0;i<b.getLongCromosoma();i++){
			System.out.print(String.format("%3d", b.getGen(i))+",");
		}
		System.out.println();
		System.out.println("Tabla Adyacencias:");
		c.muestraTabla();
		
		*/
		
		Cromosoma a=new CromosomaCiudades();
		Heuristica b=new Heuristica();
		b.muta(a);
		
	}

}
