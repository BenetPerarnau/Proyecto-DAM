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

import Controler.ControladorVENTA;
import Model.RendererArbol;

public class JPArbolNodosVENTA extends JPanel {

	private final String[] cl={"Crear Cliente","Consultar Cliente","Modificar Cliente","Borrar Cliente"};
	private final String[] vent={"Crear Venta","Consultar Venta","Modificar Venta","Borrar Venta"};
	private final String[] pro={"Consultar Producto"};
	
	private JTree tree;
	
	
	public JPArbolNodosVENTA() {
		this.setBackground(Color.GRAY);
		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(250,250));
		
		tree = new JTree();
		//tree.setBounds(10, 10, 200, 175);
		
		
		DefaultMutableTreeNode raiz=new DefaultMutableTreeNode("BAP OPERACIONES (ROL VENTAS)");
		DefaultMutableTreeNode nodocl=new DefaultMutableTreeNode("Clientes");
		DefaultMutableTreeNode nodoVenta=new DefaultMutableTreeNode("Ventas");
		DefaultMutableTreeNode nodoProduct=new DefaultMutableTreeNode("Producto");
		
		for(String a: cl){
			DefaultMutableTreeNode aux=new DefaultMutableTreeNode();
			aux.setUserObject(a);
			nodocl.add(aux);
		}

		for(String a:vent){
			DefaultMutableTreeNode aux=new DefaultMutableTreeNode();
			aux.setUserObject(a);
			nodoVenta.add(aux);
		}
		
		for(String a:pro){
			DefaultMutableTreeNode aux=new DefaultMutableTreeNode();
			aux.setUserObject(a);
			nodoProduct.add(aux);
		}
			
		raiz.add(nodocl);
		raiz.add(nodoVenta);
		raiz.add(nodoProduct);

		
		DefaultTreeModel modelo=new DefaultTreeModel(raiz);
		
		tree.setModel(modelo);
		
		tree.setCellRenderer(new RendererArbol());
		
		JScrollPane sp1=new JScrollPane(tree);
		add(sp1, BorderLayout.CENTER);
	}
	
	public void addListener(ControladorVENTA control){
		 tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		 tree.addTreeSelectionListener(control);
	}

	public JTree getTree() {
		return tree;
	}
	
}
