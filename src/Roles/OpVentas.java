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
import DAO.ProductoDAO;
import DAO.VentaDAO;
import DTO.Cliente;
import DTO.Producto;
import DTO.Venta;
import Exceptions.ExceptionCompras;
import Exceptions.ExceptionDNI_CIF;
import Exceptions.ExceptionNcuenta; 
import Exceptions.ExceptionNombre;
import Exceptions.ExceptionStock;
import View.Principal;

public class OpVentas {
	private static Object controler;
	
	public OpVentas(){}
	public OpVentas(ControladorROOT controler){this.controler=controler;}
	public OpVentas(ControladorALMAC controler){this.controler=controler;}
	public OpVentas(ControladorCOMPRA controler){this.controler=controler;}
	public OpVentas(ControladorCONTA controler){this.controler=controler;}
	public OpVentas(ControladorVENTA controler){this.controler=controler;}
	
	//
	//boton añadir en la tabla Ventas
	//1. Saber si hay stock para la cantidad deseada.
	//2. Hacer un insert a la tabla ventas para tener el registro de que se ha producido una venta
	//3. Restar la cantidad de la venta al stock del producto
	//4. Sumar una compra al cliente que le estamos haciendo la venta
	//5. En caso de que no exista la factura ahcer algo para que en facturas salga como pendiente facturar esta venta.
	//
	public static boolean btnGuardarFormulario(Principal vista){
		boolean resultat=false;
		System.out.println("Entra btn guardar ventas");
		vista.getFormVentas().getTfCantidad().setBackground(Color.WHITE);
		vista.getFormVentas().getTfPrecio().setBackground(Color.WHITE);
		try {
			//1
			ProductoDAO produc=new ProductoDAO();
			Producto p=produc.read(vista.getFormVentas().getComboProducto().getSelectedItem().toString());
			if(p.getStock()>=Integer.parseInt(vista.getFormVentas().getTfCantidad().getText())){
				//tenemos suficiente Stock
				//2
				Venta v=null;
				boolean haveFactura=vista.getFormVentas().getComboFactura().getSelectedItem().toString().equalsIgnoreCase("Facturas..");
				boolean haveId=false;
				if(haveId && haveFactura){
					  v=new Venta(
							Integer.parseInt(vista.getFormVentas().getTfId().getText()),
							vista.getFormVentas().getComboCliente().getSelectedItem().toString(),
							vista.getFormVentas().getComboProducto().getSelectedItem().toString(),
							Integer.parseInt(vista.getFormVentas().getTfCantidad().getText()),
							Float.parseFloat(vista.getFormVentas().getTfPrecio().getText()),
							Integer.parseInt(vista.getFormVentas().getComboFactura().getSelectedItem().toString())
							  	);
				}else if(!haveId && !haveFactura){
					 v=new Venta(
							vista.getFormVentas().getComboCliente().getSelectedItem().toString(),
							vista.getFormVentas().getComboProducto().getSelectedItem().toString(),
							Integer.parseInt(vista.getFormVentas().getTfCantidad().getText()),
							Float.parseFloat(vista.getFormVentas().getTfPrecio().getText())
								);
				}else if(haveId && !haveFactura){
					v=new Venta(
							Integer.parseInt(vista.getFormVentas().getTfId().getText()),
							vista.getFormVentas().getComboCliente().getSelectedItem().toString(),
							vista.getFormVentas().getComboProducto().getSelectedItem().toString(),
							Integer.parseInt(vista.getFormVentas().getTfCantidad().getText()),
							Float.parseFloat(vista.getFormVentas().getTfPrecio().getText())
							  );
				}else if(!haveId && haveFactura){
					v=new Venta(
							vista.getFormVentas().getComboCliente().getSelectedItem().toString(),
							vista.getFormVentas().getComboProducto().getSelectedItem().toString(),
							Integer.parseInt(vista.getFormVentas().getTfCantidad().getText()),
							Float.parseFloat(vista.getFormVentas().getTfPrecio().getText()),
							Integer.parseInt(vista.getFormVentas().getComboFactura().getSelectedItem().toString())
							  );
				}
				
				VentaDAO vent=new VentaDAO();					
				if(vent.create(v)){
					//insertado correctamente
					//3
					p.setStock(p.getStock()-Integer.parseInt(vista.getFormVentas().getTfCantidad().getText()));
					if(produc.update(p)){
						//Stock restado correcto
						//4
						ClienteDAO client=new ClienteDAO();
						Cliente c=client.read(vista.getFormVentas().getComboCliente().getSelectedItem().toString());
						c.setCompras(c.getCompras()+1);
						if(client.update(c)){
							//Suma de la compra OK
							//Final correcto
							JOptionPane.showMessageDialog(vista,"La venta ha sido guardada correctamente.");
							//refrescarViewConsultaVentas();
							resultat=true;
						}else{
							//Error en sumar la compra
							System.out.println("Error en sumar la compra");
						}

					}else{
						//Fallo en restar el Stock
						System.out.println("Error en restar el Stock");
					}
					
				}else{
					//no se ha insetado en la bbdd
					System.out.println("No se a podido insertar el producto en la bbdd");
				}
			}else{
				//no tenemos suficiente Stock
				JOptionPane.showMessageDialog(vista,"No hay suficiente Stock de este producto.");
			}
		}catch (HeadlessException e1) {	
			
		}catch(NumberFormatException e1){
			vista.getFormVentas().getTfCantidad().setBackground(Color.RED);
			vista.getFormVentas().getTfPrecio().setBackground(Color.RED);
		}catch (SQLException e1) {
			//JOptionPane.showMessageDialog(vista,e1.getMessage()+"");
			e1.printStackTrace();
		}catch(ExceptionStock e1){
			JOptionPane.showMessageDialog(vista,e1.getMessage());
		}catch(ExceptionCompras e1){
			JOptionPane.showMessageDialog(vista,e1.getMessage());
		}
		return resultat;
	}
	
	public static boolean btnActualizarFormulario(Principal vista){
		boolean resultat=false;
		System.out.println("boton actualizar ventas");
		if (JOptionPane.showConfirmDialog(null, "Estas seguro de actualizar el registro en la bade de datos?", "ALERTA",
		        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
		    // yes option
			//String key=vista.getVerTabla().getFormVentas().getTfId().getText();
			VentaDAO v=new VentaDAO();
			try {////////////
				//int id, String cliente, String producto, int cantidad, float precio, int factura_r
				Venta c=new Venta(
						Integer.parseInt(vista.getVerTabla().getFormVentas().getTfId().getText()),
						vista.getVerTabla().getFormVentas().getTfClient().getText(),
						vista.getVerTabla().getFormVentas().getTfProduct().getText(),
						Integer.parseInt(vista.getVerTabla().getFormVentas().getTfCantidad().getText()),
						Float.parseFloat(vista.getVerTabla().getFormVentas().getTfPrecio().getText()),
						Integer.parseInt(vista.getVerTabla().getFormVentas().getTfFactura().getText())
						);
				if(v.update(c)){
					System.out.println("modificado correctamente");
					JOptionPane.showMessageDialog(vista,"La Venta ha sido actualizada correctamente.");
					//refrescarViewModVentas();
					resultat=true;
				}else{
					JOptionPane.showMessageDialog(vista,"La Venta NO ha sido actualizada correctamente.");
				}
			} catch (NumberFormatException e1) {		
				
			}catch (HeadlessException e1) {	
				JOptionPane.showMessageDialog(vista,e1.getMessage()+"");
			} catch (SQLException e1) {
				//JOptionPane.showMessageDialog(vista,e1.getMessage()+"");
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
			String key=vista.getVerTabla().getFormVentas().getTfId().getText();
			VentaDAO c=new VentaDAO();
			try {
				if(c.delete(key)){
					JOptionPane.showMessageDialog(vista,"La Venta ha sido borrada correctamente.");
					//refrescarViewDeleteVentas();
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
