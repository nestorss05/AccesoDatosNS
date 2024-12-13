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

	/**
	 * Metodo que conecta esta app a la BD
	 * @return Resultado de la conexion
	 */
	public static boolean conectar() {
		
		// Resultado a devolver
		boolean res = false;
		
		// Datos de la BD
		String url = "jdbc:mysql://dns11036.phdns11.es:3306/ad2425_nsanchez";
        String username = "nsanchez";
        String password = "12345";
        
        // Conexion
		try {
			conn = DriverManager.getConnection(url, username, password);
			conectado = true;
			res = true;
			comprobarTablasCreadas();
		} catch (SQLException e) {
			System.err.println("ERROR: error a la hora de conectarse a la BD (0x05): ");
			e.printStackTrace();
		}
		
		// Devuelve el resultado
		return res;
		
	}
	
	/**
	 * Metodo que comprueba la existencia de las tablas creadas / que se pueden crear en la BD
	 */
	private static void comprobarTablasCreadas() {
		
		// Nombres de la BD
		String[] tableNames = {"player", "compras", "games"};
		DatabaseMetaData md;
		
		// Comprueba la existencia de cada tabla
		try {
			md = conn.getMetaData();
			for (String tableName : tableNames) {
				
				// Recorre el array de nombres de tabla y va comprobando la existencia de cada tabla una por una
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
	
	/**
	 * Metodo que crea las tablas de la BD
	 * @param opc tabla escogida
	 * @return Resultado de la operacion
	 */
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
	
	/**
	 * Metodo que inserta los datos por defecto en la BD
	 * @param opc tabla escogida
	 * @return resultado de la operacion
	 */
	public static boolean insertarDefault(int opc) {
		
		// Resultado a devolver
		boolean res = false;
		
		// Ruta del fichero
		String filePath = "";
		
		try {
			java.sql.Statement stmt = conn.createStatement();
			switch (opc) {
			
			// Creacion de la tabla player
			case 1 -> {
				if (player) {
					filePath = "src/txt/players.txt";
					res = leerDefault(stmt, filePath);
				} else {
					System.err.println("ERROR: la tabla no esta creada (0x0c)");
				}
			}
			
			// Creacion de la tabla compras
			case 2 -> {
				if (compras) {
					filePath = "src/txt/compras.txt";
					res = leerDefault(stmt, filePath);
				} else {
					System.err.println("ERROR: la tabla no esta creada (0x0c)");
				}
			}
			
			// Creacion de la tabla games
			case 3 -> {
				if (games) {
					filePath = "src/txt/games.txt";
					res = leerDefault(stmt, filePath);
				} else {
					System.err.println("ERROR: la tabla no esta creada (0x0c)");
				}
			}
			
			// Creacion de todas las tablas
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
		
		// Devuelve el resultado de la operacion
		System.out.println("Los inserts default han sido exitosos");
		return res;
		
	}
	
	/**
	 * Metodo que lee los inserts guardados en un TXT y los inserta en la BD
	 * @param stmt Statement
	 * @param filePath ruta del fichero
	 * @return Resultado de la operacion
	 */
	private static boolean leerDefault(java.sql.Statement stmt, String filePath) {
		
		// Comando a ejecutar
		String sql = "";
		
		// Resultado de la operacion a devolver
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
            
            // Insercion de datos exitosa
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
		
		// Devuelve el resultado de la operacion
		return res;
	}
	
	/**
	 * Metodo que inserta un objeto en la BD
	 * @param opc tabla escogida
	 * @return Resultado de la operacion
	 */
	public static boolean insertar(int opc) {
		
		// NOTA: Queria meter las IDs autoincrementadas ya que se me olvido ponerlas anteriormente, pero no me dio tiempo y no es plan de entregar tarde este proyecto. Es lo ultimo que falta aqui
		
		// Booleana que indica si hay datos incompletos introducidos por el usuario
		incompleto = false;
		
		// No-existencia de las tablas (rubrica explicada en la funcion existeID()
		int noExiste = 2;
		
		// Resultado de la operacion
		boolean res = false;
		
		// Comando a ejecutar
		String sql = "";
		
		// Objetos de las tablas
		ClsPlayer player = new ClsPlayer();
		ClsGames game = new ClsGames();
		ClsCompras compra = new ClsCompras();
		
		try {
			java.sql.Statement stmt = conn.createStatement();
			switch (opc) {
			
			// Insert en la tabla player
			case 1 -> {
				player = pedirJugador(true, false);
				noExiste = noExisteID(player.getIdPlayer(), 1);
				sql = "INSERT INTO player VALUES (" + player.getIdPlayer() + ", '" + player.getNick() + "', '" + player.getPassword() + "', '" + player.getEmail() + "')";
			}
			
			// Insert en la tabla compras
			case 2 -> {
				compra = pedirCompra(true, false);
				
				if (compra.getIdGames() == 0 || compra.getIdPlayer() == 0) {
					noExiste = 2;
				} else {
					noExiste = noExisteID(compra.getIdCompra(), compra.getIdPlayer(), compra.getIdGames());
				}
				
				sql = "INSERT INTO compras VALUES (" + compra.getIdCompra() + ", " + compra.getIdPlayer() + ", " + compra.getIdGames() + ", '" + compra.getCosa() + "', " + compra.getPrecio() + ", '" + compra.getDate() + "')";
			}
			
			// Insert en la tabla games
			case 3 -> {
				game = pedirJuego(true, false);
				noExiste = noExisteID(game.getIdGames(), 3);
				sql = "INSERT INTO games VALUES (" + game.getIdGames() + ", '" + game.getNombre() + "', '" + game.getTiempoJugado() + "')";
			}
			
			}
			
			if (!incompleto && noExiste == 1) {
				// Si no hay datos incompletos y el ID no existe (res 1), se procedera a realizar el insert
				stmt.executeUpdate(sql);
				System.out.println("Datos escritos en la DB");
		        res = true;
			} else {
				
				// De lo contrario, el error mostrado dependera del estado de noExiste
				switch (noExiste) {
				
				case 0 -> {
					System.err.println("ERROR: el ID insertado ya existe en la DB (0x14)");
				}
				
				case -1 -> {
					System.err.println("ERROR: el ID de jugador y/o el ID de juegos no existen en la DB. Crealos primero y vuelva a intentarlo. (0x15)");
				}
				
				case -2 -> {
					System.err.println("ERROR: IDs de juego y jugador invalidos (0x2b)");
				}
				
				}
				
			}
	        
		} catch (SQLException e) {
			System.err.println("ERROR: error a la hora de insertar el dato (0x11)");
		}
		
		// Devuelve el resultado de la operacion
		return res;
		
	}
	
	/**
	 * Metodo que pregunta al usuario los datos de un jugador
	 * @param idRequired ID obligatorio o no
	 * @return Jugador con datos insertados por el usuario
	 */
	private static ClsPlayer pedirJugador(boolean idRequired, boolean borrado) {
		
		// Datos del jugador
		int id = 0;
		String nick = "";
		String password = "";
		String email = "";
		
		try {
			
			// Pide al usuario el ID del jugador (solo si es requerido)
			if (idRequired) {
				System.out.println("Inserta el ID del jugador");
				id = Main.sc.nextInt();
				if (id == 0) {
					if (borrado) {
						System.out.println("ID saltado");
					} else {
						System.err.println("ERROR: el ID no se puede saltar. Puede que la operacion salga mal.");
					}
				} 
			} else {
				id = 0;
			}
			
			// Pide al usuario el nick del jugador
			Main.sc.nextLine();
			System.out.println("Inserta el nickname del jugador");
			nick = Main.sc.nextLine();
			if (!idRequired && nick.equals("")) {
				nick = "_";
			}
			
			// Pide al usuario la contraseña del jugador
			System.out.println("Inserta la contraseña del jugador");
			password = Main.sc.nextLine();
			if (!idRequired && password.equals("")) {
				password = "_";
			}
			
			// Pide al usuario el correo del jugador
			System.out.println("Inserta el correo del jugador");
			email = Main.sc.nextLine();
			if (!idRequired && email.equals("")) {
				email = "_";
			}
			
		} catch (InputMismatchException e) {
			System.err.println("ERROR: error de respuesta. (0x12)");
			Main.sc.nextLine();
			e.printStackTrace();
			incompleto = true;
		}
		
		// Guarda los datos en un objeto ClsPlayer
		ClsPlayer player = new ClsPlayer(id, nick, password, email);
		
		// Devuelve el objeto creado
		return player;
		
	}
	
	/**
	 * Metodo que pregunta al usuario los datos de una compra
	 * @param idRequired ID obligatorio o no
	 * @return Compra con datos insertados por el usuario
	 */
	private static ClsCompras pedirCompra(boolean idRequired, boolean borrado) {
		
		// Datos de la compra
		int idCompra = 0;
		int idPlayer = 0;
		int idGames = 0;
		String cosa = "";
		float precio = 0f;
		String fechaCompra = "";
		
		try {
			
			// Pide al usuario el ID de la compra (solo si es necesario)
			if (idRequired) {
				System.out.println("Inserta el ID de la compra");
				idCompra = Main.sc.nextInt();
				if (idCompra == 0) {
					if (borrado) {
						System.out.println("ID saltado");
					} else {
						System.err.println("ERROR: el ID no se puede saltar. Puede que la operacion salga mal.");
					}
				} 
			} else {
				idCompra = 0;
			}
			
			// Pide al usuario el ID del jugador
			System.out.println("Inserta el ID del jugador");
			idPlayer = Main.sc.nextInt();
			
			// Control para idPlayer en 0 (skipper)
			if (idPlayer == 0) {
				if (!idRequired || borrado) {
					System.out.println("ID del jugador saltado");
				} else {
					System.err.println("ERROR: ID del jugador invalido. (0x2a)");
				}
			}
			
			// Pide al usuario el ID del juego
			System.out.println("Inserta el ID del juego");
			idGames = Main.sc.nextInt();
			Main.sc.nextLine();
			
			// Control para idGames en 0 (skipper)
			if (idGames == 0) {
				if (!idRequired || borrado) {
					System.out.println("ID del juego saltado");
				} else {
					System.err.println("ERROR: ID del juego invalido. (0x2c)");
				}
			}
			
			// Pide al usuario la descripcion de la compra
			System.out.println("Inserta la descripcion de la compra");
			cosa = Main.sc.nextLine();
			if (!idRequired && cosa.equals("")) {
				cosa = "_";
			}
			
			// Pide al usuario el precio del juegog
			System.out.println("Inserta el precio del juego");
			precio = Main.sc.nextFloat();
			Main.sc.nextLine();
			
			// Pide al usuario la fecha de compra del juego
			System.out.println("Inserta la fecha de compra del juego (AAAA-MM-DD)");
			fechaCompra = Main.sc.nextLine();
			if (!idRequired && fechaCompra.equals("")) {
				fechaCompra = "1970-01-01";
			}
			
		} catch (InputMismatchException e) {
			System.err.println("ERROR: error de respuesta. (0x12)");
			Main.sc.nextLine();
			e.printStackTrace();
			incompleto = true;
		}
		
		// Guarda los datos en un objeto ClsCompras
		ClsCompras compra = new ClsCompras(idCompra, idPlayer, idGames, cosa, precio, fechaCompra);	
		
		// Devuelve el objeto creado
		return compra;
		
	}
	
	/**
	 * Metodo que pregunta al usuario los datos de un juego
	 * @param idRequired ID obligatorio o no
	 * @return Juego con datos insertados por el usuario
	 */
	private static ClsGames pedirJuego(boolean idRequired, boolean borrado) {
		
		// Datos del juego
		int id = 0;
		String nombre = "";
		String time = "";
		
		try {
			
			// Pide al usuario el ID del juego (solo si es necesario)
			if (idRequired) {
				System.out.println("Inserta el ID del juego");
				id = Main.sc.nextInt();
				if (id == 0) {
					if (borrado) {
						System.out.println("ID saltado");
					} else {
						System.err.println("ERROR: el ID no se puede saltar. Puede que la operacion salga mal.");
					}
				} 
			} else {
				id = 0;
			}
			
			// Pide al usuario el nombre del juego
			Main.sc.nextLine();
			System.out.println("Inserta el nombre del juego");
			nombre = Main.sc.nextLine();
			if (!idRequired && nombre.equals("")) {
				nombre = "_";
			}
			
			// Pide al usuario el tiempo jugado del juego
			System.out.println("Inserta el tiempo jugado del juego (HH:MM:SS)");
			time = Main.sc.nextLine();
			if (!idRequired && time.equals("")) {
				time = "00:00:00";
			}
			
		} catch (InputMismatchException e) {
			System.err.println("ERROR: error de respuesta. (0x12)");
			Main.sc.nextLine();
			e.printStackTrace();
			incompleto = true;
		}
		
		// Guarda los datos en un objeto ClsGames
		ClsGames game = new ClsGames(id, nombre, time);
		
		// Devuelve el objeto creado
		return game;
		
	}
	
	/**
	 * Metodo que comprueba la no-existencia de un ID (solo tablas Juegos y Jugadores)
	 * @param id id a analizar
	 * @param opc tabla escogida
	 * @return Existencia del ID
	 * 0 = el ID existe
	 * 1 = el ID no existe
	 */
	private static int noExisteID(int id, int opc) {
				
		// Salida que da el ResultSet
		int salida = 0;
		
		// Resultado de la operacion a devolver
		int res = 0;
		
		// Comando a ejecutar
		String sql = "";
		
		switch (opc) {
		
		case 1 -> {
			
			// Comprueba la existencia de un ID en la tabla player
			sql = "SELECT EXISTS (SELECT 1 FROM player WHERE idPlayer = " + id + ") AS existe";
			PreparedStatement pstmt;
			try {
				pstmt = conn.prepareStatement(sql);
				ResultSet resultado = pstmt.executeQuery();
				if (resultado.next()) {
					salida = resultado.getInt("existe");
				}
			} catch (SQLException e) {
				System.err.println("ERROR: error a la hora de comprobar la existencia de un jugador (0x22)");
			}
			
		}
		
		case 2 -> {
			
			// Comprueba la existencia de un ID en la tabla compras
			sql = "SELECT EXISTS (SELECT 1 FROM compras WHERE idCompra = " + id + ") AS existe";
			PreparedStatement pstmt;
			try {
				pstmt = conn.prepareStatement(sql);
				ResultSet resultado = pstmt.executeQuery();
				if (resultado.next()) {
					salida = resultado.getInt("existe");
				}
			} catch (SQLException e) {
				System.err.println("ERROR: error a la hora de comprobar la existencia de una compra (0x19)");
			}
		}
		
		case 3 -> {
			
			// Comprueba la existencia de un ID en la tabla games
			sql = "SELECT EXISTS (SELECT 1 FROM games WHERE idGames = " + id + ") AS existe";
			PreparedStatement pstmt;
			try {
				pstmt = conn.prepareStatement(sql);
				ResultSet resultado = pstmt.executeQuery();
				if (resultado.next()) {
					salida = resultado.getInt("existe");
				}
			} catch (SQLException e) {
				System.err.println("ERROR: error a la hora de comprobar la existencia de un juego (0x23)");
			}
			
		}
		
		}
		
		// Si salida da 0 (no existe), res sera 1
		if (salida == 0) {
			res = 1;
		}
		
		return res;
		
	}
	
	/**
	 * Metodo que comprueba la existencia de un ID (solo tabla Compras)
	 * @param idCompra id de la compra
	 * @param idPlayer id del jugadoor
	 * @param idGames id del juego
	 * @return Existencia del ID
	 * -1 = no existe idPlayer y/o idGames
	 * 0 = idCompra ya existe
	 * 1 = idCompra no existe
	 */
	private static int noExisteID(int idCompra, int idPlayer, int idGames) {
		
		// Salida que da el ResultSet
		int salida = 0;
		
		// Resultado de la operacion a devolver
		int res = 0;
		
		// Comprueba que no existe la ID de compra
		salida = noExisteID(idCompra, 2);
		
		if (salida == 1) {
			
			// Comprueba la existencia de la ID del jugador en caso de que la ID de compra no exista
			salida = noExisteID(idPlayer, 1);
			
			if (salida == 0) {
				
				// Comprueba la existencia de la ID del juego en caso de que la ID de juego exista
				salida = noExisteID(idGames, 3);
				
				// Si los IDs de juego y compra existen, res sera 1 (idCompra no existe). De lo contrario, sera -1
				if (salida == 0) {
					res = 1;
				} else {
					res = -1;
				}
				
			} else {
				res = -1;
			}
			// En caso de no llegar a la comprobacion de las IDs de las otras dos tablas, res sera 0.
		} else {
			res = 0;
		}
			
		// Devuelve el resultado de la operacion
		return res;
	
	}
	
	/**
	 * Metodo que lista los datos de una tabla
	 * @param opc Tabla escogida
	 */
	public static void listar(int opc) {
		
		// Opcion secundaria seleccionada
		int opc2 = 1;
		
		try {
			
			// Pide al usuario una opcion de listado a escoger
			System.out.println("¿Desea listar la tabla entera o listarla por parametros?");
			System.out.println("1. Listar la tabla entera (predeterminado)");
			System.out.println("2. Listar la tabla por parametros");
			opc2 = Main.sc.nextInt();
			
		} catch (InputMismatchException e) {
			
			System.err.println("ERROR: respuesta invalida. (0x1c)");
			Main.sc.nextLine();
			opc2 = 1;
			
		} finally {
			switch (opc2) {
			
			default -> {
				// Case 1 / default: listado completo
				listadoCompleto(opc);
			}
			
			case 2 -> {
				// Case 2 / default: listado parcial
				listadoParcial(opc);
			}
			
			}
		}
		
	}
	
	/**
	 * Metodo que lista todos los datos
	 * @param opc tabla escogida
	 */
	public static void listadoCompleto(int opc) {
		
		// Comando SQL a ejecutar
		String sql = "";
		
		switch (opc) {
		
		case 1 -> {
			
			// Lista todo el contenido de la tabla player
			sql = "SELECT * from player";
			listarJugadores(sql);
		}
		
		case 2 -> {
			
			// Lista todo el contenido de la tabla compras
			sql = "SELECT * from compras";
			listarCompras(sql);
		}
		
		case 3 -> {
			
			// Lista todo el contenido de la tabla games
			sql = "SELECT * from games";
			listarJuegos(sql);
		}
		
		}
	}
	
	/**
	 * Metodo que lista el contenido de una tabla a base de parametros
	 * @param opc tabla escogida
	 */
	public static void listadoParcial(int opc) {
		
		// Comando SQL a ejecutar
		String sql = "";
		
		// Comando inicial
		String comandoInicial = "";
		
		// Variables de objetos
		ClsPlayer player = new ClsPlayer();
		ClsGames game = new ClsGames();
		ClsCompras compra = new ClsCompras();
		
		try {
			
			// 1: Jugador, 2: Compra, 3: Juego
			switch (opc) {
			
			case 1 -> {
				
				// Pide los parametros del jugador
				player = pedirJugador(false, false);
				
				// Comando SQL
				sql = "SELECT * FROM player WHERE nick LIKE '%" + player.getNick() + "%' AND password LIKE '%" + player.getPassword() + "%' AND email LIKE '%" + player.getEmail() + "%'";
				
				// Lista los jugadores dependiendo de los parametros obtenidos
				listarJugadores(sql);
				
			}
			
			case 2 -> {
				
				// Pide los parametros de la compra
				System.out.println("NOTA: Si quieres saltarte las IDs, inserte un 0.");
				compra = pedirCompra(false, false);
				
				// Comando SQL inicial
				comandoInicial = "SELECT * FROM compras WHERE cosa LIKE '%" + compra.getCosa() + "%'";
				
				// Realiza las variaciones para los parametros de la compra
				sql = pedirVariacionesParametrosCompras(comandoInicial, compra, false);
				
				// Lista las compras dependiendo de los parametros obtenidos
				listarCompras(sql);
			}
			
			case 3 -> {
				
				// Pide los parametros del juego
				game = pedirJuego(false, false);
				
				// Comando SQL inicial
				comandoInicial = "SELECT * FROM games WHERE nombre LIKE '%" + game.getNombre() + "%'";
				
				// Realiza las variaciones para los parametros del juego
				sql = pedirVariacionesParametrosJuego(comandoInicial, game, false);
				
				// Lista los juegos dependiendo de los parametros obtenidos
				listarJuegos(sql);
				
			}
			
			}
			
		} catch (InputMismatchException e) {
			System.err.println("ERROR: dato invalido (0x29)");
		}
		
	}
	
	/**
	 * Metodo que se conecta a la BD y lista los jugadores
	 * @param sql comando SQL a ejecutar
	 */
	private static void listarJugadores(String sql) {
		PreparedStatement pstmt;
		try {
			pstmt = conn.prepareStatement(sql);
			ResultSet resultado = pstmt.executeQuery();
			while(resultado.next()){
				System.out.println("----------------------------------------------");
				System.out.println("ID: " + resultado.getInt("idPlayer"));
				System.out.println("Nombre: " + resultado.getString("nick"));
				System.out.println("Contraseña: " + resultado.getString("password"));
				System.out.println("Email: " + resultado.getString("email"));
				System.out.println("----------------------------------------------");
			}
		} catch (SQLException e) {
			System.err.println("ERROR: error a la hora de mostrar datos (0x16)");
		}
	}
	
	/**
	 * Metodo que se conecta a la BD y lista las compras
	 * @param sql comando SQL a ejecutar
	 */
	private static void listarCompras(String sql) {
		PreparedStatement pstmt;
		try {
			pstmt = conn.prepareStatement(sql);
			ResultSet resultado = pstmt.executeQuery();
			while(resultado.next()){
				System.out.println("----------------------------------------------");
				System.out.println("ID de compra: " + resultado.getInt("idCompra"));
				System.out.println("ID de jugador: " + resultado.getInt("idPlayer"));
				System.out.println("ID de juego: " + resultado.getInt("idGames"));
				System.out.println("Datos: " + resultado.getString("cosa"));
				System.out.println("Precio: " + resultado.getDouble("precio"));
				System.out.println("Fecha de compra: " + resultado.getString("FechaCompra"));
				System.out.println("----------------------------------------------");
			}
		} catch (SQLException e) {
			System.err.println("ERROR: error a la hora de mostrar datos (0x16)");
		}
	}
	
	/**
	 * Metodo que se conecta a la BD y lista los juegos
	 * @param sql comando SQL a ejecutar
	 */
	private static void listarJuegos(String sql) {
		PreparedStatement pstmt;
		try {
			pstmt = conn.prepareStatement(sql);
			ResultSet resultado = pstmt.executeQuery();
			while(resultado.next()){
				System.out.println("----------------------------------------------");
				System.out.println("ID: " + resultado.getInt("idGames"));
				System.out.println("Nombre: " + resultado.getString("nombre"));
				System.out.println("Tiempo jugado: " + resultado.getString("tiempoJugado"));
				System.out.println("----------------------------------------------");
			}
		} catch (SQLException e) {
			System.err.println("ERROR: error a la hora de mostrar datos (0x16)");
		}
	}
	
	/**
	 * Metodo que pide al usuario la variacion para el ID del jugador a eliminar
	 * 
	 * @param res codigo SQL inicial
	 * @param player jugador escogido
	 * @return Codigo SQL final
	 */
	private static String pedirVariacionIdJugador(String res, ClsPlayer player) {
		
		// Opcion escogida por el usuario
		int opc;
		
		// Pide al usuario si el ID de juego deberia ser mayor o menor
		System.out.println("ID de juego: ¿Mayor o menor?");
		opc = pedirOpcPorParametros(1);
		
		// Switch: 1 - menor, 2 - mayor
		switch (opc) {
		
		case 2 -> {
			res += " AND idPlayer > " + player.getIdPlayer();
		}
		
		default -> {
			res += " AND idPlayer < " + player.getIdPlayer();
		}
		
		}
		
		// Devuelve el comando SQL
		return res;
		
	}
	
	/**
	 * Metodo que pide al usuario los comparadores para numeros y fechas de una compra
	 * @param res codigo SQL inicial
	 * @param compra compra escogida
	 * @return Codigo SQL final
	 */
	private static String pedirVariacionesParametrosCompras(String res, ClsCompras compra, boolean hayID) {
		
		// Opcion escogida por el usuario
		int opc;
		
		// Si hayID es true, pide al usuario si el ID de compra deberia ser mayor o menor
		if (hayID || compra.getIdCompra() >= 1) {
			
			System.out.println("ID de compra: ¿Mayor o menor?");
			opc = pedirOpcPorParametros(1);
			
			// Switch: 1 - menor, 2 - mayor
			switch (opc) {
			
			case 2 -> {
				res += " AND idCompra < " + compra.getIdCompra();
			}
			
			default -> {
				res += " AND idCompra > " + compra.getIdCompra();
			}
			
			}
			
		}
		
		// Pide al usuario si el ID de jugador deberia ser mayor o menor
		if (compra.getIdPlayer() >= 1) {
			System.out.println("ID de jugador: ¿Mayor o menor?");
			opc = pedirOpcPorParametros(1);
			
			// Switch: 1 - menor, 2 - mayor
			switch (opc) {
			
			case 2 -> {
				res += " AND idPlayer > " + compra.getIdPlayer();
			}
			
			default -> {
				res += " AND idPlayer < " + compra.getIdPlayer();
			}
			
			}
		} else {
			System.out.println("ID de jugador saltado (es 0)");
		}
		
		// Pide al usuario si el ID de juego deberia ser mayor o menor
		if (compra.getIdGames() >= 1) {
			System.out.println("ID de juego: ¿Mayor o menor?");
			opc = pedirOpcPorParametros(1);
			
			// Switch: 1 - menor, 2 - mayor
			switch (opc) {
			
			case 2 -> {
				res += " AND idGames > " + compra.getIdGames();
			}
			
			default -> {
				res += " AND idGames < " + compra.getIdGames();
			}
			
			}
		} else {
			System.out.println("ID de juego saltado (es 0)");
		}
		
		// Pide al usuario si el precio deberia ser mayor o menor
		System.out.println("Precio: ¿Mayor o menor?");
		opc = pedirOpcPorParametros(1);
		
		// Switch: 1 - menor, 2 - mayor
		switch (opc) {
		
		case 2 -> {
			res += " AND precio < " + compra.getPrecio();
		}
		
		default -> {
			res += " AND precio > " + compra.getPrecio();
		}
		
		}
		
		// Pide al usuario si la fecha de compra deberia ser mayor o menor
		System.out.println("Fecha de compra: ¿Antes o despues?");
		opc = pedirOpcPorParametros(2);
		
		// Switch: 1 - menor, 2 - mayor
		switch (opc) {
		
		case 2 -> {
			res += " AND FechaCompra > " + compra.getDate();
		}
		
		default -> {
			res += " AND FechaCompra < " + compra.getDate();
		}
		
		}
		
		// Devuelve la frase SQL final
		return res;
	}
	
	/**
	 * Metodo que pide al usuario los comparadores para numeros y fechas de un juego
	 * @param sql codigo SQL inicial
	 * @param game juego escogido
	 * @return Codigo SQL final
	 */
	private static String pedirVariacionesParametrosJuego(String res, ClsGames game, boolean hayID) {
				
		// Opcion escogida por el usuario
		int opc;
		
		// Si hayID es true, pide al usuario si el ID de compra deberia ser mayor o menor
		if (hayID || game.getIdGames() >= 1) {
			
			System.out.println("ID de juego: ¿Mayor o menor?");
			opc = pedirOpcPorParametros(1);
			
			// Switch: 1 - menor, 2 - mayor
			switch (opc) {
			
			case 2 -> {
				res += " AND idGames > " + game.getIdGames();
			}
			
			default -> {
				res += " AND idGames < " + game.getIdGames();
			}
			
			}
			
		}
		
		// Pide al usuario si el tiempo jugado deberia ser mayor o menor
		System.out.println("Tiempo jugado: ¿Antes o despues?");
		opc = pedirOpcPorParametros(2);
		
		// Switch: 1 - menor, 2 - mayor
		switch (opc) {
		
		case 2 -> {
			res += " AND tiempoJugado > '" + game.getTiempoJugado() + "'";
		}
		
		default -> {
			res += " AND tiempoJugado < '" + game.getTiempoJugado() + "'";
		}
		
		}
		
		// Devuelve la frase SQL final
		return res;
	}
	
	/**
	 * Metodo que pide la opcion de listado / borrado por parametros
	 * @param opc opcion que depende unicamente en como se muestre el texto por pantalla (numero o fecha)
	 * @return opcion seleccionada por el usuario
	 */
	private static int pedirOpcPorParametros(int opc) {
		
		// Opcion seleccionada
		int res = 0;
		
		// Muestra el texto dependiendo de la opcion seleccionada
		switch (opc) {
		
		// Case 1: numero
		case 1 -> {
			System.out.println("1. Mayor (predeterminado)");
			System.out.println("2. Menor");
		}
		
		// Case 2: fecha
		case 2 -> {
			System.out.println("1. Antes (predeterminado)");
			System.out.println("2. Despues");
		}
		
		}
		
		// Pide al usuario la opcion
		try {
			res = Main.sc.nextInt();
		} catch (InputMismatchException e) {
			System.err.println("ERROR: opcion de select interna invalida. Se escogera la opcion 1 (0x2b)");
			res = 1;
		}
		
		// Devuelve la opcion seleccionada
		return res;
		
	}
	
	/**
	 * Metodo que modifica un elemento
	 * @param opc Tabla escogida
	 * @return Resultado de la operacion
	 */
	public static boolean modificar(int opc) {
		
		// Resultado de la operacion a devolver
		boolean res = false;
		
		// Booleana que indica si hay datos incompletos introducidos por el usuario
		incompleto = false;
		
		// No-existencia de las tablas (rubrica explicada en la funcion existeID()
		int noExiste = 2;
		
		// Comando SQL a ejecutar
		String sql = "";
		
		// Respuesta del commit (S/N)
		String respuestaCommit = "";
		
		// Variables de objetos
		ClsPlayer player = new ClsPlayer();
		ClsGames game = new ClsGames();
		ClsCompras compra = new ClsCompras();
		
		try {
			java.sql.Statement stmt = conn.createStatement();
			switch (opc) {
			
			case 1 -> {
				// Pide un jugador y comprueba su existencia
				player = pedirJugador(true, false);
				noExiste = noExisteID(player.getIdPlayer(), 1);
				sql = "UPDATE player SET nick = '" + player.getNick() + "', password = '" + player.getPassword() + "', email = '" + player.getEmail() + "' WHERE idPlayer = " + player.getIdPlayer();
			}
			
			case 2 -> {
				// Pide una compra y comprueba su existencia
				compra = pedirCompra(true, false);
				noExiste = noExisteID(compra.getIdCompra(), compra.getIdPlayer(), compra.getIdGames());
				sql = "UPDATE compras SET idPlayer = '" + compra.getIdPlayer() + "', idGames = '" + compra.getIdGames() + "', cosa = '" + compra.getCosa() + "', precio = '" + compra.getPrecio() + "', FechaCompra = '" + compra.getDate() + "' WHERE idCompra = " + compra.getIdCompra();
			}
			
			case 3 -> {
				// Pide un juego y comprueba su existencia
				game = pedirJuego(true, false);
				noExiste = noExisteID(game.getIdGames(), 3);
				sql = "UPDATE games SET nombre = '" + game.getNombre() + "', tiempoJugado = '" + game.getTiempoJugado() + "' WHERE idGames = " + game.getIdGames();
			}
			
			}
			
			// Pregunta al usuario si esta seguro de realizar la operacion
			System.out.println("¿Estas seguro de realizar la operacion? (S/N)");
			respuestaCommit = Main.sc.nextLine().toUpperCase();
			
			if (respuestaCommit.equals("S")) {
				System.out.println("Modificando elemento...");
				if (!incompleto && noExiste == 0) {
					// Modifica el elemento en caso de que se autorize la operacion
					stmt.executeUpdate(sql);
					System.out.println("Los datos se han actualizado correctamente");
			        res = true;
				} else {
					
					// En caso de que la operacion no esste autorizada, muestra el error en cuestion
					switch (noExiste) {
					
					case 1 -> {
						System.err.println("ERROR: el ID insertado no existe en la DB (0x1d)");
					}
					
					case -1 -> {
						System.err.println("ERROR: el ID de jugador y/o el ID de juegos no existen en la DB. Crealos primero y vuelva a intentarlo. (0x15)");
					}
					
					}
					
				}
			} else {
				System.out.println("El usuario ha decidido cancelar la operacion.");
			}
	        
		} catch (SQLException e) {
			System.err.println("ERROR: error a la hora de actualizar el dato (0x1e)");
		}
		
		// Devuelve el resultado de la operacion
		return res;
		
	}
	
	/**
	 * Metodo que borra un objeto
	 * @param opc Tabla escogida
	 * @return Resultado de la operacion
	 */
	public static boolean borrar(int opc) {
				
		// Resultado de la operacion a mostrar
		boolean res = false;
		
		// Respuesta del commit (S/N)
		String respuestaCommit = "";
		
		// Comando SQL a ejecutar
		String sql = "";
		
		// Comando inicial (solo borrado por parametros)
		String comandoInicial = "";
		
		// Opciones secundarias y terciarias de borrado
		int opc2 = 0;
		int opc3 = 0;
		
		// No-existencia de las tablas (rubrica explicada en la funcion existeID()
		int noExiste = 2;
		
		// ID del objeto introducido por el usuario
		int idObjeto = 0;
		
		// Variables de objetos
		ClsPlayer playerx = new ClsPlayer();
		ClsGames game = new ClsGames();
		ClsCompras compra = new ClsCompras();
		
		try {
			
			java.sql.Statement stmt = conn.createStatement();
			switch (opc) {
			
			case 1 -> {
				
				// Pide la opcion secundaria de borrado
				opc2 = pedirOpcionBorrado();
				switch (opc2) {
				
				case 1 -> {
					
					// Borra la tabla player en caso de que no exista la tabla compras
					if (!compras) {
						
						// Comando SQL
						sql = "DROP TABLE player";
						
						// Procede a la eliminacion del elemento
						res = borradoFinalTablaUnica(sql, true, stmt, 0);
						
					} else {
						System.err.println("ERROR: dependencia de la tabla escogida en la FK de la tabla COMPRAS (0x20)");
					}
					
				}
				
				case 2 -> {
					
					// Pide la opcion terciaria de borrado
					opc3 = pedirOpcionBorradoTabla();
					switch (opc3) {
					
					case 1 -> {
						
						// Pide al usuario el ID del jugador a borrar
						idObjeto = pideIdBorrado();
						
						// Comprueba la existencia de la ID
						noExiste = noExisteID(idObjeto, 1);
						
						// Comando SQL
						sql = "DELETE FROM player WHERE idPlayer = " + idObjeto;
						
					}
					
					case 2 -> {
						
						// Pide el jugador
						playerx = pedirJugador(true, true);
						
						// Frase SQL inicial
						comandoInicial = "DELETE FROM player WHERE nick LIKE '%" + playerx.getNick() + "%' AND password LIKE '%" + playerx.getPassword() + "%' AND email LIKE '%" + playerx.getEmail() + "%'";
						
						// Pide al usuario la variacion del ID del jugador, pero solo si el ID es 1 o mayor
						if (playerx.getIdPlayer() >= 1) {
							sql = pedirVariacionIdJugador(comandoInicial, playerx);
						}
						
						// Comprueba la existencia de la ID del jugador
						noExiste = noExisteID(playerx.getIdPlayer(), 1);
						
					}
					
					}
					
					// Procede a la eliminacion del elemento
					res = borradoFinalTablaUnica(sql, false, stmt, noExiste);
					
				}
				
				}
			}
			
			case 2 -> {
				
				// Pide la opcion secundaria de borrado
				opc2 = pedirOpcionBorrado();
				switch (opc2) {
				
				case 1 -> {
					
					// Comando SQL
					sql = "DROP TABLE compras";
					
					// Procede a la eliminacion del elemento
					res = borradoFinalTablaUnica(sql, true, stmt, 0);
					
				}
				
				case 2 -> {
					
					// Pide la opcion terciaria de borrado
					opc3 = pedirOpcionBorradoTabla();
					switch (opc3) {
					
					case 1 -> {
						
						// Pide al usuario el ID de la compra a eliminar
						idObjeto = pideIdBorrado();
						
						// Comprueba la existencia de la ID
						noExiste = noExisteID(idObjeto, 2);
						
						// Comando SQL
						sql = "DELETE FROM compras WHERE idCompra = " + idObjeto;
						
					}
					
					case 2 -> {
												
						// Pide la compra
						compra = pedirCompra(true, true);
						
						// Comando SQL inicial de la compra
						comandoInicial = "DELETE FROM compras WHERE cosa LIKE '%" + compra.getCosa() + "%'";
						
						// Pide al usuario la variacion de algunos parametros de la compra
						sql = pedirVariacionesParametrosCompras(comandoInicial, compra, true);
						
						// Comprueba la existencia de la ID de la compra
						noExiste = noExisteID(compra.getIdCompra(), 2);
						
					}
					
					}
					
					// Procede a la eliminacion del elemento
					res = borradoFinalTablaUnica(sql, false, stmt, noExiste);
					
				}
				
				}
			}
			
			case 3 -> {
				
				// Pide la opcion secundaria de borrado
				opc2 = pedirOpcionBorrado();
				switch (opc2) {
				
				case 1 -> {
					
					// Si la tabla compras no existe, procede con el dropeo
					if (!compras) {
						
						// Comando SQL
						sql = "DROP TABLE games";
						
						// Procede a la eliminacion del elemento
						res = borradoFinalTablaUnica(sql, true, stmt, 0);
						
					} else {
						System.err.println("ERROR: dependencia de la tabla escogida en la FK de la tabla COMPRAS (0x20)");
					}
					
				}
				
				case 2 -> {
					
					// Pide la opcion terciaria de borrado
					opc3 = pedirOpcionBorradoTabla();
					switch (opc3) {
					
					case 1 -> {
						
						// Pide al usuario el ID del juego a eliminar
						idObjeto = pideIdBorrado();
						
						// Comprueba la existencia de la ID
						noExiste = noExisteID(idObjeto, 3);
						
						// Comando SQL
						sql = "DELETE FROM games WHERE idGames = " + idObjeto;

					}
					
					case 2 -> {
						
						// Pide el juego
						game = pedirJuego(true, true);
						
						// Frase SQL inicial
						comandoInicial = "DELETE FROM games WHERE nombre LIKE '%" + game.getNombre() + "%'";
						
						// Pide al usuario la variacion de algunos parametros del juego
						sql = pedirVariacionesParametrosJuego(comandoInicial, game, true);
						
						// Comprueba la existencia de la ID del juego
						noExiste = noExisteID(game.getIdGames(), 3);
						
					}
					
					}
					
					// Procede a la eliminacion del elemento
					res = borradoFinalTablaUnica(sql, false, stmt, noExiste);
					
				}
				
				}
			}
			
			case 4 -> {
				
				// Dropea TODAS las tablas (cuando digo TODAS, es que TODAS)
				Main.sc.nextLine();
				System.out.println("¿Estas seguro de realizar la operacion? (S/N)");
				respuestaCommit = Main.sc.nextLine().toUpperCase();
				
				if (respuestaCommit.equals("S")) {
					if (compras) {
						sql = "DROP TABLE compras";
			            stmt.executeUpdate(sql);
					}
					if (player) {
						sql = "DROP TABLE player";
			            stmt.executeUpdate(sql);
					}
					if (games) {
						sql = "DROP TABLE games";
			            stmt.executeUpdate(sql);
					}
		            res = true;
				} else {
					System.out.println("Operacion cancelada por el usuario.");
				}

			}
			
			}
		} catch (SQLException e) {
			System.err.println("ERROR: error a la hora de eliminar la tabla (0x21)");
		}
		
		// Devuelve el resultado de la operacion
		return res;
		
	}
	
	/**
	 * Metodo que realiza el borrado de una fila / tabla
	 * @param sql comando SQL
	 * @param total Drop / Delete
	 * @param stmt ResultStatement
	 * @param noExiste existencia del objeto
	 * @return resultado de la operacion
	 */
	private static boolean borradoFinalTablaUnica(String sql, boolean total, java.sql.Statement stmt, int noExiste) {
		
		// Booleana que indica el resultado de la operacion
		boolean res = false;
		
		// Respuesta del commit
		String respuestaCommit;
		
		// Pide al usuario la confirmacion de la operacion
		Main.sc.nextLine();
		System.out.println("¿Estas seguro de realizar la operacion? (S/N)");
		respuestaCommit = Main.sc.nextLine().toUpperCase();
		
		try {
			
			// Borra el jugador
			if (respuestaCommit.equals("S")) {
				if (total) {
					stmt.executeUpdate(sql);
		            res = true;
				} else {
					if (noExiste == 0) {
						stmt.executeUpdate(sql);
			            res = true;
					} else {
						System.err.println("ERROR: el elemento no ha sido encontrado. Borrado anulado (0x25)");
					}
				}
			} else {
				System.out.println("Operacion cancelada por el usuario.");
			}
			
		} catch (SQLException e) {
			System.err.println("ERROR: error a la hora de eliminar la tabla (0x21)");
		}
		
		return res;
		
	}
	
	/**
	 * Pide al usuario el ID de un objeto a eliminar
	 * @return ID de borrado
	 */
	private static int pideIdBorrado() {
		int idObjeto = 0;
		try {
			System.out.println("Inserta el ID");
			idObjeto = Main.sc.nextInt();
		} catch (InputMismatchException e) {
			System.err.println("ERROR: ID invalido (0x28)");
		} finally {
			Main.sc.nextLine();
		}
		return idObjeto;
	}
	
	/**
	 * Metodo que devuelve la opcion de borrado seleccionada
	 * @return Opcion seleccionada
	 */
	private static int pedirOpcionBorrado() {
		int opc = 0;
		try {
			System.out.println("¿Deseas borrar toda la tabla o una serie de datos concretos?");
			System.out.println("1. Todas la tablas (predeterminado)");
			System.out.println("2. Una tabla en concreto / varias tablas filtradas");
			opc = Main.sc.nextInt();
			if (opc != 1 && opc != 2) {
				opc = 1;
			}
		} catch (InputMismatchException e) {
			System.err.println("ERROR: opcion invalida. Se escogera la opcion 1 (0x29)");
			opc = 1;
		}
		return opc;
	}
	
	/**
	 * Metodo que devuelve la opcion seleccionado del borrado de tablas
	 * @return Opcion seleccionada
	 */
	private static int pedirOpcionBorradoTabla() {
		int opc = 0;
		try {
			System.out.println("¿Deseas borrar todos los datos de una fila o varias filas segun un filtro?");
			System.out.println("1. Toda la fila (predeterminado)");
			System.out.println("2. Varias filas segun filtrado");
			opc = Main.sc.nextInt();
			if (opc != 1 && opc != 2) {
				opc = 1;
			}
		} catch (InputMismatchException e) {
			System.err.println("ERROR: opcion invalida. Se escogera la opcion 1 (0x27)");
			opc = 1;
		}
		return opc;
	}
	
}
