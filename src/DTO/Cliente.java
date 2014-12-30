package DTO;

import Exceptions.ExceptionCompras;
import Exceptions.ExceptionDNI_CIF;
import Exceptions.ExceptionNcuenta;
import Exceptions.ExceptionNombre;
import Model.Persona;

public class Cliente extends Persona{

	private int compras;
	private String nCuenta;
	
	public Cliente(String dni, String nom, String ape, String adre, String tlf,
			String mvl, int compras, String nCuenta) throws ExceptionDNI_CIF, ExceptionNcuenta, ExceptionCompras, ExceptionNombre {
		super(dni, nom, ape, adre, tlf, mvl);
		setCompras(compras);
		setnCuenta(nCuenta);
	}

	public int getCompras() {return compras;}

	public String getnCuenta() {return nCuenta;}

	public void setCompras(int compras)throws ExceptionCompras {
		if(compras>=0){
		this.compras = compras;
		}else{
			throw new ExceptionCompras("Valor negativo no permitido.");
		}
		
	}
	public void setnCuenta(String nCuenta) throws ExceptionNcuenta {
		if(nCuenta.length()==24){
			
			this.nCuenta=nCuenta;
			
		}else{
			throw new ExceptionNcuenta("El número de cuenta esta incompleto.");
		}
		//this.nCuenta = nCuenta;
	}

	public void addCompra(int num){
		this.compras+=compras;
	}
}
