package DTO;

public class Logs {
	
	private int id;
	private String name;
	private String pas;
	private int rol;
	
	public Logs(int id, String name, String pas, int rol) {
		
		setId(id);
		setName(name);
		setPas(pas);
		setRol(rol);
	}

	public int getId() {return id;}
	public String getName() {return name;}
	public String getPas() {return pas;}
	public int getRol(){return rol;}
	
	public void setRol(int rol){
		this.rol=rol;
	}
	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPas(String pas) {
		this.pas = pas;
	}
	

}
