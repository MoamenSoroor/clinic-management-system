package application.sub;

import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

import java.io.File;
import java.net.MalformedURLException;
import java.time.LocalDate;
import java.util.ArrayList;

import org.controlsfx.control.textfield.TextFields;

import application.database.ChildDatabase;
import application.database.PartnerDatabase;
import application.database.PropertiesDataBase;
import application.database.StatusDatabase;
import application.model.Child;
import application.model.PaidSystem;
import application.model.Partner;
import application.utils.AutoCompleteComboBoxListener;
import application.utils.MUtils;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;

import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.DatePicker;

public class AddChildController {
	boolean errorFlag = false;
	boolean editMode = false;
	File filePhoto;
	int childId;
	Child ch;
	static String dir= "c://";
	DoubleProperty paidMoneyP;
	DoubleProperty oneSessionPriceP;
	IntegerProperty pastSessionsP;
	IntegerProperty maxSessionsP;
	StringProperty ChildNameP;
	LocalDate ChildBirthP;
	StringProperty statusP;
	StringProperty part1NameP;
	LongProperty part1phone1P;
	LongProperty part1Phone2P;
	LongProperty part1groundPhoneP;
	StringProperty part1AddressP;
	StringProperty part2NameP;
	LongProperty part2phone1P;
	LongProperty part2Phone2P;
	ArrayList<String> extensions;

	@FXML
	private ComboBox<String> selectOldPartner;
	@FXML
	private ImageView selectOldPartnerError;
	@FXML
	private ComboBox<String> selectOldPartner1;
	@FXML
	private ImageView selectOldPartnerError1;
	@FXML
	private TextField childName;
	@FXML
	private DatePicker ChildBirth;
	@FXML
	private TextField status;
	@FXML
	private RadioButton female;
	@FXML
	private RadioButton male;
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
	private ToggleGroup maleFemale;
	@FXML
	private ImageView childNameError;
	@FXML
	private ImageView birthDayError;
	@FXML
	private ImageView statusError;
	@FXML
	private TextField part1Name;
	@FXML
	private TextField part1phone1;
	@FXML
	private TextField part1Phone2;
	@FXML
	private ImageView part1Phone2Error;
	@FXML
	private ImageView part1Phone1Error;
	@FXML
	private ImageView part1NameError;
	@FXML
	private TextField part1groundPhone;
	@FXML
	private ImageView part1GroundPhoneError;
	@FXML
	private TextField part1Address;
	@FXML
	private ImageView part1AddressError;
	@FXML
	private TextField part2Phone2;
	@FXML
	private ImageView part2Phone2Error;
	@FXML
	private TextField part2Name;
	@FXML
	private ImageView part2NameError;
	@FXML
	private TextField part2Phone1;
	@FXML
	private ImageView part2PhoneError;
	@FXML
	private Button saveButton;
	@FXML
	private ImageView photo;
	@FXML
	private Button loadPhoto;
	@FXML
	private Button cancelButton;
	@FXML
	private TextField paidMoney;
	@FXML
	private TextField oneSessionPrice;
	@FXML
	private TextField doneSessions;
	@FXML
	private Label maxSessions;
	@FXML
	private ImageView paidMoneyError;
	@FXML
	private ImageView oneSessionPriceError;
	@FXML
	private ImageView doneSessionsError;

	@FXML
	private CheckBox cancelPartner1;
	@FXML
	private CheckBox cancelPartner2;
	@FXML
	private CheckBox oldPartnerCheck;
	@FXML
	private CheckBox oldPartnerCheck1;

	private PaidSystem paid;
	
	private void closeStage(){
		Stage stage = (Stage) saveButton.getScene().getWindow();
		stage.close();
	}
	@FXML
	private void onCancelPartner1() {
		if (cancelPartner1.isSelected())
			disablePartner1(true);
		else {
			disablePartner1(false);
			oldPartnerCheck.setSelected(false);
			selectOldPartner.setDisable(true);
			
		}
	}

	private void loadPaidSystem() {
		paid = PropertiesDataBase.selectPropertiesPaidSystem();
		system1.setText("" + paid.getPaidMoney11());
		system2.setText("" + paid.getPaidMoney22());
		system3.setText("" + paid.getPaidMoney33());
		system4.setText("" + paid.getPaidMoney44());
		system5.setText("" + paid.getPaidMoney55());

		paidMoney.setText("" + paid.getPaidMoney11());
		oneSessionPrice.setText("" + paid.getOneSession11());
		doneSessions.setText("0");
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
	
	private void loadPaidSystemEditMode() {
		paid = PropertiesDataBase.selectPropertiesPaidSystem();
		system1.setText("" + paid.getPaidMoney11());
		system2.setText("" + paid.getPaidMoney22());
		system3.setText("" + paid.getPaidMoney33());
		system4.setText("" + paid.getPaidMoney44());
		system5.setText("" + paid.getPaidMoney55());

		paidMoney.setText("" + paid.getPaidMoney11());
		oneSessionPrice.setText("" + paid.getOneSession11());
		doneSessions.setText("0");
		system1.setOnAction(event ->{
			oneSessionPrice.setText("" + paid.getOneSession11());


		});
		system2.setOnAction(event ->{
			oneSessionPrice.setText("" + paid.getOneSession22());

		});
		system3.setOnAction(event ->{
			oneSessionPrice.setText("" + paid.getOneSession33());

		});
		system4.setOnAction(event ->{
			oneSessionPrice.setText("" + paid.getOneSession44());

		});
		system5.setOnAction(event ->{
			oneSessionPrice.setText("" + paid.getOneSession55());
		});
		
		
	}
	
	@FXML
	private void onCancelPartner2() {
		if (cancelPartner2.isSelected())
			disablePartner2(true);
		else {
			disablePartner2(false);
			oldPartnerCheck1.setSelected(false);
			selectOldPartner1.setDisable(true);
		}
	}

	@FXML
	private void onOldPartnerCheck() {
		if (oldPartnerCheck.isSelected()) {
			disablePartner1(true);
			cancelPartner1.setDisable(true);
			selectOldPartner.setDisable(false);
			selectOldPartner.setValue(selectOldPartner.getItems().get(0));
		} else {
			cancelPartner1.setDisable(false);
			cancelPartner1.setSelected(false);
			disablePartner1(false);
			selectOldPartner.setDisable(true);
			selectOldPartnerError.setVisible(false);

		}
	}

	@FXML
	private void onOldPartnerCheck1() {
		if (oldPartnerCheck1.isSelected()) {
			disablePartner2(true);
			cancelPartner2.setDisable(true);
			selectOldPartner1.setDisable(false);
			selectOldPartner1.setValue(selectOldPartner1.getItems().get(0));

		} else {
			cancelPartner2.setDisable(false);
			cancelPartner2.setSelected(false);
			disablePartner2(false);
			selectOldPartner1.setDisable(true);
			selectOldPartnerError1.setVisible(false);

		}
	}

	// Event Listener on Button[#saveButton].onAction
	@FXML
	public void onSave() {
		if (editMode == true) {
			
			onSaveEditMode();
			if(errorFlag != true)
				closeStage();
			return;
		}
		if (!ConstrainsIsOk() || checkIfErrorsExists()) {
			MUtils.showErrorMessage("Œÿ√ ›Ï «·„œŒ·«  ", "ÌÊÃœ Œÿ√ ›Ï «·„œŒ·«  «·—Ã«¡ „⁄«·ÃÂ «·«Œÿ«¡");
			errorFlag = true;
			return;
		}
		if (checkIfChildNameExist(ChildNameP.getValue())) {
			MUtils.showErrorMessage("Œÿ√  ﬂ—«— «”„ ", "«”„ «·Õ«·Â «·–Ï ﬁ„  »«œŒ«·… „ﬂ——");
			errorFlag = true;
			return;
		}
		if (!part1NameP.getValue().isEmpty()) {
			if (checkIfPartnerNameExist(part1NameP.getValue())) {
				MUtils.showErrorMessage("Œÿ√  ﬂ—«— «”„ ", "«”„ «·„—«›ﬁ «·«Ê· «·–Ï ﬁ„  »«œŒ«·… „ﬂ——");
				errorFlag = true;
				return;
			}
		}
		if (!part2NameP.getValue().isEmpty()) {
			if (checkIfPartnerNameExist(part2NameP.getValue())) {
				MUtils.showErrorMessage("Œÿ√  ﬂ—«— «”„ ", "«”„ «·„—«›ﬁ  «·À«‰Ï «·–Ï ﬁ„  »«œŒ«·… „ﬂ——");	
				errorFlag = true;
				return;
			}
		}
		if(part1NameP.getValue().equals(part2NameP.getValue()) && !part1NameP.getValue().isEmpty())
		{
			MUtils.showErrorMessage("Œÿ√  ﬂ—«— «”„ ", "«”„ «·„—«›ﬁ  «·À«‰Ï «·–Ï ﬁ„  »«œŒ«·… „ﬂ——");	
			errorFlag = true;
			return;
		}
		try {
			ch.setName(ChildNameP.getValue());
			ch.setBirth(ChildBirthP);
			ch.setStatus(statusP.getValue());
			ch.setGender(male.isSelected() ? true : false);
			ch.setDoneSessions(pastSessionsP.getValue());
			ch.setPaidUpMoney(paidMoneyP.getValue());
			ch.setOneSessionPrice(oneSessionPriceP.getValue());
			if (oldPartnerCheck.isSelected() && selectOldPartner.getValue() != null) {
				ch.setP1(PartnerDatabase.selectPartner(selectOldPartner.getValue()));
			}
			if (oldPartnerCheck1.isSelected()&& selectOldPartner1.getValue() != null) {
				ch.setP2(PartnerDatabase.selectPartner(selectOldPartner1.getValue()));

			}
			if (!cancelPartner1.isSelected() && !cancelPartner1.isDisable()) {
				if (!part1NameP.getValue().isEmpty()) {
					Partner p1 = new Partner(part1NameP.getValue(), part1groundPhoneP.getValue(),
							part1phone1P.getValue(), part1Phone2P.getValue(), part1Address.getText());
					p1.setNew(true);
					PartnerDatabase.insertNewPartnerWithChild(p1);
					ch.setP1(PartnerDatabase.selectPartner(p1.getName()));
					ch.getP1().setNew(true);
					ch.setPart1New(true);
				}
			}

			if (!cancelPartner2.isSelected() &&  !cancelPartner2.isDisable()) {
				if (!part2NameP.getValue().isEmpty()) {
					Partner p2 = new Partner(part2NameP.getValue(), part2phone1P.getValue(),
							part2Phone2P.getValue());
					p2.setNew(true);
					PartnerDatabase.insertNewPartnerWithChild(p2);
					ch.setP2(PartnerDatabase.selectPartner(p2.getName()));
					ch.getP2().setNew(true);
					ch.setPart2New(true);

				}
			}
			ch.setPhoto(filePhoto);
			ChildDatabase.insertNewChild(ch);
			if(!StatusDatabase.checkIfStatusExist(ch.getStatus()) && !ch.getStatus().isEmpty()){
				StatusDatabase.insertNewStatus(ch.getStatus());}
			MUtils.notification("«œŒ«· Õ«·Â", " „ «œŒ«· Õ«·Â »‰Ã«Õ\n «”„ «·Õ«·Â: " + ch.getName());
			errorFlag = false;
		} catch (Exception ex) {
			MUtils.showErrorMessage("Œÿ√ ›Ï «·«œŒ«·", "«·ﬁÌ„ «·‰Ï ﬁ„  »«œŒ«·Â« Œ«ÿ∆Â «·—Ã«¡ „⁄«·ÃÂ «·«Œÿ«¡");
			errorFlag = true;
		}
		if(errorFlag != true)
			closeStage();
	}

	public void onSaveEditMode() {
		
		if (ConstrainsIsOk() && !checkIfErrorsExists()) {
			if(ChildDatabase.checkNameIfExistEdit(ch.getName(), ChildNameP.getValue())){
				MUtils.showErrorMessage("Œÿ√  ﬂ—«— «”„ ", "«”„ «·Õ«·Â «·–Ï ﬁ„  »«œŒ«·… „ﬂ——");
				errorFlag = true;
				return;
			}
			try {
				ch.setName(ChildNameP.getValue());
				ch.setBirth(ChildBirthP);
				ch.setStatus(statusP.getValue());
				if(male.isSelected())
					ch.setGender(true);
				else ch.setGender(false);
				ch.setDoneSessions(pastSessionsP.getValue());
				ch.setPaidUpMoney(paidMoneyP.getValue());
				ch.setOneSessionPrice(oneSessionPriceP.getValue());
				ch.setP1(PartnerDatabase.selectPartner(selectOldPartner.getValue()));
				ch.setP2(PartnerDatabase.selectPartner(selectOldPartner1.getValue()));
				ChildDatabase.UpdateChild(ch);
				if(!StatusDatabase.checkIfStatusExist(ch.getStatus()) && !ch.getStatus().isEmpty()){
					StatusDatabase.insertNewStatus(ch.getStatus());
				}
				errorFlag = false;
				MUtils.notification(" ÕœÌÀ „⁄·Ê„«  Õ«·Â ", " „  ⁄œÌ· „⁄·Ê„«  «·Õ«·… »‰Ã«Õ\n «”„ «·Õ«·Â: " + ch.getName());
			} catch (Exception ex) {
				MUtils.showErrorMessage("Œÿ√ ›Ï «·«œŒ«·", "«·ﬁÌ„ «· Ï ﬁ„  »«œŒ«·Â« Œ«ÿ∆Â «·—Ã«¡ „⁄«·ÃÂ «·«Œÿ«¡");
				errorFlag = true;
			}
		}
		else{
			MUtils.showErrorMessage("Œÿ√ ›Ï «·„œŒ·«  ", "ÌÊÃœ Œÿ√ ›Ï «·„œŒ·«  «·—Ã«¡ „⁄«·ÃÂ «·«Œÿ«¡");
			errorFlag = true;
			return;
		}
	}

	private boolean checkIfPartnerNameExist(String name) {
		if (PartnerDatabase.checkIfNameExist(name))
			return true;
		else
			return false;
	}

	private boolean checkIfChildNameExist(String name) {
		if (ChildDatabase.checkNameIfExist(name))
			return true;
		else
			return false;
	}

	// Event Listener on Button.onAction
	@FXML
	public void OnCancel() {
		Stage stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();

	}

	@FXML
	public void onLoadPhoto() {
		
		FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Image Files", extensions);
		FileChooser chooser = new FileChooser();
		chooser.getExtensionFilters().add(filter);
		chooser.setTitle("select Photo");
		chooser.setInitialDirectory(new File(dir));
		File file = chooser.showOpenDialog(null);
		if (file == null)
			return;

		dir = file.getParent();
		ch.setPhoto(file);
		filePhoto = file;
		try {
			photo.setImage(new Image(file.toURI().toURL().toExternalForm()));
			getClass();
		} catch (MalformedURLException e) {
			MUtils.showErrorMessage("Œÿ√ ≈Œ Ì«—", "«·„·› «·–Ï ﬁ„  »≈Œ Ì«—Â ·« Ì’·Õ «‰ ÌﬂÊ‰ ’Ê—Â");
		}
	}

	@FXML
	private void initialize() {
		
		ch = new Child();
		
		LoadImage();
		loadListeners();
		loadRestListener();
		loadOldPartner();
		loadOldPartnerListener();
		String [] names = StatusDatabase.selectAllStatusArray();
		TextFields.bindAutoCompletion(status,names);
		// set default vaules for test
//		childName.setText("„ƒ„‰ „Õ„œ Ã„«· „Õ„œ ”—Ê—");
//		ChildBirth.setValue(LocalDate.of(1996, 1, 22));
//		status.setText("„—Ì÷");
		//oneSessionPrice.setText("20");
		//doneSessions.setText("0");
		//paidMoney.setText("200");
		loadPaidSystem();
		
	}

	
	private void loadOldPartner() {
		selectOldPartner.setDisable(true);
		selectOldPartner1.setDisable(true);

		oldPartnerCheck.setSelected(false);
		oldPartnerCheck1.setSelected(false);

		selectOldPartner.setItems(PartnerDatabase.selectAllPartnerNames());

		selectOldPartner1.setItems(PartnerDatabase.selectAllPartnerNames());

		if(!selectOldPartner.getItems().isEmpty())
		{
			selectOldPartner.setOnKeyReleased(new AutoCompleteComboBoxListener<String>(selectOldPartner));
			selectOldPartner1.setOnKeyReleased(new AutoCompleteComboBoxListener<String>(selectOldPartner1));
			selectOldPartner.getSelectionModel().select(0);
			selectOldPartner1.getSelectionModel().select(0);
			selectOldPartner.getEditor().setText(selectOldPartner.getValue());
			selectOldPartner1.getEditor().setText(selectOldPartner1.getValue());


		}
		else
		{
			selectOldPartner.getEditor().setText("·« ÌÊÃœ „—«›ﬁÌ‰");
			selectOldPartner1.getEditor().setText("·« ÌÊÃœ „—«›ﬁÌ‰");
			selectOldPartner.setValue(null);
			selectOldPartner1.setValue(null);
			selectOldPartnerError.setVisible(false);
			selectOldPartnerError1.setVisible(false);
			selectOldPartner.setDisable(true);
			selectOldPartner1.setDisable(true);
			oldPartnerCheck.setDisable(true);
			oldPartnerCheck1.setDisable(true);
		}
	}

	private void loadOldPartnerListener() {
		selectOldPartner.setOnHidden(event -> {
			String value = selectOldPartner.getEditor().getText();
			String select = selectOldPartner.getSelectionModel().getSelectedItem();

			if(select == null && value == null){
				selectOldPartnerError.setVisible(false);
				return;
			} else if(value.isEmpty()){
				selectOldPartnerError.setVisible(false);
				selectOldPartner.setValue(null);
					return;
			}
			else if(!value.isEmpty()){
				if(value.equals(select)){
					selectOldPartnerError.setVisible(false);
					return;
				}
				else{
					selectOldPartnerError.setVisible(true);
					selectOldPartner.setValue(null);
				}
			}
			if(editMode == true)
			{
				setFieldsWithSelectedPartner(PartnerDatabase.selectPartner(selectOldPartner.getValue()));
			}
		});
		selectOldPartner1.setOnHidden(event -> {
			String value = selectOldPartner1.getEditor().getText();
			String select = selectOldPartner1.getSelectionModel().getSelectedItem();
			if(select == null && value == null){
				selectOldPartnerError1.setVisible(false);
				return;
			} else if(value.isEmpty()){
				selectOldPartnerError1.setVisible(false);
				selectOldPartner1.setValue(null);
					return;
			}
			else if(!value.isEmpty()){
				if(value.equals(select)){
					selectOldPartnerError1.setVisible(false);
					return;
				}
				else{
					selectOldPartnerError1.setVisible(true);
					selectOldPartner1.setValue(null);
				}
			}

			if(editMode == true)
			{
				setFieldsWithSelectedPartner1(PartnerDatabase.selectPartner(selectOldPartner1.getValue()));
			}
		});
	}
	
	private void loadListeners() {

		paidMoneyP = new SimpleDoubleProperty(0);
		oneSessionPriceP = new SimpleDoubleProperty(1);
		pastSessionsP = new SimpleIntegerProperty(0);
		maxSessionsP = new SimpleIntegerProperty(0);
		maxSessions.textProperty().bind(maxSessionsP.asString());
		maxSessionsP.setValue(paidMoneyP.getValue() / oneSessionPriceP.getValue() - pastSessionsP.getValue());
		paidMoney.textProperty().addListener(e -> {
			paidMoneyError.setVisible(false);
			if (paidMoney.getText().isEmpty()) {
				paidMoneyP.setValue(0);
				return;
			}
			try {
				paidMoneyP.setValue(Double.parseDouble(paidMoney.getText()));
				maxSessionsP.setValue((paidMoneyP.getValue() / oneSessionPriceP.getValue()) - pastSessionsP.getValue());
			} catch (Exception ex) {
				paidMoneyError.setVisible(true);
			}
		});

		oneSessionPrice.textProperty().addListener(e -> {
			oneSessionPriceError.setVisible(false);
			if (oneSessionPrice.getText().isEmpty()) {
				oneSessionPriceP.setValue(1);
				return;
			}
			try {
				oneSessionPriceP.setValue(Double.parseDouble(oneSessionPrice.getText()));
				maxSessionsP.setValue((paidMoneyP.getValue() / oneSessionPriceP.getValue()) - pastSessionsP.getValue());
			} catch (Exception ex) {
				oneSessionPriceError.setVisible(true);
			}
		});

		doneSessions.textProperty().addListener(e -> {
			doneSessionsError.setVisible(false);
			if (doneSessions.getText().isEmpty()) {
				pastSessionsP.setValue(0);
				doneSessionsError.setVisible(false);
				return;
			}
			try {
				pastSessionsP.setValue(Integer.parseInt(doneSessions.getText()));
				maxSessionsP.setValue((paidMoneyP.getValue() / oneSessionPriceP.getValue()) - pastSessionsP.getValue());
			} catch (Exception ex) {
				doneSessionsError.setVisible(true);
				pastSessionsP.setValue(0);
			}
		});
	}

	private void loadRestListener() {
		ChildNameP = new SimpleStringProperty("");
		ChildBirthP = LocalDate.of(2006, 1, 1);
		statusP = new SimpleStringProperty("");
		part1NameP = new SimpleStringProperty("");
		part1phone1P = new SimpleLongProperty(0);
		part1Phone2P = new SimpleLongProperty(0);
		part1groundPhoneP = new SimpleLongProperty(0);
		part1AddressP = new SimpleStringProperty("");
		part2NameP = new SimpleStringProperty("");
		part2phone1P = new SimpleLongProperty(0);
		part2Phone2P = new SimpleLongProperty(0);

		childName.textProperty().addListener(event -> {
			String str = childName.getText().trim();
			childNameError.setVisible(false);
			if (str.isEmpty()) {
				ChildNameP.setValue("");
				return;
			} else if (MUtils.IsString(str)) {
				ChildNameP.setValue(str);
			} else {
				childNameError.setVisible(true);
			}

		});
		part1Name.textProperty().addListener(event -> {
			String str = part1Name.getText().trim();
			part1NameError.setVisible(false);
			if (str.isEmpty()) {
				part1NameP.setValue("");
				return;
			}

			else if (MUtils.IsString(str)) {
				part1NameP.setValue(str);
			} else {
				part1NameError.setVisible(true);
			}

		});
		part2Name.textProperty().addListener(event -> {
			String str = part2Name.getText().trim();
			part2NameError.setVisible(false);
			if (str.isEmpty()) {
				part2NameP.setValue("");
				return;
			} else if (MUtils.IsString(str)) {
				part2NameP.setValue(str);
			} else {
				part2NameError.setVisible(true);
			}

		});

		status.textProperty().addListener(event -> {
			String str = status.getText().trim();
			statusError.setVisible(false);
			if (str.isEmpty()) {
				statusP.setValue("");
				return;
			} else if (MUtils.IsString(str)) {
				statusP.setValue(str);
			} else {
				statusError.setVisible(true);
			}

		});

		ChildBirth.setOnAction(event -> {
			ChildBirthP = ChildBirth.getValue();
			if (!ChildBirthP.isBefore(LocalDate.now()))
				birthDayError.setVisible(true);
			else
				birthDayError.setVisible(false);

		});

		// phones
		part1groundPhone.textProperty().addListener(event -> {
			String str = part1groundPhone.getText().trim();

			part1GroundPhoneError.setVisible(false);
			if (str.isEmpty()) {
				part1groundPhoneP.setValue(0);
				return;
			}
			try {
				part1groundPhoneP.setValue(Long.parseLong(str));

			} catch (Exception ex) {
				part1GroundPhoneError.setVisible(true);
			}

		});
		part1phone1.textProperty().addListener(event -> {
			String str = part1phone1.getText().trim();

			part1Phone1Error.setVisible(false);
			if (str.isEmpty()) {
				part1phone1P.setValue(0);
				return;
			}
			try {
				part1phone1P.setValue(Long.parseLong(str));

			} catch (Exception ex) {
				part1Phone1Error.setVisible(true);
			}

		});
		part1Phone2.textProperty().addListener(event -> {
			String str = part1Phone2.getText().trim();

			part1Phone2Error.setVisible(false);
			if (str.isEmpty()) {
				part1Phone2P.setValue(0);
				return;
			}
			try {
				part1Phone2P.setValue(Long.parseLong(str));

			} catch (Exception ex) {
				part1Phone2Error.setVisible(true);
			}

		});
		part2Phone1.textProperty().addListener(event -> {
			String str = part2Phone1.getText().trim();

			part2PhoneError.setVisible(false);
			if (str.isEmpty()) {
				part2phone1P.setValue(0);
				return;
			}
			try {
				part2phone1P.setValue(Long.parseLong(str));

			} catch (Exception ex) {
				part2PhoneError.setVisible(true);
			}

		});
		part2Phone2.textProperty().addListener(event -> {
			String str = part2Phone2.getText().trim();

			part2Phone2Error.setVisible(false);
			if (str.isEmpty()) {
				part2Phone2P.setValue(0);
				return;
			}
			try {
				part2Phone2P.setValue(Long.parseLong(str));

			} catch (Exception ex) {
				part2Phone2Error.setVisible(true);
			}

		});

	}

	private void LoadImage() {
		extensions = new ArrayList<>();
		extensions.add("*.GIF");
		extensions.add("*.JPG");
		extensions.add("*.TIF");
		extensions.add("*.PNG");
		//URL url = getClass().getResource("/Clinic/src/application/sub/boy.png");
		filePhoto = new File("src/application/icons/boy.png");
	}

	private boolean ConstrainsIsOk() {
		/*
		 * paidMoneyP; oneSessionPriceP; pastSessionsP; maxSessionsP;
		 * ChildNameP; ChildBirthP; statusP; part1NameP; part1phone1P;
		 * part1Phone2P; part1groundPhoneP; part1AddressP; part2NameP;
		 * part2phone1P; part2Phone2P;
		 */
		if (childName.getText().isEmpty() || paidMoney.getText().isEmpty()
				|| oneSessionPrice.getText().isEmpty() || doneSessions.getText().isEmpty()) {
			return false;
		}
		else if (ChildNameP.getValue().isEmpty() || paidMoneyP.getValue() < 0.0
				|| oneSessionPriceP.getValue() < 0.0) {
			
			return false;
		}
		else {
			
			return true;

		}

	}

	private boolean checkIfErrorsExists() {
		if (paidMoneyError.isVisible() || oneSessionPriceError.isVisible() || doneSessionsError.isVisible()
				|| childNameError.isVisible() || birthDayError.isVisible() || statusError.isVisible()
				|| part1NameError.isVisible() || part2NameError.isVisible() || part1Phone1Error.isVisible()
				|| part1Phone2Error.isVisible() || part1GroundPhoneError.isVisible() || part1AddressError.isVisible()
				|| part2PhoneError.isVisible() || part2Phone2Error.isVisible() || selectOldPartnerError.isVisible()
				|| selectOldPartnerError1.isVisible()) {
			
			return true;
		} else {
			
			return false;
		}
	}

	private void disablePartner1(boolean dis) {
		part1Name.setDisable(dis);
		part1phone1.setDisable(dis);
		part1Phone2.setDisable(dis);
		part1groundPhone.setDisable(dis);
		part1Address.setDisable(dis);
		if(dis == true)
		{
			part1Name.setText("");
			part1phone1.setText("");
			part1Phone2.setText("");
			part1groundPhone.setText("");
			part1Address.setText("");
			part1NameError.setVisible(false);
			part1Phone1Error.setDisable(false);
			part1Phone2Error.setVisible(false);
			part1GroundPhoneError.setVisible(false);
			part1AddressError.setVisible(false);
		}
	}

	private void disablePartner2(boolean dis) {
		part2Name.setDisable(dis);
		part2Phone1.setDisable(dis);
		part2Phone2.setDisable(dis);
		if(dis == true)
		{
			part2Name.setText("");
			part2Phone1.setText("");
			part2Phone2.setText("");
			part2NameError.setVisible(false);
			part2PhoneError.setDisable(false);
			part2Phone2Error.setVisible(false);
		}
	}

	public void setEditMode(int childId) {
		editMode = true;
		this.childId = childId;
		editModeInit();
	}
	public void editModeInit()
	{
		loadPaidSystemEditMode();
		ch = ChildDatabase.selectChild(childId);
		loadEditCombonents();
		
		Image image = new Image("file:" + ch.getPhoto().getAbsolutePath());
		photo.setImage(image);
		File file = ch.getPhoto();
		ch.setOldPhoto(file);
	}
	public void loadEditCombonents() {
		childName.setText(ch.getName());
		ChildBirth.setValue(ch.getBirthLocalDate());
		status.setText(ch.getStatus());
		if(ch.getGender())
			maleFemale.selectToggle(male);
		else
			maleFemale.selectToggle(female);

		part1Name.setText(ch.getP1().getName());
		if(ch.getP1().getName() == "0")
			part1Name.setText("");
		part1phone1.setText("" + ch.getP1().getPhone1());
		part1Phone2.setText("" + ch.getP1().getPhone2());
		part1groundPhone.setText("" + ch.getP1().getGroundPhone());
		part1Address.setText("" + ch.getP1().getAddress());

		part2Name.setText(ch.getP2().getName());
		if(ch.getP2().getName() == "0")
			part2Name.setText("");
		part2Phone1.setText("" + ch.getP2().getPhone1());
		part2Phone2.setText("" + ch.getP2().getPhone2());

		paidMoney.setText("" + ch.getPaidUpMoney());
		oneSessionPrice.setText("" + ch.getOneSessionPrice());
		doneSessions.setText("" + ch.getDoneSessions());
		disablePartner1(true);
		disablePartner2(true);
		cancelPartner1.setDisable(true);
		cancelPartner2.setDisable(true);
		oldPartnerCheck.setDisable(true);
		oldPartnerCheck1.setDisable(true);
		if(!selectOldPartner.getItems().isEmpty())
		{
			selectOldPartner.setDisable(false);
			selectOldPartner1.setDisable(false);
			if(!ch.getP1().getName().equals("0"))
			{
				selectOldPartner.setValue(ch.getP1().getName());
				selectOldPartner.getEditor().setText(selectOldPartner.getValue());
			}
			else{
				selectOldPartner.setValue(null);
				selectOldPartner.getEditor().setText("");
			}
			
			if(!ch.getP2().getName().equals("0"))
			{
				selectOldPartner1.setValue(ch.getP2().getName());
				selectOldPartner1.getEditor().setText(selectOldPartner1.getValue());
			}
			else{
				selectOldPartner1.setValue("");
				selectOldPartner1.getEditor().setText(null);
			}

			

		}
		

	}
	private void setFieldsWithSelectedPartner(Partner selectPartner) {
		if(selectPartner.getName().equals("0"))
			selectPartner.setName("");
		part1Name.setText(selectPartner.getName());
		part1phone1.setText("" + selectPartner.getPhone1());
		part1Phone2.setText("" + selectPartner.getPhone2());
		part1groundPhone.setText("" + selectPartner.getGroundPhone());
		part1Address.setText("" + selectPartner.getAddress());
		
		}
	private void setFieldsWithSelectedPartner1(Partner selectPartner) {
		if(selectPartner.getName().equals("0"))
			selectPartner.setName("");
		part2Name.setText(selectPartner.getName());
		part2Phone1.setText("" + selectPartner.getPhone1());
		part2Phone2.setText("" + selectPartner.getPhone2());
	
	}

}
