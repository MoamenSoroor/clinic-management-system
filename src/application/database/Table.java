package application.database;

public enum Table { 
	CHILD("child"), PRTNER("partner") , SPECIALIST("specialist") 
	, SESSION("session") , BILL("bill") , PAID("paid") , ONE_SESSION("oneSession");
	
	private String tableName;
	private Table(String name){
		tableName = name;
	}
	public String getTableName(){
		return tableName;
	}



}
