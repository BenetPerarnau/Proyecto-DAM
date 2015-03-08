package DTO;

public class Compra {
	
	
	private String proveedor, producto;
	private int cantidad;
	
	public Compra(){}
	public Compra(String proveedor, String producto, int cantidad){
		this.producto=producto;
		this.proveedor=proveedor;
		this.cantidad=cantidad;
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
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	
	

}
