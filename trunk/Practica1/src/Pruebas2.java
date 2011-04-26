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
		
		ArrayList<Integer> aa=new ArrayList<Integer>();
		ArrayList<Integer> bb=new ArrayList<Integer>();
		for (int i=0;i<b.getLongCromosoma();i++){
			aa.add(i+1);
			bb.add(i+1);
		}
		
		for (int i=0;i<b.getLongCromosoma();i++){
			if (aa.contains(a.getGen(i))){
				aa.remove((Object)a.getGen(i));
			}
			else
				System.err.println("elemento de a repetido: "+a.getGen(i));
			
			
			if (bb.contains(b.getGen(i))){
				bb.remove((Object)b.getGen(i));
			}
			else
				System.err.println("elemento de b repetido: "+b.getGen(i));
		}
		
		//c.muestraTabla();
		
		
		/*
		Cromosoma a=new CromosomaCiudades();
		Cromosoma b=new CromosomaCiudades();
		OXPosciciones c=new OXPosciciones();
		c.cruza(a, b);*/
		//Heuristica b=new Heuristica();
	//	b.muta(a);
		
	}
	
	

}
