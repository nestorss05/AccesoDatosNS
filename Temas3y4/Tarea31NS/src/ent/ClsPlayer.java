package ent;

public class ClsPlayer {

	private int idPlayer;
	private String nick;
	private String password;
	private String email;
	
	public ClsPlayer() {
		
	}
	
	public ClsPlayer(int idPlayer, String nick, String password, String email) {
		if (idPlayer > 0) {
			this.idPlayer = idPlayer;
		}
		if (nick != null && !nick.equals("")) {
			this.nick = nick;
		}
		if (password != null && !password.equals("")) {
			this.password = password;
		}
		if (email != null && !email.equals("")) {
			this.email = email;
		}
	}

	public int getIdPlayer() {
		return idPlayer;
	}

	public void setIdPlayer(int idPlayer) {
		if (idPlayer >= 1) {
			this.idPlayer = idPlayer;
		}
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		if (nick != null && !nick.equals("")) {
			this.nick = nick;
		}
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		if (password != null && !password.equals("")) {
			this.password = password;
		}
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		if (email != null && !email.equals("")) {
			this.email = email;
		}
	}
	
}