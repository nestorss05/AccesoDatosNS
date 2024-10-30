package principal;

import java.io.IOException;
import java.util.Scanner;

public class Principal {

	public static void main(String[] args) {
		int opc = 0;
		Scanner sc = new Scanner(System.in);
		
		do {
			
			System.out.println("Ejercicios: [1 - 3]");
			System.out.println("Introduzca 0 para salir");
			System.out.print("Inserta el ejercicio a ejecutar: ");
			opc = sc.nextInt();
			
			// Switch para opcion
			switch (opc) {
			
			case 1 -> {
				try {
					Ejercicio1.leerFichAleatorio();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			case 2 -> {
				try {
					Ejercicio2.leerFichAleatorio();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			case 3 -> {
				try {
					Ejercicio3.leerFichAleatorio();
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
