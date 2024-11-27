package dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseDAL {
	
	private static boolean conectado;
	private static boolean compras;
	private static boolean games;
	private static boolean player;
	private static Connection conn;
	
	public static boolean isConectado() {
		return conectado;
	}

	public static boolean isCompras() {
		return compras;
	}

	public static boolean isGames() {
		return games;
	}

	public static boolean isPlayer() {
		return player;
	}

	public static boolean conectar() {
		
		boolean res = false;
		String url = "jdbc:mysql://dns11036.phdns11.es:3306/ad2425_nsanchez";
        String username = "nsanchez";
        String password = "12345";
		try {
			conn = DriverManager.getConnection(url, username, password);
			conectado = true;
			res = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
		
	}
	
	public static boolean crearTablas(int opc) {
		
		boolean res = false;
		try {
			java.sql.Statement stmt = conn.createStatement();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public static boolean insertar() {
		
		// TODO insertar los datos de una o de todas las tablas
		
	}
	
	public static void listar() {
		
		// TODO listar los datos de una o de todas las tablas
		
	}
	
	public static boolean modificar() {
		
		// TODO modificar los datos de una o de todas las tablas
		
	}
	
	public static boolean borrar() {
		
		// TODO borrar los datos de una o de todas las tablas
		
	}
	
}
