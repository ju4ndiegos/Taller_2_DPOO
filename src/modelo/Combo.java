package modelo;

import java.util.ArrayList;
import java.util.List;

public class Combo implements Producto{
	private double descuento;
	private String nombreCombo;
	private List<Producto> itemsCombo;
	private int calorias;
	
	public Combo(double descuento,String nombreCombo,int calorias) 
	{
		this.descuento = descuento;
		this.nombreCombo = nombreCombo;
		this.calorias = calorias;
		
		this.itemsCombo=new ArrayList<>();
	}
	
	public void agregarItemACombo(Producto itemCombo)
	{
		this.itemsCombo.add(itemCombo);
	}

	@Override
	public int getPrecio() {
		// TODO Auto-generated method stub
		
		int precio =0;
		
		for (int i = 0;i<this.itemsCombo.size();i++) 
		{
			precio+=this.itemsCombo.get(i).getPrecio();
		}
		
		return (int) (precio*(1.0-this.descuento));
	}

	@Override
	public String getNombre() {
		// TODO Auto-generated method stub
		return this.nombreCombo;
	}

	@Override
	public String generarTextoFactura() {
		// TODO Auto-generated method stub
		return "	COMBO:\t"+this.getNombre()+"\t"+this.getPrecio()+"\t"+this.getCalorias()+"\n";
	}

	public int getCalorias() {
		return calorias;
	}

}
