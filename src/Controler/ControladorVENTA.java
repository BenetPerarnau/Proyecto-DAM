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
import View.JPArbolNodosVENTA;
import View.JPPie;
import View.JPVertabla;
import View.Principal;
import View.Formularios.JPFormularioClientes;
import View.Formularios.JPFormularioEmpleados;
import View.Formularios.JPFormularioProductos;
import View.Formularios.JPFormularioProveedores;

public class ControladorVENTA implements ActionListener , TreeSelectionListener, 
									KeyListener, MouseListener, Runnable {
	
	private static int op;//para el Thread
	private static int num_form;//variable para saber en que form estamos
	private static String modo;//para saber si estamos en Consulta, Borrado o Insertar
	
	private static Principal vista; //container de la aplicación donde se iran mostrando las diferentes ventanas especificas dependiendo de la interacción del usuario
	
	private static ControladorVENTA context;
	
	public ControladorVENTA(Principal vista){
		
		this.vista=vista;
		this.vista.addlisteners(this);
		this.vista.remove(this.vista.getBienvenidaRoot());
		this.vista.setTreeVenta(new JPArbolNodosVENTA());
		this.vista.getBienvenidaVenta().setVisible(true);
		this.vista.getBienvenidaVenta().addListener(this);
		this.vista.repaint();
		context=this;
				
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Acciones controladas por la clase ControladorVENTA");
		if(e.getSource()==this.vista.getItem1desp1()){//cerrar sesión
			
			this.vista.getBienvenidaVenta().setVisible(false);
			this.vista.getBienvenidaRoot().setVisible(false);
			this.vista.getVerTabla().setVisible(false);
			this.vista.getPie().setVisible(false);
			this.vista.getLog().setVisible(true);
			
			
		}else if(e.getSource()==this.vista.getBienvenidaVenta().getTree()){//NS
			
		
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
		}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
					//OPERACIONES PRODUCTOS
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		}else if(this.vista.getVerTabla().getFormProductos()!=null && num_form==4){
			
			
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
							//OPERACIONES VENTAS
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		}//add else if
		
		
	}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//			ACCIONES QUE SE REALIZAN CUANDO SE PULSA EN EL ARBOL (panel de la izquierda)
//
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void valueChanged(TreeSelectionEvent se) {
		System.out.println("Acciones controladas por la clase ControladorVENTA");
		JTree tree = (JTree) se.getSource();
	    DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
	    String selectedNodeName = selectedNode.toString();
	    System.out.println(selectedNode.toString());
	    if (selectedNode.isLeaf()) {//si no es carpeta
	    	

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	    	  				// ARBOL CLIENTE
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////	    	  
	    	  
	      if(selectedNode.toString().equals("Crear Cliente")){
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
	      // ARBOL PRODUCTO
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////	

	    }else if(selectedNode.toString().equals("Consultar Producto")){
	    	refrescarViewConsultaProductos();
	    }
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
		

		
	}

	@Override
	public void keyReleased(KeyEvent e) {//cuando se suelta la tecla
		// TODO Auto-generated method stub
		
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
        case 4:
        	this.vista.getVerTabla().getFormProductos().rellenarTodo(values);
        	this.vista.getVerTabla().getFormProductos().desactivar();       	
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
	  System.out.println("Consultar Clientes.");
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
	public void refrescarViewConsultaProductos(){
	  System.out.println("Consultar Producto.");
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
	

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	}


