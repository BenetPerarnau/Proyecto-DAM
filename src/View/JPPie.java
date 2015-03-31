package View;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Constants.Constant;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.net.URL;

public class JPPie extends JPanel {

	private JLabel autor;
	private JLabel logo;
	private JLabel user;
	
	public JPPie() {
		this.setBackground(Color.LIGHT_GRAY);
		this.setLayout(new FlowLayout(FlowLayout.RIGHT));
		this.setPreferredSize(new Dimension(1000, 30));
		
		user=new JLabel("Name User Conected");
		user.setBounds(372, 0, 61, 16);
		this.add(user);
		
		autor=new JLabel(Constant.COPYRIGHT);
		autor.setBounds(371, 0, 61, 16);
		this.add(autor);
		
		logo=new JLabel("");
		logo.setPreferredSize(new Dimension(30,20));
		logo.setBounds(300, 0, 61, 16);
		ImageIcon fot = new ImageIcon(getClass().getResource("../Img/logo.png"));
		Icon icono = new ImageIcon(fot.getImage().getScaledInstance(logo.getWidth(), 
				logo.getHeight(), Image.SCALE_DEFAULT));
		logo.setIcon(fot);
		logo.repaint();
		
		this.add(logo);
			
	}
	
	public void setStatusUser(String name){
		user.setText(name);
		this.repaint();
	}
}
