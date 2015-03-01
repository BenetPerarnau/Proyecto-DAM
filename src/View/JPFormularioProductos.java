package View;

import java.awt.Dimension;
import java.awt.Image;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;

import Constants.Constant;
import Controler.ControladorROOT;

public class JPFormularioProductos extends JPanel {

	private JTextField tfname;
	private JTextField tfcod;
	private JTextField tfdes;
	private JTextField tfprecio;
	private JTextField tfstock;
	private JTextField tfproveedor;
	private JButton btnEliminar;
	private JButton btnGuardar;
	private JLabel lbfoto;
	private JButton btnActualizar;

	/**
	 * Create the panel.
	 */
	public JPFormularioProductos() {
		setLayout(null);
		
		JLabel lbname = new JLabel("Nombre");
		lbname.setBounds(6, 71, 61, 16);
		add(lbname);
		
		tfname = new JTextField();
		tfname.setBounds(105, 65, 134, 28);
		add(tfname);
		tfname.setColumns(10);
		
		JLabel lbcod = new JLabel("COD");
		lbcod.setBounds(6, 43, 61, 16);
		add(lbcod);
		
		tfcod = new JTextField();
		tfcod.setBounds(105, 37, 134, 28);
		add(tfcod);
		tfcod.setColumns(10);
		
		JLabel lbddesc = new JLabel("Descripción");
		lbddesc.setBounds(6, 98, 75, 16);
		add(lbddesc);
		
		JLabel lbstock = new JLabel("Stock");
		lbstock.setBounds(6, 154, 77, 16);
		add(lbstock);
		
		JLabel lbproveedor = new JLabel("Proveedor");
		lbproveedor.setBounds(6, 182, 75, 16);
		add(lbproveedor);
		
		JLabel lbprecio = new JLabel("Precio");
		lbprecio.setBounds(6, 126, 61, 16);
		add(lbprecio);
		
		tfdes = new JTextField();
		tfdes.setColumns(10);
		tfdes.setBounds(105, 92, 134, 28);
		add(tfdes);
		
		tfprecio = new JTextField();
		tfprecio.setColumns(10);
		tfprecio.setBounds(105, 120, 134, 28);
		add(tfprecio);
		
		tfstock = new JTextField();
		tfstock.setColumns(10);
		tfstock.setBounds(105, 148, 134, 28);
		add(tfstock);
		
		tfproveedor = new JTextField();
		tfproveedor.setColumns(10);
		tfproveedor.setBounds(105, 176, 134, 28);
		add(tfproveedor);
		
		lbfoto = new JLabel("");
		lbfoto.setBounds(251, 64, 216, 125);
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

	public JTextField getTfproveedor() {return tfproveedor;}
	public JTextField getTfname() {return tfname;}
	public JTextField getTfcod() {return tfcod;}
	public JTextField getTfdes() {return tfdes;}
	public JTextField getTfprecio() {return tfprecio;}
	public JTextField getTfstock() {return tfstock;}
	public JButton getBtn(){return btnEliminar;}
	public JButton getBtnGuardar() {return btnGuardar;}
	public JButton getBtnActualizar() {return btnActualizar;}

	public void addListeners(ControladorROOT control){
		btnEliminar.addActionListener(control);
		btnGuardar.addActionListener(control);
		btnActualizar.addActionListener(control);
	}

	public void setTfname(String tfname) {
		this.tfname.setText(tfname);
	}

	public void setTfcod(String tfcod) {
		this.tfcod.setText(tfcod);
	}
	public void setTfdes(String Tfdes) {
		this.tfdes.setText(Tfdes);
	}
	public void setTfprecio(String precio) {
		this.tfprecio.setText(precio);
	}
	public void setTfstock(String stock) {
		this.tfstock.setText(stock);
	}
	
	public void setTfproveedor(String proveedor) {
		this.tfproveedor.setText( proveedor);
	}
	public void rellenarTodo(String [] array){
		tfcod.setText(array[1]);
		tfname.setText(array[2]);
		tfdes.setText(array[3]);
		tfprecio.setText(array[4]);
		tfstock.setText(array[5]);
		tfproveedor.setText(array[6]);
		
		try {
			ImageIcon fot = new ImageIcon(new URL("http://"+Constant.IP_SERVER+"/ERP/FotosProductos/default.jpg"));
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
		tfcod.setEditable(false);
		tfname.setEditable(false);
		tfdes.setEditable(false);
		tfprecio.setEditable(false);
		tfstock.setEditable(false);
		tfproveedor.setEditable(false);
	}
	public void activar(){
		tfcod.setEditable(false);
		tfname.setEditable(true);
		tfdes.setEditable(true);
		tfprecio.setEditable(true);
		tfstock.setEditable(true);
		tfproveedor.setEditable(true);
	}
}
