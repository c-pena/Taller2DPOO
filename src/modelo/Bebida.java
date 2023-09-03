package modelo;

import procesamiento.Producto;

public class Bebida implements Producto{
	private String nombre;
	private int precioBase;
	private int calorias;
	
	public Bebida(String nombre, int precioBase, int calorias) {
		this.nombre = nombre;
		this.precioBase = precioBase;
		this.calorias = calorias;
	}

	public int getPrecio() {
		return precioBase;
	}

	public String getNombre() {
		return nombre;
	}
	
	public int getCalorias() {
		return calorias;
	}

	public String generarTextoFactura() {
		return String.format("|%-40s%10d|\n", nombre, precioBase);
	}

}
