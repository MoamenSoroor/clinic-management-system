package application.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SpecialistModel {
	
	private IntegerProperty id;
	private StringProperty name;
	private StringProperty phone1;
	private StringProperty phone2;

	public SpecialistModel(Specialist sp)
	{
		id = new SimpleIntegerProperty(sp.getId());
		name = new SimpleStringProperty(sp.getName());
		phone1 = new SimpleStringProperty("" + sp.getPhone1());
		phone2 = new SimpleStringProperty("" + sp.getPhone2());
		setNulls();
	}

	private void setNulls()
	{
		if(phone1.getValue().equals("0"))
			phone1.setValue("");
		if(phone2.getValue().equals("0"))
			phone2.setValue("");
	}
	public IntegerProperty getId() {
		return id;
	}

	public StringProperty getName() {
		return name;
	}

	public StringProperty getPhone1() {
		return phone1;
	}

	public StringProperty getPhone2() {
		return phone2;
	}
	
}
