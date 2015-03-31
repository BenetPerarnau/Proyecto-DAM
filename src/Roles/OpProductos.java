package Roles;

import java.awt.Color;
import java.awt.HeadlessException;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import View.Principal;
import Controler.ControladorALMAC;
import Controler.ControladorCOMPRA;
import Controler.ControladorCONTA;
import Controler.ControladorROOT;
import Controler.ControladorVENTA;
import DAO.ProductoDAO;
import DTO.Producto;
import Exceptions.ExceptionNombre;
import Exceptions.ExceptionPrecio;
import Exceptions.ExceptionStock;

public class OpProductos {

	private static Object controler;
	
	public OpProductos(){}
	public OpProductos(ControladorROOT controler){this.controler=controler;}
	public OpProductos(ControladorALMAC controler){this.controler=controler;}
	public OpProductos(ControladorCOMPRA controler){this.controler=controler;}
	public OpProductos(ControladorCONTA controler){this.controler=controler;}
	public OpProductos(ControladorVENTA controler){this.controler=controler;}
	
	public static boolean btnGuardarFormulario(Principal vista){
		boolean resultat=false;
		System.out.println("Entra");
		vista.getFormProductos().getTfcod().setBackground(Color.WHITE);
		vista.getFormProductos().getTfname().setBackground(Color.WHITE);
		vista.getFormProductos().getTfdes().setBackground(Color.WHITE);
		vista.getFormProductos().getTfprecio().setBackground(Color.WHITE);
		vista.getFormProductos().getTfstock().setBackground(Color.WHITE);
		vista.getFormProductos().getTfproveedor().setBackground(Color.WHITE);
		try {
			Producto c=new Producto(vista.getFormProductos().getTfcod().getText(),
									  vista.getFormProductos().getTfname().getText(),
									  vista.getFormProductos().getTfdes().getText(),
									  Float.parseFloat(vista.getFormProductos().getTfprecio().getText()),
									  Integer.parseInt(vista.getFormProductos().getTfstock().getText()),
									  vista.getFormProductos().getComboProvedores().getSelectedItem().toString());
			ProductoDAO cd=new ProductoDAO();
			
			if(cd.create(c)){//insertado correctamente
				JOptionPane.showMessageDialog(vista,"El Producto ha sido guarado correctamente.");
				//refrescarViewConsultaProductos();
				resultat=true;
			}else{//no se ha insetado en la bbdd
				System.out.println("No se a podido insertar el producto en la bbdd");
			}
		}catch (ExceptionNombre e1) {
			vista.getFormProductos().getTfname().setBackground(Color.RED);
		} catch (HeadlessException e1) {	
			
		}catch(NumberFormatException e1){
			vista.getFormProductos().getTfprecio().setBackground(Color.RED);
			//this.vista.getFormProductos().getTfstock().setBackground(Color.RED);
		}catch (SQLException e1) {
			JOptionPane.showMessageDialog(vista,e1.getMessage()+"");
		} catch (ExceptionStock e1) {
			vista.getFormProductos().getTfstock().setBackground(Color.RED);
			e1.printStackTrace();
		} catch (ExceptionPrecio e1) {
			vista.getFormProductos().getTfprecio().setBackground(Color.RED);
			e1.printStackTrace();
		} 
		return resultat;
	}
	
	public static boolean btnActualizarFormulario(Principal vista){
		boolean resultat=false;
		System.out.println("boton actualizar");
		if (JOptionPane.showConfirmDialog(null, "Estas seguro de actualizar el registro en la bade de datos?", "ALERTA",
		        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
		    // yes option
			String key=vista.getVerTabla().getFormProductos().getTfcod().getText();
			ProductoDAO p=new ProductoDAO();
			try {////////////
				Producto c=new Producto(vista.getVerTabla().getFormProductos().getTfcod().getText(),
						vista.getVerTabla().getFormProductos().getTfname().getText(),
						vista.getVerTabla().getFormProductos().getTfdes().getText(),
						Float.parseFloat(vista.getVerTabla().getFormProductos().getTfprecio().getText()),
						Integer.parseInt(vista.getVerTabla().getFormProductos().getTfstock().getText()),
						vista.getVerTabla().getFormProductos().getTfproveedor().getText());
				if(p.update(c)){
					System.out.println("modificado correctamente");
					JOptionPane.showMessageDialog(vista,"El Producto ha sido actualizado correctamente.");
					//refrescarViewModProductos();
					resultat=true;
				}
			} catch (NumberFormatException e1) {		
				
			}catch (ExceptionNombre e1) {
				vista.getVerTabla().getFormProductos().getTfname().setBackground(Color.RED);
			} catch (HeadlessException e1) {	
				JOptionPane.showMessageDialog(vista,e1.getMessage()+"");
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(vista,e1.getMessage()+"");
			} catch (ExceptionStock e1) {
				vista.getVerTabla().getFormProductos().getTfstock().setBackground(Color.RED);
				e1.printStackTrace();
			} catch (ExceptionPrecio e1) {
				vista.getVerTabla().getFormProductos().getTfprecio().setBackground(Color.RED);
				e1.printStackTrace();
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
			String key=vista.getVerTabla().getFormProductos().getTfcod().getText();
			ProductoDAO c=new ProductoDAO();
			try {
				if(c.delete(key)){
					JOptionPane.showMessageDialog(vista,"El Producto ha sido borrado correctamente.");
					resultat=true;
					//refrescarViewDeleteProductos();
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
