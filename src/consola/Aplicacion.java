package consola;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import modelo.Ingrediente;
import modelo.Pedido;
import modelo.Producto;
import modelo.ProductoAjustado;
import modelo.ProductoMenu;
import procesamiento.Restaurante;

public class Aplicacion {
	
	private Restaurante restaurante;
	
	public void mostrarMenu() {
		System.out.println("----------------MEMU----------------");
		System.out.println("\nOpciones de la aplicación\n");
		System.out.println("1. Iniciar pedido");
		System.out.println("2. Agregar un producto (combo,base o ajustado)");
		System.out.println("3. Cerrar pedido y guardar la factura");
		System.out.println("4. Consultar pedido por id");
		System.out.println("5. Salir de la aplicación\n");
		//TODO completar
	}
	
	public void iniciarPedido()
	{
		if (this.restaurante.getPedidoEnCurso()==null)
		{
		String nomCliente = input("Ingrese el nombre del cliente: ");
		String direccionCliente = input("Ingrese la dirección del cliente: ");
		this.restaurante.iniciarPedido(nomCliente, direccionCliente);
		
		Pedido pedido = this.restaurante.getPedidoEnCurso();
		
		System.out.println("El pedido de id "+pedido.getIdPedido()+" ha sido creado con exito.");
		}
		else 
		{
			System.out.println("Ya existe un pedido en curso, cierrelo para poder crear otro");
		}
	}
	public void ejecutarOpcion(int opcion) throws IOException {
		
		if (opcion == 1)
		{	
			this.iniciarPedido();
		}
		
		if (opcion ==2)
		{
			
			Pedido pedido = this.restaurante.getPedidoEnCurso();
			System.out.println("Seleccione el producto base que desee, podrá ajustarlo luego si así lo prefiere");
			
			System.out.println("Productos menu:");
			ArrayList<Producto> productos = this.restaurante.getMenuBase();
			
			int i = 0;
			for (Producto producto:productos)
			{
				i++;
				System.out.println(i+". "+producto.getNombre());
				
			}
			
			int seleccion = Integer.parseInt(input("¿Qué producto desea ordenar?"));
			Producto orden = productos.get(seleccion-1);
			
			if (orden instanceof ProductoMenu)
			{
				int ajuste = Integer.parseInt(input("¿Desea ajustar el producto?\n1 para sí\n2 para no\n->"));
			
				if (ajuste ==1)
				{	
					ProductoAjustado ordenAjustada = new ProductoAjustado((ProductoMenu) orden);
				//TODO modificar Ajustado
					this.ajustarOrden(ordenAjustada);
					
					pedido.agregarProducto(ordenAjustada);
					System.out.println("El producto se agregó a la orden exitosamente");
				}
				if (ajuste==2)
				{
					pedido.agregarProducto(orden);
					System.out.println("El producto se agregó a la orden exitosamente");
				}
			}
			else 
			{
				
				pedido.agregarProducto(orden);
				System.out.println("El producto se agregó a la orden exitosamente");
			}
			
				
		}
		if (opcion ==3)
			
		{
			this.restaurante.cerrarYGuardarPedido();
			
			System.out.println("La factura ha quedado guardada");
		}
		
		if (opcion == 4)
		{
			System.out.println("Consultar pedido por id");
			
			int id = Integer.parseInt(input("Ingrese el id: "));
			
			ArrayList<Pedido> pedidos = (ArrayList<Pedido>) this.restaurante.getPedidos();
			if (id<= pedidos.size())
			{Pedido pedido = pedidos.get(id);
			
			System.out.println(pedido.toString());
			}
			else
			{
				System.out.println("Ingrese un id válido");
			}
		}
	}
	
	private void ajustarOrden(ProductoAjustado ordenAjustada) {
		boolean ajustado = false;
		while (ajustado==false)
		{
			
			ArrayList<Ingrediente> ingredientes = this.restaurante.getIngredientes();
		
			int i1 = 0;
			for (Ingrediente ingrediente:ingredientes)
			{
				i1++;
				System.out.println(i1+". "+ingrediente.getNombre());
				
			}
			
			int seleccion = Integer.parseInt(input("¿Qué ingrediente desea añadir o eliminar?\n->"));
			
			if (seleccion <= ingredientes.size() & seleccion >0)
			{
				Ingrediente ingredienteSeleccionado = ingredientes.get(seleccion-1);
				
				int ajuste =  Integer.parseInt(input("¿Desea eliminar o agregar un producto?\n1 agregar\n2 eliminar\n->"));
				if (ajuste==1)
				{
				ordenAjustada.addIngredientes(ingredienteSeleccionado);
				}
				else if (ajuste==2)
				{
					ordenAjustada.removeIngredientes(ingredienteSeleccionado);
				}
				else
				{System.out.println("Selecciono un numero distinto de 2 o 1");}
			}
			else
			{
				System.out.println("Seleccione un ingrediente válido");
			}
			
			int fin =  Integer.parseInt(input("¿Desea continuar?\n1 para finalizar\nOtro número para continuar\n->"));
		
			if (fin==1)
			{
				ajustado=true;
			}
		}
		
	}

	public void iniciarAplicacion() throws FileNotFoundException, IOException {
		this.restaurante = new Restaurante();
		
		File archivoIngredientes = new File("./data/ingredientes.txt");
		File archivoMenu = new File ("./data/menu.txt") ;
		File archivoCombos = new File ("./data/combos.txt");
		
		this.restaurante.cargarInformacionRestaurante(archivoIngredientes,archivoMenu,archivoCombos);
		
		
		//Seleccionar opciones-------------------------------------------------------------
		
				boolean continuar = true;
				while (continuar) {
				
				this.mostrarMenu();
				//pedir numero
				int opcion = 0;
				
				
				// cambios de input hechos por eclipse
				try {
					opcion = Integer.parseInt(input("Por favor seleccione una opción: "));
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// -------------------------------
				
				//si la opcion es salir
				if (opcion==5)
				{continuar=false;
				System.out.println("Saliendo de la aplicación..");}
				
				else {
					this.ejecutarOpcion(opcion);
					
				}
				
				// Fin seleccionar opciones-----------------------------------------------------
				}
	}
	
	public static void main(String[] args) throws NumberFormatException, FileNotFoundException, IOException {
		Aplicacion aplicacion = new Aplicacion();
		aplicacion.iniciarAplicacion();
		
	}
	
	
	//input funcion
	
	public static String input(String mensaje)
	{
		try
		{
			System.out.print(mensaje);
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			return reader.readLine();
		}
		catch (IOException e)
		{
			System.out.println("Error leyendo de la consola");
			e.printStackTrace();
		}
		return null;
	}

}
