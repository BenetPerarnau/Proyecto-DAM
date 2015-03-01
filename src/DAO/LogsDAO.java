package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DTO.Cliente;
import DTO.Logs;
import Exceptions.ExceptionDNI_CIF;
import Exceptions.ExceptionNcuenta;
import Model.ConectorBBDD;

public class LogsDAO implements InterfaceDAO<Logs> {
	
	private static final String SQL_INSERT="INSERT INTO LOGS "
							  				+ "(ID, NOMBRE, PASSWORD) "
							  				+ "VALUES(?,?,?)";
	private static final String SQL_DELETE="DELETE FROM LOGS "
					 						+ "WHERE ID = ?";
	private static final String SQL_UPDATE="UPDATE LOGS "
				     						+ "SET NOMBRE = ?, PASSWORD = ? "
				     						+ "WHERE ID = ?";
	private static final String SQL_READ="SELECT * FROM LOGS WHERE ID = ?";
	private static final String SQL_READALL="SELECT * FROM LOGS";
	
	private static final String SQL_READ_LOGIN="SELECT * FROM LOGS WHERE NOMBRE = ? AND PASSWORD = ?";
	private static final String SQL_GET_ROL_USER="SELECT ROL FROM LOGS WHERE NOMBRE = ? AND PASSWORD = ?";

	private static ConectorBBDD cnn;//aplicamos Singleton
	
	@Override
	public boolean create(Logs c) throws SQLException{
		PreparedStatement ps;
		cnn=ConectorBBDD.saberEstado();
		try {
			
			ps=cnn.getConexion().prepareStatement(SQL_INSERT);
			ps.setInt(1, c.getId());
			ps.setString(2, c.getName());
			ps.setString(3, c.getPas());

			
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
	public boolean update(Logs c) throws SQLException{
		PreparedStatement ps;
		cnn=ConectorBBDD.saberEstado();
		try {
			
			ps=cnn.getConexion().prepareStatement(SQL_UPDATE);
			ps.setString(1, c.getName());
			ps.setString(2, c.getPas());
			ps.setInt(3, c.getId());

			
			if(ps.executeUpdate()>0){
				return true;
			}
		
		}finally{
			cnn.cerrarConexion(); //cerrar conexion al final de la instrucción si o si
		}
		return false;
	}

	@Override
	public Logs read(Object key) throws SQLException{
		PreparedStatement ps;
		ResultSet res;
		Logs c=null;
		cnn=ConectorBBDD.saberEstado();
		try {
			
			ps=cnn.getConexion().prepareStatement(SQL_READ);
			ps.setString(1, key.toString());
			
			res = ps.executeQuery();
			
			while(res.next()){
				c=new Logs(res.getInt(1),res.getString(2),res.getString(3), res.getInt(4));
			}
			return c;
		
		}finally{
			cnn.cerrarConexion(); //cerrar conexion al final de la instrucción si o si
		}
		
	}

	@Override
	public ArrayList<Logs> readAll() throws SQLException{
		PreparedStatement ps;
		ArrayList<Logs> array=new ArrayList<Logs>();
		ResultSet res;
		Logs c=null;
		cnn=ConectorBBDD.saberEstado();
		try {
			
			ps=cnn.getConexion().prepareStatement(SQL_READALL);
						
			res = ps.executeQuery();
			
			while(res.next()){
				array.add(new Logs(res.getInt(1),res.getString(2),res.getString(3), res.getInt(4)));
			}
		
		} finally{
			cnn.cerrarConexion(); //cerrar conexion al final de la instrucción si o si
		}
		return array;
	}
	
	public boolean isUserSystem(String name, String pass) throws SQLException{
		PreparedStatement ps;
		ResultSet res;
		Logs c=null;
		cnn=ConectorBBDD.saberEstado();
		try {
			
			ps=cnn.getConexion().prepareStatement(SQL_READ_LOGIN);
			ps.setString(1, name);
			ps.setString(2,pass);
			
			res = ps.executeQuery();
			
			while(res.next()){
				return true;
			}

		
		}finally{
			cnn.cerrarConexion(); //cerrar conexion al final de la instrucción si o si
		}
		return false;
	}
	
	public int getRolUser(String name, String pass) throws SQLException{
		PreparedStatement ps;
		ResultSet res;
		Logs c=null;
		cnn=ConectorBBDD.saberEstado();
		try {
			
			ps=cnn.getConexion().prepareStatement(SQL_GET_ROL_USER);
			ps.setString(1, name);
			ps.setString(2,pass);
			
			res = ps.executeQuery();
			
			while(res.next()){
				return res.getInt(1);
			}

		
		}finally{
			cnn.cerrarConexion(); //cerrar conexion al final de la instrucción si o si
		}
		return -1;
	}

}
