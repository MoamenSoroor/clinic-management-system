package application.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import application.model.Bill;
import application.model.BillModel;
import application.model.Child;
import application.model.Partner;
import application.undoRedoSystem.Bulk;
import application.undoRedoSystem.Operation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class BillDatabase extends Database {
	private BillDatabase(){}

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
		String qe = "select max(id) from bill";
		try {
			pr = conn.prepareStatement(qe);
			result = pr.executeQuery();
			max = result.getInt(1);
			return max;
		} catch (SQLException e) { e.printStackTrace(); }
		return 0;
	}
	
	public static boolean isMaxBillForChild(int billId , int childId){
		int maxId = 0;
		PreparedStatement pr = null;
		ResultSet result = null;
		String qe = "select max(id) from bill where childId = " + childId + ";";
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
	

	public static ObservableList<Bill> selectAllBill()
	{
		
		PreparedStatement pr = null;
		ResultSet result = null;
		ObservableList<Bill> list = FXCollections.observableArrayList();
		String qe = "select * from Bill";
		try {
			pr = conn.prepareStatement(qe);
			result = pr.executeQuery();
			while(result.next())
			{
				Bill bill = new Bill();
				Child ch = ChildDatabase.selectChild(result.getInt(2));
				Partner part = PartnerDatabase.selectPartner(result.getInt(3));
				bill.setId(result.getInt(1));
				ch.setPaidUpMoney(result.getDouble(5));
				ch.setOneSessionPrice(result.getDouble(6));
				ch.setDoneSessions(result.getInt(7));
				bill.setNewPayment(result.getDouble(4));
				bill.setPayDate(result.getString(8));
				bill.setDuration(result.getInt(9));
				bill.setChild(ch);
				bill.setPartner(part);
				bill.setPartName(result.getString(10));
				bill.setNewOneSessionPrice(result.getDouble(11));
				list.add(bill);
			}
			return list;
		} catch (SQLException e) { e.printStackTrace(); }
		finally{try { pr.close(); result.close(); } catch(SQLException ex){}}
		return null;
	}

	public static ObservableList<BillModel> selectAllBillModel()
	{
		PreparedStatement pr = null;
		ResultSet result = null;
		ObservableList<BillModel> list = FXCollections.observableArrayList();
		String qe = "select * from Bill";
		try {
			pr = conn.prepareStatement(qe);
			result = pr.executeQuery();
			while(result.next())
			{
				Bill bill = new Bill();
				Child ch = ChildDatabase.selectChild(result.getInt(2));
				Partner part = PartnerDatabase.selectPartner(result.getInt(3));
				bill.setId(result.getInt(1));
				ch.setPaidUpMoney(result.getDouble(5));
				ch.setOneSessionPrice(result.getDouble(6));
				ch.setDoneSessions(result.getInt(7));
				bill.setNewPayment(result.getDouble(4));
				bill.setPayDate(result.getString(8));
				bill.setDuration(result.getInt(9));
				bill.setChild(ch);
				bill.setPartner(part);
				bill.setPartName(result.getString(10));
				bill.setNewOneSessionPrice(result.getDouble(11));
				BillModel billModel = new BillModel(bill);
				list.add(billModel);
			}
			
			
			return list;
		} catch (SQLException e) { e.printStackTrace(); }
		finally{try { pr.close(); result.close(); } catch(SQLException ex){}}
		return null;
	}

	public static Bill selectBill(int id)
	{
		PreparedStatement pr = null;
		ResultSet result = null;
		Bill bill = new Bill();
		String qe = "select * from Bill where id = " + id + ";";
		try {
			pr = conn.prepareStatement(qe);
			result = pr.executeQuery();
			while(result.next())
			{

				Child ch = ChildDatabase.selectChild(result.getInt(2));
				Partner part = PartnerDatabase.selectPartner(result.getInt(3));
				bill.setId(result.getInt(1));
				ch.setPaidUpMoney(result.getDouble(5));
				ch.setOneSessionPrice(result.getDouble(6));
				ch.setDoneSessions(result.getInt(7));
				bill.setNewPayment(result.getDouble(4));
				bill.setPayDate(result.getString(8));
				bill.setDuration(result.getInt(9));
				bill.setChild(ch);
				bill.setPartner(part);
				bill.setPartName(result.getString(10));
				bill.setNewOneSessionPrice(result.getDouble(11));
				bill.setPartName(result.getString("partName"));
			}
			
			
			return bill;
		} catch (SQLException e) { e.printStackTrace(); }
		finally{try { pr.close(); result.close(); } catch(SQLException ex){}}
		return null;
	}

	public static Bill selectLastBillOfChild(int childId)
	{
		PreparedStatement pr = null;
		ResultSet result = null;
		Bill bill = new Bill();
		String qe = "select * from bill where Id = (select max(id) from bill where childId = "+childId+");" ;                           
		try {
			pr = conn.prepareStatement(qe);
			result = pr.executeQuery();
			while(result.next())
			{

				Child ch = ChildDatabase.selectChild(result.getInt(2));
				Partner part = PartnerDatabase.selectPartner(result.getInt(3));
				bill.setId(result.getInt(1));
				ch.setPaidUpMoney(result.getDouble(5));
				ch.setOneSessionPrice(result.getDouble(6));
				ch.setDoneSessions(result.getInt(7));
				bill.setNewPayment(result.getDouble(4));
				bill.setPayDate(result.getString(8));
				bill.setDuration(result.getInt(9));
				bill.setChild(ch);
				bill.setPartner(part);
				bill.setPartName(result.getString(10));
				bill.setNewOneSessionPrice(result.getDouble(11));
			}
			return bill;
		} catch (SQLException e) { e.printStackTrace(); }
		finally{try { pr.close(); result.close(); } catch(SQLException ex){}}
		return null;
	}

	public static ObservableList<Bill> selectBillByChildName(String childName)
	{
		int id = ChildDatabase.selectChild(childName).getId();
		ObservableList<Bill> list = FXCollections.observableArrayList();
		PreparedStatement pr = null;
		ResultSet result = null;
		
		String qe = "select * from Bill where childId = '" + id + "';";
		try {
			pr = conn.prepareStatement(qe);
			result = pr.executeQuery();
			while(result.next())
			{
				Bill bill = new Bill();
				Child ch = ChildDatabase.selectChild(result.getInt(2));
				Partner part = PartnerDatabase.selectPartner(result.getInt(3));
				bill.setId(result.getInt(1));
				ch.setPaidUpMoney(result.getDouble(5));
				ch.setOneSessionPrice(result.getDouble(6));
				ch.setDoneSessions(result.getInt(7));
				bill.setNewPayment(result.getDouble(4));
				bill.setPayDate(result.getString(8));
				bill.setDuration(result.getInt(9));
				bill.setChild(ch);
				bill.setPartner(part);
				bill.setPartName(result.getString(10));
				bill.setNewOneSessionPrice(result.getDouble(11));
				list.add(bill);
			}
			
			
			return list;
		} catch (SQLException e) { e.printStackTrace(); }
		finally{try { pr.close(); result.close(); } catch(SQLException ex){}}
		return null;
	}

	public static ArrayList<Bill> selectBillByChildId(int id)
	{
		ArrayList<Bill> list = new ArrayList<>();
		PreparedStatement pr = null;
		ResultSet result = null;
		
		String qe = "select * from Bill where childId = '" + id + "';";
		try {
			pr = conn.prepareStatement(qe);
			result = pr.executeQuery();
			while(result.next())
			{
				Bill bill = new Bill();
				Child ch = ChildDatabase.selectChild(result.getInt(2));
				Partner part = PartnerDatabase.selectPartner(result.getInt(3));
				bill.setId(result.getInt(1));
				ch.setPaidUpMoney(result.getDouble(5));
				ch.setOneSessionPrice(result.getDouble(6));
				ch.setDoneSessions(result.getInt(7));
				bill.setNewPayment(result.getDouble(4));
				bill.setPayDate(result.getString(8));
				bill.setDuration(result.getInt(9));
				bill.setChild(ch);
				bill.setPartner(part);
				bill.setPartName(result.getString(10));
				bill.setNewOneSessionPrice(result.getDouble(11));
				list.add(bill);
			}
			
			
			return list;
		} catch (SQLException e) { e.printStackTrace(); }
		finally{try { pr.close(); result.close(); } catch(SQLException ex){}}
		return null;
	}

	public static void insertNewBill(Bill b)
	{
		int id = generateId();
		b.setId(id);
		PreparedStatement pr = null;
		String values = "VALUES ("  + id
					+ "," + b.getChildId()
					+ "," + b.getPartnerId() 
					+ "," + b.getNewPayment()
					+ "," + b.getChild().getPaidUpMoney()
					+ "," + b.getOldOneSessionPrice()
					+ "," + b.getOldDoneSessions()
					+ ",'" + b.getPayDateString()
					+ "'," + b.getDuration()
					+ ",'" + b.getPartName()  
					+ "'," + b.getNewOneSessionPrice()
					+ ");";
        
		String qe = "INSERT INTO bill (id,childId,partnerId,newpayment,paidUpMoney,oneSessionPrice,doneSessions,billDate,duration , partName , newOneSessionPrice)"
		+ values; 
		
		try {
			pr = conn.prepareStatement(qe);
			pr.execute();
			undoData.push(new Bulk(b, true, Operation.INSERT));
			main.getBillController().fillTable();
		} catch (SQLException e) { e.printStackTrace(); }
		finally{try { pr.close();} catch(SQLException ex){
			
		}}
	}

	public static void insertNewBillWithId(Bill b)
	{
		//int id = generateId();
		PreparedStatement pr = null;
		String values = "VALUES ("  + b.getId()
					+ "," + b.getChildId()
					+ "," + b.getPartnerId() 
					+ "," + b.getNewPayment()
					+ "," + b.getChild().getPaidUpMoney()
					+ "," + b.getOldOneSessionPrice()
					+ "," + b.getOldDoneSessions()
					+ ",'" + b.getPayDateString()
					+ "'," + b.getDuration()
					+ ",'" + b.getPartName()  
					+ "');";
        
		String qe = "INSERT INTO bill (id,childId,partnerId,newpayment,paidUpMoney,oneSessionPrice,doneSessions,billDate,duration , partName)"
		+ values; 
		
		try {
			pr = conn.prepareStatement(qe);
			pr.execute();
			//MUtils.notification("«œŒ«· „—«›ﬁ", " „ «œŒ«· ›« Ê—… »‰Ã«Õ\n «”„ «·Õ«·…: " + b.getChild().getName());
			main.getBillController().fillTable();
		} catch (SQLException e) { e.printStackTrace(); }
		finally{try { pr.close();} catch(SQLException ex){
			
		}}
	}

	public static ArrayList<Bill> deleteBillOfChild(String chName)
	{
		Child ch = ChildDatabase.selectChild(chName);
		ArrayList<Bill> bills = selectBillByChildId(ch.getId());
		PreparedStatement pr = null;
		String qe = "DELETE from bill WHERE childId = (select id from child where name = '" +chName +"');";                            
		try {
			pr = conn.prepareStatement(qe);
			pr.execute();
			
			main.getBillController().fillTable();
			idCounter = maxId();
			return bills;
		} catch (SQLException e) { e.printStackTrace(); }
		finally{try { pr.close();} catch(SQLException ex){}}
		return null;
	}
	public static ArrayList<Bill> deleteBillOfChildId(int id)
	{
		ArrayList<Bill> bills = selectBillByChildId(id);
		PreparedStatement pr = null;
		String qe = "DELETE from Bill where childId = "+id+";";                            
		try {
			pr = conn.prepareStatement(qe);
			pr.execute();
			main.getBillController().fillTable();
			idCounter = maxId();
			return bills;
		} catch (SQLException e) { e.printStackTrace(); }
		finally{try { pr.close();} catch(SQLException ex){}}
		return null;
	}
	
	public static void updateBill(Bill s)
	{
		/*
		 *
			UPDATE "main"."Bill" SET "childId" = ?1, "partnerId" = ?2, "newpayment" = ?3, "PaidUpMoney" = ?4, "oneSessionPrice" = ?5, "doneSessions" = ?6, "billDate" = ?7, "duration" = ?8 WHERE  "id" = 3
			Parameters:
			param 1 (integer): 1
			param 2 (integer): 1
			param 3 (integer): 180
			param 4 (integer): 200
			param 5 (integer): 20
			param 6 (integer): 2
			param 7 (text): 2016-12-05
			param 8 (integer): 1
		 */
		PreparedStatement pr = null;
		String qe = "UPDATE main.Bill SET childId = ?1, partnerId = ?2, newpayment = ?3, "
				+ "PaidUpMoney = ? , oneSessionPrice = ? , doneSessions = ? , billDate = '?', duration = ? , partName = '?' , newOneSessionPrice = ? "
				+ "WHERE  id = ?";
		
		try {
			pr = conn.prepareStatement(qe);
			pr.setInt(1, s.getChildId());
			pr.setInt(2, s.getPartnerId());
			pr.setDouble(3, s.getNewPayment());
			pr.setDouble(4, s.getOldRestMoney());
			pr.setDouble(5, s.getOldOneSessionPrice());
			pr.setInt(6, s.getOldDoneSessions());
			pr.setString(7, s.getPayDateString());
			pr.setInt(8, s.getDuration());
			pr.setString(9 , s.getPartName());
			pr.setDouble(10, s.getNewOneSessionPrice());
			pr.setInt(11, s.getId());
			
			pr.execute();
			
			main.getBillController().fillTable();
		} catch (SQLException e) { e.printStackTrace(); }
		finally{try { pr.close();} catch(SQLException ex){}}
	}
	
	public static ObservableList<Bill> searchBillByChildName(String name)
	{
		PreparedStatement pr = null;
		ResultSet result = null;
		ObservableList<Bill> list = FXCollections.observableArrayList();
		String qe = "select * from bill where childId in (select id from child where instr(name , '"+name+"'));";
		
		try {
			pr = conn.prepareStatement(qe);
			result = pr.executeQuery();
			while(result.next())
			{
				Bill bill = new Bill();
				Child ch = ChildDatabase.selectChild(result.getInt(2));
				Partner part = PartnerDatabase.selectPartner(result.getInt(3));
				bill.setId(result.getInt(1));
				ch.setPaidUpMoney(result.getDouble(5));
				ch.setOneSessionPrice(result.getDouble(6));
				ch.setDoneSessions(result.getInt(7));
				bill.setNewPayment(result.getDouble(4));
				bill.setPayDate(result.getString(8));
				bill.setDuration(result.getInt(9));
				bill.setChild(ch);
				bill.setPartner(part);
				bill.setPartName(result.getString(10));
				bill.setNewOneSessionPrice(result.getDouble(11));
				list.add(bill);
			}
			return list;
		} catch (SQLException e) { e.printStackTrace(); }
		return null;
	}

	public static ObservableList<BillModel> searchBill(String chName  , String p1Name)
	{
		PreparedStatement pr = null;
		ResultSet result = null;
		ObservableList<BillModel> list = FXCollections.observableArrayList();
		boolean chEmpty = chName.isEmpty();
		boolean p1Empty = p1Name.isEmpty();
		String qe = "";
		String qe1 = "select id from child where instr(name , '" +chName + "' ) > 0";
		String qe2 = "select id from Partner where instr(name , '" +p1Name + "' ) > 0";
		if(!chEmpty && !p1Empty)
			qe += "select * from bill where childId in (" + qe1 + ") and partnerId in (" 
					+ qe2 + ");";
		else if(!chEmpty && p1Empty)
			qe += "select * from bill where childId in (" + qe1 +");";
		else if(chEmpty && !p1Empty)
			qe += "select * from bill where partnerId in (" + qe2 +");";
		else
			qe = "select * from bill;";
		
		
		
		try {
			pr = conn.prepareStatement(qe);
			result = pr.executeQuery();
			while(result.next())
			{
				Bill bill = new Bill();
				Child ch = ChildDatabase.selectChild(result.getInt(2));
				Partner part = PartnerDatabase.selectPartner(result.getInt(3));
				bill.setId(result.getInt(1));
				ch.setPaidUpMoney(result.getDouble(5));
				ch.setOneSessionPrice(result.getDouble(6));
				ch.setDoneSessions(result.getInt(7));
				bill.setNewPayment(result.getDouble(4));
				bill.setPayDate(result.getString(8));
				bill.setDuration(result.getInt(9));
				bill.setChild(ch);
				bill.setPartner(part);
				bill.setPartName(result.getString(10));
				bill.setNewOneSessionPrice(result.getDouble(11));
				BillModel model = new BillModel(bill);
				list.add(model);
			}
			return list;
		} catch (SQLException e) { e.printStackTrace(); }
		return null;
	}
	
	public static void deleteBill(int id) {
		Bill b = selectBill(id);
		PreparedStatement pr = null;
		String qe = "DELETE from Bill where Id = "+id+";";                            
		
		try {
			pr = conn.prepareStatement(qe);
			pr.execute();
			undoData.push(new Bulk(b, true, Operation.DELETE));
			main.getBillController().fillTable();
			idCounter = maxId();
		} catch (SQLException e) { e.printStackTrace(); }
		finally{try { pr.close();} catch(SQLException ex){}}
		
	}
	public static void deleteBillUndo(int id) {
		PreparedStatement pr = null;
		String qe = "DELETE from Bill where Id = "+id+";";                            
		
		try {
			pr = conn.prepareStatement(qe);
			pr.execute();
			main.getBillController().fillTable();
			idCounter = maxId();
		} catch (SQLException e) { e.printStackTrace(); }
		finally{try { pr.close();} catch(SQLException ex){}}
		
	}
	
	
	public static void deleteBillMulti(ObservableList<Bill> bills) {
		for(Bill bill : bills){
			deleteBill(bill.getId());
		}
	}
	
	public static void deleteLastBillOfChild(int childId) {
		PreparedStatement pr = null;
		String qe = "DELETE from Bill where Id = (select max(id) from bill where childId = "+childId+");" ;                           
		
		try {
			pr = conn.prepareStatement(qe);
			pr.execute();		
			main.getBillController().fillTable();
			idCounter = maxId();
		} catch (SQLException e) { e.printStackTrace(); }
		finally{try { pr.close();} catch(SQLException ex){}}
	}
	

	
	public static void insertNewBillUndo(Bill b) {
		System.out.println("bill id insert new bill undo = " + b.getId());
		//int id = generateId();
		PreparedStatement pr = null;
		String values = "VALUES ("  + b.getId()
					+ "," + b.getChildId()
					+ "," + b.getPartnerId() 
					+ "," + b.getNewPayment()
					+ "," + b.getChild().getPaidUpMoney()
					+ "," + b.getOldOneSessionPrice()
					+ "," + b.getOldDoneSessions()
					+ ",'" + b.getPayDateString()
					+ "'," + b.getDuration()
					+ ",'" + b.getPartName()  
					+ "');";
        
		String qe = "INSERT INTO bill (id,childId,partnerId,newpayment,paidUpMoney,oneSessionPrice,doneSessions,billDate,duration , partName)"
		+ values;
		try {
			pr = conn.prepareStatement(qe);
			pr.execute();
			main.getBillController().fillTable();
		} catch (SQLException e) { e.printStackTrace(); }
		finally{try { pr.close();} catch(SQLException ex){
			
		}}
		
	}

	public static int count(){
		int count = 0;
		PreparedStatement pr = null;
		ResultSet result = null;
		String qe = "select count(id) from bill";
		try {
			pr = conn.prepareStatement(qe);
			result = pr.executeQuery();
			count = result.getInt(1);
			return count;
		} catch (SQLException e) { e.printStackTrace(); }
		return 0;
	}
}
