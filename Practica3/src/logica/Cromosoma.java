package logica;
/**
 * @author Aleix Garrido Oberink, Sergio Salmerón Majadas.
 * G07
 */
import java.util.Random;


public abstract class Cromosoma{

	protected Arbol gen;
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
	
	public Arbol getGen(){
		return gen;
	}
	
	public void setGen(int pos, Arbol valor){
		gen=valor;
	}
	
	public void setGenes(Arbol valor){
		gen=valor;
	}
	
	public void mutaGen(int i){
		gen.mutacionArbol();
		//genes[i].mutacionTerminal();
		//genes[i].mutacionFuncional();
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
		return gen.getNumElementos();
	}

	/**
	 * Función que devuelve el valor del fenotipo
	 * @return double fenotipo
	 */
	public abstract String valorFenotipo();
	
	/**
	 * Evaluador de la función del problema concreto
	 * @return double aptitud
	 */
	public abstract double evalua();
	
	public abstract int calculaLongCromosoma();
	

    public abstract Object clone();

}
