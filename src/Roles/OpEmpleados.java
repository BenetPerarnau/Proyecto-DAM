package Roles;

import java.awt.Color;
import java.awt.HeadlessException;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import Controler.ControladorALMAC;
import Controler.ControladorCOMPRA;
import Controler.ControladorCONTA;
import Controler.ControladorROOT;
import Controler.ControladorVENTA;
import DAO.EmpleadoDAO;
import DAO.ProveedorDAO;
import DTO.Empleado;
import DTO.Proveedor;
import Exceptions.ExceptionCompras;
import Exceptions.ExceptionDNI_CIF;
import Exceptions.ExceptionNcuenta;
import Exceptions.ExceptionNombre;
import Exceptions.ExceptionSalario;
import View.Principal;

public class OpEmpleados {
	private static Object controler;
	
	public OpEmpleados(){}
	public OpEmpleados(ControladorROOT controler){this.controler=controler;}
	public OpEmpleados(ControladorALMAC controler){this.controler=controler;}
	public OpEmpleados(ControladorCOMPRA controler){this.controler=controler;}
	public OpEmpleados(ControladorCONTA controler){this.controler=controler;}
	public OpEmpleados(ControladorVENTA controler){this.controler=controler;}
	
	public static boolean btnGuardarFormulario(Principal vista){
		boolean resultat=false;
		  vista.getForme().getTfdni().setBackground(Color.WHITE);
		  vista.getForme().getTfname().setBackground(Color.WHITE);
		  vista.getForme().getTfapellido().setBackground(Color.WHITE);
		  vista.getForme().getTfdireccion().setBackground(Color.WHITE);
		  vista.getForme().getTftelefono().setBackground(Color.WHITE);
		  vista.getForme().getTfmovil().setBackground(Color.WHITE);
		  vista.getForme().getTfpuesto().setBackground(Color.WHITE);
		  vista.getForme().getTfsalario().setBackground(Color.WHITE);
		  vista.getForme().getTfcuenta().setBackground(Color.WHITE);
		try {
			Empleado em=new Empleado(vista.getForme().getTfdni().getText(),
									  vista.getForme().getTfname().getText(),
									  vista.getForme().getTfapellido().getText(),
									  vista.getForme().getTfdireccion().getText(),
									  vista.getForme().getTftelefono().getText(),
									  vista.getForme().getTfmovil().getText(),
									  vista.getForme().getTfpuesto().getText(),
									  Double.parseDouble(vista.getForme().getTfsalario().getText()),
									  vista.getForme().getTfcuenta().getText().replace("-", ""));
			EmpleadoDAO ed=new EmpleadoDAO();
			
			if(ed.create(em)){//insertado correctamente
				JOptionPane.showMessageDialog(vista,"El Empleado ha sido guarado correctamente.");
				//refrescarViewConsultaEmpleados();
				resultat = true;
			}else{//no se ha insetado en la bbdd
				
			}
		} catch (NumberFormatException e1) {		
			vista.getForme().getTfsalario().setBackground(Color.RED);
		} catch (ExceptionSalario e1) {
			vista.getForme().getTfsalario().setBackground(Color.RED);
		} catch (ExceptionDNI_CIF e1) {
			vista.getForme().getTfdni().setBackground(Color.RED);
		} catch (ExceptionNcuenta e1) {
			vista.getForme().getTfcuenta().setBackground(Color.RED);
		} catch (ExceptionNombre e1) {
			vista.getForme().getTfname().setBackground(Color.RED);
		} catch (HeadlessException e1) {	
			
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(vista,e1.getMessage()+"");
		}
		return resultat;
	}
	
	public static boolean btnActualizarFormulario(Principal vista){
		boolean resultat=false;
		System.out.println("boton actualizar");
		if (JOptionPane.showConfirmDialog(null, "Estas seguro de actualizar el registro en la bade de datos?", "ALERTA",
		        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
		    // yes option
			String key=vista.getVerTabla().getFormE().getTfdni().getText();
			EmpleadoDAO p=new EmpleadoDAO();
			try {
				Empleado em=new Empleado(vista.getVerTabla().getFormE().getTfdni().getText(),
						vista.getVerTabla().getFormE().getTfname().getText(),
						vista.getVerTabla().getFormE().getTfapellido().getText(),
						vista.getVerTabla().getFormE().getTfdireccion().getText(),
						vista.getVerTabla().getFormE().getTftelefono().getText(),
						vista.getVerTabla().getFormE().getTfmovil().getText(),
						vista.getVerTabla().getFormE().getTfpuesto().getText(),
						Double.parseDouble(vista.getVerTabla().getFormE().getTfsalario().getText()),
						vista.getVerTabla().getFormE().getTfcuenta().getText().replace("-", ""));
				if(p.update(em)){
					System.out.println("modificado correctamente");
					JOptionPane.showMessageDialog(vista,"El Empleado ha sido actualizado correctamente.");
					//refrescarViewModEmpleados();
					resultat=true;
				}
			} catch (NumberFormatException e1) {		
				vista.getVerTabla().getFormE().getTfsalario().setBackground(Color.RED);
			} catch (ExceptionDNI_CIF e1) {
				vista.getVerTabla().getFormE().getTfdni().setBackground(Color.RED);
			} catch (ExceptionNombre e1) {
				vista.getVerTabla().getFormE().getTfname().setBackground(Color.RED);
			}catch (ExceptionNcuenta e1) {
				vista.getVerTabla().getFormE().getTfcuenta().setBackground(Color.RED);
			} catch (ExceptionSalario e1) {
				vista.getVerTabla().getFormE().getTfsalario().setBackground(Color.RED);
			} catch (HeadlessException e1) {	
				JOptionPane.showMessageDialog(vista,e1.getMessage()+"");
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(vista,e1.getMessage()+"");
			} 
		} else {
		    // no option
			//no fa res
		}
		return resultat;
	}
	public static boolean btnEliminarFormulario(Principal vista){
		boolean resultat=false;
		if (JOptionPane.showConfirmDialog(null, "Estas seguro de eliminar el registro de la bade de datos?", "ALERTA",
		        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
		    // yes option
			String key=vista.getVerTabla().getFormE().getTfdni().getText();
			EmpleadoDAO emp=new EmpleadoDAO();
			try {
				if(emp.delete(key)){
					JOptionPane.showMessageDialog(vista,"El Empleado ha sido borrado correctamente.");
					//refrescarViewDeleteEmpleados();	
					resultat=true;
				}
			} catch (HeadlessException e1) {
				e1.printStackTrace();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} else {
		    // no option
		}
		return resultat;
	}
}
