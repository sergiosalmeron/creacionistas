
import javax.swing.JFrame;

import org.math.plot.Plot2DPanel;

import utils.DatosGrafica;


public class Principal {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		AGenetico AG=new AGenetico();
		AG.inicializa();
		DatosGrafica datos=new DatosGrafica(100, true);
		for (int i=0; i<100; i++){
			AG.evaluarPoblacion(true);
			AG.seleccionRuleta();
			//AG.seleccionTorneoDet(3);
			AG.reproduccion();
			AG.mutacion();
			datos.addDato(AG.getMejorLocal(), AG.getMediaPoblacion());
			System.out.println(AG.elMejor.aptitud+" "+AG.getMediaPoblacion());
		}
		System.out.println(AG.elMejor.aptitud);
		System.out.println(AG.elMejor.fenotipo[0]);
		//System.out.println(AG.elMejor.fenotipo[1]);
		Plot2DPanel plot = new Plot2DPanel();
		plot.addLegend("SOUTH");
		plot.addLinePlot("Mejor absoluto", datos.getGeneraciones(), datos.getMejorAbsoluto());
		plot.addLinePlot("Mejor de la generación", datos.getGeneraciones(), datos.getMejorLocal());
		plot.addLinePlot("Media", datos.getGeneraciones(), datos.getMedia());

		JFrame frame = new JFrame("tiriririri");
		frame.setSize(600, 600);
		frame.setContentPane(plot);
		frame.setVisible(true);
	}

}
