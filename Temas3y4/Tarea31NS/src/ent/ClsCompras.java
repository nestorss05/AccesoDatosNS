package ent;

public class ClsCompras {

	private int idCompra;
	private int idPlayer;
	private int idGames;
	private String cosa;
	private float precio;
	private String date;
	
	public ClsCompras() {
		
	}
	
	public ClsCompras(int idCompra, int idPlayer, int idGames, String cosa, float precio, String date) {
		if (idCompra >= 0) {
			this.idCompra = idCompra;
		}
		if (idPlayer >= 0) {
			this.idPlayer = idPlayer;
		}
		if (idGames >= 0) {
			this.idGames = idGames;
		}
		if (cosa != null && !cosa.equals("")) {
			this.cosa = cosa;
		}
		if (precio >= 0f) {
			this.precio = precio;
		}
		if (date != null && !date.equals("")) {
			if (revisarFecha(date)) {
				this.date = date;
			}
		}
	}
	
	public int getIdCompra() {
		return idCompra;
	}

	public void setIdCompra(int idCompra) {
		if (idCompra >= 0) {
			this.idCompra = idCompra;
		}
	}

	public int getIdPlayer() {
		return idPlayer;
	}

	public void setIdPlayer(int idPlayer) {
		if (idPlayer >= 0) {
			this.idPlayer = idPlayer;
		}
	}

	public int getIdGames() {
		return idGames;
	}

	public void setIdGames(int idGames) {
		if (idGames >= 0) {
			this.idGames = idGames;
		}
	}

	public String getCosa() {
		return cosa;
	}

	public void setCosa(String cosa) {
		if (cosa != null && !cosa.equals("")) {
			this.cosa = cosa;
		}
	}

	public float getPrecio() {
		return precio;
	}

	public void setPrecio(float precio) {
		if (precio >= 0f) {
			this.precio = precio;
		}
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		if (date != null && !date.equals("")) {
			if (revisarFecha(date)) {
				this.date = date;
			}
		}
	}

	private static boolean revisarFecha(String date) {
		// Formato: AAAA-MM-DD
		boolean res = false;
		String[] info = date.split("-");
		if (info.length == 3) {
			int año = Integer.parseInt(info[0]);
			int mes = Integer.parseInt(info[1]);
			int dia = Integer.parseInt(info[2]);
			if (año >= 1901 && mes >= 1 && mes <= 12 && dia >= 1) {
				int maxDia = 0;
				switch (dia) {
				
				case 1, 3, 5, 7, 8, 10, 12 -> {
					maxDia = 31;
				}
				
				case 4, 6, 9, 11 -> {
					maxDia = 30;
				}
				
				case 2 -> {
					if (año % 4 == 0) {
						maxDia = 29;
					} else {
						maxDia = 28;
					}
				}
				
				}
				
				if (dia <= maxDia) {
					res = true;
				}
			}
		}
		return res;
	}
	
}