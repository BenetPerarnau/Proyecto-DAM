package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DTO.Cliente;
import Exceptions.ExceptionCompras;
import Exceptions.ExceptionDNI_CIF;
import Exceptions.ExceptionNcuenta;
import Exceptions.ExceptionNombre;
import Model.ConectorBBDD;

public class ClienteDAO implements InterfaceDAO<Cliente> {
	
	private static final String SQL_INSERT="INSERT INTO CLIENTES "
								+ "(DNI, NOMBRE, APELLIDO, DIRECCION, TELEFONO, MOVIL, COMPRAS, NCUENTA)"
								+ "VALUES(?,?,?,?,?,?,?,?)";
	private static final String SQL_DELETE="DELETE FROM CLIENTES "
										 + "WHERE DNI = ?";
	private static final String SQL_UPDATE="UPDATE CLIENTES "
									     + "SET NOMBRE = ?, APELLIDO = ?, DIRECCION = ?, TELEFONO = ?, "
									     + "MOVIL = ?, COMPRAS = ?, NCUENTA = ? "
									     + "WHERE DNI = ?";
	private static final String SQL_READ="SELECT * FROM CLIENTES WHERE DNI = ?";
	private static final String SQL_READALL="SELECT * FROM CLIENTES";

	//private static final ConectorBBDD cnn=ConectorBBDD.saberEstado().;//aplicamos Singleton
	private static ConectorBBDD cnn;//aplicamos Singleton
	
	@Override
	public boolean create(Cliente c) throws SQLException{
		PreparedStatement ps;
		cnn=ConectorBBDD.saberEstado();
		try {
			
			ps=cnn.getConexion().prepareStatement(SQL_INSERT);
			ps.setString(1, c.getDni());//en el primer interrogante 
			ps.setString(2, c.getNom());
			ps.setString(3, c.getApe());
			ps.setString(4, c.getAdre());
			ps.setString(5, c.getTlf());
			ps.setString(6, c.getMvl());
			ps.setInt(7, c.getCompras());
			ps.setString(8, c.getnCuenta());
			
			if(ps.executeUpdate()>0){
				return true;
			}
		
		}finally{
			cnn.cerrarConexion(); //cerrar conexion al final de la instrucción si o si
		}
		return false;
	}

	@Override
	public boolean delete(Object key)throws SQLException {
		PreparedStatement ps;
		cnn=ConectorBBDD.saberEstado();
		try {
			
			ps=cnn.getConexion().prepareStatement(SQL_DELETE);
			ps.setString(1, key.toString());
			
			if(ps.executeUpdate()>0){
				return true;
			}
		
		}finally{
			cnn.cerrarConexion(); //cerrar conexion al final de la instrucción si o si
		}
		return false;
	}

	@Override
	public boolean update(Cliente c) throws SQLException{
		PreparedStatement ps;
		cnn=ConectorBBDD.saberEstado();
		try {
			
			ps=cnn.getConexion().prepareStatement(SQL_UPDATE);
			ps.setString(1, c.getNom());
			ps.setString(2, c.getApe());
			ps.setString(3, c.getAdre());
			ps.setString(4, c.getTlf());
			ps.setString(5, c.getMvl());
			ps.setInt(6, c.getCompras());
			ps.setString(7, c.getnCuenta());
			ps.setString(8, c.getDni());
			
			if(ps.executeUpdate()>0){
				return true;
			}
		
		}finally{
			cnn.cerrarConexion(); //cerrar conexion al final de la instrucción si o si
		}
		return false;
	}

	@Override
	public Cliente read(Object key) throws SQLException{
		PreparedStatement ps;
		ResultSet res;
		Cliente c=null;
		cnn=ConectorBBDD.saberEstado();
		try {
			
			ps=cnn.getConexion().prepareStatement(SQL_READ);
			ps.setString(1, key.toString());
			
			res = ps.executeQuery();
			
			while(res.next()){
				c=new Cliente(res.getString(1),res.getString(2),res.getString(3),res.getString(4),
							  res.getString(5),res.getString(6),res.getInt(7),res.getString(8));
			}
			return c;
		
		}catch (ExceptionDNI_CIF e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExceptionNcuenta e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExceptionCompras e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExceptionNombre e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			cnn.cerrarConexion(); //cerrar conexion al final de la instrucción si o si
		}
		return c;
	}

	@Override
	public ArrayList<Cliente> readAll() throws SQLException{
		PreparedStatement ps;
		ArrayList<Cliente> array=new ArrayList<Cliente>();
		ResultSet res;
		Cliente c=null;
		cnn=ConectorBBDD.saberEstado();
		try {
			
			ps=cnn.getConexion().prepareStatement(SQL_READALL);
						
			res = ps.executeQuery();
			
			while(res.next()){
				array.add(new Cliente(res.getString(1),res.getString(2),res.getString(3),res.getString(4),
						  res.getString(5),res.getString(6),res.getInt(7),res.getString(8)));
			}
		
		}catch (ExceptionDNI_CIF e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExceptionNcuenta e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExceptionCompras e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExceptionNombre e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			cnn.cerrarConexion(); //cerrar conexion al final de la instrucción si o si
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
