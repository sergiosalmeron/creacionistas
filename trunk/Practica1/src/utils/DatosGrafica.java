package utils;

public class DatosGrafica {
	
	private double[] generaciones;
	private double[] mejorAbsoluto;
	private double[] mejorLocal;
	private double[] media;
	private int numGeneraciones;
	private int i;
	//private boolean maximizar;
	
	public DatosGrafica(int nGeneraciones){
		this.numGeneraciones=nGeneraciones;
		generaciones=new double[nGeneraciones];
		mejorAbsoluto=new double[nGeneraciones];
		mejorLocal=new double[nGeneraciones];
		media=new double[nGeneraciones];
		i=0;
		//this.maximizar=maximiza;
	}
	
	public void addDato(double mejorAbsoluto, double mejorGeneracion, double mediaGeneracion){
		if (i<numGeneraciones){
			mejorLocal[i]=mejorGeneracion;
			media[i]=mediaGeneracion;
			generaciones[i]=i;
			//addMejorAbs(mejorGeneracion);
			this.mejorAbsoluto[i]=mejorAbsoluto;
			i++;
		}
	}
	
	public double[] getGeneraciones() {
		return generaciones;
	}

	public double[] getMejorAbsoluto() {
		return mejorAbsoluto;
	}

	public double[] getMejorLocal() {
		return mejorLocal;
	}

	public double[] getMedia() {
		return media;
	}
/*
	private void addMejorAbs(double mejorGeneracion){
		if (i>0){
			boolean mejorMaximizando=maximizar && (mejorAbsoluto[i-1]<mejorGeneracion);
			boolean mejorMinimizando=(!maximizar) && (mejorAbsoluto[i-1]>mejorGeneracion);
			if (mejorMaximizando||mejorMinimizando)
				mejorAbsoluto[i]=mejorGeneracion;
			else
				mejorAbsoluto[i]=mejorAbsoluto[i-1];

		}
		else
			mejorAbsoluto[i]=mejorGeneracion;
	}*/


}
