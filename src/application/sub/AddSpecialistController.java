package application.sub;

import javafx.fxml.FXML;

import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import application.database.SpecialistDatabase;
import application.model.Specialist;
import application.utils.MUtils;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;

import javafx.scene.image.ImageView;

import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AddSpecialistController {
	boolean editMode = false;
	boolean errorFlag = false;
	StringProperty specialistNameP;
	LongProperty phone1P;
	LongProperty phone2P;
	Specialist sp;
	@FXML
	private VBox container;
	@FXML
	private TextField specialistName;
	@FXML
	private TextField phone1;
	@FXML
	private TextField phone2;
	@FXML
	private ImageView phone2Error;
	@FXML
	private ImageView phone1Error;
	@FXML
	private ImageView specialistNameError;
	
	
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
			if(errorFlag == false)
				closeStage();
			return;
		}
		if(specialistNameP.getValue().equals("") && checkErrors()){
			MUtils.showErrorMessage("Œÿ√ «÷«›…", "«·ﬁÌ„ «· Ï «œŒ·  Œ«ÿ∆…");
			errorFlag = true;
			return;
		}
		if(SpecialistDatabase.checkIfNameExist(specialistNameP.getValue()))
		{
			MUtils.showErrorMessage("Œÿ√  ﬂ—«— «”„", "«·«”„ «·–Ï ﬁ„  »≈œŒ«·… „ÊÃÊœ ”«»ﬁ«");
			errorFlag = true;
			return;
		}
		else{
			SpecialistDatabase.insertNewSpecialist(new Specialist(specialistNameP.getValue() ,phone1P.getValue(),phone2P.getValue() ));
			errorFlag = false;
			MUtils.notification("«÷«›Â «Œ’«∆Ï", " „ «÷«›… «Œ’«∆Ï/ÿ»Ì» »‰Ã«Õ\n «”„ «·«Œ’«∆Ï: " + specialistNameP.getValue());

		}
			if(errorFlag == false)
				closeStage();
		
	}
	private void closeStage(){
		Stage stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
	}
	// Event Listener on Button[#cancelButton].onAction
	@FXML
	public void onCancel(ActionEvent event) {
		Stage stage = (Stage)cancelButton.getScene().getWindow();
		stage.close();
	}
	@FXML
	private void initialize()
	{
		loadListeners();
		
	}
	
	private boolean checkErrors()
	{
		if(specialistNameError.isVisible()|| phone1Error.isVisible() || phone2.isVisible())
			return true;
		else return false;
	}

	private void loadListeners()
	{
		 specialistNameP = new SimpleStringProperty("");
		 phone1P = new SimpleLongProperty(0);
		 phone2P = new SimpleLongProperty(0);
		 
		 specialistName.textProperty().addListener(event ->{
				String str = specialistName.getText().trim();
				specialistNameError.setVisible(false);
				if(str.isEmpty())
				{
					specialistNameP.setValue("0");
					return;
				}
				else if(MUtils.IsString(str))
				{
					specialistNameP.setValue(str);

				}
				else
				{
					specialistNameError.setVisible(true);
				}
				
			});
		 phone1.textProperty().addListener(event ->{
				String str = phone1.getText().trim();
				
				phone1Error.setVisible(false);
				if(str.isEmpty())
				{
					phone1P.setValue(0);
					return;
				}
				try
				{
					phone1P.setValue(Long.parseLong(str));
					
				} catch(Exception ex) {
					phone1Error.setVisible(true);
				}
				
			});
		 
		 phone2.textProperty().addListener(event ->{
				String str = phone2.getText().trim();
				
				phone2Error.setVisible(false);
				if(str.isEmpty())
				{
					phone2P.setValue(0);
					return;
				}
				try
				{
					phone2P.setValue(Long.parseLong(str));
					
				} catch(Exception ex) {
					phone2Error.setVisible(true);
				}
				
			});
	}


	private void onEditModeSave()
	{
		if(specialistNameP.getValue().equals("") && checkErrors()){
			MUtils.showErrorMessage("Œÿ√ «÷«›…", "«·ﬁÌ„ «· Ï «œŒ·  Œ«ÿ∆…");
			errorFlag = true;
		}
		if(SpecialistDatabase.checkIfNameExistEdit(sp.getName(), specialistNameP.getValue()))
		{
			MUtils.showErrorMessage("Œÿ√  ﬂ—«— «”„", "«·«”„ «·–Ï ﬁ„  »≈œŒ«·… „ÊÃÊœ ”«»ﬁ«");
			errorFlag = true;
			return;
		}
		else {
			sp.setName(specialistNameP.getValue());
			sp.setPhone1(phone1P.getValue());
			sp.setPhone2(phone2P.getValue());
			SpecialistDatabase.updateSpecialist(sp.getId() , sp);
			errorFlag = false;
			MUtils.notification(" ÕœÌÀ „⁄·Ê„«  «Œ’«∆Ï ", " „  ⁄œÌ· „⁄·Ê„«  «·«Œ’«∆Ï »‰Ã«Õ\n «”„ «·«Œ’«∆Ï: " + sp.getName());
		}
	}
	
	public void setEditMode(int spId)
	{
		editMode = true;
		sp = SpecialistDatabase.selectSpecialist(spId);
		specialistName.setText(sp.getName());
		phone1.setText("" + sp.getPhone1());
		phone2.setText("" + sp.getPhone2());
	}

	
}























