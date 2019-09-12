package application.users;

public class User {

	private int id;
	private String name;
	private String password;
	public User() {
		id = 0;
		name = "admin";
		password = "admin";
	}
	public User(String name, String password) {
		super();
		this.id = 0;
		this.name = name;
		this.password = password;
	}
	public User(int id, String name, String password) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	

}
