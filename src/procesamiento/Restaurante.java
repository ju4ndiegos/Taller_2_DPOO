package procesamiento;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import modelo.Bebida;
import modelo.Combo;
import modelo.Ingrediente;
import modelo.Pedido;
import modelo.Producto;
import modelo.ProductoMenu;

public class Restaurante {
	private List<Ingrediente> ingredientes;
	private Map<String,Producto> menuBase;
	private List<Combo> combos;
	private List<Bebida> bebidas;
	private List<Pedido> pedidos;
	
	public List<Pedido> getPedidos() {
		return pedidos;
	}

	private Pedido pedidoEnCurso;
	
	public Restaurante()
	{
		this.pedidos=new ArrayList<>();
	} 

	private void cargarIngredientes(File archivoIngredientes) throws FileNotFoundException, IOException 
	{
		//Inicializo lista ingredientes
		
		this.ingredientes = new ArrayList<>();
		
		// Abrir el archivo y leerlo línea por línea usando un BufferedReader
		try (
			BufferedReader br = new BufferedReader(new FileReader(archivoIngredientes))) 
		{
			String linea = br.readLine();
			while (linea != null) 
			{
				
				// Separar los valores que estaban en una línea
				String[] partes = linea.split(";");
				
				String nombre = partes[0];
				int costoAdicional = Integer.parseInt(partes[1]);
				
				Ingrediente ingrediente = new Ingrediente(nombre, costoAdicional);
				
				this.ingredientes.add(ingrediente);
				
				linea = br.readLine();
				
				
							
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void cargarMenu(File archivoMenu) throws FileNotFoundException, IOException 
	{
		
			//Inicializo lista ingredientes
			
			this.menuBase=new HashMap<>();
			
			
			// Abrir el archivo y leerlo línea por línea usando un BufferedReader
			try (
				BufferedReader br = new BufferedReader(new FileReader(archivoMenu))) 
			{
				String linea = br.readLine();
				while (linea != null) 
				{
					
					// Separar los valores que estaban en una línea
					String[] partes = linea.split(";");
					
					String nombre = partes[0];
					int precioBase = Integer.parseInt(partes[1]);
					int calorias = Integer.parseInt(partes[2]);
					
					ProductoMenu productoMenu = new ProductoMenu(nombre, precioBase,calorias);
					
					this.menuBase.put(nombre,productoMenu);
					
					linea = br.readLine();
					
					
								
				}
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	
	private void cargarCombos(File archivoCombos) throws FileNotFoundException, IOException
	
	{
		
		//Inicializo lista combos
		
		this.combos=new ArrayList<>();
		
		
		// Abrir el archivo y leerlo línea por línea usando un BufferedReader
		try (
			BufferedReader br = new BufferedReader(new FileReader(archivoCombos))) 
		{
			String linea = br.readLine();
			while (linea != null) 
			{
				
				// Separar los valores que estaban en una línea
				String[] partes = linea.split(";");
				
				String nombre = partes[0];
				double descuento = Double.parseDouble(partes[1].replace("%", "")) / 100.0;
				int calorias = Integer.parseInt(partes[2]);
				
				Combo combo = new Combo(descuento, nombre,calorias);
				
				for (int i=3;i<partes.length;i++)
				{
					String nombre_producto = partes[i];
					
					Producto producto =this.menuBase.get(nombre_producto);
					
					combo.agregarItemACombo(producto);
					
					
				}
				
				
				this.combos.add(combo);
				
				linea = br.readLine();
				
				
							
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
private void cargarBebidas(File archivoCombos) throws FileNotFoundException, IOException
	
	{
		
		//Inicializo lista combos
		
		this.bebidas=new ArrayList<>();
		
		
		// Abrir el archivo y leerlo línea por línea usando un BufferedReader
		try (
			BufferedReader br = new BufferedReader(new FileReader(archivoCombos))) 
		{
			String linea = br.readLine();
			while (linea != null) 
			{
				
				// Separar los valores que estaban en una línea
				String[] partes = linea.split(";");
				
				String nombre = partes[0];
				int precio = Integer.parseInt(partes[1]);
				int calorias = Integer.parseInt(partes[2]);
				
				Bebida bebida = new Bebida(calorias,nombre,precio);
				
				
				
				
				this.bebidas.add(bebida);
				
				linea = br.readLine();
				
				
							
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void cargarInformacionRestaurante(File archivoIngredientes, File archivoMenu, File archivoCombos, File archivoBebidas) throws FileNotFoundException, IOException
	{
		cargarIngredientes(archivoIngredientes);
		cargarMenu(archivoMenu);
		cargarCombos(archivoCombos);
		cargarBebidas(archivoBebidas);
	}

	public void iniciarPedido(String nombreCliente, String direccionCliente)
	{
		this.pedidoEnCurso = new Pedido(nombreCliente, direccionCliente);
	}
	public boolean cerrarYGuardarPedido() throws IOException
	{
		File factura =new File(this.pedidoEnCurso.getIdPedido()+".txt");
		this.pedidoEnCurso.guardarFactura(factura);
		
		boolean respuesta = this.pedidoEnCurso.equals(pedidos);
		this.pedidos.add(this.pedidoEnCurso);
		
		this.pedidoEnCurso=null;
		
		return respuesta;
	}
	
	public Pedido getPedidoEnCurso()
	{
		return this.pedidoEnCurso;
	}
	
	public ArrayList<Producto> getMenuBase()
	{
		List<Producto> listaMenu = new ArrayList<>();
		
		for (Entry<String, Producto> entry : this.menuBase.entrySet()) 
		{
		    listaMenu.add(entry.getValue());
		}
		
		for (Combo combo:this.combos)
		{
			listaMenu.add(combo);
		}
		for (Bebida bebida:this.bebidas)
		{
			listaMenu.add(bebida);
		}
		
		return (ArrayList<Producto>) listaMenu;
	}
	
	public ArrayList<Ingrediente> getIngredientes()
	{	
	
		return (ArrayList<Ingrediente>) this.ingredientes;
		
	} 
}
