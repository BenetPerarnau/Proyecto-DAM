package View;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class JPCargando extends JPanel  {

	private JLabel cargando;
	private ImageIcon imagen;
	
	public JPCargando() {
		
		//imagen=new ImageIcon(getClass().getResource("../Img/Cargando.gif"));
		imagen=new ImageIcon(JPCargando.class.getResource("/Img/Cargando.gif"));


		cargando=new JLabel("");
		cargando.setIcon(imagen);
		this.add(cargando);
	}


}
