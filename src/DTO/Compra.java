package DTO;

import Exceptions.ExceptionPrecio;

public class Compra {
	
	
	private String proveedor, producto;
	private int cantidad,id;
	
	public Compra(){}
	public Compra(int id,int cantidad,String producto, String proveedor) throws ExceptionPrecio{
		setProducto(producto);
		setProveedor(proveedor);
		setCantidad(cantidad);
		setId(id);
	}
	public Compra(int cantidad,String producto, String proveedor) throws ExceptionPrecio{
		setProducto(producto);
		setProveedor(proveedor);
		setCantidad(cantidad);
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProveedor() {
		return proveedor;
	}
	public String getProducto() {
		return producto;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setProveedor(String proveedor) {
		this.proveedor = proveedor;
	}
	public void setProducto(String producto) {
		this.producto = producto;
	}
	public void setCantidad(int cantidad) throws ExceptionPrecio{
		if(cantidad>0){
			this.cantidad = cantidad;
		}else{
			throw new ExceptionPrecio("Una compra no pude tener de cantidad 0 o inferior");
		}
	}
	
	

}
