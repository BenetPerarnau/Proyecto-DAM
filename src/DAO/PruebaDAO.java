package DAO;

import java.sql.SQLException;
import java.util.ArrayList;

import DTO.Logs;
import DTO.Proveedor;



public class PruebaDAO {

	public static void  main (String[] args) throws SQLException{
		
		ClienteDAO c=new ClienteDAO();
		LogsDAO l=new LogsDAO();
		EmpleadoDAO e=new EmpleadoDAO();
		ProveedorDAO p=new ProveedorDAO();
		
		System.out.println(p.read("D31241252").getCIF());
		if(p.delete("D31241252")){
			System.out.println("Eliminat");
		}else{
			System.out.println("error");
		}
		
		System.out.println(e.read("23485435J").getNom());
		
		/*System.out.println(c.read("39382545J").getNom());
		
		System.out.println("Nom => "+l.read("1").getName()+" Pas => "+l.read("1").getPas());
		l.update(new Logs(1,"Benet2","1111"));
		System.out.println("Nom => "+l.read("1").getName()+" Pas => "+l.read("1").getPas());*/
		
	}

}
