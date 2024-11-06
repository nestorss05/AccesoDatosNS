package principal;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Ejercicio3 {
	
	public static void leerFichAleatorio(String info) throws IOException {
		File fichero = new File("src/ficheros/lectura3.txt");
		RandomAccessFile file = new RandomAccessFile(fichero, "rw");
		file.setLength(0); // Vacia todo		
		
		int posicion = 0;
		file.writeBytes(info); // Inserta la informacion introducida por pantalla
		
		String letraLeida = "";
		file.seek(0); // Debo posicionarme para que el programa entre al bucle

		while (file.getFilePointer() < file.length()) {
			
			file.seek(posicion); // nos posicionamos en posicion
			letraLeida = file.readLine();
			System.out.println("Leido");
			escribirFichAleatorio(letraLeida);
			posicion = posicion + 2;

		} // fin bucle while

		file.close(); // cerrar fichero
	}

	private static void escribirFichAleatorio(String linea) throws IOException {
		File fichero = new File("src/ficheros/escritura3.txt");
		// declara el fichero de acceso aleatorio
		RandomAccessFile file = new RandomAccessFile(fichero, "rw");
		file.setLength(0); // Vacia todo
		String inversa = "";
		for(int i = linea.length()-1; i>=0; i-=3)
	    {
			inversa = inversa + linea.charAt(i-1);
			inversa = inversa + linea.charAt(i);
			inversa = inversa + " ";
	    }
		file.writeChars(inversa);// insertar frase
		file.close(); // cerrar fichero
		System.out.println("Escrito");
	}
	
}