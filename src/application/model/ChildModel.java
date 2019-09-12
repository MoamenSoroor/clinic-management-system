package application.model;

import java.time.LocalDate;

import application.database.BillDatabase;
import application.utils.MUtils;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ChildModel {
	private IntegerProperty id;
	private StringProperty name;
	private StringProperty p1;
	private StringProperty p2;
	private StringProperty status;
	private StringProperty gender;
	private StringProperty expired;
	private IntegerProperty numberOfSessions; // Concloded
	private IntegerProperty doneSessions;
	public ChildModel(Child ch1)
	{
		id = new SimpleIntegerProperty(ch1.getId());
		name = new SimpleStringProperty(ch1.getName());
		this.p1 = new SimpleStringProperty(ch1.getP1().getName());
		this.p2 = new SimpleStringProperty(ch1.getP2().getName());
		status = new SimpleStringProperty(ch1.getStatus());
		gender = new SimpleStringProperty(ch1.getGenderString());
		expired = new SimpleStringProperty(ch1.getExpiredString());
		numberOfSessions = new SimpleIntegerProperty(ch1.getNumberOfSessions());
		doneSessions= new SimpleIntegerProperty(ch1.getDoneSessions());
		LocalDate date = BillDatabase.selectLastBillOfChild(ch1.getId()).getPayDate();
		LocalDate end = date.plusMonths(MUtils.getDuration());
		if(end.isBefore(LocalDate.now()))
			expired.setValue("«‰ Â«¡ „œ… «·œ›⁄");
		setNulls();
	}
	public void setNulls()
	{
		if(p1.getValue().equals("0"))
			p1.setValue("");
		if(p2.getValue().equals("0"))
			p2.setValue("");
		if(status.getValue().equals("0"))
			status.setValue("");
	}
	public IntegerProperty getId() {
		return id;
	}
	public StringProperty getName() {
		return name;
	}
	public StringProperty getP1() {
		return p1;
	}
	public StringProperty getP2() {
		return p2;
	}
	public StringProperty getStatus() {
		return status;
	}
	public StringProperty getGender() {
		return gender;
	}
	public StringProperty getExpired() {
		return expired;
	}
	public IntegerProperty getNumberOfSessions() {
		return numberOfSessions;
	}
	public IntegerProperty getDoneSessions() {
		return doneSessions;
	}
	
	
	


}
