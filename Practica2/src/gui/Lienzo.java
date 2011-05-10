package gui;


import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.ImageObserver;

import javax.swing.ImageIcon;

import logica.Cromosoma;
import logica.Distancias;


class Lienzo extends Canvas
{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ImageObserver observer;
	private Cromosoma c;

    public void paint (Graphics g)
    {
    	ImageIcon i=new ImageIcon("src/gui/mapa.jpg");
		g.drawImage(i.getImage(), 0, 0, 800, 600, observer);
		g.setColor(Color.GREEN);
		g.drawLine(Distancias.getPosX(0),Distancias.getPosY(0),Distancias.getPosX(c.getGen(0)),Distancias.getPosY(c.getGen(0)));
		g.setColor(Color.RED);
		for (int j=0; j<c.getLongCromosoma()-1; j++){
			g.drawLine(Distancias.getPosX(c.getGen(j)),Distancias.getPosY(c.getGen(j)),Distancias.getPosX(c.getGen(j+1)),Distancias.getPosY(c.getGen(j+1)));
		}

		g.drawLine(Distancias.getPosX(c.getGen(c.getLongCromosoma()-1)),Distancias.getPosY(c.getGen(c.getLongCromosoma()-1)),Distancias.getPosX(0),Distancias.getPosY(0));
		
		g.setColor(Color.BLUE);
		g.drawOval(Distancias.getPosX(0),Distancias.getPosY(0), 5, 5);
		for (int j=0; j<c.getLongCromosoma(); j++){
			g.drawOval(Distancias.getPosX(c.getGen(j)),Distancias.getPosY(c.getGen(j)), 5, 5);
		}
		
    }


	public void setCromosoaFinal(Cromosoma cromosoma) {
		c=cromosoma;
	}
} 