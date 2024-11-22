package main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseDAL {
	
	public static void crearDB(Connection conn, java.sql.Statement stmt) throws SQLException {
		try {
			String filePath = "src/txt/inserts.txt";
            // Creamos un nuevo objeto con la conexión
            stmt = conn.createStatement();
            
            String sql = "drop table if exists persona";
            stmt.executeUpdate(sql);
            
            // Definimos la sentencia de crear una nueva base de datos y ejecutarla
            sql = "CREATE TABLE persona (id INT, nombre VARCHAR(50), apellido VARCHAR(50), edad INT);\n";
            stmt.executeUpdate(sql);
            
            try {
    			BufferedReader br = new BufferedReader(new FileReader(filePath));
    			String line;

                // Leer el archivo línea por línea
                while ((line = br.readLine()) != null) {
                    if (!line.trim().isEmpty()) { // Ignorar líneas vacías
                    	sql = line;
                        stmt.executeUpdate(sql);
                    }
                }
                
                System.out.println("Los inserts han sido exitosos");
                
                br.close();
    		} catch (FileNotFoundException e) {
    			e.printStackTrace();
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
            
        } catch(SQLException se) {
            //Gestionamos los posibles errores que puedan surgir durante la ejecucion de la insercion
            se.printStackTrace();
        } catch(Exception e) {
            //Gestionamos los posibles errores
            e.printStackTrace();
        } finally {
            stmt.close();
        }
	}
	
	public static void selectAllOrdEd(Connection conn) throws SQLException {
		String sql = "SELECT * FROM persona ORDER BY edad";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet resultado = pstmt.executeQuery();
		while(resultado.next()){
			System.out.println("---------------------------------------------");
			System.out.println("ID: " + resultado.getInt("id"));
			System.out.println("Nombre: " + resultado.getString("nombre"));
			System.out.println("Apellidos: " + resultado.getString("apellido"));
			System.out.println("Edad: " + resultado.getInt("edad"));
			System.out.println("---------------------------------------------");
		}
		resultado.close();
	}
	
	public static void selectNomApOrdAp(Connection conn) throws SQLException {
		String sql = "SELECT nombre, apellido FROM persona ORDER BY apellido";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet resultado = pstmt.executeQuery();
		while(resultado.next()){
			System.out.println("---------------------------------------------");
			System.out.println("Nombre: " + resultado.getString("nombre"));
			System.out.println("Apellidos: " + resultado.getString("apellido"));
			System.out.println("---------------------------------------------");
		}
		resultado.close();
	}
	
	public static void selectNomApEd30(Connection conn) throws SQLException {
		String sql = "SELECT nombre, apellido, edad FROM persona WHERE edad >= 30 ORDER BY edad";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet resultado = pstmt.executeQuery();
		while(resultado.next()){
			System.out.println("---------------------------------------------");
			System.out.println("Nombre: " + resultado.getString("nombre"));
			System.out.println("Apellidos: " + resultado.getString("apellido"));
			System.out.println("Edad: " + resultado.getInt("edad"));
			System.out.println("---------------------------------------------");
		}
		resultado.close();
	}
	
	public static void selectNomJApOrdAp(Connection conn) throws SQLException {
		String sql = "SELECT nombre, apellido FROM persona WHERE nombre LIKE 'J%' ORDER BY apellido";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet resultado = pstmt.executeQuery();
		while(resultado.next()){
			System.out.println("---------------------------------------------");
			System.out.println("Nombre: " + resultado.getString("nombre"));
			System.out.println("Apellidos: " + resultado.getString("apellido"));
			System.out.println("---------------------------------------------");
		}
		resultado.close();
	}
	
	public static void selectNomCApAEdOrdEd(Connection conn) throws SQLException {
		String sql = "SELECT nombre, apellido, edad FROM persona WHERE nombre LIKE 'C%' AND apellido LIKE 'A%' ORDER BY edad desc";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet resultado = pstmt.executeQuery();
		while(resultado.next()){
			System.out.println("---------------------------------------------");
			System.out.println("Nombre: " + resultado.getString("nombre"));
			System.out.println("Apellidos: " + resultado.getString("apellido"));
			System.out.println("Edad: " + resultado.getInt("edad"));
			System.out.println("---------------------------------------------");
		}
		resultado.close();
	}
	
	public static void mediaEdad(Connection conn) throws SQLException {
		String sql = "SELECT avg(edad) FROM persona";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet resultado = pstmt.executeQuery();
		while(resultado.next()) {
			System.out.println("---------------------------------------------");
			System.out.println("Edad: " + resultado.getInt("avg(edad)"));
			System.out.println("---------------------------------------------");
		}
		resultado.close();
	}
	
	public static void apellidosMPIN(Connection conn) throws SQLException {
		System.out.println("Debido a que 'oh' y 'ma' no suelen ser frecuentes en esta BD, he usado los terminos 'in' y 'mp' ");
		String sql = "SELECT apellido FROM persona WHERE apellido LIKE '%in%' OR apellido LIKE '%mp%'";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet resultado = pstmt.executeQuery();
		while(resultado.next()) {
			System.out.println("---------------------------------------------");
			System.out.println("Apellidos: " + resultado.getString("apellido"));
			System.out.println("---------------------------------------------");
		}
		resultado.close();
	}
	
	public static void selectAllEd2432(Connection conn) throws SQLException {
		String sql = "SELECT * FROM persona WHERE edad >= 24 AND edad <= 32 ORDER BY edad";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet resultado = pstmt.executeQuery();
		while(resultado.next()){
			System.out.println("---------------------------------------------");
			System.out.println("ID: " + resultado.getInt("id"));
			System.out.println("Nombre: " + resultado.getString("nombre"));
			System.out.println("Apellidos: " + resultado.getString("apellido"));
			System.out.println("Edad: " + resultado.getInt("edad"));
			System.out.println("---------------------------------------------");
		}
		resultado.close();
	}
	
	public static void selectAllEd65(Connection conn) throws SQLException {
		System.out.println("NOTA: si se ejecuta la opcion nº13, no se mostraran datos aqui.");
		String sql = "SELECT * FROM persona WHERE edad >= 65";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet resultado = pstmt.executeQuery();
		while(resultado.next()){
			System.out.println("---------------------------------------------");
			System.out.println("ID: " + resultado.getInt("id"));
			System.out.println("Nombre: " + resultado.getString("nombre"));
			System.out.println("Apellidos: " + resultado.getString("apellido"));
			System.out.println("Edad: " + resultado.getInt("edad"));
			System.out.println("---------------------------------------------");
		}
		resultado.close();
	}
	
	public static void crearLaboral(Connection conn, java.sql.Statement stmt) throws SQLException {
		try {
			
            // Creamos un nuevo objeto con la conexión
            stmt = conn.createStatement();
            
            String sql = "ALTER TABLE persona ADD COLUMN laboral ENUM('estudiante', 'ocupado', 'parado', 'jubilado') NOT NULL;";
            stmt.executeUpdate(sql);
            System.out.println("Columna 'laboral' añadida exitosamente.");
            
        } catch(SQLException se) {
            //Gestionamos los posibles errores que puedan surgir durante la ejecucion de la insercion
            se.printStackTrace();
        } catch(Exception e) {
            //Gestionamos los posibles errores
            e.printStackTrace();
        } finally {
            stmt.close();
        }
	}
	
	public static void actualizarLaboral(Connection conn, java.sql.Statement stmt) throws SQLException {
		try {
			
            // Creamos un nuevo objeto con la conexión
            stmt = conn.createStatement();
            
            String sql = "UPDATE persona " +
                    "SET laboral = CASE " +
                    "WHEN edad < 18 THEN 'estudiante' " +
                    "WHEN edad > 65 THEN 'jubilado' " +
                    "WHEN MOD(edad, 2) = 1 THEN 'parado' " +
                    "ELSE 'ocupado' " +
                    "END";
            stmt.executeUpdate(sql);
            System.out.println("Actualización completada exitosamente.");
            
        } catch(SQLException se) {
            //Gestionamos los posibles errores que puedan surgir durante la ejecucion de la insercion
            se.printStackTrace();
        } catch(Exception e) {
            //Gestionamos los posibles errores
            e.printStackTrace();
        } finally {
            stmt.close();
        }
	}
	
	public static void consultaPropia(Connection conn, java.sql.Statement stmt) throws SQLException {
		System.out.println("Consulta en cuestion: borrado de personas en caso de que sean mayores de 60");
		try {
			
            // Creamos un nuevo objeto con la conexión
            stmt = conn.createStatement();
            
            String sql = "DELETE FROM persona " +
                    "WHERE edad > 60;";
            stmt.executeUpdate(sql);
            System.out.println("Eliminacion completada exitosamente.");
            
        } catch(SQLException se) {
            //Gestionamos los posibles errores que puedan surgir durante la ejecucion de la insercion
            se.printStackTrace();
        } catch(Exception e) {
            //Gestionamos los posibles errores
            e.printStackTrace();
        } finally {
            stmt.close();
        }
	}
	
}
