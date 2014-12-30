package DAO;

import java.sql.SQLException;
import java.util.ArrayList;




public interface InterfaceDAO <CualquierCosa>{

		public boolean create(CualquierCosa c)throws SQLException;
		public boolean delete(Object key)throws SQLException;
		public boolean update(CualquierCosa c)throws SQLException;		
		public CualquierCosa read(Object key)throws SQLException;		
		public ArrayList<CualquierCosa> readAll()throws SQLException;
}
