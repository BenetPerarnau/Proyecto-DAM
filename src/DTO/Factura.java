package DTO;

import java.sql.Date;

import Exceptions.ExceptionCantidad;
import Exceptions.ExceptionPrecio;

public class Factura {
	
	
	private String cliente, producto;
	private int cantidad, id;
	private float precio;
	private boolean pagada;
	private Date fecha;
	
	public Factura(){}
	
	public Factura(int id,String cliente, String producto, int cantidad, float precio, boolean pagada, Date fecha) throws ExceptionCantidad{
		setCliente(cliente);
		setProducto(producto);
		setCantidad(cantidad);
		setPrecio(precio);
		setPagada(pagada);
		setFecha(fecha);
		setId(id);
	}
	
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
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
	public boolean isPagada() {
		return pagada;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	public void setProducto(String producto) {
		this.producto = producto;
	}
	public void setCantidad(int cantidad) throws ExceptionCantidad {
		if(cantidad>0){
			this.cantidad = cantidad;
		}else{
			throw new ExceptionCantidad("La cantidad no puede ser 0 o inferior");
		}
	}
	public void setPrecio(float precio) {
		this.precio = precio;
	}
	public void setPagada(boolean pagada) {
		this.pagada = pagada;
	}
	
	

}
