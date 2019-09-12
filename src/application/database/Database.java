package application.database;

import java.sql.Connection;
import java.sql.SQLException;
import application.MainFXMLController;
import application.database.undo_redo_system.RedoData;
import application.database.undo_redo_system.UndoData;
public class Database {
	static UndoData undoData = new UndoData();
	public static UndoData getUndoData() {
		return undoData;
	}
	static RedoData redoData = new RedoData();
	public static RedoData getRedoData() {
		return redoData;
	}

	static MainFXMLController main;
	public static MainFXMLController getMainFXMLController(){
		return main;
	}
	public static Connection conn;
	protected Database(){
	}
 	public static void setDataBaseConnection()
	{
		conn = ConnectDatabase.connect();
	}

	public static void closeDatabase()
	{
		try {
			conn.close();
		} catch (SQLException e){ e.printStackTrace();}
	}

	public static void loadMain(MainFXMLController mainFXMLController) {
		main = mainFXMLController;
		
	}



}













