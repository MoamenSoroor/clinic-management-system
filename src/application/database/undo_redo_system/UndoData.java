package application.database.undo_redo_system;

import java.util.ArrayList;

import application.database.BillDatabase;
import application.database.ChildDatabase;
import application.database.Database;
import application.database.PartnerDatabase;
import application.database.SessionDatabase;
import application.database.SpecialistDatabase;
import application.model.Bill;
import application.model.Child;
import application.model.MyModel;
import application.model.Partner;
import application.model.Session;
import application.model.Specialist;
import application.undoRedoSystem.Bulk;
import application.undoRedoSystem.Operation;
import application.undoRedoSystem.Undo;

public class UndoData extends Undo{

	public UndoData() {
		
	}

	@Override
	public void makeUndo() {
		if(!hasNext()){
			Database.getMainFXMLController().getMenusController().disableUndoButton(true);
			return;
		}
		Bulk bulk = popBulk();
		if(bulk.checkType() instanceof Child)
			makeUndoChild(bulk);
		else if(bulk.checkType() instanceof Session)
			makeUndoSession(bulk);
		else if(bulk.checkType() instanceof Bill)
			makeUndoBill(bulk);
		else if(bulk.checkType() instanceof Partner)
			makeUndoPartner(bulk);
		else if(bulk.checkType() instanceof Specialist)
			makeUndoSpecialist(bulk);
		if(this.list.isEmpty()){
			Database.getMainFXMLController().getMenusController().disableUndoButton(true);
		}
	}
	public void makeUndoChild(Bulk bulk){
		if(bulk.getOperation() == Operation.DELETE){
			insertChild(bulk);
			Database.getRedoData().push(bulk);
		}
		else if(bulk.getOperation() == Operation.INSERT){
			deleteChild(bulk);
			Database.getRedoData().push(bulk);
		}
		else if(bulk.getOperation() == Operation.UPDATE){
			unpdateChild(bulk);
			Database.getRedoData().pushCurrentBulk();
			Database.getUndoData().pushCurrent(bulk);
		}	
	}

	public void makeUndoSession(Bulk bulk){
		Session ses = (Session)bulk.getSimpleData();
		if(bulk.getOperation() == Operation.DELETE){
			SessionDatabase.insertNewSessionUndo(ses);
			Database.getRedoData().push(bulk);
		}
		else if(bulk.getOperation() == Operation.INSERT){
			SessionDatabase.deleteSessionUndo(ses.getId());
			ChildDatabase.updateChildDoneSessions(ses.getChildId(), ses.getCh().getDoneSessions() - 1);
			Database.getRedoData().push(bulk);
		}	
	}
	public void makeUndoBill(Bulk bulk){
		Bill bill = (Bill) bulk.getSimpleData();
		if(bulk.getOperation() == Operation.DELETE){
			BillDatabase.insertNewBillUndo(bill);
			Database.getRedoData().push(bulk);
		}
		else if(bulk.getOperation() == Operation.INSERT){
			BillDatabase.deleteBillUndo(bill.getId());
			System.out.println("bill id inside undo = " + bill.getId());
			//Child ch = (Child) bulk.getSubBulks().get(0).getSimpleData();
			ChildDatabase.updateChildPaidUpMoneyMinus(bill);
			Database.getRedoData().push(bulk);
		}
	}
	public void makeUndoPartner(Bulk bulk){
		Partner part = (Partner) bulk.getSimpleData();

		if(bulk.getOperation() == Operation.DELETE){
	
			PartnerDatabase.insertNewPartnerUndo(part);
			Bulk chsBulk = bulk.getSubBulks().get(0);
			ArrayList<Child> chs = (ArrayList<Child>)chsBulk.getData();
			for(Child ch : chs){
				System.out.println("update partner in child");
				ChildDatabase.UpdateChildPartnerUndo(ch);
			}
			Database.getMainFXMLController().refreshTables();
			Database.getRedoData().push(bulk);
		}
		else if(bulk.getOperation() == Operation.INSERT){
			PartnerDatabase.deletePartnerUndo(part.getId());
			Database.getRedoData().push(bulk);
		}
		else if(bulk.getOperation() == Operation.UPDATE){
			PartnerDatabase.updatePartnerUndo(part.getId() , part);
			Database.getRedoData().pushCurrentBulk();
			Database.getUndoData().pushCurrent(bulk);
		}
	}
	public void makeUndoSpecialist(Bulk bulk){
		Specialist sp = (Specialist) bulk.getSimpleData();

		if(bulk.getOperation() == Operation.DELETE){
			SpecialistDatabase.insertNewSpecialistUndo(sp);
			Database.getRedoData().push(bulk);
		}
		else if(bulk.getOperation() == Operation.INSERT){
			SpecialistDatabase.deleteSpecialistUndo(sp.getId());
			Database.getRedoData().push(bulk);
		}
		else if(bulk.getOperation() == Operation.UPDATE){
			SpecialistDatabase.updateSpecialistUndo(sp.getId(), sp);
			Database.getRedoData().pushCurrentBulk();
			Database.getUndoData().pushCurrent(bulk);
		}		
	}

	public void insertChild(Bulk bulk){
		Child ch = (Child)bulk.getSimpleData();
		ChildDatabase.insertNewChildUndo(ch);
		Bulk b1 = bulk.getSubBulks().get(0);
		if(b1.checkType() instanceof Session)
		{
			ArrayList<Session> ses =  (ArrayList<Session>)(ArrayList<?>)b1.getData();
			Bulk b2 = bulk.getSubBulks().get(1);
			ArrayList<Bill> bills = (ArrayList<Bill>)(ArrayList<?>) b2.getData();
			for(Session s : ses){
				SessionDatabase.insertNewSessionWithId(s);
			}
			for(Bill b : bills){
				BillDatabase.insertNewBillWithId(b);
			}

		}
		else{
			ArrayList<Bill> bills = (ArrayList<Bill>)(ArrayList<?>) b1.getData();
			Bulk b2 = bulk.getSubBulks().get(1);
			ArrayList<Session> ses = (ArrayList<Session>)(ArrayList<?>) b2.getData();

			for(Session s : ses){
				SessionDatabase.insertNewSessionWithId(s);
			}
			for(Bill b : bills){
				BillDatabase.insertNewBillWithId(b);
			}
		}
	}

	private void deleteChild(Bulk bulk) {
		Child ch = (Child)bulk.getSimpleData();
		ChildDatabase.deleteChildFresh(ch.getId());
		if(ch.isPart1New()){
			Partner p1 = (Partner)bulk.getSubBulks().get(0).getSimpleData();
			PartnerDatabase.deletePartner(p1.getId());
		}
		if(ch.isPart2New() && ch.isPart1New()){
			Partner p2 = (Partner)bulk.getSubBulks().get(1).getSimpleData();
			PartnerDatabase.deletePartner(p2.getId());
		}
		else if(ch.isPart2New() && !ch.isPart1New()){
			Partner p2 = (Partner)bulk.getSubBulks().get(0).getSimpleData();
			PartnerDatabase.deletePartner(p2.getId());
		}
	}
	private void unpdateChild(Bulk bulk) {
		Child ch = (Child)bulk.getSimpleData();
		ChildDatabase.UpdateChildUndo(ch);
	}

	
		


}
