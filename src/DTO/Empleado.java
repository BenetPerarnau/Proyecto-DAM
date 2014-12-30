package DTO;

import Exceptions.ExceptionDNI_CIF;
import Exceptions.ExceptionNcuenta;
import Exceptions.ExceptionNombre;
import Exceptions.ExceptionSalario;
import Model.Persona;

public class Empleado extends Persona {

	private String puesto;
	private double salario;
	private String cuenta;
	
	public Empleado(String dni, String nom, String ape, 
					String adre,String tlf, String mvl, String puesto, double salario, String cuenta) throws ExceptionDNI_CIF, ExceptionNcuenta, ExceptionNombre, ExceptionSalario {
		
		super(dni, nom, ape, adre, tlf, mvl);
		setPuesto(puesto);
		setSalario(salario);
		setCuenta(cuenta);
	}

	public String getPuesto() {return puesto;}
	public double getSalario() {return salario;}
	public String getCuenta() {return cuenta;}
	public void setPuesto(String puesto) {
		this.puesto = puesto;
	}

	public void setSalario(double salario) throws ExceptionSalario{
		if(salario<0){
			throw new ExceptionSalario("El salario no puede ser un valor negativo.");
		}else{
			this.salario = salario;
		}
		
	}

	public void setCuenta(String cuenta) throws ExceptionNcuenta {
		if(cuenta.length()==24){
			
			this.cuenta=cuenta;
			
		}else{
			throw new ExceptionNcuenta("El número de cuenta esta incompleto.");
		}
		//this.nCuenta = nCuenta;
	
	}
	
}
