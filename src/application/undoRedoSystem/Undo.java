package application.undoRedoSystem;
import java.util.Stack;

import application.database.Database;

public abstract class Undo{
	// bulk is counter increase when new main bulk is entered
	protected int maxUndo = 30;
	protected int bulkIdCounter = 0;
	protected Stack<Bulk> list;
	public Undo() {
		list = new Stack<>();
		currentBulk = new Stack<>();
	}
	protected Stack<Bulk> currentBulk;
	
	public void pushCurrent(Bulk bulk){
		currentBulk.push(bulk);
	}
	public Bulk popCurrent(){
		if(currentBulk.isEmpty())
			return null;
		return currentBulk.pop();
	}
	public void removeAllCurrent(){
		currentBulk.removeAllElements();
	}
	public void pushCurrentBulk() {
		push(popCurrent());
	}
	public void push(Bulk bulk){
		Database.getMainFXMLController().getMenusController().disableUndoButton(false);
		Database.getMainFXMLController().getMenusController().disableSaveButton(false);
		if(bulk.isMain()){
			bulk.setBulkId(++bulkIdCounter);
			this.list.add(bulk);
		}
		else {
			list.peek().getSubBulks().add(bulk);
		}
	}
	public Bulk popBulk(){
		if(list.size() == 0){
			Database.getMainFXMLController().getMenusController().disableUndoButton(true);
			return null;
		}
		return list.pop();
	}
	public boolean hasNext(){
		if(list.size() != 0)
			return true;
		else return false;
	}
	public abstract void makeUndo();
	
	public void removeAll(){
		list.removeAllElements();
		currentBulk.removeAllElements();
	}
	public int getMaxUndo() {
		return maxUndo;
	}
	public void setMaxUndo(int maxUndo) {
		this.maxUndo = maxUndo;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}















