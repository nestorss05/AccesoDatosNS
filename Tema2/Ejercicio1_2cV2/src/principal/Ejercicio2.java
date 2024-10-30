package principal;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Ejercicio2 {

	public static void leerFichAleatorio() throws IOException {
		File fichero = new File("src/ficheros/lectura2.txt");
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
		File fichero = new File("src/ficheros/escritura2.txt");
		// declara el fichero de acceso aleatorio
		RandomAccessFile file = new RandomAccessFile(fichero, "rw");
		String inversa = "";
		for(int i = linea.length()-1; i>=0; i--)
	    {
			inversa = inversa + linea.charAt(i);
	    }
		file.writeChars(inversa);// insertar frase
		file.close(); // cerrar fichero
		System.out.println("Escrito");
	}
	
}