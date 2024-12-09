package juego;

import java.awt.Color;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Random;
import javax.sound.sampled.Clip;
import entorno.Entorno;
import entorno.Herramientas;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego {
	
	private String estado = "inicio";
	private Entorno entorno;
	private Piedra p1;
	private boolean disparo = false;
	private Obstaculos[] tigres = new Obstaculos[3];
	private int velocidad_tigre = 3;
	private Obstaculos serpientes;
	private Arbol[] ramas = new Arbol[4];
	private Mono mono;
	private Arbol piso;	
	private Obstaculos fruta;	
	private int numeroRandom = 0;
	private int numRandomAguila = 0;
	private int numRandomAguila2 = 0;
	private Obstaculos aguila;
	private Obstaculos aguila2;
	boolean nivel2 = false;
	
	Random r = new Random();

	// variables para salto
	private double velocidad = 10.0;
	private boolean gravedad = false;
	private boolean saltando = false;
	// variables de puntuacion
	int puntuacion = 0;
	boolean yaSumo = false;
	boolean sumoFruta = false;
	boolean superoPuntuacion = false;
	// variables de escribir mensaje
	String MensajeAEscribir = "";
	String MensajeLeido = "";
	boolean yaEscribio = false;
	boolean superoRecord = true;
	
	
	
	

	// imagenes
	private Image imgMono_caminando = Herramientas.cargarImagen("mono_camina.gif");
	private Image imgRama = Herramientas.cargarImagen("rama.png");
	private Image imgserpiente = Herramientas.cargarImagen("serpiente.png");
	private Image imgtigre = Herramientas.cargarImagen("tigre.gif");
	private Image imgArbol = Herramientas.cargarImagen("arbol.png");
	private Image imgInicio = Herramientas.cargarImagen("inicio.jpg");
	private Image imgCorazon = Herramientas.cargarImagen("corazon.png");
	private Image imgfondo = Herramientas.cargarImagen("fondo.jpg");
	private Image imgfondo2 = Herramientas.cargarImagen("fondo2.jpg");
	private Image imgPierde = Herramientas.cargarImagen("fondo3.jpg");
	private Image imgpiedra = Herramientas.cargarImagen("Piedra.png");
	private Image imgBanana = Herramientas.cargarImagen("banana.png");
	private Image imgAguila = Herramientas.cargarImagen("aguila.gif");
	private Image imgAguila2 = Herramientas.cargarImagen("aguila.gif");
	private Image imgBajoFondo = Herramientas.cargarImagen("bordeInferiorFondo.png");

	// sonidos
	private Clip sonidoInicio = Herramientas.cargarSonido("inicio.mid");
	private Clip sonido1 = Herramientas.cargarSonido("Sonido_fondo.mid");
	private Clip sonido2 = Herramientas.cargarSonido("Sonido_fondo2.mid");
	private Clip sonidoPerdio = Herramientas.cargarSonido("sonidoPerdio.mid");

	Juego() {
		// Inicializa el objeto entorno

		this.entorno = new Entorno(this, "Selva Mono Capuchin  o - Grupo XX - v1", 800, 600);

		// Inicializar lo que haga falta para el juego
		this.mono = new Mono(100, 515, 80, 100, velocidad);
		this.generarRamas();
		this.generarTigres();
		this.generarSerpientes();
		this.piso = new Arbol(400, 600, 800, 50, 0);
		this.generaFrutaAleatoria();
		this.generarAguila();

		if (this.nivel2 == true) {
			this.generarAguila2();

		}

		// Inicia el juego!
		this.entorno.iniciar();

	}

	public void dibujar_inicio() {
		this.entorno.dibujarImagen(this.imgInicio, 400, 300, 0, 1);
	}

	// crea un .txt y escribe el mensaje en el
	void escribirPuntaje() {
		try {

			FileWriter ficher = new FileWriter("record.txt");
			ficher.write(MensajeAEscribir);
			ficher.close();
			yaEscribio = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	// lee el archivo

	public String mostrarPuntaje() {

		try {

			FileReader lector = new FileReader("record.txt");
			BufferedReader BR = new BufferedReader(lector);
			MensajeLeido = BR.readLine();
			lector.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return MensajeLeido;

	}

	/*
	 * Durante el juego, el método tick() será ejecutado en cada instante y por lo
	 * tanto es el método más importante de esta clase. Aquí se debe actualizar el
	 * estado interno del juego para simular el paso del tiempo (ver el enunciado
	 * del TP para mayor detalle).
	 */

	public void tick() {

		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// ******* Pantalla de INICIO ******* 																			//
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		if (this.estado.equals("inicio")) {
			// Dibujar la pantalla de inicio.
			this.dibujar_inicio();
			sonidoInicio.start();
			// Si se presiona la tecla ENTER se cambia el estado del objeto juego.
			// En consecuencia se cambia la pantalla a la de juego.
			if (entorno.estaPresionada(entorno.TECLA_ENTER)) {
				this.estado = "juego";
				sonidoInicio.stop();
			}

		///////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// ******* Jugando *******																					 //
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////
			
		} else if (this.estado.equals("juego")) {

			if (puntuacion < 50 && superoPuntuacion == false) {
				this.entorno.dibujarImagen(imgfondo, 500, 300, 0, 0.6);
				sonido1.start();

			} else {
				superoPuntuacion = true;
				this.velocidad_tigre = 5;

				this.nivel2 = true;

				this.entorno.dibujarImagen(imgfondo2, 500, 300, 0, 0.6);
				sonido1.stop();
				sonido2.start();
			}

			entorno.cambiarFont("Times New Roman", 25, Color.yellow);
			entorno.escribirTexto("PUNTUACION: " + puntuacion, 50, 50);

			////////// Creacion, dibujo y movimiento de objetos //////////////

			//// <<< RAMAS >>> ////
			for (int j = 0; j < ramas.length; j++) {
				if (this.ramas[j] != null) {
					this.ramas[j].dibujar(this.entorno, imgRama, imgArbol);
					this.ramas[j].mover();
				} else {
					int alturaAleatoria = r.nextInt(300, 450);
					this.ramas[j] = new Arbol(1000, alturaAleatoria - (50 * j), 130, 10, 2);
				}
				if ((this.ramas[j].getX() + (this.ramas[j].getAncho())) < 0) {
					this.ramas[j] = null;
				}
			}

			//// <<< TIGRES >>> ///////
			// creacion aleatoria y continua de tigres
			for (int k = 0; k < tigres.length; k++) {
				// si tigre pasa del borde izquierdo se lo vuelve a crear
				if (this.tigres[k] != null && this.tigres[k].getX() + (this.tigres[k].getAncho()) == 0) {
					this.tigres[k] = null;
				}

				// si me mataron al tigre lo vuelvo a crear
				if (this.tigres[k] != null) {
					this.tigres[k].dibujar(this.entorno, imgtigre);
					this.tigres[k].mover();
				}
				if (this.tigres[k] == null) {
					this.tigres[k] = new Obstaculos(900 + (800 * k), 530, 100, 60, this.velocidad_tigre);
				}

			}

			//// <<< SERPIENTES >>> ////
			// crear nuevamente serpientes
			if (serpientes != null && (serpientes.getX() + serpientes.getAncho() < 0)) {
				serpientes = null;
			}

			if (serpientes != null) {

				serpientes.dibujar(this.entorno, imgserpiente);
				serpientes.mover();
			}

			if (serpientes == null && this.ramas[1] != null && this.ramas[1].getX() > 600) {
				serpientes = new Obstaculos(this.ramas[1].getX() + 25, this.ramas[1].getY() - 25, 50, 50, 2);
			}

			//// <<<FRUTA>>> ////
			if (this.fruta != null && (this.fruta.getX() + (this.fruta.getAncho())) < 0) {
				this.fruta = null;
			}
			if (this.fruta != null) {
				this.fruta.dibujar(entorno, imgBanana);
				this.fruta.mover();
			} else {
				this.generaFrutaAleatoria();
				if (this.fruta != null) {
					sumoFruta = false;
					this.fruta.dibujar(this.entorno, imgBanana);
					this.fruta.mover();
				}

			}

			//// <<< AGUILA >>> ////
			if (aguila != null && (aguila.getX() + aguila.getAncho() < 0)) {
				aguila = null;
			}

			if (aguila != null) {
				aguila.dibujar(entorno, imgAguila);
				aguila.mover();
			}

			if (aguila == null) {
				generarAguila();

			}

			if (nivel2 == true) {

				this.chequeaAguila2();
				this.nivel2 = false;
			}

			//// <<< MONO >>> ////

			this.mono.dibujarmono(entorno, imgMono_caminando);
			// funcion de salto
			if (this.entorno.sePresiono(entorno.TECLA_ARRIBA) && saltando == false) {
				gravedad = true;
				saltando = true;
				this.mono.setVelocidad(10.0);
			}
			if (gravedad == true) {
				this.mono.Saltar();
			}

			//// <<< PIEDRAS >>> ////
			int pocisionPriedraY = (this.mono.getY());
			// cuando piedra sale de la pantalla se vuelve nula
			if (this.p1 != null && this.p1.getX() > 800) {
				disparo = false;
				this.p1 = null;
			}
			// crear la piedra
			if (this.p1 == null && disparo == false) {
				this.p1 = new Piedra(100, pocisionPriedraY, 15, 15, 8);

			}
			if (p1 != null && disparo == false) {
				this.p1.setY(pocisionPriedraY);
			}

			if (this.entorno.sePresiono(entorno.TECLA_ESPACIO)) {
				disparo = true;
			}
			if (disparo == true && p1.getX() < 800) {
				this.p1.mover();
				this.p1.dibujar(this.entorno, imgpiedra);
			}

			//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			// **************COLISIONES************** //
			//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

			// Colision_Piedra_Tigre
			for (int i = 0; i < tigres.length; i++) {
				if (p1 != null && tigres[i] != null && (Colision_Piedra_Animales(p1, tigres[i]) && disparo == true)) {
					tigres[i] = null;
					p1 = null;
					disparo = false;

				}

			}

			// Colision_Piedra_Serpiente
			if (p1 != null && serpientes != null && (Colision_Piedra_Animales(p1, serpientes) && disparo == true)) {
				serpientes = null;
				p1 = null;
				disparo = false;

			}

			// Colision_Piedra_Auila
			if (p1 != null && aguila != null && (Colision_Piedra_Animales(p1, aguila) && disparo == true)) {
				aguila = null;
				p1 = null;
				disparo = false;

			}

			// Colision_Piedra_Aguila2
			if (p1 != null && aguila2 != null && (Colision_Piedra_Animales(p1, aguila2) && disparo == true)) {
				aguila2 = null;
				p1 = null;
				disparo = false;

			}

			if ((Colision_Mono_Rama(mono, ramas[0]) || Colision_Mono_Rama(mono, ramas[1])
					|| Colision_Mono_Rama(mono, ramas[2]) || Colision_Mono_Rama(mono, ramas[3]))
					&& this.mono.getVelocidad() < 0.0) {
				this.mono.setVelocidad(0.0);
				saltando = false;
				if (yaSumo == false) {
					puntuacion += 5;
				}
				yaSumo = true;
			} else {
				yaSumo = false;
			}

			if (Colision_Mono_Piso(mono, piso)) {
				this.mono.setVelocidad(0.0);
				saltando = false;
			}
			// Colision_Mono_Tigre
			for (int i = 0; i < tigres.length; i++) {
				if (Colision_Mono_Animales(mono, tigres[i])) {
					this.mono.setVidas(this.mono.getVidas() - 1);
					tigres[i] = null;
					if (this.mono.getVidas() <= 0) {
						this.estado = "final";
					}
				}
			}

			// Colision Mono_Serpiente
			if (Colision_Mono_Animales(mono, serpientes)) {
				this.mono.setVidas(this.mono.getVidas() - 1);
				serpientes = null;
				puntuacion = puntuacion - 5;
				if (this.mono.getVidas() <= 0) {
					this.estado = "final";
				}
			}

			// Colision Mono_Aguila
			if (Colision_Mono_Animales(mono, aguila)) {
				puntuacion = puntuacion - 15;
				aguila = null;

			}

			// Colision Mono_Aguila2
			if (Colision_Mono_Animales(mono, aguila2)) {
				puntuacion = puntuacion - 15;
				aguila2 = null;

			}

			// Colisiona Mono_fruta
			if (fruta != null && sumoFruta == false && Colision_Mono_Animales(mono, fruta)) {

				this.puntuacion = this.puntuacion + 7;
				sumoFruta = true;
				fruta = null;
				System.out.println("mono choco con fruta");
			}

			this.entorno.dibujarImagen(imgBajoFondo, 500, 300, 0, 0.6);

			// Dibuja_vidas
			for (int i = 1; i <= this.mono.getVidas(); i++) {
				this.entorno.dibujarImagen(this.imgCorazon, 550 + 60 * i, 550, 0, 0.05);
			}

			//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			// 					**************PERDIO************** 															//
			//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		} else if (this.estado.equals("final")) {

			this.entorno.dibujarImagen(imgPierde, 400, 300, 0, 1);
			entorno.cambiarFont("Arial", 32, Color.white);
			sonido1.stop();
			sonido2.stop();
			sonidoPerdio.start();

			// Graba y muestra la mayor puntuacion
			if (Integer.parseInt(mostrarPuntaje()) < puntuacion && yaEscribio == false && superoRecord == true) {
				this.MensajeAEscribir = this.MensajeAEscribir + puntuacion;
				escribirPuntaje();
			} else if (superoRecord = true) {
				entorno.cambiarFont("Arial", 28, Color.black);
				entorno.escribirTexto("El record es: " + mostrarPuntaje(), 200, 350);
			}
			// entorno.cambiarFont("Arial", 28, Color.black);
			entorno.cambiarFont("Arial", 50, Color.red);
			entorno.escribirTexto("GAME OVER ", 230, 200);
			entorno.cambiarFont("Arial", 28, Color.darkGray);
			entorno.escribirTexto("Hiciste " + puntuacion + " puntos!!!", 200, 300);

			entorno.cambiarFont("Arial", 28, Color.red);
			entorno.escribirTexto("¡Presione ENTER para volver a jugar", 200, 400);
			entorno.escribirTexto("o la tecla Fin para salir!", 200, 430);

		
			
			///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			//     **************  SEGUIR JUGANDO  O SALIR  **************													 //
			///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

			if (entorno.estaPresionada(entorno.TECLA_ENTER)) {
				superoPuntuacion = false;
				sonidoPerdio.stop();
				this.aguila2 = null;
				this.tigres = null;
				this.serpientes = null;
				this.ramas = null;
				this.fruta = null;
				this.aguila = null;

				ramas = new Arbol[4];
				this.generarRamas();
				this.tigres = new Obstaculos[3];
				this.generaFrutaAleatoria();
				this.velocidad_tigre = 3;
				this.generarTigres();
				this.generarSerpientes();
				this.generarAguila();

				this.mono.setVidas(3);
				puntuacion = 0;
				this.estado = "juego";
			}
			if (entorno.estaPresionada(entorno.TECLA_FIN)) {
				System.exit(0);
			}
		}
	}

	public void generarTigres() {

		for (int i = 0; i < tigres.length; i++) {
			int distanciaAleatoria = r.nextInt(600, 900);
			this.tigres[i] = new Obstaculos(distanciaAleatoria + (500 * i), 530, 100, 60, this.velocidad_tigre);
		}
	}

	public void generarRamas() {
		for (int i = 0; i < ramas.length; i++) {
			int distanciaAleatoria = r.nextInt(800, 900);
			int altura = r.nextInt(300, 450);
			this.ramas[i] = new Arbol(distanciaAleatoria + (250 * i), altura - (50 * i), 130, 2, 2);
		}
	}

	public void generarSerpientes() {
		this.serpientes = new Obstaculos(this.ramas[1].getX() - 30, this.ramas[1].getY() - 30, 60, 60, 2);
	}

	public void generarAguila() {

		numRandomAguila = (int) (Math.random() * (200 - 50 + 1) + 50);
		System.out.println(numRandomAguila);
		this.aguila = new Obstaculos(800, numRandomAguila, 50, 100, 1);

	}

	public void generarAguila2() {

		numRandomAguila2 = (int) (Math.random() * (300 - 100 + 1) + 100);
		System.out.println(numRandomAguila2);
		this.aguila2 = new Obstaculos(800, numRandomAguila2, 50, 100, 3);

	}

	public void chequeaAguila2() {
		if (aguila2 != null && (aguila2.getX() + aguila2.getAncho() < 0)) {
			aguila2 = null;
		}
		if (aguila2 != null) {
			aguila2.dibujar(entorno, imgAguila2);
			aguila2.mover();
		}
		if (aguila2 == null) {
			generarAguila2();
		}
	}

	public void generaFrutaAleatoria() {
		numeroRandom = (int) (Math.random() * 100 + 1);

		if (ramas[0] != null && 0 < numeroRandom && numeroRandom < 6 && ramas[0].getX() > 750) {
			this.fruta = new Obstaculos(ramas[0].getX(), ramas[0].getY() - 20, 30, 2, 2);
		} else if (ramas[1] != null && 25 > numeroRandom && numeroRandom < 31 && ramas[1].getX() > 750) {
			this.fruta = new Obstaculos(ramas[1].getX(), ramas[1].getY() - 20, 30, 2, 2);
		} else if (ramas[2] != null && 50 > numeroRandom && numeroRandom < 56 && ramas[2].getX() > 750) {
			this.fruta = new Obstaculos(ramas[2].getX(), ramas[2].getY() - 20, 30, 2, 2);
		} else if (ramas[3] != null && 75 > numeroRandom && numeroRandom < 86 && ramas[3].getX() > 750) {
			this.fruta = new Obstaculos(ramas[3].getX(), ramas[3].getY() - 20, 30, 2, 2);
		}
	}

	public static boolean Colision(int x1, int y1, int b1, int h1, int x2, int y2, int b2, int h2) {
		Rectangle inter = new Rectangle();
		Point aux = new Point();
		// Bloque para la coor x e y del rec mayor
		inter.x = Math.min(x1 - b1 / 2, x2 - b2 / 2);
		inter.y = Math.min(y1 - h1 / 2, y2 - h2 / 2);
		// Bloque para la coor x . y sup der del PRO mayor
		aux.x = Math.max(x1 + b1 / 2, x2 + b2 / 2);
		aux.y = Math.max(y1 + h1 / 2, y2 + h2 / 2);
		// Bloque pará el alto y ancho del rec mayor
		inter.width = aux.x - inter.x;
		inter.height = aux.y - inter.y;
		// Bloque para determincar si hay interseccion
		if (inter.width < b1 + b2 && inter.height < h1 + h2) {
			return true;
		}
		return false;
	}

	public static boolean Colision_Mono_Rama(Mono mono, Arbol rama) {
		if (rama != null && ((mono.getY() + mono.getAlto() / 3) < rama.getY())
				&& (Colision(mono.getX(), mono.getY() + 3 * mono.getAlto() / 8, mono.getAncho(), mono.getAlto() / 4,
						rama.getX(), rama.getY(), rama.getAncho(), rama.getAlto()))) {
			return true;
		}
		return false;
	}

	public static boolean Colision_Mono_Piso(Mono mono, Arbol piso) {
		if (Colision(mono.getX(), mono.getY() + 3 * mono.getAlto() / 8, mono.getAncho(), mono.getAlto() / 4,
				piso.getX(), piso.getY(), piso.getAncho(), piso.getAlto())) {
			return true;
		}
		return false;
	}

	public static boolean Colision_Piedra_Animales(Piedra piedra, Obstaculos animal) {
		if (animal != null && (Colision(piedra.getX(), piedra.getY() + piedra.getAlto() / 2, piedra.getAncho(),
				piedra.getAlto() / 2, animal.getX(), animal.getY(), animal.getAncho(), animal.getAlto()))) {
			return true;
		}
		return false;
	}

	public static boolean Colision_Mono_Animales(Mono mono, Obstaculos animal) {
		if (animal != null && (Colision(mono.getX(), mono.getY() + mono.getAlto() / 2, mono.getAncho(),
				mono.getAlto() / 2, animal.getX(), animal.getY(), animal.getAncho(), animal.getAlto()))) {
			return true;
		}

		return false;
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego();

	}
}
