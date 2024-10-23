package principal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Principal {

	public static void main(String[] args) {
		try (FileReader fr = new FileReader("src/ficheros/carpetas.txt")) {
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
		File d = new File("C:/Users/nsanchez/"+linea);
		System.out.println(linea); // Codigo a cambiar
		if (d.exists()) {
			File f = new File("C:/Users/nsanchez/"+linea+"/index.html");
			crearWeb(f, d);
		} else {
			System.out.println("ERROR: el directorio no existe");
		}
		
	}
	
	private static void crearWeb(File f, File d) {
		String web = "";
		if (!f.exists()) {
			web += "<html>\n";
			web += "<head>\n";
			web += "<title>"+d.getName()+"</title>\n";
			web += "</head>\n";
			web += "<body>\n";
			web += "<h1>"+f.getParent()+"</h1>\n";
			web += "<h3>Autor: Nestor Sanchez</h3>\n";
			web += "</body>\n";
			web += "</html>";
			try{
			     BufferedWriter bw = new BufferedWriter(new FileWriter(f));
			     bw.write(web);
			     bw.close();
			  } catch (IOException ioe){
			     ioe.printStackTrace();
			  }
			System.out.println("Pagina web creada");
		} else {
			System.out.println("ERROR: pagina web ya creada");
		}
	}

}
