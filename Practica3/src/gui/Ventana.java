package gui;
/**
 * @author Aleix Garrido Oberink, Sergio Salmerón Majadas.
 * G07
 */
import gui.ConfigPanel.ChoiceOption;
import gui.ConfigPanel.ConfigListener;
import gui.ConfigPanel.DoubleOption;
import gui.ConfigPanel.InnerOption;
import gui.ConfigPanel.IntegerOption;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import org.math.plot.Plot2DPanel;

import utils.DatosGrafica;

import logica.AGenetico;
import logica.Seleccion;
import mutaciones.MutaEnum;
import logica.EnumMejoras;

public class Ventana extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private AGenetico aG;
	private DatosGrafica datos;
	private boolean datosOK;
	private JTabbedPane graficas;
	
	
	public Ventana(){

		super("Práctica 3: Grupo G07");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		JPanel panelPrinc=new JPanel();
	
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
				int generacionActual=0;
				double mediaAnterior=Double.MAX_VALUE;
				int generacionFail=0;
				while (generacionActual<aG.getNumMaxGen()){
					aG.evaluarPoblacion();
					if (aG.getMediaPoblacion()<mediaAnterior || generacionFail>=aG.getLimiteContractividad() || aG.getMejora()==EnumMejoras.Ninguna){
						mediaAnterior=aG.getMediaPoblacion();
						generacionActual++;
						generacionFail=0;
						datos.addDato(aG.getElMejor(), aG.getMejorLocal(), aG.getMediaPoblacion());
					}	
					else
						generacionFail++;
					if (aG.usaElite())
						aG.apartaElementosElite();
					aG.seleccion();
					aG.reproduccion();
					aG.mutacion();
					if (aG.usaElite())
						aG.reinsertaElementosElite();
				}
				/*for (int i=0; i<aG.getNumMaxGen(); i++){
					aG.evaluarPoblacion();
					datos.addDato(aG.getElMejor(), aG.getMejorLocal(), aG.getMediaPoblacion());
					if (aG.usaElite())
						aG.apartaElementosElite();
					aG.seleccion();
					aG.reproduccion();
					aG.mutacion();
					if (aG.usaElite())
						aG.reinsertaElementosElite();
				}*/
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
				"Tamaño Población", 					     
				"Número de cromosomas que va a tener cada generación",   
				"tamPob",  						    
				1, Integer.MAX_VALUE))							     
			  .addOption(new IntegerOption<AGenetico>(	 
			    "Num. Generaciones",							 
			    "Numero de generaciones que se va a ejecutar el algoritmo", 					
			    "numMaxGen",   							 
			    1, Integer.MAX_VALUE))
			  .addOption(new IntegerOption<AGenetico>(	 
			    "Máx. Profundidad Árbol Inicial",							 
			    "Profundidad máxima que puede tener un árbol generado aleatoriamente", 					
			    "tamArbol",   							 
			    1, 10))  
			  .addOption(new IntegerOption<AGenetico>(	 
			    "Máx. Prufoundidad tras el cruce",							 
			    "Profundidad máxima a la que puede llegar un árbol tras producirse un cruce", 					
			    "tamCruce",   							 
			    1, 15))
			  .addOption(new IntegerOption<AGenetico>(	 
			    "Núm. Máx. Iteraciones de la función DU",							 
			    "Número máximo de iteraciones que permitimos que se ejecute la operación DU", 					
			    "numIter",   							 
			    1, 10))
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
			    "% Elitismo", 					 
			    "Porcentaje de elitismo",           
			    "elite",                     
			    0, 100, 100));
		/*config.addOption(new ChoiceOption<AGenetico>(	 
			    "Cruce",							 
			    "Tipo de cruce de nuestro algoritmo", 					 
			    "cruce",   							 
			    MutaEnum.values()))*/
		config.addOption(new ChoiceOption<AGenetico>(	 
			    "Mutación",							 
			    "Tipo de mutación de nuestro algoritmo", 					 
			    "mutacion",   							 
			    MutaEnum.values()))
			    .addOption(new ChoiceOption<AGenetico>(	 
			    "Mejoras",							 
			    "Mejoras adicionales", 					 
			    "mejora",   							 
			    EnumMejoras.values()));
		config.beginInner(new InnerOption<AGenetico, AGenetico>( 
			  	"Opciones de Contractividad", "opciones para la contractividad", 
			  	"mejora", EnumMejoras.class) {
					public AGenetico inner(AGenetico target) {
						return (target.getMejora() == EnumMejoras.Contractividad) ? target : null;
					}
				 })
		  		  .addInner(new IntegerOption<AGenetico>(
		  		     "Limite de generaciones", "generaciones máximas sin mejora de la media", "limiteContractividad", 1, Integer.MAX_VALUE))
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
		  		  .endInner();
		 config.beginInner(new InnerOption<AGenetico, AGenetico>( 
			  	"Opciones para Ranking", "opciones de ranking", 
			  	"seleccion", Seleccion.class) {
					public AGenetico inner(AGenetico target) {
						return (target.getSeleccion() == Seleccion.Ranking) ? target : null;
					}
				 })
		  		  .addInner(new DoubleOption<AGenetico>(
		  		     "Valor de Beta", "Valor a la variable Beta", "beta", 1, 2))
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
		plot.addLinePlot("Mejor de la generación", datos.getGeneraciones(), datos.getMejorLocal());
		plot.addLinePlot("Media", datos.getGeneraciones(), datos.getMedia());
		

		
		JTextArea area=new JTextArea();
		JScrollPane scrolly=new JScrollPane(area);
		
		textoEtiqueta(area);
		area.setEditable(false);
		area.setBackground(Color.LIGHT_GRAY);
		
		
		
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                plot, scrolly);
		splitPane.setDividerLocation(450);
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
		graficas.addTab("Grafica de árboles", graf);
		graficas.setSelectedIndex(graficas.getComponentCount()-1);
		
	}

	private void textoEtiqueta(JTextArea area) {
		String aux=null;
		aux="  Valor "+ aG.getElMejor() + " en el árbol:"+'\n';
		area.append(aux);
		
		area.append(aG.getCromosomaMejor().valorFenotipo());
		
	}

}
