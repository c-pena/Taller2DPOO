package procesamiento;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import modelo.Combo;
import modelo.Ingrediente;
import modelo.ProductoMenu;

public class Restaurante {
	
	Map<String, Ingrediente> ingredientes = new HashMap<>();
	Map<String, ProductoMenu> menu = new HashMap<>();
	Map<String, Combo> combos = new HashMap<>();
	
	public void cargarInformacionRestaurante(String archivoIngredientes, String archivoMenu, String archivoCombos) {
		String workingDir = System.getProperty("user.dir");
        String filePath = workingDir + File.separator + "data" + File.separator;
        
		cargarIngredientes(filePath + archivoIngredientes);
		cargarMenu(filePath + archivoMenu);
		cargarCombos(filePath + archivoCombos);
	}
	
	private void cargarIngredientes(String archivoIngredientes) {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(archivoIngredientes), StandardCharsets.UTF_8))) {
			String linea = br.readLine();
			while(linea != null) {
				String[] partes = linea.split(";");
				String nombreProducto = partes[0];
				int precioProducto = Integer.parseInt(partes[1]);
				Ingrediente nuevo = new Ingrediente(nombreProducto, precioProducto);
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
				ProductoMenu nuevo = new ProductoMenu(nombreProducto, precioProducto);
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
				nuevoCombo.agregarItemACombo(menu.get(partes[4]));
				
				combos.put(nombre, nuevoCombo);
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
		
		String completa = uLine + combosFinal + uLine + menuFinal;
		return completa;
	}
	
	
	
}
