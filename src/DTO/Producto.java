package DTO;

import Exceptions.ExceptionNombre;
import Exceptions.ExceptionPrecio;
import Exceptions.ExceptionStock;

public class Producto {
	
	private String cod, name, desc, proveedor;
	private int stock;
	private float precio;
	
	public Producto(String cod, String name, String desc, float precio, int stock, String proveedor) throws ExceptionNombre, ExceptionStock, ExceptionPrecio{
	
		setCod(cod);
		setName(name);
		setDesc(desc);
		setPrecio(precio);
		setStock(stock);
		setProveedor(proveedor);
	}

	public String getCod() {return cod;}
	public String getName() {return name;}
	public String getDesc() {return desc;}
	public String getProveedor() {return proveedor;}
	public int getStock() {return stock;}
	public float getPrecio() {return precio;}

	public void setCod(String cod) {
		this.cod = cod;
	}

	public void setName(String name) throws ExceptionNombre{
		if(name.length()>0){
			this.name = name;
		}else{
			throw new ExceptionNombre("El nombre del producto no puede estar vacio.");
		}
		
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void setProveedor(String proveedor) {
		this.proveedor = proveedor;
	}

	public void setStock(int stock) throws ExceptionStock{
		if(stock>=0){
			this.stock = stock;
		}else{
			throw new ExceptionStock("El stock no puede ser negativo.");
		}
		
	}

	public void setPrecio(float precio) throws ExceptionPrecio{
		if(precio<0){
			throw new ExceptionPrecio("El precio no puede ser negativo.");
		}else{
			this.precio = precio;
		}	
	}
	
	
}
