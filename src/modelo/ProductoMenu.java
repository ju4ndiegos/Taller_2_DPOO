package modelo;

public class ProductoMenu implements Producto 
{

	private String nombre;
	private int precioBase;
	private int calorias;
	
	public ProductoMenu(String nombre,int precioBase,int calorias) 
	{
		this.nombre = nombre;
		this.precioBase = precioBase;
		this.calorias = calorias;
	}

	@Override
	public String getNombre() 
	{
		return nombre;
	}
	
	@Override
	public int getPrecio() 
	{
		return precioBase;
	}


	@Override
	public String generarTextoFactura() 
	{
		// TODO Auto-generated method stub
		return "	PRODUCTO MENU\t"+this.getNombre()+"\t"+this.getPrecio()+"\t"+this.getCalorias()+"\n";
	}

	public int getCalorias() {
		return calorias;
	}
	
	
}
