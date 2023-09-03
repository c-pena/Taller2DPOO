package modelo;

import procesamiento.Producto;

public class ProductoMenu implements Producto {
	private String nombre;
	private int precioBase;
	private int calorias;
	
	public ProductoMenu(String nombre, int precioBase, int calorias) {
		this.nombre = nombre;
		this.precioBase = precioBase;
		this.calorias = calorias;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public int getPrecio() {
		return precioBase;
	}
	
	public String generarTextoFactura() {
	    return String.format("|%-40s%10d|\n", nombre, precioBase);
	}

	public int getCalorias() {
		return calorias;
	}
}
