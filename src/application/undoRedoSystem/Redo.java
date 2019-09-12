package application.undoRedoSystem;

import java.util.Stack;

import application.database.Database;

public abstract class Redo  {
	// bulk is counter increase when new main bulk is entered
	protected Stack<Bulk> list;
	public Redo() {
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
	
	public void push(Bulk bulk){
		Database.getMainFXMLController().getMenusController().disableRedoButton(false);
		Database.getMainFXMLController().getMenusController().disableSaveButton(false);
		list.add(bulk);

	}
	public Bulk popBulk(){
		if(list.size() == 0){
			Database.getMainFXMLController().getMenusController().disableRedoButton(true);
			return null;
		}
		return list.pop();
	}

	public void pushCurrentBulk(){
		push(popCurrent());
	}
	public boolean hasNext(){
		if(list.size() != 0)
			return true;
		else return false;
	}
		
	public abstract void makeRedo();
	
	public void removeAll(){
		list.removeAllElements();
		currentBulk.removeAllElements();
	}
}
