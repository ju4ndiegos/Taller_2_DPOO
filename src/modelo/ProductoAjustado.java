package modelo;

import java.util.ArrayList;
import java.util.List;

public class ProductoAjustado implements Producto 
{	
	private ProductoMenu base;
	private List<Ingrediente> agregados;
	private List<Ingrediente> eliminados;
	
	public void addIngredientes(Ingrediente add)
	{
		this.agregados.add(add);
	}
	
	public void removeIngredientes(Ingrediente ingrediente)
	{
		this.eliminados.add(ingrediente);
	}

	@Override
	public int getPrecio() {
		// TODO Auto-generated method stub
		int precio = this.base.getPrecio();
		
		for (int i=0;i<this.agregados.size();i++)
		{
			precio+=this.agregados.get(i).getCostoAdicional();
		} 
		return precio;
	}

	@Override
	public String getNombre() {
		// TODO Auto-generated method stub
		String nombre = this.base.getNombre();
				
		return nombre ;
	}

	@Override
	public String generarTextoFactura() {
		// TODO Auto-generated method stub
		String factura ="	PRODUCTO MOD:\t"+this.getNombre()+"\t"+this.getPrecio()+"\t"+this.getCalorias()+"\n";

		for (int i = 0; i<this.agregados.size();i++)
		{
			factura+="	 Add.\t"+this.agregados.get(i).getNombre()+"\t"+this.agregados.get(i).getCostoAdicional()+"\n";
		}
		
		for (int i = 0; i<this.eliminados.size();i++)
		{
			factura+="	 Sin.\t"+this.eliminados.get(i).getNombre()+"\t0\n";
		}
		
		return factura;
	}
	
	public ProductoAjustado(ProductoMenu base)
	{
		this.base =base;
		
		this.agregados = new ArrayList<>();
		this.eliminados = new ArrayList<>();
	}

	public int getCalorias() {
		return this.base.getCalorias();
	}

}
