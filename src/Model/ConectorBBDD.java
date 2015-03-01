package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Constants.Constant;

public class ConectorBBDD {
	
	private ResultSet resultado;
	public static ConectorBBDD instancia; //aplicar Singleton
	private static Connection conexion;
	

	private ConectorBBDD() throws SQLException{
		
		try {
			
			Class.forName("com.mysql.jdbc.Driver");//Driver
			
			conexion=DriverManager.getConnection("jdbc:mysql://"+Constant.IP_SERVER+":3306/"+Constant.NAME_BBDD,
												 Constant.USER_BBDD,
												 Constant.PASSWORD_BBDD);
			
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
			System.out.println("error 1");
		
		} 
	}
	public synchronized static ConectorBBDD saberEstado() throws SQLException{//singleton
		//la unica forma de hacer una conexion es invocando a este metodo
		if(instancia==null){
			instancia=new ConectorBBDD();
			
		}
		return instancia;
	}
	public static Connection getConexion() {
		return conexion;
	}
	public static void cerrarConexion(){
		if(instancia!=null)
			instancia=null;
	}
}
