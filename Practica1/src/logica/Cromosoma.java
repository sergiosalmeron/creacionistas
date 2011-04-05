package logica;
/**
 * @author Aleix Garrido Oberink, Sergio Salmerón Majadas.
 * G07
 */
import java.util.Random;


public abstract class Cromosoma{

	protected boolean[] genes;
	protected double[] fenotipo;
	protected double aptitud;
	private double puntuacion;
	private double puntAcum;
	private Random r;
	
	public Cromosoma() {
		this.r = new Random();
	}
	
	protected boolean iniciaGen(){
		return r.nextBoolean();
	}
	
	
	protected double binDec(int posIni, int posFin){
		double decimal=0;
		double eleva=1;
        for (int i=posIni; i<posFin; i++){
                if (genes[i]==true) {
                        decimal=decimal+eleva;
                }
                eleva=eleva*2;
        }
        return decimal;
	}
	
	public double getAptitud(){
		return aptitud;
	}
	
	public boolean getGen(int i){
		return genes[i];
	}
	
	public void setGenes(boolean[] genes){
		for (int i=0; i<this.genes.length; i++){
			this.genes[i]=genes[i];
		}
	}
	
	public void mutaGen(int i){
		genes[i]=!genes[i];
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
	public abstract double[] valorFenotipo();
	
	/**
	 * Evaluador de la función del problema concreto
	 * @return double aptitud
	 */
	public abstract double evalua();
	
	public abstract int calculaLongCromosoma(double tolerancia);
	

    public abstract Object clone();

}
