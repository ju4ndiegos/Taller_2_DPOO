package modelo;

public class Bebida implements Producto{
	int calorias;
	String nombre;
	int precio;
	@Override
	public int getPrecio() {
		// TODO Auto-generated method stub
		return this.precio;
	}
	@Override
	public String getNombre() {
		// TODO Auto-generated method stub
		return this.nombre;
	}
	@Override
	public String generarTextoFactura() {
		// TODO Auto-generated method stub
		return "	BEBIDA:\t"+this.getNombre()+"\t"+this.getPrecio()+"\t"+this.getCalorias()+"\n";
	}
	public Bebida(int calorias, String nombre, int precio) {
		this.calorias = calorias;
		this.nombre = nombre;
		this.precio = precio;
	}
	public int getCalorias() {
		return calorias;
	}

	
	

}
