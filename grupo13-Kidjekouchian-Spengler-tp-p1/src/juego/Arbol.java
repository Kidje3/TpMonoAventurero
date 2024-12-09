package juego;


import java.awt.Image;
import java.util.Random;

import entorno.Entorno;

public class Arbol {
	private int x;
	private int y;
	private int ancho;
	private int alto;
	private int velocidad;
	

	Random r= new Random();
	 

	
	public Arbol(int x, int y, int ancho, int alto,  int velocidad) {		
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.alto =  alto;
		this.velocidad = velocidad;
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getAncho() {
		return ancho;

	}

	public int getAlto() {
		return alto;
	}

	public void mover()
	{
		this.x -= this.velocidad;
		
	}

	public void invertirVelocidad()
	{
		this.velocidad *= -1;
	}
	
	
	public int getVelocidad() {
		return velocidad;
	}

	public void setVelocidad(int velocidad) {
		this.velocidad = velocidad;
	}
	
	public void dibujar(Entorno entorno, Image img_rama, Image img_arbol) {
		
		if(this.y<300) 
			{entorno.dibujarImagen(img_arbol, this.x, 350, 0, 0.7);}
		else
			{entorno.dibujarImagen(img_arbol, this.x, 400, 0, 0.5);}
		entorno.dibujarImagen(img_rama, this.x, this.y-5, 0, 0.1);
	}
	


}
