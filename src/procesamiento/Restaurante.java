package procesamiento;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import modelo.Combo;
import modelo.Ingrediente;
import modelo.ProductoMenu;

public class Restaurante {
	
	Map<String, Ingrediente> ingredientes = new HashMap<>();
	Map<String, ProductoMenu> menu = new HashMap<>();
	Map<String, Combo> combos = new HashMap<>();
	
	public void cargarInformacionRestaurante(File archivoIngredientes, File archivoMenu, File archivoCombos) {
		cargarIngredientes(archivoIngredientes);
		cargarMenu(archivoMenu);
		cargarCombos(archivoCombos);
	}
	
	
	private void cargarIngredientes(File archivoIngredientes) {
		try (BufferedReader br = new BufferedReader(new FileReader(archivoIngredientes))) {
			String linea = br.readLine();
			while(linea != null) {
				String[] partes = linea.split(";");
				String nombreProducto = partes[0];
				int precioProducto = Integer.parseInt(partes[1]);
				Ingrediente nuevo = new Ingrediente(nombreProducto, precioProducto);
				ingredientes.put(nombreProducto, nuevo);
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void cargarMenu(File archivoMenu) {
		try (BufferedReader br = new BufferedReader(new FileReader(archivoMenu))) {
			String linea = br.readLine();
			while(linea != null) {
				String[] partes = linea.split(";");
				String nombreProducto = partes[0];
				int precioProducto = Integer.parseInt(partes[1]);
				ProductoMenu nuevo = new ProductoMenu(nombreProducto, precioProducto);
				menu.put(nombreProducto, nuevo);
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void cargarCombos(File archivoCombos) {
		try (BufferedReader br = new BufferedReader(new FileReader(archivoCombos))) {
			String linea = br.readLine();
			while(linea != null) {
				String[] partes = linea.split(";");
				String nombre = partes[0];
				double descuento = 1 - Integer.parseInt(partes[1].replace('%', ' ')) * 0.01;
				Combo nuevoCombo = new Combo(nombre, descuento);
				
				nuevoCombo.agregarItemACombo(menu.get(partes[2]));
				nuevoCombo.agregarItemACombo(menu.get(partes[3]));
				nuevoCombo.agregarItemACombo(menu.get(partes[4]));
				
				
				// Aca tengo que añadir al combo las cosas extra
				
				
				combos.put(nombre, nuevoCombo);
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
