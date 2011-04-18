package gui;
/**
 * @author Aleix Garrido Oberink, Sergio Salmer�n Majadas.
 * G07
 */
import gui.ConfigPanel.ChoiceOption;
import gui.ConfigPanel.ConfigListener;
import gui.ConfigPanel.DoubleOption;
import gui.ConfigPanel.InnerOption;
import gui.ConfigPanel.IntegerOption;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import org.math.plot.Plot2DPanel;

import utils.DatosGrafica;

import logica.AGenetico;
import logica.Distancias;
import logica.Funcion;
import logica.Seleccion;
import cruces.CruceEnum;

public class Ventana extends JFrame{
	private AGenetico aG;
	private DatosGrafica datos;
	private boolean datosOK;
	private JTabbedPane graficas;
	
	private JFrame map;
	
	public Ventana(){

		super("Pr�ctica 2: Grupo G07");
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
	}
	
	public ConfigPanel<AGenetico> creaPanelConfiguracion() {
		
		ConfigPanel<AGenetico> config = new ConfigPanel<AGenetico>();
		
		
		config.addOption(new IntegerOption<AGenetico>(  
				"Tama�o Poblaci�n", 					     
				"N�mero de cromosomas que va a tener cada generaci�n",   
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
			    "% Probabilidad Mutaci�n", 					 
			    "probabilidad de que haya mutaci�n",          
			    "probMutacion",                     
			    0, 100, 100))
			    .addOption(new DoubleOption<AGenetico>(   
			    "Tolerancia", 					 
			    "Tolerancia de la funci�n",          
			    "tolerancia",                     
			    0, 1))						     
			    .addOption(new DoubleOption<AGenetico>(   
			    "% Elitismo", 					 
			    "Porcentaje de elitismo",           
			    "elite",                     
			    0, 100, 100));
		config.addOption(new ChoiceOption<AGenetico>(	 
			    "Cruce",							 
			    "Tipo de cruce de nuestro algoritmo", 					 
			    "cruce",   							 
			    CruceEnum.values()));
			    /*.addOption(new ChoiceOption<AGenetico>(	 
			    "Funci�n",							 
			    "Funci�n que queremos probar", 					 
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
				*/
		config.addOption(new ChoiceOption<AGenetico>(	 
			    "Selecci�n",							 
			    "Tipo de selecci�n de nuestro algoritmo", 					 
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
		  		     "Participantes torneo", "N�mero de participantes", "torneoAux", 1, 5))
		  		  .endInner();
		 config.beginInner(new InnerOption<AGenetico, AGenetico>( 
			  	"Opciones para Ranking", "opciones de ranking", 
			  	"seleccion", Seleccion.class) {
					public AGenetico inner(AGenetico target) {
						return (target.getSeleccion() == Seleccion.Ranking) ? target : null;
					}
				 })
		  		  .addInner(new IntegerOption<AGenetico>(
		  		     "Valor de Beta", "Valor a la variable Beta", "beta", 1, 3))
		  		  .endInner()
			    
				
		
		  	  .endOptions();
		
		return config;
	}
	
	
	// construye y muestra la interfaz
	public static void main(String[] args) {
		
		Ventana v = new Ventana();
		v.setSize(900, 650);
		v.setVisible(true);	
	}
	
	private void grafica(){
		Plot2DPanel plot = new Plot2DPanel();
		
		plot.addLegend("SOUTH");
		plot.addLinePlot("Mejor absoluto", datos.getGeneraciones(), datos.getMejorAbsoluto());
		plot.addLinePlot("Mejor de la generaci�n", datos.getGeneraciones(), datos.getMejorLocal());
		plot.addLinePlot("Media", datos.getGeneraciones(), datos.getMedia());
		
		JTextArea area=new JTextArea();
		textoEtiqueta(area);
		area.setEditable(false);
		area.setBackground(Color.LIGHT_GRAY);
		
		
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                plot, area);
		splitPane.setDividerLocation(450);
		splitPane.setResizeWeight(1);
	//	splitPane.setSize(300, 300);
		splitPane.setSize(600, 600);
		//splitPane.repack();
		JPanel graf=new JPanel();
		graf.setLayout(new BorderLayout());
		graf.add(splitPane, BorderLayout.CENTER);
		JButton cerrar=new JButton("Cerrar gr�fica");
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
		graficas.addTab("Grafica de Rutas", graf);
		graficas.setSelectedIndex(graficas.getComponentCount()-1);
		///aaa
		parteMapa();
	}

	private void parteMapa() {
		map=new JFrame();
		Lienzo l=new Lienzo();
		l.setCromosoaFinal(aG.getCromosomaMejor());
		Graphics g= l.getGraphics();
		map.setSize(800, 600);
		map.setVisible(true);
		map.add(l);
		
	}

	private void textoEtiqueta(JTextArea area) {
		String aux=null;
		aux="  Valor "+ aG.getElMejor() + " en:"+'\n';
		area.append(aux);
		int i=1;
		int contador=0;
		for (int fenotipo : aG.getCromosomaMejor().valorFenotipo())  {
		      aux="    X"+i+"="+Distancias.CIUDADES[fenotipo];
		      contador++;
		      if (contador%6==0){
		    	  aux=aux+'\n';
		      }
		      area.append(aux);
		      i++;
		}
	}

}
