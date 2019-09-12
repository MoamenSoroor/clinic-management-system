package application.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class BillModel {
	private IntegerProperty id;
	private StringProperty childName;
	private StringProperty payDay;
	private StringProperty payDate;
	private StringProperty partnerName;
	private DoubleProperty newPayment;
	private DoubleProperty newOneSessionPrice;
	
	private DoubleProperty restMoney;
	private DoubleProperty allMoney;
	private IntegerProperty newMaxSession;
	
	public BillModel(Bill bill)
	{
		// it should use after doing new payment calculations
		id = new SimpleIntegerProperty(bill.getId());
		childName = new SimpleStringProperty(bill.getChild().getName());
		payDay = new SimpleStringProperty(bill.getDay().toString());
		payDate = new SimpleStringProperty(bill.getPayDate().toString());
		partnerName = new SimpleStringProperty(bill.getPartName());
		newPayment = new SimpleDoubleProperty(bill.getNewPayment());
		newOneSessionPrice = new SimpleDoubleProperty(bill.getNewOneSessionPrice());
		restMoney = new SimpleDoubleProperty(bill.getOldRestMoney());
		allMoney = new SimpleDoubleProperty(bill.getOldRestMoney() + bill.getNewPayment());
		int newMax = (int)((bill.getOldRestMoney() + bill.getNewPayment())/bill.getOldOneSessionPrice());
		newMaxSession = new SimpleIntegerProperty(newMax);
		setNulls();
	}
	private void setNulls()
	{
		if(partnerName.getValue().equals("0"))
			partnerName.setValue("");
	}

	public IntegerProperty getId() {
		return id;
	}

	public StringProperty getChildName() {
		return childName;
	}
	public DoubleProperty getNewOneSessionPrice() {
		return newOneSessionPrice;
	}


	public StringProperty getPayDay() {
		return payDay;
	}

	public StringProperty getPayDate() {
		return payDate;
	}
	public StringProperty getPartnerName() {
		return partnerName;
	}

	public DoubleProperty getNewPayment() {
		return newPayment;
	}

	public DoubleProperty getRestMoney() {
		return restMoney;
	}

	public DoubleProperty getAllMoney() {
		return allMoney;
	}

	public IntegerProperty getNewMaxSession() {
		return newMaxSession;
	}
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
