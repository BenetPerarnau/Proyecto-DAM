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
import DAO.ProductoDAO;
import DAO.ProveedorDAO;
import DTO.Producto;
import DTO.Proveedor;
import Exceptions.ExceptionCompras;
import Exceptions.ExceptionDNI_CIF;
import Exceptions.ExceptionNombre;
import Exceptions.ExceptionPrecio;
import Exceptions.ExceptionStock;
import View.Principal;
import View.Formularios.JPFormularioProveedores;

public class OpProveedores {

	
	public static Object controler;
	
	public OpProveedores(){}
	public OpProveedores(ControladorROOT controler){this.controler=controler;}
	public OpProveedores(ControladorALMAC controler){this.controler=controler;}
	public OpProveedores(ControladorCOMPRA controler){this.controler=controler;}
	public OpProveedores(ControladorCONTA controler){this.controler=controler;}
	public OpProveedores(ControladorVENTA controler){this.controler=controler;}
	
	public static boolean btnGuardarFormulario(Principal vista){
		boolean resultat=false;
		  vista.getFormp().getTfcif().setBackground(Color.WHITE);
		  vista.getFormp().getTfname().setBackground(Color.WHITE);
		  vista.getFormp().getTfdireccion().setBackground(Color.WHITE);
		  vista.getFormp().getTftelefono().setBackground(Color.WHITE);
		  vista.getFormp().getTfdescripcion().setBackground(Color.WHITE);
		  vista.getFormp().getTfcompras().setBackground(Color.WHITE);
		try {
			Proveedor p=new Proveedor(vista.getFormp().getTfcif().getText(),
									  vista.getFormp().getTfname().getText(),
									  vista.getFormp().getTfdireccion().getText(),
									  vista.getFormp().getTftelefono().getText(),
									  vista.getFormp().getTfdescripcion().getText(),
									  Integer.parseInt(vista.getFormp().getTfcompras().getText()));
			ProveedorDAO pd=new ProveedorDAO();
			  
			if(pd.create(p)){//insertado correctamente
				JOptionPane.showMessageDialog(vista,"El Proveedor ha sido guarado correctamente.");
				//refrescarViewConsultaProvedores();
				resultat = true;
			}else{//no se ha insetado en la bbdd
				
			}
		} catch (NumberFormatException e1) {		
			vista.getFormp().getTfcompras().setBackground(Color.RED);
		} catch (ExceptionDNI_CIF e1) {
			vista.getFormp().getTfcif().setBackground(Color.RED);
		} catch (ExceptionCompras e1) {
			vista.getFormp().getTfcompras().setBackground(Color.RED);
		} catch (ExceptionNombre e1) {
			vista.getFormp().getTfname().setBackground(Color.RED);
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
			String key=vista.getVerTabla().getFormP().getTfcif().getText();
			ProveedorDAO p=new ProveedorDAO();
			try {
				Proveedor pr=new Proveedor(vista.getVerTabla().getFormP().getTfcif().getText(),
										  vista.getVerTabla().getFormP().getTfname().getText(),
										  vista.getVerTabla().getFormP().getTfdireccion().getText(),
										  vista.getVerTabla().getFormP().getTftelefono().getText(),
										  vista.getVerTabla().getFormP().getTfdescripcion().getText(),
										  Integer.parseInt(vista.getVerTabla().getFormP().getTfcompras().getText()));
				if(p.update(pr)){
					System.out.println("modificado correctamente");
					JOptionPane.showMessageDialog(vista,"El Proveedor ha sido actualizado correctamente.");
					//refrescarViewModProvedores();
					resultat = true;
				}
			} catch (NumberFormatException e1) {		
				vista.getVerTabla().getFormP().getTfcompras().setBackground(Color.RED);
			} catch (ExceptionDNI_CIF e1) {
				vista.getVerTabla().getFormP().getTfcif().setBackground(Color.RED);
			} catch (ExceptionCompras e1) {
				vista.getVerTabla().getFormP().getTfcompras().setBackground(Color.RED);
			} catch (ExceptionNombre e1) {
				vista.getVerTabla().getFormP().getTfname().setBackground(Color.RED);
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
		System.out.println("boton eliminar proveedor");
		if (JOptionPane
				.showConfirmDialog(
						null,
						"Estas seguro de eliminar el registro de la bade de datos?",
						"ALERTA", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			// yes option borrar
			String key = vista.getVerTabla().getFormP().getTfcif().getText();
			ProveedorDAO p = new ProveedorDAO();
			try {
				if (p.delete(key)) {
					JOptionPane.showMessageDialog(vista,
									"El Proveedor ha sido borrado correctamente.");
					
					//refrescarViewDeleteProvedores();//refrescar la pantalla
					resultat=true;
				}
			} catch (HeadlessException e1) {
				e1.printStackTrace();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} else {
			// no borra option
			// no fa res
		}
		return resultat;
	}
	


	
}
