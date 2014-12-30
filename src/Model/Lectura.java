package Model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Lectura {
	
	private final static Path ruta=Paths.get("fastLogin.dat");
	
	public static void saveFastLogin(String name, String pass){
		
		DataOutputStream fw=null;
		try {
			fw=new DataOutputStream (Files.newOutputStream(ruta,
								java.nio.file.StandardOpenOption.CREATE,
								java.nio.file.StandardOpenOption.TRUNCATE_EXISTING));
			
			fw.writeUTF(name);
			fw.writeUTF(pass);
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}finally{
			try {
				fw.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
	public static String[] readFastLogin(){
		
		if(Files.exists(ruta)){
			String[] aux=null;
		DataInputStream fr=null;
		try {
			aux=new String[2];
			fr=new DataInputStream (Files.newInputStream(ruta));
			aux[0]=fr.readUTF();
			aux[1]=fr.readUTF();
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}finally{
			try {
				fr.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return aux;
		}
		return null;
	}
	public static void removeFastLogin(){
		
		try {
			
			Files.deleteIfExists(ruta);
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
