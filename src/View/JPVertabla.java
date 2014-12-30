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
	private JPFormularioClientes form1;
	private JPFormularioEmpleados form2;
	private JPFormularioProveedores form3;
	private JPFormularioProductos form4;
	
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
	        form1=new JPFormularioClientes();
	        form2=new JPFormularioEmpleados();
	        form3=new JPFormularioProveedores();
	        form4=new JPFormularioProductos();
	        
	        form1.setVisible(false);
	        form2.setVisible(false);
	        form3.setVisible(false);
	        form4.setVisible(false);
	        
	        add(scrollPane); 
	        add(form1);
	        add(form2);
	        add(form3);
	        add(form4);
	        
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
		if(form2!=null)this.form2.setVisible(false);
		if(form3!=null)this.form3.setVisible(false);
		if(form4!=null)this.form4.setVisible(false);
		if(form1!=null)this.form1.setVisible(true);
		return form1;
	}
	public JPFormularioEmpleados getFormE(){
		if(form1!=null)this.form1.setVisible(false);
		if(form3!=null)this.form3.setVisible(false);
		if(form4!=null)this.form4.setVisible(false);
		if(form2!=null)this.form2.setVisible(true);
		return form2;
	}
	public JPFormularioProveedores getFormP(){
		if(form1!=null)this.form1.setVisible(false);
		if(form2!=null)this.form2.setVisible(false);
		if(form4!=null)this.form4.setVisible(false);
		if(form3!=null)this.form3.setVisible(true);
		return form3;
	}
	public JPFormularioProductos getFormProductos(){
		if(form1!=null)this.form1.setVisible(false);
		if(form2!=null)this.form2.setVisible(false);
		if(form3!=null)this.form3.setVisible(false);
		if(form4!=null)this.form4.setVisible(true);
		return form4;
	}
	
}
