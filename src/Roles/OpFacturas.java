package Roles;

import java.awt.Color;
import java.awt.HeadlessException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JOptionPane;

import Controler.ControladorALMAC;
import Controler.ControladorCOMPRA;
import Controler.ControladorCONTA;
import Controler.ControladorROOT;
import Controler.ControladorVENTA;
import DAO.CompraDAO;
import DAO.FacturaDAO;
import DAO.ProductoDAO;
import DAO.ProveedorDAO;
import DTO.Compra;
import DTO.Factura;
import DTO.Proveedor;
import Exceptions.ExceptionCantidad;
import Exceptions.ExceptionCompras;
import Exceptions.ExceptionPrecio;
import View.Principal;

public class OpFacturas {
	private static Object controler;
	
	public OpFacturas(){}
	public OpFacturas(ControladorROOT controler){this.controler=controler;}
	public OpFacturas(ControladorALMAC controler){this.controler=controler;}
	public OpFacturas(ControladorCOMPRA controler){this.controler=controler;}
	public OpFacturas(ControladorCONTA controler){this.controler=controler;}
	public OpFacturas(ControladorVENTA controler){this.controler=controler;}
	

	public static boolean btnGuardarFormulario(Principal vista){
		boolean resultat=false;
		System.out.println("Entra btn guardar Facturas");
		vista.getFormFacturas().getTfCantidad().setBackground(Color.WHITE);
		vista.getFormFacturas().getTfId().setBackground(Color.WHITE);
		vista.getFormFacturas().getTfFecha().setBackground(Color.WHITE);			
		try {
			//int id,String cliente, String producto, int cantidad, float precio, boolean pagada, Date fecha
			boolean auxPagada=false;
			if(vista.getFormFacturas().getTfPagada().getText().equalsIgnoreCase("0")){
				auxPagada=false;
			}else if(vista.getFormFacturas().getTfPagada().getText().equalsIgnoreCase("1")){
				auxPagada=true;
			}
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			
			String aux=(vista.getFormFacturas().getPickerDate().getDate().getYear()+1900)+"-"
					  +(vista.getFormFacturas().getPickerDate().getDate().getMonth()+1)+"-"
					  +vista.getFormFacturas().getPickerDate().getDate().getDate();        
			java.util.Date parsed = format.parse(aux);
	        java.sql.Date sql = new java.sql.Date(parsed.getTime());
			Factura v=null;
				  v=new Factura(
						Integer.parseInt(vista.getFormFacturas().getTfId().getText()),
						vista.getFormFacturas().getComboCliente().getSelectedItem().toString(),
						vista.getFormFacturas().getComboProducto().getSelectedItem().toString(),
						Integer.parseInt(vista.getFormFacturas().getTfCantidad().getText()),
						Float.parseFloat(vista.getFormFacturas().getTfPrecio().getText()),
						auxPagada,
						sql
						  );
			
			FacturaDAO vent=new FacturaDAO();
			
			if(vent.create(v)){//insertado correctamente
				JOptionPane.showMessageDialog(vista,"La Factura ha sido guardada correctamente.");
				//refrescarViewConsultaFacturas();
				resultat=true;
			}else{//no se ha insetado en la bbdd
				System.out.println("No se a podido insertar la Factura en la bbdd");
			}
		}catch (HeadlessException e1) {	
			e1.printStackTrace();
		}catch(NumberFormatException e1){
			if(vista.getFormCompras().getTfCantidad().getText().equalsIgnoreCase("")||
			   vista.getFormCompras().getTfCantidad().getText().contains("-")){
				vista.getFormCompras().getTfCantidad().setBackground(Color.RED);
			}
			if(vista.getFormFacturas().getTfId().getText().equalsIgnoreCase("")){
				vista.getFormFacturas().getTfId().setBackground(Color.RED);
			}
			e1.printStackTrace();
		}catch (SQLException e1) {
			JOptionPane.showMessageDialog(vista,e1.getMessage()+"");
			e1.printStackTrace();
		} catch (ParseException e1) {
			e1.printStackTrace();
			vista.getFormFacturas().getTfFecha().setBackground(Color.RED);
		} catch (ExceptionCantidad e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return resultat;
	}
	
	public static boolean btnActualizarFormulario(Principal vista){
		boolean resultat=false;
		System.out.println("boton actualizar Facturas");
		if (JOptionPane.showConfirmDialog(null, "Estas seguro de actualizar el registro en la bade de datos?", "ALERTA",
		        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
		    // yes option
			String key=vista.getVerTabla().getFormFacturas().getTfId().getText();
			FacturaDAO v=new FacturaDAO();
			try {////////////
				//int id,String cliente, String producto, int cantidad, float precio, boolean pagada, Date fecha
				boolean auxPagada;
				if(vista.getVerTabla().getFormFacturas().getTfPagada().getText().equalsIgnoreCase("0")){
					auxPagada=false;
				}else{
					auxPagada=true;
				} 
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		        java.util.Date parsed = format.parse(vista.getVerTabla().getFormFacturas().getTfFecha().getText());
		        java.sql.Date sql = new java.sql.Date(parsed.getTime());
				Factura c=new Factura(
						Integer.parseInt(vista.getVerTabla().getFormFacturas().getTfId().getText()),
						vista.getVerTabla().getFormFacturas().getTfCliente().getText(),
						vista.getVerTabla().getFormFacturas().getTfProducto().getText(),
						Integer.parseInt(vista.getVerTabla().getFormFacturas().getTfCantidad().getText()),
						Float.parseFloat(vista.getVerTabla().getFormFacturas().getTfPrecio().getText()),
						auxPagada,
						sql
						);
				if(v.update(c)){
					System.out.println("modificado correctamente");
					JOptionPane.showMessageDialog(vista,"La Factura ha sido actualizada correctamente.");
					//refrescarViewModFacturas();
					resultat=true;
				}
			} catch (NumberFormatException e1) {		
				
			}catch (HeadlessException e1) {	
				JOptionPane.showMessageDialog(vista,e1.getMessage()+"");
			} catch (SQLException e1) {
				//JOptionPane.showMessageDialog(vista,e1.getMessage()+"");
				e1.printStackTrace();
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ExceptionCantidad e1) {
				// TODO Auto-generated catch block
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
			String key=vista.getVerTabla().getFormFacturas().getTfId().getText();
			FacturaDAO c=new FacturaDAO();
			try {
				if(c.delete(key)){
					JOptionPane.showMessageDialog(vista,"La Factura ha sido borrada correctamente.");
					//refrescarViewDeleteFacturas();
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
