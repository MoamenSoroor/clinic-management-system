package application.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import application.model.Child;
import application.model.Partner;
import application.model.PartnerModel;
import application.undoRedoSystem.Bulk;
import application.undoRedoSystem.Operation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PartnerDatabase extends Database {
	public static ObservableList<Partner> selectAllPartner() {
		PreparedStatement pr = null;
		ResultSet result = null;
		ObservableList<Partner> list = FXCollections.observableArrayList();

		String qe = "select * from partner;";
		try {
			pr = conn.prepareStatement(qe);
			result = pr.executeQuery();
			while (result.next()) {
				Partner part = new Partner();
				part.setId(result.getInt(1));
				part.setName(result.getString(2));
				part.setPhone1(result.getLong(3));
				part.setPhone2(result.getLong(4));
				part.setGroundPhone(result.getLong(5));
				part.setAddress(result.getString(6));
				list.add(part);
			}
			return list;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pr.close();
				result.close();
			} catch (SQLException ex) {
			}
		}
		return null;
	}

	public static ObservableList<PartnerModel> selectAllPartnerModel() {
		PreparedStatement pr = null;
		ResultSet result = null;
		ObservableList<PartnerModel> list = FXCollections.observableArrayList();

		String qe = "select * from partner;";
		try {
			pr = conn.prepareStatement(qe);
			result = pr.executeQuery();
			while (result.next()) {
				Partner part = new Partner();
				part.setId(result.getInt(1));
				part.setName(result.getString(2));
				part.setPhone1(result.getLong(3));
				part.setPhone2(result.getLong(4));
				part.setGroundPhone(result.getLong(5));
				part.setAddress(result.getString(6));
				PartnerModel model = new PartnerModel(part);
				list.add(model);
			}
			return list;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pr.close();
				result.close();
			} catch (SQLException ex) {
			}
		}
		return null;
	}

	public static ObservableList<String> selectAllPartnerNames() {
		PreparedStatement pr = null;
		ResultSet result = null;
		ObservableList<String> list = FXCollections.observableArrayList();

		String qe = "select name from partner;";
		try {
			pr = conn.prepareStatement(qe);
			result = pr.executeQuery();
			while (result.next()) {
				list.add(result.getString("name"));
			}
			return list;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pr.close();
				result.close();
			} catch (SQLException ex) {
			}
		}
		return null;
	}

	public static ObservableList<Integer> selectAllPartnerId() {
		PreparedStatement pr = null;
		ResultSet result = null;
		ObservableList<Integer> list = FXCollections.observableArrayList();

		String qe = "select id from partner;";
		try {
			pr = conn.prepareStatement(qe);
			result = pr.executeQuery();
			while (result.next()) {
				list.add(result.getInt("id"));
			}
			return list;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pr.close();
				result.close();
			} catch (SQLException ex) {
			}
		}
		return null;
	}

	public static Partner selectPartner(int id) {
		PreparedStatement pr = null;
		ResultSet result = null;
		Partner part = new Partner();
		String qe = "select * from partner where id = " + id + ";";
		try {
			pr = conn.prepareStatement(qe);
			result = pr.executeQuery();
			while (result.next()) {

				part.setId(result.getInt(1));
				part.setName(result.getString(2));
				part.setPhone1(result.getLong(3));
				part.setPhone2(result.getLong(4));
				part.setGroundPhone(result.getLong(5));
				part.setAddress(result.getString(6));
			}
			return part;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pr.close();
				result.close();
			} catch (SQLException ex) {
			}
		}
		return null;
	}

	public static ObservableList<String> SearchPartnerNames(String name) {
		if (name.equals(""))
			return selectAllPartnerNames();
		PreparedStatement pr = null;
		ResultSet result = null;
		ObservableList<String> list = FXCollections.observableArrayList();
		String qe = "select * from partner where instr(name , '" + name + "' ) > 0 ;";
		try {
			pr = conn.prepareStatement(qe);
			result = pr.executeQuery();
			while (result.next()) {
				list.add(result.getString("name"));
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pr.close();
				result.close();
			} catch (SQLException ex) {
			}
		}
		return null;

	}

	public static ObservableList<PartnerModel> SearchPartner(String name) {
		if (name.equals(""))
			return selectAllPartnerModel();
		PreparedStatement pr = null;
		ResultSet result = null;
		ObservableList<PartnerModel> list = FXCollections.observableArrayList();
		String qe = "select * from partner where instr(name , '" + name + "' ) > 0 ;";
		try {
			pr = conn.prepareStatement(qe);
			result = pr.executeQuery();
			while (result.next()) {
				Partner part = new Partner();
				part.setId(result.getInt(1));
				part.setName(result.getString(2));
				part.setPhone1(result.getLong(3));
				part.setPhone2(result.getLong(4));
				part.setGroundPhone(result.getLong(5));
				part.setAddress(result.getString(6));
				PartnerModel model = new PartnerModel(part);
				list.add(model);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pr.close();
				result.close();
			} catch (SQLException ex) {
			}
		}
		return null;

	}

	public static Partner selectPartner(String name) {
		if(name == null)
			return new Partner();
		PreparedStatement pr = null;
		ResultSet result = null;
		Partner part = new Partner();
		String qe = "select * from partner where name = '" + name + "';";
		try {
			pr = conn.prepareStatement(qe);
			result = pr.executeQuery();
			if (result.next()) {

				part.setId(result.getInt(1));
				part.setName(result.getString(2));
				part.setPhone1(result.getLong(3));
				part.setPhone2(result.getLong(4));
				part.setGroundPhone(result.getLong(5));
				part.setAddress(result.getString(6));
			}
			return part;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pr.close();
				result.close();
			} catch (SQLException ex) {
			}
		}
		return null;
	}

	public static void insertNewPartner(Partner p) {
		PreparedStatement pr = null;
		String values = "VALUES ('" + p.getName() + "'," + p.getPhone1() + "," + p.getPhone2() + ","
				+ p.getGroundPhone() + ",'" + p.getAddress() + "');";

		String qe = "INSERT INTO partner (name ,Phone1 , Phone2 , groundPhone , Address) " + values;
		
		try {
			pr = conn.prepareStatement(qe);
			pr.execute();
			undoData.push(new Bulk(PartnerDatabase.selectPartner(p.getName()), true, Operation.INSERT));
			main.getPartnerController().fillTable();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pr.close();
			} catch (SQLException ex) {
			}
		}
	}

	public static void insertNewPartnerWithChild(Partner p) {
		PreparedStatement pr = null;
		String values = "VALUES ('" + p.getName() + "'," + p.getPhone1() + "," + p.getPhone2() + ","
				+ p.getGroundPhone() + ",'" + p.getAddress() + "');";

		String qe = "INSERT INTO partner (name ,Phone1 , Phone2 , groundPhone , Address) " + values;
		
		try {
			pr = conn.prepareStatement(qe);
			pr.execute();
			main.getPartnerController().fillTable();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pr.close();
			} catch (SQLException ex) {
			}
		}
	}

	
	public static void insertNewPartnerUndo(Partner p) {
		PreparedStatement pr = null;
		String values = "VALUES ("+p.getId() +", '" + p.getName() + "'," + p.getPhone1() + "," + p.getPhone2() + ","
				+ p.getGroundPhone() + ",'" + p.getAddress() + "');";

		String qe = "INSERT INTO partner (id , name ,Phone1 , Phone2 , groundPhone , Address) " + values;
		
		try {
			pr = conn.prepareStatement(qe);
			pr.execute();			
			main.refreshTables();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pr.close();
			} catch (SQLException ex) {
			}
		}
		
	}
	
	public static void updatePartner(int id, Partner p) {
		Partner part = PartnerDatabase.selectPartner(id);
		PreparedStatement pr = null;
		String qe = "UPDATE partner SET name = '" + p.getName() + "', phone1 = " + p.getPhone1() + ", phone2 = "
				+ p.getPhone2() + ", groundPhone = " + p.getGroundPhone() + ", address = '" + p.getAddress()
				+ "' where id = " + id + ";";
		try {
			pr = conn.prepareStatement(qe);
			pr.execute();
			undoData.push(new Bulk(part, true, Operation.UPDATE));
			redoData.pushCurrent(new Bulk(selectPartner(id), true, Operation.UPDATE));

			main.getPartnerController().fillTable();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {try { pr.close(); } catch (SQLException ex) {}}
	}

	public static ArrayList<Child> deletePartner(int id) {
		Partner part = PartnerDatabase.selectPartner(id);
		ArrayList<Child> childs = ChildDatabase.selectChildOfPartner(id);
		ArrayList<Child> childs2 = ChildDatabase.selectChildOfPartner(id);

		PreparedStatement pr = null;
		String qe = "DELETE from partner where Id = " + id + ";";
		
		try {
			pr = conn.prepareStatement(qe);
			pr.execute();
			deletePartnerFromChild(id, childs);

			undoData.push(new Bulk(part, true, Operation.DELETE));
			undoData.push(new Bulk(childs2, false, Operation.UPDATE));
			
			main.getPartnerController().fillTable();
			return childs;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pr.close();
			} catch (SQLException ex) {
			}
		}
		return null;
	}

	public static ArrayList<Child> deletePartnerUndo(int id) {
		ArrayList<Child> childs = ChildDatabase.selectChildOfPartner(id);
		PreparedStatement pr = null;
		String qe = "DELETE from partner where Id = " + id + ";";
		
		try {
			pr = conn.prepareStatement(qe);
			pr.execute();
			if(!childs.isEmpty())
				deletePartnerFromChild(id, new ArrayList<>(childs));
			main.getPartnerController().fillTable();
			return childs;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pr.close();
			} catch (SQLException ex) {
			}
		}
		return null;
	}

	public static void deletePartnerFromChild(int partId, ArrayList<Child> Childs) {
		for (Child ch : Childs) {
			
			if (ch.getP1().getId() == partId) {
				ch.setP1(new Partner());
			}
			if (ch.getP2().getId() == partId) {
				ch.setP2(new Partner());
			}
			ChildDatabase.UpdateChildPartnerUndo(ch);

		}
		main.getChildController().fillTable();
	}

	public static boolean checkIfNameExist(String name) {
		Partner part = PartnerDatabase.selectPartner(name);
		if (part.getName().equals(name))
			return true;
		else return false;
	}
	public static boolean checkIfNameExistEdit(String exceptedname , String name) {
		Partner part = PartnerDatabase.selectPartnerExcept(exceptedname ,name);
		if (part.getName().equals(name))
			return true;
		else return false;
	}

	public static Partner selectPartnerExcept(String exceptedname, String name) {
		if(name == null)
			return new Partner();
		PreparedStatement pr = null;
		ResultSet result = null;
		Partner part = new Partner();
		String qe = "select * from partner where name = '" + name 
				+ "' and name != '" + exceptedname + "';";
		try {
			pr = conn.prepareStatement(qe);
			result = pr.executeQuery();
			if (result.next()) {

				part.setId(result.getInt(1));
				part.setName(result.getString(2));
				part.setPhone1(result.getLong(3));
				part.setPhone2(result.getLong(4));
				part.setGroundPhone(result.getLong(5));
				part.setAddress(result.getString(6));
			}
			return part;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pr.close();
				result.close();
			} catch (SQLException ex) {
			}
		}
		return null;
	}

	public static void deletePartnerMulti(ObservableList<Partner> parts) {
		for(Partner part : parts){
			deletePartner(part.getId());
		}		
	}

	public static void updatePartnerUndo(int id, Partner p) {
		PreparedStatement pr = null;
		String qe = "UPDATE partner SET name = '" + p.getName() + "', phone1 = " + p.getPhone1() + ", phone2 = "
				+ p.getPhone2() + ", groundPhone = " + p.getGroundPhone() + ", address = '" + p.getAddress()
				+ "' where id = " + id + ";";
		try {
			pr = conn.prepareStatement(qe);
			pr.execute();
			main.refreshTables();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally { try { pr.close(); } catch (SQLException ex) {}}
	}

	
}
