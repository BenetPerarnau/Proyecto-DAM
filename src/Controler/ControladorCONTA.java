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
import View.JPArbolNodosCOMPRA;
import View.JPArbolNodosCONTA;
import View.JPArbolNodosVENTA;
import View.JPPie;
import View.JPVertabla;
import View.Principal;
import View.Formularios.JPFormularioClientes;
import View.Formularios.JPFormularioEmpleados;
import View.Formularios.JPFormularioProductos;
import View.Formularios.JPFormularioProveedores;

public class ControladorCONTA implements ActionListener , TreeSelectionListener, 
									KeyListener, MouseListener, Runnable {
	
	private static int op;//para el Thread
	private static int num_form;//variable para saber en que form estamos
	private static String modo;//para saber si estamos en Consulta, Borrado o Insertar
	
	private static Principal vista; //container de la aplicación donde se iran mostrando las diferentes ventanas especificas dependiendo de la interacción del usuario
	
	private static ControladorCONTA context;
	
	public ControladorCONTA(Principal vista){
		
		this.vista=vista;
		this.vista.addlisteners(this);
		this.vista.remove(this.vista.getBienvenidaRoot());
		this.vista.setTreeCONTA(new JPArbolNodosCONTA());
		this.vista.getBienvenidaConta().setVisible(true);
		this.vista.getBienvenidaConta().addListener(this);
		this.vista.repaint();
		context=this;
				
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Acciones controladas por la clase ControladorCONTA");
		if(e.getSource()==this.vista.getItem1desp1()){//cerrar sesión
			
			this.vista.getBienvenidaVenta().setVisible(false);
			this.vista.getBienvenidaRoot().setVisible(false);
			this.vista.getVerTabla().setVisible(false);
			this.vista.getPie().setVisible(false);
			this.vista.getLog().setVisible(true);
			
			
		}else if(e.getSource()==this.vista.getBienvenidaVenta().getTree()){//NS
					
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
							//OPERACIONES EMPLEADOS
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
				//
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
			}		
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
					//OPERACIONES FACTURAS
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		}else if(this.vista.getVerTabla().getFormProductos()!=null && num_form==4){//mod condition els if
			
			
		}//add else if
		
		
	}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//			ACCIONES QUE SE REALIZAN CUANDO SE PULSA EN EL ARBOL (panel de la izquierda)
//
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void valueChanged(TreeSelectionEvent se) {
		System.out.println("Acciones controladas por la clase ControladorCONTA");
		JTree tree = (JTree) se.getSource();
	    DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
	    String selectedNodeName = selectedNode.toString();
	    System.out.println(selectedNode.toString());
	    if (selectedNode.isLeaf()) {//si no es carpeta
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// ARBOL EMPLEADOS
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	    	if(selectedNode.toString().equals("Crear Trabajador")){
	    		modo="I";
	    		num_form=2;
	    		this.vista.remove(this.vista.getFormc());
	    		this.vista.remove(this.vista.getForme());
	    		this.vista.remove(this.vista.getFormp());
	    		this.vista.remove(this.vista.getFormProductos());
	    		this.vista.remove(this.vista.getVerTabla());//limpiar la ventana antes de mostrar otra
	    		JPFormularioEmpleados a=new JPFormularioEmpleados();
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


