package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Principal {

	public static void main(String[] args) {
		
		String url = "jdbc:mysql://dns11036.phdns11.es:3306/ad2425_nsanchez";
        String username = "nsanchez";
        String password = "12345";
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            java.sql.Statement stmt = null;
            DatabaseDAL.crearDB(conn, stmt);
        } catch (SQLException e) {
            System.err.println("Error en la conexión: " + e.getMessage());
        }
        
        /*
         * try {
         * 		PreparedStatement pstmt = null;
    			Persona per = new Persona();
    			String SQL = "select from TABLA order by apellido";
    			pstmt = conn.prepareStatement(SQL);
    			pstmt.setInt(1, per.getId());
    			pstmt.setString(2, per.getNombre());
    			pstmt.setString(3, per.getApellido());
    			pstmt.setInt(4, per.getEdad());
    			System.out.println("acho");
    			System.out.println(pstmt.toString()); // fufara?
    			//continuación de código
    		} catch (SQLException e) {
    			//continuación de código
    		} finally {
    			pstmt.close();
    		}
         */
	}

}
