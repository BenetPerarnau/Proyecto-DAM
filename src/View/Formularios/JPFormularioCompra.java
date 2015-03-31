package View.Formularios;

import java.awt.Dimension;
import java.awt.Image;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;

import Constants.Constant;
import Controler.ControladorCOMPRA;
import Controler.ControladorROOT;

public class JPFormularioCompra extends JPanel {

	private JTextField tfcantidad;
	private JTextField tfid;
	private JTextField tfproducto;
	private JComboBox comboProducto;
	private JTextField tfproveedor;
	private JComboBox comboProveedor;
	
	private JButton btnEliminar;
	private JButton btnGuardar;
	private JLabel lbfoto;
	private JButton btnActualizar;

	/**
	 * Create the panel.
	 */
	public JPFormularioCompra() {
		setLayout(null);
		
		JLabel lbcif = new JLabel("ID");
		lbcif.setBounds(6, 43, 77, 16);
		add(lbcif);
		
		tfid = new JTextField();
		tfid.setBounds(95, 37, 134, 28);
		add(tfid);
		tfid.setColumns(10);
		
		JLabel lbname = new JLabel("CANTIDAD");
		lbname.setBounds(6, 71, 77, 16);
		add(lbname);
		
		tfcantidad = new JTextField();
		tfcantidad.setBounds(95, 65, 134, 28);
		add(tfcantidad);
		tfcantidad.setColumns(10);	
		
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
		
		JLabel lbdescripcion = new JLabel("PROVEEDOR");
		lbdescripcion.setBounds(6, 126, 77, 16);
		add(lbdescripcion);
		
		tfproveedor = new JTextField();
		tfproveedor.setColumns(10);
		tfproveedor.setBounds(95, 118, 134, 28);
		add(tfproveedor);
		
		comboProveedor=new JComboBox();
		comboProveedor.setBounds(95, 118, 134, 28);
		comboProveedor.setVisible(false);
		comboProveedor.addItem("Proveedores...");
		add(comboProveedor);
			
		lbfoto = new JLabel("");
		lbfoto.setBounds(241, 64, 216, 125);
		add(lbfoto);
		this.setPreferredSize(new Dimension(700, 300));
		
		btnEliminar = new JButton("Eliminar");
		btnEliminar.setBounds(112, 209, 117, 29);
		btnEliminar.setVisible(false);
		add(btnEliminar);
		
		btnGuardar = new JButton("Guardar");
		btnGuardar.setBounds(112, 209, 117, 29);
		btnGuardar.setVisible(false);
		add(btnGuardar);
		
		btnActualizar = new JButton("Actualizar");
		btnActualizar.setBounds(112, 209, 117, 29);
		btnActualizar.setVisible(false);
		add(btnActualizar);
	}

	public JTextField getTfCantidad() {return tfcantidad;}
	public JTextField getTfId() {return tfid;}
	public JTextField getTfProducto() {return tfproducto;}
	public JTextField getTfProveedor() {return tfproveedor;}
	
	public JComboBox getComboProducto(){return comboProducto;}
	public JComboBox getComboProveedor(){return comboProveedor;}
	
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

	public void setTfCantidad(String tfname) {
		this.tfcantidad.setText(tfname);
	}

	public void setTfId(String tfdni) {
		this.tfid.setText(tfdni);
	}
	public void setTfProducto(String tfdireccion) {
		this.tfproducto.setText(tfdireccion);
	}
	public void setTfProveedor(String tfmovil) {
		this.tfproveedor.setText(tfmovil);
	}
	
	public void rellenarTodo(String [] array){
		tfid.setText(array[0]);
		tfcantidad.setText(array[1]);
		tfproducto.setText(array[2]);
		tfproveedor.setText(array[3]);
		
		try {
			ImageIcon fot = new ImageIcon(new URL("http://"+Constant.IP_SERVER+"/ERP/FotosCompras/compra.jpg"));
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
		tfcantidad.setEditable(false);
		tfproducto.setEditable(false);
		tfproveedor.setEditable(false);
	}
	public void activar(){
		tfid.setEditable(false);
		tfcantidad.setEditable(true);
		tfproducto.setEditable(true);
		tfproveedor.setEditable(true);
	}
}
