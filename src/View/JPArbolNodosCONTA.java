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
import Controler.ControladorCONTA;
import Controler.ControladorROOT;
import Model.RendererArbol;

public class JPArbolNodosCONTA extends JPanel {

	private final String[] tr={"Crear Trabajador","Consultar Trabajador","Modificar Trabajador","Borrar Trabajador"};
	private final String[] fac={"Crear Factura","Consultar Factura","Modificar Factura","Borrar Factura"};
	private JTree tree;
	
	
	public JPArbolNodosCONTA() {
		this.setBackground(Color.GRAY);
		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(250,250));
		
		tree = new JTree();
		//tree.setBounds(10, 10, 200, 175);
		
		
		DefaultMutableTreeNode raiz=new DefaultMutableTreeNode("BAP OPERACIONES (ROL CONTABILIDAD)");
		DefaultMutableTreeNode nodoemp=new DefaultMutableTreeNode("Empleados");
		DefaultMutableTreeNode nodofac=new DefaultMutableTreeNode("Facturas");
		
		
		for(String a: tr){
			DefaultMutableTreeNode aux=new DefaultMutableTreeNode();
			aux.setUserObject(a);
			nodoemp.add(aux);
		}
		for(String a: fac){
			DefaultMutableTreeNode aux=new DefaultMutableTreeNode();
			aux.setUserObject(a);
			nodofac.add(aux);
		}

			
		raiz.add(nodoemp);
		raiz.add(nodofac);
		
		DefaultTreeModel modelo=new DefaultTreeModel(raiz);
		
		tree.setModel(modelo);
		
		tree.setCellRenderer(new RendererArbol());
		
		JScrollPane sp1=new JScrollPane(tree);
		add(sp1, BorderLayout.CENTER);
	}
	
	public void addListener(ControladorCONTA control){
		 tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		 tree.addTreeSelectionListener(control);
	}

	public JTree getTree() {
		return tree;
	}
	
}
