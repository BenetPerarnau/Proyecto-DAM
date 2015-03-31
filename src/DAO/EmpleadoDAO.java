package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.PreparedStatement;

import DTO.Empleado;
import Exceptions.ExceptionDNI_CIF;
import Exceptions.ExceptionNcuenta;
import Exceptions.ExceptionNombre;
import Exceptions.ExceptionSalario;
import Model.ConectorBBDD;

public class EmpleadoDAO implements InterfaceDAO<Empleado>{
	
	private static final String SQL_INSERT="INSERT INTO EMPLEADOS "
											+ "(DNI, NOMBRE, APELLIDO, DIRECCION, TELEFONO, MOVIL, PUESTO, SALARIO, CUENTA) "
											+ "VALUES(?,?,?,?,?,?,?,?,?)";
	private static final String SQL_DELETE="DELETE FROM EMPLEADOS "
					 						+ "WHERE DNI = ?";
	private static final String SQL_UPDATE="UPDATE EMPLEADOS "
				     						+ "SET NOMBRE = ?, APELLIDO = ?, DIRECCION = ?, TELEFONO = ?, "
				     						+ "MOVIL = ?, PUESTO = ?, SALARIO = ?, CUENTA=? "
				     						+ "WHERE DNI = ?";
	private static final String SQL_READ="SELECT * FROM EMPLEADOS WHERE DNI = ?";
	private static final String SQL_READALL="SELECT * FROM EMPLEADOS";
	private static final String SQL_READ_ALL_TITLES_TABLA="SELECT * FROM EMPLEADOS";
	private static  ConectorBBDD cnn;//aplicamos Singleton

	@Override
	public boolean create(Empleado c) throws SQLException{
		PreparedStatement ps;
		cnn=ConectorBBDD.saberEstado();
		try {
			ps=(PreparedStatement) cnn.getConexion().prepareStatement(SQL_INSERT);
			ps.setString(1, c.getDni());
			ps.setString(2, c.getNom());
			ps.setString(3, c.getApe());
			ps.setString(4, c.getAdre());
			ps.setString(5, c.getTlf());
			ps.setString(6, c.getMvl());
			ps.setString(7, c.getPuesto());
			ps.setDouble(8, c.getSalario());
			ps.setString(9, c.getCuenta());
			
			if(ps.executeUpdate()>0){
				return true;
			}
			
		}finally{
			cnn.cerrarConexion();
		}
		return false;
	}

	@Override
	public boolean delete(Object key) throws SQLException{
		PreparedStatement ps;
		cnn=ConectorBBDD.saberEstado();
		try {
			ps=(PreparedStatement) cnn.getConexion().prepareStatement(SQL_DELETE);
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
	public boolean update(Empleado c) throws SQLException{
		PreparedStatement ps;
		cnn=ConectorBBDD.saberEstado();
		try {
			ps=(PreparedStatement) cnn.getConexion().prepareStatement(SQL_UPDATE);
			ps.setString(1, c.getNom());
			ps.setString(2, c.getApe());
			ps.setString(3, c.getAdre());
			ps.setString(4, c.getTlf());
			ps.setString(5, c.getMvl());
			ps.setString(6, c.getPuesto());
			ps.setDouble(7, c.getSalario());
			ps.setString(8, c.getCuenta());
			ps.setString(9, c.getDni());
			
			if(ps.executeUpdate()>0){
				return true;
			}
			
		}finally{
			cnn.cerrarConexion();
		}
		return false;
	}

	@Override
	public Empleado read(Object key)throws SQLException {
		PreparedStatement ps;
		ResultSet res;
		Empleado a=null;
		cnn=ConectorBBDD.saberEstado();
		try {
			ps=(PreparedStatement) cnn.getConexion().prepareStatement(SQL_READ);
			ps.setString(1, key.toString());
			res=ps.executeQuery();
			
			while(res.next()){
				a=new Empleado(res.getString(1),res.getString(2),res.getString(3),res.getString(4),
							   res.getString(5),res.getString(6),res.getString(7),res.getDouble(8),
							   res.getString(9));
			}
			return a;
		}catch (ExceptionDNI_CIF e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExceptionNcuenta e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExceptionNombre e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExceptionSalario e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			cnn.cerrarConexion();
		}
		return a;
	}

	@Override
	public ArrayList readAll()throws SQLException {
		PreparedStatement ps;
		ResultSet res;
		Empleado a=null;
		ArrayList<Empleado> array=new ArrayList<Empleado>();
		cnn=ConectorBBDD.saberEstado();
		try {
			ps=(PreparedStatement) cnn.getConexion().prepareStatement(SQL_READALL);
			res=ps.executeQuery();
			while(res.next()){
				array.add(new Empleado(res.getString(1),res.getString(2),res.getString(3),res.getString(4),
						   res.getString(5),res.getString(6),res.getString(7),res.getDouble(8),
						   res.getString(9)));
			}
		}catch (ExceptionDNI_CIF e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExceptionNcuenta e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExceptionNombre e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExceptionSalario e) {
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
