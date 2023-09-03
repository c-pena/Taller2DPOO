package procesamiento;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Pedido {
	
	private static int numeroPedidos = 0;
	private int idPedido;
	private String nombreCliente;
	private String direccionCliente;
	private ArrayList<Producto> itemsPedido = new ArrayList<>();
	
	public Pedido(String nombreCliente, String direccionCliente) {
		this.nombreCliente = nombreCliente;
		this.direccionCliente = direccionCliente;
		this.idPedido = numeroPedidos;
		numeroPedidos++;
	}
	
	public int getIdPedido() {
		return idPedido;
	}
	
	public void agregarProducto(Producto nuevoItem) {
		itemsPedido.add(nuevoItem);
	}
	
	private int getPrecioNetoPedido() {
		int precioNeto = 0;
		for (int i = 0; i < itemsPedido.size(); i++) {
			Producto item = itemsPedido.get(i);
			precioNeto += item.getPrecio();
		}
		return precioNeto;
	}
	
	private int getPrecioIVAPedido() {
		int precioNeto = getPrecioNetoPedido();
		int precioIVA = (int) (precioNeto * 0.19);
		return precioIVA;
	}
	
	private int getPrecioTotalPedido() {
		int precioNeto = getPrecioNetoPedido();
		int precioIVA = getPrecioIVAPedido();
		return precioNeto + precioIVA;
	}
	
	public String generarTextoFactura() {
		StringBuilder textoFactura = new StringBuilder(String.format(new String(new char[52]).replace('\0', '_') + "\n"));
		textoFactura.append(String.format("|%-50s|\n", "=======BIENVENIDO A HAMBURGUESAS EL CORRAL========"));
		textoFactura.append(String.format("|     %-20s%25s|\n", "NOMBRE DEL CLIENTE:", nombreCliente));
		textoFactura.append(String.format("|     %-15s%30s|\n", "DIRECCION:", direccionCliente));
		textoFactura.append(String.format("|     %-20s%25s|\n", "NÚMERO DE FACTURA:", idPedido));
		textoFactura.append(new String(new char[52]).replace('\0', '_') + "\n");
		
		textoFactura.append(generarDetallesPedido());
		textoFactura.append(new String(new char[52]).replace('\0', '_') + "\n");
		
		textoFactura.append(String.format("|%-25s%25s|\n", "PRECIO NETO DEL PEDIDO:", getPrecioNetoPedido()));
		textoFactura.append(String.format("|%-25s%25s|\n", "PRECIO IVA DEL PEDIDO:", getPrecioIVAPedido()));
		textoFactura.append(String.format("|%-25s%25s|\n", "PRECIO TOTAL DEL PEDIDO:", getPrecioTotalPedido()));
		
		String facturaFinal = textoFactura.toString();
		
		return facturaFinal;
	}
	
	public String generarDetallesPedido() {
		StringBuilder textoDetalle = new StringBuilder(String.format("|%-50s|\n", "DETALLES DEL PEDIDO:"));
		for (int i = 0; i < itemsPedido.size(); i++) {
			Producto item = itemsPedido.get(i);
			textoDetalle.append(item.generarTextoFactura());
		}
		return textoDetalle.toString();
	}
	
	public void guardarFactura() {
		try (FileWriter writer = new FileWriter("Factura"+Integer.toString(idPedido)+".txt")) {
            writer.write(generarTextoFactura());
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
