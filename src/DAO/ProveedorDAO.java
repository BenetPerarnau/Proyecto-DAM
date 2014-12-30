package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DTO.Proveedor;
import Exceptions.ExceptionCompras;
import Exceptions.ExceptionDNI_CIF;
import Exceptions.ExceptionNombre;
import Model.ConectorBBDD;

public class ProveedorDAO implements InterfaceDAO<Proveedor> {
	private static final String SQL_INSERT="INSERT INTO PROVEEDORES "
											+ "(CIF, NOMBRE, DIRECCION, TELEFONO, DESCRIPCION, COMPRAS)"
											+ "VALUES(?,?,?,?,?,?)";
	private static final String SQL_DELETE="DELETE FROM PROVEEDORES "
											+ "WHERE CIF = ?";
	private static final String SQL_UPDATE="UPDATE PROVEEDORES "
											+ "SET NOMBRE = ?, DIRECCION = ?, TELEFONO = ?, "
											+ "DESCRIPCION = ?, COMPRAS = ? "
											+ "WHERE CIF = ?";
	private static final String SQL_READ="SELECT * FROM PROVEEDORES WHERE CIF = ?";
	private static final String SQL_READALL="SELECT * FROM PROVEEDORES";

	private static final ConectorBBDD cnn=ConectorBBDD.saberEstado();//aplicamos Singleton
	@Override
	public boolean create(Proveedor c) throws SQLException{
		PreparedStatement ps;
		
		try {
			ps=cnn.getConexion().prepareStatement(SQL_INSERT);
			ps.setString(1, c.getCIF());
			ps.setString(2, c.getNom());
			ps.setString(3, c.getAdr());
			ps.setString(4, c.getTlf());
			ps.setString(5, c.getDesc());
			ps.setInt(6, c.getCompras());
			
			if(ps.executeUpdate()>0){
				return true;
			}

		}finally{
			cnn.cerrarConexion();
		}
		
		return false;
	}

	@Override
	public boolean delete(Object key)throws SQLException {
		PreparedStatement ps;

		try {
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
	public boolean update(Proveedor c) throws SQLException{
		PreparedStatement ps;
		try {
			ps=cnn.getConexion().prepareStatement(SQL_UPDATE);
			
			ps.setString(1, c.getNom());
			ps.setString(2, c.getAdr());
			ps.setString(3, c.getTlf());
			ps.setString(4, c.getDesc());
			ps.setInt(5, c.getCompras());
			ps.setString(6, c.getCIF());
			
			if(ps.executeUpdate()>0){
				return true;
			}
			
		}finally{
			cnn.cerrarConexion();
		}
		return false;
	}

	@Override
	public Proveedor read(Object key)throws SQLException {
		PreparedStatement ps;
		ResultSet res;
		Proveedor p=null;		
		try {
			ps=cnn.getConexion().prepareStatement(SQL_READ);
			ps.setString(1, key.toString());
			res=ps.executeQuery();
			while(res.next()){
				p=new Proveedor(res.getString(1),res.getString(2),res.getString(3),
								res.getString(4), res.getString(5), res.getInt(6));
			}
		}catch (ExceptionDNI_CIF e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExceptionCompras e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExceptionNombre e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			cnn.cerrarConexion();
		}
		return p;
	}

	@Override
	public ArrayList<Proveedor> readAll() throws SQLException {
		PreparedStatement ps;
		ResultSet res;
		Proveedor p=null;
		ArrayList<Proveedor> array=new ArrayList<Proveedor>();
		try {
			ps=cnn.getConexion().prepareStatement(SQL_READALL);
			res=ps.executeQuery();
			while(res.next()){
				array.add(new Proveedor(res.getString(1),res.getString(2),res.getString(3),
						res.getString(4), res.getString(5), res.getInt(6)));
			}
		}catch (ExceptionDNI_CIF e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExceptionCompras e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExceptionNombre e) {
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
		}
		return aux;
	}

	public Object[][] consultaDatosTablaToArray(){
		ArrayList<String[]> array=new ArrayList<String[]>();
		PreparedStatement consulta;
		try {
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
