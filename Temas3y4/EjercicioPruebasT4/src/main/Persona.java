package main;

public class Persona {

	private int id;
	private String nombre;
	private String apellido;
	private int edad;
	
	public Persona() {
		
	}
	
	public Persona(int id, String nombre, String apellidos, int edad) {
		if (id > 0) {
			this.id = id;
		}
		if (nombre != null && !nombre.equals("")) {
			this.nombre = nombre;
		}
		if (apellidos != null && !apellidos.equals("")) {
			this.apellido = apellidos;
		}
		if (edad >= 0) {
			this.edad = edad;
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		if (id > 0) {
			this.id = id;
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

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		if (apellido != null && !apellido.equals("")) {
			this.apellido = apellido;
		}
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		if (edad >= 0) {
			this.edad = edad;
		}
	}
	
}
