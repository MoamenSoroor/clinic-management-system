package application.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import application.model.Session;
import application.model.SessionModel;
import application.undoRedoSystem.Bulk;
import application.undoRedoSystem.Operation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SessionDatabase extends Database {
	
	static int idCounter = maxId();
	public static int generateId()
	{
		idCounter = maxId();
		return ++idCounter;
	}
	
	public static int maxId()
	{
		int max = 0;
		PreparedStatement pr = null;
		ResultSet result = null;
		String qe = "select max(id) from Session";
		try {
			pr = conn.prepareStatement(qe);
			result = pr.executeQuery();
			max = result.getInt(1);
			return max;
		} catch (SQLException e) { e.printStackTrace(); }
		return 0;
	}
	
	public static boolean isMaxSessionForChild(int billId , int childId){
		int maxId = 0;
		PreparedStatement pr = null;
		ResultSet result = null;
		String qe = "select max(id) from session where childId = " + childId + ";";
		try {
			pr = conn.prepareStatement(qe);
			result = pr.executeQuery();
			maxId = result.getInt(1);
			if(maxId == billId)
				return true;
			else 
				return false;
		} catch (SQLException e) { e.printStackTrace(); }
		return false;
	}
	
	public static ObservableList<Session> selectAllSession()
	{
		PreparedStatement pr = null;
		ResultSet result = null;
		ObservableList<Session> list = FXCollections.observableArrayList();
		String qe = "select * from Session";
		try {
			pr = conn.prepareStatement(qe);
			result = pr.executeQuery();
			while(result.next())
			{
				Session session = new Session();
				session.setId(result.getInt(1));
				session.setCh(ChildDatabase.selectChild(result.getInt(2)));
				session.setSpecialist(SpecialistDatabase.selectSpecialist(result.getInt(3)));
				session.setPartner(PartnerDatabase.selectPartner(result.getInt(4)));
				session.setChildDoneSessions(result.getInt(5));
				session.setChildMaxSessions(result.getInt(6));
				session.setSessionDate(result.getString(7));
				session.setSpName(result.getString(9));
				session.setPartName(result.getString(10));
				list.add(session);
			}
			
			
			return list;
		} catch (SQLException e) { e.printStackTrace(); }
		return null;
	}

	public static ObservableList<SessionModel> selectAllSessionModel()
	{
		PreparedStatement pr = null;
		ResultSet result = null;
		ObservableList<SessionModel> list = FXCollections.observableArrayList();
		String qe = "select * from Session";
		try {
			pr = conn.prepareStatement(qe);
			result = pr.executeQuery();
			while(result.next())
			{
				Session session = new Session();
				session.setId(result.getInt(1));
				session.setCh(ChildDatabase.selectChild(result.getInt(2)));
				session.setSpecialist(SpecialistDatabase.selectSpecialist(result.getInt(3)));
				session.setPartner(PartnerDatabase.selectPartner(result.getInt(4)));
				session.setChildDoneSessions(result.getInt(5));
				session.setChildMaxSessions(result.getInt(6));
				session.setSessionDate(result.getString(7));
				session.setSpName(result.getString(9));
				session.setPartName(result.getString(10));

				SessionModel model = new SessionModel(session);
				list.add(model);
			}
			
			
			return list;
		} catch (SQLException e) { e.printStackTrace(); }
		finally{try { pr.close(); result.close(); } catch(SQLException ex){}}
		return null;
	}

	public static void deleteSession(int id){
		Session old = selectSession(id);
		PreparedStatement pr = null;
		String qe = "DELETE from session WHERE id = " + id+";";
		try {
			pr = conn.prepareStatement(qe);
			pr.execute();
			main.getSessionController().fillTable();
			idCounter = maxId();
			undoData.push(new Bulk(old, true, Operation.DELETE));
			main.getChildController().fillTable();
		} catch (SQLException e) { e.printStackTrace(); }
		finally{try { pr.close();} catch(SQLException ex){}}
	}
	public static void deleteSessionUndo(int id) {
		PreparedStatement pr = null;
		String qe = "DELETE from session WHERE id = " + id+";";

		try {
			pr = conn.prepareStatement(qe);
			pr.execute();
			main.getSessionController().fillTable();
			idCounter = maxId();
		} catch (SQLException e) { e.printStackTrace(); }
		finally{try { pr.close();} catch(SQLException ex){}}
	}
	
	public static void deleteSessionMulti(ObservableList<Session> ses){
		for(Session s : ses){
			deleteSession(s.getId());
		}
	}
	
	public static ArrayList<Session> deleteSessionsOfChild(String childName)
	{
		int childId = ChildDatabase.selectChild(childName).getId(); 
		ArrayList<Session> ses = selectSessionByChildId(childId); 
		PreparedStatement pr = null;
		String qe = "DELETE from session WHERE childId = " + childId +";";
		try {
			pr = conn.prepareStatement(qe);
			pr.execute();
			main.getSessionController().fillTable();
			idCounter = maxId();
			return ses;
		} catch (SQLException e) { e.printStackTrace(); }
		finally{try { pr.close();} catch(SQLException ex){}}
		return null;
	}

	
	public static ArrayList<Session> deleteSessionsOfChild(int id) {
		ArrayList<Session> ses = selectSessionByChildId(id); 
		PreparedStatement pr = null;
		String qe = "DELETE from session WHERE childId = " + id +";";
		try {
			pr = conn.prepareStatement(qe);
			pr.execute();
			main.getSessionController().fillTable();
			idCounter = maxId();
			return ses;
		} catch (SQLException e) { e.printStackTrace(); }
		finally{try { pr.close();} catch(SQLException ex){}}
		return null;
	}
	
	public static Session selectLastSessionOfChild(int childId) {
		PreparedStatement pr = null;
		ResultSet result = null;
		
		String qe = "select * from session where Id = (select max(id) from session where childId = "+childId+");" ;                           
		
		try {
			pr = conn.prepareStatement(qe);
			result = pr.executeQuery();	
			Session session = new Session();
			session.setId(result.getInt(1));
			session.setCh(ChildDatabase.selectChild(result.getInt(2)));
			session.setSpecialist(SpecialistDatabase.selectSpecialist(result.getInt(3)));
			session.setPartner(PartnerDatabase.selectPartner(result.getInt(4)));
			session.setChildDoneSessions(result.getInt(5));
			session.setChildMaxSessions(result.getInt(6));
			session.setSessionDate(result.getString(7));
			session.setSpName(result.getString(9));
			session.setPartName(result.getString(10));
			return session;
		} catch (SQLException e) { e.printStackTrace(); }
		finally{try { pr.close();} catch(SQLException ex){}}
		return null;
	}
	
	
	public static void deleteLastSessionOfChild(int childId) {
		PreparedStatement pr = null;
		String qe = "DELETE from session where Id = (select max(id) from session where childId = "+childId+");" ;                           
		
		try {
			pr = conn.prepareStatement(qe);
			pr.execute();		
			main.getSessionController().fillTable();
			idCounter = maxId();
		} catch (SQLException e) { e.printStackTrace(); }
		finally{try { pr.close();} catch(SQLException ex){}}
	}
	
	public static void deleteSessionsofSpecialist(String name)
	{
		int spId = SpecialistDatabase.selectSpecialist(name).getId();
		PreparedStatement pr = null;
		String qe = "DELETE from session WHERE specialistId = " + spId +";";
		
		
		try {
			pr = conn.prepareStatement(qe);
			pr.execute();
			
			main.getSessionController().fillTable();
			idCounter = maxId();
		} catch (SQLException e) { e.printStackTrace(); }
		finally{try { pr.close();} catch(SQLException ex){}}
		
	}
	
	public static void insertNewSession(Session s)
	{
		int id = generateId();
		s.setId(id);
		PreparedStatement pr = null;
		String values = " Values (" + id
		+ "," + s.getChildId() 
		+ "," + s.getSpecialist().getId() 
		+ "," +  s.getPartner().getId() 
		+ "," +  s.getChildDoneSessions()
		+ "," +  s.getChildMaxSessions()
		+ ",'" +  s.getSessionDateString() 
		+ "','" +  s.getExpired() 
		+"','" + s.getSpName()
		+"','" + s.getPartName()
		+ "');";
		String qe = "INSERT INTO Session (id ,childId,specialistId,partnerId,doneSessions,maxSessions,date,expired , spName , partName)"
		+ values; 
		try {
			pr = conn.prepareStatement(qe);
			pr.execute();
			undoData.push(new Bulk(s, true, Operation.INSERT));
			ChildDatabase.updateChildDoneSessions(s.getChildId(), ChildDatabase.selectChild(s.getChildId()).getDoneSessions() + 1);
			main.getSessionController().fillTable();
		} catch (SQLException e) { e.printStackTrace(); }
		finally{try { pr.close();} catch(SQLException ex){}}

	}

	public static void insertNewSessionUndo(Session s) {
		//int id = generateId();
		PreparedStatement pr = null;
		String values = " Values (" + s.getId()
		+ "," + s.getChildId() 
		+ "," + s.getSpecialist().getId() 
		+ "," +  s.getPartner().getId() 
		+ "," +  s.getChildDoneSessions()
		+ "," +  s.getChildMaxSessions()
		+ ",'" +  s.getSessionDateString() 
		+ "','" +  s.getExpired() 
		+"','" + s.getSpName()
		+"','" + s.getPartName()
		+ "');";
		String qe = "INSERT INTO Session (id ,childId,specialistId,partnerId,doneSessions,maxSessions,date,expired , spName , partName)"
		+ values; 
		
		try {
			pr = conn.prepareStatement(qe);
			pr.execute();
			ChildDatabase.updateChildDoneSessions(s.getChildId(), ChildDatabase.selectChild(s.getChildId()).getDoneSessions() + 1);
			main.getSessionController().fillTable();
			//MUtils.notification("«œŒ«· Ã·”Â", " „ «œŒ«· Ã·”… »‰Ã«Õ\n «”„ «·Õ«·Â: " + s.getCh().getName());
		} catch (SQLException e) { e.printStackTrace(); }
		finally{try { pr.close();} catch(SQLException ex){}}
		
	}
	public static void insertNewSessionWithId(Session s)
	{
		//int id = generateId();
		PreparedStatement pr = null;
		String values = " Values (" + s.getId()
		+ "," + s.getChildId() 
		+ "," + s.getSpecialist().getId() 
		+ "," +  s.getPartner().getId() 
		+ "," +  s.getChildDoneSessions()
		+ "," +  s.getChildMaxSessions()
		+ ",'" +  s.getSessionDateString() 
		+ "','" +  s.getExpired() 
		+"','" + s.getSpName()
		+"','" + s.getPartName()
		+ "');";
		String qe = "INSERT INTO Session (id ,childId,specialistId,partnerId,doneSessions,maxSessions,date,expired , spName , partName)"
		+ values; 
		
		try {
			pr = conn.prepareStatement(qe);
			pr.execute();
			main.getSessionController().fillTable();
			//MUtils.notification("«œŒ«· Ã·”Â", " „ «œŒ«· Ã·”… »‰Ã«Õ\n «”„ «·Õ«·Â: " + s.getCh().getName());
		} catch (SQLException e) { e.printStackTrace(); }
		finally{try { pr.close();} catch(SQLException ex){}}

	}

	public static ObservableList<Session> searchSessionsByChildName(String name)
	{
		PreparedStatement pr = null;
		ResultSet result = null;
		ObservableList<Session> list = FXCollections.observableArrayList();
		String qe = "select * from Session where childId in (select id from child where instr(name , '"+name+"'));";
		
		try {
			pr = conn.prepareStatement(qe);
			result = pr.executeQuery();
			while(result.next())
			{
				Session session = new Session();
				session.setId(result.getInt(1));
				session.setCh(ChildDatabase.selectChild(result.getInt(2)));
				session.setSpecialist(SpecialistDatabase.selectSpecialist(result.getInt(3)));
				session.setPartner(PartnerDatabase.selectPartner(result.getInt(4)));
				session.setChildDoneSessions(result.getInt(5));
				session.setChildMaxSessions(result.getInt(6));
				session.setSessionDate(result.getString(7));	
				session.setSpName(result.getString(9));
				session.setPartName(result.getString(10));
				list.add(session);
			}
			return list;
		} catch (SQLException e) { e.printStackTrace(); }
		return null;
	}

	public static ObservableList<SessionModel> searchSessions(String chName , String p1Name , String spName)
	{
		PreparedStatement pr = null;
		ResultSet result = null;
		ObservableList<SessionModel> list = FXCollections.observableArrayList();
		boolean chEmpty = chName.isEmpty();
		boolean p1Empty = p1Name.isEmpty();
		boolean p2Empty = spName.isEmpty();
		String qe = "";
		String qe1 = "select id from child where instr(name , '" +chName + "' ) > 0";
		String qe2 = "select id from Partner where instr(name , '" +p1Name + "' ) > 0";
		String qe3 = "select id from specialist where instr(name , '" +spName + "' ) > 0";
		if(!chEmpty && !p1Empty && !p2Empty)
			qe += "select * from session where childId in (" + qe1 
					+ ") and partnerId in (" + qe2 
					+ ") and specialistId in (" + qe3 + ");";
		else if(!chEmpty && !p1Empty && p2Empty)
			qe += "select * from session where childId in (" + qe1 
					+ ") and partnerId in (" + qe2 +");";	
		else if(!chEmpty && p1Empty && p2Empty)
			qe += "select * from session where childId in (" + qe1 +");";
		else if(chEmpty && !p1Empty && !p2Empty)
			qe += "select * from session where  partnerId in (" + qe2 
			+ ") and specialistId in (" + qe3 + ");";		
		else if(chEmpty && p1Empty && !p2Empty)
			qe += "select * from session where specialistId in (" + qe3 + ");";	
		else if(chEmpty && !p1Empty && p2Empty)
			qe += "select * from session where  partnerId in (" + qe2 +");";
		else if(!chEmpty && p1Empty && !p2Empty)
			qe += "select * from session where childId in (" + qe1 
			+ ") and specialistId in (" + qe3 + ");";	
		else
			qe = "select * from session;";
		
		try {
			pr = conn.prepareStatement(qe);
			result = pr.executeQuery();
			while(result.next())
			{
				Session session = new Session();
				session.setId(result.getInt(1));
				session.setCh(ChildDatabase.selectChild(result.getInt(2)));
				session.setSpecialist(SpecialistDatabase.selectSpecialist(result.getInt(3)));
				session.setPartner(PartnerDatabase.selectPartner(result.getInt(4)));
				session.setChildDoneSessions(result.getInt(5));
				session.setChildMaxSessions(result.getInt(6));
				session.setSessionDate(result.getString(7));	
				session.setSpName(result.getString(9));
				session.setPartName(result.getString(10));
				SessionModel model = new SessionModel(session);
				list.add(model);
			}
			return list;
		} catch (SQLException e) { e.printStackTrace(); }
		return null;
	}

	public static ObservableList<Session> searchSessionsBySpecialistName(String name)
	{
		if(name.equals(""))
			return selectAllSession();
			
		PreparedStatement pr = null;
		ResultSet result = null;
		ObservableList<Session> list = FXCollections.observableArrayList();
		String qe = "select * from Session where specialistId in (select id from specialist where instr(name , '"+name+"'));";
		
		try {
			pr = conn.prepareStatement(qe);
			result = pr.executeQuery();
			while(result.next())
			{
				Session session = new Session();
				session.setId(result.getInt(1));
				session.setCh(ChildDatabase.selectChild(result.getInt(2)));
				session.setSpecialist(SpecialistDatabase.selectSpecialist(result.getInt(3)));
				session.setPartner(PartnerDatabase.selectPartner(result.getInt(4)));
				session.setChildDoneSessions(result.getInt(5));
				session.setChildMaxSessions(result.getInt(6));
				session.setSessionDate(result.getString(7));
				session.setSpName(result.getString(9));
				session.setPartName(result.getString(10));
				list.add(session);
			}
			return list;
		} catch (SQLException e) { e.printStackTrace(); }
		return null;
	}
	
	public static ObservableList<Session> searchSessionsByChAndSp(String chName , String spName)
	{
		String qe = "select * from session where childId in "
				+ "(select id from child where instr(name , '"+chName+"') > 0 ) "
				+ "and specialistid in (select id from specialist where instr(name , '"+spName+"') > 0 )  ;" ;
		
		PreparedStatement pr = null;
		ResultSet result = null;
		ObservableList<Session> list = FXCollections.observableArrayList();
		try {
			pr = conn.prepareStatement(qe);
			result = pr.executeQuery();
			while(result.next())
			{
				Session session = new Session();
				session.setId(result.getInt(1));
				session.setCh(ChildDatabase.selectChild(result.getInt(2)));
				session.setSpecialist(SpecialistDatabase.selectSpecialist(result.getInt(3)));
				session.setPartner(PartnerDatabase.selectPartner(result.getInt(4)));
				session.setChildDoneSessions(result.getInt(5));
				session.setChildMaxSessions(result.getInt(6));
				session.setSessionDate(result.getString(7));
				session.setSpName(result.getString(9));
				session.setPartName(result.getString(10));
				list.add(session);
			}
			return list;
		} catch (SQLException e) { e.printStackTrace(); }
		return null;
		
		
	}

	public static void updateSession(Session s)
	{
		PreparedStatement pr = null;
		String qe = "UPDATE session SET childId = "+ s.getChildId() + ", specialistId = " 
		+ s.getSpecialist().getId() + ", partnerId = " + s.getPartner().getId()
		+ ", doneSessions = " + s.getChildDoneSessions() + ", maxSessions = " + s.getChildMaxSessions()
		+ ", date = '" + s.getSessionDateString() + "' , expired = '" + s.getExpiredString()
		+ "', spName = '" + s.getSpName() + "', partName = '" + s.getPartName()
		+ "' where id = "+ s.getId();
		
		try {
			pr = conn.prepareStatement(qe);
			pr.execute();
			

			main.getSessionController().fillTable();
		} catch (SQLException e) { e.printStackTrace(); }
		finally{try { pr.close();} catch(SQLException ex){}}
	}
	
	public static Session selectSession(int id)
	{
		PreparedStatement pr = null;
		ResultSet result = null;
		Session session = new Session();
		String qe = "select * from Session where id = " + id + ";";
		try {
			pr = conn.prepareStatement(qe);
			result = pr.executeQuery();
			while(result.next())
			{
				session.setId(result.getInt(1));
				session.setCh(ChildDatabase.selectChild(result.getInt(2)));
				session.setSpecialist(SpecialistDatabase.selectSpecialist(result.getInt(3)));
				session.setPartner(PartnerDatabase.selectPartner(result.getInt(4)));
				session.setChildDoneSessions(result.getInt(5));
				session.setChildMaxSessions(result.getInt(6));
				session.setSessionDate(result.getString(7));	
				session.setSpName(result.getString(9));
				session.setPartName(result.getString(10));
			}
			return session;
		} catch (SQLException e) { e.printStackTrace(); }
		return null;
	}

	public static ArrayList<Session> selectSessionByChildId(int id)
	{
		PreparedStatement pr = null;
		ResultSet result = null;
		ArrayList<Session> list = new ArrayList<>();
		String qe = "select * from Session where childId = " + id + ";";
		try {
			pr = conn.prepareStatement(qe);
			result = pr.executeQuery();
			while(result.next())
			{
				Session session = new Session();
				session.setId(result.getInt(1));
				session.setCh(ChildDatabase.selectChild(result.getInt(2)));
				session.setSpecialist(SpecialistDatabase.selectSpecialist(result.getInt(3)));
				session.setPartner(PartnerDatabase.selectPartner(result.getInt(4)));
				session.setChildDoneSessions(result.getInt(5));
				session.setChildMaxSessions(result.getInt(6));
				session.setSessionDate(result.getString(7));	
				session.setSpName(result.getString(9));
				session.setPartName(result.getString(10));
				list.add(session);
			}
			return list;
		} catch (SQLException e) { e.printStackTrace(); }
		return null;
	}

	public static ObservableList<Session> selectSessionBySpecialistId(int id)
	{
		PreparedStatement pr = null;
		ResultSet result = null;
		ObservableList<Session> list = FXCollections.observableArrayList();
		String qe = "select * from Session where specialistId = " + id + ";";
		try {
			pr = conn.prepareStatement(qe);
			result = pr.executeQuery();
			while(result.next())
			{
				Session session = new Session();
				session.setId(result.getInt(1));
				session.setCh(ChildDatabase.selectChild(result.getInt(2)));
				session.setSpecialist(SpecialistDatabase.selectSpecialist(result.getInt(3)));
				session.setPartner(PartnerDatabase.selectPartner(result.getInt(4)));
				session.setChildDoneSessions(result.getInt(5));
				session.setChildMaxSessions(result.getInt(6));
				session.setSessionDate(result.getString(7));	
				session.setSpName(result.getString(9));
				session.setPartName(result.getString(10));
				list.add(session);
			}
			return list;
		} catch (SQLException e) { e.printStackTrace(); }
		return null;
	}

	public static ObservableList<Session> selectSessionBySpecialistName(String name)
	{
		PreparedStatement pr = null;
		ResultSet result = null;
		ObservableList<Session> list = FXCollections.observableArrayList();
		String qe = "select * from Session where spName = '" + name + "';";
		try {
			pr = conn.prepareStatement(qe);
			result = pr.executeQuery();
			while(result.next())
			{
				Session session = new Session();
				session.setId(result.getInt(1));
				session.setCh(ChildDatabase.selectChild(result.getInt(2)));
				session.setSpecialist(SpecialistDatabase.selectSpecialist(result.getInt(3)));
				session.setPartner(PartnerDatabase.selectPartner(result.getInt(4)));
				session.setChildDoneSessions(result.getInt(5));
				session.setChildMaxSessions(result.getInt(6));
				session.setSessionDate(result.getString(7));	
				session.setSpName(result.getString(9));
				session.setPartName(result.getString(10));
				list.add(session);
			}
			return list;
		} catch (SQLException e) { e.printStackTrace(); }
		return null;
	}

	

	

}


























