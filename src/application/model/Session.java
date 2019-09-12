package application.model;

import java.time.LocalDate;

public class Session implements MyModel {
	private int id;
	private int childId;
	private Specialist specialist;
	private Partner partner;
	private String spName;
	private String partName;
	private Child ch;
	private int childDoneSessions;	//  ” ‰ Ã
	private int childMaxSessions;	// ” ‰ Ã
	private LocalDate sessionDate;
	private boolean expired;
	
	public Session getSession()
	{
		Session s2 = new Session(id ,specialist, ch ,partner , sessionDate);
		return s2;
	}
	public Session() {
		super();
		ch = new Child();
		specialist = new Specialist();
		
	}
	public Session(int id, Specialist sp, Child ch,Partner partner, LocalDate sessionDate) {
		super();
		this.id = id;
		this.specialist = sp;
		this.ch = ch;
		this.partner = partner;
		ch.addDoneSession();
		this.sessionDate = sessionDate;
		childDoneSessions = ch.getDoneSessions();
		childId = ch.getId();
		childMaxSessions = ch.getNumberOfSessions();
		if(ch.isExpired())
			expired = true;
		else
			expired = false;
		if(this.partner == null)
			this.partner = new Partner();
		if(this.specialist == null)
			this.specialist = new Specialist();
		spName = specialist.getName();
		partName = this.partner.getName();

	}
	public Session(Specialist sp, Child ch,Partner partner, LocalDate sessionDate) {
		super();
		this.specialist = sp;
		this.ch = ch;
		this.partner = partner;
		ch.addDoneSession();
		this.sessionDate = sessionDate;
		childDoneSessions = ch.getDoneSessions();
		childId = ch.getId();
		childMaxSessions = ch.getNumberOfSessions();
		if(ch.isExpired())
			expired = true;
		else
			expired = false;
		if(this.partner == null)
			this.partner = new Partner();
		if(this.specialist == null)
			this.specialist = new Specialist();
		spName = specialist.getName();
		partName = this.partner.getName();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getChildId() {
		return childId;
	}
	public void setChildId(int childId) {
		this.childId = childId;
	}
	public Specialist getSpecialist() {
		return specialist;
	}
	public void setSpecialist(Specialist specialist) {
		this.specialist = specialist;
		spName = specialist.getName();
	}
	public Partner getPartner() {
		return partner;
	}
	public void setPartner(Partner partner) {
		this.partner = partner;
		partName = partner.getName();
	}
	public String getSpName() {
		return spName;
	}
	public String getPartName() {
		return partName;
	}
	
	public void setSpName(String spName) {
		this.spName = spName;
	}
	public void setPartName(String partName) {
		this.partName = partName;
	}
	public Child getCh() {
		return ch;
	}
	public void setCh(Child ch) {
		this.ch = ch;
		childDoneSessions = ch.getDoneSessions();
		childId = ch.getId();
		childMaxSessions = ch.getNumberOfSessions();
		if(ch.isExpired())
			expired = true;
		else
			expired = false;
	}
	public int getChildDoneSessions() {
		return childDoneSessions;
	}
	public void setChildDoneSessions(int childDoneSessions) {
		this.childDoneSessions = childDoneSessions;
		ch.setDoneSessions(childDoneSessions);
		if(childDoneSessions <= childMaxSessions)
			expired = false;
		else expired = true;
	}
	public int getChildMaxSessions() {
		return childMaxSessions;
	}
	public void setChildMaxSessions(int childMaxSessions) {
		this.childMaxSessions = childMaxSessions;
		ch.setNumberOfSessions(childMaxSessions);
		if(childDoneSessions <= this.childMaxSessions)
			expired = false;
		else expired = true;
	}
	public LocalDate getSessionDate() {
		return sessionDate;
	}
	public String getSessionDateString() {
		return sessionDate.toString();
	}
	public void setSessionDate(LocalDate sessionDate) {
		this.sessionDate = sessionDate;
	}
	public void setSessionDate(String sessionDate) {
		this.sessionDate = LocalDate.parse(sessionDate);
	}
	
	public boolean getExpired() {
		return expired;
	}
	public String getExpiredString() {
		if(getExpired())
			return "„‰ ÂÏ";
		else
			return "”«—Ï";
	}
	
	
}
