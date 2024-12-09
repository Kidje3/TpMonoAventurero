package juego;

import java.awt.Image;

import entorno.Entorno;

public class Mono {
	private int x;
	private int y;
	private int ancho;
	private int alto;
	private double velocidad;
	private int vidas = 3;

	public Mono(int x, int y, int ancho, int alto, double velocidad) {
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.alto = alto;
		this.velocidad = velocidad;
	}

	public void dibujarmono(Entorno entorno, Image mono_camindando) {
		entorno.dibujarImagen(mono_camindando, this.x, this.y, 0.0, 0.3);
	}

	public void Saltar() {
		this.setVelocidad(this.getVelocidad() - 0.25);
		this.y -= this.getVelocidad();
		if (this.getY() + this.getAlto() / 2 > 580) {
			this.setY(580 - this.getAlto() / 2);
		}
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public int getAncho() {
		return this.ancho;
	}

	public int getAlto() {
		return this.alto;
	}

	public double getVelocidad() {
		return this.velocidad;
	}

	public int getVidas() {
		return this.vidas;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setVelocidad(double d) {
		this.velocidad = d;
	}

	public void setVidas(int vidas) {
		this.vidas = vidas;
	}

}
	
	
