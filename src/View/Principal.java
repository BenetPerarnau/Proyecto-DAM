package View;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import Constants.Constant;
import Controler.ControladorROOT;


public class Principal extends JFrame{
	private JMenuBar menuBar;
	private JMenu desp1;
	private JMenu desp2;
	private JMenuItem item1desp1;
	
	private JPLogin log;
	private JPCargando cargando;
	private JPBienvenida bienvenida;
	private JPVertabla verTabla;
	private JPFormularioProveedores formp;
	private JPFormularioClientes formc;
	private JPFormularioEmpleados forme;
	private JPPie pie;
	
	public Principal(){
		
		
		
		//this.setLayout(new FlowLayout(FlowLayout.CENTER));
		this.setLayout(new BorderLayout());
		addContent();
		
		this.setTitle(Constant.NAME_APP);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);//maximizar al iniciar
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	public JMenuItem getItem1desp1() {return item1desp1;}
	public JPLogin getLog() {return log;}
	public JPCargando getCargando() {return cargando;}
	public JPBienvenida getBienvenida() {return bienvenida;}
	public JPVertabla getVerTabla() {return verTabla;}
	public JPFormularioProveedores getFormp() {return formp;}
	public JPFormularioClientes getFormc() {return formc;}
	public JPFormularioEmpleados getForme() {return forme;}
	public JPPie getPie() {return pie;}

	public void setVerTabla(JPVertabla verTabla) {
		this.verTabla = verTabla;
		this.add(this.verTabla,BorderLayout.CENTER);//
		this.paintAll(getGraphics());
	}
	
	public void setForme(JPFormularioEmpleados forme) {
		this.forme = forme;
		this.add(this.forme,BorderLayout.CENTER);//
		this.paintAll(getGraphics());
	}
	
	public void setFormp(JPFormularioProveedores formp) {
		this.formp = formp;
		this.add(this.formp,BorderLayout.CENTER);//
		this.paintAll(getGraphics());
	}

	public void setFormc(JPFormularioClientes formc) {
		this.formc = formc;
		this.add(this.formc,BorderLayout.CENTER);//
		this.paintAll(getGraphics());
	}

	public void setPie(JPPie pie) {
		this.pie = pie;
		this.add(this.pie,BorderLayout.PAGE_END);//
		this.paintAll(getGraphics());
	}

	public void addContent(){
		menuBar=new JMenuBar();//creación de la barra de menus
		
		desp1=new JMenu("BAP ERP");
		item1desp1=new JMenuItem("Cerrar Sesión");
		desp1.add(item1desp1);
		menuBar.add(desp1);
		
		desp2=new JMenu("Ayuda");
		menuBar.add(desp2);
		
		this.setJMenuBar(menuBar);
		
		log=new JPLogin();
		//log.setAlignmentX(LEFT_ALIGNMENT);	
		this.add(log, BorderLayout.PAGE_START);
		
		//
		formc=new JPFormularioClientes();
		formc.setVisible(false);
		this.add(formc, BorderLayout.CENTER);
		forme=new JPFormularioEmpleados();
		forme.setVisible(false);
		this.add(forme, BorderLayout.CENTER);
		formp=new JPFormularioProveedores();
		formp.setVisible(false);
		this.add(formp, BorderLayout.CENTER);
		//
		
		verTabla=new JPVertabla();
		verTabla.setVisible(false);
		this.add(verTabla, BorderLayout.CENTER);
		cargando=new JPCargando();
		cargando.setVisible(false);
		this.add(cargando,BorderLayout.CENTER);
		
		//this.setLayout(new FlowLayout(FlowLayout.LEFT));
		bienvenida=new JPBienvenida();
		bienvenida.setVisible(false);
		this.add(bienvenida,BorderLayout.LINE_START);
		/*
		verTabla=new JPVertabla();
		verTabla.setVisible(false);
		this.add(verTabla, BorderLayout.CENTER);
		*/
		pie=new JPPie();
		pie.setVisible(false);
		this.add(pie, BorderLayout.PAGE_END);
	}
	public void addlisteners(ControladorROOT control){
		item1desp1.addActionListener(control);
	}
	public void repintar(){
		this.verTabla.repaint();
	}
	
}
