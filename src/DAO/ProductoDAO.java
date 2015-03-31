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
	
	private static final String SQL_INSERT="INSERT INTO PRODUCTO "
								+ "(COD, NOMBRE, DESCRIPCCION, PRECIOV, STOCK, PROVEEDOR) "
								+ "VALUES(?,?,?,?,?,?)";
	private static final String SQL_DELETE="DELETE FROM PRODUCTO "
											+ "WHERE COD = ?";
	private static final String SQL_UPDATE="UPDATE PRODUCTO "
										+ "SET NOMBRE = ?, DESCRIPCCION = ?, PRECIOV = ?, "
										+ "STOCK = ?, PROVEEDOR = ? "
										+ "WHERE COD = ?";
	private static final String SQL_UPDATESTOCK="UPDATE PRODUCTO "
										+ "SET STOCK = ? "
										+ "WHERE COD = ?";
	private static final String SQL_READ="SELECT * FROM PRODUCTO WHERE COD = ?";
	private static final String SQL_READALL="SELECT * FROM PRODUCTO";
	
	private static final String SQL_READ_PROV="SELECT * FROM PRODUCTO WHERE PROVEEDOR = ?";

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
	
	public boolean updateStock(String cod, int stock)throws SQLException {
		//
		Producto aux=read(cod);
		int stockActual=aux.getStock();
		//
		int stockfinal=stockActual+stock;
		PreparedStatement ps;
		cnn=ConectorBBDD.saberEstado();
		try{
		ps=cnn.getConexion().prepareStatement(SQL_UPDATESTOCK);
		ps.setInt(1, stockfinal);
		ps.setString(2, cod);
		
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
							res.getString(2),res.getString(3),res.getString(4),
							res.getFloat(5),res.getInt(6),res.getString(7)
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
					res.getString(2),res.getString(3),res.getString(4),
					res.getFloat(5),res.getInt(6),res.getString(7)
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
	
	public ArrayList<Producto> readAllProv(String name_prov) throws SQLException {
		PreparedStatement ps;
		ResultSet res;
		Producto p;
		ArrayList<Producto> array=null;
		cnn=ConectorBBDD.saberEstado();
		try{
		array=new ArrayList<Producto>();
		ps=cnn.getConexion().prepareStatement(SQL_READ_PROV);
		ps.setString(1, name_prov);
		res=ps.executeQuery();
		while(res.next()){
			p=new Producto(
					res.getString(2),res.getString(3),res.getString(4),
					res.getFloat(5),res.getInt(6),res.getString(7)
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
					aux[i]=resultado.getMetaData().getColumnName(i+1).toUpperCase();
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
