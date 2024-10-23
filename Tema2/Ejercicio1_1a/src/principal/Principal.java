package principal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Principal {

	public static void main(String[] args) {
		try (FileReader fr = new FileReader("src/ficheros/carpetas.txt")) {
			BufferedReader br = new BufferedReader(fr);
			String linea;
			crearBase();
			while ((linea=br.readLine())!=null) {
				crearOtro(linea);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void crearBase() {
		File d = new File("C:/Users/nsanchez"); //directorio que creo a partir del actual
		crearDir(d);
	}
	
	private static void crearOtro(String linea) {
		File d = new File("C:/Users/nsanchez/"+linea);
		crearDir(d);
	}
	
	private static void crearDir(File d) {
		if (!d.exists()) {
			d.mkdir();//CREAR DIRECTORIO
			System.out.println("Directorio creado");
		} else {
			System.out.println("ERROR: directorio " + d + " ya creado");
		}
	}

}
