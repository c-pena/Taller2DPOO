package modelo;

import java.util.ArrayList;

import procesamiento.Producto;

public class ProductoAjustado implements Producto {
	private String nombre;
	private int precioBase;
	private ArrayList<String> ajustesIngredientesNombre = new ArrayList<>();
	private ArrayList<String> ajustesIngredientesPrecio = new ArrayList<>();
	
	public ProductoAjustado(ProductoMenu productoBase) {
		this.nombre = productoBase.getNombre();
		this.precioBase = productoBase.getPrecio();
	}

	public int getPrecio() {
		return precioBase;
	}

	public String getNombre() {
		return nombre;
	}
	
	public void agregarIngrediente(Ingrediente ingrediente) {
		String nombreIngrediente = ingrediente.getNombre();
		this.ajustesIngredientesNombre.add("+" + nombreIngrediente);
		int precioIngrediente = ingrediente.getCostoAdicional();
		this.precioBase += precioIngrediente;
		this.ajustesIngredientesPrecio.add("+" + Integer.toString(precioIngrediente));
	}
	
	public void eliminarIngrediente(Ingrediente ingrediente) {
		String nombreIngrediente = ingrediente.getNombre();
		this.ajustesIngredientesNombre.add("-" + nombreIngrediente);
		this.ajustesIngredientesPrecio.add("-0");
	}
	
	public String generarTextoFactura() {
		String texto1 = String.format("|%-40s%10d|\n", nombre, precioBase);
		StringBuilder textoAjustes = new StringBuilder(String.format("|     %-45s|\n", "LISTADO DE AJUSTES:"));
		for (int i = 0; i < ajustesIngredientesNombre.size(); i++) {
			String nombre = ajustesIngredientesNombre.get(i);
			String ajustePrecio = ajustesIngredientesPrecio.get(i);
			String textoAjuste = String.format("|    %-30s%10d     |\n", nombre, ajustePrecio);
			textoAjustes.append(textoAjuste);
		}
		String ajusteFinal = textoAjustes.toString();
		String completa = texto1 + ajusteFinal;
		return completa;
	}
}
