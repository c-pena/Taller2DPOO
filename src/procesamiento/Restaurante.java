package procesamiento;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import modelo.Ingrediente;
import modelo.ProductoMenu;

public class Restaurante {
	
	Map<String, Ingrediente> ingredientes = new HashMap<>();
	Map<String, ProductoMenu> menu = new HashMap<>();
	Map<String, Combo> combos = new HashMap<>();
	
	public void cargarInformacionRestaurante(File archivoIngredientes, File archivoMenu, File archivoCombos) {
		cargarIngredientes(archivoIngredientes);
		cargarMenu(archivoMenu);
	}
	
	
	private void cargarIngredientes(File archivoIngredientes) {
		try (BufferedReader br = new BufferedReader(new FileReader(archivoIngredientes))) {
			String linea = br.readLine();
			while(linea != null) {
				String[] partes = linea.split(",");
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
				String[] partes = linea.split(",");
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
				
				
				String[] partes = linea.split(",");
				String nombreCombo = partes[0];
				float descuento = (float) Integer.parseInt(partes[1].replace('%', ' ')) / 100;
				String nombreProducto = partes[2];
				String tipoPapas = partes[3];
				String tipoBebida = partes[4];
				Combo nuevo = new Combo();
				menu.put(nombreCombo, nuevo);
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
