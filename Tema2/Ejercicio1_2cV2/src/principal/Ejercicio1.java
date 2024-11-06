package principal;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Ejercicio1 {

	public static void leerFichAleatorio(String info) throws IOException {
		if (info.length() != 1) {
			System.err.println("ERROR: no se ha insertado ninguna letra o caracter o se ha insertado una palabra");
		} else {
			File fichero = new File("src/ficheros/lectura.txt");
			RandomAccessFile file = new RandomAccessFile(fichero, "rw");
			
			file.setLength(0); // Vacia todo	
			int posicion = 0; // Establece posicion a 0
			file.writeBytes(info); // Inserta la informacion introducida por pantalla
			
			String letraLeida = "";
			
			file.seek(posicion); // nos posicionamos en posicion
			letraLeida = file.readLine();
			System.out.println("Leido");
			escribirFichAleatorio(letraLeida);

			file.close(); // cerrar fichero
		}
	}

	private static void escribirFichAleatorio(String linea) throws IOException {
		File fichero = new File("src/ficheros/escritura.txt");
		// declara el fichero de acceso aleatorio
		RandomAccessFile file = new RandomAccessFile(fichero, "rw");
		file.setLength(0); // Vacia todo
		StringBuffer buffer = null; // buffer para almacenar letra
		
		for (int i = 1; i <= 5; i++) { // recorro los arrays
			buffer = new StringBuffer(linea);
			buffer.setLength(2);
			file.writeChars(linea);// insertar letra
		}
		
		System.out.println("Escrito");
		file.close(); // cerrar fichero
	}
	
}
