package application.sub;

import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import application.database.PartnerDatabase;
import application.model.Partner;
import application.utils.MUtils;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class AddPartnerController {
	boolean editMode = false;
	boolean errorFlag = false;
	Partner part;
	StringProperty part1NameP;
	LongProperty part1phone1P;
	LongProperty part1Phone2P;
	LongProperty part1groundPhoneP;
	StringProperty part1AddressP;
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
	private Button saveButton;
	@FXML
	private Button cancelButton;

	// Event Listener on Button[#saveButton].onAction
	@FXML
	public void onSave(ActionEvent event) {
		if(editMode == true)
		{
			onEditModeSave();
			if(errorFlag == false) {
				Stage stage = (Stage) saveButton.getScene().getWindow();
				stage.close();
				return;
			}
		}
		if(checkIfErrorExists())
		{
			MUtils.showErrorMessage("ÎØÃ Ýì ÇáãÏÎáÇÊ", "íæÌÏ ÎØÃ Ýì ÇáãÚáæãÇÊ ÇáãÏÎáå ÑÌÇÁ ãÚÇáÌå ÇáÇÎØÇÁ");
			errorFlag = true;
			return;
		}

		try{
			if(PartnerDatabase.checkIfNameExist(part1NameP.getValue()))
			{
				MUtils.showErrorMessage("ÎØÃ ÊßÑÇÑ ÇÓã", "ÇáÇÓã ÇáÐì ÞãÊ ÈÅÏÎÇáÉ ãæÌæÏ ÓÇÈÞÇ");
				errorFlag = true;
				return;
			}
			
			
			part = new Partner(part1NameP.getValue() ,part1groundPhoneP.getValue()
					,part1phone1P.getValue() , part1Phone2P.getValue()
					, part1Address.getText());                          
			PartnerDatabase.insertNewPartner(part);
			clearText();
			errorFlag = false;
			MUtils.notification("ÇÏÎÇá ãÑÇÝÞ", "Êã ÇÏÎÇá ãÑÇÝÞ ÈäÌÇÍ\n ÇÓã ÇáãÑÇÝÞ: " + part.getName());			
		}catch(Exception ex) {}
		if(errorFlag == false)
			closeStage();
	}	

	public void onEditModeSave()
	{
		if(checkIfErrorExists())
		{
			MUtils.showErrorMessage("ÎØÃ Ýì ÇáãÏÎáÇÊ", "íæÌÏ ÎØÃ Ýì ÇáãÚáæãÇÊ ÇáãÏÎáå ÑÌÇÁ ãÚÇáÌå ÇáÇÎØÇÁ");
			errorFlag = true;
			return;
		}
		if(PartnerDatabase.checkIfNameExistEdit(part.getName(), part1NameP.getValue()))
		{
			MUtils.showErrorMessage("ÎØÃ ÊßÑÇÑ ÇÓã", "ÇáÇÓã ÇáÐì ÞãÊ ÈÅÏÎÇáÉ ãæÌæÏ ÓÇÈÞÇ");
			errorFlag = true;
			return;
		}
		try{
			part.setName(part1NameP.getValue());
			part.setGroundPhone(part1groundPhoneP.getValue());
			part.setPhone1(part1phone1P.getValue());
			part.setPhone2(part1Phone2P.getValue());
			part.setAddress(part1Address.getText());
			PartnerDatabase.updatePartner(part.getId() , part);		
			errorFlag = false;
			MUtils.notification("ÊÍÏíË ãÚáæãÇÊ ãÑÇÝÞ ", "Êã ÊÏíË ãÚáæãÇÊ ÇáãÑÇÝÞ ÈäÌÇÍ\n ÇÓã ÇáÍÇáå: " + part.getName());
		}catch(Exception ex) {}
		if(errorFlag == false)
			closeStage();
	}
	
	
	// Event Listener on Button[#cancelButton].onAction
	@FXML	public void onCancel(ActionEvent event) {
		Stage stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
	}
	
	private void closeStage(){
		Stage stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
	}
	@FXML private void initialize()
	{
		part1Address.setText("");
		saveButton.setDisable(true);
		loadListener();
	}
	private void clearText()
	{
		part1Name.setText("");
		part1phone1.setText("");
		part1Phone2.setText("");
		part1groundPhone.setText("");
		part1Address.setText("");
	}
	private void loadListener() {
		part1NameP = new SimpleStringProperty("0");
		part1phone1P = new SimpleLongProperty(0);
		part1Phone2P = new SimpleLongProperty(0);
		part1groundPhoneP = new SimpleLongProperty(0);
		part1AddressP = new SimpleStringProperty("0");
				
		part1Name.textProperty().addListener(event -> {
			String str = part1Name.getText().trim();
			part1NameError.setVisible(false);
			if (str.isEmpty()) {
				part1NameP.setValue("");
				saveButton.setDisable(true);
				return;
			}

			else if (MUtils.IsString(str)) {
				part1NameP.setValue(str);
				saveButton.setDisable(false);

			} else {
				part1NameError.setVisible(true);
				part1NameP.setValue("");
				saveButton.setDisable(true);
			}

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
		

	}
	private boolean checkIfErrorExists()
	{
		if(part1NameError.isVisible() || part1GroundPhoneError.isVisible() 
				|| part1Phone1Error.isVisible() || part1Phone2Error.isVisible())
			return true;
		else
			return false;
	}

	public void setEditMode(int partId)
	{
		editMode = true;
		part = PartnerDatabase.selectPartner(partId);
		part1Name.setText(part.getName());
		part1groundPhone.setText("" + part.getGroundPhone());
		part1phone1.setText("" + part.getPhone1());
		part1Phone2.setText("" + part.getPhone2());
		part1Address.setText("" + part.getAddress());
	}
}
























