/**
 * 
 */
package pools;

import java.io.IOException;
import java.net.ServerSocket;
import java.security.Security;
import java.util.concurrent.Semaphore;

import threadServidor.ThreadServidor;

/**
 * Esta clase implementa el pool de conexiones para el servidor.
 * Infraestructura Computacional 201320
 * Universidad de los Andes.
 * Las tildes han sido eliminadas por cuestiones de compatibilidad.
 * @author Michael Andres Carrillo Pinzon
 */
public class Pool_2_Thread {

	/**
	 * Constante que especifica el numero de threads que se usan en el pool de conexiones.
	 */
	public static final int N_THREADS = 2;
	
	/**
	 * Puerto en el cual escucha el servidor.
	 */
	public static final int PUERTO = 5555;
	
	/**
	 * Metodo main del servidor con seguridad que inicializa un 
	 * pool de threads determinado por la constante nThreads.
	 * @param args Los argumentos del metodo main (vacios para este ejemplo).
	 * @throws IOException Si el socket no pudo ser creado.
	 */
	public static void main(String[] args) throws IOException {
		
		// Adiciona la libreria como un proveedor de seguridad.
		// Necesario para crear llaves.
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());		
		
		// Crea el socket que escucha en el puerto seleccionado.
		ServerSocket socket = new ServerSocket(PUERTO);
		
		// Crea un semaforo que da turnos para usar el socket.
		Semaphore semaphore = new Semaphore(1);
		
		// Genera n threads que correran durante la sesion.
		ThreadServidor [] threads = new ThreadServidor[N_THREADS];
		for ( int i = 0 ; i < N_THREADS ; i++) {
			try {
				threads[i] = new ThreadServidor(socket , semaphore);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("El servidor esta listo para aceptar conexiones.");
	}
	
}