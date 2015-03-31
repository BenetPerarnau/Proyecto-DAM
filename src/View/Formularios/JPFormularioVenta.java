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

public class JPFormularioVenta extends JPanel {

	private JTextField tfid;
	private JTextField tfclient; 
	private JComboBox comboClient;
	private JTextField tfproduct;
	private JComboBox comboProducto;
	private JTextField tfcantidad;
	private JTextField tfprecio;
	private JTextField tffactura;
	private JComboBox comboFactura;
	private JButton btnEliminar;
	private JButton btnGuardar;
	private JLabel lbfoto;
	private JButton btnActualizar;

	/**
	 * Create the panel.
	 */
	public JPFormularioVenta() {
		setLayout(null);
		

		tfid = new JTextField();
		tfid.setBounds(95, 65, 134, 28);
		tfid.setVisible(false);
		add(tfid);
		
		JLabel lbcif = new JLabel("CLIENTE");
		lbcif.setBounds(6, 71, 77, 16);
		add(lbcif);
		
		tfclient = new JTextField();
		tfclient.setBounds(95, 65, 134, 28);
		add(tfclient);
		tfclient.setColumns(10);
		
		comboClient = new JComboBox();
		comboClient.setBounds(95, 65, 134, 28);
		comboClient.addItem("Clientes...");
		comboClient.setVisible(false);
		add(comboClient);
		
	
		JLabel lbdireccion = new JLabel("PRODUCTO");
		lbdireccion.setBounds(6, 98, 77, 16);
		add(lbdireccion);
		
		tfproduct = new JTextField();
		tfproduct.setColumns(10);
		tfproduct.setBounds(95, 92, 134, 28);
		add(tfproduct);
		
		comboProducto = new JComboBox();
		comboProducto.setBounds(95, 92, 134, 28);
		comboProducto.addItem("Productos...");
		comboProducto.setVisible(false);
		add(comboProducto);
		
		JLabel lbdescripcion = new JLabel("PRECIO");
		lbdescripcion.setBounds(6, 154, 77, 16);
		add(lbdescripcion);
		
		tfprecio = new JTextField();
		tfprecio.setColumns(10);
		tfprecio.setBounds(95, 148, 134, 28);
		add(tfprecio);
		
		JLabel lbcompras = new JLabel("FACTURA");
		lbcompras.setBounds(6, 182, 77, 16);
		add(lbcompras);
		
		tffactura = new JTextField();
		tffactura.setColumns(10);
		tffactura.setBounds(95, 176, 134, 28);
		add(tffactura);
		
		comboFactura = new JComboBox();
		comboFactura.setBounds(95, 176, 134, 28);
		comboFactura.addItem("Facturas...");
		comboFactura.setVisible(false);
		add(comboFactura);
		
		JLabel lbtelefono = new JLabel("CANTIDAD");
		lbtelefono.setBounds(6, 126, 77, 16);
		add(lbtelefono);
		
		tfcantidad = new JTextField();
		tfcantidad.setColumns(10);
		tfcantidad.setBounds(95, 120, 134, 28);
		add(tfcantidad);
		

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

	public JTextField getTfFactura() {return tffactura;}
	public JTextField getTfId() {return tfid;}
	public JTextField getTfClient() {return tfclient;}
	public JTextField getTfProduct() {return tfproduct;}
	public JTextField getTfCantidad() {return tfcantidad;}
	public JTextField getTfPrecio() {return tfprecio;}
	
	public JComboBox getComboCliente(){return comboClient;}
	public JComboBox getComboProducto(){return comboProducto;}
	public JComboBox getComboFactura(){return comboFactura;}
	
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
		this.tfid.setText(tfname);
	}

	public void setTfClient(String tfdni) {
		this.tfclient.setText(tfdni);
	}
	public void setTfProduct(String tfdireccion) {
		this.tfproduct.setText(tfdireccion);
	}
	public void setTfCantidad(String tftelefono) {
		this.tfcantidad.setText(tftelefono);
	}
	public void setTfPrecio(String tfmovil) {
		this.tfprecio.setText(tfmovil);
	}
	
	public void setTfFactura(String tfcompras) {
		this.tffactura.setText( tfcompras);
	}
	public void rellenarTodo(String [] array){
		tfid.setText(array[0]);
		tfclient.setText(array[1]);	
		tfproduct.setText(array[2]);
		tfcantidad.setText(array[3]);
		tfprecio.setText(array[4]);
		tffactura.setText(array[5]);
		
		try {
			ImageIcon fot = new ImageIcon(new URL("http://"+Constant.IP_SERVER+"/ERP/FotosVentas/venta.jpg"));
			Icon icono = new ImageIcon(fot.getImage().getScaledInstance(lbfoto.getWidth(), 
					lbfoto.getHeight(), Image.SCALE_DEFAULT));
			lbfoto.setIcon(icono);
			lbfoto.repaint();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	public void desactivar(){
		tfclient.setEditable(false);
		//tfid.setEditable(false);
		tfproduct.setEditable(false);
		tfcantidad.setEditable(false);
		tfprecio.setEditable(false);
		tffactura.setEditable(false);
	}
	public void activar(){
		tfclient.setEditable(false);
		//tfid.setEditable(true);
		tfproduct.setEditable(true);
		tfcantidad.setEditable(true);
		tfprecio.setEditable(true);
		tffactura.setEditable(true);
	}
}
