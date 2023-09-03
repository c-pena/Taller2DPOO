package modelo;

public class Ingrediente {
	private String nombre;
	private int costoAdicional;
	private int calorias;
	
	public Ingrediente(String nombre, int costoAdicional, int calorias) {
		this.nombre = nombre;
		this.costoAdicional = costoAdicional;
		this.calorias = calorias;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public int getCostoAdicional() {
		return costoAdicional;
	}
	
	public int getCalorias() {
		return calorias;
	}
}
