package View.Formularios;

import java.awt.Dimension;
import java.awt.Image;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;

import org.jdesktop.swingx.JXDatePicker;

import Constants.Constant;
import Controler.ControladorCOMPRA;
import Controler.ControladorROOT;

public class JPFormularioFactura extends JPanel {
	
	//int id,String cliente, String producto, int cantidad, float precio, boolean pagada, Date fecha
	private JTextField tfid;
	private JTextField tfcliente;
	private JComboBox comboCliente;
	private JTextField tfproducto;
	private JComboBox comboProducto;
	private JTextField tfcantidad;
	private JTextField tfprecio;
	private JTextField tfpagada;
	private JTextField tffecha;
	private JXDatePicker picker;

	private JButton btnEliminar;
	private JButton btnGuardar;
	private JLabel lbfoto;
	private JLabel lbfecha;
	private JButton btnActualizar;

	/**
	 * Create the panel.
	 */
	public JPFormularioFactura() {
		setLayout(null);
		
		
		JLabel lbcif = new JLabel("ID");
		lbcif.setBounds(6, 43, 77, 16);
		add(lbcif);
		
		tfid = new JTextField();
		tfid.setBounds(95, 37, 134, 28);
		add(tfid);
		tfid.setColumns(10);
		
		JLabel lbname = new JLabel("CLIENTE");
		lbname.setBounds(6, 71, 77, 16);
		add(lbname);
		
		tfcliente = new JTextField();
		tfcliente.setBounds(95, 65, 134, 28);
		add(tfcliente);
		tfcliente.setColumns(10);
		
		comboCliente=new JComboBox();
		comboCliente.setBounds(95, 65, 134, 28);
		comboCliente.setVisible(false);
		comboCliente.addItem("Clientes...");
		add(comboCliente);
	
		JLabel lbdireccion = new JLabel("PRODUCTO");
		lbdireccion.setBounds(6, 98, 77, 16);
		add(lbdireccion);
		
		tfproducto = new JTextField();
		tfproducto.setColumns(10);
		tfproducto.setBounds(95, 92, 134, 28);
		add(tfproducto);
		
		comboProducto=new JComboBox();
		comboProducto.setBounds(95, 92, 134, 28);
		comboProducto.setVisible(false);
		comboProducto.addItem("Productos...");
		add(comboProducto);
		
		JLabel lbdescripcion = new JLabel("CANTIDAD");
		lbdescripcion.setBounds(6, 154, 77, 16);
		add(lbdescripcion);
		
		tfcantidad = new JTextField();
		tfcantidad.setColumns(10);
		tfcantidad.setBounds(95, 148, 134, 28);
		add(tfcantidad);
		
		JLabel lbcompras = new JLabel("PRECIO");
		lbcompras.setBounds(6, 182, 77, 16);
		add(lbcompras);
		
		tfprecio = new JTextField();
		tfprecio.setColumns(10);
		tfprecio.setBounds(95, 176, 134, 28);
		add(tfprecio);
		
		JLabel lbtelefono = new JLabel("PAGADA");
		lbtelefono.setBounds(6, 126, 77, 16);
		add(lbtelefono);
			
		tfpagada = new JTextField();
		tfpagada.setColumns(10);
		tfpagada.setBounds(95, 120, 134, 28);
		add(tfpagada);
		//
		lbfecha = new JLabel("FECHA");
		lbfecha.setBounds(6, 211, 77, 16);
		add(lbfecha);
		
		tffecha = new JTextField();
		tffecha.setColumns(10);
		tffecha.setBounds(95, 205, 134, 28);
		add(tffecha);			
		//
		picker = new JXDatePicker();
        picker.setDate(Calendar.getInstance().getTime());
        picker.setFormats(new SimpleDateFormat("dd/MM/yyyy"));
        
        picker.setBounds(6, 205, 150, 20);
        picker.setVisible(false);
        add(picker);
		//
		//
		lbfoto = new JLabel("");
		lbfoto.setBounds(241, 64, 216, 125);
		add(lbfoto);
		this.setPreferredSize(new Dimension(700, 300));
		
		btnEliminar = new JButton("Eliminar");
		btnEliminar.setBounds(112, 233, 117, 29);
		btnEliminar.setVisible(false);
		add(btnEliminar);
		
		btnGuardar = new JButton("Guardar");
		btnGuardar.setBounds(112, 233, 117, 29);
		btnGuardar.setVisible(false);
		add(btnGuardar);
		
		btnActualizar = new JButton("Actualizar");
		btnActualizar.setBounds(112, 233, 117, 29);
		btnActualizar.setVisible(false);
		add(btnActualizar);
	}

	public JTextField getTfPrecio() {return tfprecio;}
	public JTextField getTfCliente() {return tfcliente;}
	public JTextField getTfId() {return tfid;}
	public JTextField getTfProducto() {return tfproducto;}
	public JTextField getTfPagada() {return tfpagada;}
	public JTextField getTfCantidad() {return tfcantidad;}
	public JTextField getTfFecha() {return tffecha;}
	
	public JXDatePicker getPickerDate(){return picker;}
	public JLabel getLabelFecha(){return lbfecha;}
	
	public JComboBox getComboCliente(){return comboCliente;}
	public JComboBox getComboProducto(){return comboProducto;}
	
	public JButton getBtn(){return btnEliminar;}
	public JButton getBtnGuardar() {return btnGuardar;}
	public JButton getBtnActualizar() {return btnActualizar;}

	public void addListeners(ControladorROOT control){
		btnEliminar.addActionListener(control);
		btnGuardar.addActionListener(control);
		btnActualizar.addActionListener(control);
	}
	public void addListeners(ControladorCOMPRA control){
		btnEliminar.addActionListener(control);
		btnGuardar.addActionListener(control);
		btnActualizar.addActionListener(control);
	}

	public void setTfname(String tfname) {
		this.tfcliente.setText(tfname);
	}

	public void setTfId(String tfdni) {
		this.tfid.setText(tfdni);
	}
	public void setTfProducto(String tfdireccion) {
		this.tfproducto.setText(tfdireccion);
	}
	public void setTfPagada(String tftelefono) {
		this.tfpagada.setText(tftelefono);
	}
	public void setTfCantidad(String tfmovil) {
		this.tfcantidad.setText(tfmovil);
	}
	public void setTfPrecio(String tfcompras) {
		this.tfprecio.setText( tfcompras);
	}
	public void setTfecha(String tfcompras) {
		this.tffecha.setText( tfcompras);
	}
	public void rellenarTodo(String [] array){
		//int id,String cliente, String producto, int cantidad, float precio, boolean pagada, Date fecha
		tfid.setText(array[2]);
		tfcliente.setText(array[1]);
		tfproducto.setText(array[5]);
		tfpagada.setText(array[3]);
		tfcantidad.setText(array[0]);
		tfprecio.setText(array[4]);
		tffecha.setText(array[6]);
		try {
			ImageIcon fot = new ImageIcon(new URL("http://"+Constant.IP_SERVER+"/ERP/FotosFacturas/factura.jpg"));
			Icon icono = new ImageIcon(fot.getImage().getScaledInstance(lbfoto.getWidth(), 
					lbfoto.getHeight(), Image.SCALE_DEFAULT));
			lbfoto.setIcon(icono);
			lbfoto.repaint();
			//lbfoto.setIcon(new ImageIcon(new URL("http://192.168.1.45/ERP/FotosEmpleados/.jpg")));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void desactivar(){
		tfid.setEditable(false);
		tfcliente.setEditable(false);
		tfproducto.setEditable(false);
		tfpagada.setEditable(false);
		tfcantidad.setEditable(false);
		tfprecio.setEditable(false);
		tffecha.setEditable(false);
	}
	public void activar(){
		tfid.setEditable(false);
		tfcliente.setEditable(true);
		tfproducto.setEditable(true);
		tfpagada.setEditable(true);
		tfcantidad.setEditable(true);
		tfprecio.setEditable(true);
		tffecha.setEditable(true);
	}
}
