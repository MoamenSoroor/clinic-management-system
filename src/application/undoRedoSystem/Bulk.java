package application.undoRedoSystem;

import java.util.ArrayList;
import application.model.MyModel;

public class Bulk {

	private boolean isSimple = true;
	private int size;
	private int bulkId;
	private boolean isMain = false;
	private ArrayList<MyModel> data;
	private ArrayList<Bulk> bigData;
	private MyModel model;
	private Operation op;
	private ArrayList<Bulk> subBulks;
	public Bulk() {
		isSimple = true;
		size = 0;
		bulkId = 0;
		data = new ArrayList<>();
		subBulks = new ArrayList<>();
		op = Operation.NONE;
		isMain = false;
	}
	public Bulk(ArrayList<Bulk> bulks){
		bigData = new ArrayList<>(bulks);
	}
	public ArrayList<Bulk> getBigData(){
		return bigData;
	}
	public Bulk(ArrayList<? extends MyModel> data , boolean isMainData, Operation oper) {
		this.isMain = isMainData;
		this.data = new ArrayList<>();
		this.data.addAll(data);
		if(!data.isEmpty())
			this.model = data.get(0);
		else
			this.model = new EmptyModel();
		subBulks = new ArrayList<>();
		size = data.size();
		this.op = oper;
		isSimple = false;
	}
	public Bulk(MyModel model , boolean isMainData, Operation oper) {
		this.isMain = isMainData;
		this.data = new ArrayList<>();
		data.add(model);
		subBulks = new ArrayList<>();
		this.size = 1;
		this.op = oper;
		this.model = model;
		isSimple = true;
	}
	public ArrayList<Bulk> getSubBulks(){
		return subBulks;
	}
	public void setSubBulks(ArrayList<Bulk> l){
		subBulks = new ArrayList<>(l);
	}
	public void setBulk(ArrayList<? extends MyModel> data , boolean isMainData, Operation oper) {
		this.isMain = isMainData;
		this.data = new ArrayList<>();
		this.data.addAll(data);
		this.size = data.size();
		this.op = oper;
		this.isSimple = false;
	}
	public void setBulk(MyModel data , boolean isMainData, Operation oper) {
		this.isMain = isMainData;
		this.size = 1;
		model = data;
		this.op = oper;
	}
	public void setBulkToNull(){
		size = 0;
		bulkId = 0;
		data = null;
		op = Operation.NONE;
	}
	
	public int getSize() {
		return size;
	}
	public int getBulkId() {
		return bulkId;
	}
	public void setBulkId(int id){
		bulkId = id;
	}
	public ArrayList<? extends MyModel> getData() {
			return data;

	}
	public MyModel getSimpleData() {
		return model;

}
	public ArrayList<? extends MyModel>  copyData() {
			ArrayList<MyModel> data2 = new ArrayList<>();
			data2.addAll(data);
			return data2;
	}
	public ArrayList<? extends MyModel>  copyDataReversed() {
		ArrayList<MyModel> data2 = new ArrayList<>();
		int counter = data.size() -1;
		while(counter >= 0){
			data2.add(data.get(counter));
		}
		return data2;
	}
	public Operation getOperation(){
		return  op;
	}
	public boolean isMain() {
		return isMain;
	}
	public boolean isSimple() {
		return isSimple;
	}
	public void setSimple(boolean isSimple) {
		this.isSimple = isSimple;
	}
	public MyModel checkType() {
		return model;
	}
	

}
