package Controler;

import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import DAO.CompraDAO;
import DAO.EmpleadoDAO;
import DAO.FacturaDAO;
import DAO.LogsDAO;
import DAO.ProductoDAO;
import DAO.ProveedorDAO;
import DAO.VentaDAO;
import DTO.Cliente;
import DTO.Compra;
import DTO.Empleado;
import DTO.Factura;
import DTO.Producto;
import DTO.Proveedor;
import DTO.Venta;
import Exceptions.ExceptionCantidad;
import Exceptions.ExceptionCompras;
import Exceptions.ExceptionDNI_CIF;
import Exceptions.ExceptionNcuenta;
import Exceptions.ExceptionNombre;
import Exceptions.ExceptionPrecio;
import Exceptions.ExceptionSalario;
import Exceptions.ExceptionStock;
import Model.ConectorBBDD;
import Model.Lectura;
import Roles.OpClientes;
import Roles.OpCompras;
import Roles.OpEmpleados;
import Roles.OpFacturas;
import Roles.OpProductos;
import Roles.OpProveedores;
import Roles.OpVentas;
import View.JPArbolNodosROOT;
import View.JPPie;
import View.JPVertabla;
import View.Principal;
import View.Formularios.JPFormularioClientes;
import View.Formularios.JPFormularioCompra;
import View.Formularios.JPFormularioEmpleados;
import View.Formularios.JPFormularioFactura;
import View.Formularios.JPFormularioProductos;
import View.Formularios.JPFormularioProveedores;
import View.Formularios.JPFormularioVenta;

public class ControladorROOT implements ActionListener , TreeSelectionListener, 
									KeyListener, MouseListener, Runnable {
	/**
	 * @param op variable de selección de tipo de accion que realizará el hilo secundario
	 */
	private static int op;
	private static int num_form;//variable para saber en que form estamos
	private static int rol;//varaible que definira las opciones que tendrá el usuario en la pantalla
	private static String modo;//para saber si estamos en Consulta, Borrado o Insertar
	private static Principal vista; //container de la aplicación donde se iran mostrando las diferentes ventanas especificas dependiendo de la interacción del usuario
	private static String name;
	private static String pass;
	private static ControladorROOT context;
	
	
	/**
	 * 
	 * @param vista
	 */
	public ControladorROOT(Principal vista){
		
		this.vista=vista;
		this.vista.addlisteners(this);
		this.vista.getLog().addListeners(this);
		//this.vista.getBienvenidaRoot().addListener(this);		
		context=this;				
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Acciones controladas por la clase ControladorROOT");
		if(e.getSource()==this.vista.getLog().getBtnIngresar()){//login implementado en el thread op 1
			
			name=this.vista.getLog().getTxtNombre();
			pass=this.vista.getLog().getTxtPassword();	
			
			this.vista.getLog().setVisible(false);
			this.vista.getCargando().setVisible(true);
			this.vista.getCargando().repaint();
			this.vista.repaint();
			
			op=1;
			//ControladorROOT b=this;
			new Thread (this).start();

		}else if(e.getSource()==this.vista.getItem1desp1()){//cerrar sesión
			
			this.vista.getBienvenidaRoot().setVisible(false);
			this.vista.getVerTabla().setVisible(false);
			//TODO implementar en los otros controlers
			this.vista.cleanWindow();
			//
			this.vista.getPie().setVisible(false);
			this.vista.getLog().setVisible(true);
			
			
		}else if(e.getSource()==this.vista.getLog().getChckbxNewCheckBox()){ //si se marca el campo guardar password
			
			if(this.vista.getLog().getChckbxNewCheckBox().isSelected()){
				JOptionPane.showMessageDialog(vista, "Tu usuario y password seran recordados en la proxima sesión.");
			}
			
		}else if(e.getSource()==this.vista.getBienvenidaRoot().getTree()){//NS
			

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//									OPERACIONES PROVEEDORES
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		}else if(this.vista.getVerTabla().getFormP()!=null && num_form==3 ){
			OpProveedores opP=new OpProveedores(this);
			//
			//boton de borrar en la tabla proveedores
			//
			if (e.getSource() == this.vista.getVerTabla().getFormP().getBtn()) {
				
				if(opP.btnEliminarFormulario(vista)){
					refrescarViewDeleteProvedores();
				}
			//
			//boton Actualizar en la tabla proveedores
			//
			}else if(e.getSource()==this.vista.getVerTabla().getFormP().getBtnActualizar()){
				
				if(opP.btnActualizarFormulario(vista)){
					refrescarViewModProvedores();
				}
		//
		//btn save en formulario new Proveedor
		//
		}else if(e.getSource()==this.vista.getFormp().getBtnGuardar()){
			
			if(opP.btnGuardarFormulario(vista)){
				refrescarViewConsultaProvedores();
			}
		}		
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//								OPERACIONES EMPLEADOS
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		}else if(this.vista.getVerTabla().getFormE()!=null && num_form==2 ){
			OpEmpleados opE=new OpEmpleados();
			//
			//boton de borrar en la tabla Empleados
			//
			if(e.getSource()==this.vista.getVerTabla().getFormE().getBtn()){
				
				if(opE.btnEliminarFormulario(vista)){
					refrescarViewDeleteEmpleados();	
				}
			//
			//actualizar tabla Empleados
			//
			}else if(e.getSource()==this.vista.getVerTabla().getFormE().getBtnActualizar()){
				
				if(opE.btnActualizarFormulario(vista)){
					refrescarViewModEmpleados();
				}
		//
		////boton añadir en la tabla empleados
		//
		}else if(e.getSource()==this.vista.getForme().getBtnGuardar()){
			
			if(opE.btnGuardarFormulario(vista)){
				refrescarViewConsultaEmpleados();
			}
		}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
								//OPERACIONES CLIENTES
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		}else if(this.vista.getVerTabla().getFormC()!=null && num_form==1){			
			OpClientes opC= new OpClientes(this);		
			//
			//boton de borrar en la tabla Clientes
			//
			if(e.getSource()==this.vista.getVerTabla().getFormC().getBtn()){
				if(opC.btnEliminarFormulario(vista)){
					refrescarViewDeleteClientes();
				}
		    //
			//actualizar tabla Clientes
			//	
			}else if(e.getSource()==this.vista.getVerTabla().getFormC().getBtnActualizar()){
				if(opC.btnActualizarFormulario(vista)){
					refrescarViewModClientes();
				}
		//
		//boton añadir en la tabla Clientes
		//
		}else if(e.getSource()==this.vista.getFormc().getBtnGuardar()){
			if(opC.btnGuardarFormulario(vista)){
				refrescarViewConsultaClientes();
			}
		}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//								OPERACIONES PRODUCTOS
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		}else if(this.vista.getVerTabla().getFormProductos()!=null && num_form==4){
			OpProductos opP=new OpProductos(this);
			//
			//boton de borrar en la tabla Productos
			//
			if(e.getSource()==this.vista.getVerTabla().getFormProductos().getBtn()){
				if(opP.btnEliminarFormulario(vista)){
					refrescarViewDeleteProductos();
				}
		    //
			//actualizar tabla Productos
			//	
			}else if(e.getSource()==this.vista.getVerTabla().getFormProductos().getBtnActualizar()){
				if(opP.btnActualizarFormulario(vista)){
					refrescarViewModProductos();
				}			
		//
		//boton añadir en la tabla Productos
		//
		}else if(e.getSource()==this.vista.getFormProductos().getBtnGuardar()){
			if(opP.btnGuardarFormulario(vista)){
				refrescarViewConsultaProductos();
			}
		}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//								OPERACIONES VENTAS
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		}else if(this.vista.getVerTabla().getFormVentas()!=null && num_form==5){
			OpVentas opV=new OpVentas();
			//
			//boton de borrar en la tabla Ventas
			//
			if(e.getSource()==this.vista.getVerTabla().getFormVentas().getBtn()){
				if(opV.btnEliminarFormulario(vista)){
					refrescarViewDeleteVentas();
				}
		    //
			//actualizar tabla Ventas
			//	
			}else if(e.getSource()==this.vista.getVerTabla().getFormVentas().getBtnActualizar()){
			
				if(opV.btnActualizarFormulario(vista)){
					refrescarViewModVentas();
				}
			
		}else if(e.getSource()==this.vista.getFormVentas().getBtnGuardar()){
			if(opV.btnGuardarFormulario(vista)){
				refrescarViewConsultaVentas();
			}
			
		}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
							//OPERACIONES Compras
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		}else if(this.vista.getVerTabla().getFormCompras()!=null && num_form==6){
			OpCompras opC=new OpCompras();
			//
			//boton de borrar en la tabla Compras
			//
			if(e.getSource()==this.vista.getVerTabla().getFormCompras().getBtn()){
				if(opC.btnEliminarFormulario(vista)){
					refrescarViewDeleteCompras();
				}
		    //
			//actualizar tabla Compras
			//	
			}else if(e.getSource()==this.vista.getVerTabla().getFormCompras().getBtnActualizar()){
				if(opC.btnActualizarFormulario(vista)){
					refrescarViewModCompras();
				}		

		}else if(e.getSource()==this.vista.getFormCompras().getBtnGuardar()){
			if(opC.btnGuardarFormulario(vista)){
				refrescarViewConsultaCompras();
			}
		}
			
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
							//OPERACIONES Facturas
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
		}else if(this.vista.getVerTabla().getFormFacturas()!=null && num_form==7){
			OpFacturas opF=new OpFacturas();
			//
			//boton de borrar en la tabla Facturas
			//
			if(e.getSource()==this.vista.getVerTabla().getFormFacturas().getBtn()){ 
				if(opF.btnEliminarFormulario(vista)){
					refrescarViewDeleteFacturas();
				}
		    //
			//actualizar tabla Facturas
			//	
			}else if(e.getSource()==this.vista.getVerTabla().getFormFacturas().getBtnActualizar()){
			if(opF.btnActualizarFormulario(vista)){
				refrescarViewModFacturas();
			}		
		//
		//boton añadir en la tabla Facturas
		//
		}else if(e.getSource()==this.vista.getFormFacturas().getBtnGuardar()){
			if(opF.btnGuardarFormulario(vista)){
				refrescarViewConsultaFacturas();
			}
		}
			
		}//add else if
		
		
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
	    	  clean();
	    	  JPFormularioEmpleados a=new JPFormularioEmpleados();
	    	  a.getTfcuenta().addKeyListener(new KeyListener() {
				
				@Override
				public void keyTyped(KeyEvent e) {}
				
				@Override
				public void keyReleased(KeyEvent e) {
					
					if(e.getKeyCode()!=8){
						//- IBAN electrónico: ES79-2100-0813-6101-2345-6789
						String input=a.getTfcuenta().getText().toString();
						if(input.length()==4 || input.length()==9 || input.length()==14 || 
						   input.length()==19 || input.length()==24){
							
							input+="-";
							
						}
						a.getTfcuenta().setText(input);
					}		
					
				}
				
				@Override
				public void keyPressed(KeyEvent e) {}
			});
	    	  this.vista.setForme(a);
	    	  this.vista.getForme().getBtnGuardar().setVisible(true);
	    	  this.vista.getForme().addListeners(context);
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
		    	  clean();
		    	  JPFormularioClientes a=new JPFormularioClientes();
		    	  a.getTfcuenta().addKeyListener(new KeyListener() {
					
					@Override
					public void keyTyped(KeyEvent e) {}
					
					@Override
					public void keyReleased(KeyEvent e) {
						if(e.getKeyCode()!=8){
							//- IBAN electrónico: ES79-2100-0813-6101-2345-6789
							String input=a.getTfcuenta().getText().toString();
							if(input.length()==4 || input.length()==9 || input.length()==14 || 
							   input.length()==19 || input.length()==24){
								
								input+="-";
								
							}
							a.getTfcuenta().setText(input);
						}												
					}
					
					@Override
					public void keyPressed(KeyEvent e) {}
				});
		    	  a.getTfcompras().setEditable(false);
		    	  a.setTfcompras("0");
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
	    	  clean();
	    	  
	    	  JPFormularioProveedores a=new JPFormularioProveedores();
	    	  a.setTfcompras("0");
	    	  a.getTfcompras().setEditable(false);
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
	    	  clean();
	    	  JPFormularioProductos a=new JPFormularioProductos();
	    	  a.getTfstock().setEditable(false);
	    	  a.getTfstock().setText("0");
	    	  this.vista.setFormProductos(a);
	    	  this.vista.getFormProductos().getTfproveedor().setVisible(false);
	    	  this.vista.getFormProductos().getComboProvedores().setVisible(true);
	    	  //TODO select CIF proveedores and insert into comboBox
	    	  ProveedorDAO p=new ProveedorDAO();
	    	  try {
				ArrayList<Proveedor> aux=p.readAll();
				for(int i=0; i<aux.size(); i++){
					this.vista.getFormProductos().getComboProvedores().addItem(aux.get(i).getCIF());
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    	  this.vista.getFormProductos().getBtnGuardar().setVisible(true);
	    	  this.vista.getFormProductos().addListeners(this);
	    	  this.vista.repaint();
	        	 	  
	      }else if(selectedNode.toString().equals("Consultar Producto")){
	    	 refrescarViewConsultaProductos();
	    	  
	    	  
	      }else if(selectedNode.toString().equals("Borrar Producto")){
	    	  refrescarViewDeleteProductos();
	    	  
	      }else if(selectedNode.toString().equals("Modificar Producto")){
	    	 refrescarViewModProductos();
	    	
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	    	 					// ARBOL VENTAS
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////	    	 
	      }else if(selectedNode.toString().equals("Crear Venta")){
	    	  System.out.println("Crear Venta.");
	    	  DecimalFormat df=new DecimalFormat("0.00");
	    	  ClienteDAO c=new ClienteDAO();
	    	  ProductoDAO p=new ProductoDAO();
	    	  FacturaDAO f=new FacturaDAO();
	    	  modo="I";
	    	  num_form=5;
	    	  clean();
	    	  JPFormularioVenta a=new JPFormularioVenta();
	    	  a.getTfPrecio().setEditable(false);
	    	  a.getTfCantidad().addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					System.out.println("entra en cantidad product");
					try{
					float number=Float.parseFloat(a.getTfCantidad().getText().toString());
					if(number<0){number=number*-1; a.getTfCantidad().setText(number+"");}
					float precio=Float.parseFloat((p.read(a.getComboProducto().getSelectedItem().toString())).getPrecio()+"");				
					a.getTfPrecio().setText(df.format(number*precio).replace(',', '.')+"");
					System.out.println("total => "+df.format(number*precio));
					}catch(Exception ee){
						
					}
					
				}
			});
	    	  this.vista.setFormVentas(a);
	    	  this.vista.getFormVentas().getTfClient().setVisible(false);
	    	  this.vista.getFormVentas().getTfFactura().setVisible(false);
	    	  this.vista.getFormVentas().getTfProduct().setVisible(false);
	    	  this.vista.getFormVentas().getComboCliente().setVisible(true);
	    	  this.vista.getFormVentas().getComboProducto().setVisible(true);
	    	  this.vista.getFormVentas().getComboFactura().setVisible(true);
	    	  //rellenar combos

	    	  
	    	  try {
				ArrayList<Cliente> cl=c.readAll();
				ArrayList<Producto> pr=p.readAll();
				ArrayList<Factura> fa=f.readAll();
				
				for(int i=0; i<cl.size(); i++){
					this.vista.getFormVentas().getComboCliente().addItem(cl.get(i).getDni());
				}
				for(int i=0; i<pr.size(); i++){
					this.vista.getFormVentas().getComboProducto().addItem(pr.get(i).getCod());
				}
				for(int i=0; i<fa.size(); i++){
					this.vista.getFormVentas().getComboFactura().addItem(fa.get(i).getId());
				}
				
	    	  } catch (SQLException e) {
				e.printStackTrace();
	    	  }
	    	  
	    	  
	    	  this.vista.getFormVentas().getComboProducto().addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// Dependiendo del producto seleccionado setearemos el precio de este.
					Producto product=null;
					try {
						String codP=vista.getFormVentas().getComboProducto().getSelectedItem().toString();
						product=p.read(vista.getFormVentas().getComboProducto().getSelectedItem().toString());
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					
					vista.getFormVentas().getTfPrecio().setText(product.getPrecio()+"");
					
				}
			});
	    	  
	    	  this.vista.getFormVentas().getBtnGuardar().setVisible(true);
	    	  this.vista.getFormVentas().addListeners(this);
	    	  this.vista.repaint();
	    	  
	      }else if(selectedNode.toString().equals("Consultar Venta")){
	    	  System.out.println("Consultar Venta.");
	    	  refrescarViewConsultaVentas();
	      }else if(selectedNode.toString().equals("Borrar Venta")){
	    	  System.out.println("Borrar Venta.");
	    	  refrescarViewDeleteVentas();
	      }else if(selectedNode.toString().equals("Modificar Venta")){
	    	  System.out.println("Modificar Venta.");
	    	  refrescarViewModVentas();
	    	  
	    	  
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	    	  				  // ARBOL COMPRAS
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////    	
	      }else if(selectedNode.toString().equals("Crear Compra")){
	    	  System.out.println("Crear Compra");
	    	  modo="I";
	    	  num_form=6;
	    	  clean();
	    	  
	    	  ProveedorDAO prov=new ProveedorDAO();
	    	  JPFormularioCompra a=new JPFormularioCompra();
	    	  this.vista.setFormCompras(a);
	    	  this.vista.getFormCompras().getTfProducto().setVisible(false);
	    	  this.vista.getFormCompras().getTfProveedor().setVisible(false);
	    	  this.vista.getFormCompras().getComboProducto().setVisible(true);
	    	  this.vista.getFormCompras().getComboProveedor().setVisible(true);
	    	  this.vista.getFormCompras().getComboProveedor().addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					if(e.getSource()==vista.getFormCompras().getComboProveedor()){
						//dependiendo del provedor seleccionado los productos seran unos u otros
						System.out.println(vista.getFormCompras().getComboProveedor().getSelectedItem().toString());
						//Borrar combo antiguo si lo hay
						if(vista.getFormCompras().getComboProducto().getItemCount()>0){
							vista.getFormCompras().getComboProducto().removeAllItems();
						}
						//
						ProductoDAO product=new ProductoDAO();
						try {
							ArrayList<Producto> data=product.readAllProv(vista.getFormCompras().getComboProveedor().getSelectedItem().toString());
								for(int i=0; i<data.size(); i++){
									vista.getFormCompras().getComboProducto().addItem(data.get(i).getCod());
								}
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
				}
			});
	    	  //add content in combo
	    	  try {
				//ArrayList<Producto> data=product.readAll();
				ArrayList<Proveedor> data1=prov.readAll();			
				//for(int i=0; i<data.size(); i++){
					//this.vista.getFormCompras().getComboProducto().addItem(data.get(i).getCod());
				//}
				for(int i=0; i<data1.size(); i++){
					this.vista.getFormCompras().getComboProveedor().addItem(data1.get(i).getCIF());
				}
				
	    	  } catch (SQLException e) {
				e.printStackTrace();
	    	  }
	    	  
	    	  //
	    	  this.vista.getFormCompras().getBtnGuardar().setVisible(true);
	    	  this.vista.getFormCompras().addListeners(this);
	    	  this.vista.repaint();
	    	  
	      }else if(selectedNode.toString().equals("Consultar Compra")){
	    	  System.out.println("Consultar Compra");
	    	  refrescarViewConsultaCompras();
	      }else if(selectedNode.toString().equals("Borrar Compra")){
	    	  System.out.println("Borrar Compra");
	    	  refrescarViewDeleteCompras();
	      }else if(selectedNode.toString().equals("Modificar Compra")){
	    	  System.out.println("Modificar Compra");
	    	  refrescarViewModCompras();
	    	  
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	    	  				// ARBOL FACTURAS
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////  	    	  
	      }else if(selectedNode.toString().equals("Crear Factura")){
	    	  System.out.println("Crear Factura");
	    	  modo="I";
	    	  num_form=7;
	    	  clean();
	    	  JPFormularioFactura a=new JPFormularioFactura();
	    	  a.getTfFecha().setVisible(false);
      		  a.getLabelFecha().setVisible(false);
      		  a.getPickerDate().setVisible(true);
	    	  this.vista.setFormFacturas(a);  
	    	  this.vista.getFormFacturas().getTfCliente().setVisible(false);
	    	  this.vista.getFormFacturas().getTfProducto().setVisible(false);
	    	  this.vista.getFormFacturas().getComboCliente().setVisible(true);
	    	  this.vista.getFormFacturas().getComboProducto().setVisible(true);
	    	  //add items combo
	    	  ClienteDAO c=new ClienteDAO();
	    	  ProductoDAO p=new ProductoDAO();
	    	  
	    	  try {
				ArrayList<Cliente> cl=c.readAll();
				ArrayList<Producto> pr=p.readAll();
				
				for(int i=0; i<cl.size(); i++){
					this.vista.getFormFacturas().getComboCliente().addItem(cl.get(i).getDni());
				}
				for(int i=0; i<pr.size(); i++){
					this.vista.getFormFacturas().getComboProducto().addItem(pr.get(i).getCod());
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    	  
	    	  this.vista.getFormFacturas().getBtnGuardar().setVisible(true);
	    	  this.vista.getFormFacturas().addListeners(this);
	    	  this.vista.repaint();
	      }else if(selectedNode.toString().equals("Consultar Factura")){
	    	  System.out.println("Consultar Factura");
	    	  refrescarViewConsultaFacturas();
	      }else if(selectedNode.toString().equals("Borrar Factura")){
	    	  System.out.println("Borrar Factura");
	    	  refrescarViewDeleteFacturas();
	      }else if(selectedNode.toString().equals("Modificar Factura")){
	    	  System.out.println("Modificar Factura");
	    	  refrescarViewModFacturas();
	      }
	      
	      
	    }else{//es carpeta
			System.out.println(selectedNode.toString());
			clean();
			this.vista.getVerTabla().setVisible(false);
			this.vista.repaint();
	    }
	    
	  }
	
	//
	//AUX CLEAN WINDOW
	//
	
	public void clean(){
		//limpiar la ventana antes de mostrar otra
		this.vista.remove(this.vista.getFormc());
		this.vista.remove(this.vista.getForme());
		this.vista.remove(this.vista.getFormp());
		this.vista.remove(this.vista.getFormProductos());
    	this.vista.remove(this.vista.getFormVentas());
    	this.vista.remove(this.vista.getFormCompras());
    	this.vista.remove(this.vista.getFormFacturas()); 
		this.vista.remove(this.vista.getVerTabla());
	}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//					ACCIONES QUE SE REALIZARA EL TECLADO
//
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public void keyTyped(KeyEvent e) {}
	@Override
	public void keyReleased(KeyEvent e) {}
	@Override
	public void keyPressed(KeyEvent e) {
		
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
					//this.vista.getBienvenidaRoot().setVisible(true);
					this.vista.getPie().setVisible(true);
					this.vista.getPie().setStatusUser(name);
					
					rol=log.getRolUser(name, pass);
					//dependiendo del rol que sea cargará un controlador u otro
					switch(rol){
					case 1://Ventas
						System.out.println("Rol => Ventas");
						ControladorVENTA c=new ControladorVENTA(vista);
						break;
					case 2://Compras
						System.out.println("Rol => Compras");
						ControladorCOMPRA v=new ControladorCOMPRA(vista);
						break;
					case 3://Contabilidad
						System.out.println("Rol => CONTABILIDAD");
						ControladorCONTA co=new ControladorCONTA(vista);
						break;
					case 4://Almacén
						System.out.println("Rol => ALMACÉN");
						ControladorALMAC almac=new ControladorALMAC(vista);
						break;
						default:
							System.out.println("Rol => ROOT");
							this.vista.setTreeRoot(new JPArbolNodosROOT());
							this.vista.getBienvenidaRoot().setVisible(true);
							this.vista.getBienvenidaRoot().addListener(context);
							this.vista.repaint();
							break;
					}
					//JOptionPane.showMessageDialog(vista, "Bienvenido a BAP ERP. Uer{"+name+" "+pass+" "+rol+"}");
					
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
	public void mouseClicked(MouseEvent e) {}
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	
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
        case 1://Cliente
        	this.vista.getVerTabla().getFormC().rellenarTodo(values);
            if(modo.equals("D")){
            	this.vista.getVerTabla().getFormC().desactivar();
            	this.vista.getVerTabla().getFormC().getBtn().setVisible(true);
        		this.vista.getVerTabla().getFormC().addListeners(this);
            }else if(modo.equals("M")){
            	this.vista.getVerTabla().getFormC().activar();
            	//
            	this.vista.getVerTabla().getFormC().getTfcuenta().addKeyListener(new KeyListener() {
    				
    				@Override
    				public void keyTyped(KeyEvent e) {}
    				
    				@Override
    				public void keyReleased(KeyEvent e) {
    					
    					if(e.getKeyCode()!=8){
    						//- IBAN electrónico: ES79-2100-0813-6101-2345-6789
    						String input=vista.getVerTabla().getFormC().getTfcuenta().getText().toString();
    						if(input.length()==4 || input.length()==9 || input.length()==14 || 
    						   input.length()==19 || input.length()==24){
    							
    							input+="-";
    							
    						}
    						vista.getVerTabla().getFormC().getTfcuenta().setText(input);
    					}		
    					
    				}
    				
    				@Override
    				public void keyPressed(KeyEvent e) {}
    			});
            	//
            	this.vista.getVerTabla().getFormC().getBtnActualizar().setVisible(true);
            	this.vista.getVerTabla().getFormC().addListeners(this);
            	System.out.println("modo m clientes");
            }else if(modo.equals("C")){
            	this.vista.getVerTabla().getFormC().desactivar();
            }
        	break;
        case 2://Empleado
        	this.vista.getVerTabla().getFormE().rellenarTodo(values);
        	if(modo.equals("D")){
        		this.vista.getVerTabla().getFormE().desactivar();
        		this.vista.getVerTabla().getFormE().getBtn().setVisible(true);
        		this.vista.getVerTabla().getFormE().addListeners(this);
            }else if(modo.equals("M")){
            	this.vista.getVerTabla().getFormE().activar();
            	//
            	this.vista.getVerTabla().getFormE().getTfcuenta().addKeyListener(new KeyListener() {
				
				@Override
				public void keyTyped(KeyEvent e) {}
				
				@Override
				public void keyReleased(KeyEvent e) {
					
					if(e.getKeyCode()!=8){
						//- IBAN electrónico: ES79-2100-0813-6101-2345-6789
						String input=vista.getVerTabla().getFormE().getTfcuenta().getText().toString();
						if(input.length()==4 || input.length()==9 || input.length()==14 || 
						   input.length()==19 || input.length()==24){
							
							input+="-";
							
						}
						vista.getVerTabla().getFormE().getTfcuenta().setText(input);
					}		
					
				}
				
				@Override
				public void keyPressed(KeyEvent e) {}
			});
            	//
            	this.vista.getVerTabla().getFormE().getBtnActualizar().setVisible(true);
            	this.vista.getVerTabla().getFormE().addListeners(this);
            	System.out.println("modo m empleados");
            }else if(modo.equals("C")){
            	this.vista.getVerTabla().getFormE().desactivar();
            }
        	break;
        case 3://Proveedor
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
        case 4://productos
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
        case 5://ventas
        	this.vista.getVerTabla().getFormVentas().rellenarTodo(values);
        	if(modo.equals("D")){
        		this.vista.getVerTabla().getFormVentas().desactivar();
        		this.vista.getVerTabla().getFormVentas().getBtn().setVisible(true);
        		this.vista.getVerTabla().getFormVentas().addListeners(this);
        	}else if(modo.equals("M")){
        		this.vista.getVerTabla().getFormVentas().activar();
            	this.vista.getVerTabla().getFormVentas().getBtnActualizar().setVisible(true);
            	this.vista.getVerTabla().getFormVentas().addListeners(this);
        	}else if(modo.equals("C")){
        		this.vista.getVerTabla().getFormVentas().desactivar();
        	}
        	break;
        case 6://compras
        	this.vista.getVerTabla().getFormCompras().rellenarTodo(values);
        	if(modo.equals("D")){
        		this.vista.getVerTabla().getFormCompras().desactivar();
        		this.vista.getVerTabla().getFormCompras().getBtn().setVisible(true);
        		this.vista.getVerTabla().getFormCompras().addListeners(this);
        	}else if(modo.equals("M")){
        		this.vista.getVerTabla().getFormCompras().activar();
            	this.vista.getVerTabla().getFormCompras().getBtnActualizar().setVisible(true);
            	this.vista.getVerTabla().getFormCompras().addListeners(this);
        	}else if(modo.equals("C")){
        		this.vista.getVerTabla().getFormCompras().desactivar();
        	}
        	break;
        case 7://facturas
        	this.vista.getVerTabla().getFormFacturas().rellenarTodo(values);
        	if(modo.equals("D")){
        		this.vista.getVerTabla().getFormFacturas().desactivar();
        		this.vista.getVerTabla().getFormFacturas().getTfFecha().setVisible(true);
        		this.vista.getVerTabla().getFormFacturas().getLabelFecha().setVisible(true);
        		this.vista.getVerTabla().getFormFacturas().getBtn().setVisible(true);
        		this.vista.getVerTabla().getFormFacturas().addListeners(this);
        	}else if(modo.equals("M")){
        		this.vista.getVerTabla().getFormFacturas().activar();
        		this.vista.getVerTabla().getFormFacturas().getTfFecha().setVisible(true);
        		this.vista.getVerTabla().getFormFacturas().getLabelFecha().setVisible(true);
            	this.vista.getVerTabla().getFormFacturas().getBtnActualizar().setVisible(true);
            	this.vista.getVerTabla().getFormFacturas().addListeners(this);
        	}else if(modo.equals("C")){
        		this.vista.getVerTabla().getFormFacturas().getTfFecha().setVisible(true);
        		this.vista.getVerTabla().getFormFacturas().getLabelFecha().setVisible(true);
        		this.vista.getVerTabla().getFormFacturas().desactivar();
        	}
        	break;
        	default:
        		System.out.println("Número de formulario no válido.");
        		break;
        }

        this.vista.getVerTabla().repaint();
        this.vista.repaint();
        
        //String aux = this.vista.getVerTabla().getTable().getValueAt(row-1, column-1)+"";
        //System.out.println("Fila "+row+" Columna "+column+" Valor => "+aux+" Total columnas => "+columnt+" Total filas => "+rowt);
		
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
  	  clean();
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
   	  clean();
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
  	  clean();
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
  	  clean();
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
  	  clean();
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
  	  clean();
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
  	  clean();
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
  	  clean();
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
  	  clean();
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
  	  clean();
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
   	  clean();
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
   	  
   	  clean();
   	  this.vista.setVerTabla(a);
   	  this.vista.getVerTabla().addlistenersToTable(this);
	}
	//
	//REFERNTE A VENTAS
	//
	public void refrescarViewDeleteVentas(){
	  	  modo="D";
	  	  num_form=5;
	  	  VentaDAO venta=new VentaDAO();
	  	  String [] titulos=venta.consultaTitulosTablaToArray();
	  	  Object[][] array=venta.consultaDatosTablaToArray();
	  	  
	  	  JPVertabla a=new JPVertabla(array,titulos);
	  	  clean();
	  	  this.vista.setVerTabla(a);
	  	  this.vista.getVerTabla().addlistenersToTable(this);
		}
		public void refrescarViewModVentas(){
	   	  modo="M";
	   	  num_form=5;
	   	  VentaDAO venta=new VentaDAO();
	   	  String [] titulos=venta.consultaTitulosTablaToArray();
	   	  Object[][] array=venta.consultaDatosTablaToArray();
	   	  
	   	  JPVertabla a=new JPVertabla(array,titulos);
	   	  clean();
	   	  this.vista.setVerTabla(a);
	   	  this.vista.getVerTabla().addlistenersToTable(this);
		}
		public void refrescarViewConsultaVentas(){
	   	  modo="C";
	   	  num_form=5;
	   	  VentaDAO venta=new VentaDAO();
	   	  String [] titulos=venta.consultaTitulosTablaToArray();
	   	  Object[][] array=venta.consultaDatosTablaToArray();
	   	  
	   	  JPVertabla a=new JPVertabla(array,titulos);
	   	  
	   	  clean();
	   	  this.vista.setVerTabla(a);
	   	  this.vista.getVerTabla().addlistenersToTable(this);
		}
		//
		//REFERNTE A COMPRAS
		//
	public void refrescarViewDeleteCompras() {
		modo = "D";
		num_form = 6;
		CompraDAO compra = new CompraDAO();
		String[] titulos = compra.consultaTitulosTablaToArray();
		Object[][] array = compra.consultaDatosTablaToArray();

		JPVertabla a = new JPVertabla(array, titulos);
		clean();
		this.vista.setVerTabla(a);
		this.vista.getVerTabla().addlistenersToTable(this);
	}

	public void refrescarViewModCompras() {
		modo = "M";
		num_form = 6;
		CompraDAO compra = new CompraDAO();
		String[] titulos = compra.consultaTitulosTablaToArray();
		Object[][] array = compra.consultaDatosTablaToArray();

		JPVertabla a = new JPVertabla(array, titulos);
		clean();
		this.vista.setVerTabla(a);
		this.vista.getVerTabla().addlistenersToTable(this);
	}

	public void refrescarViewConsultaCompras() {
		modo = "C";
		num_form = 6;
		CompraDAO compra = new CompraDAO();
		String[] titulos = compra.consultaTitulosTablaToArray();
		Object[][] array = compra.consultaDatosTablaToArray();

		JPVertabla a = new JPVertabla(array, titulos);

		clean();
		this.vista.setVerTabla(a);
		this.vista.getVerTabla().addlistenersToTable(this);
	}
	//
	//REFERNTE A FACTURAS
	//
	public void refrescarViewDeleteFacturas() {
		modo = "D";
		num_form = 7;
		FacturaDAO fac = new FacturaDAO();
		String[] titulos = fac.consultaTitulosTablaToArray();
		Object[][] array = fac.consultaDatosTablaToArray();

		JPVertabla a = new JPVertabla(array, titulos);
		clean();
		this.vista.setVerTabla(a);
		this.vista.getVerTabla().addlistenersToTable(this);
	}

	public void refrescarViewModFacturas() {
		modo = "M";
		num_form = 7;
		FacturaDAO fac = new FacturaDAO();
		String[] titulos = fac.consultaTitulosTablaToArray();
		Object[][] array = fac.consultaDatosTablaToArray();

		JPVertabla a = new JPVertabla(array, titulos);
		clean();
		this.vista.setVerTabla(a);
		this.vista.getVerTabla().addlistenersToTable(this);
	}

	public void refrescarViewConsultaFacturas() {
		modo = "C";
		num_form = 7;
		FacturaDAO fac = new FacturaDAO();
		String[] titulos = fac.consultaTitulosTablaToArray();
		Object[][] array = fac.consultaDatosTablaToArray();

		JPVertabla a = new JPVertabla(array, titulos);

		clean();
		this.vista.setVerTabla(a);
		this.vista.getVerTabla().addlistenersToTable(this);
	}
	}