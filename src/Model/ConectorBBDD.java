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
	

	private ConectorBBDD(){
		
		try {
			
			Class.forName("com.mysql.jdbc.Driver");//Driver
			
			conexion=DriverManager.getConnection("jdbc:mysql://"+Constant.IP_SERVER+":3306/"+Constant.NAME_BBDD,
												 Constant.USER_BBDD,
												 Constant.PASSWORD_BBDD);
			
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
			System.out.println("error 1");
		
		} catch (SQLException e) {
			
			e.printStackTrace();
			System.out.println("error 2");
		}
	}
	public synchronized static ConectorBBDD saberEstado(){//singleton
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
//	public void consultaTabla(String nametabla){
//		PreparedStatement consulta;
//		try {
//			consulta = conexion.prepareStatement("select * from "+nametabla+"");
//			resultado=consulta.executeQuery();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}
//	public Object[][] consultaDatosTablaToArray(String nametabla){
//		ArrayList<String[]> array=new ArrayList<String[]>();
//		PreparedStatement consulta;
//		try {
//			consulta = conexion.prepareStatement("select * from "+nametabla+"");
//			resultado=consulta.executeQuery();
//			
//			//número de columnas (campos) de la consula SQL            	  
//			int numColumnas = resultado.getMetaData().getColumnCount(); 
//			String name=resultado.getMetaData().getColumnName(1);
//			System.out.println("Num Columnas => "+numColumnas+""
//							+ "\nNombre Columna 1 => "+name);
//			
//			
//			while(resultado.next()){
//
//				String registro="";
//				for(int i=0; i<numColumnas; i++){
//					registro+=resultado.getString(i+1)+";";
//				}
//				array.add(registro.split(";"));
//					
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		Object[][] aux=new Object[array.size()][array.get(0).length];
//		
//		for(int i=0; i<array.size(); i++){
//			for(int j=0; j<array.get(i).length; j++){
//				aux[i][j]=array.get(i)[j];
//				System.out.print(aux[i][j]+"\t");
//			}
//			System.out.println("");
//		}
//		
//		return aux;
//	}
//	public String[] consultaTitulosTablaToArray(String nameTabla){
//		PreparedStatement consulta;
//		String[] aux=null;
//		try {
//			consulta = conexion.prepareStatement("select * from "+nameTabla+"");
//			resultado=consulta.executeQuery();
//			int numcl=resultado.getMetaData().getColumnCount();
//			aux=new String[numcl];
//			while(resultado.next()){
//				for(int i=0; i<numcl; i++){
//					aux[i]=resultado.getMetaData().getColumnName(i+1);
//				}			
//			}
//			
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return aux;
//	}
//	public boolean validUser(String name, String pass){
//		
//		try {
//			while(resultado.next()){
//				
//				if(resultado.getString(2).equals(name) && resultado.getString(3).equals(pass)){
//					
//					System.out.println(resultado.getString(2)+"\t"+resultado.getString(3));
//					return true;
//				}
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return false;
//	}
//	public int getRol(){
//		int rol=-1;
//		try {
//			 rol= resultado.getInt(4);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return rol;
//	}
}
