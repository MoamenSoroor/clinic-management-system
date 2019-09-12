package application.model;

import java.io.File;
import java.time.LocalDate;

public class Child implements MyModel{
	
	
	private int id;
	private String name;
	private String status;
	private boolean gender;
	private String genderString;
	private LocalDate birth;
	private int age;
	private Partner p1;
	private Partner p2;
	
	private double oneSessionPrice;
	private double paidUpMoney;
	private double restMoney; // concloded
	private int numberOfSessions; // Concloded
	private int doneSessions;
	private boolean expired = false;

	private File photo;
	private File oldPhoto;
	
	// for undo system
	private boolean part1New = false;
	private boolean part2New = false;

	public Child() {
		this.id = 0;
		name = "0";
		status = "0";
		birth = LocalDate.of(2006, 1, 1);
		age = LocalDate.now().getYear() - birth.getYear();
		gender = true;
		genderString = "ÐßÑ";
		this.p1 = new Partner();
		this.p2 = new Partner();
		this.oneSessionPrice = 1;
		this.paidUpMoney = 0;
		this.numberOfSessions = (int) (paidUpMoney / oneSessionPrice);
		this.doneSessions = 0;
		this.restMoney = paidUpMoney - doneSessions * oneSessionPrice;
		if (doneSessions >= numberOfSessions)
			this.expired = true;
		else
			this.expired = false;

		photo = null;
		oldPhoto = null;
	}
	

	public File getOldPhoto() {
		return oldPhoto;
	}


	public void setOldPhoto(File oldPhoto) {
		this.oldPhoto = oldPhoto;
	}


	public Child(int id, String name, String status, boolean gender, LocalDate birth, int oneSessionPrice, int paidUpMoney,
			int doneSessions) {
		super();
		this.id = id;
		this.name = name;
		this.status = status;
		this.gender = gender;
		setGender(gender);
		this.birth = birth;
		age = LocalDate.now().getYear() - this.birth.getYear();
		
		this.p1 = new Partner();
		this.p2 = new Partner();

		this.oneSessionPrice = oneSessionPrice;
		this.paidUpMoney = paidUpMoney;
		this.doneSessions = doneSessions;
		this.numberOfSessions = paidUpMoney / oneSessionPrice;
		this.restMoney = paidUpMoney - doneSessions * oneSessionPrice;
		if (doneSessions >= numberOfSessions)
			this.expired = true;
		}

	public void setPhoto(File file)
	{
		photo = file;
	}

	public File getPhoto()
	{
		return photo;
	}
	
	public void setChild(Child ch) {
		this.id = ch.getId();
		this.name = ch.getName();
		this.status = ch.getStatus();
		this.genderString = ch.getGenderString();
		this.gender = ch.getGender();
		this.birth = ch.getBirthLocalDate();
		age = LocalDate.now().getYear() - birth.getYear();
		
		p1 = ch.getP1();
		p2 = ch.getP2();
		
		this.oneSessionPrice = ch.getOneSessionPrice();
		this.paidUpMoney = ch.getPaidUpMoney();
		this.doneSessions = ch.getDoneSessions();
		this.numberOfSessions = (int) (paidUpMoney / oneSessionPrice);
		this.restMoney = paidUpMoney - doneSessions * oneSessionPrice;
		if (doneSessions >= numberOfSessions)
			this.expired = true;
		else
			this.expired = false;

	}

	public int getAge() {
		return age;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDate getBirthLocalDate() {
		return birth;
	}
	public String getBirthDate() {
		
		return birth.toString();
	}

	public void setBirth(LocalDate birth) {
		this.birth = birth;
		age = LocalDate.now().getYear() - this.birth.getYear();
	}
	public void setBirth(String birth) {
		this.birth = LocalDate.parse(birth);
		age = LocalDate.now().getYear() - this.birth.getYear();
	}

	public boolean getGender() {
		return gender;
	}
	public int getGenderInt()
	{
		if(gender == true)
			return 1;
		else return 0;
	}
	public void setGenderInt(int gen)
	{
		if(gen == 1)
		{
			gender = true;
			genderString = "ÐßÑ";
		}
		else if(gen == 0)
		{
			gender = false;
			genderString = "ÇäËì";
		}
		else return;
	}

	public void setGender(boolean gender) {
		this.gender = gender;
		if(gender == true)
			genderString = "ÐßÑ";
		else
			genderString = "ÇäËì";
	}

	public double getOneSessionPrice() {
		return oneSessionPrice;
	}

	public void setOneSessionPrice(Double oneSessionPrice) {
		this.oneSessionPrice = oneSessionPrice;
		this.numberOfSessions = (int) (paidUpMoney / oneSessionPrice);
		this.restMoney = paidUpMoney - doneSessions * oneSessionPrice;
		if (doneSessions >= numberOfSessions)
			this.expired = true;
		else this.expired = false;
	}

	public double getPaidUpMoney() {
		return paidUpMoney;
	}

	public void setPaidUpMoneyPlus(double paidUpMoney) {

		if(restMoney < 0)
		{
			this.paidUpMoney = paidUpMoney;
			this.doneSessions -= numberOfSessions;
			this.numberOfSessions = (int) (paidUpMoney / oneSessionPrice);
			this.restMoney = this.paidUpMoney - doneSessions * oneSessionPrice;
			if (doneSessions >= numberOfSessions)
				this.expired = true;
			else this.expired = false;
		}
		else{
			this.paidUpMoney = paidUpMoney + restMoney;
			this.doneSessions = 0;
			this.numberOfSessions = (int) (this.paidUpMoney / oneSessionPrice);
			this.restMoney = this.paidUpMoney - doneSessions * oneSessionPrice;
			if (doneSessions >= numberOfSessions)
				this.expired = true;
			else this.expired = false;
		}
		
	}

	public void setPaidUpMoney(double paidUpMoney) {
		this.paidUpMoney = paidUpMoney;
		this.numberOfSessions = (int) (this.paidUpMoney / oneSessionPrice);
		this.restMoney = this.paidUpMoney - doneSessions * oneSessionPrice;
		if (doneSessions >= numberOfSessions)
			this.expired = true;
		else this.expired = false;
	}

	public int getNumberOfSessions() {
		return numberOfSessions;
	}

	public void setNumberOfSessions(int numberOfSessions) {
		this.numberOfSessions = numberOfSessions;
		this.paidUpMoney = this.numberOfSessions * oneSessionPrice;
		this.restMoney = paidUpMoney - doneSessions * oneSessionPrice;
		if (doneSessions >= numberOfSessions)
			this.expired = true;
		else this.expired = false;
	}

	public int getDoneSessions() {
		return doneSessions;
	}

	public void setDoneSessions(int doneSessions) {
		this.doneSessions = doneSessions;
		this.restMoney = paidUpMoney - this.doneSessions * oneSessionPrice;
		if (doneSessions >= numberOfSessions)
			this.expired = true;
		else this.expired = false;
	}

	public void addDoneSession() {
		this.doneSessions++;
		this.restMoney -= oneSessionPrice;
		if (doneSessions >= numberOfSessions)
			this.expired = true;
		else this.expired = false;
	}
	public double getRestMoney() {
		return this.restMoney;
	}

	public Boolean isExpired() {
		return expired;
	}

	public void setExpired(boolean ex) {
		expired = ex;
	}
	
	public Partner getP1() {
		return p1;
	}


	public void setP1(Partner p1) {
		this.p1 = p1;
	}


	public Partner getP2() {
		return p2;
	}


	public void setP2(Partner p2) {
		this.p2 = p2;
	}


	public void printChild() {
		System.out.println("\nid = " + id + "\nname: " + name + "\nstatus = " + status + "\ngender = " +gender +  "\nGenderString = " + genderString
				+ "\nbirth = " + getBirthLocalDate() + "\nbirth = " + getBirthDate() +"\nage = " 
				+ age + "\noneSessionPrice:" + oneSessionPrice + "\npaidUpMoney: " + paidUpMoney
				+ "\ndoneSessions: " + doneSessions + "\nexpired :" + expired
				+ "\nrestMoney = " + restMoney + "\nnumberOfSessions = " + numberOfSessions
				+"\npartner1 = "+ getP1().getName() + "\npartner2 = " + getP2().getName());
		
	}


	public String getExpiredString() {
		int restSessions = numberOfSessions - doneSessions;
		
		if(isExpired())
			return "ãäÊåì";
		else if(restSessions >= 3)
			return "ÓÇÑì";
		else if(restSessions < 3)
			return "ÊÌÏíÏ (ÃÎÑ 3 ÌáÓÇÊ)";
		else
			return "ÎØÃ";
	}


	public String getGenderString() {
		return genderString;
	}


	public boolean isPart1New() {
		return part1New;
	}


	public void setPart1New(boolean part1New) {
		this.part1New = part1New;
	}


	public boolean isPart2New() {
		return part2New;
	}


	public void setPart2New(boolean part2New) {
		this.part2New = part2New;
	}
	public int getRestSessions() {
		return numberOfSessions - doneSessions;
	}

}
