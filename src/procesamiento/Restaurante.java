package procesamiento;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import modelo.Ingrediente;

public class Restaurante {
	Map<String, Ingrediente> ingredientes = new HashMap<>();
	
	public void cargarInformacionRestaurante(File archivoIngredientes, File archivoMenu, File archivoCombos) {
		cargarIngredientes(archivoIngredientes);
	}
	
	
	private void cargarIngredientes(File archivoIngredientes) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(archivoIngredientes));
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
}
