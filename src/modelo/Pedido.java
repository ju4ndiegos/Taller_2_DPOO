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
	
	public List<Producto> getItemsPedido() {
		return itemsPedido;
	}

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
	
	private int getCaloriasTotales()
	{
		int caloriasTotales=0;
		for (int i=0;i<this.itemsPedido.size();i++)
		{
			caloriasTotales+=this.itemsPedido.get(i).getCalorias();
		}
		return caloriasTotales;
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
		factura.append("	Descripción\tNombre\tCalorias\tPrecio\n");
		
		for (int i=0;i<this.itemsPedido.size();i++)
		{
			factura.append(itemsPedido.get(i).generarTextoFactura());
		}
		
		
		factura.append("\n------------------------------------------------------\n");
		factura.append("	Total calorias:\t"+this.getCaloriasTotales()+"\n");
		factura.append("	Total neto:\t\t"+this.getPrecioNetoPedido()+"\n");
		factura.append("	Total IVA:\t\t"+this.getPrecioIVAPedido()+"\n");
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
	
	public boolean equals(List<Pedido> pedidoList)
	{
		List<Producto> itemsA = this.getItemsPedido();
		
		
		
		boolean comparacion = false ;
		
		
		int i=0;
		while(comparacion==false & i < pedidoList.size())
		{
		List<Producto> itemsB = pedidoList.get(i).getItemsPedido();
		
		boolean comparador = true;
		if (itemsB.size() == itemsA.size())
		{
			for (Producto B:itemsB)
			{
				boolean icomparacion = false;
				int j = 0;
				// B es el primer elemento de B
				while (icomparacion==false & j<itemsA.size())
				{
					Producto A = itemsA.get(j);
					if (B.equals(A))
					{
						icomparacion = true;
					}
				
					j++;
				}
			//Si icomparacion es verdadera hay que seguir comprobando, si no. Hay un item en B que no está en a
			comparador&=icomparacion;
			
			}
			comparacion=comparador;
		}
		
		i++;
		}
		
		return comparacion;
	}
	
}
