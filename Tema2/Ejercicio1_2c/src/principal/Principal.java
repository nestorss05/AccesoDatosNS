package principal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class Principal {

	public static void main(String[] args) {

		try (FileReader fr = new FileReader("src/ficheros/ordenadas1.txt")) {
			BufferedReader br = new BufferedReader(fr);
			String linea;
			String contenido = "";
			while ((linea = br.readLine()) != null) {
				contenido += linea;
			}
			leerDir(contenido);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void leerDir(String linea) {
		File f = new File("src/ficheros/palabrasOrdenadas.txt");
		crearFichero(f, linea);
	}

	private static void crearFichero(File f, String linea) {

		String[] palabras = linea.split("(?=[A-Z])");
		Arrays.sort(palabras);
		String fraseFinal = "";
		for (String palabra : palabras) {
			fraseFinal += palabra + "\n";
		}
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(f));
			bw.write(fraseFinal);
			bw.close();
			System.out.println("Se han ordenado las palabras correctamente");
		} catch (IOException ioe) {
			ioe.printStackTrace();
			System.err.println("ERROR: no se ha podido crear el fichero");
		}

	}

}
