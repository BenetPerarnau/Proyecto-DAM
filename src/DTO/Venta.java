package DTO;

public class Venta {
	
	private String cliente, producto;
	private int cantidad;
	private float precio;
	
	
	public Venta(){}
	public Venta(String cliente, String producto, int cantidad, float precio){
		this.cliente=cliente;
		this.producto=producto;
		this.cantidad=cantidad;
		this.precio=precio;
		
	}
	public String getCliente() {
		return cliente;
	}
	public String getProducto() {
		return producto;
	}
	public int getCantidad() {
		return cantidad;
	}
	public float getPrecio() {
		return precio;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	public void setProducto(String producto) {
		this.producto = producto;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	public void setPrecio(float precio) {
		this.precio = precio;
	}
	

}
