package procesamiento;

public interface Producto {
	public int getPrecio();
	public int getCalorias();
	public String getNombre();
	public String generarTextoFactura();
}
