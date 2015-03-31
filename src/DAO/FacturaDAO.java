package DAO;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.PreparedStatement;

import DTO.Empleado;
import DTO.Factura;
import Exceptions.ExceptionCantidad;
import Exceptions.ExceptionDNI_CIF;
import Exceptions.ExceptionNcuenta;
import Exceptions.ExceptionNombre;
import Exceptions.ExceptionSalario;
import Model.ConectorBBDD;

public class FacturaDAO implements InterfaceDAO<Factura> {
	
	private static final String SQL_INSERT="INSERT INTO FACTURAS "
							+ "(ID, CLIENTE, PRODUCTO, CANTIDAD, PRECIO, PAGADA, FECHA) "
							+ "VALUES(?,?,?,?,?,?,?)";
	private static final String SQL_DELETE="DELETE FROM FACTURAS "
								+ "WHERE ID = ?";
	private static final String SQL_UPDATE="UPDATE FACTURAS "
								+ "SET CLIENTE = ?, PRODUCTO = ?, CANTIDAD = ?, PRECIO = ?, "
								+ "PAGADA = ?, FECHA = ?"
								+ "WHERE ID = ?";
	private static final String SQL_READ="SELECT * FROM FACTURAS WHERE ID = ?";
	private static final String SQL_READALL="SELECT * FROM FACTURAS";
	private static  ConectorBBDD cnn;//aplicamos Singleton
	
	@Override
	public boolean create(Factura c) throws SQLException {
		PreparedStatement ps;
		cnn=ConectorBBDD.saberEstado();
		try {
			ps=(PreparedStatement) cnn.getConexion().prepareStatement(SQL_INSERT);
			ps.setInt(1,c.getId());
			ps.setString(2, c.getCliente());
			ps.setString(3, c.getProducto());
			ps.setInt(4, c.getCantidad());
			ps.setFloat(5, c.getPrecio());
			ps.setBoolean(6, c.isPagada());
			ps.setDate(7, c.getFecha());
			
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
	public boolean update(Factura c) throws SQLException {
		PreparedStatement ps;
		cnn=ConectorBBDD.saberEstado();
		try {
			ps=(PreparedStatement) cnn.getConexion().prepareStatement(SQL_UPDATE);
			ps.setString(1, c.getCliente());
			ps.setString(2, c.getProducto());
			ps.setInt(3, c.getCantidad());
			ps.setFloat(4, c.getPrecio());
			ps.setBoolean(5, c.isPagada());
			ps.setDate(6, c.getFecha());
			ps.setInt(7,c.getId());
			
			if(ps.executeUpdate()>0){
				return true;
			}
			
		}finally{
			cnn.cerrarConexion();
		}
		return false;
	}
	@Override
	public Factura read(Object key) throws SQLException {
		PreparedStatement ps;
		ResultSet res;
		Factura a=null;
		cnn=ConectorBBDD.saberEstado();
		try {
			ps=(PreparedStatement) cnn.getConexion().prepareStatement(SQL_READ);
			ps.setInt(1, Integer.parseInt(key.toString()));
			res=ps.executeQuery();
			
			while(res.next()){
				a=new Factura(res.getInt(1),res.getString(2),res.getString(3),res.getInt(4),
							   res.getFloat(5),res.getBoolean(6),res.getDate(7));
			}
			
		} catch (ExceptionCantidad e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			cnn.cerrarConexion();
		}
		return a;
	}
	@Override
	public ArrayList<Factura> readAll() throws SQLException {
		PreparedStatement ps;
		ResultSet res;
		Factura a=null;
		ArrayList<Factura> array=new ArrayList<Factura>();
		cnn=ConectorBBDD.saberEstado();
		try {
			ps=(PreparedStatement) cnn.getConexion().prepareStatement(SQL_READALL);
			res=ps.executeQuery();
			//int id,String cliente, String producto, int cantidad, float precio, boolean pagada, Date fecha
			while(res.next()){
				array.add(new Factura(
						res.getInt(3),//id
						res.getString(2),//cliente	
						res.getString(6),//producto
						res.getInt(1),//cantidad
						res.getFloat(5),//precio
						res.getBoolean(4),//pagada
					    res.getDate(7)));//fecha
			}
		} catch (ExceptionCantidad e) {
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
