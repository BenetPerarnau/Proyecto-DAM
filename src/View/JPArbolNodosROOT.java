package View;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JTree;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;

import Controler.ControladorROOT;
import Model.RendererArbol;

public class JPArbolNodosROOT extends JPanel {

	private final String[] cl={"Crear Cliente","Consultar Cliente","Modificar Cliente","Borrar Cliente"};
	private final String[] pr={"Crear Proveedor","Consultar Proveedor","Modificar Proveedor","Borrar Proveedor"};
	private final String[] tr={"Crear Trabajador","Consultar Trabajador","Modificar Trabajador","Borrar Trabajador"};
	private final String[] pro={"Crear Producto","Consultar Producto","Modificar Producto","Borrar Producto"};
	private final String[] vent={"Crear Venta","Consultar Venta","Modificar Venta","Borrar Venta"};
	private final String[] comp={"Crear Compra","Consultar Compra","Modificar Compra","Borrar Compra"};
	
	private JTree tree;
	
	
	public JPArbolNodosROOT() {
		this.setBackground(Color.GRAY);
		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(250,250));
		
		tree = new JTree();
		//tree.setBounds(10, 10, 200, 175);
		
		
		DefaultMutableTreeNode raiz=new DefaultMutableTreeNode("BAP OPERACIONES (ROL ROOT)");
		DefaultMutableTreeNode nodocl=new DefaultMutableTreeNode("Clientes");
		DefaultMutableTreeNode nodopr=new DefaultMutableTreeNode("Proveedores");
		DefaultMutableTreeNode nodotr=new DefaultMutableTreeNode("Empleados");
		DefaultMutableTreeNode nodopro=new DefaultMutableTreeNode("Productos");
		DefaultMutableTreeNode nodoVenta=new DefaultMutableTreeNode("Ventas");
		DefaultMutableTreeNode nodoCompra=new DefaultMutableTreeNode("Compras");
		
		for(String a: cl){
			DefaultMutableTreeNode aux=new DefaultMutableTreeNode();
			aux.setUserObject(a);
			nodocl.add(aux);
		}
		
		for(String a: pr){
			DefaultMutableTreeNode aux=new DefaultMutableTreeNode();
			aux.setUserObject(a);
			nodopr.add(aux);
		}
		for(String a: tr){
			DefaultMutableTreeNode aux=new DefaultMutableTreeNode();
			aux.setUserObject(a);
			nodotr.add(aux);
		}
		for(String a: pro){
			DefaultMutableTreeNode aux=new DefaultMutableTreeNode();
			aux.setUserObject(a);
			nodopro.add(aux);
		}
		for(String a:vent){
			DefaultMutableTreeNode aux=new DefaultMutableTreeNode();
			aux.setUserObject(a);
			nodoVenta.add(aux);
		}
		for(String a:comp){
			DefaultMutableTreeNode aux=new DefaultMutableTreeNode();
			aux.setUserObject(a);
			nodoCompra.add(aux);
		}
			
		raiz.add(nodocl);
		raiz.add(nodopr);
		raiz.add(nodotr);
		raiz.add(nodopro);
		raiz.add(nodoVenta);
		raiz.add(nodoCompra);
		
		DefaultTreeModel modelo=new DefaultTreeModel(raiz);
		
		tree.setModel(modelo);
		
		tree.setCellRenderer(new RendererArbol());
		
		JScrollPane sp1=new JScrollPane(tree);
		add(sp1, BorderLayout.CENTER);
	}
	
	public void addListener(ControladorROOT control){
		 tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		 tree.addTreeSelectionListener(control);
	}

	public JTree getTree() {
		return tree;
	}
	
}
