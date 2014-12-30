package DTO;

import Exceptions.ExceptionCompras;
import Exceptions.ExceptionDNI_CIF;
import Exceptions.ExceptionNombre;

public class Proveedor {
	
	private String nom;
	private String CIF;
	private String desc;
	private String adr;
	private String tlf;
	private int compras;
	
	public Proveedor( String CIF, String nom, String adr, String tlf, String desc, int compras) throws ExceptionDNI_CIF, ExceptionCompras, ExceptionNombre{
		
		setCIF(CIF);
		setNom(nom);
		setAdr(adr);
		setTlf(tlf);
		setDesc(desc);
		setCompras(compras);
		
	}

	public String getNom() {return nom;}

	public String getCIF() {return CIF;}

	public String getDesc() {return desc;}

	public String getAdr() {return adr;}

	public String getTlf() {return tlf;}

	public int getCompras() {return compras;}

	public void setNom(String nom) throws ExceptionNombre {
		if(nom.length()==0){
			throw new ExceptionNombre("El nombre no puede estar vacio");
		}else{
			this.nom = nom;
		}
	
	}

	public void setCIF(String cIF) throws ExceptionDNI_CIF {
		
		if(cIF.length()==9){
			cIF=cIF.toUpperCase();
			if(cIF.charAt(0)>=65 && cIF.charAt(0)<=90){
				this.CIF = cIF;
			}else{
				throw new ExceptionDNI_CIF("El CIF tiene que tener que empezar por una letra.");
			}
					
		}else{
			throw new ExceptionDNI_CIF("El CIF tiene que tener 9 digitos.");
		}
		
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void setAdr(String adr) {
		this.adr = adr;
	}

	public void setTlf(String tlf) {
		this.tlf = tlf;
	}

	public void setCompras(int compras)throws ExceptionCompras {
		if(compras>=0){
		this.compras = compras;
		}else{
			throw new ExceptionCompras("Valor negativo no permitido.");
		}
		
	}
	
	public void addCompra(int num){
		this.compras+=num;
	}

}
