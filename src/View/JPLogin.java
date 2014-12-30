package View;

import javax.swing.JPanel;

import java.awt.GridBagLayout;

import javax.swing.JLabel;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;

import Controler.ControladorROOT;
import Model.Lectura;

import javax.swing.JCheckBox;

public class JPLogin extends JPanel {
	private JTextField txtNombre;
	private JPasswordField txtPassword;
	private static JButton btnIngresar;
	private JCheckBox chckbxNewCheckBox;

	/**
	 * Create the panel.
	 */
	public JPLogin() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{31, 85, 141, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 39, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblUsuario = new JLabel("Usuario");
		GridBagConstraints gbc_lblUsuario = new GridBagConstraints();
		gbc_lblUsuario.anchor = GridBagConstraints.WEST;
		gbc_lblUsuario.insets = new Insets(0, 0, 5, 5);
		gbc_lblUsuario.gridx = 1;
		gbc_lblUsuario.gridy = 1;
		add(lblUsuario, gbc_lblUsuario);
		
		txtNombre = new JTextField("");
		GridBagConstraints gbc_txtNombre = new GridBagConstraints();
		gbc_txtNombre.anchor = GridBagConstraints.WEST;
		gbc_txtNombre.insets = new Insets(0, 0, 5, 5);
		gbc_txtNombre.gridx = 2;
		gbc_txtNombre.gridy = 1;
		add(txtNombre, gbc_txtNombre);
		txtNombre.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password");
		GridBagConstraints gbc_lblPassword = new GridBagConstraints();
		gbc_lblPassword.anchor = GridBagConstraints.WEST;
		gbc_lblPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblPassword.gridx = 1;
		gbc_lblPassword.gridy = 2;
		add(lblPassword, gbc_lblPassword);
		
		txtPassword = new JPasswordField("");
		txtPassword.setColumns(10);
		GridBagConstraints gbc_txtPassword = new GridBagConstraints();
		gbc_txtPassword.anchor = GridBagConstraints.WEST;
		gbc_txtPassword.insets = new Insets(0, 0, 5, 5);
		gbc_txtPassword.gridx = 2;
		gbc_txtPassword.gridy = 2;
		add(txtPassword, gbc_txtPassword);
		
		btnIngresar = new JButton("Ingresar");
		GridBagConstraints gbc_btnIngresar = new GridBagConstraints();
		gbc_btnIngresar.anchor = GridBagConstraints.WEST;
		gbc_btnIngresar.insets = new Insets(0, 0, 5, 5);
		gbc_btnIngresar.gridx = 1;
		gbc_btnIngresar.gridy = 4;
		add(btnIngresar, gbc_btnIngresar);

		chckbxNewCheckBox = new JCheckBox("Guardar datos");
		GridBagConstraints gbc_chckbxNewCheckBox = new GridBagConstraints();
		gbc_chckbxNewCheckBox.anchor = GridBagConstraints.WEST;
		gbc_chckbxNewCheckBox.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxNewCheckBox.gridx = 2;
		gbc_chckbxNewCheckBox.gridy = 3;
		add(chckbxNewCheckBox, gbc_chckbxNewCheckBox);
		this.setMaximumSize(new Dimension(200,200));
		isFastLogin();
	}
	
	public void isFastLogin(){
		
		String[] credenciales=Lectura.readFastLogin();
		if(credenciales!=null){
			setTxtNombre(credenciales[0]);
			setTxtPassword(credenciales[1]);
			chckbxNewCheckBox.setSelected(true);
		}
	}
	
	public void addListeners(ControladorROOT control){
		
		this.btnIngresar.addActionListener(control);
		this.chckbxNewCheckBox.addActionListener(control);
		this.btnIngresar.addKeyListener(control);
		this.txtNombre.addKeyListener(control);
		this.txtPassword.addKeyListener(control);
	}
	
	public String getTxtNombre() {return txtNombre.getText();}

	public String getTxtPassword() {return txtPassword.getText();}

	public JButton getBtnIngresar() {return btnIngresar;}

	public void setTxtNombre(String txt) {
		this.txtNombre.setText(txt);
	}

	public void setTxtPassword(String txtPassword) {
		this.txtPassword.setText(txtPassword);
	}

	public JCheckBox getChckbxNewCheckBox() {
		return chckbxNewCheckBox;
	}

	public void selectChckbxNewCheckBox() {
		this.chckbxNewCheckBox.setSelected(true);
	}
	
	

}
