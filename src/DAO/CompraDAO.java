package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.PreparedStatement;

import DTO.Compra;
import DTO.Empleado;
import DTO.Factura;
import DTO.Venta;
import Exceptions.ExceptionDNI_CIF;
import Exceptions.ExceptionNcuenta;
import Exceptions.ExceptionNombre;
import Exceptions.ExceptionPrecio;
import Exceptions.ExceptionSalario;
import Model.ConectorBBDD;

public class CompraDAO implements InterfaceDAO<Compra> {
	
	private static final String SQL_INSERT="INSERT INTO COMPRAS "
							+ "(ID, CANTIDA, PRODUCTO, PROVEEDOR) "
							+ "VALUES(?,?,?,?)";
	private static final String SQL_DELETE="DELETE FROM COMPRAS "
								+ "WHERE ID = ?";
	private static final String SQL_UPDATE="UPDATE COMPRAS "
								+ "SET CANTIDA = ?, PRODUCTO = ?, PROVEEDOR=? "
								+ "WHERE ID = ?";
	private static final String SQL_READ="SELECT * FROM COMPRAS WHERE ID = ?";
	private static final String SQL_READALL="SELECT * FROM COMPRAS";
	private static  ConectorBBDD cnn;//aplicamos Singleton
	
	@Override
	public boolean create(Compra c) throws SQLException {
		PreparedStatement ps;
		cnn=ConectorBBDD.saberEstado();
		try {
			ps=(PreparedStatement) cnn.getConexion().prepareStatement(SQL_INSERT);
			ps.setInt(1,c.getId());
			ps.setInt(2, c.getCantidad());
			ps.setString(3, c.getProducto());
			ps.setString(4, c.getProveedor());
			
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
		try {
			ps=(PreparedStatement) cnn.getConexion().prepareStatement(SQL_DELETE);
			ps.setInt(1, Integer.parseInt(key.toString()));
			
			if(ps.executeUpdate()>0){
				return true;
			}
			
		}finally{
			cnn.cerrarConexion();
		}
		
		return false;
	}
	@Override
	public boolean update(Compra c) throws SQLException {
		PreparedStatement ps;
		cnn=ConectorBBDD.saberEstado();
		try {
			ps=(PreparedStatement) cnn.getConexion().prepareStatement(SQL_UPDATE);
			ps.setInt(1, c.getCantidad());
			ps.setString(2, c.getProducto());
			ps.setString(3, c.getProveedor());
			ps.setInt(4, c.getId());
			
			if(ps.executeUpdate()>0){
				return true;
			}
			
		}finally{
			cnn.cerrarConexion();
		}
		return false;
	}
	@Override
	public Compra read(Object key) throws SQLException {
		PreparedStatement ps;
		ResultSet res;
		Compra a=null;
		cnn=ConectorBBDD.saberEstado();
		try {
			ps=(PreparedStatement) cnn.getConexion().prepareStatement(SQL_READ);
			ps.setInt(1, Integer.parseInt(key.toString()));
			res=ps.executeQuery();
			
			while(res.next()){
				a=new Compra(res.getInt(1),res.getInt(2),res.getString(3),res.getString(4));
			}
			
		} catch (ExceptionPrecio e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			cnn.cerrarConexion();
		}
		return a;
		
	}
	@Override
	public ArrayList<Compra> readAll() throws SQLException {
		PreparedStatement ps;
		ResultSet res;
		Compra a=null;
		ArrayList<Compra> array=new ArrayList<Compra>();
		cnn=ConectorBBDD.saberEstado();
		try {
			ps=(PreparedStatement) cnn.getConexion().prepareStatement(SQL_READALL);
			res=ps.executeQuery();
			while(res.next()){
				array.add(new Compra(res.getInt(1),res.getInt(2),res.getString(3),res.getString(4)));
			}
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
