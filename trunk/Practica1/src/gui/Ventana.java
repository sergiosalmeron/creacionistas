package gui;

import gui.ConfigPanel.ChoiceOption;
import gui.ConfigPanel.ConfigListener;
import gui.ConfigPanel.DoubleOption;
import gui.ConfigPanel.IntegerOption;
import gui.Demo.Figura;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.math.plot.Plot2DPanel;

import utils.DatosGrafica;

import logica.AGenetico;
import logica.Funcion;
import logica.Seleccion;

public class Ventana extends JFrame{
	private AGenetico aG;
	private DatosGrafica datos;
	
	public Ventana(){

		super("Práctica 1");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		JPanel panelCentral = new JPanel(new GridLayout(4,1));
		add(panelCentral, BorderLayout.CENTER);

		// crea dos figuras
		aG = new AGenetico();
		
		// crea un panel central y lo asocia con la primera figura
		final ConfigPanel<AGenetico> cp = creaPanelConfiguracion();
		// asocia el panel con la figura
		cp.setTarget(aG);
		// carga los valores de la figura en el panel
		cp.initialize();		
		add(cp, BorderLayout.WEST);
		
		// crea una etiqueta que dice si todo es valido
		final String textoTodoValido = "Todos los campos OK";
		final String textoHayErrores = "Hay errores en algunos campos";
		final JLabel valido = new JLabel(textoHayErrores);
		// este evento se lanza cada vez que la validez cambia
		cp.addConfigListener(new ConfigListener() {
			@Override
			public void configChanged(boolean isConfigValid) {
				valido.setText(isConfigValid ? textoTodoValido: textoHayErrores);				
			}
		});
		add(valido, BorderLayout.SOUTH);
		
		// crea una etiqueta que indica la figura que se esta editando
		final JLabel panelEnEdicion = new JLabel("Editando datos");
		add(panelEnEdicion, BorderLayout.NORTH);
		
		// usado por todos los botones
		JButton boton;

		boton = new JButton("Ejecutar");
		boton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				datos=new DatosGrafica(aG.getNumMaxGen(), true);
				aG.inicializa();
				for (int i=0; i<aG.getNumMaxGen(); i++){
					aG.evaluarPoblacion(true);
					datos.addDato(aG.getMejorLocal(), aG.getMediaPoblacion());
					aG.seleccionRuleta();
					aG.reproduccion();
					aG.mutacion();
				}
				grafica();
			}
		});
		panelCentral.add(boton);
		panelCentral.add(Box.createGlue());
		
		boton = new JButton("Resetear");
		boton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				aG=new AGenetico();
				cp.setTarget(aG);
				cp.initialize();
			}
		});
		panelCentral.add(boton);
		panelCentral.add(Box.createGlue());
		// crea botones para mostrar el estado de las figuras por consola
/*		boton = new JButton("muestra fig. 1");
		boton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.err.println(f1.toString());
			}
		});
		panelCentral.add(boton);
		boton = new JButton("muestra fig. 2");
		boton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.err.println(f2.toString());
			}
		});
		panelCentral.add(boton);

		// crea botones para sobreescribir el panel con las figuras
		boton = new JButton("fig. 1 a panel");
		boton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cp.setTarget(f1);
				cp.initialize();
				panelEnEdicion.setText("Editando figura 1");
			}
		});
		panelCentral.add(boton);
		boton = new JButton("fig. 2 a panel");
		boton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cp.setTarget(f2);
				cp.initialize();
				panelEnEdicion.setText("Editando figura 2");
			}
		});
		panelCentral.add(boton);

		// crea botones para sobreescribir las figuras con el panel
		boton = new JButton("panel a fig. 1");
		boton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cp.setTarget(f1);
				cp.copyUpdate();
				panelEnEdicion.setText("Editando figura 1");
			}
		});
		panelCentral.add(boton);
		boton = new JButton("panel a fig. 2");
		boton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cp.setTarget(f2);
				cp.copyUpdate();
				panelEnEdicion.setText("Editando figura 2");
			}
		});
		panelCentral.add(boton);*/
	}
	
	public ConfigPanel<AGenetico> creaPanelConfiguracion() {
		
		ConfigPanel<AGenetico> config = new ConfigPanel<AGenetico>();
		
		
		config.addOption(new IntegerOption<Figura>(  
				"Tamaño Población", 					     
				"Número de cromosomas que va a tener cada generación",   
				"tamPob",  						    
				1, Integer.MAX_VALUE))							     
			  .addOption(new IntegerOption<AGenetico>(	 
			    "Num. Generaciones",							 
			    "Numero de generaciones que se va a ejecutar el algoritmo", 					
			    "numMaxGen",   							 
			    1, Integer.MAX_VALUE))                            
			  .addOption(new DoubleOption<AGenetico>(   
			    "% Probabilidad Cruce", 					 
			    "probabilidad de que haya cruce entre cromosomas",           
			    "probCruce",                     
			    0, 100, 100))				
			    .addOption(new DoubleOption<AGenetico>(   
			    "% Probabilidad Mutación", 					 
			    "probabilidad de que haya mutación",          
			    "probMutacion",                     
			    0, 100, 100))
			    .addOption(new DoubleOption<AGenetico>(   
			    "Tolerancia", 					 
			    "Tolerancia de la función",          
			    "tolerancia",                     
			    0, 1))						     
			    .addOption(new DoubleOption<AGenetico>(   
			    "% Elitismo", 					 
			    "Porcentaje de elitismo",           
			    "elite",                     
			    0, 100, 100))
			    .addOption(new ChoiceOption<AGenetico>(	 
			    "Función",							 
			    "Función que queremos probar", 					 
			    "funcion",   							 
			    Funcion.values()))
			    .addOption(new ChoiceOption<AGenetico>(	 
			    "Selección",							 
			    "Tipo de selección de nuestro algoritmo", 					 
			    "seleccion",   							 
			    Seleccion.values()))
				
		/*	  // para cada clase de objeto interno, hay que definir sus opciones entre un beginInner y un endInner 
			  .beginInner(new InnerOption<Figura,Forma>(  
			  	"circulo",							 // titulo del sub-panel
			  	"opciones del circulo",				 // tooltip asociado
			  	"forma",							 // campo
			  	Circulo.class))						 // tipo que debe de tener ese campo para que se active el sub-panel
		  		  .addInner(new DoubleOption<Forma>(
		  		     "radio", "radio del circulo", "radio", 0, Integer.MAX_VALUE))
		  		  .endInner()						 // cierra este sub-panel
		  	  // igual, pero opciones para el caso 'forma de tipo rectangulo'  
              .beginInner(new InnerOption<Figura,Forma>( 
			  	"rectangulo", "opciones del rectangulo", "forma", Rectangulo.class))
		  		  .addInner(new DoubleOption<Forma>(
		  		     "ancho", "ancho del rectangulo", "ancho", 0, Double.POSITIVE_INFINITY))
		  		  .addInner(new DoubleOption<Forma>(
		  		     "alto", "alto del rectangulo", "alto", 0, Double.POSITIVE_INFINITY))
		  		  .endInner()

			  // y por ultimo, el punto (siempre estara visible)
			  .beginInner(new InnerOption<Figura,Punto>(
			  	"coordenadas", "coordenadas de la figura", "coordenadas", Punto.class))
		  		  .addInner(new DoubleOption<Forma>(
		  		     "x", "coordenada x", "x", Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY))
		  		  .addInner(new DoubleOption<Forma>(
		  		     "y", "coordenada y", "y", Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY))
		  		  .endInner()
		  		  
			  // y ahora ya cerramos el formulario*/
		  	  .endOptions();
		
		return config;
	}
	
	
	// construye y muestra la interfaz
	public static void main(String[] args) {
		
		Ventana v = new Ventana();
		v.setSize(400, 350);
		v.setVisible(true);	
	}
	
	private void grafica(){
		Plot2DPanel plot = new Plot2DPanel();
		plot.addLegend("SOUTH");
		plot.addLinePlot("Mejor absoluto", datos.getGeneraciones(), datos.getMejorAbsoluto());
		plot.addLinePlot("Mejor de la generación", datos.getGeneraciones(), datos.getMejorLocal());
		plot.addLinePlot("Media", datos.getGeneraciones(), datos.getMedia());
		
		JFrame frame = new JFrame("Grafica");
		frame.setSize(600, 600);
		frame.setContentPane(plot);
		frame.setVisible(true);
	}

}
