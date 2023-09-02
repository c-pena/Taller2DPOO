package modelo;

import java.util.ArrayList;
import procesamiento.Producto;

public class Combo implements Producto {
	private double descuento;
	private String nombreCombo;
	private ArrayList<String> nombresProductos = new ArrayList<>();
	private int precioCombo;
	
	public Combo(String nombre, double descuento) {
		this.descuento = descuento;
		this.nombreCombo = nombre;
	}
	
	public void agregarItemACombo(Producto itemCombo) {
		String nombreItem = itemCombo.getNombre();
		this.nombresProductos.add(nombreItem);
		int precioItem = itemCombo.getPrecio();
		this.precioCombo += precioItem;
	}
	
	public int getPrecio() {
		return (int) (precioCombo * descuento);
	}
	
	public String getNombre() {
		return nombreCombo;
	}
	
	public String generarTextoFactura() {
		String texto1 = String.format("|%-40s%10d|\n", nombreCombo, getPrecio());
		String texto2 = String.format("|    *%-45s|\n", nombresProductos.get(0));
		String texto3 = String.format("|    *%-45s|\n", nombresProductos.get(1));
		String texto4 = String.format("|    *%-45s|\n", nombresProductos.get(2));
		String completa = texto1 + texto2 + texto3 + texto4;
		return completa;
	}
}
