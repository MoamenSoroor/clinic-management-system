package application.model;

import application.database.Table;

public class TableProperties {

	private Table tableType;
	private String tableName;
	private boolean property1;
	private boolean property2;
	private boolean property3;
	private boolean property4;
	private boolean property5;
	private boolean property6;
	private boolean property7;
	private boolean property8;
	private boolean property9;

	public TableProperties(Table table) {
		tableType = table;
		tableName = tableType.getTableName();
		property1 = true;
		property2 = true;
		property3 = true;
		property4 = true;
		property5 = true;
		property6 = true;
		property7 = true;
		property8 = true;
		property9 = true;
	}

	public Table getTableType() {
		return tableType;
	}

	public void setTableType(Table tableType) {
		this.tableType = tableType;
		tableName = this.tableType.getTableName();
		property1 = true;
		property2 = true;
		property3 = true;
		property4 = true;
		property5 = true;
		property6 = true;
		property7 = true;
		property8 = true;
		property9 = true;
	}

	public String getTableName() {
		return tableName;
	}

	public boolean isProperty1() {
		return property1;
	}

	public void setProperty1(boolean property1) {
		this.property1 = property1;
	}

	public boolean isProperty2() {
		return property2;
	}

	public void setProperty2(boolean property2) {
		this.property2 = property2;
	}

	public boolean isProperty3() {
		return property3;
	}

	public void setProperty3(boolean property3) {
		this.property3 = property3;
	}

	public boolean isProperty4() {
		return property4;
	}

	public void setProperty4(boolean property4) {
		this.property4 = property4;
	}

	public boolean isProperty5() {
		return property5;
	}

	public void setProperty5(boolean property5) {
		this.property5 = property5;
	}

	public boolean isProperty6() {
		return property6;
	}

	public void setProperty6(boolean property6) {
		this.property6 = property6;
	}

	public boolean isProperty7() {
		return property7;
	}

	public void setProperty7(boolean property7) {
		this.property7 = property7;
	}

	public boolean isProperty8() {
		return property8;
	}

	public void setProperty8(boolean property8) {
		this.property8 = property8;
	}

	public void setProperty9(boolean selected) {
		this.property9 = selected;
	}

	public boolean isProperty9() {
		return property9;
	}

	public void setPropertyInt1(int int1) {
		this.property1 = (int1 == 1) ? true : false;
	}

	public void setPropertyInt2(int int1) {
		this.property2 = (int1 == 1) ? true : false;
	}

	public void setPropertyInt3(int int1) {
		this.property3 = (int1 == 1) ? true : false;
	}

	public void setPropertyInt4(int int1) {
		this.property4 = (int1 == 1) ? true : false;
	}

	public void setPropertyInt5(int int1) {
		this.property5 = (int1 == 1) ? true : false;
	}

	public void setPropertyInt6(int int1) {
		this.property6 = (int1 == 1) ? true : false;
	}

	public void setPropertyInt7(int int1) {
		this.property7 = (int1 == 1) ? true : false;
	}

	public void setPropertyInt8(int int1) {
		this.property8 = (int1 == 1) ? true : false;
	}

	public void setPropertyInt9(int int1) {
		this.property9 = (int1 == 1) ? true : false;
	}

	
}
