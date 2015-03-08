package View.Formularios;

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
import Controler.ControladorCOMPRA;
import Controler.ControladorROOT;

public class JPFormularioProveedores extends JPanel {

	private JTextField tfname;
	private JTextField tfcif;
	private JTextField tfdireccion;
	private JTextField tftelefono;
	private JTextField tfdescripcion;
	private JTextField tfcompras;
	private JButton btnEliminar;
	private JButton btnGuardar;
	private JLabel lbfoto;
	private JButton btnActualizar;

	/**
	 * Create the panel.
	 */
	public JPFormularioProveedores() {
		setLayout(null);
		
		JLabel lbname = new JLabel("Nombre");
		lbname.setBounds(6, 71, 61, 16);
		add(lbname);
		
		tfname = new JTextField();
		tfname.setBounds(95, 65, 134, 28);
		add(tfname);
		tfname.setColumns(10);
		
		JLabel lbcif = new JLabel("CIF");
		lbcif.setBounds(6, 43, 61, 16);
		add(lbcif);
		
		tfcif = new JTextField();
		tfcif.setBounds(95, 37, 134, 28);
		add(tfcif);
		tfcif.setColumns(10);
		
		JLabel lbdireccion = new JLabel("Dirección");
		lbdireccion.setBounds(6, 98, 61, 16);
		add(lbdireccion);
		
		JLabel lbdescripcion = new JLabel("Descripción");
		lbdescripcion.setBounds(6, 154, 77, 16);
		add(lbdescripcion);
		
		JLabel lbcompras = new JLabel("Compras");
		lbcompras.setBounds(6, 182, 61, 16);
		add(lbcompras);
		
		JLabel lbtelefono = new JLabel("Telefono");
		lbtelefono.setBounds(6, 126, 61, 16);
		add(lbtelefono);
		
		tfdireccion = new JTextField();
		tfdireccion.setColumns(10);
		tfdireccion.setBounds(95, 92, 134, 28);
		add(tfdireccion);
		
		tftelefono = new JTextField();
		tftelefono.setColumns(10);
		tftelefono.setBounds(95, 120, 134, 28);
		add(tftelefono);
		
		tfdescripcion = new JTextField();
		tfdescripcion.setColumns(10);
		tfdescripcion.setBounds(95, 148, 134, 28);
		add(tfdescripcion);
		
		tfcompras = new JTextField();
		tfcompras.setColumns(10);
		tfcompras.setBounds(95, 176, 134, 28);
		add(tfcompras);
		
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

	public JTextField getTfcompras() {return tfcompras;}
	public JTextField getTfname() {return tfname;}
	public JTextField getTfcif() {return tfcif;}
	public JTextField getTfdireccion() {return tfdireccion;}
	public JTextField getTftelefono() {return tftelefono;}
	public JTextField getTfdescripcion() {return tfdescripcion;}
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
		this.tfname.setText(tfname);
	}

	public void setTfdni(String tfdni) {
		this.tfcif.setText(tfdni);
	}
	public void setTfdireccion(String tfdireccion) {
		this.tfdireccion.setText(tfdireccion);
	}
	public void setTftelefono(String tftelefono) {
		this.tftelefono.setText(tftelefono);
	}
	public void setTfmovil(String tfmovil) {
		this.tfdescripcion.setText(tfmovil);
	}
	
	public void setTfcompras(String tfcompras) {
		this.tfcompras.setText( tfcompras);
	}
	public void rellenarTodo(String [] array){
		tfcif.setText(array[0]);
		tfname.setText(array[1]);
		tfdireccion.setText(array[2]);
		tftelefono.setText(array[3]);
		tfdescripcion.setText(array[4]);
		tfcompras.setText(array[5]);
		
		try {
			ImageIcon fot = new ImageIcon(new URL("http://"+Constant.IP_SERVER+"/ERP/FotosProveedores/micro.jpg"));
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
		tfcif.setEditable(false);
		tfname.setEditable(false);
		tfdireccion.setEditable(false);
		tftelefono.setEditable(false);
		tfdescripcion.setEditable(false);
		tfcompras.setEditable(false);
	}
	public void activar(){
		tfcif.setEditable(false);
		tfname.setEditable(true);
		tfdireccion.setEditable(true);
		tftelefono.setEditable(true);
		tfdescripcion.setEditable(true);
		tfcompras.setEditable(true);
	}
}
