package main;

import java.util.InputMismatchException;
import java.util.Scanner;

import dal.DatabaseDAL;

public class Main {

	public static Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) {
		
		int opc = -1;
		int opc2 = 0;
		boolean res = false;
		do {
			
			try {
				
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
						System.err.println("ERROR: no se pudo conectar a la BD (0x03)");
					}
				}
				
				case 2 -> {
					if (DatabaseDAL.isConectado()) {
						System.out.println("¿Que tabla quieres crear?");
						System.out.println("1. Player");
						System.out.println("2. Compras");
						System.out.println("3. Games");
						System.out.println("4. Todas (predeterminado)");
						System.out.println("NOTA: si la tabla esta creada, se sobreescribira");
						opc2 = sc.nextInt();
						
						switch (opc2) {
						
						case 2 -> {
							if (DatabaseDAL.isGames() && DatabaseDAL.isPlayer()) {
								res = DatabaseDAL.crearTablas(2);
							} else {
								System.err.println("ERROR: se deben crear las dos tablas anteriores primero (0x02)");
							}
						}
						
						case 1, 3 -> {
							res = DatabaseDAL.crearTablas(opc2);
						}
						
						default -> {
							res = DatabaseDAL.crearTablas(4);
						}
						
						}
						
						if (res) {
							System.out.println("Se han creado las tablas exitosamente");
						} else {
							System.err.println("ERROR: no se pudieron crear las tablas (0x04)");
						}
						
					} else {
						System.err.println("ERROR: conectate a la BD primero (0x01)");
					}
				}
				
				case 3 -> {
					if (DatabaseDAL.isConectado()) {
						System.out.println("¿En que tabla quieres insertar datos?");
						System.out.println("1. Player");
						System.out.println("2. Compras");
						System.out.println("3. Games");
						opc2 = sc.nextInt();
						
						switch (opc2) {
						
						case 1 -> {
							if (DatabaseDAL.isPlayer()) {
								res = DatabaseDAL.insertar(opc2);
							} else {
								System.err.println("ERROR: la tabla no estan creada. Insert anulado. (0x10)");
							}
						}
						
						case 2 -> {
							if (DatabaseDAL.isCompras() && DatabaseDAL.isGames() && DatabaseDAL.isPlayer()) {
								res = DatabaseDAL.insertar(opc2);
							} else {
								System.err.println("ERROR: la tabla (o una de las otras dos) no estan creadas. Insert anulado (0x0f)");
							}
						}
						
						case 3 -> {
							if (DatabaseDAL.isGames()) {
								res = DatabaseDAL.insertar(opc2);
							} else {
								System.err.println("ERROR: la tabla no estan creada. Insert anulado. (0x10)");
							}
						}
						
						default -> {
							System.err.println("ERROR: opcion de insert invalida (0x0e)");
						}
						
						}
						
						if (res) {
							System.out.println("Se han creado las tablas exitosamente");
						} else {
							System.err.println("ERROR: no se pudo insertar el objeto (0x04)");
						}
					} else {
						System.err.println("ERROR: conectate a la BD primero (0x01)");
					}
				}
				
				case 4 -> {
					if (DatabaseDAL.isConectado()) {
						System.out.println("¿Que tabla deseas listar?");
						System.out.println("1. Player");
						System.out.println("2. Compras");
						System.out.println("3. Games");
						opc2 = sc.nextInt();
						
						switch (opc2) {
						
						case 1, 2, 3 -> {
							DatabaseDAL.listar(opc2);
						}
						
						default -> {
							System.err.println("ERROR: opcion de select invalida (0x1b)");
						}
						
						}
					} else {
						System.err.println("ERROR: conectate a la BD primero (0x01)");
					}
				}
				
				case 5 -> {
					if (DatabaseDAL.isConectado()) {
						System.out.println("¿En que tabla quieres modificar datos?");
						System.out.println("1. Player");
						System.out.println("2. Compras");
						System.out.println("3. Games");
						opc2 = sc.nextInt();
						
						switch (opc2) {
						
						case 1 -> {
							if (DatabaseDAL.isPlayer()) {
								res = DatabaseDAL.modificar(opc2);
							} else {
								System.err.println("ERROR: la tabla no esta creada. Modify anulado. (0x17)");
							}
						}
						
						case 2 -> {
							if (DatabaseDAL.isCompras() && DatabaseDAL.isGames() && DatabaseDAL.isPlayer()) {
								res = DatabaseDAL.modificar(opc2);
							} else {
								System.err.println("ERROR: la tabla (o una de las otras dos) no estan creadas. Modify anulado (0x18)");
							}
						}
						
						case 3 -> {
							if (DatabaseDAL.isGames()) {
								res = DatabaseDAL.modificar(opc2);
							} else {
								System.err.println("ERROR: la tabla no esta creada. Modify anulado. (0x17)");
							}
						}
						
						default -> {
							System.err.println("ERROR: opcion de modify invalida (0x20)");
						}
						
						}
						
						if (res) {
							System.out.println("Se han creado las tablas exitosamente");
						} else {
							System.err.println("ERROR: no se pudo insertar el objeto (0x04)");
						}
					} else {
						System.err.println("ERROR: conectate a la BD primero (0x01)");
					}
				}
				
				case 6 -> {
					if (DatabaseDAL.isConectado()) {
						System.out.println("¿Que tabla quieres eliminar?");
						System.out.println("1. Player");
						System.out.println("2. Compras");
						System.out.println("3. Games");
						System.out.println("4. Todas (predeterminado)");
						opc2 = sc.nextInt();
						
						switch (opc2) {
						
						case 2, 4 -> {
							res = DatabaseDAL.borrar(opc2);
						}
						
						case 1, 3 -> {
							if (!DatabaseDAL.isCompras()) {
								res = DatabaseDAL.borrar(opc2);
							} else {
								System.err.println("ERROR: dependencia de la tabla escogida en la FK de la tabla COMPRAS (0x20)");
							}
						}
						
						default -> {
							res = DatabaseDAL.borrar(4);
						}
						
						}
						
						if (res) {
							System.out.println("Se han eliminado las tablas exitosamente");
						} else {
							System.err.println("ERROR: no se pudieron eliminar las tablas (0x1f)");
						}
						
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
				
			} catch (InputMismatchException e) {
				System.err.println("ERROR: respuesta invalida. (0x13)");
				sc.nextLine();
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
