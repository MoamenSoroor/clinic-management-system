package application.sub;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import application.database.BillDatabase;
import application.database.ChildDatabase;
import application.model.Bill;
import application.model.Child;
import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class ViewBillController {
	String infor = "";
	Bill bill = null;
	@FXML
	private TextArea billInfor;
	@FXML
	private Button cancelButton;
	// Event Listener on Button[#cancelButton].onAction
	@FXML
	public void onCancel(ActionEvent event) {
		Stage stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
	}
	@FXML 
	private void initialize()
	{
		billInfor.setEditable(false);
	}
	public void loadBillInfor(int id)
	{
		bill = BillDatabase.selectBill(id);
		Child ch = ChildDatabase.selectChild(bill.getChild().getId());
			infor = "ãÚáæãÇÊ ÇáİÇÊæÑÉ ÇáÇÓÇÓíÉ :\n"
					+ "==================================\n"
					+ "ÇÓã ÇáØİá : " + bill.getChild().getName()
					+ "\nÇÓã ÇáãÑÇİŞ: " + bill.getPartName()
					+ "\níæã ÇáÏİÚ: " + bill.getDay()
					+ "\nÊÇÑíÎ ÇáÏİÚ: " + bill.getPayDateString()
					+ "\nÊÇÑíÎ ÇáÇäÊåÇÁ: " + bill.getExpiredDateString()
					+ "\nÇáãÏÉ: " + bill.getDuration()

					+ "\n\nãÚáæãÇÊ ÇáİÇÊæÑÉ ÇáãÇáíÉ :\n"
					+ "==================================\n"
					+ "\nÇáãÈáÛ ÇáãÏİæÚ: " + bill.getNewPayment()
					+ "\nÇáãÈáÛ ÇáÈÇŞì ÓÇÈŞÇ: " + bill.getOldRestMoney()
					+ "\nÇáãÈáÛ Çáßáì ÇáÌÏíÏ: " + (bill.getOldRestMoney() + bill.getNewPayment())
					+ "\nÚÏÏ ÇáÌáÓÇÊ ÇáÈÇŞíÉ: " + (bill.getChild().getRestSessions())
					+ "\nÚÏÏ ÇáÌáÓÇÊ ÇáÌÏíÏ: " + ((bill.getOldRestMoney() + bill.getNewPayment())/bill.getOldOneSessionPrice())
					+ "\n\nÇáãÈáÛ Çáßáì ÍÇáíÇ: " + ch.getPaidUpMoney()
					+ "\nÇáãÈáÛ ÇáÈÇŞì ÍÇáíÇ: " + ch.getRestMoney()
					+ "\nÚÏÏ ÇáÌáÓÇÊ ÍÇáíÇ: " + ch.getNumberOfSessions()
					+ "\nÚÏÏ ÇáÌáÓÇÊ ÇáÈÇŞíå ÍÇáíÇ: " + ch.getRestSessions()
					+ "\n==================================\n";
					
			billInfor.setText(infor);
	}
}
