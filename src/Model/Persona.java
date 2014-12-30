package Model;

import Exceptions.ExceptionDNI_CIF;
import Exceptions.ExceptionNombre;

public class Persona {
	
	private String dni;
	private String nom;
	private String ape;
	private String adre;
	private String tlf;
	private String mvl;
	
	public Persona(String dni, String nom, String ape, String adre, String tlf, String mvl)throws ExceptionDNI_CIF, ExceptionNombre{
		
		setDni(dni);
		setNom(nom);
		setApe(ape);
		setAdre(adre);
		setTlf(tlf);
		setMvl(mvl);
	}

	public String getDni() {return dni;}
	public String getNom() {return nom;}
	public String getApe() {return ape;}
	public String getAdre() {return adre;}
	public String getTlf() {return tlf;}
	public String getMvl() {return mvl;}
	
	
	public void setDni(String dni) throws ExceptionDNI_CIF {
	
		if(dni.length()==9){
			dni=dni.toUpperCase();
			if(dni.charAt(dni.length()-1)>=65 && dni.charAt(dni.length()-1)<=90){
				this.dni=dni;
			}else{
				throw new ExceptionDNI_CIF("El DNI tiene que terminar con una letra.");
			}
		
		}else{
			throw new ExceptionDNI_CIF("El DNI tiene que tener 9 Caracteres.");
		}
	}

	public void setNom(String nom) throws ExceptionNombre {
		if(nom.length()==0){
			throw new ExceptionNombre("El nombre no puede estar vacio");
		}else{
			this.nom = nom;
		}
	
	}

	public void setApe(String ape) {
		this.ape = ape;
	}

	public void setAdre(String adre) {
		this.adre = adre;
	}

	public void setTlf(String tlf) {
		this.tlf = tlf;
	}

	public void setMvl(String mvl) {
		this.mvl = mvl;
	}
	

}
