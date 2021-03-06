package View;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import View.Formularios.JPFormularioClientes;
import View.Formularios.JPFormularioCompra;
import View.Formularios.JPFormularioEmpleados;
import View.Formularios.JPFormularioFactura;
import View.Formularios.JPFormularioProductos;
import View.Formularios.JPFormularioProveedores;
import View.Formularios.JPFormularioVenta;
import Constants.Constant;
import Controler.ControladorALMAC;
import Controler.ControladorCOMPRA;
import Controler.ControladorCONTA;
import Controler.ControladorROOT;
import Controler.ControladorVENTA;


public class Principal extends JFrame{
	private JMenuBar menuBar;
	private JMenu desp1;
	private JMenu desp2;
	private JMenuItem item1desp1;
	
	private JPLogin log;
	private JPCargando cargando;
	private JPArbolNodosROOT bienvenidaRoot;
	private JPArbolNodosVENTA bienvenidaVenta;
	private JPArbolNodosCOMPRA bienvenidaCompra;
	private JPArbolNodosCONTA bienvenidaConta;
	private JPArbolNodosALMA bienvenidaAlma;
	private JPVertabla verTabla;
	private JPFormularioProveedores formproveedores;
	private JPFormularioClientes formclientes;
	private JPFormularioEmpleados formempleados;
	private JPFormularioProductos formProductos;
	private JPFormularioVenta formVentas;
	private JPFormularioCompra formCompras;
	private JPFormularioFactura formFacturas;
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
	public JPArbolNodosROOT getBienvenidaRoot() {return bienvenidaRoot;}
	public JPArbolNodosVENTA getBienvenidaVenta() {return bienvenidaVenta;}
	
	public JPArbolNodosCOMPRA getBienvenidaCompra() {return bienvenidaCompra;}
	public JPArbolNodosCONTA getBienvenidaConta() {return bienvenidaConta;}
	public JPArbolNodosALMA getBienvenidaAlma() {return bienvenidaAlma;}
	public JPVertabla getVerTabla() {return verTabla;}
	public JPFormularioProveedores getFormp() {return formproveedores;}
	public JPFormularioClientes getFormc() {return formclientes;}
	public JPFormularioEmpleados getForme() {return formempleados;}
	public JPFormularioProductos getFormProductos(){return formProductos;}
	public JPFormularioVenta getFormVentas(){return formVentas;}
	public JPFormularioCompra getFormCompras(){return formCompras;}
	public JPFormularioFactura getFormFacturas(){return formFacturas;}
	public JPPie getPie() {return pie;}

	public void setVerTabla(JPVertabla verTabla) {
		if(this.verTabla.isShowing()){this.remove(verTabla);}
		this.verTabla = verTabla;
		this.add(this.verTabla,BorderLayout.CENTER);//
		this.paintAll(getGraphics());
	}
	
	public void setForme(JPFormularioEmpleados forme) {
		cleanWindow();
		this.formempleados = forme;
		this.add(this.formempleados,BorderLayout.CENTER);//
		this.paintAll(getGraphics());
	}
	
	public void setFormp(JPFormularioProveedores formp) {
		cleanWindow();
		this.formproveedores = formp;
		this.add(this.formproveedores,BorderLayout.CENTER);//
		this.paintAll(getGraphics());
	}

	public void setFormc(JPFormularioClientes formc) {
		cleanWindow();
		this.formclientes = formc;
		this.add(this.formclientes,BorderLayout.CENTER);//
		this.paintAll(getGraphics());
	}
	
	public void setFormProductos(JPFormularioProductos formProductos){
		cleanWindow();
		this.formProductos=formProductos;
		this.add(this.formProductos,BorderLayout.CENTER);//
		this.paintAll(getGraphics());
	}
	public void setFormVentas(JPFormularioVenta formVenta){
		cleanWindow();
		this.formVentas=formVenta;
		this.add(this.formVentas,BorderLayout.CENTER);//
		this.paintAll(getGraphics());
	}
	public void setFormCompras(JPFormularioCompra formCompra){
		cleanWindow();
		this.formCompras=formCompra;
		this.add(this.formCompras,BorderLayout.CENTER);//
		this.paintAll(getGraphics());
	}
	public void setFormFacturas(JPFormularioFactura formCompra){
		cleanWindow();
		this.formFacturas=formCompra;
		this.add(this.formFacturas,BorderLayout.CENTER);//
		this.paintAll(getGraphics());
	}

	public void setPie(JPPie pie) {
		this.pie = pie;
		this.add(this.pie,BorderLayout.PAGE_END);//
		this.paintAll(getGraphics());
	}
	
	public void setTreeRoot(JPArbolNodosROOT treeRoot){
		this.bienvenidaRoot=treeRoot;
		this.add(this.bienvenidaRoot, BorderLayout.LINE_START);
		this.paintAll(getGraphics());
	}
	public void setTreeVenta(JPArbolNodosVENTA treeVenta){
		this.bienvenidaVenta=treeVenta;
		this.add(this.bienvenidaVenta, BorderLayout.LINE_START);
		this.paintAll(getGraphics());
	}
	public void setTreeCOMPRA(JPArbolNodosCOMPRA treeCompra){
		this.bienvenidaCompra=treeCompra;
		this.add(this.bienvenidaCompra, BorderLayout.LINE_START);
		this.paintAll(getGraphics());
	}
	public void setTreeCONTA(JPArbolNodosCONTA treeConta){
		this.bienvenidaConta=treeConta;
		this.add(this.bienvenidaConta, BorderLayout.LINE_START);
		this.paintAll(getGraphics());
	}
	public void setTreeALMA(JPArbolNodosALMA treeAlma){
		this.bienvenidaAlma=treeAlma;
		this.add(this.bienvenidaAlma, BorderLayout.LINE_START);
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
		this.add(log, BorderLayout.PAGE_START);
		
		//
		formclientes=new JPFormularioClientes();
		formclientes.setVisible(false);
		this.add(formclientes, BorderLayout.CENTER);
		formempleados=new JPFormularioEmpleados();
		formempleados.setVisible(false);
		this.add(formempleados, BorderLayout.CENTER);
		formproveedores=new JPFormularioProveedores();
		formproveedores.setVisible(false);
		this.add(formproveedores, BorderLayout.CENTER);
		formProductos=new JPFormularioProductos();
		formProductos.setVisible(false);
		this.add(formProductos, BorderLayout.CENTER);
		formVentas=new JPFormularioVenta();
		formVentas.setVisible(false);
		this.add(formVentas, BorderLayout.CENTER);
		formCompras=new JPFormularioCompra();
		formCompras.setVisible(false);
		this.add(formCompras, BorderLayout.CENTER);
		formFacturas=new JPFormularioFactura();
		formFacturas.setVisible(false);
		this.add(formFacturas, BorderLayout.CENTER);
		//
		
		verTabla=new JPVertabla();
		verTabla.setVisible(false);
		this.add(verTabla, BorderLayout.CENTER);
		cargando=new JPCargando();
		cargando.setVisible(false);
		this.add(cargando,BorderLayout.CENTER);
		
		
		bienvenidaVenta=new JPArbolNodosVENTA();
		bienvenidaVenta.setVisible(false);
		this.add(bienvenidaVenta,BorderLayout.LINE_START);
		
		
		bienvenidaRoot=new JPArbolNodosROOT();
		bienvenidaRoot.setVisible(false);
		this.add(bienvenidaRoot,BorderLayout.LINE_START);
		
		
		/*
		verTabla=new JPVertabla();
		verTabla.setVisible(false);
		this.add(verTabla, BorderLayout.CENTER);
		*/
		pie=new JPPie();
		pie.setVisible(false);
		this.add(pie, BorderLayout.PAGE_END);
		
	}
	
	public void cleanWindow(){
		if(formproveedores.isShowing()){this.remove(formproveedores);}
		if(formclientes.isShowing()){this.remove(formclientes);}
		if(formempleados.isShowing()){this.remove(formempleados);}
		if(formProductos.isShowing()){this.remove(formProductos);}
		if(formVentas.isShowing()){this.remove(formVentas);}
		if(formCompras.isShowing()){this.remove(formCompras);}
		if(formFacturas.isShowing()){this.remove(formFacturas);}
	}
	
	public void addlisteners(ControladorROOT control){
		item1desp1.addActionListener(control);
	}
	public void addlisteners(ControladorVENTA control){
		item1desp1.addActionListener(control);
	}
	public void addlisteners(ControladorCOMPRA control){
		item1desp1.addActionListener(control);
	}
	public void addlisteners(ControladorCONTA control){
		item1desp1.addActionListener(control);
	}
	public void addlisteners(ControladorALMAC control){
		item1desp1.addActionListener(control);
	}
	public void repintar(){
		this.verTabla.repaint();
	}
	
}
