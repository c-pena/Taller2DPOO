package modelo;

public class ProductoMenu {
	private String nombre;
	private int precioBase;
	
	public ProductoMenu (String nombre, int precioBase) {
		this.nombre = nombre;
		this.precioBase = precioBase;
	}
	
	public String getNombre () {
		return nombre;
	}
	
	public int getPrecio () {
		return precioBase;
	}
	
	public String generarTextoFactura () {
		String texto = String.format("|%-40s%10d|", nombre, precioBase);
	    texto = texto.replace(' ', '_');
	    return texto;
	}
}
