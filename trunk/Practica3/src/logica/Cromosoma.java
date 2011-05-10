package logica;
/**
 * @author Aleix Garrido Oberink, Sergio Salmerón Majadas.
 * G07
 */
import java.util.Random;


public abstract class Cromosoma{

	protected int[] genes;
	protected double[] fenotipo;
	protected double aptitud;
	private double puntuacion;
	private double puntAcum;
	protected Random r;
	
	public Cromosoma() {
		this.r = new Random();
	}
	
	protected boolean iniciaGen(){
		return r.nextBoolean();
	}
	
	
	
	public double getAptitud(){
		return aptitud;
	}
	
	public int getGen(int i){
		return genes[i];
	}
	
	public void setGen(int pos, int valor){
		genes[pos]=valor;
	}
	
	public void setGenes(int[] genes){
		for (int i=0; i<this.genes.length; i++){
			this.genes[i]=genes[i];
		}
	}
	
	public void mutaGen(int i){
		genes[i]=0;
	}
	
	public void setPuntuacion(double puntuacion){
		this.puntuacion=puntuacion;
	}
	
	public double getPuntuacion(){
		return puntuacion;
	}
	
	
	public double getPuntAcum() {
		return puntAcum;
	}

	public void setPuntAcum(double puntAcum) {
		this.puntAcum = puntAcum;
	}
	
	public int getLongCromosoma(){
		return genes.length;
	}

	/**
	 * Función que devuelve el valor del fenotipo
	 * @return double fenotipo
	 */
	public abstract int[] valorFenotipo();
	
	/**
	 * Evaluador de la función del problema concreto
	 * @return double aptitud
	 */
	public abstract double evalua();
	
	public abstract int calculaLongCromosoma();
	

    public abstract Object clone();

}
