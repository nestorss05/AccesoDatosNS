package dal;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;

import ent.ClsCompras;
import ent.ClsGames;
import ent.ClsPlayer;
import main.Main;

public class DatabaseDAL {
	
	private static boolean conectado;
	private static boolean compras;
	private static boolean games;
	private static boolean player;
	private static Connection conn;
	private static boolean incompleto;
	
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
			comprobarTablasCreadas();
		} catch (SQLException e) {
			System.err.println("ERROR: error a la hora de conectarse a la BD (0x05): ");
			e.printStackTrace();
		}
		return res;
		
	}
	
	private static void comprobarTablasCreadas() {
		String[] tableNames = {"player", "compras", "games"};
		DatabaseMetaData md;
		try {
			md = conn.getMetaData();
			for (String tableName : tableNames) {
				try(ResultSet rs = md.getTables(null, null, tableName, new String[] {"TABLE"})) {
					if (rs.next()) {
						switch (tableName) {
						
						case "player" -> {
							player = true;
						}
						
						case "compras" -> {
							compras = true;
						}
						
						case "games" -> {
							games = true;
						}
						
						}
					}
				}
			}
		} catch (SQLException e) {
			System.err.println("ERROR: error a la hora de comprobar la existencia de las tablas (0x06): ");
			e.printStackTrace();
		}
		
	}
	
	public static boolean crearTablas(int opc) {
		
		boolean res = false;
		try {
			java.sql.Statement stmt = conn.createStatement();
			String sql = "";
			switch(opc) {
			
			case 1 -> {
				
				// Borra la tabla en caso de estar creada para rehacerla
				sql = "drop table if exists player";
				stmt.executeUpdate(sql);
				
				// Crea la tabla
				sql = "create table player (\r\n"
						+ "	idPlayer INT PRIMARY KEY,\r\n"
						+ "    nick VARCHAR(45),\r\n"
						+ "    password VARCHAR(128),\r\n"
						+ "    email VARCHAR(100)\r\n"
						+ ")";
				stmt.executeUpdate(sql);
				
				// Informa al usuario sobre la actualizacion
				System.out.println("Tabla player creada");
				
				// Actualiza la booleana a true
				player = true;
				
				// Establece los datos por defecto
				insertarDefault(1);
				
				// Actualiza res a true
				res = true;
				
			}
			
			case 2 -> {
				
				// Borra la tabla en caso de estar creada para rehacerla
				sql = "drop table if exists compras";
				stmt.executeUpdate(sql);
				
				// Crea la tabla
				sql = "create table compras (\r\n"
						+ "	idCompra INT PRIMARY KEY,\r\n"
						+ "    idPlayer INT,\r\n"
						+ "    idGames INT,\r\n"
						+ "    cosa VARCHAR(25),\r\n"
						+ "    precio DECIMAL(6,2),\r\n"
						+ "    FechaCompra DATE,\r\n"
						+ "    FOREIGN KEY (idPlayer) REFERENCES player(idPlayer),\r\n"
						+ "    FOREIGN KEY (idGames) REFERENCES games(idGames)\r\n"
						+ ")";
				stmt.executeUpdate(sql);
				
				// Informa al usuario sobre la actualizacion
				System.out.println("Tablas compras creada");
				
				// Actualiza la booleana a true
				compras = true;
				
				// Establece los datos por defecto
				insertarDefault(2);
				
				// Actualiza res a true
				res = true;
				
			}
			
			case 3 -> {
				
				// Borra la tabla en caso de estar creada para rehacerla
				sql = "drop table if exists games";
				stmt.executeUpdate(sql);
				
				// Crea la tabla
				sql = "create table games (\r\n"
						+ "	idGames INT PRIMARY KEY,\r\n"
						+ "    nombre VARCHAR(45),\r\n"
						+ "    tiempoJugado TIME\r\n"
						+ ")";
				stmt.executeUpdate(sql);
				
				// Informa al usuario sobre la actualizacion
				System.out.println("Tablas games creada");
				
				// Actualiza la booleana a true
				games = true;
				
				// Establece los datos por defecto
				insertarDefault(3);
				
				// Actualiza res a true
				res = true;
				
			}
			
			default -> {
				
				// Borra las tablas en caso de estar creadas para rehacerlas
				// Queria hacer los tres drops y los tres creates a la vez, pero me daba un SQLSyntaxErrorException al intentarlo
				sql = "drop table if exists compras";
				stmt.executeUpdate(sql);
				
				sql = "drop table if exists player";
				stmt.executeUpdate(sql);
				
				sql = "drop table if exists games";
				stmt.executeUpdate(sql);
				
				// Crea las tablas
				sql = "create table player (\r\n"
						+ "	idPlayer INT PRIMARY KEY,\r\n"
						+ "    nick VARCHAR(45),\r\n"
						+ "    password VARCHAR(128),\r\n"
						+ "    email VARCHAR(100)\r\n"
						+ ")";
				stmt.executeUpdate(sql);
				
				sql = "create table games (\r\n"
						+ "	idGames INT PRIMARY KEY,\r\n"
						+ "    nombre VARCHAR(45),\r\n"
						+ "    tiempoJugado TIME\r\n"
						+ ")";
				stmt.executeUpdate(sql);
				
				sql = "create table compras (\r\n"
						+ "	idCompra INT PRIMARY KEY,\r\n"
						+ "    idPlayer INT,\r\n"
						+ "    idGames INT,\r\n"
						+ "    cosa VARCHAR(25),\r\n"
						+ "    precio DECIMAL(6,2),\r\n"
						+ "    FechaCompra DATE,\r\n"
						+ "    FOREIGN KEY (idPlayer) REFERENCES player(idPlayer),\r\n"
						+ "    FOREIGN KEY (idGames) REFERENCES games(idGames)\r\n"
						+ ")";
				stmt.executeUpdate(sql);
				
				// Informa al usuario sobre la actualizacion
				System.out.println("Las tablas han sido creadas");
				
				// Actualiza las booleanas a true
				player = true;
				games = true;
				compras = true;
				
				// Establece los datos por defecto
				insertarDefault(4);
				
				// Actualiza res a true
				res = true;
				
			}
			
			}
			
			stmt.close();
			
		} catch (SQLException e) {
			System.err.println("ERROR: ha habido un error a la hora de crear la tabla (0x07): ");
			e.printStackTrace();
		}
		
		return res;
		
	}
	
	public static boolean insertarDefault(int opc) {
		
		boolean res = false;
		String filePath = "";
		
		try {
			java.sql.Statement stmt = conn.createStatement();
			switch (opc) {
			
			case 1 -> {
				if (player) {
					filePath = "src/txt/players.txt";
					res = leerDefault(stmt, filePath);
				} else {
					System.err.println("ERROR: la tabla no esta creada (0x0c)");
				}
			}
			
			case 2 -> {
				if (compras) {
					filePath = "src/txt/compras.txt";
					res = leerDefault(stmt, filePath);
				} else {
					System.err.println("ERROR: la tabla no esta creada (0x0c)");
				}
			}
			
			case 3 -> {
				if (games) {
					filePath = "src/txt/games.txt";
					res = leerDefault(stmt, filePath);
				} else {
					System.err.println("ERROR: la tabla no esta creada (0x0c)");
				}
			}
			
			case 4 -> {
				if (player && compras && games) {
					filePath = "src/txt/players.txt";
					res = leerDefault(stmt, filePath);
					filePath = "src/txt/games.txt";
					res = leerDefault(stmt, filePath);
					filePath = "src/txt/compras.txt";
					res = leerDefault(stmt, filePath);
				} else {
					System.err.println("ERROR: una o varias de las tablas no estan creadas (0x0d)");
				}
			}
			
			}
			
		} catch (SQLException e) {
			System.err.println("ERROR: error interno a la hora de insertar los datos default (0x0b)");
			e.printStackTrace();
		}
		
		System.out.println("Los inserts default han sido exitosos");
		return res;
		
	}
	
	private static boolean leerDefault(java.sql.Statement stmt, String filePath) {
		String sql = "";
		boolean res = false;
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
            br.close();
            System.out.println("Datos default escritos en la DB");
            res = true;
		} catch (FileNotFoundException e) {
			System.err.println("ERROR: no se ha encontrado el archivo (0x08): ");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("ERROR: error de E/S (0x09)");
			e.printStackTrace();
		} catch (SQLException e) {
			System.err.println("ERROR: error a la hora de insertar los datos default (0x0a)");
			e.printStackTrace();
		}
		return res;
	}
	
	public static boolean insertar(int opc) {
		
		incompleto = false;
		int existe = 2;
		boolean res = false;
		String sql = "";
		ClsPlayer player = new ClsPlayer();
		ClsGames game = new ClsGames();
		ClsCompras compra = new ClsCompras();
		
		try {
			java.sql.Statement stmt = conn.createStatement();
			switch (opc) {
			
			case 1 -> {
				player = pedirJugador();
				existe = existeID(player.getIdPlayer(), 1);
				sql = "INSERT INTO player VALUES (" + player.getIdPlayer() + ", '" + player.getNick() + "', '" + player.getPassword() + "', '" + player.getEmail() + "')";
			}
			
			case 2 -> {
				compra = pedirCompra();
				existe = existeID(compra.getIdCompra(), compra.getIdPlayer(), compra.getIdGames());
				sql = "INSERT INTO compras VALUES (" + compra.getIdCompra() + ", " + compra.getIdPlayer() + ", " + compra.getIdGames() + ", '" + compra.getCosa() + "', " + compra.getPrecio() + ", '" + compra.getDate() + "')";
			}
			
			case 3 -> {
				game = pedirJuego();
				existe = existeID(game.getIdGames(), 3);
				sql = "INSERT INTO games VALUES (" + game.getIdGames() + ", '" + game.getNombre() + "', '" + game.getTiempoJugado() + "')";
			}
			
			}
			
			if (!incompleto && existe == 1) {
				stmt.executeUpdate(sql);
				System.out.println("Datos escritos en la DB");
		        res = true;
			} else {
				switch (existe) {
				
				case 0 -> {
					System.err.println("ERROR: el ID insertado ya existe en la DB (0x14)");
				}
				
				case -1 -> {
					System.err.println("ERROR: el ID de jugador y/o el ID de juegos no existen en la DB. Crealos primero y vuelva a intentarlo. (0x15)");
				}
				
				}
				
			}
	        
		} catch (SQLException e) {
			System.err.println("ERROR: error a la hora de insertar el dato (0x11)");
		}
		
		return res;
		
	}
	
	private static ClsPlayer pedirJugador() {
		
		int id = 0;
		String nick = "";
		String password = "";
		String email = "";
		
		try {
			System.out.println("Inserta el ID del jugador");
			id = Main.sc.nextInt();
			Main.sc.nextLine();
			
			System.out.println("Inserta el nickname del jugador");
			nick = Main.sc.nextLine();
			
			System.out.println("Inserta la contraseña del jugador");
			password = Main.sc.nextLine();
			
			System.out.println("Inserta el correo del jugador");
			email = Main.sc.nextLine();
		} catch (InputMismatchException e) {
			System.err.println("ERROR: error de respuesta. (0x12)");
			Main.sc.nextLine();
			e.printStackTrace();
			incompleto = true;
		}
		
		ClsPlayer player = new ClsPlayer(id, nick, password, email);
		return player;
		
	}
	
	private static ClsCompras pedirCompra() {
		
		int idCompra = 0;
		int idPlayer = 0;
		int idGames = 0;
		String cosa = "";
		float precio = 0f;
		String fechaCompra = "";
		
		try {
			System.out.println("Inserta el ID de la compra");
			idCompra = Main.sc.nextInt();
			
			System.out.println("Inserta el ID del jugador");
			idPlayer = Main.sc.nextInt();
			
			System.out.println("Inserta el ID del juego");
			idGames = Main.sc.nextInt();
			Main.sc.nextLine();
			
			System.out.println("Inserta la descripcion de la compra");
			cosa = Main.sc.nextLine();
			
			System.out.println("Inserta el precio del juego");
			precio = Main.sc.nextFloat();
			Main.sc.nextLine();
			
			System.out.println("Inserta la fecha de compra del juego (AAAA-MM-DD)");
			fechaCompra = Main.sc.nextLine();
			
		} catch (InputMismatchException e) {
			System.err.println("ERROR: error de respuesta. (0x12)");
			Main.sc.nextLine();
			e.printStackTrace();
			incompleto = true;
		}
		
		ClsCompras compra = new ClsCompras(idCompra, idPlayer, idGames, cosa, precio, fechaCompra);	
		return compra;
		
	}
	
	private static ClsGames pedirJuego() {
		
		int id = 0;
		String nombre = "";
		String time = "";
		
		try {
			System.out.println("Inserta el ID del juego");
			id = Main.sc.nextInt();
			Main.sc.nextLine();
			
			System.out.println("Inserta el nombre del juego");
			nombre = Main.sc.nextLine();
			
			System.out.println("Inserta el tiempo jugado del juego (HH:MM:SS)");
			time = Main.sc.nextLine();
		} catch (InputMismatchException e) {
			System.err.println("ERROR: error de respuesta. (0x12)");
			Main.sc.nextLine();
			e.printStackTrace();
			incompleto = true;
		}
		
		ClsGames game = new ClsGames(id, nombre, time);
		return game;
		
	}
	
	private static int existeID(int id, int opc) {
		
		int salida = 0;
		int res = 0;
		String sql = "";
		
		switch (opc) {
		
		case 1 -> {
			
			sql = "SELECT EXISTS (SELECT 1 FROM player WHERE idPlayer = " + id + ") AS existe";
			PreparedStatement pstmt;
			try {
				pstmt = conn.prepareStatement(sql);
				ResultSet resultado = pstmt.executeQuery();
				salida = resultado.getInt("existe");
			} catch (SQLException e) {
				System.out.println("El jugador existe");
			}
			
		}
		
		case 3 -> {
			
			sql = "SELECT EXISTS (SELECT 1 FROM games WHERE idGames = " + id + ") AS existe";
			PreparedStatement pstmt;
			try {
				pstmt = conn.prepareStatement(sql);
				ResultSet resultado = pstmt.executeQuery();
				salida = resultado.getInt("existe");
			} catch (SQLException e) {
				System.out.println("El juego existe");
			}
			
		}
		
		}
		
		if (salida == 0) {
			res = 1;
		}
		
		return res;
		
	}
	
	private static int existeID(int idCompra, int idPlayer, int idGames) {
		
		// TODO: comprobar codigo (no me deja el perro del Linux)
		
		int salida = 0;
		int res = 1;
		String sql = "";
		
		sql = "SELECT EXISTS (SELECT 1 FROM compras WHERE idCompra = " + idCompra + ") AS existe";
		PreparedStatement pstmt;
		try {
			pstmt = conn.prepareStatement(sql);
			ResultSet resultado = pstmt.executeQuery();
			salida = resultado.getInt("existe");
		} catch (SQLException e) {
			// TODO: a veces se muestra este mensaje de error aunque el objeto no exista
			System.err.println("La compra existe");
		}
		
		if (salida == 0) {
			sql = "SELECT EXISTS (SELECT 1 FROM player WHERE idPlayer = " + idPlayer + ") AS existe";
			try {
				pstmt = conn.prepareStatement(sql);
				ResultSet resultado = pstmt.executeQuery();
				salida = resultado.getInt("existe");
			} catch (SQLException e) {
				// TODO: a veces se muestra este mensaje de error aunque el objeto no exista
				System.err.println("ERROR: el jugador no existe (0x19)");
			}
			if (salida == 1) {
				sql = "SELECT EXISTS (SELECT 1 FROM games WHERE idGames = " + idGames + ") AS existe";
				try {
					pstmt = conn.prepareStatement(sql);
					ResultSet resultado = pstmt.executeQuery();
					salida = resultado.getInt("existe");
				} catch (SQLException e) {
					// TODO: a veces se muestra este mensaje de error aunque el objeto no exista
					System.err.println("ERROR: el juego no existe (0x1a)");
				}
				if (salida == 1) {
					res = 1;
				} else {
					res = -1;
				}
			} else {
				res = -1;
			}
		} else {
			res = 0;
		}
				
		return res;
	
	}
	
	public static void listar(int opc) {
		
		int opc2 = 1;
		System.out.println("¿Deseas listar la tabla entera o listarla por parametros");
		System.out.println("1. Listar la tabla entera (predeterminado)");
		System.out.println("2. Listar la tabla por parametros");
		
		try {
			opc2 = Main.sc.nextInt();
			
		} catch (InputMismatchException e) {
			System.err.println("ERROR: respuesta invalida. (0x1c)");
			Main.sc.nextLine();
			opc2 = 1;
		} finally {
			listar2(opc, opc2);
		}
		
	}
	
	public static void listar2(int opc, int opc2) {
		switch (opc2) {
		
		case 1 -> {
			listadoCompleto(opc);
		}
		
		case 2 -> {
			listadoParcial(opc);
		}
		
		default -> {
			System.err.println("ERROR: respuesta invalida");
		}
		
		}
	}
	
	public static void listadoCompleto(int opc) {
		
		// TODO probar codigo
		String sql = "";
		
		switch (opc) {
		
		case 1 -> {
			sql = "SELECT * from player";
			PreparedStatement pstmt;
			try {
				pstmt = conn.prepareStatement(sql);
				ResultSet resultado = pstmt.executeQuery();
				while(resultado.next()){
					System.out.println("ID: " + resultado.getInt("idPlayer"));
					System.out.println("Nombre" + resultado.getString("nick"));
					System.out.println("Contraseña" + resultado.getString("password"));
					System.out.println("Email" + resultado.getString("email"));
				}
			} catch (SQLException e) {
				System.err.println("ERROR: error a la hora de mostrar datos (0x16)");
			}
		}
		
		case 2 -> {
			sql = "SELECT * from compras";
			PreparedStatement pstmt;
			try {
				pstmt = conn.prepareStatement(sql);
				ResultSet resultado = pstmt.executeQuery();
				while(resultado.next()){
					System.out.println("ID de compra: " + resultado.getInt("idCompra"));
					System.out.println("ID de jugador: " + resultado.getInt("idPlayer"));
					System.out.println("ID de juego: " + resultado.getInt("idGames"));
					System.out.println("Datos: " + resultado.getString("cosa"));
					System.out.println("Precio: " + resultado.getDouble("precio"));
					System.out.println("Fecha de compra: " + resultado.getString("FechaCompra"));
				}
			} catch (SQLException e) {
				System.err.println("ERROR: error a la hora de mostrar datos (0x16)");
			}
		}
		
		case 3 -> {
			sql = "SELECT * from games";
			PreparedStatement pstmt;
			try {
				pstmt = conn.prepareStatement(sql);
				ResultSet resultado = pstmt.executeQuery();
				while(resultado.next()){
					System.out.println("ID: " + resultado.getInt("idGames"));
					System.out.println("Nombre" + resultado.getString("nombre"));
					System.out.println("Tiempo jugado" + resultado.getString("tiempoJugado"));
				}
			} catch (SQLException e) {
				System.err.println("ERROR: error a la hora de mostrar datos (0x16)");
			}
		}
		
		}
	}
	
	public static void listadoParcial(int opc) {
		
		// TODO: terminar listado parcial (como meto los parametros de por medio?)
		String sql = "";
		
		switch (opc) {
		
		case 1 -> {
			System.err.println("ERROR: no implementado");
		}
		
		case 2 -> {
			System.err.println("ERROR: no implementado");
		}
		
		case 3 -> {
			System.err.println("ERROR: no implementado");
		}
		
		}
		
	}
	
	public static boolean modificar(int opc) {
		
		// TODO probar el modify
		boolean res = false;
		incompleto = false;
		boolean commit = false;
		int noExiste = 2;
		String sql = "";
		String respuestaCommit = "";
		ClsPlayer player = new ClsPlayer();
		ClsGames game = new ClsGames();
		ClsCompras compra = new ClsCompras();
		
		try {
			java.sql.Statement stmt = conn.createStatement();
			switch (opc) {
			
			case 1 -> {
				player = pedirJugador();
				noExiste = existeID(player.getIdPlayer(), 1);
				sql = "UPDATE player SET (" + player.getIdPlayer() + ", '" + player.getNick() + "', '" + player.getPassword() + "', '" + player.getEmail() + "') WHERE idPlayer = " + player.getIdPlayer();
			}
			
			case 2 -> {
				compra = pedirCompra();
				noExiste = existeID(compra.getIdCompra(), compra.getIdPlayer(), compra.getIdGames());
				sql = "UPDATE compras SET (" + compra.getIdCompra() + ", " + compra.getIdPlayer() + ", " + compra.getIdGames() + ", '" + compra.getCosa() + "', " + compra.getPrecio() + ", '" + compra.getDate() + "') WHERE idGames = " + compra.getIdCompra();
			}
			
			case 3 -> {
				game = pedirJuego();
				noExiste = existeID(game.getIdGames(), 3);
				sql = "UPDATE games SET (" + game.getIdGames() + ", '" + game.getNombre() + "', '" + game.getTiempoJugado() + "') WHERE idGames = " + game.getIdGames();
			}
			
			}
			
			System.out.println("¿Estas seguro de realizar la operacion? (S/N)");
			respuestaCommit = Main.sc.nextLine().toUpperCase();
			
			if (respuestaCommit == "S") {
				commit = true;
			}
			
			if (!incompleto && noExiste == 0 && commit) {
				stmt.executeUpdate(sql);
				System.out.println("Los datos se han actualizado correctamente");
		        res = true;
			} else {
				switch (noExiste) {
				
				case 1 -> {
					System.err.println("ERROR: el ID insertado no existe en la DB (0x1d)");
				}
				
				case -1 -> {
					System.err.println("ERROR: el ID de jugador y/o el ID de juegos no existen en la DB. Crealos primero y vuelva a intentarlo. (0x15)");
				}
				
				case 2 -> {
					System.out.println("El usuario ha decidido cancelar la operacion.");
				}
				
				}
				
			}
	        
		} catch (SQLException e) {
			System.err.println("ERROR: error a la hora de actualizar el dato (0x1e)");
		}
		
		return res;
		
	}
	
	public static boolean borrar(int opc) {
		
		// TODO: probar los borrados basicos
		boolean res = false;
		boolean commit = false;
		String respuestaCommit = "";
		String sql = "";
		int opc2 = 0;
		try {
			
			java.sql.Statement stmt = conn.createStatement();
			switch (opc) {
			
			case 1 -> {
				opc2 = pedirOpcionBorrado();
				switch (opc2) {
				
				case 1 -> {
					System.out.println("¿Estas seguro de realizar la operacion? (S/N)");
					respuestaCommit = Main.sc.nextLine().toUpperCase();
					
					if (respuestaCommit == "S") {
						commit = true;
					}
					
					if (commit) {
						sql = "DELETE FROM compras";
			            stmt.executeUpdate(sql);
			            res = true;
					} else {
						System.out.println("Operacion cancelada por el usuario.");
					}
					
				}
				
				case 2 -> {
					// TODO: borrado con parametros
					System.err.println("ERROR: no implementado");
				}
				
				}
			}
			
			case 2 -> {
				opc2 = pedirOpcionBorrado();
				switch (opc2) {
				
				case 1 -> {
					System.out.println("¿Estas seguro de realizar la operacion? (S/N)");
					respuestaCommit = Main.sc.nextLine().toUpperCase();
					
					if (respuestaCommit == "S") {
						commit = true;
					}
					
					if (commit) {
			            sql = "DELETE FROM player";
			            stmt.executeUpdate(sql);
			            res = true;
					} else {
						System.out.println("Operacion cancelada por el usuario.");
					}
				}
				
				case 2 -> {
					// TODO: borrado con parametros
					System.err.println("ERROR: no implementado");
				}
				
				}
			}
			
			case 3 -> {
				opc2 = pedirOpcionBorrado();
				switch (opc2) {
				
				case 1 -> {
					System.out.println("¿Estas seguro de realizar la operacion? (S/N)");
					respuestaCommit = Main.sc.nextLine().toUpperCase();
					
					if (respuestaCommit == "S") {
						commit = true;
					}
					
					if (commit) {
			            sql = "DELETE FROM games";
			            stmt.executeUpdate(sql);
			            res = true;
					} else {
						System.out.println("Operacion cancelada por el usuario.");
					}
					
				}
				
				case 2 -> {
					// TODO: borrado con parametros
					System.err.println("ERROR: no implementado");
				}
				
				}
			}
			
			case 4 -> {
				System.out.println("¿Estas seguro de realizar la operacion? (S/N)");
				respuestaCommit = Main.sc.nextLine().toUpperCase();
				
				if (respuestaCommit == "S") {
					commit = true;
				}
				
				if (commit) {
					sql = "DELETE FROM compras";
		            stmt.executeUpdate(sql);
		            sql = "DELETE FROM player";
		            stmt.executeUpdate(sql);
		            sql = "DELETE FROM games";
		            stmt.executeUpdate(sql);
		            res = true;
				} else {
					System.out.println("Operacion cancelada por el usuario.");
				}
			}
			
			}
		} catch (SQLException e) {
			System.err.println("ERROR: error a la hora de eliminar la tabla (0x21)");
		}
		
		return res;
		
	}
	
	public static int pedirOpcionBorrado() {
		int opc = 0;
		try {
			System.out.println("¿Deseas borrar toda la tabla o una serie de datos concretos?");
			System.out.println("1. Toda la tabla (predeterminado");
			System.out.println("2. Datos concretos");
			opc = Main.sc.nextInt();
			if (opc != 1 && opc != 2) {
				opc = 1;
			}
		} catch (InputMismatchException e) {
			System.err.println("ERROR: opcion invalida. Se escogera la opcion 1");
			opc = 1;
		}
		return opc;
	}
	
}
