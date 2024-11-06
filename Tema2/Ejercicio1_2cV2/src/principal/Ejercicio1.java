package principal;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Ejercicio1 {

	public static void leerFichAleatorio() throws IOException {
		File fichero = new File("src/ficheros/lectura.txt");
		RandomAccessFile file = new RandomAccessFile(fichero, "r");
			
		int posicion = 0;
		String letra = "";

		while (file.getFilePointer() < file.length()) {
			
			file.seek(posicion); // nos posicionamos en posicion
			letra = file.readLine();
			System.out.println("Leido");
			escribirFichAleatorio(letra);
			posicion = posicion + 2;

		} // fin bucle while

		file.close(); // cerrar fichero
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
