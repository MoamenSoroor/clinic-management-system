package application.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import application.model.PaidSystem;
import application.model.TableProperties;

public class PropertiesDataBase extends Database {

	private PropertiesDataBase() {}
	
	public static void insertnewProperty(TableProperties pro){
		PreparedStatement pr = null;
		String values = "VALUES ('" + pro.getTableName() 
						+ "'," + (pro.isProperty1()? 1:0 )
						+ "," + (pro.isProperty2()? 1:0 )
						+ "," + (pro.isProperty3()? 1:0 )
						+ "," + (pro.isProperty4()? 1:0 ) 
						+ "," + (pro.isProperty5()? 1:0 )
						+ "," + (pro.isProperty6()? 1:0 )
						+ "," + (pro.isProperty7()? 1:0 )
						+ "," + (pro.isProperty8()? 1:0 )
						+ "," + (pro.isProperty9()? 1:0 ) + ");"; 
		String qe = "INSERT INTO Properties (tableName,prop1,prop2,prop3,prop4,prop5,prop6,prop7,prop8,prop9) " + values;
		
		try {
			pr = conn.prepareStatement(qe);
			pr.execute();
			setVisabilityToTable(pro);
		} catch (SQLException e) { e.printStackTrace(); } finally { try { pr.close(); } catch (SQLException ex) {}}
	}

	public static TableProperties selectProperties(TableProperties pro)
	{
		PreparedStatement pr = null;
		ResultSet result = null;
		TableProperties prop = new TableProperties(pro.getTableType());
		String qe = "select * from Properties where tableName = '" +pro.getTableName() + "';" ;
		try {
			pr = conn.prepareStatement(qe);
			result = pr.executeQuery();
			while(result.next())
			{
				prop.setPropertyInt1(result.getInt(2));
				prop.setPropertyInt2(result.getInt(3));
				prop.setPropertyInt3(result.getInt(4));
				prop.setPropertyInt4(result.getInt(5));
				prop.setPropertyInt5(result.getInt(6));
				prop.setPropertyInt6(result.getInt(7));
				prop.setPropertyInt7(result.getInt(8));
				prop.setPropertyInt8(result.getInt(9));
				prop.setPropertyInt9(result.getInt(10));

			}
			return prop;
		} catch (SQLException e) { e.printStackTrace(); }
		finally{try { pr.close(); result.close(); } catch(SQLException ex){}}
		return null;
	}

	public static TableProperties selectProperties(Table pro)
	{
		PreparedStatement pr = null;
		ResultSet result = null;
		TableProperties prop = new TableProperties(pro);
		String qe = "select * from Properties where tableName = '" +pro.getTableName() + "';" ;
		try {
			pr = conn.prepareStatement(qe);
			result = pr.executeQuery();
			while(result.next())
			{
				prop.setPropertyInt1(result.getInt(2));
				prop.setPropertyInt2(result.getInt(3));
				prop.setPropertyInt3(result.getInt(4));
				prop.setPropertyInt4(result.getInt(5));
				prop.setPropertyInt5(result.getInt(6));
				prop.setPropertyInt6(result.getInt(7));
				prop.setPropertyInt7(result.getInt(8));
				prop.setPropertyInt8(result.getInt(9));
				prop.setPropertyInt9(result.getInt(10));

			}
			return prop;
		} catch (SQLException e) { e.printStackTrace(); }
		finally{try { pr.close(); result.close(); } catch(SQLException ex){}}
		return null;
	}

	public static void UpdateProperties(TableProperties pro) {
		PreparedStatement pr = null;
		String qe = "UPDATE properties SET "
				+ " prop1 = " +   (pro.isProperty1()? 1:0 )
				+ " , prop2 = " + (pro.isProperty2()? 1:0 )
				+ " , prop3 = " + (pro.isProperty3()? 1:0 )
				+ " , prop4 = " + (pro.isProperty4()? 1:0 )
				+ " , prop5 = " + (pro.isProperty5()? 1:0 )
				+ " , prop6 = " + (pro.isProperty6()? 1:0 )
				+ " , prop7 = " + (pro.isProperty7()? 1:0 )
				+ " , prop8 = " + (pro.isProperty8()? 1:0 )
				+ " , prop9 = " + (pro.isProperty9()? 1:0 )
				+ " where tableName = '" + pro.getTableName() + "';";
		
		try {
			
			pr = conn.prepareStatement(qe);
			pr.execute();
			
			setVisabilityToTable(pro);
			
			
		} catch (Exception e) { e.printStackTrace(); }
		finally{try { pr.close();} catch(SQLException ex){}}
	}

	public static void UpdatePropertiesPaid(PaidSystem sys) {
		PreparedStatement pr = null;
		String qe = "UPDATE paidSystem SET "
				+ " paidSystem1 = " +   sys.getPaidMoney11()
				+ " , paidSystem2 = " + sys.getPaidMoney22()
				+ " , paidSystem3 = " + sys.getPaidMoney33()
				+ " , paidSystem4 = " + sys.getPaidMoney44()
				+ " , paidSystem5 = " + sys.getPaidMoney55()
				+ " where tableName = 'paid';";
		
		try {
			
			pr = conn.prepareStatement(qe);
			pr.execute();			
		} catch (Exception e) { e.printStackTrace(); }
		finally{try { pr.close();} catch(SQLException ex){}}
	}

	public static void UpdatePropertiesSessions(PaidSystem sys) {
		PreparedStatement pr = null;
		String qe = "UPDATE paidSystem SET "
				+ " paidSystem1 = " +   sys.getOneSession11()
				+ " , paidSystem2 = " + sys.getOneSession22()
				+ " , paidSystem3 = " + sys.getOneSession33()
				+ " , paidSystem4 = " + sys.getOneSession44()
				+ " , paidSystem5 = " + sys.getOneSession55()
				+ " where tableName = 'oneSession';";
		
		try {
			
			pr = conn.prepareStatement(qe);
			pr.execute();			
		} catch (Exception e) { e.printStackTrace(); }
		finally{try { pr.close();} catch(SQLException ex){}}
	}

	public static PaidSystem selectPropertiesPaidSystem()
	{
		PreparedStatement pr = null;
		ResultSet result = null;
		PaidSystem paid = new PaidSystem();
		String qe = "select * from paidSystem;" ;
		try {
			pr = conn.prepareStatement(qe);
			result = pr.executeQuery();
			int counter = 1;
			while(result.next())
			{
				if(counter == 1)
				{
					paid.setPaidMoney11(result.getInt(1));
					paid.setPaidMoney22(result.getInt(2));
					paid.setPaidMoney33(result.getInt(3));
					paid.setPaidMoney44(result.getInt(4));
					paid.setPaidMoney55(result.getInt(5));
				}
				else if(counter == 2)
				{
					paid.setOneSession11(result.getInt(1));
					paid.setOneSession22(result.getInt(2));
					paid.setOneSession33(result.getInt(3));
					paid.setOneSession44(result.getInt(4));
					paid.setOneSession55(result.getInt(5));
				}
				counter++;
			}
			return paid;
		} catch (SQLException e) { e.printStackTrace(); }
		finally{try { pr.close(); result.close(); } catch(SQLException ex){}}
		return null;
	}

	
	
	
	
	
	public static void updateTableName(String old , String newName){
		PreparedStatement pr = null;
		String qe = "UPDATE properties SET tableName = '" +old 
				+ "' where tableName = '" + newName + "';";
		
		try {
			
			pr = conn.prepareStatement(qe);
			pr.execute();
		} catch (Exception e) { e.printStackTrace(); }
		finally{try { pr.close();} catch(SQLException ex){}}
	}
	
	public static void deleteProperties(TableProperties pro){
		PreparedStatement pr = null;
		String qe = "delete from properties where tableName = '" 
				+pro.getTableName() + "';";
		try {
			
			pr = conn.prepareStatement(qe);
			pr.execute();
		} catch (Exception e) { e.printStackTrace(); }
		finally{try { pr.close();} catch(SQLException ex){}}
	}
	
	public static void deleteProperties(String  tableName){
		PreparedStatement pr = null;
		String qe = "delete from properties where tableName = '" 
				+tableName + "';";
		try {
			
			pr = conn.prepareStatement(qe);
			pr.execute();
		} catch (Exception e) { e.printStackTrace(); }
		finally{try { pr.close();} catch(SQLException ex){}}
	}
	
	
	public static void setVisabilityToTable(TableProperties pro){
		if(pro.getTableType() == Table.CHILD){
			main.getChildController().setColumnsVisability(pro);
		}
		else if(pro.getTableType() == Table.PRTNER){
			main.getPartnerController().setColumnsVisability(pro);
		}
		else if(pro.getTableType() == Table.SPECIALIST){
			main.getSpecialistController().setColumnsVisability(pro);
		}
		else if(pro.getTableType() == Table.SESSION){
			main.getSessionController().setColumnsVisability(pro);
		}
		else{
			main.getBillController().setColumnsVisability(pro);
		}
	}
}

/*
public static void main(String...args){
	Database.setDataBaseConnection();
	insertnewProperty(new TableProperties(Table.CHILD));
	insertnewProperty(new TableProperties(Table.PRTNER));
	insertnewProperty(new TableProperties(Table.SESSION));
	insertnewProperty(new TableProperties(Table.SPECIALIST));
	insertnewProperty(new TableProperties(Table.BILL));
	
	Database.closeDatabase();
}
*/










