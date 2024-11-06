package principal;

import java.io.IOException;
import java.util.Scanner;

public class Principal {

	public static void main(String[] args) {
		int opc = 0;
		Scanner sc = new Scanner(System.in);
		String linea = "";
		
		do {
			
			System.out.println("Ejercicios: [1 - 3]");
			System.out.println("Introduzca 0 para salir");
			System.out.print("Inserta el ejercicio a ejecutar: ");
			opc = sc.nextInt();
			sc.nextLine();
			
			// Switch para opcion
			switch (opc) {
			
			case 1 -> {
				try {
					System.out.println("Inserta una letra");
					linea = sc.nextLine();
					Ejercicio1.leerFichAleatorio(linea);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			case 2 -> {
				try {
					System.out.println("ALERTA: ejercicio sin remodelar / en remodelacion");
					System.out.println("Inserta una frase (abcde). Los saltos de linea se pondran automaticamente");
					linea = sc.nextLine();
					Ejercicio2.leerFichAleatorio(linea);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			case 3 -> {
				try {
					System.out.println("Inserta una frase (a1 b2 c3 d4 e5)");
					linea = sc.nextLine();
					Ejercicio3.leerFichAleatorio(linea);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			case 0 -> {
				System.out.println("Saliendo...");
			}
			default -> {
				System.out.println("ERROR: respuesta invalida");
			}
			
			} // Fin Switch
			
		} while (opc != 0);
		
		sc.close();
	}

}
