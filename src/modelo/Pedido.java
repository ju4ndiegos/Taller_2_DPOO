package modelo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Pedido {
	private static int numPedidos;
	private int idPedido;
	private String nombreCliente;
	private String dirreccionCliente;
	private List<Producto> itemsPedido;
	
	public Pedido(String nombreCliente, String dirreccionCliente)
	{
		this.dirreccionCliente=dirreccionCliente;
		this.nombreCliente=nombreCliente;
		this.itemsPedido = new ArrayList<>();
		
		this.idPedido  = Pedido.numPedidos;
		Pedido.numPedidos+=1;
		
	}

	public int getIdPedido() {
		return this.idPedido;
	}
	
	
	public void agregarProducto(Producto nuevoItem) 
	{
		this.itemsPedido.add(nuevoItem);
	}
	
	private int getPrecioNetoPedido()
	{
		int precioNeto=0;
		for (int i=0;i<this.itemsPedido.size();i++)
		{
			precioNeto+=this.itemsPedido.get(i).getPrecio();
		}
		return precioNeto;
	}
	
	private int getPrecioTotalPedido()
	{
		return this.getPrecioNetoPedido()+this.getPrecioIVAPedido();
	}
	
	private int getPrecioIVAPedido()
	{
		float IVA = (float) 0.19;
		
		return (int) (this.getPrecioNetoPedido()*IVA);
	}
	
	private String generarTextoFactura() 
	{
		StringBuilder factura = new StringBuilder();
		
		factura.append("--------------- Hamburguesas El Corral ---------------\n");
		factura.append("Datos cliente:\n");
		factura.append("	Nombre:\t"+this.nombreCliente+"\n");
		factura.append("	Dirección:\t"+this.dirreccionCliente+"\n");
		factura.append("------------------------------------------------------\n");
		factura.append("PRODUCTOS:\n");
		factura.append("	Descripción\tNombre\tPrecio\n");
		
		for (int i=0;i<this.itemsPedido.size();i++)
		{
			factura.append(itemsPedido.get(i).generarTextoFactura());
		}
		
		
		factura.append("\n------------------------------------------------------\n");
		factura.append("	Total neto:\t"+this.getPrecioNetoPedido()+"\n");
		factura.append("	Total IVA:\t"+this.getPrecioIVAPedido()+"\n");
		factura.append("------------------------------------------------------\n");
		factura.append("	TOTAL\t"+this.getPrecioTotalPedido()+"\n");
		
		
		
		return factura.toString();
	}
	
	public void guardarFactura(File archivo) throws IOException 
	{
		
		FileWriter fileWriter = new FileWriter(archivo);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(this.generarTextoFactura());
        bufferedWriter.close();
	}
	
}
