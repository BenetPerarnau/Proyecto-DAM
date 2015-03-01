package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DTO.Producto;
import Exceptions.ExceptionNombre;
import Exceptions.ExceptionPrecio;
import Exceptions.ExceptionStock;
import Model.ConectorBBDD;

public class ProductoDAO implements InterfaceDAO<Producto>{
	
	private static final String SQL_INSERT="INSERT INTO PRODUCTOS "
								+ "(COD, NOMBRE, DESCRIPCCION, PRECIOV, STOCK, PROVEEDOR) "
								+ "VALUES(?,?,?,?,?,?)";
	private static final String SQL_DELETE="DELETE FROM PRODUCTO "
											+ "WHERE COD = ?";
	private static final String SQL_UPDATE="UPDATE PRODUCTO "
										+ "SET NOMBRE = ?, DESCRIPCCION = ?, PRECIOV = ?, "
										+ "STOCK = ?, PROVEEDOR = ? "
										+ "WHERE COD = ?";
	private static final String SQL_READ="SELECT * FROM PRODUCTO WHERE COD = ?";
	private static final String SQL_READALL="SELECT * FROM PRODUCTO";

	private static ConectorBBDD cnn;//aplicamos Singleton

	@Override
	public boolean create(Producto c) throws SQLException {
		PreparedStatement ps;
		cnn=ConectorBBDD.saberEstado();
		try{
		ps=cnn.getConexion().prepareStatement(SQL_INSERT);
		ps.setString(1, c.getCod());
		ps.setString(2, c.getName());
		ps.setString(3, c.getDesc());
		ps.setFloat(4, c.getPrecio());
		ps.setInt(5, c.getStock());
		ps.setString(6, c.getProveedor());
		
		if(ps.executeUpdate()>0){
			return true;
		}
		}finally{
			cnn.cerrarConexion();
		}
		return false;
	}

	@Override
	public boolean delete(Object key) throws SQLException {
		PreparedStatement ps;
		cnn=ConectorBBDD.saberEstado();
		try{
			ps=cnn.getConexion().prepareStatement(SQL_DELETE);
			ps.setString(1, key.toString());
			if(ps.executeUpdate()>0){
				return true;
			}
		}finally{
			cnn.cerrarConexion();
		}
		return false;
	}

	@Override
	public boolean update(Producto c) throws SQLException {
		PreparedStatement ps;
		cnn=ConectorBBDD.saberEstado();
		try{
		ps=cnn.getConexion().prepareStatement(SQL_UPDATE);
		ps.setString(1, c.getName());
		ps.setString(2, c.getDesc());
		ps.setFloat(3, c.getPrecio());
		ps.setInt(4, c.getStock());
		ps.setString(5, c.getProveedor());
		ps.setString(6, c.getCod() );
		
		if(ps.executeUpdate()>0){
			return true;
		}
		}finally{
			cnn.cerrarConexion();
		}
		return false;
	}

	@Override
	public Producto read(Object key) throws SQLException {
		PreparedStatement ps;
		ResultSet res;
		Producto p=null;
		cnn=ConectorBBDD.saberEstado();
		try{
		ps=cnn.getConexion().prepareStatement(SQL_READ);
		ps.setString(1, key.toString());
		res=ps.executeQuery();
		while(res.next()){
			p=new Producto(
							res.getString(1),res.getString(2),res.getString(3),
							res.getFloat(4),res.getInt(5),res.getString(6)
							);
		}
		} catch (ExceptionNombre e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExceptionStock e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExceptionPrecio e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			cnn.cerrarConexion();
		}
		return p;
	}

	@Override
	public ArrayList<Producto> readAll() throws SQLException {
		PreparedStatement ps;
		ResultSet res;
		Producto p;
		ArrayList<Producto> array=null;
		cnn=ConectorBBDD.saberEstado();
		try{
		array=new ArrayList<Producto>();
		ps=cnn.getConexion().prepareStatement(SQL_READALL);
		res=ps.executeQuery();
		while(res.next()){
			p=new Producto(
					res.getString(1),res.getString(2),res.getString(3),
					res.getFloat(4),res.getInt(5),res.getString(6)
						);
			array.add(p);
		}
		} catch (ExceptionNombre e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExceptionStock e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExceptionPrecio e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			cnn.cerrarConexion();
		}
		return array;
	}
	public String[] consultaTitulosTablaToArray(){
		PreparedStatement ps;
		String[] aux=null;
		try {
			cnn=ConectorBBDD.saberEstado();
			ps=(PreparedStatement) cnn.getConexion().prepareStatement(SQL_READALL);
			ResultSet resultado=ps.executeQuery();
			int numcl=resultado.getMetaData().getColumnCount();
			aux=new String[numcl];
			while(resultado.next()){
				for(int i=0; i<numcl; i++){
					aux[i]=resultado.getMetaData().getColumnName(i+1);
				}			
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			cnn.cerrarConexion();
		}
		return aux;
	}

	public Object[][] consultaDatosTablaToArray(){
		ArrayList<String[]> array=new ArrayList<String[]>();
		PreparedStatement consulta;
		try {
			cnn=ConectorBBDD.saberEstado();
			consulta = (PreparedStatement) cnn.getConexion().prepareStatement(SQL_READALL);
			ResultSet resultado=consulta.executeQuery();
			
			//número de columnas (campos) de la consula SQL            	  
			int numColumnas = resultado.getMetaData().getColumnCount(); 
			String name=resultado.getMetaData().getColumnName(1);
			System.out.println("Num Columnas => "+numColumnas+""
							+ "\nNombre Columna 1 => "+name);
			
			
			while(resultado.next()){

				String registro="";
				for(int i=0; i<numColumnas; i++){
					registro+=resultado.getString(i+1)+";";
				}
				array.add(registro.split(";"));
					
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			cnn.cerrarConexion();
		}
		Object[][] aux=new Object[array.size()][array.get(0).length];
		
		for(int i=0; i<array.size(); i++){
			for(int j=0; j<array.get(i).length; j++){
				aux[i][j]=array.get(i)[j];
				System.out.print(aux[i][j]+"\t");
			}
			System.out.println("");
		}
		
		return aux;
	}
}
