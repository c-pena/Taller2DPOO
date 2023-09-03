package procesamiento;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import modelo.Bebida;
import modelo.Combo;
import modelo.Ingrediente;
import modelo.ProductoMenu;

public class Restaurante {
	
	public int estado = 0;
	private Pedido pedidoActual;
	private ArrayList<Pedido> historialPedidos = new ArrayList<>();
	Map<String, Ingrediente> ingredientes = new HashMap<>();
	Map<String, ProductoMenu> menu = new HashMap<>();
	Map<String, Combo> combos = new HashMap<>();
	Map<String, Bebida> bebidas = new HashMap<>();
	
	public int getEstado() {
		return estado;
	}
	
	public void cargarInformacionRestaurante(String archivoIngredientes, String archivoMenu, String archivoCombos, String archivoBebidas) {
		String workingDir = System.getProperty("user.dir");
        String filePath = workingDir + File.separator + "data" + File.separator;
        
		cargarIngredientes(filePath + archivoIngredientes);
		cargarMenu(filePath + archivoMenu);
		cargarBebidas(filePath + archivoBebidas);
		cargarCombos(filePath + archivoCombos);
		
		if (ingredientes.size() > 0 && menu.size() > 0 && combos.size() > 0 && bebidas.size() > 0) {
			estado++;
		}
	}
	
	private void cargarIngredientes(String archivoIngredientes) {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(archivoIngredientes), StandardCharsets.UTF_8))) {
			String linea = br.readLine();
			while(linea != null) {
				String[] partes = linea.split(";");
				String nombreProducto = partes[0];
				int precioProducto = Integer.parseInt(partes[1]);
				int caloriasProducto = Integer.parseInt(partes[2]);
				Ingrediente nuevo = new Ingrediente(nombreProducto, precioProducto, caloriasProducto);
				ingredientes.put(nombreProducto, nuevo);
				linea = br.readLine();
			}
			br.close();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void cargarMenu(String archivoMenu) {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(archivoMenu), StandardCharsets.UTF_8))) {
			String linea = br.readLine();
			while(linea != null) {
				String[] partes = linea.split(";");
				String nombreProducto = partes[0];
				int precioProducto = Integer.parseInt(partes[1]);
				int caloriasProducto = Integer.parseInt(partes[2]);
				ProductoMenu nuevo = new ProductoMenu(nombreProducto, precioProducto, caloriasProducto);
				menu.put(nombreProducto, nuevo);
				linea = br.readLine();
			}
			br.close();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void cargarCombos(String archivoCombos) {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(archivoCombos), StandardCharsets.UTF_8))) {
			String linea = br.readLine();
			while(linea != null) {
				String[] partes = linea.split(";");
				String nombre = partes[0];
				double descuento = 1.0 - (Integer.parseInt(partes[1].replace("%", "")) * 0.01);
				Combo nuevoCombo = new Combo(nombre, descuento);
				
				nuevoCombo.agregarItemACombo(menu.get(partes[2]));
				nuevoCombo.agregarItemACombo(menu.get(partes[3]));
				nuevoCombo.agregarItemACombo(bebidas.get(partes[4]));
				
				combos.put(nombre, nuevoCombo);
				linea = br.readLine();
			}
			br.close();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void cargarBebidas(String archivoBebidas) {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(archivoBebidas), StandardCharsets.UTF_8))) {
			String linea = br.readLine();
			while(linea != null) {
				String[] partes = linea.split(";");
				String nombreBebida = partes[0];
				int precioBebida = Integer.parseInt(partes[1]);
				int caloriasBebida = Integer.parseInt(partes[2]);
				Bebida nueva = new Bebida(nombreBebida, precioBebida, caloriasBebida);
				bebidas.put(nombreBebida, nueva);
				linea = br.readLine();
			}
			br.close();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getMenuCompleto() {
		String uLine = new String(new char[52]).replace('\0', '_') + "\n";
		
		StringBuilder combosBase = new StringBuilder(String.format("|%-50s|\n", "LISTADO DE COMBOS DISPONIBLES:"));
		for (Map.Entry<String, Combo> set : combos.entrySet()) {
			Combo combo = set.getValue();
			String factura = combo.generarTextoFactura();
			combosBase.append(factura);
		}
		String combosFinal = combosBase.toString();
		
		StringBuilder menuBase = new StringBuilder(String.format("|%-50s|\n", "LISTADO DE PRODUCTOS DISPONIBLES:"));
		for (Map.Entry<String, ProductoMenu> set : menu.entrySet()) {
			ProductoMenu menu = set.getValue();
			String factura = menu.generarTextoFactura();
			menuBase.append(factura);
		}
		String menuFinal = menuBase.toString();
		
		StringBuilder bebidasBase = new StringBuilder(String.format("|%-50s|\n", "LISTADO DE BEBIDAS DISPONIBLES:"));
		for (Map.Entry<String, Bebida> set : bebidas.entrySet()) {
			Bebida bebida = set.getValue();
			String factura = bebida.generarTextoFactura();
			bebidasBase.append(factura);
		}
		String bebidasFinal = bebidasBase.toString();
		
		String completa = uLine + combosFinal + uLine + menuFinal + uLine + bebidasFinal;
		return completa;
	}
	
	public ArrayList<Producto> getMenuBase() {
		ArrayList<Producto> itemsMenuBase = new ArrayList<>();
		for (Map.Entry<String, ProductoMenu> set : menu.entrySet()) {
			Producto productoMenu = set.getValue();
			itemsMenuBase.add(productoMenu);
		}
		return itemsMenuBase;
	}
	
	public ArrayList<Ingrediente> getIngredientes() {
		ArrayList<Ingrediente> itemsIngredientes = new ArrayList<>();
		for (Map.Entry<String, Ingrediente> set : ingredientes.entrySet()) {
			Ingrediente ingrediente = set.getValue();
			itemsIngredientes.add(ingrediente);
		}
		return itemsIngredientes;
	}
	
	public ArrayList<Combo> getCombos() {
		ArrayList<Combo> itemsCombos = new ArrayList<>();
		for (Map.Entry<String, Combo> set : combos.entrySet()) {
			Combo combo = set.getValue();
			itemsCombos.add(combo);
		}
		return itemsCombos;
	}
	
	public ArrayList<Bebida> getBebidas() {
		ArrayList<Bebida> itemsBebidas = new ArrayList<>();
		for (Map.Entry<String, Bebida> set : bebidas.entrySet()) {
			Bebida bebida = set.getValue();
			itemsBebidas.add(bebida);
		}
		return itemsBebidas;
	}
	
	public void iniciarPedido(String nombreCliente, String direccionCliente) {
		Pedido pedidoNuevo = new Pedido(nombreCliente, direccionCliente);
		pedidoActual = pedidoNuevo;
		estado++;
	}
	
	public void adicionarItemPedido(Producto producto) {
		pedidoActual.agregarProducto(producto);
		estado++;
	}
	
	public Pedido getPedidoEnCurso() {
		return pedidoActual;
	}
	
	public void cerrarYGuardarPedido() {
		System.out.println(pedidoActual.generarTextoFactura());
		pedidoActual.guardarFactura();
		ArrayList<Producto> itemsActual = pedidoActual.getItemsPedido();
		ArrayList<Integer> idPedidosIgual = new ArrayList<>();
		for (int i = 0; i < historialPedidos.size(); i++) {
			Pedido pedidoComparar = historialPedidos.get(i);
			ArrayList<Producto> itemsComparar = pedidoComparar.getItemsPedido();
			if (itemsActual.containsAll(itemsComparar) && itemsComparar.containsAll(itemsActual)) {
				idPedidosIgual.add(pedidoComparar.getIdPedido());
			}
		}
		
		if (!idPedidosIgual.isEmpty()) {
			System.out.println("Pedidos identicos encontrados (por ID):");
			for (int i = 0; i < idPedidosIgual.size(); i++) {
				int idPedido = idPedidosIgual.get(i);
				System.out.println("     * "+Integer.toString(idPedido));
			}
		}
		historialPedidos.add(pedidoActual);
		estado = 1;
	}
	
	public void getPedidoPorId(int id) {
		boolean encontrado = false;
		for (int i = 0; i < historialPedidos.size(); i++) {
			Pedido pedido = historialPedidos.get(i);
			if (pedido.getIdPedido() == id) {
				System.out.println(pedido.generarTextoFactura());
			}
		}
		if (!encontrado) {
			System.out.println("El pedido no fue encontrado...");
		}
	}
}
