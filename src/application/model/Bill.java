package application.model;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class Bill implements MyModel{
	private int id;
	private int childId;
	private int partnerId;
	private Child ch;
	private Partner partner;
	private String partName;
	private DayOfWeek day; // ” ‰ Ã
	private LocalDate payDate;
	private LocalDate expiredDate;
	private int duration;
	
	private double newPayment;
	private double newOneSessionPrice;
	private double oldRestMoney;
	private int oldDoneSessions;
	private double oldOneSessionPrice;
	private double oldPaidUpMoney;
	
	public Bill()
	{
		id = 0;
		childId = 0;
		partnerId = 0;
		ch = new Child();
		partner = new Partner();
		partName = partner.getName();

		newPayment = 0;
		oldRestMoney = 0;
		oldDoneSessions = 0;
		oldOneSessionPrice = 1;
		
		this.payDate = LocalDate.now();
		day = payDate.getDayOfWeek();
		expiredDate = payDate.plusDays(duration);
	}
	
	// insert new bill
	public Bill(Child ch , double newPayment , Partner part , LocalDate date)
	{
		id = 0;
		childId = ch.getId();
		partnerId = part.getId();
		this.ch = ch;
		this.partner = part;
		partName = partner.getName();
		this.newPayment = newPayment;
		oldRestMoney = ch.getRestMoney();
		oldDoneSessions = ch.getDoneSessions();
		oldOneSessionPrice = ch.getOneSessionPrice();
		
		this.payDate = date;
		day = payDate.getDayOfWeek();
		expiredDate = payDate.plusDays(duration);
	}

	public void printBill()
	{
		System.out.println("newPayment = " + newPayment
						+ "\nold rest = " + oldRestMoney
						+ "\noldDone = " + oldDoneSessions
						+ "\noldOneSession Price = " + oldDoneSessions
						+ "\nnew Max Sessions = " + ((newPayment + oldRestMoney)/oldOneSessionPrice)
				);
	}
	public void setNewPayment(double pay)
	{
		this.newPayment = pay;
	}
	
	public double getNewPayment() {
		return newPayment;
	}

	public double getOldRestMoney() {
		return oldRestMoney;
	}

	public int getOldDoneSessions() {
		return oldDoneSessions;
	}

	public double getOldOneSessionPrice() {
		return oldOneSessionPrice;
	}

	public void setChild(Child ch)
	{
		this.ch = ch;
		childId = ch.getId();
		oldPaidUpMoney = ch.getPaidUpMoney();
		oldRestMoney = ch.getRestMoney();
		oldDoneSessions = ch.getDoneSessions();
		oldOneSessionPrice = ch.getOneSessionPrice();
	}
	public double getOldPaidUpMoney() {
		return oldPaidUpMoney;
	}

	public Child getChild()
	{
		return this.ch;
	}
	public Partner getPartner()
	{
		return this.partner;
	}
	public void setPartner(Partner part)
	{
		this.partner = part;
		partnerId = part.getId();
		partName = partner.getName();
	}
	
	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public int getChildId()
	{
		return childId;
	}
	public int getPartnerId()
	{
		return partnerId;
	}

	
	
	
	
	
	
	
	public DayOfWeek getDay() {
		return day;
	}


	public LocalDate getPayDate() {
		return payDate;
	}
	public String getPayDateString() {
		return payDate.toString();
	}

	public void setPayDate(LocalDate payDate) {
		this.payDate = payDate;
		day = payDate.getDayOfWeek();
		expiredDate = payDate.plusDays(duration);
	}
	public void setPayDate(String payDate) {
		this.payDate = LocalDate.parse(payDate);
		day = this.payDate.getDayOfWeek();
		expiredDate = this.payDate.plusDays(duration);
	}

	public LocalDate getExpiredDate() {
		return expiredDate;
	}
	public String getExpiredDateString() {
		return expiredDate.toString();
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
		expiredDate = payDate.plusMonths(duration);
	}

	public double getNewOneSessionPrice() {
		return newOneSessionPrice;
	}

	public void setNewOneSessionPrice(double newOneSessionPrice) {
		this.newOneSessionPrice = newOneSessionPrice;
	}


	
	

}
