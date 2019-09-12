package application.model;

public class Specialist implements MyModel{
	private int id;
	private String name;
	private long phone1;
	private long phone2;
	
	public Specialist() {
		super();
		this.id = 0;
		this.name = "0";
		this.phone1 = 0;
		this.phone2 = 0;
	}
	public Specialist( String name, long phone1, long phone2) {
		super();
		this.name = name;
		this.phone1 = phone1;
		this.phone2 = phone2;
	}
	public Specialist(int id, String name) {
		super();
		this.id = id;
		this.name = name;
		this.phone1 = 0;
		this.phone2 = 0;
	}
	public Specialist(int id, String name, long phone1, long phone2) {
		super();
		this.id = id;
		this.name = name;
		this.phone1 = phone1;
		this.phone2 = phone2;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getPhone1() {
		return phone1;
	}
	public void setPhone1(long phone1) {
		this.phone1 = phone1;
	}
	public long getPhone2() {
		return phone2;
	}
	
	public void setPhone2(long phone2) {
		this.phone2 = phone2;
	}
	

}
