package Controler;

import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.TableColumn;
import javax.swing.tree.DefaultMutableTreeNode;

import Constants.Constant;
import DAO.ClienteDAO;
import DAO.EmpleadoDAO;
import DAO.LogsDAO;
import DAO.ProductoDAO;
import DAO.ProveedorDAO;
import DTO.Cliente;
import DTO.Empleado;
import DTO.Producto;
import DTO.Proveedor;
import Exceptions.ExceptionCompras;
import Exceptions.ExceptionDNI_CIF;
import Exceptions.ExceptionNcuenta;
import Exceptions.ExceptionNombre;
import Exceptions.ExceptionPrecio;
import Exceptions.ExceptionSalario;
import Exceptions.ExceptionStock;
import Model.ConectorBBDD;
import Model.Lectura;
import View.JPFormularioClientes;
import View.JPFormularioEmpleados;
import View.JPFormularioProductos;
import View.JPFormularioProveedores;
import View.JPPie;
import View.JPVertabla;
import View.Principal;

public class ControladorROOT implements ActionListener , TreeSelectionListener, 
									KeyListener, MouseListener, Runnable {
	
	private static int op;//para el Thread
	private static int num_form;//variable para saber en que form estamos
	private static int rol;//varaible que definira las opciones que tendrá el usuario en la pantalla
	private static String modo;//para saber si estamos en Consulta, Borrado o Insertar
	
	private static Principal vista; //container de la aplicación donde se iran mostrando las diferentes ventanas especificas dependiendo de la interacción del usuario
	
	private static String name;
	private static String pass;
	
	public ControladorROOT(Principal vista){
		
		this.vista=vista;
		this.vista.addlisteners(this);
		this.vista.getLog().addListeners(this);
		this.vista.getBienvenida().addListener(this);
				
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource()==this.vista.getLog().getBtnIngresar()){//login implementado en el thread op 1
			
			name=this.vista.getLog().getTxtNombre();
			pass=this.vista.getLog().getTxtPassword();	
			
			this.vista.getLog().setVisible(false);
			this.vista.getCargando().setVisible(true);
			this.vista.getCargando().repaint();
			this.vista.repaint();
			
			op=1;
			ControladorROOT b=this;
			new Thread (b).start();

		}else if(e.getSource()==this.vista.getItem1desp1()){//cerrar sesión
			
			this.vista.getBienvenida().setVisible(false);
			this.vista.getVerTabla().setVisible(false);
			this.vista.getPie().setVisible(false);
			this.vista.getLog().setVisible(true);
			
			
		}else if(e.getSource()==this.vista.getLog().getChckbxNewCheckBox()){ //si se marca el campo guardar password
			
			if(this.vista.getLog().getChckbxNewCheckBox().isSelected()){
				JOptionPane.showMessageDialog(vista, "Tu usuario y password seran recordados en la proxima sesión.");
			}
			
		}else if(e.getSource()==this.vista.getBienvenida().getTree()){//NS
			
			System.out.println("Entra");

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//									OPERACIONES PROVEEDORES
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		}else if(this.vista.getVerTabla().getFormP()!=null && num_form==3 ){		
			System.out.println("OP proveedor");
			//
			//boton de borrar en la tabla proveedores
			//
			if (e.getSource() == this.vista.getVerTabla().getFormP().getBtn()) {
				System.out.println("boton eliminar proveedor");
				if (JOptionPane
						.showConfirmDialog(
								null,
								"Estas seguro de eliminar el registro de la bade de datos?",
								"WARNING", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					// yes option borrar
					String key = this.vista.getVerTabla().getFormP().getTfcif().getText();
					ProveedorDAO p = new ProveedorDAO();
					try {
						if (p.delete(key)) {
							JOptionPane.showMessageDialog(vista,
											"El Proveedor ha sido borrado correctamente.");
							
							refrescarViewDeleteProvedores();//refrescar la pantalla
						}
					} catch (HeadlessException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					// no borra option
					// no fa res
				}
			//
			//boton Actualizar en la tabla proveedores
			//
			}else if(e.getSource()==this.vista.getVerTabla().getFormP().getBtnActualizar()){
				System.out.println("boton actualizar");
				if (JOptionPane.showConfirmDialog(null, "Estas seguro de actualizar el registro en la bade de datos?", "WARNING",
				        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				    // yes option
					String key=this.vista.getVerTabla().getFormP().getTfcif().getText();
					ProveedorDAO p=new ProveedorDAO();
					try {
						Proveedor pr=new Proveedor(this.vista.getVerTabla().getFormP().getTfcif().getText(),
												  this.vista.getVerTabla().getFormP().getTfname().getText(),
												  this.vista.getVerTabla().getFormP().getTfdireccion().getText(),
												  this.vista.getVerTabla().getFormP().getTftelefono().getText(),
												  this.vista.getVerTabla().getFormP().getTfdescripcion().getText(),
												  Integer.parseInt(this.vista.getVerTabla().getFormP().getTfcompras().getText()));
						if(p.update(pr)){
							System.out.println("modificado correctamente");
							JOptionPane.showMessageDialog(vista,"El Proveedor ha sido actualizado correctamente.");
							refrescarViewModProvedores();
						}
					} catch (NumberFormatException e1) {		
						this.vista.getVerTabla().getFormP().getTfcompras().setBackground(Color.RED);
					} catch (ExceptionDNI_CIF e1) {
						this.vista.getVerTabla().getFormP().getTfcif().setBackground(Color.RED);
					} catch (ExceptionCompras e1) {
						this.vista.getVerTabla().getFormP().getTfcompras().setBackground(Color.RED);
					} catch (ExceptionNombre e1) {
						this.vista.getVerTabla().getFormP().getTfname().setBackground(Color.RED);
					} catch (HeadlessException e1) {	
						JOptionPane.showMessageDialog(vista,e1.getMessage()+"");
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(vista,e1.getMessage()+"");
					}
				} else {
				    // no option
					//no fa res
				}
			}//
		//
		//btn save en formulario new Proveedor
		//
		}else if(e.getSource()==this.vista.getFormp().getBtnGuardar()){
			  this.vista.getFormp().getTfcif().setBackground(Color.WHITE);
			  this.vista.getFormp().getTfname().setBackground(Color.WHITE);
			  this.vista.getFormp().getTfdireccion().setBackground(Color.WHITE);
			  this.vista.getFormp().getTftelefono().setBackground(Color.WHITE);
			  this.vista.getFormp().getTfdescripcion().setBackground(Color.WHITE);
			  this.vista.getFormp().getTfcompras().setBackground(Color.WHITE);
			try {
				Proveedor p=new Proveedor(this.vista.getFormp().getTfcif().getText(),
										  this.vista.getFormp().getTfname().getText(),
										  this.vista.getFormp().getTfdireccion().getText(),
										  this.vista.getFormp().getTftelefono().getText(),
										  this.vista.getFormp().getTfdescripcion().getText(),
										  Integer.parseInt(this.vista.getFormp().getTfcompras().getText()));
				ProveedorDAO pd=new ProveedorDAO();
				  
				if(pd.create(p)){//insertado correctamente
					JOptionPane.showMessageDialog(vista,"El Proveedor ha sido guarado correctamente.");
					refrescarViewConsultaProvedores();
				}else{//no se ha insetado en la bbdd
					
				}
			} catch (NumberFormatException e1) {		
				this.vista.getFormp().getTfcompras().setBackground(Color.RED);
			} catch (ExceptionDNI_CIF e1) {
				this.vista.getFormp().getTfcif().setBackground(Color.RED);
			} catch (ExceptionCompras e1) {
				this.vista.getFormp().getTfcompras().setBackground(Color.RED);
			} catch (ExceptionNombre e1) {
				this.vista.getFormp().getTfname().setBackground(Color.RED);
			} catch (HeadlessException e1) {	
				
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(vista,e1.getMessage()+"");
			}
					
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//								OPERACIONES EMPLEADOS
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		}else if(this.vista.getVerTabla().getFormE()!=null && num_form==2 ){
			System.out.println("boton en carptera empleados");
			//
			//boton de borrar en la tabla Empleados
			//
			if(e.getSource()==this.vista.getVerTabla().getFormE().getBtn()){
			if (JOptionPane.showConfirmDialog(null, "Estas seguro de eliminar el registro de la bade de datos?", "WARNING",
			        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			    // yes option
				String key=this.vista.getVerTabla().getFormE().getTfdni().getText();
				EmpleadoDAO emp=new EmpleadoDAO();
				try {
					if(emp.delete(key)){
						JOptionPane.showMessageDialog(vista,"El Empleado ha sido borrado correctamente.");
						refrescarViewDeleteEmpleados();					
					}
				} catch (HeadlessException e1) {
					e1.printStackTrace();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			} else {
			    // no option
			}
			//
			//actualizar tabla Empleados
			//
			}else if(e.getSource()==this.vista.getVerTabla().getFormE().getBtnActualizar()){
			System.out.println("boton actualizar");
			if (JOptionPane.showConfirmDialog(null, "Estas seguro de actualizar el registro en la bade de datos?", "WARNING",
			        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			    // yes option
				String key=this.vista.getVerTabla().getFormE().getTfdni().getText();
				EmpleadoDAO p=new EmpleadoDAO();
				try {
					Empleado em=new Empleado(this.vista.getVerTabla().getFormE().getTfdni().getText(),
							this.vista.getVerTabla().getFormE().getTfname().getText(),
							this.vista.getVerTabla().getFormE().getTfapellido().getText(),
							this.vista.getVerTabla().getFormE().getTfdireccion().getText(),
							this.vista.getVerTabla().getFormE().getTftelefono().getText(),
							this.vista.getVerTabla().getFormE().getTfmovil().getText(),
							this.vista.getVerTabla().getFormE().getTfpuesto().getText(),
							Double.parseDouble(this.vista.getVerTabla().getFormE().getTfsalario().getText()),
							this.vista.getVerTabla().getFormE().getTfcuenta().getText());
					if(p.update(em)){
						System.out.println("modificado correctamente");
						JOptionPane.showMessageDialog(vista,"El Empleado ha sido actualizado correctamente.");
						refrescarViewModEmpleados();
					}
				} catch (NumberFormatException e1) {		
					this.vista.getVerTabla().getFormE().getTfsalario().setBackground(Color.RED);
				} catch (ExceptionDNI_CIF e1) {
					this.vista.getVerTabla().getFormE().getTfdni().setBackground(Color.RED);
				} catch (ExceptionNombre e1) {
					this.vista.getVerTabla().getFormE().getTfname().setBackground(Color.RED);
				}catch (ExceptionNcuenta e1) {
					this.vista.getVerTabla().getFormE().getTfcuenta().setBackground(Color.RED);
				} catch (ExceptionSalario e1) {
					this.vista.getVerTabla().getFormE().getTfsalario().setBackground(Color.RED);
				} catch (HeadlessException e1) {	
					JOptionPane.showMessageDialog(vista,e1.getMessage()+"");
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(vista,e1.getMessage()+"");
				} 
			} else {
			    // no option
				//no fa res
			}
			}//
		//
		////boton añadir en la tabla empleados
		//
		}else if(e.getSource()==this.vista.getForme().getBtnGuardar()){
			  this.vista.getForme().getTfdni().setBackground(Color.WHITE);
			  this.vista.getForme().getTfname().setBackground(Color.WHITE);
			  this.vista.getForme().getTfapellido().setBackground(Color.WHITE);
			  this.vista.getForme().getTfdireccion().setBackground(Color.WHITE);
			  this.vista.getForme().getTftelefono().setBackground(Color.WHITE);
			  this.vista.getForme().getTfmovil().setBackground(Color.WHITE);
			  this.vista.getForme().getTfpuesto().setBackground(Color.WHITE);
			  this.vista.getForme().getTfsalario().setBackground(Color.WHITE);
			  this.vista.getForme().getTfcuenta().setBackground(Color.WHITE);
			try {
				Empleado em=new Empleado(this.vista.getForme().getTfdni().getText(),
										  this.vista.getForme().getTfname().getText(),
										  this.vista.getForme().getTfapellido().getText(),
										  this.vista.getForme().getTfdireccion().getText(),
										  this.vista.getForme().getTftelefono().getText(),
										  this.vista.getForme().getTfmovil().getText(),
										  this.vista.getForme().getTfpuesto().getText(),
										  Double.parseDouble(this.vista.getForme().getTfsalario().getText()),
										  this.vista.getForme().getTfcuenta().getText());
				EmpleadoDAO ed=new EmpleadoDAO();
				
				if(ed.create(em)){//insertado correctamente
					JOptionPane.showMessageDialog(vista,"El Empleado ha sido guarado correctamente.");
					refrescarViewConsultaEmpleados();
				}else{//no se ha insetado en la bbdd
					
				}
			} catch (NumberFormatException e1) {		
				this.vista.getForme().getTfsalario().setBackground(Color.RED);
			} catch (ExceptionSalario e1) {
				this.vista.getForme().getTfsalario().setBackground(Color.RED);
			} catch (ExceptionDNI_CIF e1) {
				this.vista.getForme().getTfdni().setBackground(Color.RED);
			} catch (ExceptionNcuenta e1) {
				this.vista.getForme().getTfcuenta().setBackground(Color.RED);
			} catch (ExceptionNombre e1) {
				this.vista.getForme().getTfname().setBackground(Color.RED);
			} catch (HeadlessException e1) {	
				
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(vista,e1.getMessage()+"");
			}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
								//OPERACIONES CLIENTES
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		}else if(this.vista.getVerTabla().getFormC()!=null && num_form==1){
			//
			//boton de borrar en la tabla Clientes
			//
			if(e.getSource()==this.vista.getVerTabla().getFormC().getBtn()){
				if (JOptionPane.showConfirmDialog(null, "Estas seguro de eliminar el registro de la bade de datos?", "WARNING",
			        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			    // yes option
				String key=this.vista.getVerTabla().getFormC().getTfdni().getText();
				ClienteDAO c=new ClienteDAO();
				try {
					if(c.delete(key)){
						JOptionPane.showMessageDialog(vista,"El Cliente ha sido borrado correctamente.");
						refrescarViewDeleteClientes();
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
		    //
			//actualizar tabla Clientes
			//	
			}else if(e.getSource()==this.vista.getVerTabla().getFormC().getBtnActualizar()){
			System.out.println("boton actualizar");
			if (JOptionPane.showConfirmDialog(null, "Estas seguro de actualizar el registro en la bade de datos?", "WARNING",
			        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			    // yes option
				String key=this.vista.getVerTabla().getFormC().getTfdni().getText();
				ClienteDAO p=new ClienteDAO();
				try {////////////
					Cliente c=new Cliente(this.vista.getVerTabla().getFormC().getTfdni().getText(),
							this.vista.getVerTabla().getFormC().getTfname().getText(),
							this.vista.getVerTabla().getFormC().getTfapellido().getText(),
							this.vista.getVerTabla().getFormC().getTfdireccion().getText(),
							this.vista.getVerTabla().getFormC().getTftelefono().getText(),
							this.vista.getVerTabla().getFormC().getTfmovil().getText(),
							Integer.parseInt(this.vista.getVerTabla().getFormC().getTfcompras().getText()),
							this.vista.getVerTabla().getFormC().getTfcuenta().getText());
					if(p.update(c)){
						System.out.println("modificado correctamente");
						JOptionPane.showMessageDialog(vista,"El Cliente ha sido actualizado correctamente.");
						refrescarViewModClientes();
					}
				} catch (NumberFormatException e1) {		

				} catch (ExceptionDNI_CIF e1) {
					this.vista.getVerTabla().getFormC().getTfdni().setBackground(Color.RED);
				} catch (ExceptionNombre e1) {
					this.vista.getVerTabla().getFormC().getTfname().setBackground(Color.RED);
				}catch (ExceptionNcuenta e1) {
					this.vista.getVerTabla().getFormC().getTfcuenta().setBackground(Color.RED);
				} catch (ExceptionCompras e1) {
					this.vista.getVerTabla().getFormC().getTfcompras().setBackground(Color.RED);
				} catch (HeadlessException e1) {	
					JOptionPane.showMessageDialog(vista,e1.getMessage()+"");
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(vista,e1.getMessage()+"");
				} 
			} else {
			    // no option
				//no fa res
			}
			}
		//
		//boton añadir en la tabla Clientes
		//
		}else if(e.getSource()==this.vista.getFormc().getBtnGuardar()){
			this.vista.getFormc().getTfdni().setBackground(Color.WHITE);
			this.vista.getFormc().getTfname().setBackground(Color.WHITE);
			this.vista.getFormc().getTfapellido().setBackground(Color.WHITE);
			this.vista.getFormc().getTfdireccion().setBackground(Color.WHITE);
			this.vista.getFormc().getTftelefono().setBackground(Color.WHITE);
			this.vista.getFormc().getTfmovil().setBackground(Color.WHITE);
			this.vista.getFormc().getTfcompras().setBackground(Color.WHITE);
			this.vista.getFormc().getTfcuenta().setBackground(Color.WHITE);
			try {
				Cliente c=new Cliente(this.vista.getFormc().getTfdni().getText(),
										  this.vista.getFormc().getTfname().getText(),
										  this.vista.getFormc().getTfapellido().getText(),
										  this.vista.getFormc().getTfdireccion().getText(),
										  this.vista.getFormc().getTftelefono().getText(),
										  this.vista.getFormc().getTfmovil().getText(),
										  Integer.parseInt(this.vista.getFormc().getTfcompras().getText()),
										  this.vista.getFormc().getTfcuenta().getText());
				ClienteDAO cd=new ClienteDAO();
				
				if(cd.create(c)){//insertado correctamente
					JOptionPane.showMessageDialog(vista,"El Cliente ha sido guarado correctamente.");
					refrescarViewConsultaClientes();
				}else{//no se ha insetado en la bbdd
					
				}
			} catch (NumberFormatException e1) {		
				this.vista.getFormc().getTfcompras().setBackground(Color.RED);
			} catch (ExceptionDNI_CIF e1) {
				this.vista.getFormc().getTfdni().setBackground(Color.RED);
			} catch (ExceptionNcuenta e1) {
				this.vista.getFormc().getTfcuenta().setBackground(Color.RED);
			} catch (ExceptionNombre e1) {
				this.vista.getFormc().getTfname().setBackground(Color.RED);
			}catch (ExceptionCompras e1) {
				this.vista.getFormc().getTfcompras().setBackground(Color.RED);
			} catch (HeadlessException e1) {	
				
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(vista,e1.getMessage()+"");
			}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//									OPERACIONES PRODUCTOS
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		}else if(this.vista.getVerTabla().getFormProductos()!=null && num_form==4){
			//
			//boton de borrar en la tabla Productos
			//
			if(e.getSource()==this.vista.getVerTabla().getFormProductos().getBtn()){
				if (JOptionPane.showConfirmDialog(null, "Estas seguro de eliminar el registro de la bade de datos?", "WARNING",
			        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			    // yes option
				String key=this.vista.getVerTabla().getFormProductos().getTfcod().getText();
				ProductoDAO c=new ProductoDAO();
				try {
					if(c.delete(key)){
						JOptionPane.showMessageDialog(vista,"El Producto ha sido borrado correctamente.");
						refrescarViewDeleteProductos();
					}
				} catch (HeadlessException e1) {
					e1.printStackTrace();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				} else {
					// no option
				}
		    //
			//actualizar tabla Productos
			//	
			}else if(e.getSource()==this.vista.getVerTabla().getFormProductos().getBtnActualizar()){
			System.out.println("boton actualizar");
			if (JOptionPane.showConfirmDialog(null, "Estas seguro de actualizar el registro en la bade de datos?", "WARNING",
			        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			    // yes option
				String key=this.vista.getVerTabla().getFormProductos().getTfcod().getText();
				ProductoDAO p=new ProductoDAO();
				try {////////////
					Producto c=new Producto(this.vista.getVerTabla().getFormProductos().getTfcod().getText(),
							this.vista.getVerTabla().getFormProductos().getTfname().getText(),
							this.vista.getVerTabla().getFormProductos().getTfdes().getText(),
							Float.parseFloat(this.vista.getVerTabla().getFormProductos().getTfprecio().getText()),
							Integer.parseInt(this.vista.getVerTabla().getFormProductos().getTfstock().getText()),
							this.vista.getVerTabla().getFormProductos().getTfproveedor().getText());
					if(p.update(c)){
						System.out.println("modificado correctamente");
						JOptionPane.showMessageDialog(vista,"El Cliente ha sido actualizado correctamente.");
						refrescarViewModProductos();
					}
				} catch (NumberFormatException e1) {		
					
				}catch (ExceptionNombre e1) {
					this.vista.getVerTabla().getFormProductos().getTfname().setBackground(Color.RED);
				} catch (HeadlessException e1) {	
					JOptionPane.showMessageDialog(vista,e1.getMessage()+"");
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(vista,e1.getMessage()+"");
				} catch (ExceptionStock e1) {
					this.vista.getVerTabla().getFormProductos().getTfstock().setBackground(Color.RED);
					e1.printStackTrace();
				} catch (ExceptionPrecio e1) {
					this.vista.getVerTabla().getFormProductos().getTfprecio().setBackground(Color.RED);
					e1.printStackTrace();
				} 
			} else {
			    // no option
				//no fa res
			}
			}
		//
		//boton añadir en la tabla Productos
		//
		}else if(e.getSource()==this.vista.getFormProductos().getBtnGuardar()){
			System.out.println("Entra");
			this.vista.getFormProductos().getTfcod().setBackground(Color.WHITE);
			this.vista.getFormProductos().getTfname().setBackground(Color.WHITE);
			this.vista.getFormProductos().getTfdes().setBackground(Color.WHITE);
			this.vista.getFormProductos().getTfprecio().setBackground(Color.WHITE);
			this.vista.getFormProductos().getTfstock().setBackground(Color.WHITE);
			this.vista.getFormProductos().getTfproveedor().setBackground(Color.WHITE);
			try {
				Producto c=new Producto(this.vista.getFormProductos().getTfcod().getText(),
										  this.vista.getFormProductos().getTfname().getText(),
										  this.vista.getFormProductos().getTfdes().getText(),
										  Float.parseFloat(this.vista.getFormProductos().getTfprecio().getText()),
										  Integer.parseInt(this.vista.getFormProductos().getTfstock().getText()),
										  this.vista.getFormProductos().getTfproveedor().getText());
				ProductoDAO cd=new ProductoDAO();
				
				if(cd.create(c)){//insertado correctamente
					JOptionPane.showMessageDialog(vista,"El Producto ha sido guarado correctamente.");
					refrescarViewConsultaProductos();
				}else{//no se ha insetado en la bbdd
					System.out.println("No se a podido insertar el producto en la bbdd");
				}
			}catch (ExceptionNombre e1) {
				this.vista.getFormProductos().getTfname().setBackground(Color.RED);
			} catch (HeadlessException e1) {	
				
			}catch(NumberFormatException e1){
				this.vista.getFormProductos().getTfprecio().setBackground(Color.RED);
				this.vista.getFormProductos().getTfstock().setBackground(Color.RED);
			}catch (SQLException e1) {
				JOptionPane.showMessageDialog(vista,e1.getMessage()+"");
			} catch (ExceptionStock e1) {
				this.vista.getFormProductos().getTfstock().setBackground(Color.RED);
				e1.printStackTrace();
			} catch (ExceptionPrecio e1) {
				this.vista.getFormProductos().getTfprecio().setBackground(Color.RED);
				e1.printStackTrace();
			} 
		}
		
		
	}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//			ACCIONES QUE SE REALIZAN CUANDO SE PULSA EN EL ARBOL (panel de la izquierda)
//
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void valueChanged(TreeSelectionEvent se) {
		JTree tree = (JTree) se.getSource();
	    DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
	    String selectedNodeName = selectedNode.toString();
	    
	    if (selectedNode.isLeaf()) {//si no es carpeta
	    	
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	    				// ARBOL EMPLEADOS
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	    	
	      if(selectedNode.toString().equals("Crear Trabajador")){
	    	  modo="I";
	    	  num_form=2;
	    	  /* pq esto no va?
	    	  //this.vista.getVerTabla().setVisible(false);
	    	  this.vista.remove(this.vista.getVerTabla());
	    	  this.vista.getForme().setVisible(true);
	    	  this.vista.paintAll(this.vista.getGraphics());
	    	  this.vista.repaint();*/
	    	  this.vista.remove(this.vista.getFormc());
	    	  this.vista.remove(this.vista.getForme());
	    	  this.vista.remove(this.vista.getFormp());
	    	  this.vista.remove(this.vista.getFormProductos());
	    	  this.vista.remove(this.vista.getVerTabla());//limpiar la ventana antes de mostrar otra
	    	  JPFormularioEmpleados a=new JPFormularioEmpleados();
	    	  this.vista.setForme(a);
	    	  this.vista.getForme().getBtnGuardar().setVisible(true);
	    	  this.vista.getForme().addListeners(this);
	    	  this.vista.repaint();
	    	  
	      }else if(selectedNode.toString().equals("Consultar Trabajador")){
	    	  refrescarViewConsultaEmpleados();
	    	    	     	  
	      }else if(selectedNode.toString().equals("Borrar Trabajador")){ 
	    	 refrescarViewDeleteEmpleados();
	    	  
	      }else if(selectedNode.toString().equals("Modificar Trabajador")){ 
	    	  refrescarViewModEmpleados();
	    	
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	    	  				// ARBOL CLIENTE
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////	    	  
	    	  
	      }else if(selectedNode.toString().equals("Crear Cliente")){
		    	  modo="I";
		    	  num_form=1;
		    	  /* pq esto no va?
		    	  //this.vista.getVerTabla().setVisible(false);
		    	  this.vista.remove(this.vista.getVerTabla());
		    	  this.vista.getForme().setVisible(true);
		    	  this.vista.paintAll(this.vista.getGraphics());
		    	  this.vista.repaint();*/
		    	  this.vista.remove(this.vista.getFormc());
		    	  this.vista.remove(this.vista.getForme());
		    	  this.vista.remove(this.vista.getFormp());
		    	  this.vista.remove(this.vista.getFormProductos());
		    	  this.vista.remove(this.vista.getVerTabla());//limpiar la ventana antes de mostrar otra
		    	  JPFormularioClientes a=new JPFormularioClientes();
		    	  this.vista.setFormc(a);
		    	  this.vista.getFormc().getBtnGuardar().setVisible(true);
		    	  this.vista.getFormc().addListeners(this);
		    	  this.vista.repaint();
		    	  
		  }else if(selectedNode.toString().equals("Consultar Cliente")){
	    	  refrescarViewConsultaClientes();
	    	  
	      }else if(selectedNode.toString().equals("Borrar Cliente")){	    	  
	    	 refrescarViewDeleteClientes();
	    	  
	      }else if(selectedNode.toString().equals("Modificar Cliente")){	    	  
	    	  refrescarViewModClientes();

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//						    	  ARBOL PROVEEDOR
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////	    	  
	    	  
	      }else if(selectedNode.toString().equals("Crear Proveedor")){
	    	  modo="I";
	    	  num_form=3;
	    	  /* pq esto no va?
	    	  //this.vista.getVerTabla().setVisible(false);
	    	  this.vista.remove(this.vista.getVerTabla());
	    	  this.vista.getForme().setVisible(true);
	    	  this.vista.paintAll(this.vista.getGraphics());
	    	  this.vista.repaint();*/
	    	  this.vista.remove(this.vista.getFormc());
	    	  this.vista.remove(this.vista.getForme());
	    	  this.vista.remove(this.vista.getFormp());
	    	  this.vista.remove(this.vista.getFormProductos());
	    	  this.vista.remove(this.vista.getVerTabla());//limpiar la ventana antes de mostrar otra
	    	  JPFormularioProveedores a=new JPFormularioProveedores();
	    	  this.vista.setFormp(a);
	    	  this.vista.getFormp().getBtnGuardar().setVisible(true);
	    	  this.vista.getFormp().addListeners(this);
	    	  this.vista.repaint();
	    	  
	      }else if(selectedNode.toString().equals("Consultar Proveedor")){
	    	  refrescarViewConsultaProvedores();
	    	  
	      }else if(selectedNode.toString().equals("Borrar Proveedor")){

	    	  refrescarViewDeleteProvedores();
	    	  
	      }else if(selectedNode.toString().equals("Modificar Proveedor")){
	    	  refrescarViewModProvedores();
	    	  
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	    	  					// ARBOL PRODUCTO
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	    	  
	      }else if(selectedNode.toString().equals("Crear Producto")){
	    	  System.out.println("Crear Producto.");
	    	  modo="I";
	    	  num_form=4;
	    	  this.vista.remove(this.vista.getFormc());
	    	  this.vista.remove(this.vista.getForme());
	    	  this.vista.remove(this.vista.getFormp());
	    	  this.vista.remove(this.vista.getFormProductos());
	    	  this.vista.remove(this.vista.getVerTabla());//limpiar la ventana antes de mostrar otra
	    	  JPFormularioProductos a=new JPFormularioProductos();
	    	  this.vista.setFormProductos(a);
	    	  this.vista.getFormProductos().getBtnGuardar().setVisible(true);
	    	  this.vista.getFormProductos().addListeners(this);
	    	  this.vista.repaint();
	        	 	  
	      }else if(selectedNode.toString().equals("Consultar Producto")){
	    	 refrescarViewConsultaProductos();
	    	  
	    	  
	      }else if(selectedNode.toString().equals("Borrar Producto")){
	    	  refrescarViewDeleteProductos();
	    	  
	      }else if(selectedNode.toString().equals("Modificar Producto")){
	    	 refrescarViewModProductos();
	    	  
	      }
	      
	      
	    }else{//es carpeta
			System.out.println(selectedNode.toString());
			this.vista.remove(this.vista.getFormc());
			this.vista.remove(this.vista.getForme());
			this.vista.remove(this.vista.getFormp());
			this.vista.remove(this.vista.getFormProductos());
			this.vista.remove(this.vista.getVerTabla());
			this.vista.getVerTabla().setVisible(false);
			this.vista.repaint();
	    }
	  }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//					ACCIONES QUE SE REALIZARA EL TECLADO
//
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public void keyTyped(KeyEvent e) {//primera vez presionada
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {//mientras se mantiene la tecla presionada
		
		if(e.getKeyChar()==KeyEvent.VK_ENTER){
			System.out.println("Tecla enter presionada");
			name=this.vista.getLog().getTxtNombre();
			pass=this.vista.getLog().getTxtPassword();	
			
			this.vista.getLog().setVisible(false);
			this.vista.getCargando().setVisible(true);
			this.vista.getCargando().repaint();
			this.vista.repaint();
			
			op=1;
			ControladorROOT b=this;
			new Thread (b).start();
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {//cuando se suelta la tecla
		// TODO Auto-generated method stub
		
	}
	
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//				ACCIONES QUE SE REALIZARAN USANDO HILOS
//
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////


	@Override
	public void run() {
		// TODO Auto-generated method stub
		switch(op){
		case 1:
			this.vista.repaint();
			try {
				Thread.sleep(Constant.SLEEP_THREAD_LOGIN);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}			
			try {
				LogsDAO log = new LogsDAO();
				if(log.isUserSystem(name, pass)){
					if(this.vista.getLog().getChckbxNewCheckBox().isSelected()){
						Lectura.saveFastLogin(name, pass);
					}else{
						Lectura.removeFastLogin();
					}
					this.vista.getCargando().setVisible(false);
					this.vista.getBienvenida().setVisible(true);
					this.vista.getPie().setVisible(true);
					this.vista.getPie().setStatusUser(name);
					
					rol=log.getRolUser(name, pass);
					//dependiendo del rol que sea cargará un controlador u otro
					switch(rol){
					case 1://Ventas
						System.out.println("Rol => Ventas");
						break;
					case 2://Compras
						System.out.println("Rol => Compras");
						break;
					case 3://Contabilidad
						System.out.println("Rol => CONTABILIDAD");
						break;
					case 4://Almacén
						System.out.println("Rol => ALMACÉN");
						break;
						default:
							System.out.println("Rol => ROOT");
							break;
					}
					JOptionPane.showMessageDialog(vista, "Bienvenido a BAP ERP. Uer{"+name+" "+pass+" "+rol+"}");
					
				}else{
					this.vista.getCargando().setVisible(false);
					this.vista.getLog().setVisible(true);
					JOptionPane.showMessageDialog(vista, "El usuario o contraseña no existen.");
				}
			} catch (SQLException e) {
				e.printStackTrace();
				this.vista.getCargando().setVisible(false);
				this.vista.getLog().setVisible(true);
				JOptionPane.showMessageDialog(vista, "Problema al conectarse al servidor.");
			}
			break;
		}
	}


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//			ACCIONES QUE SE REALIZAN CON EL MOUSE
//
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		int row = (this.vista.getVerTabla().getTable().rowAtPoint(e.getPoint()))+1;//saber que fila a seleccionado
        //int column = (this.vista.getVerTabla().getTable().columnAtPoint(e.getPoint()))+1;//saber que columna
        
        int columnt=this.vista.getVerTabla().getTable().getColumnCount();//contar columnas
        //int rowt=this.vista.getVerTabla().getTable().getRowCount();//contar filas
        
        //guardar los valores de la fila para cargarlos en una plantilla
        String [] values=new String[columnt];   
        for(int i=0; i<columnt; i++){
        values[i]=this.vista.getVerTabla().getTable().getValueAt(row-1, i)+"";
        }
        switch(num_form){
        case 1:
        	this.vista.getVerTabla().getFormC().rellenarTodo(values);
            if(modo.equals("D")){
            	this.vista.getVerTabla().getFormC().desactivar();
            	this.vista.getVerTabla().getFormC().getBtn().setVisible(true);
        		this.vista.getVerTabla().getFormC().addListeners(this);
            }else if(modo.equals("M")){
            	this.vista.getVerTabla().getFormC().activar();
            	this.vista.getVerTabla().getFormC().getBtnActualizar().setVisible(true);
            	this.vista.getVerTabla().getFormC().addListeners(this);
            	System.out.println("modo m clientes");
            }else if(modo.equals("C")){
            	this.vista.getVerTabla().getFormC().desactivar();
            }
        	break;
        case 2:
        	this.vista.getVerTabla().getFormE().rellenarTodo(values);
        	if(modo.equals("D")){
        		this.vista.getVerTabla().getFormE().desactivar();
        		this.vista.getVerTabla().getFormE().getBtn().setVisible(true);
        		this.vista.getVerTabla().getFormE().addListeners(this);
            }else if(modo.equals("M")){
            	this.vista.getVerTabla().getFormE().activar();
            	this.vista.getVerTabla().getFormE().getBtnActualizar().setVisible(true);
            	this.vista.getVerTabla().getFormE().addListeners(this);
            	System.out.println("modo m empleados");
            }else if(modo.equals("C")){
            	this.vista.getVerTabla().getFormE().desactivar();
            }
        	break;
        case 3:
        	this.vista.getVerTabla().getFormP().rellenarTodo(values);
        	if(modo.equals("D")){//eliminar
        		this.vista.getVerTabla().getFormP().desactivar();
        		this.vista.getVerTabla().getFormP().getBtn().setVisible(true);
        		this.vista.getVerTabla().getFormP().addListeners(this);
            }else if(modo.equals("M")){//modificar
            	this.vista.getVerTabla().getFormP().activar();
            	this.vista.getVerTabla().getFormP().getBtnActualizar().setVisible(true);
            	this.vista.getVerTabla().getFormP().addListeners(this);
            	System.out.println("modo m prove");
            }else if(modo.equals("C")){//consultar
            	this.vista.getVerTabla().getFormP().desactivar();
            }
        	break;
        case 4:
        	this.vista.getVerTabla().getFormProductos().rellenarTodo(values);
        	if(modo.equals("D")){
        		this.vista.getVerTabla().getFormProductos().desactivar();
        		this.vista.getVerTabla().getFormProductos().getBtn().setVisible(true);
        		this.vista.getVerTabla().getFormProductos().addListeners(this);
        	}else if(modo.equals("M")){
        		this.vista.getVerTabla().getFormProductos().activar();
            	this.vista.getVerTabla().getFormProductos().getBtnActualizar().setVisible(true);
            	this.vista.getVerTabla().getFormProductos().addListeners(this);
        	}else if(modo.equals("C")){
        		this.vista.getVerTabla().getFormProductos().desactivar();
        	}
        	break;
        }

        this.vista.getVerTabla().repaint();
        this.vista.repaint();
        
        //String aux = this.vista.getVerTabla().getTable().getValueAt(row-1, column-1)+"";
        //System.out.println("Fila "+row+" Columna "+column+" Valor => "+aux+" Total columnas => "+columnt+" Total filas => "+rowt);
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//				ACCIONES QUE SE REALIZAN EN DIFERENTES PARTES
//
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// REFERENTE A PROVEEDORES
	//
	public void refrescarViewDeleteProvedores(){
	  modo="D";
  	  num_form=3;
  	  ProveedorDAO emple=new ProveedorDAO();
  	  String [] titulos=emple.consultaTitulosTablaToArray();
  	  Object[][] array=emple.consultaDatosTablaToArray();
  	  
  	  JPVertabla a=new JPVertabla(array,titulos);
  	  this.vista.remove(this.vista.getFormc());
  	  this.vista.remove(this.vista.getForme());
  	  this.vista.remove(this.vista.getFormp());
  	this.vista.remove(this.vista.getFormProductos());
  	  this.vista.remove(this.vista.getVerTabla());
  	  this.vista.setVerTabla(a);
  	  this.vista.getVerTabla().addlistenersToTable(this);
	}
	public void refrescarViewModProvedores(){
	  modo="M";
   	  num_form=3;
   	  ProveedorDAO emple=new ProveedorDAO();
   	  String [] titulos=emple.consultaTitulosTablaToArray();
   	  Object[][] array=emple.consultaDatosTablaToArray();
   	  
   	  JPVertabla a=new JPVertabla(array,titulos);
   	  this.vista.remove(this.vista.getFormc());
   	  this.vista.remove(this.vista.getForme());
   	  this.vista.remove(this.vista.getFormp());
   	this.vista.remove(this.vista.getFormProductos());
   	  this.vista.remove(this.vista.getVerTabla());
   	  this.vista.setVerTabla(a);
   	  this.vista.getVerTabla().addlistenersToTable(this);
	}
	public void refrescarViewConsultaProvedores(){
	  modo="C";
  	  num_form=3;
  	  ProveedorDAO emple=new ProveedorDAO();
  	  String [] titulos=emple.consultaTitulosTablaToArray();
  	  Object[][] array=emple.consultaDatosTablaToArray();
  	  
  	  JPVertabla a=new JPVertabla(array,titulos);
  	  this.vista.remove(this.vista.getFormc());
  	  this.vista.remove(this.vista.getForme());
  	  this.vista.remove(this.vista.getFormp());
  	this.vista.remove(this.vista.getFormProductos());
  	  this.vista.remove(this.vista.getVerTabla());
  	  this.vista.setVerTabla(a);
  	  this.vista.getVerTabla().addlistenersToTable(this);
	}
	//
	//REFERENTE A TRABAJADORES
	//
	public void refrescarViewDeleteEmpleados(){
  	  modo="D";
  	  num_form=2;
  	  EmpleadoDAO emple=new EmpleadoDAO();
  	  String [] titulos=emple.consultaTitulosTablaToArray();
  	  Object[][] array=emple.consultaDatosTablaToArray();
  	  
  	  JPVertabla a=new JPVertabla(array,titulos);
  	  this.vista.remove(this.vista.getFormc());
  	  this.vista.remove(this.vista.getForme());
  	  this.vista.remove(this.vista.getFormp());
  	this.vista.remove(this.vista.getFormProductos());
  	  this.vista.remove(this.vista.getVerTabla());//limpiar la ventana antes de mostrar otra
  	  this.vista.setVerTabla(a);
  	  this.vista.getVerTabla().addlistenersToTable(this);
	}
	public void refrescarViewModEmpleados(){
	  modo="M";
  	  num_form=2;
  	  EmpleadoDAO emple=new EmpleadoDAO();
  	  String [] titulos=emple.consultaTitulosTablaToArray();
  	  Object[][] array=emple.consultaDatosTablaToArray();
  	  
  	  JPVertabla a=new JPVertabla(array,titulos);
  	  this.vista.remove(this.vista.getFormc());
  	  this.vista.remove(this.vista.getForme());
  	  this.vista.remove(this.vista.getFormp());
  	this.vista.remove(this.vista.getFormProductos());
  	  this.vista.remove(this.vista.getVerTabla());//limpiar la ventana antes de mostrar otra
  	  this.vista.setVerTabla(a);
  	  this.vista.getVerTabla().addlistenersToTable(this);
	}
	public void refrescarViewConsultaEmpleados(){
	  modo="C";
  	  num_form=2;
  	  EmpleadoDAO emple=new EmpleadoDAO();
  	  String [] titulos=emple.consultaTitulosTablaToArray();
  	  Object[][] array=emple.consultaDatosTablaToArray();
  	  
  	  JPVertabla a=new JPVertabla(array,titulos);
  	  this.vista.remove(this.vista.getFormc());
  	  this.vista.remove(this.vista.getForme());
  	  this.vista.remove(this.vista.getFormp());
  	this.vista.remove(this.vista.getFormProductos());
  	  this.vista.remove(this.vista.getVerTabla());//limpiar la ventana antes de mostrar otra
  	  this.vista.setVerTabla(a);
  	  this.vista.getVerTabla().addlistenersToTable(this);
	}
	//
	//REFERENTE A CLIENTES
	//
	public void refrescarViewDeleteClientes(){
	  modo="D";
  	  num_form=1;
  	  ClienteDAO emple=new ClienteDAO();
  	  String [] titulos=emple.consultaTitulosTablaToArray();
  	  Object[][] array=emple.consultaDatosTablaToArray();
  	  
  	  JPVertabla a=new JPVertabla(array,titulos);
  	  this.vista.remove(this.vista.getFormc());
  	  this.vista.remove(this.vista.getForme());
  	  this.vista.remove(this.vista.getFormp());
  	this.vista.remove(this.vista.getFormProductos());
  	  this.vista.remove(this.vista.getVerTabla());
  	  this.vista.setVerTabla(a);
  	  this.vista.getVerTabla().addlistenersToTable(this);
	}
	public void refrescarViewModClientes(){
	  modo="M";
  	  num_form=1;
  	  ClienteDAO emple=new ClienteDAO();
  	  String [] titulos=emple.consultaTitulosTablaToArray();
  	  Object[][] array=emple.consultaDatosTablaToArray();
  	  
  	  JPVertabla a=new JPVertabla(array,titulos);
  	  this.vista.remove(this.vista.getFormc());
  	  this.vista.remove(this.vista.getForme());
  	  this.vista.remove(this.vista.getFormp());
  	this.vista.remove(this.vista.getFormProductos());
  	  this.vista.remove(this.vista.getVerTabla());
  	  this.vista.setVerTabla(a);
  	  this.vista.getVerTabla().addlistenersToTable(this);
	}
	public void refrescarViewConsultaClientes(){
		modo="C";
  	  num_form=1;
  	  ClienteDAO emple=new ClienteDAO();
  	  String [] titulos=emple.consultaTitulosTablaToArray();
  	  Object[][] array=emple.consultaDatosTablaToArray();
  	  
  	  JPVertabla a=new JPVertabla(array,titulos);
  	  this.vista.remove(this.vista.getFormc());
  	  this.vista.remove(this.vista.getForme());
  	  this.vista.remove(this.vista.getFormp());
  	this.vista.remove(this.vista.getFormProductos());
  	  this.vista.remove(this.vista.getVerTabla());
  	  this.vista.setVerTabla(a);
  	  this.vista.getVerTabla().addlistenersToTable(this);
	}
	//
	//REFERNTE A PRODUCTOS
	//
	public void refrescarViewDeleteProductos(){
	  System.out.println("Borrar Producto.");
  	  modo="D";
  	  num_form=4;
  	  ProductoDAO emple=new ProductoDAO();
  	  String [] titulos=emple.consultaTitulosTablaToArray();
  	  Object[][] array=emple.consultaDatosTablaToArray();
  	  
  	  JPVertabla a=new JPVertabla(array,titulos);
  	  this.vista.remove(this.vista.getFormc());
  	  this.vista.remove(this.vista.getForme());
  	  this.vista.remove(this.vista.getFormp());
  	  this.vista.remove(this.vista.getFormProductos());
  	  this.vista.remove(this.vista.getVerTabla());
  	  this.vista.setVerTabla(a);
  	  this.vista.getVerTabla().addlistenersToTable(this);
	}
	public void refrescarViewModProductos(){
	  System.out.println("Modificar Producto.");
   	  modo="M";
   	  num_form=4;
   	  ProductoDAO emple=new ProductoDAO();
   	  String [] titulos=emple.consultaTitulosTablaToArray();
   	  Object[][] array=emple.consultaDatosTablaToArray();
   	  
   	  JPVertabla a=new JPVertabla(array,titulos);
   	  this.vista.remove(this.vista.getFormc());
   	  this.vista.remove(this.vista.getForme());
   	  this.vista.remove(this.vista.getFormp());
   	  this.vista.remove(this.vista.getFormProductos());
   	  this.vista.remove(this.vista.getVerTabla());
   	  this.vista.setVerTabla(a);
   	  this.vista.getVerTabla().addlistenersToTable(this);
	}
	public void refrescarViewConsultaProductos(){
	  System.out.println("Consultar Producto.");
   	  modo="C";
   	  num_form=4;
   	  ProductoDAO product=new ProductoDAO();
   	  String [] titulos=product.consultaTitulosTablaToArray();
   	  Object[][] array=product.consultaDatosTablaToArray();
   	  
   	  JPVertabla a=new JPVertabla(array,titulos);
   	  
   	  this.vista.remove(this.vista.getFormc());
   	  this.vista.remove(this.vista.getForme());
   	  this.vista.remove(this.vista.getFormp());
   	  this.vista.remove(this.vista.getFormProductos());
   	  this.vista.remove(this.vista.getVerTabla());
   	  this.vista.setVerTabla(a);
   	  this.vista.getVerTabla().addlistenersToTable(this);
	}
	}


