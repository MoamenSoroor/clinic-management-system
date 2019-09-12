package application.database;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectDatabase {
	public static Connection connect() {
         try 
         {
        	 Connection conn;
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:src/Clinic.sqlite");
            return conn;
         }  
         catch (Exception e) { e.printStackTrace(); }
         return null;
	}
	
}
