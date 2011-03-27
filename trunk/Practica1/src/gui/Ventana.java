package gui;

import gui.ConfigPanel.ChoiceOption;
import gui.ConfigPanel.ConfigListener;
import gui.ConfigPanel.DoubleOption;
import gui.ConfigPanel.InnerOption;
import gui.ConfigPanel.IntegerOption;
import gui.Demo.Figura;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.math.plot.Plot2DPanel;

import utils.DatosGrafica;

import logica.AGenetico;
import logica.Funcion;
import logica.Seleccion;

public class Ventana extends JFrame{
	private AGenetico aG;
	private DatosGrafica datos;
	private boolean datosOK;
	private JTabbedPane graficas;
	
	public Ventana(){

		super("Práctica 1: Grupo G07");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		JPanel panelPrinc=new JPanel();///aaa
	
		panelPrinc.setLayout(new BorderLayout());
		JPanel panelCentral = new JPanel(new GridLayout(4,1));
		panelPrinc.add(panelCentral, BorderLayout.CENTER);

		// crea dos figuras
		aG = new AGenetico();
		
		// crea un panel central y lo asocia con la primera figura
		final ConfigPanel<AGenetico> cp = creaPanelConfiguracion();
		// asocia el panel con la figura
		cp.setTarget(aG);
		// carga los valores de la figura en el panel
		cp.initialize();		
		panelPrinc.add(cp, BorderLayout.WEST);
		
		add(panelPrinc, BorderLayout.WEST);
		
		graficas=new JTabbedPane();
		add(graficas, BorderLayout.CENTER);
		
		// crea una etiqueta que dice si todo es valido
		final String textoTodoValido = "Todos los campos OK";
		final String textoHayErrores = "Hay errores en algunos campos";
		final JLabel valido = new JLabel(textoHayErrores);
		// este evento se lanza cada vez que la validez cambia
		cp.addConfigListener(new ConfigListener() {
			@Override
			public void configChanged(boolean isConfigValid) {
				if (isConfigValid){
					valido.setText(textoTodoValido);
					datosOK=true;
				}
				else{
					valido.setText(textoHayErrores);
					datosOK=false;
				}
				//valido.setText(isConfigValid ? textoTodoValido: textoHayErrores);				
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
				if (datosOK){
				datos=new DatosGrafica(aG.getNumMaxGen());
				aG.inicializa();
				//aG.setElite(1);
				for (int i=0; i<aG.getNumMaxGen(); i++){
					aG.evaluarPoblacion();
					datos.addDato(aG.getElMejor(), aG.getMejorLocal(), aG.getMediaPoblacion());
					if (aG.usaElite())
						aG.apartaElementosElite();
					aG.seleccion();
					aG.reproduccion();
					aG.mutacion();
					if (aG.usaElite())
						aG.reinsertaElementosElite();
				}
				grafica();
				}
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
			    Funcion.values()));
		config.beginInner(new InnerOption<AGenetico, AGenetico>( 
			  	"opciones para Funcion 4", "opciones de la funcion 4", 
			  	"funcion", Funcion.class) {
					public AGenetico inner(AGenetico target) {
						return (target.getFuncion() == Funcion.F4) ? target : null;
					}
				 })
		  		  .addInner(new IntegerOption<AGenetico>(
		  		     "Variable n", "numero de variables", "f4Aux", 1, Integer.MAX_VALUE))
		  		  .endInner();
	
		
		config.addOption(new ChoiceOption<AGenetico>(	 
			    "Selección",							 
			    "Tipo de selección de nuestro algoritmo", 					 
			    "seleccion",   							 
			    Seleccion.values()));
		config.beginInner(new InnerOption<AGenetico, AGenetico>( 
			  	"Opciones para torneo", "opciones de torneo", 
			  	"seleccion", Seleccion.class) {
					public AGenetico inner(AGenetico target) {
						return (target.getSeleccion() == Seleccion.Torneo) ? target : null;
					}
				 })
		  		  .addInner(new IntegerOption<AGenetico>(
		  		     "Participantes torneo", "Número de participantes", "torneoAux", 1, 5))
		  		  .endInner()
			    
				
		
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
		
		JTextArea area=new JTextArea();
		textoEtiqueta(area);
		area.setEditable(false);
		area.setBackground(Color.LIGHT_GRAY);
		//area.setRows(3);
		//JFrame frame = new JFrame("Grafica de la función "+ aG.getFuncion().toString());
		
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                plot, area);
		splitPane.setDividerLocation(475);
		splitPane.setResizeWeight(1);
	//	splitPane.setSize(300, 300);
		splitPane.setSize(600, 600);
		//splitPane.repack();
		JPanel graf=new JPanel();
		graf.setLayout(new BorderLayout());
		graf.add(splitPane, BorderLayout.CENTER);
		JButton cerrar=new JButton("Cerrar gráfica");
		cerrar.addActionListener(
				new ActionListener() {
		            public void actionPerformed(ActionEvent e) {
		                int a=graficas.getSelectedIndex();
		                if (a>=0)
		                	graficas.remove(a);
		            }
		        }
		);
		graf.add(cerrar, BorderLayout.SOUTH);
		//frame.setContentPane(splitPane);
		//frame.setVisible(true);
		graficas.addTab("Grafica de la función "+ aG.getFuncion().toString(), graf);
		graficas.setSelectedIndex(graficas.getComponentCount()-1);
		///aaa
	}

	private void textoEtiqueta(JTextArea area) {
		String aux=null;
		aux="  Valor "+ aG.getElMejor() + " en:"+'\n';
		area.append(aux);
		int i=1;
		
		for (double fenotipo : aG.getCromosomaMejor().valorFenotipo())  {
		      aux="    X"+i+"="+fenotipo+'\n';
		      area.append(aux);
		      i++;
		}
	}

}
