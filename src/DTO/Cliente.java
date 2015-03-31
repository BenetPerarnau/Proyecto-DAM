package DTO;

import Exceptions.ExceptionCompras;
import Exceptions.ExceptionDNI_CIF;
import Exceptions.ExceptionNcuenta;
import Exceptions.ExceptionNombre;
import Model.Persona;

/**
 * 
 * @author Benet
 *
 */
/*
 * Veamos un ejemplo con cuentas de La Caixa:

- Código de cuenta: 2100 0813 61 0123456789

- IBAN electrónico: ES79-2100-0813-6101-2345-6789 (ES es la identificación de España)
 */
public class Cliente extends Persona{

	private int compras;
	private String nCuenta;
	/**
	 * 
	 * @param dni
	 * @param nom
	 * @param ape
	 * @param adre
	 * @param tlf
	 * @param mvl
	 * @param compras
	 * @param nCuenta
	 * @throws ExceptionDNI_CIF
	 * @throws ExceptionNcuenta
	 * @throws ExceptionCompras
	 * @throws ExceptionNombre
	 */
	
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
