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
import DAO.ClienteDAO;
import DAO.EmpleadoDAO;
import DTO.Cliente;
import DTO.Empleado;
import Exceptions.ExceptionCompras;
import Exceptions.ExceptionDNI_CIF;
import Exceptions.ExceptionNcuenta;
import Exceptions.ExceptionNombre;
import Exceptions.ExceptionSalario;
import View.Principal;

public class OpClientes {
	private static Object controler;
	
	public OpClientes(){}
	public OpClientes(ControladorROOT controler){this.controler=controler;}
	public OpClientes(ControladorALMAC controler){this.controler=controler;}
	public OpClientes(ControladorCOMPRA controler){this.controler=controler;}
	public OpClientes(ControladorCONTA controler){this.controler=controler;}
	public OpClientes(ControladorVENTA controler){this.controler=controler;}
	
	public static boolean btnGuardarFormulario(Principal vista){
		boolean resultat=false;
		vista.getFormc().getTfdni().setBackground(Color.WHITE);
		vista.getFormc().getTfname().setBackground(Color.WHITE);
		vista.getFormc().getTfapellido().setBackground(Color.WHITE);
		vista.getFormc().getTfdireccion().setBackground(Color.WHITE);
		vista.getFormc().getTftelefono().setBackground(Color.WHITE);
		vista.getFormc().getTfmovil().setBackground(Color.WHITE);
		vista.getFormc().getTfcompras().setBackground(Color.WHITE);
		vista.getFormc().getTfcuenta().setBackground(Color.WHITE);
		try {
			Cliente c=new Cliente(vista.getFormc().getTfdni().getText(),
									  vista.getFormc().getTfname().getText(),
									  vista.getFormc().getTfapellido().getText(),
									  vista.getFormc().getTfdireccion().getText(),
									  vista.getFormc().getTftelefono().getText(),
									  vista.getFormc().getTfmovil().getText(),
									  Integer.parseInt(vista.getFormc().getTfcompras().getText()),
									  vista.getFormc().getTfcuenta().getText().replace("-",""));
			ClienteDAO cd=new ClienteDAO();
			
			if(cd.create(c)){//insertado correctamente
				JOptionPane.showMessageDialog(vista,"El Cliente ha sido guarado correctamente.");
				//refrescarViewConsultaClientes();
				resultat = true;
			}else{//no se ha insetado en la bbdd
				
			}
		} catch (NumberFormatException e1) {		
			vista.getFormc().getTfcompras().setBackground(Color.RED);
		} catch (ExceptionDNI_CIF e1) {
			vista.getFormc().getTfdni().setBackground(Color.RED);
		} catch (ExceptionNcuenta e1) {
			vista.getFormc().getTfcuenta().setBackground(Color.RED);
		} catch (ExceptionNombre e1) {
			vista.getFormc().getTfname().setBackground(Color.RED);
		}catch (ExceptionCompras e1) {
			vista.getFormc().getTfcompras().setBackground(Color.RED);
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
			String key=vista.getVerTabla().getFormC().getTfdni().getText();
			ClienteDAO p=new ClienteDAO();
			try {////////////
				Cliente c=new Cliente(vista.getVerTabla().getFormC().getTfdni().getText(),
						vista.getVerTabla().getFormC().getTfname().getText(),
						vista.getVerTabla().getFormC().getTfapellido().getText(),
						vista.getVerTabla().getFormC().getTfdireccion().getText(),
						vista.getVerTabla().getFormC().getTftelefono().getText(),
						vista.getVerTabla().getFormC().getTfmovil().getText(),
						Integer.parseInt(vista.getVerTabla().getFormC().getTfcompras().getText()),
						vista.getVerTabla().getFormC().getTfcuenta().getText().replace("-", ""));
				if(p.update(c)){
					System.out.println("modificado correctamente");
					JOptionPane.showMessageDialog(vista,"El Cliente ha sido actualizado correctamente.");
					//refrescarViewModClientes();
					resultat = true;
				}
			} catch (NumberFormatException e1) {		

			} catch (ExceptionDNI_CIF e1) {
				vista.getVerTabla().getFormC().getTfdni().setBackground(Color.RED);
			} catch (ExceptionNombre e1) {
				vista.getVerTabla().getFormC().getTfname().setBackground(Color.RED);
			}catch (ExceptionNcuenta e1) {
				vista.getVerTabla().getFormC().getTfcuenta().setBackground(Color.RED);
			} catch (ExceptionCompras e1) {
				vista.getVerTabla().getFormC().getTfcompras().setBackground(Color.RED);
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
			String key=vista.getVerTabla().getFormC().getTfdni().getText();
			ClienteDAO c=new ClienteDAO();
			try {
				if(c.delete(key)){
					JOptionPane.showMessageDialog(vista,"El Cliente ha sido borrado correctamente.");
					//refrescarViewDeleteClientes();
					resultat = true;
				}
			} catch (HeadlessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			} else {
				// no option
			}
		return resultat;
	}
}
