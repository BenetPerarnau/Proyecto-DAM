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

import View.Formularios.JPFormularioClientes;
import View.Formularios.JPFormularioCompra;
import View.Formularios.JPFormularioEmpleados;
import View.Formularios.JPFormularioFactura;
import View.Formularios.JPFormularioProductos;
import View.Formularios.JPFormularioProveedores;
import View.Formularios.JPFormularioVenta;
import Controler.ControladorALMAC;
import Controler.ControladorCOMPRA;
import Controler.ControladorCONTA;
import Controler.ControladorROOT;
import Controler.ControladorVENTA;

public class JPVertabla extends JPanel {
	private JScrollPane scrollPane;
	private JTable table;
	private JPFormularioClientes formClientes;
	private JPFormularioEmpleados formEmpleados;
	private JPFormularioProveedores formProveedores;
	private JPFormularioProductos formProductos;
	private JPFormularioVenta formVentas;
	private JPFormularioCompra formCompras;
	private JPFormularioFactura formFacturas;
	
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
	        formVentas=new JPFormularioVenta();
	        formCompras=new JPFormularioCompra();
	        formFacturas=new JPFormularioFactura();
	        
	        formClientes.setVisible(false);
	        formEmpleados.setVisible(false);
	        formProveedores.setVisible(false);
	        formProductos.setVisible(false);
	        formVentas.setVisible(false);
	        formCompras.setVisible(false);
	        formFacturas.setVisible(false);
	        
	        add(scrollPane); 
	        add(formClientes);
	        add(formEmpleados);
	        add(formProveedores);
	        add(formProductos);
	        add(formVentas);
	        add(formCompras);
	        add(formFacturas);
	        
	}

	public void addlistenersToTable(ControladorROOT control){
		//scrollPane.addMouseListener((MouseListener) control);
		//table.getModel().addTableModelListener((TableModelListener) control);
		table.addMouseListener((MouseListener) control);
		//table.addAncestorListener((AncestorListener) control);
	}
	public void addlistenersToTable(ControladorVENTA control){
		//scrollPane.addMouseListener((MouseListener) control);
		//table.getModel().addTableModelListener((TableModelListener) control);
		table.addMouseListener((MouseListener) control);
		//table.addAncestorListener((AncestorListener) control);
	}
	public void addlistenersToTable(ControladorCOMPRA control){
		//scrollPane.addMouseListener((MouseListener) control);
		//table.getModel().addTableModelListener((TableModelListener) control);
		table.addMouseListener((MouseListener) control);
		//table.addAncestorListener((AncestorListener) control);
	}
	public void addlistenersToTable(ControladorCONTA control){
		//scrollPane.addMouseListener((MouseListener) control);
		//table.getModel().addTableModelListener((TableModelListener) control);
		table.addMouseListener((MouseListener) control);
		//table.addAncestorListener((AncestorListener) control);
	}
	public void addlistenersToTable(ControladorALMAC control){
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
		clean();
		if(formClientes!=null)this.formClientes.setVisible(true);
		return formClientes;
	}
	public JPFormularioEmpleados getFormE(){
		clean();
		if(formEmpleados!=null)this.formEmpleados.setVisible(true);
		return formEmpleados;
	}
	public JPFormularioProveedores getFormP(){
		clean();
		if(formProveedores!=null)this.formProveedores.setVisible(true);
		return formProveedores;
	}
	public JPFormularioProductos getFormProductos(){
		clean();
		if(formProductos!=null)this.formProductos.setVisible(true);
		return formProductos;
	}
	public JPFormularioVenta getFormVentas(){
		clean();
		if(formVentas!=null)this.formVentas.setVisible(true);
		return formVentas;
	}
	public JPFormularioCompra getFormCompras(){
		clean();
		if(formCompras!=null)this.formCompras.setVisible(true);
		return formCompras;
	}
	public JPFormularioFactura getFormFacturas(){
		clean();
		if(formFacturas!=null)this.formFacturas.setVisible(true);
		return formFacturas;
	}
	public void clean(){
		if(formEmpleados!=null)this.formEmpleados.setVisible(false);
		if(formProveedores!=null)this.formProveedores.setVisible(false);
		if(formProductos!=null)this.formProductos.setVisible(false);
		if(formVentas!=null)this.formVentas.setVisible(false);
		if(formCompras!=null)this.formCompras.setVisible(false);
		if(formFacturas!=null)this.formFacturas.setVisible(false);
		if(formClientes!=null)this.formClientes.setVisible(false);
	}
}
