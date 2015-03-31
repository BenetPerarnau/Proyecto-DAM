package Model;

import java.awt.Component;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeNode;

public class RendererArbol extends DefaultTreeCellRenderer{


    ImageIcon imgconsultar;
    ImageIcon imgnuevo;
    ImageIcon imgeliminar;
    ImageIcon imgmodificar;

    public RendererArbol() {

    	//imgconsultar = new ImageIcon(getClass().getResource("../Img/ImgTree/find.png"));
    	//imgnuevo = new ImageIcon(getClass().getResource("../Img/imgTree/new.png"));
    	//imgeliminar = new ImageIcon(getClass().getResource("../Img/ImgTree/delete.png"));
    	//imgmodificar = new ImageIcon(getClass().getResource("../Img/ImgTree/mod.png"));
    	
    	imgconsultar = new ImageIcon(RendererArbol.class.getResource("/Img/ImgTree/find.png"));
    	imgnuevo = new ImageIcon(RendererArbol.class.getResource("/Img/imgTree/new.png"));
    	imgeliminar = new ImageIcon(RendererArbol.class.getResource("/Img/ImgTree/delete.png"));
    	imgmodificar = new ImageIcon(RendererArbol.class.getResource("/Img/ImgTree/mod.png"));

    }

    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, 
    		boolean expanded, boolean leaf, int row, boolean hasFocus)
    {               

        super.getTreeCellRendererComponent(tree,value,selected,expanded,leaf,row,hasFocus);
        String val = value.toString();


        DefaultMutableTreeNode nodo = (DefaultMutableTreeNode)value;

        if(val.compareTo("Clientes")!=0 && 
        		val.compareTo("Proveedores")!=0 &&
        		val.compareTo("Empleados")!=0 &&
        		val.compareTo("BAP OPERACIONES")!=0){   

            if(nodo!=null){

                	if(nodo.toString().contains("Crear")){
                		setIcon(imgnuevo);
                	}else if(nodo.toString().contains("Consultar")){
                		setIcon(imgconsultar);
                	}else if(nodo.toString().contains("Borrar")){
                		setIcon(imgeliminar);		
                	}else if(nodo.toString().contains("Modificar")){
                		setIcon(imgmodificar);		
                	}
        }                            
    }
        return this;
}
}


