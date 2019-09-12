package application.database;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import application.model.Bill;
import application.model.Child;
import application.model.ChildModel;
import application.model.ChildPhoto;
import application.model.Partner;
import application.model.Session;
import application.undoRedoSystem.Bulk;
import application.undoRedoSystem.Operation;
import application.utils.MUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

public class ChildDatabase extends Database {
	public static int step = 0;
	public static int generateStep(){
		return ++step;
	}
	public static int getStep(){
		return step;
	}
	public static void insertNewChild(Child ch)
	{
		if(checkNameIfExist(ch.getName()))
			return;
		PreparedStatement pr = null;
		String values = "('" + ch.getName() + "' , '"
        + ch.getBirthDate() + "', "
		+ "'" +  ch.getStatus() + "' ,"
		+ ch.getGenderInt() + " ,"
		+ ch.getOneSessionPrice() + ", "
		+ ch.getPaidUpMoney()+ ", "
		+ ch.getDoneSessions()+ ", "
		+ ch.getP1().getId()+ ", "
		+ ch.getP2().getId();
		String qe = "INSERT INTO child (name,birth,status,gender,oneSessionPrice,paidUpMoney"
				+ ",doneSessions,partner1Id,partner2Id , image)"
				+ " Values " + values + ",? );";
		try {
			pr = conn.prepareStatement(qe);
			FileInputStream input = new FileInputStream(ch.getPhoto());			
			pr = conn.prepareStatement(qe);
			pr.setBinaryStream(1, input, (int)ch.getPhoto().length());
			pr.execute();
			ch.setId(selectChild(ch.getName()).getId());
			undoData.push(new Bulk(ch, true, Operation.INSERT));
			if(ch.isPart1New()){
				undoData.push(new Bulk(ch.getP1(), false, Operation.INSERT));

			}
			if(ch.isPart1New()){
				undoData.push(new Bulk(ch.getP2(), false, Operation.INSERT));

			}
			main.getChildController().fillTable();
		} catch (Exception e) { e.printStackTrace(); }
		finally{try { pr.close();} catch(SQLException ex){
			
		}}

	}
	
	public static void insertNewChildUndo(Child ch) {
		if(checkNameIfExist(ch.getName()))
			return;
		PreparedStatement pr = null;
		String values = "(" + ch.getId() + ",'" + ch.getName() + "' , '"
        + ch.getBirthDate() + "', "
		+ "'" +  ch.getStatus() + "' ,"
		+ ch.getGenderInt() + " ,"
		+ ch.getOneSessionPrice() + ", "
		+ ch.getPaidUpMoney()+ ", "
		+ ch.getDoneSessions()+ ", "
		+ ch.getP1().getId()+ ", "
		+ ch.getP2().getId();
		String qe = "INSERT INTO child (id,name,birth,status,gender,oneSessionPrice,paidUpMoney"
				+ ",doneSessions,partner1Id,partner2Id , image)"
				+ " Values " + values + ",? );";
		try {
			pr = conn.prepareStatement(qe);
			FileInputStream input = new FileInputStream(ch.getPhoto());			
			pr = conn.prepareStatement(qe);
			pr.setBinaryStream(1, input, (int)ch.getPhoto().length());
			pr.execute();
			main.getChildController().fillTable();
		} catch (Exception e) { e.printStackTrace(); }
		finally{try { pr.close();} catch(SQLException ex){
			
		}}
		
	}
		
	public static ObservableList<Child> selectAllChild() {
		PreparedStatement pr = null;
		ResultSet result = null;
		ObservableList<Child> list = FXCollections.observableArrayList();
		String qe = "select * from child;";
		try {
			pr = conn.prepareStatement(qe);
			result = pr.executeQuery();
			while(result.next())
			{
				Child ch = new Child();
				ch.setId(result.getInt(1));
				ch.setName(result.getString(2));
				ch.setBirth(result.getString(3));
				ch.setStatus(result.getString(4));
				ch.setGenderInt(result.getInt(5));
				ch.setOneSessionPrice(result.getDouble(6));
				ch.setPaidUpMoney(result.getDouble(7));
				ch.setDoneSessions(result.getInt(8));
				ch.setP1(PartnerDatabase.selectPartner(result.getInt(9)));
				ch.setP2(PartnerDatabase.selectPartner(result.getInt(10)));
				ch.setPhoto(writeToFile(result.getBinaryStream(11)));
				list.add(ch);
			}
			
			
			return list;
		} catch (SQLException e) { e.printStackTrace(); }
		finally{try { pr.close(); result.close(); } catch(SQLException ex){}}
		return null;
		
	}

	public static File writeToFile(InputStream in) {
		OutputStream out = null;
		File file = new File(MUtils.generateNewPhotoName());
		try{
			out = new FileOutputStream(file);
			byte[] buffer = new byte[1024];
			int length = 0;
			while ((length = in.read(buffer)) != -1) {
				out.write(buffer , 0 , length);
			}
			return file;
		}catch(Exception ex){
			ex.printStackTrace();
		}
		finally{ try { in.close();  out.close(); } catch (IOException e) {e.printStackTrace();}}
		return null;
	}

	public static ObservableList<String> selectAllChildNames()
	{
		PreparedStatement pr = null;
		ResultSet result = null;
		ObservableList<String> list = FXCollections.observableArrayList();
		String qe = "select name from child;";
		try {
			pr = conn.prepareStatement(qe);
			result = pr.executeQuery();
			while(result.next())
			{
				list.add(result.getString("name"));
			}
			
			return list;
		} catch (SQLException e) { e.printStackTrace(); }
		finally{try { pr.close(); result.close(); } catch(SQLException ex){}}
		return null;
	}

	public static ObservableList<Integer> selectAllChildId()
	{
		PreparedStatement pr = null;
		ResultSet result = null;
		ObservableList<Integer> list = FXCollections.observableArrayList();
		String qe = "select id from child;";
		try {
			pr = conn.prepareStatement(qe);
			result = pr.executeQuery();
			while(result.next())
			{
				list.add(result.getInt("id"));
			}
			
			return list;
		} catch (SQLException e) { e.printStackTrace(); }
		finally{try { pr.close(); result.close(); } catch(SQLException ex){}}
		return null;
	}

	public static String selectChildName(int id)
	{
		String name = "";
		PreparedStatement pr = null;
		ResultSet result = null;
		String qe = "select name from child where id = " + id + ";";
		try {
			pr = conn.prepareStatement(qe);
			result = pr.executeQuery();
			if(result.next())
			{
				name = result.getString(2);
			}
			
			return name;
		} catch (SQLException e) { e.printStackTrace(); }
		finally{try { pr.close(); result.close(); } catch(SQLException ex){}}
		return null;
	}

	public static ObservableList<ChildModel> selectAllChildModel()
	{
		PreparedStatement pr = null;
		ResultSet result = null;
		ObservableList<ChildModel> list = FXCollections.observableArrayList();
		String qe = "select * from child;";
		try {
			pr = conn.prepareStatement(qe);
			result = pr.executeQuery();
			while(result.next())
			{
				Child ch = new Child();
				ch.setId(result.getInt(1));
				ch.setName(result.getString(2));
				ch.setBirth(result.getString(3));
				ch.setStatus(result.getString(4));
				ch.setGenderInt(result.getInt(5));
				ch.setOneSessionPrice(result.getDouble(6));
				ch.setPaidUpMoney(result.getDouble(7));
				ch.setDoneSessions(result.getInt(8));
				ch.setP1(PartnerDatabase.selectPartner(result.getInt(9)));
				ch.setP2(PartnerDatabase.selectPartner(result.getInt(10)));
				ch.setPhoto(writeToFile(result.getBinaryStream(11)));
				ChildModel model = new ChildModel(ch);
				list.add(model);
			}
			return list;
		} catch (SQLException e) { e.printStackTrace(); }
		finally{try { pr.close(); result.close(); } catch(SQLException ex){}}
		return null;
	}
	
	public static Child selectChild(int id)
	{
		PreparedStatement pr = null;
		ResultSet result = null;
		Child ch = new Child();
		String qe = "select * from child where id = " +id + ";" ;
		try {
			pr = conn.prepareStatement(qe);
			result = pr.executeQuery();
			while(result.next())
			{
				
				ch.setId(result.getInt(1));
				ch.setName(result.getString(2));
				ch.setBirth(result.getString(3));
				ch.setStatus(result.getString(4));
				ch.setGenderInt(result.getInt(5));
				ch.setOneSessionPrice(result.getDouble(6));
				ch.setPaidUpMoney(result.getDouble(7));
				ch.setDoneSessions(result.getInt(8));
				ch.setP1(PartnerDatabase.selectPartner(result.getInt(9)));
				ch.setP2(PartnerDatabase.selectPartner(result.getInt(10)));
				ch.setPhoto(writeToFile(result.getBinaryStream(11)));

			}
			return ch;
		} catch (SQLException e) { e.printStackTrace(); }
		finally{try { pr.close(); result.close(); } catch(SQLException ex){}}
		return null;
	}
	
	public static Child selectChild(String name)
	{
		Child ch = new Child();
		PreparedStatement pr = null;
		ResultSet result = null;
		String qe = "select * from child where name = '" + name + "';";
		try {
			pr = conn.prepareStatement(qe);
			result = pr.executeQuery();
			if(result.next())
			{
				ch.setId(result.getInt(1));
				ch.setName(result.getString(2));
				ch.setBirth(result.getString(3));
				ch.setStatus(result.getString(4));
				ch.setGenderInt(result.getInt(5));
				ch.setOneSessionPrice(result.getDouble(6));
				ch.setPaidUpMoney(result.getDouble(7));
				ch.setDoneSessions(result.getInt(8));
				ch.setP1(PartnerDatabase.selectPartner(result.getInt(9)));
				ch.setP2(PartnerDatabase.selectPartner(result.getInt(10)));
				ch.setPhoto(writeToFile(result.getBinaryStream(11)));
			}
			return ch;
		} catch (SQLException e) { 
			e.printStackTrace(); }
		finally{try { pr.close(); result.close(); } catch(SQLException ex){}}
		return null;
	}
	
	public static Child selectChildExcept(String exceptName , String name)
	{
		Child ch = new Child();
		PreparedStatement pr = null;
		ResultSet result = null;
		String qe = "select * from child where name = '" + name 
				+ "' and name != '"+exceptName+"';";
		try {
			pr = conn.prepareStatement(qe);
			result = pr.executeQuery();
			if(result.next())
			{
				ch.setId(result.getInt(1));
				ch.setName(result.getString(2));
				ch.setBirth(result.getString(3));
				ch.setStatus(result.getString(4));
				ch.setGenderInt(result.getInt(5));
				ch.setOneSessionPrice(result.getDouble(6));
				ch.setPaidUpMoney(result.getDouble(7));
				ch.setDoneSessions(result.getInt(8));
				ch.setP1(PartnerDatabase.selectPartner(result.getInt(9)));
				ch.setP2(PartnerDatabase.selectPartner(result.getInt(10)));
				ch.setPhoto(writeToFile(result.getBinaryStream(11)));
			}
			return ch;
		} catch (SQLException e) { 
			e.printStackTrace(); }
		finally{try { pr.close(); result.close(); } catch(SQLException ex){}}
		return null;
	}
	
	public static ArrayList<Child> selectChildOfPartner(int id) {
		PreparedStatement pr = null;
		ResultSet result = null;
		ArrayList<Child> list = new ArrayList<>();
		String qe = "select * from child where partner1Id = " + id + " or partner2Id = " + id + ";";
		try {
			pr = conn.prepareStatement(qe);
			result = pr.executeQuery();
			while(result.next())
			{
				Child ch = new Child();
				ch.setId(result.getInt(1));
				ch.setName(result.getString(2));
				ch.setBirth(result.getString(3));
				ch.setStatus(result.getString(4));
				ch.setGenderInt(result.getInt(5));
				ch.setOneSessionPrice(result.getDouble(6));
				ch.setPaidUpMoney(result.getDouble(7));
				ch.setDoneSessions(result.getInt(8));
				ch.setP1(PartnerDatabase.selectPartner(result.getInt(9)));
				ch.setP2(PartnerDatabase.selectPartner(result.getInt(10)));
				ch.setPhoto(writeToFile(result.getBinaryStream(11)));
				list.add(ch);
			}
			return list;
		} catch (SQLException e) { e.printStackTrace(); }
		finally{try { pr.close(); result.close(); } catch(SQLException ex){}}
		return null;
	}

	public static boolean checkNameIfExist(String name)
	{
		Child ch = ChildDatabase.selectChild(name);
		if(ch.getName().equals(name))
			return true;
		else return false;
	}
	public static boolean checkNameIfExistEdit( String exceptedName , String name)
	{
		Child ch = ChildDatabase.selectChildExcept(exceptedName , name);
		if(ch.getName().equals(name))
			return true;
		else return false;
	}
	
	public static ObservableList<ChildModel> SearchChild(String name) {
		if(name.equals(""))
			return selectAllChildModel();
		PreparedStatement pr = null;
		ResultSet result = null;
		ObservableList<ChildModel> list = FXCollections.observableArrayList();
		String qe = "select * from child where instr(name , '" +name + "' ) > 0 ;";
		try {
			pr = conn.prepareStatement(qe);
			result = pr.executeQuery();
			while(result.next())
			{
				Child ch = new Child();
				ch.setId(result.getInt(1));
				ch.setName(result.getString(2));
				ch.setBirth(result.getString(3));
				ch.setStatus(result.getString(4));
				ch.setGenderInt(result.getInt(5));
				ch.setOneSessionPrice(result.getDouble(6));
				ch.setPaidUpMoney(result.getDouble(7));
				ch.setDoneSessions(result.getInt(8));
				ch.setP1(PartnerDatabase.selectPartner(result.getInt(9)));
				ch.setP2(PartnerDatabase.selectPartner(result.getInt(10)));
				ch.setPhoto(writeToFile(result.getBinaryStream(11)));
				ChildModel model = new ChildModel(ch);
				list.add(model);
			}
			return list;
		} catch (SQLException e) { e.printStackTrace(); }
		finally{try { pr.close(); result.close(); } catch(SQLException ex){}}
		return null;
		
	}

	public static ObservableList<ChildModel> search(String chName , String p1Name , String p2Name)
	{
		ObservableList<ChildModel> list = FXCollections.observableArrayList();
		boolean chEmpty = chName.isEmpty();
		boolean p1Empty = p1Name.isEmpty();
		boolean p2Empty = p2Name.isEmpty();
		PreparedStatement pr = null;
		ResultSet result = null;
		String qe = "";
		String qe1 = "select * from child where instr(name , '" +chName + "' ) > 0";
		String qe2 = "select id from Partner where instr(name , '" +p1Name + "' ) > 0";
		String qe3 = "select id from Partner where instr(name , '" +p2Name + "' ) > 0";
		if(!chEmpty && !p1Empty && !p2Empty)
			qe += qe1 + " and partner1Id in (" + qe2 + ") and partner2Id in (" + qe3 + ");"	;		
		else if(!chEmpty && !p1Empty && p2Empty)
			qe += qe1 + " and partner1Id in (" + qe2 + ");"	;		
		else if(!chEmpty && p1Empty && p2Empty)
			qe += qe1 + ";";
		else if(chEmpty && !p1Empty && !p2Empty)
			qe += "select * from child where partner1Id in (" + qe2 + ") and partner2Id in (" + qe3 + ");"	;
		else if(chEmpty && p1Empty && !p2Empty)
			qe += "select * from child where partner2Id in (" + qe3 + ");";
		else if(chEmpty && !p1Empty && p2Empty)
			qe += "select * from child where partner1Id in (" + qe2 + ");";
		else if(!chEmpty && p1Empty && !p2Empty)
			qe += qe1 + " and partner2Id in (" + qe3 + ");"	;		
		else
			qe = "select * from child;";
		try {
			
			pr = conn.prepareStatement(qe);
			result = pr.executeQuery();
			while(result.next())
			{
				Child ch = new Child();
				ch.setId(result.getInt(1));
				ch.setName(result.getString(2));
				ch.setBirth(result.getString(3));
				ch.setStatus(result.getString(4));
				ch.setGenderInt(result.getInt(5));
				ch.setOneSessionPrice(result.getDouble(6));
				ch.setPaidUpMoney(result.getDouble(7));
				ch.setDoneSessions(result.getInt(8));
				ch.setP1(PartnerDatabase.selectPartner(result.getInt(9)));
				ch.setP2(PartnerDatabase.selectPartner(result.getInt(10)));
				ch.setPhoto(writeToFile(result.getBinaryStream(11)));
				ChildModel model = new ChildModel(ch);
				list.add(model);
			}
			return list;
		} catch (SQLException e) { e.printStackTrace(); }
		finally{try { pr.close(); result.close(); } catch(SQLException ex){}}
		return null;
	}
	
	public static ObservableList<String> SearchChildNames(String name) {
		if(name.equals(""))
			return selectAllChildNames();
		PreparedStatement pr = null;
		ResultSet result = null;
		ObservableList<String> list = FXCollections.observableArrayList();
		String qe = "select * from child where instr(name , '" +name + "' ) > 0 ;";
		try {
			pr = conn.prepareStatement(qe);
			result = pr.executeQuery();
			while(result.next())
			{
				list.add(result.getString("name"));
			}
			return list;
		} catch (SQLException e) { e.printStackTrace(); }
		finally{try { pr.close(); result.close(); } catch(SQLException ex){}}
		return null;
		
	}
	
	public static ObservableList<Integer> SearchChildId(String name) {
		if(name.equals(""))
			return selectAllChildId();
		PreparedStatement pr = null;
		ResultSet result = null;
		ObservableList<Integer> list = FXCollections.observableArrayList();
		String qe = "select id from child where instr(name , '" +name + "' ) > 0 ;";
		try {
			pr = conn.prepareStatement(qe);
			result = pr.executeQuery();
			while(result.next())
			{
				list.add(result.getInt("id"));
			}
			return list;
		} catch (SQLException e) { e.printStackTrace(); }
		finally{try { pr.close(); result.close(); } catch(SQLException ex){}}
		return null;
		
	}
	
	public static void UpdateChild(Child ch)
	{
		
		Child old = ChildDatabase.selectChild(ch.getId());
		PreparedStatement pr = null;
		String qe = "UPDATE child SET name = '"+ ch.getName() + "',birth =  '" + ch.getBirthDate()
		+ "' ,status = '" + ch.getStatus() + "' ,gender = " + ch.getGenderInt()
		+ ",oneSessionPrice = " + ch.getOneSessionPrice()
		+ " ,paidUpMoney = " + ch.getPaidUpMoney() + " ,doneSessions = " + ch.getDoneSessions()
		+ ",partner1Id = " + ch.getP1().getId() + ",partner2Id = " + ch.getP2().getId()
		+ ", image = ? "
		+ " WHERE id = " + ch.getId() + ";";
		
		
		try {
			pr = conn.prepareStatement(qe);
			FileInputStream input = new FileInputStream(ch.getPhoto());			
			pr.setBinaryStream(1, input, (int)ch.getPhoto().length());
			pr.execute();
			undoData.push(new Bulk(old, true, Operation.UPDATE));
			redoData.pushCurrent(new Bulk(ch, true, Operation.UPDATE));
			main.refreshTables();
		} catch (Exception e) { e.printStackTrace(); }
		finally{try { pr.close();} catch(SQLException ex){}}
	}
	
	public static void UpdateChildUndo(Child ch)
	{
		PreparedStatement pr = null;
		String qe = "UPDATE child SET name = '"+ ch.getName() + "',birth =  '" + ch.getBirthDate()
		+ "' ,status = '" + ch.getStatus() + "' ,gender = " + ch.getGenderInt()
		+ ",oneSessionPrice = " + ch.getOneSessionPrice()
		+ " ,paidUpMoney = " + ch.getPaidUpMoney() + " ,doneSessions = " + ch.getDoneSessions()
		+ ",partner1Id = " + ch.getP1().getId() + ",partner2Id = " + ch.getP2().getId()
		+ ", image = ? "
		+ " WHERE id = " + ch.getId() + ";";
		try {
			pr = conn.prepareStatement(qe);
			FileInputStream input = new FileInputStream(ch.getPhoto());			
			pr.setBinaryStream(1, input, (int)ch.getPhoto().length());
			pr.execute();
			main.refreshTables();
		} catch (Exception e) { e.printStackTrace(); }
		finally{try { pr.close();} catch(SQLException ex){}}
	}
	
	public static void UpdateChildPhoto(Child ch)
	{
		PreparedStatement pr = null;
		String qe = "UPDATE child SET image = ?"
		+ " WHERE id = " + ch.getId() + ";";
		try {
			pr = conn.prepareStatement(qe);
			FileInputStream input = new FileInputStream(ch.getPhoto());			
			pr.setBinaryStream(1, input, (int)ch.getPhoto().length());
			pr.execute();
			main.refreshTables();
		} catch (Exception e) { e.printStackTrace(); }
		finally{try { pr.close();} catch(SQLException ex){}}
	}

	public static Image readPhoto(int ChildId)
	{
		PreparedStatement pr = null;
		ResultSet result = null;
		try
		{
			String qe = "select image from child where id = " + ChildId + ";";
			pr = conn.prepareStatement(qe);
			result = pr.executeQuery();
			String fileName = MUtils.generateNewPhotoName();
			File file = new File(fileName);
			InputStream stream = result.getBinaryStream("image");
			OutputStream out = new FileOutputStream(file);
			byte [] contnent = new byte[1024];
			int size = 0;
			while((size = stream.read(contnent)) != -1)
			{
				out.write(contnent, 0 , size);
			}
			stream.close();
			out.close();
			
			return new Image("file:" + fileName);
		} catch(Exception ex) {
			MUtils.showErrorMessage("Œÿ√   Õ„Ì· «·’Ê—Â", "Œÿ√ ›Ï  Õ„Ì· «·’Ê—Â „‰ ﬁ«⁄œÂ «·»Ì«‰« ");
		}
		finally{try {pr.close();result.close();} catch (SQLException e) { e.printStackTrace(); }}
		return null;
	}
	
	public static ChildPhoto readPhotoAsFile(int ChildId)
	{
		 PreparedStatement pr = null;
		 ResultSet result = null;
		try
		{
			String qe = "select image from child where id = " + ChildId + ";";
			pr = conn.prepareStatement(qe);
			result = pr.executeQuery();
			InputStream stream = result.getBinaryStream("image");
			String fileName = MUtils.generateNewPhotoName();
			File file = new File(fileName);
			OutputStream out = new FileOutputStream(file);
			byte [] contnent = new byte[1024];
			int size = 0;
			while((size = stream.read(contnent)) != -1)
			{
				out.write(contnent, 0 , size);
			}
			stream.close();
			out.close();
			return new ChildPhoto(ChildId, file);
		} catch(Exception ex) {
			MUtils.showErrorMessage("Œÿ√   Õ„Ì· «·’Ê—Â", "Œÿ√ ›Ï  Õ„Ì· «·’Ê—Â „‰ ﬁ«⁄œÂ «·»Ì«‰« ");
		}
		finally{
			
			try {
				pr.close();
				result.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public static void UpdateChildPartner(Child ch  , Partner newPart, boolean basic)
	{
		PreparedStatement pr = null;
		String qe = "";
		if(basic){
			qe = "UPDATE child SET partner1Id = "+ newPart.getId()
					+" where id = "+ch.getId()+ ";";
		}
		else {
			qe = "UPDATE child SET partner2Id = "+ newPart.getId()
					+ " where id = " + ch.getId() + ";";
		}
		
		
		try {
			pr = conn.prepareStatement(qe);
			pr.execute();
			main.getChildController().fillTable();
		} catch (SQLException e) { e.printStackTrace(); }
		finally{try { pr.close();} catch(SQLException ex){}}
	}
	
	public static void UpdateChildPartner(Child ch)
	{
		PreparedStatement pr = null;
			String qe = "UPDATE child SET partner1Id = "+ ch.getP1().getId() 
					+ " , partner2Id = " + ch.getP2().getId()+" where id = " + ch.getId() + ";";
		try {
			pr = conn.prepareStatement(qe);
			pr.execute();
			main.getChildController().fillTable();
		} catch (SQLException e) { e.printStackTrace(); }
		finally{try { pr.close();} catch(SQLException ex){}}
	}
	
	public static void UpdateChildPartnerUndo(Child ch)
	{
		PreparedStatement pr = null;
			String qe = "UPDATE child SET partner1Id = "+ ch.getP1().getId() 
					+ " , partner2Id = " + ch.getP2().getId()+" where id = " + ch.getId() + ";";
		try {
			pr = conn.prepareStatement(qe);
			pr.execute();
		} catch (SQLException e) { e.printStackTrace(); }
		finally{try { pr.close();} catch(SQLException ex){}}
	}
	
	public static void updateChildDoneSessions(int id , int doneSessions)
	{		
		PreparedStatement pr = null;
		String qe = "UPDATE child SET doneSessions = "+ doneSessions+ " where id = "+ id +";";
		
		try {
			pr = conn.prepareStatement(qe);
			pr.execute();
			main.getChildController().fillTable();
		} catch (SQLException e) { e.printStackTrace(); }
		finally{try { pr.close();} catch(SQLException ex){}}
	}
	
	// related to bill 
	public static void updateChildPaidUpMoney(Bill bill)
	{
		Child ch = bill.getChild();
		ch.setPaidUpMoneyPlus(bill.getNewPayment());
		ch.setOneSessionPrice(bill.getNewOneSessionPrice());
		PreparedStatement pr = null;
		String qe = "UPDATE child SET paidUpMoney = "+ ch.getPaidUpMoney()
				+ " , doneSessions = " + ch.getDoneSessions()
				+ " , oneSessionPrice = " + ch.getOneSessionPrice()
				+ " where id = "+ ch.getId()+";";
		
		try {
			pr = conn.prepareStatement(qe);
			pr.execute();
			main.getChildController().fillTable();		
		} catch (SQLException e) { e.printStackTrace(); }
		finally{try { pr.close();} catch(SQLException ex){}}
	}
	
	// related to bill
	public static void updateChildPaidUpMoneyMinus(Bill bill)
	{
		Child ch = bill.getChild();
		ch.setPaidUpMoney(bill.getOldPaidUpMoney());
		ch.setOneSessionPrice(bill.getOldOneSessionPrice());
		ch.setDoneSessions(bill.getOldDoneSessions());
		PreparedStatement pr = null;
		String qe = "UPDATE child SET paidUpMoney = "+ ch.getPaidUpMoney()
		+ " , doneSessions = " + ch.getDoneSessions()
		+ " , oneSessionPrice = " + ch.getOneSessionPrice()
		+ " where id = "+ ch.getId()+";";
		
		try {
			pr = conn.prepareStatement(qe);
			pr.execute();
			main.getChildController().fillTable();
		} catch (SQLException e) { e.printStackTrace(); }
		finally{try { pr.close();} catch(SQLException ex){}}
	}
	
	public static void deleteChild(int id)
	{
		Child ch = ChildDatabase.selectChild(id);
		ArrayList<Session> ses = SessionDatabase.deleteSessionsOfChild(id);
		ArrayList<Bill> bills = BillDatabase.deleteBillOfChildId(id);
		PreparedStatement pr = null;
		String qe = "DELETE from child WHERE id = " + id+";";
		try {			
			pr = conn.prepareStatement(qe);
			pr.execute();
			undoData.push(new Bulk(ch, true, Operation.DELETE));
			undoData.push(new Bulk(ses, false, Operation.DELETE));
			undoData.push(new Bulk(bills, false, Operation.DELETE));

			main.getChildController().fillTable();
		} catch (SQLException e) { e.printStackTrace(); }
		finally{try { pr.close();} catch(SQLException ex){}}
	}
	
	public static void deleteChildFresh(int id)
	{
		PreparedStatement pr = null;
		String qe = "DELETE from child WHERE id = " + id+";";
		try {			
			pr = conn.prepareStatement(qe);
			pr.execute();
			main.getChildController().fillTable();
		} catch (SQLException e) { e.printStackTrace(); }
		finally{try { pr.close();} catch(SQLException ex){}}
	}
	
	public static void deleteChildMulti(ArrayList<Child> childs){
		for(Child ch : childs){
			deleteChild(ch.getId());
		}
		main.getChildController().fillTable();
	}
	
}
