package application.model;


public class Partner implements MyModel{
	private int id;
	private String name;
	private String address;
	private long groundPhone;
	private long phone1;
	private long phone2;
	
	
	// for undo system
	private boolean isNew = false;
	
	public Partner() {
		super();
		id = 0;
		name = "0";
		this.groundPhone = 0;
		this.phone1 = 0;
		this.phone2 = 0;
		this.address = "0";
	}
	public Partner(int id, String name) {
		super();
		this.id = id;
		this.name = name;
		this.phone1 = 0;
		this.phone2 = 0;
		this.address = "0";
		this.groundPhone = 0;
	}
	public Partner(Partner part) {
		super();
		this.id = part.getId();
		this.name = part.getName();
		this.groundPhone = part.getGroundPhone();
		this.phone1 = part.getPhone1();
		this.phone2 = part.getPhone2();
		this.address = part.getAddress();
	}
	
	public Partner(String name, long phone1, long phone2) {
		super();
		this.name = name;
		this.phone1 = phone1;
		this.phone2 = phone2;
		this.address = "0";
		this.groundPhone = 0;
	}
	public Partner(String name, long groundPhone, long phone1, long phone2 , String address) {
		super();
		this.name = name;
		this.groundPhone = groundPhone;
		this.phone1 = phone1;
		this.phone2 = phone2;
		this.address = address;
		if(address.equals(""))
			address = "0";
	}
	public Partner(int id , String name, long phone1, long phone2) {
		super();
		this.id = id;
		this.name = name;
		this.phone1 = phone1;
		this.phone2 = phone2;
		this.address = "0";
		this.groundPhone = 0;

	}
	public Partner(int id , String name, long groundPhone, long phone1, long phone2 , String address) {
		super();
		this.id = id;
		this.name = name;
		this.groundPhone = groundPhone;
		this.phone1 = phone1;
		this.phone2 = phone2;
		this.address = address;
		if(address.equals(""))
			address = "0";
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
		if(address.equals(""))
			address = "0";
	}
	public long getGroundPhone() {
		return groundPhone;
	}
	public void setGroundPhone(long groundPhone) {
		this.groundPhone = groundPhone;
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
	public boolean isNew() {
		return isNew;
	}
	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}
		
	

}
