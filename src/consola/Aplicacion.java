package consola;

import procesamiento.Restaurante;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Aplicacion {
	
	private Restaurante restaurante;
	
	public void mostrarMenu() {
		System.out.println("\nOpciones de la aplicación\n");
		System.out.println("1. Mostrar Menú");
		System.out.println("2. Iniciar un nuevo pedido");
		System.out.println("3. Agregar un elemento a un pedido");
		System.out.println("4. Cerrar un pedido y guardar la factura");
		System.out.println("5. Consultar la información de un pedido dado su ID");
		System.out.println("6. Salir\n");
	}
	
	public void ejecutarAplicacion() {
		restaurante = new Restaurante();
		try {
			restaurante.cargarInformacionRestaurante("ingredientes.txt", "menu.txt", "combos.txt");
		}
		catch (NumberFormatException e) {
			System.out.println("Revisar los archivos en la carpeta data.");
			e.printStackTrace();
		}
		boolean continuar = true;
		while (continuar) {
			try {
				mostrarMenu();
				int opcion_seleccionada = Integer.parseInt(input("Por favor seleccione una opción"));
				if (opcion_seleccionada == 1)
					System.out.println(restaurante.getMenuCompleto());
				else if (opcion_seleccionada == 2 && restaurante != null)
					;
				else if (opcion_seleccionada == 3 && restaurante != null)
					;
				else if (opcion_seleccionada == 4 && restaurante != null)
					;
				else if (opcion_seleccionada == 5 && restaurante != null)
					;
				else if (opcion_seleccionada == 6) {
					System.out.println("Saliendo de la aplicación ...");
					continuar = false;
				}
				else if (restaurante == null) {
					System.out.println("Para poder ejecutar esta opción primero debe cargar un archivo de combos, ingredientes y menu.");
				}
				else {
					System.out.println("Por favor seleccione una opción válida.");
				}
			}
			catch (NumberFormatException e) {
				System.out.println("Debe seleccionar uno de los números de las opciones.");
			}
		}
	}
	
	public String input(String mensaje) {
		try {
			System.out.print(mensaje + ": ");
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			return reader.readLine();
		}
		catch (IOException e) {
			System.out.println("Error leyendo de la consola");
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		Aplicacion app = new Aplicacion();
		app.ejecutarAplicacion();
	}

}
