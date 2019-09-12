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
import application.model.Partner;
import application.model.Session;
import application.model.Specialist;
import application.undoRedoSystem.Bulk;
import application.undoRedoSystem.Operation;
import application.undoRedoSystem.Redo;

public class RedoData extends Redo{

	public RedoData() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public void makeRedo() {
		if(!hasNext()){
			Database.getMainFXMLController().getMenusController().disableRedoButton(true);
			return;
		}
		Bulk bulk = popBulk();
		if(bulk.checkType() instanceof Child)
			makeRedoChild(bulk);
		else if(bulk.checkType() instanceof Session)
			makeRedoSession(bulk);
		else if(bulk.checkType() instanceof Bill)
			makeRedoBill(bulk);
		else if(bulk.checkType() instanceof Partner)
			makeRedoPartner(bulk);
		else if(bulk.checkType() instanceof Specialist)
			makeRedoSpecialist(bulk);
		if(this.list.isEmpty()){
			Database.getMainFXMLController().getMenusController().disableRedoButton(true);
		}
	}
	public void makeRedoChild(Bulk bulk){
		if(bulk.getOperation() == Operation.DELETE){
			deleteChild(bulk);
			Database.getUndoData().push(bulk);
		}
		else if(bulk.getOperation() == Operation.INSERT){
			insertChild(bulk);
			Database.getUndoData().push(bulk);
		}
		else if(bulk.getOperation() == Operation.UPDATE){
			unpdateChild(bulk);
			Database.getUndoData().pushCurrentBulk();
			Database.getRedoData().pushCurrent(bulk);
		}	
	}

	public void makeRedoSession(Bulk bulk){
		Session ses = (Session)bulk.getSimpleData();
		if(bulk.getOperation() == Operation.DELETE){
			SessionDatabase.deleteSessionUndo(ses.getId());
			ChildDatabase.updateChildDoneSessions(ses.getChildId(), ses.getChildDoneSessions() - 1);
			Database.getUndoData().push(bulk);
			
		}
		else if(bulk.getOperation() == Operation.INSERT){
			SessionDatabase.insertNewSessionUndo(ses);
			Database.getUndoData().push(bulk);
		}
	}
	public void makeRedoBill(Bulk bulk){
		Bill bill = (Bill) bulk.getSimpleData();
		if(bulk.getOperation() == Operation.DELETE){
			BillDatabase.deleteBillUndo(bill.getId());
			//Child ch = (Child) bulk.getSubBulks().get(0).getSimpleData();
			Database.getUndoData().push(bulk);
		}
		else if(bulk.getOperation() == Operation.INSERT){
			BillDatabase.insertNewBillUndo(bill);
			ChildDatabase.updateChildPaidUpMoney(bill);
			Database.getUndoData().push(bulk);
		}
	}
	public void makeRedoPartner(Bulk bulk){
		Partner part = (Partner) bulk.getSimpleData();

		if(bulk.getOperation() == Operation.DELETE){
			PartnerDatabase.deletePartnerUndo(part.getId());
			Database.getUndoData().push(bulk);
			
		}
		else if(bulk.getOperation() == Operation.INSERT){
			PartnerDatabase.insertNewPartnerUndo(part);
			Database.getUndoData().push(bulk);
		}
		else if(bulk.getOperation() == Operation.UPDATE){
			
			PartnerDatabase.updatePartnerUndo(part.getId() , part);
			Database.getUndoData().pushCurrentBulk();
			Database.getRedoData().pushCurrent(bulk);
		}
	}
	public void makeRedoSpecialist(Bulk bulk){
		Specialist sp = (Specialist) bulk.getSimpleData();

		if(bulk.getOperation() == Operation.DELETE){
			SpecialistDatabase.deleteSpecialistUndo(sp.getId());
			Database.getUndoData().push(bulk);
			
		}
		else if(bulk.getOperation() == Operation.INSERT){
			SpecialistDatabase.insertNewSpecialistUndo(sp);
			Database.getUndoData().push(bulk);
		}
		else if(bulk.getOperation() == Operation.UPDATE){
			SpecialistDatabase.updateSpecialistUndo(sp.getId(), sp);
			Database.getUndoData().pushCurrentBulk();
			Database.getRedoData().pushCurrent(bulk);
		}
	}

	public void insertChild(Bulk bulk){
		Child ch = (Child)bulk.getSimpleData();
		ChildDatabase.insertNewChildUndo(ch);
		Bulk b1 = bulk.getSubBulks().get(0);
		if(b1.checkType() instanceof Session)
		{
			ArrayList<Session> ses = (ArrayList<Session>)(ArrayList<?>) b1.getData();
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
