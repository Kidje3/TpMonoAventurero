package juego;

import java.awt.Image;

import entorno.Entorno;

public class Obstaculos {
	private int x;
	private int y;
	private int ancho;
	private int alto;
	private int velocidad;

	public Obstaculos(int x, int y, int ancho, int alto, int velocidad) {
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
		this.x -= this.velocidad;

	}

	public void SetY(int a) {
		this.y = a;
	}

	public void dibujar(Entorno entorno, Image imgdepredador) {
		entorno.dibujarImagen(imgdepredador, this.x + 20, this.y - 5, 0, 0.4);
	}

}
