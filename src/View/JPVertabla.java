package View;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JScrollPane;

import java.awt.Color;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.border.LineBorder;
import javax.swing.event.AncestorListener;
import javax.swing.event.TableModelListener;
import javax.swing.JButton;

import Controler.ControladorROOT;

public class JPVertabla extends JPanel {
	private JScrollPane scrollPane;
	private JTable table;
	private JPFormularioClientes formClientes;
	private JPFormularioEmpleados formEmpleados;
	private JPFormularioProveedores formProveedores;
	private JPFormularioProductos formProductos;
	
	public JPVertabla() {

	}
	
	public JPVertabla(Object[][]datos, String[] cabecera) {
	       
			this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			table = new JTable(datos, cabecera);
			
	        //table.setPreferredScrollableViewportSize(new Dimension(1100, 50));

	        //Create the scroll pane and add the table to it. 
	        scrollPane = new JScrollPane(table,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
	        								   JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	        scrollPane.setPreferredSize(new Dimension(700, 300));
	        //table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	        table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
	        
	        //Add the scroll pane to this window.
	        formClientes=new JPFormularioClientes();
	        formEmpleados=new JPFormularioEmpleados();
	        formProveedores=new JPFormularioProveedores();
	        formProductos=new JPFormularioProductos();
	        
	        formClientes.setVisible(false);
	        formEmpleados.setVisible(false);
	        formProveedores.setVisible(false);
	        formProductos.setVisible(false);
	        
	        add(scrollPane); 
	        add(formClientes);
	        add(formEmpleados);
	        add(formProveedores);
	        add(formProductos);
	        
	}

	public void addlistenersToTable(ControladorROOT control){
		//scrollPane.addMouseListener((MouseListener) control);
		//table.getModel().addTableModelListener((TableModelListener) control);
		table.addMouseListener((MouseListener) control);
		//table.addAncestorListener((AncestorListener) control);
	}

	public JTable getTable() {
		return table;
	}

	public void setTable(JTable table) {
		this.table = table;
	}
	public JPFormularioClientes getFormC(){
		if(formEmpleados!=null)this.formEmpleados.setVisible(false);
		if(formProveedores!=null)this.formProveedores.setVisible(false);
		if(formProductos!=null)this.formProductos.setVisible(false);
		if(formClientes!=null)this.formClientes.setVisible(true);
		return formClientes;
	}
	public JPFormularioEmpleados getFormE(){
		if(formClientes!=null)this.formClientes.setVisible(false);
		if(formProveedores!=null)this.formProveedores.setVisible(false);
		if(formProductos!=null)this.formProductos.setVisible(false);
		if(formEmpleados!=null)this.formEmpleados.setVisible(true);
		return formEmpleados;
	}
	public JPFormularioProveedores getFormP(){
		if(formClientes!=null)this.formClientes.setVisible(false);
		if(formEmpleados!=null)this.formEmpleados.setVisible(false);
		if(formProductos!=null)this.formProductos.setVisible(false);
		if(formProveedores!=null)this.formProveedores.setVisible(true);
		return formProveedores;
	}
	public JPFormularioProductos getFormProductos(){
		if(formClientes!=null)this.formClientes.setVisible(false);
		if(formEmpleados!=null)this.formEmpleados.setVisible(false);
		if(formProveedores!=null)this.formProveedores.setVisible(false);
		if(formProductos!=null)this.formProductos.setVisible(true);
		return formProductos;
	}
	
}
