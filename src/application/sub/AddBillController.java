package application.sub;

import javafx.fxml.FXML;

import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

import java.time.LocalDate;

import application.database.BillDatabase;
import application.database.ChildDatabase;
import application.database.PartnerDatabase;
import application.database.PropertiesDataBase;
import application.model.Bill;
import application.model.Child;
import application.model.PaidSystem;
import application.utils.AutoCompleteComboBoxListener;
import application.utils.MUtils;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;

import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ComboBox;

import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.control.DatePicker;

public class AddBillController {
	Bill bill;
	boolean errorFlag = false;
	private DoubleProperty restMoneyLabelP;
	private IntegerProperty maxSessionLabelP;
	private DoubleProperty paidMoneyP;
	private DoubleProperty oneSessionPriceP;
	@FXML
	private DatePicker paidDate;
	@FXML
	private ComboBox<String> childSelect;
	@FXML
	private ComboBox <String> partnerSelect;
	@FXML
	private Label restMoneyLabel;
	@FXML
	private Label AllMonneyLabel;
	@FXML
	private Label maxSessionLabel;
	@FXML
	private ImageView paidUpMoneyError;
	@FXML
	private ImageView childError;
	@FXML
	private ImageView partnerError;
	@FXML
	private ImageView payDayError;
	@FXML
	private ImageView oneSessionPriceError;
	@FXML
	private Button saveButton;
	@FXML
	private Button cancelButton;
	@FXML
	private ToggleGroup paySystem;
	@FXML
	private RadioButton system1;
	@FXML
	private RadioButton system2;
	@FXML
	private RadioButton system3;
	@FXML
	private RadioButton system4;
	@FXML
	private RadioButton system5;
	@FXML
	private RadioButton mainSystem;
	@FXML
	private TextField paidMoney;
	@FXML
	private TextField oneSessionPrice;
	
	private PaidSystem paid;
		// Event Listener on Button[#saveButton].onAction
	@FXML
	public void onSave(ActionEvent event) {
		if(checkIfErrorsExist())
		{
			MUtils.showErrorMessage("ÎØÃ Ýì ÇáÞíã ÇáãÏÎáÉ", "ÇáÞíã ÇáÊì ÞãÊ ÈÇÏÎÇáåÇ ÎØÃ ÇáÑÌÇÁ ãÚÇáÌå ÇáÇÎØÇÁ");
			errorFlag = true;
			return;
		}
		if(!checkConstrainsOk())
		{
			MUtils.showErrorMessage("ÎØÃ Ýì ÇáÞíã ÇáãÏÎáÉ", "ÇáÞíã ÇáÊì ÞãÊ ÈÇÏÎÇáåÇ ÎØÃ ÇáÑÌÇÁ ãÚÇáÌå ÇáÇÎØÇÁ");
			errorFlag = true;
			return;
		}
		Child ch = ChildDatabase.selectChild(childSelect.getValue());
		bill.setChild(ch);
		bill.setPartner(PartnerDatabase.selectPartner(partnerSelect.getValue()));
		bill.setNewPayment(paidMoneyP.getValue());
		bill.setPayDate(paidDate.getValue());
		bill.setNewOneSessionPrice(oneSessionPriceP.getValue());
		BillDatabase.insertNewBill(bill);
		ChildDatabase.updateChildPaidUpMoney(bill);
		errorFlag = false;
		MUtils.notification("ÇÏÎÇá ãÑÇÝÞ", "Êã ÇÏÎÇá ÝÇÊæÑÉ ÈäÌÇÍ\n ÇÓã ÇáÍÇáÉ: " + bill.getChild().getName());
		if(errorFlag == false)
			closeStage();
	}
	
	private void closeStage(){
		Stage stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
	}
	// Event Listener on Button[#cancelButton].onAction
	@FXML
	public void OnCancel(ActionEvent event) {
		Stage stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
	}
	private void loadPaidSystem() {
		paid = PropertiesDataBase.selectPropertiesPaidSystem();
		system1.setText("" + paid.getPaidMoney11());
		system2.setText("" + paid.getPaidMoney22());
		system3.setText("" + paid.getPaidMoney33());
		system4.setText("" + paid.getPaidMoney44());
		system5.setText("" + paid.getPaidMoney55());

		mainSystem.setOnAction(event ->{
			paidMoney.setText("" + bill.getOldPaidUpMoney());
			oneSessionPrice.setText("" + bill.getOldOneSessionPrice());
		});
		system1.setOnAction(event ->{
			paidMoney.setText("" + paid.getPaidMoney11());
			oneSessionPrice.setText("" + paid.getOneSession11());
		});
		system2.setOnAction(event ->{
			paidMoney.setText("" + paid.getPaidMoney22());
			oneSessionPrice.setText("" + paid.getOneSession22());

		});
		system3.setOnAction(event ->{
			paidMoney.setText("" + paid.getPaidMoney33());
			oneSessionPrice.setText("" + paid.getOneSession33());

		});
		system4.setOnAction(event ->{
			paidMoney.setText("" + paid.getPaidMoney44());
			oneSessionPrice.setText("" + paid.getOneSession44());

		});
		system5.setOnAction(event ->{
			paidMoney.setText("" + paid.getPaidMoney55());
			oneSessionPrice.setText("" + paid.getOneSession55());
		});
		
		
	}
	
	@FXML
	private void initialize()
	{
		//init properties
		paidMoneyP = new SimpleDoubleProperty(0);
		restMoneyLabelP = new SimpleDoubleProperty(0);
		maxSessionLabelP = new SimpleIntegerProperty(0);
		oneSessionPriceP = new SimpleDoubleProperty(0);
		//init bill
		bill = new Bill();
		// paidDate;
		// childSelect;
		// partnerSelect;
		// paidExpired;
		// duration;
		// restMoneyLabel;
		// paidMoney;
		// AllMonneyLabel;
		// maxSessionLabel;
		// paidUpMoneyError;
		// durationError;
		// childError;
		// partnerError;
		// payDayError;
		// saveButton;
		// cancelButton;
		initValues();
		loadPaidSystem();
	}
	
	private void initValues()
	{
		paidDate.setValue(LocalDate.now());
		childSelect.setOnKeyPressed(new AutoCompleteComboBoxListener<>(childSelect));
		partnerSelect.setOnKeyPressed(new AutoCompleteComboBoxListener<>(partnerSelect));
		childSelect.getItems().addAll(ChildDatabase.selectAllChildNames());
		if(childSelect.getItems().isEmpty())
		{
			setCombonentsDisable(true);
			return;
		}
		childSelect.setValue(childSelect.getItems().get(0));
		
		Child ch = ChildDatabase.selectChild(childSelect.getValue());
		bill.setChild(ch);
		oneSessionPrice.setText("" + bill.getOldOneSessionPrice());
		paidMoney.setText("" + bill.getOldPaidUpMoney());
		oneSessionPriceP.setValue(Double.parseDouble(oneSessionPrice.getText()));
		paidMoneyP.setValue(Double.parseDouble(paidMoney.getText()));
		loadPartners(ch);
		setListeners();
		loadPayListeners();
		updateListeners();
	}

	public void loadFromOut(int id)
	{
		Child ch = ChildDatabase.selectChild(id);
		childSelect.setValue(ch.getName());
		bill.setChild(ch);
		oneSessionPrice.setText("" + bill.getOldOneSessionPrice());
		paidMoney.setText("" + bill.getOldPaidUpMoney());
		oneSessionPriceP.setValue(Double.parseDouble(oneSessionPrice.getText()));
		paidMoneyP.setValue(Double.parseDouble(paidMoney.getText()));
		loadPartners(ch);
		updateListeners();
	}
	private void setCombonentsDisable(boolean disable)
	{
		
		paidDate.setDisable(disable);
		oneSessionPrice.setDisable(disable);
		partnerSelect.setDisable(disable);
		restMoneyLabel.setDisable(disable);
		paidMoney.setDisable(disable);
		AllMonneyLabel.setDisable(disable);
		maxSessionLabel.setDisable(disable);
		saveButton.setDisable(disable);
	}
	
	private void setListeners()
	{
		childSelect.setOnHidden(event->{
			String value = childSelect.getEditor().getText();
			String select = childSelect.getSelectionModel().getSelectedItem();
			if(value == null || select == null){
				setCombonentsDisable(true);
				childError.setVisible(true);
				return;
				}
			else if(select.isEmpty()|| value.isEmpty())
			{
				setCombonentsDisable(true);
				childError.setVisible(true);
				return;
			}
			else if(!value.equals(select))
			{
				childSelect.getEditor().setText(select);
				childError.setVisible(false);
				setCombonentsDisable(false);

			}
			else {
				setCombonentsDisable(false);
				childError.setVisible(false);
				childSelect.getEditor().setText(select);
			}
			Child ch = ChildDatabase.selectChild(childSelect.getValue());
			bill.setChild(ch);
			oneSessionPrice.setText("" + bill.getOldOneSessionPrice());
			paidMoney.setText("" + bill.getOldPaidUpMoney());
			paySystem.selectToggle(mainSystem);
			oneSessionPriceP.setValue(Double.parseDouble(oneSessionPrice.getText()));
			paidMoneyP.setValue(Double.parseDouble(paidMoney.getText()));
			updateListeners();
			loadPartners(ch);
		});
	}

	private void updateListeners()
	{
		maxSessionLabelP.setValue((paidMoneyP.getValue() + bill.getOldRestMoney())
				/ oneSessionPriceP.getValue());
		restMoneyLabelP.setValue(bill.getOldRestMoney());
	}

	
	
	private void loadPayListeners() {

		// duration;
		// restMoneyLabel;
		// paidMoney;
		// AllMonneyLabel;
		// maxSessionLabel;
		// paidUpMoneyError;
				// durationError;
				// childError;
				// partnerError;
				// payDayError;
		
		
		maxSessionLabel.textProperty().bind(maxSessionLabelP.asString());
		restMoneyLabel.textProperty().bind(restMoneyLabelP.asString());
		AllMonneyLabel.textProperty().bind(paidMoneyP.add(restMoneyLabelP).asString());
		paidMoney.textProperty().addListener(e -> {
			paidUpMoneyError.setVisible(false);
			if (paidMoney.getText().isEmpty()) {
				paidMoneyP.setValue(0);
				return;
			}
			try {
				paidMoneyP.setValue(Double.parseDouble(paidMoney.getText()));
				updateListeners();
			} catch (Exception ex) {
				paidUpMoneyError.setVisible(true);
			}
		});
		oneSessionPrice.textProperty().addListener(e -> {
			oneSessionPriceError.setVisible(false);
			if (oneSessionPrice.getText().isEmpty()) {
				oneSessionPriceP.setValue(0);
				return;
			}
			try {
				oneSessionPriceP.setValue(Double.parseDouble(oneSessionPrice.getText()));
				updateListeners();
			} catch (Exception ex) {
				oneSessionPriceError.setVisible(true);
			}
		});
	}


	private boolean checkIfErrorsExist()
	{
		if( paidUpMoneyError.isVisible() 
				|| childError.isVisible() || partnerError.isVisible() 
				|| payDayError.isVisible()|| oneSessionPriceError.isVisible())
			return true;
		else
			return false;
	}
	

	private boolean checkConstrainsOk()
	{
		if(childSelect.getValue().isEmpty() || paidMoney.getText().isEmpty()
			|| oneSessionPrice.getText().isEmpty())
			return false;
		else return true;
	}
	private void loadPartners(Child ch)
	{
		if(ch.getP1().getId() == 0 && ch.getP2().getId() == 0)
			return;
		else if ((ch.getP1().getId() != 0 && ch.getP2().getId() == 0)){
			partnerSelect.getItems().setAll(ch.getP1().getName());
			partnerSelect.setValue(partnerSelect.getItems().get(0));
		}
		else if ((ch.getP1().getId() == 0 && ch.getP2().getId() != 0)){
			partnerSelect.getItems().setAll(ch.getP2().getName());
			partnerSelect.setValue(partnerSelect.getItems().get(0));
		}
		else
		{
			partnerSelect.getItems().setAll(ch.getP1().getName(), ch.getP2().getName());
			partnerSelect.setValue(partnerSelect.getItems().get(0));
		}
		
	}


}




























