package application.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import application.utils.MUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class StatusDatabase extends Database {

	public static ObservableList<String> selectAllStatus()
	{
		PreparedStatement pr = null;
		ResultSet result = null;
		ObservableList<String> list = FXCollections.observableArrayList();
		String qe = "select * from Status";
		try {
			pr = conn.prepareStatement(qe);
			result = pr.executeQuery();
			while(result.next())
			{
				list.add(result.getString("statusName"));
			}
			return list;
		} catch (SQLException e) { e.printStackTrace(); }
		finally{try { pr.close(); result.close(); } catch(SQLException ex){}}
		return null;
	}

	public static String [] selectAllStatusArray()
	{
		PreparedStatement pr = null;
		ResultSet result = null;
		String [] names = new String[countStatus()];
		String qe = "select * from Status";
		try {
			pr = conn.prepareStatement(qe);
			result = pr.executeQuery();
			int counter = 0;
			while(result.next())
			{
				names[counter++] = result.getString("statusName");
			}
			return names;
		} catch (SQLException e) { e.printStackTrace(); }
		finally{try { pr.close(); result.close(); } catch(SQLException ex){}}
		return null;
	}

	public static void deleteStatus(String name)
	{
		PreparedStatement pr = null;
		String qe = "DELETE from Status WHERE statusName = '" + name+"';";
		
		
		try {
			pr = conn.prepareStatement(qe);
			pr.execute();
			
		} catch (SQLException e) { e.printStackTrace(); }
		finally{try { pr.close();} catch(SQLException ex){}}
	}

	public static void insertNewStatus(String name)
	{
		if(checkIfStatusExist(name))
			return;
		PreparedStatement pr = null;
		String qe = "INSERT INTO status (StatusName) values('" + name + "');";
		
		try {
			pr = conn.prepareStatement(qe);
			pr.execute();
			MUtils.notification("«œŒ«·  ‘ŒÌ’", " „ «œŒ«· «· ‘ŒÌ’ »‰Ã«Õ");
		} catch (SQLException e) { e.printStackTrace(); }
		finally{try { pr.close();} catch(SQLException ex){
			
		}}
	}
	
	public static boolean checkIfStatusExist(String name)
	{
		ObservableList<String> names = selectAllStatus();
		for(String n: names)
		{
			if(n.equals(name))
				return true;
		}
		return false;
	}

	public static int countStatus()
	{
		PreparedStatement pr = null;
		ResultSet result = null;
		
		String qe = "select count(*) from status;";
		try{
			pr = conn.prepareStatement(qe);
			result = pr.executeQuery();
			return result.getInt(1);
		}catch (SQLException e) { e.printStackTrace(); }
		finally{try { pr.close();} catch(SQLException ex){}}
		return 0;
	}
}
