package application.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import application.model.Specialist;
import application.model.SpecialistModel;
import application.undoRedoSystem.Bulk;
import application.undoRedoSystem.Operation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SpecialistDatabase extends Database {
	public static ObservableList<Specialist> selectAllSpecialist() {
		PreparedStatement pr = null;
		ResultSet result = null;
		ObservableList<Specialist> list = FXCollections.observableArrayList();
		
		String qe = "select * from specialist;" ;
		try {
			pr = conn.prepareStatement(qe);
			result = pr.executeQuery();
			while(result.next())
			{
				Specialist sp = new Specialist();
				sp.setId(result.getInt(1));
				sp.setName(result.getString(2));
				sp.setPhone1(result.getLong(3));
				sp.setPhone2(result.getLong(4));
				list.add(sp);
			}
			return list;
		} catch (SQLException e) { e.printStackTrace(); }
		finally{try { pr.close(); result.close(); } catch(SQLException ex){}}

		return null;
	}
	
	public static ObservableList<String> selectAllSpecialistNames() {
		PreparedStatement pr = null;
		ResultSet result = null;
		ObservableList<String> list = FXCollections.observableArrayList();
		
		String qe = "select name from specialist;" ;
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
	
	public static Specialist selectSpecialist(int id) {
		PreparedStatement pr = null;
		ResultSet result = null;
		Specialist sp = new Specialist();
		String qe = "select * from specialist where id = " +id + ";" ;
		try {
			pr = conn.prepareStatement(qe);
			result = pr.executeQuery();
			while(result.next())
			{
				
				sp.setId(result.getInt(1));
				sp.setName(result.getString(2));
				sp.setPhone1(result.getLong(3));
				sp.setPhone2(result.getLong(4));
			}
			
			
			return sp;
		} catch (SQLException e) { e.printStackTrace(); }
		finally{try { pr.close(); result.close(); } catch(SQLException ex){}}
		return null;
	}
	
	public static Specialist selectSpecialist(String name) {
		if(name == null)
			return new Specialist();
		PreparedStatement pr = null;
		ResultSet result = null;
		Specialist sp = new Specialist();
		String qe = "select * from specialist where name = '" +name + "';" ;
		try {
			pr = conn.prepareStatement(qe);
			result = pr.executeQuery();
			while(result.next())
			{
				
				sp.setId(result.getInt(1));
				sp.setName(result.getString(2));
				sp.setPhone1(result.getLong(3));
				sp.setPhone2(result.getLong(4));
			}
			
			return sp;
		} catch (SQLException e) { e.printStackTrace(); }
		finally{try { pr.close(); result.close(); } catch(SQLException ex){}}
		return null;
	}
	
	public static ObservableList<String> searchSpecialistNames(String name) {
		PreparedStatement pr = null;
		ResultSet result = null;
		ObservableList<String> list = FXCollections.observableArrayList();
		
		String qe = "select name from specialist where instr(name , '" + name +"');" ;
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
	
	public static ObservableList<SpecialistModel> searchSpecialist(String name) {
		PreparedStatement pr = null;
		ResultSet result = null;
		ObservableList<SpecialistModel> list = FXCollections.observableArrayList();
		
		String qe = "select * from specialist where instr(name , '" + name +"');" ;
		try {
			pr = conn.prepareStatement(qe);
			result = pr.executeQuery();
			while(result.next())
			{
				Specialist sp = new Specialist();
				sp.setId(result.getInt(1));
				sp.setName(result.getString(2));
				sp.setPhone1(result.getLong(3));
				sp.setPhone2(result.getLong(4));
				SpecialistModel model = new SpecialistModel(sp);
				list.add(model);
			}
			return list;
		} catch (SQLException e) { e.printStackTrace(); }
		finally{try { pr.close(); result.close(); } catch(SQLException ex){}}

		return null;
	}

	public static ObservableList<Integer> searchSpecialistId(String name) {
		PreparedStatement pr = null;
		ResultSet result = null;
		ObservableList<Integer> list = FXCollections.observableArrayList();
		
		String qe = "select id from specialist where instr(name , '" + name +"');" ;
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

	public static void insertNewSpecialist(Specialist sp)
	{
		PreparedStatement pr = null;
		String values = "VALUES ('" + sp.getName()
					+ "'," + sp.getPhone1() 
					+ "," + sp.getPhone2()
					+ ");";
        
		String qe = "INSERT INTO specialist (name , phone1 , phone2) "
		+ values; 
		
		try {
			pr = conn.prepareStatement(qe);
			pr.execute();
			undoData.push(new Bulk(selectSpecialist(sp.getName()), true, Operation.INSERT));
			main.getSpecialistController().fillTable();
		} catch (SQLException e) { e.printStackTrace(); }
		finally{try { pr.close();} catch(SQLException ex){}}
	}
	public static void insertNewSpecialistUndo(Specialist sp)
	{
		PreparedStatement pr = null;
		String values = "VALUES ("+sp.getId() +" , '" + sp.getName()
					+ "'," + sp.getPhone1() 
					+ "," + sp.getPhone2()
					+ ");";
        
		String qe = "INSERT INTO specialist (id , name , phone1 , phone2) "
		+ values; 
		
		try {
			pr = conn.prepareStatement(qe);
			pr.execute();
			main.getSpecialistController().fillTable();
		} catch (SQLException e) { e.printStackTrace(); }
		finally{try { pr.close();} catch(SQLException ex){}}
	}

	public static ObservableList<SpecialistModel> selectAllSpecialistModel() {
		PreparedStatement pr = null;
		ResultSet result = null;
		ObservableList<SpecialistModel> list = FXCollections.observableArrayList();
		
		String qe = "select * from specialist;" ;
		try {
			pr = conn.prepareStatement(qe);
			result = pr.executeQuery();
			while(result.next())
			{
				Specialist sp = new Specialist();
				sp.setId(result.getInt(1));
				sp.setName(result.getString(2));
				sp.setPhone1(result.getLong(3));
				sp.setPhone2(result.getLong(4));
				SpecialistModel model = new SpecialistModel(sp);
				list.add(model);
			}
			return list;
		} catch (SQLException e) { e.printStackTrace(); }
		finally{try { pr.close(); result.close(); } catch(SQLException ex){}}

		return null;
	}

	public static void deleteSpecialist(int id)
	{
		Specialist sp = selectSpecialist(id);
		PreparedStatement pr = null;
		String qe = "DELETE from specialist where Id = "+id+";";                            
		
		try {
			pr = conn.prepareStatement(qe);
			pr.execute();
			undoData.push(new Bulk(sp, true, Operation.DELETE));
			main.getSpecialistController().fillTable();
		} catch (SQLException e) { e.printStackTrace(); }
		finally{try { pr.close();} catch(SQLException ex){}}
	}

	public static void deleteSpecialistUndo(int id)
	{
		PreparedStatement pr = null;
		String qe = "DELETE from specialist where Id = "+id+";";                            
		
		try {
			pr = conn.prepareStatement(qe);
			pr.execute();
			main.getSpecialistController().fillTable();
		} catch (SQLException e) { e.printStackTrace(); }
		finally{try { pr.close();} catch(SQLException ex){}}
	}

	public static void updateSpecialist(int id, Specialist sp) {
		Specialist old = selectSpecialist(id);
		PreparedStatement pr = null;
		String qe = "UPDATE Specialist SET name = '"+ sp.getName()
					+ "', phone1 = " + sp.getPhone1() + ", phone2 = " + sp.getPhone2()
					 + " where id = " + id + ";";
		try {
			pr = conn.prepareStatement(qe);
			pr.execute();
			undoData.push(new Bulk(old , true, Operation.UPDATE));
			redoData.pushCurrent(new Bulk(sp, true, Operation.UPDATE));
			main.getSpecialistController().fillTable();
		} catch (SQLException e) { e.printStackTrace(); }
		finally{try { pr.close();} catch(SQLException ex){}}
		
	}

	public static void updateSpecialistUndo(int id, Specialist sp) {
		PreparedStatement pr = null;
		String qe = "UPDATE Specialist SET name = '"+ sp.getName()
					+ "', phone1 = " + sp.getPhone1() + ", phone2 = " + sp.getPhone2()
					 + " where id = " + id + ";";
		try {
			pr = conn.prepareStatement(qe);
			pr.execute();
			main.getSpecialistController().fillTable();
		} catch (SQLException e) { e.printStackTrace(); }
		finally{try { pr.close();} catch(SQLException ex){}}
		
	}

	public static void deleteSpecialistMulti(ObservableList<Specialist> sps) {
		for(Specialist sp : sps){
			deleteSpecialist(sp.getId());
		}		
	}

	public static boolean checkIfNameExist(String value) {
		Specialist sps = SpecialistDatabase.selectSpecialist(value);
		if (sps.getName().equals(value))
			return true;
		else return false;
	}
	public static boolean checkIfNameExistEdit(String exceptedname , String value) {
		Specialist sps = SpecialistDatabase.selectSpecialistExcept(exceptedname,value);
		if (sps.getName().equals(value))
			return true;
		else return false;
	}

	private static Specialist selectSpecialistExcept(String exceptedname, String name) {
		if(name == null)
			return new Specialist();
		PreparedStatement pr = null;
		ResultSet result = null;
		Specialist sp = new Specialist();
		String qe = "select * from specialist where name = '" + name 
				+ "' and name != '" + exceptedname + "';";
		try {
			pr = conn.prepareStatement(qe);
			result = pr.executeQuery();
			while(result.next())
			{
				
				sp.setId(result.getInt(1));
				sp.setName(result.getString(2));
				sp.setPhone1(result.getLong(3));
				sp.setPhone2(result.getLong(4));
			}
			
			return sp;
		} catch (SQLException e) { e.printStackTrace(); }
		finally{try { pr.close(); result.close(); } catch(SQLException ex){}}
		return null;
	}
}
