package ent;

public class ClsGames {

	private int idGames;
	private String nombre;
	private String tiempoJugado;
	
	public ClsGames() {
		
	}
	
	public ClsGames(String nombre, String tiempoJugado) {
		if (nombre != null && !nombre.equals("")) {
			this.nombre = nombre;
		}
		if (tiempoJugado != null && !tiempoJugado.equals("")) {
			boolean res = revisarTiempo(tiempoJugado);
			if (res) {
				this.tiempoJugado = tiempoJugado;
			}
		}
	}
	
	public int getIdGames() {
		return idGames;
	}

	public void setIdGames(int idGames) {
		if (idGames >= 1) {
			this.idGames = idGames;
		}
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		if (nombre != null && !nombre.equals("")) {
			this.nombre = nombre;
		}
	}

	public String getTiempoJugado() {
		return tiempoJugado;
	}

	public void setTiempoJugado(String tiempoJugado) {
		if (tiempoJugado != null && !tiempoJugado.equals("")) {
			boolean res = revisarTiempo(tiempoJugado);
			if (res) {
				this.tiempoJugado = tiempoJugado;
			}
		}
	}

	private static boolean revisarTiempo(String tiempoJugado) {
		// Formato: HH:MM:SS
		boolean res = false;
		String[] info = tiempoJugado.split(":");
		if (info.length == 3) {
			int horas = Integer.parseInt(info[0]);
			int minutos = Integer.parseInt(info[1]);
			int segundos = Integer.parseInt(info[2]);
			if (horas >= 0 && horas <= 23 && minutos >= 0 && minutos <= 59 && segundos >= 0 && segundos <= 59) {
				res = true;
			}
		}
		return res;
	}
	
}
