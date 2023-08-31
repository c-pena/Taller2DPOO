package procesamiento;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import modelo.Ingrediente;

public class Restaurante {
	ArrayList<Ingrediente> ingredientes = new ArrayList<Ingrediente>();
	
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
				ingredientes.add(nuevo);
			}
			
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
