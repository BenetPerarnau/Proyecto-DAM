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
import DAO.CompraDAO;
import DAO.ProductoDAO;
import DAO.ProveedorDAO;
import DAO.VentaDAO;
import DTO.Cliente;
import DTO.Compra;
import DTO.Producto;
import DTO.Proveedor;
import DTO.Venta;
import Exceptions.ExceptionCompras;
import Exceptions.ExceptionPrecio;
import Exceptions.ExceptionStock;
import View.Principal;

public class OpCompras {
	private static Object controler;
	
	public OpCompras(){}
	public OpCompras(ControladorROOT controler){this.controler=controler;}
	public OpCompras(ControladorALMAC controler){this.controler=controler;}
	public OpCompras(ControladorCOMPRA controler){this.controler=controler;}
	public OpCompras(ControladorCONTA controler){this.controler=controler;}
	public OpCompras(ControladorVENTA controler){this.controler=controler;}
	
	//
	//boton añadir en la tabla Compra
	//
	//1. Generar registro en la tabla compras
	//2. Sumar Stock al producto seleccionado
	//3. Sumar una compra al proveedor que estamos haciendole la compra
	//
	public static boolean btnGuardarFormulario(Principal vista){
		boolean resultat=false;
		System.out.println("Entra btn guardar compras");
		vista.getFormCompras().getTfCantidad().setBackground(Color.WHITE);
		try {
			//int id,int cantidad,String producto, String proveedor
			//int cantidad,String producto, String proveedor
			Compra v=null;
			boolean haveId=!vista.getFormCompras().getTfId().getText().toString().equalsIgnoreCase("");
			if(haveId){
				  v=new Compra(
						Integer.parseInt(vista.getFormCompras().getTfId().getText()),
						Integer.parseInt(vista.getFormCompras().getTfCantidad().getText()),
						vista.getFormCompras().getComboProducto().getSelectedItem().toString(),
						vista.getFormCompras().getComboProveedor().getSelectedItem().toString()
						  	);
			}else if(!haveId){
				 v=new Compra(
						Integer.parseInt(vista.getFormCompras().getTfCantidad().getText()),
						vista.getFormCompras().getComboProducto().getSelectedItem().toString(),
						vista.getFormCompras().getComboProveedor().getSelectedItem().toString()
							);
			}
			
			CompraDAO comp=new CompraDAO();
			ProductoDAO prod=new ProductoDAO();
			ProveedorDAO prov=new ProveedorDAO();
			if(comp.create(v)){
				//Compra insertada correctamente			
				if(prod.updateStock(vista.getFormCompras().getComboProducto().getSelectedItem().toString(), 
						Integer.parseInt(vista.getFormCompras().getTfCantidad().getText()))){
					//Stock del producto Modificado OK
					Proveedor p=prov.read(vista.getFormCompras().getComboProveedor().getSelectedItem().toString());
					p.setCompras(p.getCompras()+1);
					if(prov.update(p)){
						//Suma de una compra al proveedor OK
						JOptionPane.showMessageDialog(vista,"La Compra ha sido guardada correctamente.");
						//refrescarViewConsultaCompras();
						resultat=true;
					}else{
						//Suma de una compra al proveedor FAIL
						System.out.println("Suma de una compra al proveedor FAIL");
					}
				}else{
					//Stock del producto Modificado FAIL
					System.out.println("No se a podido insertar la Compra en la bbdd");
				}
							
			}else{
				//no se ha insetado la compra en la bbdd
				System.out.println("No se a podido insertar la Compra en la bbdd");
			}
		}catch (HeadlessException e1) {	
			
		}catch(NumberFormatException e1){
			vista.getFormCompras().getTfCantidad().setBackground(Color.RED);
		}catch (SQLException e1) {
			//JOptionPane.showMessageDialog(vista,e1.getMessage()+"");
			e1.printStackTrace();
		} catch (ExceptionPrecio e1) {
			JOptionPane.showMessageDialog(vista,e1.getMessage()+"");
			vista.getFormCompras().getTfCantidad().setBackground(Color.RED);
		} catch (ExceptionCompras e1) {
			JOptionPane.showMessageDialog(vista,e1.getMessage()+"");
			e1.printStackTrace();
		}
		return resultat;
	}
	
	public static boolean btnActualizarFormulario(Principal vista){
		boolean resultat=false;
		System.out.println("boton actualizar Compras");
		if (JOptionPane.showConfirmDialog(null, "Estas seguro de actualizar el registro en la bade de datos?", "ALERTA",
		        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
		    // yes option
			String key=vista.getVerTabla().getFormCompras().getTfId().getText();
			CompraDAO v=new CompraDAO();
			try {////////////
				//int id,int cantidad,String producto, String proveedor
				Compra c=new Compra(
						Integer.parseInt(vista.getVerTabla().getFormCompras().getTfId().getText()),
						Integer.parseInt(vista.getVerTabla().getFormCompras().getTfCantidad().getText()),
						vista.getVerTabla().getFormCompras().getTfProducto().getText(),
						vista.getVerTabla().getFormCompras().getTfProveedor().getText()
						);
				if(v.update(c)){
					System.out.println("modificado correctamente");
					JOptionPane.showMessageDialog(vista,"La Compra ha sido actualizada correctamente.");
					//refrescarViewModCompras();
					resultat=true;
				}
			} catch (NumberFormatException e1) {		
				
			}catch (HeadlessException e1) {	
				JOptionPane.showMessageDialog(vista,e1.getMessage()+"");
			} catch (SQLException e1) {
				//JOptionPane.showMessageDialog(vista,e1.getMessage()+"");
				e1.printStackTrace();
			} catch (ExceptionPrecio e1) {
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
			String key=vista.getVerTabla().getFormCompras().getTfId().getText();
			CompraDAO c=new CompraDAO();
			try {
				if(c.delete(key)){
					JOptionPane.showMessageDialog(vista,"La Compra ha sido borrada correctamente.");
					//refrescarViewDeleteCompras();
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
