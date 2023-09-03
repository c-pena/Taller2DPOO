package consola;

import procesamiento.Producto;
import procesamiento.Restaurante;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import modelo.Combo;
import modelo.Ingrediente;
import modelo.ProductoAjustado;
import modelo.ProductoMenu;

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
				else if (opcion_seleccionada == 2 && restaurante.getEstado() == 1) {
					String nombreCliente = input("Por favor ingrese su nombre");
					String direccionCliente = input("Por favor ingrese su dirección");
					restaurante.IniciarPedido(nombreCliente, direccionCliente);
				}
				else if (opcion_seleccionada == 3 && restaurante.getEstado() == 2) {
					boolean continuarAgregar = true;
					while (continuarAgregar) {
						System.out.println("\n     Tu pedido actual es:");
						System.out.println(restaurante.getPedidoEnCurso().generarDetallesPedido());
						System.out.println("\n     ¿Qué tipo de elemento desea agregar?");
						System.out.println("     1. Menú Base");
						System.out.println("     2. Combo");
						System.out.println("     3. Salir\n");
						int opcionAgregar = Integer.parseInt(input("Por favor seleccione una opción"));
						if (opcionAgregar == 1) {
							ArrayList<Producto> itemsMenuBase = restaurante.getMenuBase();
							System.out.println("\n          ¿Qué producto desea agregar?\n");
							for (int i = 0; i < itemsMenuBase.size(); i++) {
								Producto item = itemsMenuBase.get(i);
								String nombre = item.getNombre();
								System.out.println(String.format("          %s. %s",Integer.toString(i+1),nombre));
							}
							int opcionAgregarBase = Integer.parseInt(input("\nPor favor seleccione una opción"));
							if (opcionAgregarBase > 0 && opcionAgregarBase <= itemsMenuBase.size()) {
								System.out.println("\n     ¿Desea modificar su producto?");
								System.out.println("     1. Si");
								System.out.println("     2. No\n");
								int opcionModificar = Integer.parseInt(input("Por favor seleccione una opción"));
								if (opcionModificar == 1) {
									ArrayList<Ingrediente> itemsIngredientes = restaurante.getIngredientes();
									ProductoAjustado productoAjustado = new ProductoAjustado((ProductoMenu) itemsMenuBase.get(opcionAgregarBase-1));
									boolean continuarModificar = true;
									while (continuarModificar) {
										System.out.println("\n     ¿Qué tipo de ingrediente desea agregar/eliminar?");
										for (int i = 0; i < itemsIngredientes.size(); i++) {
											Ingrediente ingrediente = itemsIngredientes.get(i);
											String nombre = ingrediente.getNombre();
											System.out.println(String.format("          %s. %s",Integer.toString(i+1),nombre));
										}
										int opcionIngrediente = Integer.parseInt(input("\nPor favor seleccione una opción"));
										if (opcionIngrediente > 0 && opcionIngrediente <= itemsIngredientes.size()) {
											System.out.println("\n     ¿Desea añadir o eliminar "+itemsIngredientes.get(opcionIngrediente-1).getNombre()+" de su producto?");
											System.out.println("     1. Añadir");
											System.out.println("     2. Eliminar\n");
											int opcionAE = Integer.parseInt(input("Por favor seleccione una opción"));
											if (opcionAE == 1) {
												productoAjustado.agregarIngrediente(itemsIngredientes.get(opcionIngrediente-1));
											}
											else {
												productoAjustado.eliminarIngrediente(itemsIngredientes.get(opcionIngrediente-1));
											}
										}
										else {
											System.out.println("Por favor seleccione una opción válida.");
										}
										System.out.println("\n     ¿Desea seguir modificando su producto?");
										System.out.println("     1. Si");
										System.out.println("     2. No\n");
										int opcionContinuarModificar = Integer.parseInt(input("Por favor seleccione una opción"));
										if (opcionContinuarModificar == 2) {
											System.out.println("Terminando de modificar ingredientes del producto...");
											continuarModificar = false;
										}
									}
									restaurante.AdicionarItemPedido(productoAjustado);
								}
								else {
									restaurante.AdicionarItemPedido(itemsMenuBase.get(opcionAgregarBase-1));
								}
							}
							else {
								System.out.println("Por favor seleccione una opción válida.");
							}
						}
						else if (opcionAgregar == 2) {
							ArrayList<Combo> itemsCombo = restaurante.getCombos();
							System.out.println("\n          ¿Qué combo desea agregar?\n");
							for (int i = 0; i < itemsCombo.size(); i++) {
								Combo item = itemsCombo.get(i);
								String nombre = item.getNombre();
								System.out.println(String.format("          %s. %s",Integer.toString(i+1),nombre));
							}
							int opcionAgregarCombo = Integer.parseInt(input("\nPor favor seleccione una opción"));
							if (opcionAgregarCombo > 0 && opcionAgregarCombo <= itemsCombo.size()) {
								restaurante.AdicionarItemPedido(itemsCombo.get(opcionAgregarCombo-1));
							}
							else {
								System.out.println("Por favor seleccione una opción válida.");
							}
						}
						else {
							System.out.println("Terminando de agregar elementos al pedido...");
							continuarAgregar = false;
						}
					}
				}
				else if (opcion_seleccionada == 4 && restaurante.getEstado() > 2) {
					restaurante.cerrarYGuardarPedido();
					System.out.println("Su factura se ha guardado en formato .txt...");
				}
				else if (opcion_seleccionada == 5 && restaurante.getEstado() == 1) {
					int idConsultar = Integer.parseInt(input("Por favor ingrese un ID (NÚMERO DE FACTURA)"));
					restaurante.getPedidoPorId(idConsultar);
				}
				else if (opcion_seleccionada == 6) {
					System.out.println("Saliendo de la aplicación ...");
					continuar = false;
				}
				else if (opcion_seleccionada == 5) {
					System.out.println("Para poder consultar la información de un pedido, debe haber cerrado el pedido.");
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
