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
		
		pruebaHeuristica();
		
		
	}
	
	private static void pruebaHeuristica(){
		Cromosoma a=new CromosomaCiudades();
		System.out.print("Cromo:");
		for (int i=0;i<a.getLongCromosoma();i++){
			System.out.print(String.format("%3d", a.getGen(i))+",");
		}
		
		Heuristica b=new Heuristica();
		
		b.muta(a);
		
		for (int i=0;i<a.getLongCromosoma();i++){
			System.out.print(String.format("%3d", a.getGen(i))+",");
		}
		
	}
	
	private void pruebaOXPosiciones(){
		int contador=0;
		boolean erroneo=false;
		
		while ((contador<100)&&(!erroneo)){
			

		
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
				else{
					erroneo=true;
					System.err.println("elemento de a repetido: "+a.getGen(i));
				}
				
				
				if (bb.contains(b.getGen(i))){
					bb.remove((Object)b.getGen(i));
				}
				else{
					erroneo=true;
					System.err.println("elemento de b repetido: "+b.getGen(i));
				}
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
	
	

}
