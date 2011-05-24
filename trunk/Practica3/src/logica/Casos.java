package logica;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Casos {
	
	private static ArrayList<String> casos;
	
	public Casos(){
		
		casos = new ArrayList<String>();
		String sCadena;
		try {
			BufferedReader bf=new BufferedReader(new FileReader("src/casos.txt"));
			while ((sCadena = bf.readLine())!=null) {
				casos.add(sCadena);
				} 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public static String getCaso(int pos){
		return casos.get(pos);
	}
	
	public static int getNumCasos(){
		return casos.size();
	}

}
