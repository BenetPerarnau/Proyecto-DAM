package View.Formularios;

import java.awt.Dimension;
import java.awt.Image;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

import Constants.Constant;
import Controler.ControladorCONTA;
import Controler.ControladorROOT;
import Controler.ControladorVENTA;

public class JPFormularioEmpleados extends JPanel {
	private JTextField tfname;
	private JTextField tfapellido;
	private JTextField tfdni;
	private JTextField tfdireccion;
	private JTextField tftelefono;
	private JTextField tfmovil;
	private JTextField tfpuesto;
	private JTextField tfsalario;
	private JTextField tfcuenta;
	private JButton btnEliminar;
	private JLabel lbfoto;
	private JButton btnGuardar;
	private JButton btnActualizar;
	/**
	 * Create the panel.
	 */
	public JPFormularioEmpleados() {
		setLayout(null);
		
		JLabel lbname = new JLabel("Nombre");
		lbname.setBounds(6, 71, 61, 16);
		add(lbname);
		
		JLabel lbapellido = new JLabel("Apellido");
		lbapellido.setBounds(6, 99, 61, 16);
		add(lbapellido);
		
		tfname = new JTextField();
		tfname.setBounds(95, 65, 134, 28);
		add(tfname);
		tfname.setColumns(10);
		
		tfapellido = new JTextField();
		tfapellido.setBounds(95, 93, 134, 28);
		add(tfapellido);
		tfapellido.setColumns(10);
		
		JLabel lbdni = new JLabel("DNI");
		lbdni.setBounds(6, 43, 61, 16);
		add(lbdni);
		
		tfdni = new JTextField();
		tfdni.setBounds(95, 37, 134, 28);
		add(tfdni);
		tfdni.setColumns(10);
		
		JLabel lbdireccion = new JLabel("Dirección");
		lbdireccion.setBounds(6, 127, 61, 16);
		add(lbdireccion);
		
		JLabel lbmovil = new JLabel("Movil");
		lbmovil.setBounds(6, 183, 61, 16);
		add(lbmovil);
		
		JLabel lbpuesto = new JLabel("Puesto");
		lbpuesto.setBounds(6, 211, 61, 16);
		add(lbpuesto);
		
		JLabel lbtelefono = new JLabel("Telefono");
		lbtelefono.setBounds(6, 155, 61, 16);
		add(lbtelefono);
		
		JLabel lbsalario = new JLabel("Salario");
		lbsalario.setBounds(6, 239, 61, 16);
		add(lbsalario);
		
		JLabel lbcuenta = new JLabel("Nº Cuenta");
		lbcuenta.setBounds(6, 267, 73, 16);
		add(lbcuenta);
		
		tfdireccion = new JTextField();
		tfdireccion.setColumns(10);
		tfdireccion.setBounds(95, 121, 134, 28);
		add(tfdireccion);
		
		tftelefono = new JTextField();
		tftelefono.setColumns(10);
		tftelefono.setBounds(95, 149, 134, 28);
		add(tftelefono);
		
		tfmovil = new JTextField();
		tfmovil.setColumns(10);
		tfmovil.setBounds(95, 177, 134, 28);
		add(tfmovil);
		
		tfpuesto = new JTextField();
		tfpuesto.setColumns(10);
		tfpuesto.setBounds(95, 205, 134, 28);
		add(tfpuesto);
		
		tfsalario = new JTextField();
		tfsalario.setColumns(10);
		tfsalario.setBounds(95, 233, 134, 28);
		add(tfsalario);
		
		tfcuenta = new JTextField();
		tfcuenta.setColumns(10);
		tfcuenta.setBounds(95, 261, 251, 28);
		add(tfcuenta);
		
		lbfoto = new JLabel("");
		lbfoto.setBounds(352, 43, 183, 240);
		add(lbfoto);
		this.setPreferredSize(new Dimension(700, 329));
		
		btnEliminar = new JButton("Eliminar");
		btnEliminar.setBounds(112, 294, 117, 29);
		btnEliminar.setVisible(false);
		add(btnEliminar);
		
		btnGuardar = new JButton("Guardar");
		btnGuardar.setBounds(112, 294, 117, 29);
		btnGuardar.setVisible(false);
		add(btnGuardar);
		
		btnActualizar = new JButton("Actualizar");
		btnActualizar.setBounds(112, 294, 117, 29);
		btnActualizar.setVisible(false);
		add(btnActualizar);
	}

	public JTextField getTfname() {return tfname;}
	public JTextField getTfapellido() {return tfapellido;}
	public JTextField getTfdni() {return tfdni;}
	public JTextField getTfdireccion() {return tfdireccion;}
	public JTextField getTftelefono() {return tftelefono;}
	public JTextField getTfmovil() {return tfmovil;}
	public JTextField getTfpuesto() {return tfpuesto;}
	public JTextField getTfsalario() {return tfsalario;}
	public JTextField getTfcuenta() {return tfcuenta;}
	public JButton getBtn(){return btnEliminar;}
	public JButton getBtnGuardar() {return btnGuardar;}
	public JButton getBtnActualizar() {return btnActualizar;}

	public void addListeners(ControladorROOT control){
		btnEliminar.addActionListener(control);
		btnGuardar.addActionListener(control);
		btnActualizar.addActionListener(control);
	}
	public void addListeners(ControladorCONTA control){
		btnEliminar.addActionListener(control);
		btnGuardar.addActionListener(control);
		btnActualizar.addActionListener(control);
	}
	public void setTfname(String tfname) {
		this.tfname.setText(tfname);
	}
	public void setTfapellido(String tfapellido) {
		this.tfapellido.setText(tfapellido);
	}
	public void setTfdni(String tfdni) {
		this.tfdni.setText(tfdni);
	}
	public void setTfdireccion(String tfdireccion) {
		this.tfdireccion.setText(tfdireccion);
	}
	public void setTftelefono(String tftelefono) {
		this.tftelefono.setText(tftelefono);
	}
	public void setTfmovil(String tfmovil) {
		this.tfmovil.setText(tfmovil);
	}
	public void setTfpuesto(String tfpuesto) {
		this.tfpuesto.setText(tfpuesto);
	}
	public void setTfsalario(String tfsalario) {
		this.tfsalario.setText(tfsalario);
	}
	public void setTfcuenta(String tfcuenta) {
		this.tfcuenta.setText(tfcuenta);
	}
	
	public void rellenarTodo(String [] array){
		tfdni.setText(array[0]);
		tfname.setText(array[1]);
		tfapellido.setText(array[2]);
		tfdireccion.setText(array[3]);
		tftelefono.setText(array[4]);
		tfmovil.setText(array[5]);
		tfpuesto.setText(array[6]);
		tfsalario.setText(array[7]);
		tfcuenta.setText(insertGionesInCuenta(array[8]));
		try {
			ImageIcon fot = new ImageIcon(new URL("http://"+Constant.IP_SERVER+"/ERP/FotosEmpleados/def.jpg"));
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
		tfdni.setEditable(false);
		tfname.setEditable(false);
		tfapellido.setEditable(false);
		tfdireccion.setEditable(false);
		tftelefono.setEditable(false);
		tfmovil.setEditable(false);
		tfpuesto.setEditable(false);
		tfsalario.setEditable(false);
		tfcuenta.setEditable(false);
	}
	public void activar(){
		tfdni.setEditable(false);
		tfname.setEditable(true);
		tfapellido.setEditable(true);
		tfdireccion.setEditable(true);
		tftelefono.setEditable(true);
		tfmovil.setEditable(true);
		tfpuesto.setEditable(true);
		tfsalario.setEditable(true);
		tfcuenta.setEditable(true);
	}
	
	public String insertGionesInCuenta(String cuentaSin){
		String cuentaCon="";							 //    4   8   12  16  20
														 //012345678911234567892123
		StringBuffer str1 = new StringBuffer (cuentaSin);//000011112222333344445555
		String aux = "-";
		str1.insert (4, aux);
		str1.insert(9, aux);
		str1.insert(14, aux);
		str1.insert(19, aux);
		str1.insert(24, aux);
		
		cuentaCon=str1.toString();
		
		return cuentaCon;
	}
}
