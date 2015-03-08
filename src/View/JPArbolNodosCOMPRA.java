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

import Controler.ControladorCOMPRA;
import Controler.ControladorROOT;
import Model.RendererArbol;

public class JPArbolNodosCOMPRA extends JPanel {

	private final String[] pr={"Crear Proveedor","Consultar Proveedor","Modificar Proveedor","Borrar Proveedor"};
	private final String[] pro={"Crear Producto","Consultar Producto","Modificar Producto","Borrar Producto"};
	private final String[] comp={"Crear Compra","Consultar Compra","Modificar Compra","Borrar Compra"};
	
	private JTree tree;
	
	
	public JPArbolNodosCOMPRA() {
		this.setBackground(Color.GRAY);
		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(250,250));
		
		tree = new JTree();
		//tree.setBounds(10, 10, 200, 175);
		
		
		DefaultMutableTreeNode raiz=new DefaultMutableTreeNode("BAP OPERACIONES (ROL COMPRAS)");
		DefaultMutableTreeNode nodopr=new DefaultMutableTreeNode("Proveedores");
		DefaultMutableTreeNode nodoproduct=new DefaultMutableTreeNode("Productos");
		DefaultMutableTreeNode nodoCompra=new DefaultMutableTreeNode("Compras");
		
		
		for(String a: pr){
			DefaultMutableTreeNode aux=new DefaultMutableTreeNode();
			aux.setUserObject(a);
			nodopr.add(aux);
		}
		for(String a: pr){
			DefaultMutableTreeNode aux=new DefaultMutableTreeNode();
			aux.setUserObject(a);
			nodoproduct.add(aux);
		}
		for(String a:comp){
			DefaultMutableTreeNode aux=new DefaultMutableTreeNode();
			aux.setUserObject(a);
			nodoCompra.add(aux);
		}
			
		raiz.add(nodopr);
		raiz.add(nodoproduct);
		raiz.add(nodoCompra);
		
		DefaultTreeModel modelo=new DefaultTreeModel(raiz);
		
		tree.setModel(modelo);
		
		tree.setCellRenderer(new RendererArbol());
		
		JScrollPane sp1=new JScrollPane(tree);
		add(sp1, BorderLayout.CENTER);
	}
	
	public void addListener(ControladorCOMPRA control){
		 tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		 tree.addTreeSelectionListener(control);
	}

	public JTree getTree() {
		return tree;
	}
	
}
