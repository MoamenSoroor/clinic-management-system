package application.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SessionModel {
	private IntegerProperty id;
	private IntegerProperty childId;
	private IntegerProperty childDoneSessions;	//  ” ‰ Ã
	private IntegerProperty childMaxSessions;	// ” ‰ Ã
	private StringProperty childName;
	private StringProperty specialistName;
	private StringProperty partnerName;
	private StringProperty sessionDate;
	private StringProperty expired;
	private StringProperty status;
	
	
	public SessionModel(Session session)
	{
		this.id = new SimpleIntegerProperty(session.getId());
		this.childId = new SimpleIntegerProperty(session.getCh().getId());
		childDoneSessions = new SimpleIntegerProperty(session.getCh().getDoneSessions());
		childMaxSessions = new SimpleIntegerProperty(session.getCh().getNumberOfSessions());
		childName = new SimpleStringProperty(session.getCh().getName());
		specialistName = new SimpleStringProperty(session.getSpName());
		partnerName = new SimpleStringProperty(session.getPartName());
		sessionDate = new SimpleStringProperty(session.getSessionDate().toString());
		expired = new SimpleStringProperty(session.getExpiredString());
		status = new SimpleStringProperty(session.getCh().getStatus());
		setNulls();
	}
	private void setNulls()
	{
		if(specialistName.getValue().equals("0"))
			specialistName.setValue("");
		if(partnerName.getValue().equals("0"))
			partnerName.setValue("");
		
	}
	public IntegerProperty getId() {
		return id;
	}

	public IntegerProperty getChildId() {
		return childId;
	}

	public IntegerProperty getChildDoneSessions() {
		return childDoneSessions;
	}

	public IntegerProperty getChildMaxSessions() {
		return childMaxSessions;
	}

	public StringProperty getChildName() {
		return childName;
	}

	public StringProperty getSpecialistName() {
		return specialistName;
	}

	public StringProperty getPartnerName() {
		return partnerName;
	}

	public StringProperty getSessionDate() {
		return sessionDate;
	}

	public StringProperty getExpired() {
		return expired;
	}

	public StringProperty getStatus() {
		return status;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	


}
