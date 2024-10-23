package principal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Principal {

	public static void main(String[] args) {
		
		try (FileReader fr = new FileReader("src/ficheros/palabras.txt")) {
			BufferedReader br = new BufferedReader(fr);
			String linea;
			while ((linea=br.readLine())!=null) {
				leerDir(linea);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private static void leerDir(String linea) {
		File d = new File("C:/Users/nsanchez/OutputNS");
		if (d.exists()) {
			File f = new File("C:/Users/nsanchez/OutputNS/palabrasOrdenadas.txt");
			crearFichero(f, linea);
		} else {
			System.out.println("ERROR: el directorio no existe"); // Llega aqui
		}
		
	}

	private static void crearFichero(File f, String linea) {
		
		String[] palabras = linea.split("(?=[A-Z])");
		String fraseFinal = "";
		for (String palabra: palabras) {
			fraseFinal += palabra + "\n";
		}
		try{
			BufferedWriter bw = new BufferedWriter(new FileWriter(f));
			bw.write(fraseFinal);
			bw.close();
		} catch (IOException ioe){
		    ioe.printStackTrace();
		}
		
	}

}
