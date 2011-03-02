import java.util.Random;


public abstract class Cromosoma {

	protected boolean[] genes;
	private double fenotipo;
	private double aptitud;
	private double puntuacion;
	private double puntAcum;
	
	protected boolean iniciaGen(){
		Random r= new Random();
		return r.nextBoolean();
	}
	
	
}
