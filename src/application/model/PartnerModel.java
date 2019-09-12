package application.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PartnerModel {
	private IntegerProperty id;
	private StringProperty name;
	private StringProperty address;
	private StringProperty groundPhone;
	private StringProperty phone2;
	private StringProperty phone3;
	
	
	public PartnerModel(Partner part)
	{
		id = new SimpleIntegerProperty(part.getId());
		name = new SimpleStringProperty(part.getName());
		address = new SimpleStringProperty(part.getAddress());
		groundPhone = new SimpleStringProperty("" + part.getGroundPhone());
		phone2 = new SimpleStringProperty("" + part.getPhone1());
		phone3 = new SimpleStringProperty("" + part.getPhone2());
		setNulls();
	}

	public void setNulls()
	{
		if(address.getValue().equals("0"))
			address.setValue("");
		if(groundPhone.getValue().equals("0"))
			groundPhone.setValue("");
		if(phone2.getValue().equals("0"))
			phone2.setValue("");
		if(phone3.getValue().equals("0"))
			phone3.setValue("");
	}

	public IntegerProperty getId() {
		return id;
	}


	public StringProperty getName() {
		return name;
	}


	public StringProperty getAddress() {
		return address;
	}


	public StringProperty getGroundPhone() {
		return groundPhone;
	}


	public StringProperty getPhone2() {
		return phone2;
	}


	public StringProperty getPhone3() {
		return phone3;
	}

	

}
