package DTO;

public class Venta {
	
	private String cliente, producto;
	private int cantidad, id;
	private float precio;
	private int factura_r;
	
	
	public Venta(){}
	public Venta(int id, String cliente, String producto, int cantidad, float precio, int factura_r){
		this.cliente=cliente;
		this.producto=producto;
		this.cantidad=cantidad;
		this.precio=precio;
		this.factura_r=factura_r;
		this.id=id;
	}
	public Venta(String cliente, String producto, int cantidad, float precio, int factura_r){
		this.cliente=cliente;
		this.producto=producto;
		this.cantidad=cantidad;
		this.precio=precio;
		this.factura_r=factura_r;
		//this.id=id;
	}
	public Venta(int id, String cliente, String producto, int cantidad, float precio){
		this.cliente=cliente;
		this.producto=producto;
		this.cantidad=cantidad;
		this.precio=precio;
		//this.factura_r=0;
		this.id=id;
	}
	public Venta(String cliente, String producto, int cantidad, float precio){
		this.cliente=cliente;
		this.producto=producto;
		this.cantidad=cantidad;
		this.precio=precio;
		//this.factura_r=0;
		//this.id=id;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public int getFactura_r() {
		return factura_r;
	}
	public void setFactura_r(int factura_r) {
		this.factura_r = factura_r;
	}
	
	

}
