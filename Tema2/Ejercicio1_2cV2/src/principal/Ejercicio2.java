package principal;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Ejercicio2 {

	public static void leerFichAleatorio(String info) throws IOException {
		File fichero = new File("src/ficheros/lectura2.txt");
		RandomAccessFile file = new RandomAccessFile(fichero, "rw");
			
		file.setLength(0); // Vacia todo
		
		// rellena el fichero con cada caracter de la frase mediante un For y un charAt
		for (int i = 0; i < info.length(); i++) {
			file.writeBytes(info.charAt(i) + "\n");
		}
		
		long posicion = file.length() - 1;
		String frase = "";

		while (posicion >= 0) {
			
			file.seek(posicion); // nos posicionamos en posicion
			char caracter = (char) file.readByte();  // Leer el byte como carácter

            // Añadir el carácter a la cadena (evitar el salto de línea)
            if (caracter != '\n' && caracter != '\r') {
                frase = frase + caracter;
            } else {
            	frase = frase + " ";
            }
            
			posicion--;

		} // fin bucle while

		file.close(); // cerrar fichero
		escribirFichAleatorio(frase.trim()); // escribe el fichero
	}

	private static void escribirFichAleatorio(String linea) throws IOException {
		File fichero = new File("src/ficheros/escritura2.txt");
		// declara el fichero de acceso aleatorio
		RandomAccessFile file = new RandomAccessFile(fichero, "rw");

		file.writeChars(linea);// insertar frase
		file.close(); // cerrar fichero
		System.out.println("Escrito");
	}
	
}