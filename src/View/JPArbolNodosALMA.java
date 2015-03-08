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

import Controler.ControladorALMAC;
import Controler.ControladorCOMPRA;
import Controler.ControladorROOT;
import Model.RendererArbol;

public class JPArbolNodosALMA extends JPanel {

	private final String[] pro={"Consultar Producto","Modificar Producto"};
	
	private JTree tree;
	
	
	public JPArbolNodosALMA() {
		this.setBackground(Color.GRAY);
		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(250,250));
		
		tree = new JTree();
		//tree.setBounds(10, 10, 200, 175);
		
		
		DefaultMutableTreeNode raiz=new DefaultMutableTreeNode("BAP OPERACIONES (ROL ALMACÉN)");
		DefaultMutableTreeNode nodoproduct=new DefaultMutableTreeNode("Productos");
		
		
		for(String a: pro){
			DefaultMutableTreeNode aux=new DefaultMutableTreeNode();
			aux.setUserObject(a);
			nodoproduct.add(aux);
		}
		raiz.add(nodoproduct);
		
		DefaultTreeModel modelo=new DefaultTreeModel(raiz);
		
		tree.setModel(modelo);
		
		tree.setCellRenderer(new RendererArbol());
		
		JScrollPane sp1=new JScrollPane(tree);
		add(sp1, BorderLayout.CENTER);
	}
	
	public void addListener(ControladorALMAC control){
		 tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		 tree.addTreeSelectionListener(control);
	}

	public JTree getTree() {
		return tree;
	}
	
}
