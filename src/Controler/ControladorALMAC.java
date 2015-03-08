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
import View.JPArbolNodosALMA;
import View.JPArbolNodosCOMPRA;
import View.JPArbolNodosVENTA;
import View.JPPie;
import View.JPVertabla;
import View.Principal;
import View.Formularios.JPFormularioClientes;
import View.Formularios.JPFormularioEmpleados;
import View.Formularios.JPFormularioProductos;
import View.Formularios.JPFormularioProveedores;

public class ControladorALMAC implements ActionListener , TreeSelectionListener, 
									KeyListener, MouseListener, Runnable {
	
	private static int op;//para el Thread
	private static int num_form;//variable para saber en que form estamos
	private static String modo;//para saber si estamos en Consulta, Borrado o Insertar
	
	private static Principal vista; //container de la aplicación donde se iran mostrando las diferentes ventanas especificas dependiendo de la interacción del usuario
	
	private static ControladorALMAC context;
	
	public ControladorALMAC(Principal vista){
		
		this.vista=vista;
		this.vista.addlisteners(this);
		this.vista.remove(this.vista.getBienvenidaRoot());
		this.vista.setTreeALMA(new JPArbolNodosALMA());
		this.vista.getBienvenidaAlma().setVisible(true);
		this.vista.getBienvenidaAlma().addListener(this);
		this.vista.repaint();
		context=this;
				
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Acciones controladas por la clase ControladorCOMPRA");
		if(e.getSource()==this.vista.getItem1desp1()){//cerrar sesión
			
			this.vista.getBienvenidaVenta().setVisible(false);
			this.vista.getBienvenidaRoot().setVisible(false);
			this.vista.getVerTabla().setVisible(false);
			this.vista.getPie().setVisible(false);
			this.vista.getLog().setVisible(true);
			
			
		}else if(e.getSource()==this.vista.getBienvenidaVenta().getTree()){//NS		
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
					//OPERACIONES PRODUCTOS
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		}else if(this.vista.getVerTabla().getFormProductos()!=null && num_form==4){
			
		    //
			//actualizar tabla Productos
			//	
			if(e.getSource()==this.vista.getVerTabla().getFormProductos().getBtnActualizar()){
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
	    		// ARBOL PRODUCTO
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////	

	    	if(selectedNode.toString().equals("Consultar Producto")){
	    		refrescarViewConsultaProductos();


	    	}else if(selectedNode.toString().equals("Modificar Producto")){
	    		refrescarViewModProductos();
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
        case 4:
        	this.vista.getVerTabla().getFormProductos().rellenarTodo(values);
        	if(modo.equals("M")){
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

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//				ACCIONES QUE SE REALIZAN EN DIFERENTES PARTES
//
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	//REFERNTE A PRODUCTOS
	//
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


