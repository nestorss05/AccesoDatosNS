package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Principal {

	public static void main(String[] args) {
		
		int opc = -1;
		String url = "jdbc:mysql://dns11036.phdns11.es:3306/ad2425_nsanchez";
        String username = "nsanchez";
        String password = "12345";
        Scanner sc = new Scanner(System.in);
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            java.sql.Statement stmt = null;
            
            do {
            	mostrarMenu();
                opc = sc.nextInt();
                
                switch (opc) {
                
                case 1 -> {
                	DatabaseDAL.crearDB(conn, stmt);
                }
                
                case 2 -> {
                	DatabaseDAL.selectAllOrdEd(conn);
                }
                
                case 3 -> {
                	DatabaseDAL.selectNomApOrdAp(conn);
                }
                
                case 4 -> {
                	DatabaseDAL.selectNomApEd30(conn);
                }
                
                case 5 -> {
                	DatabaseDAL.selectNomJApOrdAp(conn);
                }
                
                case 6 -> {
                	DatabaseDAL.selectNomCApAEdOrdEd(conn);
                }
                
                case 7 -> {
                	DatabaseDAL.mediaEdad(conn);
                }
                
                case 8 -> {
                	DatabaseDAL.apellidosMPIN(conn);
                }
                
                case 9 -> {
                	DatabaseDAL.selectAllEd2432(conn);
                }
                
                case 10 -> {
                	DatabaseDAL.selectAllEd65(conn);
                }
                
                case 11 -> {
                	DatabaseDAL.crearLaboral(conn, stmt);
                }
                
                case 12 -> {
                	DatabaseDAL.actualizarLaboral(conn, stmt);
                }
                
                case 13 -> {
                	DatabaseDAL.consultaPropia(conn, stmt);
                }
                
                case 0 -> {
                	System.out.println("Saliendo...");
                }
                
                default -> {
                	System.err.println("ERROR: opcion invalida");
                }
                
                }
                
            } while (opc != 0);
            
            conn.close();
            
        } catch (SQLException e) {
            System.err.println("Error en la conexión: " + e.getMessage());
        } finally {
        	sc.close();
        }
        
	}
	
	private static void mostrarMenu() {
		System.out.println("1. Crear DB");
        System.out.println("2. Listado ordenado por edad");
        System.out.println("3. Listado de los nombres y apellidos ordenados por apellido.");
        System.out.println("4. Listado de nombres y apellidos de más de 30 años");
        System.out.println("5. Listado de los nombres que comiencen por \"J\" ordenados por apellido");
        System.out.println("6. Listado de los nombres que comiencen por \"C\" y los apellidos por \"A\" ordenados por edad de mayor a menor");
        System.out.println("7. Media de edad de la muestra.");
        System.out.println("8. Listado de los apellidos que contengan las letras \"oh\" o las letras \"ma\" (si el resultado fuera nulo, cambiar las letras)");
        System.out.println("9. Listado de las personas en la franja de edad comprendida entre los 24 y los 32 años.");
        System.out.println("10. Listado de las personas mayores de 65 años.");
        System.out.println("11. Crea una columna denominada \"laboral\" que contendrá los siguientes valores: estudiante, ocupado, parado, jubilado");
        System.out.println("12. Actualizar la columna laboral");
        System.out.println("13. Mi consulta propia");
        System.out.println("0. Salir");
        System.out.println("Inserta una opcion");
	}

}
