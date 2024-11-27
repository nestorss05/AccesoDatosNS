package main;

import java.util.Scanner;

import dal.DatabaseDAL;

public class Main {

	public static void main(String[] args) {
		
		int opc = -1;
		int opc2 = 0;
		DatabaseDAL db = new DatabaseDAL();
		Scanner sc = new Scanner(System.in);
		do {
			
			if(!DatabaseDAL.isConectado()) {
				System.out.println("Estas desconectado de la BD. Para acceder a ella, use la opcion 1.");
			}
			
			mostrarMenu();
			opc = sc.nextInt();
			
			switch (opc) {
			
			case 1 -> {
				if (DatabaseDAL.conectar()) {
					System.out.println("Se ha conectado a la BD exitosamente");
				} else {
					System.err.println("ERROR: no se pudo conectar a la BD (0x04)");
				}
			}
			
			case 2 -> {
				if (DatabaseDAL.isConectado()) {
					System.out.println("¿Que tabla quieres insertar?");
					System.out.println("1. Player");
					System.out.println("2. Compras");
					System.out.println("3. Games");
					System.out.println("4. Todas");
					opc2 = sc.nextInt();
					
					switch (opc2) {
					
					case 2 -> {
						if (DatabaseDAL.isGames() && DatabaseDAL.isPlayer()) {
							DatabaseDAL.crearTablas(2);
						} else {
							System.err.println("ERROR: se deben crear las dos tablas anteriores primero (0x03)");
						}
					}
					
					case 1, 3, 4 -> {
						boolean res = DatabaseDAL.crearTablas(opc2);
					}
					
					default -> {
						System.err.println("ERROR: opcion de insertado invalida (0x02)");
					}
					
					}
					
				} else {
					System.err.println("ERROR: conectate a la BD primero (0x01)");
				}
			}
			
			case 3 -> {
				if (DatabaseDAL.isConectado()) {
					System.err.println("ERROR: no implementado");
					// TODO: conexion al ejercicio
				} else {
					System.err.println("ERROR: conectate a la BD primero (0x01)");
				}
			}
			
			case 4 -> {
				if (DatabaseDAL.isConectado()) {
					System.err.println("ERROR: no implementado");
					// TODO: conexion al ejercicio
				} else {
					System.err.println("ERROR: conectate a la BD primero (0x01)");
				}
			}
			
			case 5 -> {
				if (DatabaseDAL.isConectado()) {
					System.err.println("ERROR: no implementado");
					// TODO: conexion al ejercicio
				} else {
					System.err.println("ERROR: conectate a la BD primero (0x01)");
				}
			}
			
			case 6 -> {
				if (DatabaseDAL.isConectado()) {
					System.err.println("ERROR: no implementado");
					// TODO: conexion al ejercicio
				} else {
					System.err.println("ERROR: conectate a la BD primero (0x01)");
				}
			}
			
			case 0 -> {
				System.out.println("Saliendo...");
			}
			
			default -> {
				System.err.println("ERROR: opcion invalida (0x00)");
			}
			
			}
		} while (opc != 0);
		sc.close();
	}
	
	private static void mostrarMenu() {
		System.out.println("1. Conectar a BD");
		System.out.println("2. Crear tablas");
		System.out.println("3. Insertar datos");
		System.out.println("4. Listar datos");
		System.out.println("5. Modificar datos");
		System.out.println("6. Borrar datos");
		System.out.println("0. Salir");
		System.out.println("Inserta la opcion");
	}

}
