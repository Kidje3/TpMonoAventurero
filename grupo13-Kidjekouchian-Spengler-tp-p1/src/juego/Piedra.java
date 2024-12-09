package juego;

import java.awt.Color;
import java.awt.Image;

import entorno.Entorno;

public class Piedra {
	private int x;
	private int y;
	private int ancho;
	private int alto;
	private int velocidad;

	public Piedra(int x, int y, int ancho, int alto, int velocidad) {
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.alto = alto;
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

	public void mover() {

		this.x += this.velocidad;

	}

	public void setY(int y) {
		this.y = y;
	}

	public void dibujar(Entorno entorno, Image imgpiedra) {
		entorno.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, 45, Color.green);
		entorno.dibujarImagen(imgpiedra, this.x, this.y, 0.0, 0.3);
	}

}
