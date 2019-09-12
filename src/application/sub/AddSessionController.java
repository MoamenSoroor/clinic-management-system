package application.sub;

import javafx.fxml.FXML;

import javafx.scene.control.Button;

import java.time.LocalDate;
import application.database.ChildDatabase;
import application.database.PartnerDatabase;
import application.database.SessionDatabase;
import application.database.SpecialistDatabase;
import application.model.Child;
import application.model.Partner;
import application.model.Session;
import application.model.Specialist;
import application.utils.AutoCompleteComboBoxListener;
import application.utils.MUtils;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.control.DatePicker;

public class AddSessionController {	
	boolean errorFlag = false;
	@FXML
	private DatePicker dateofSession;
	@FXML
	private ComboBox <String>selectSpecialist;
	@FXML
	private ComboBox <String>selectChild;
	@FXML
	private ComboBox <String>selectPartner;
	@FXML
	private ImageView childNameError;
	@FXML
	private ImageView statusError;
	@FXML
	private ImageView specialistError;
	@FXML
	private ImageView partnerError;
	@FXML
	private ImageView dateError;
	@FXML
	private ImageView warning;
	@FXML
	private Label restSessions;
	@FXML
	private Button saveButton;
	@FXML
	private Button cancelButton;

	private void initCombonents() {
		selectChild.setOnKeyReleased(new AutoCompleteComboBoxListener<String>(selectChild));
		selectPartner.setOnKeyReleased(new AutoCompleteComboBoxListener<String>(selectPartner));
		selectSpecialist.setOnKeyReleased(new AutoCompleteComboBoxListener<String>(selectSpecialist));		
		dateofSession.setValue(LocalDate.now());
		selectChild.getItems().addAll(ChildDatabase.selectAllChildNames());
		if(selectChild.getItems().isEmpty())
		{
			selectChild.getEditor().setText("·« ÌÊÃœ");
			selectChild.setValue(null);
			selectChild.setDisable(true);
			disableCombonents(true);
			restSessions.setText("");
			return;
		}
		else
		{
			loadAll();
		}
	}
	private void closeStage(){
		Stage stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
	}
	private void loadAll()
	{
		Child ch = ChildDatabase.selectChild(selectChild.getItems().get(0));
		selectChild.setValue(selectChild.getItems().get(0));
		loadSpecialist();
		loadPartners(ch);
		loadRestSessions(ch);
	}	

	// Event Listener on Button[#saveButton].onAction
	@FXML
	public void onSave(ActionEvent event) {
		if(checkIfErrorsExist())
		{
			MUtils.showErrorMessage("Œÿ√ ›Ï «·ﬁÌ„ «·„œŒ·…", "«·ﬁÌ„ «· Ï ﬁ„  »«œŒ«·Â« Œÿ√ «·—Ã«¡ „⁄«·ÃÂ «·«Œÿ«¡");
			errorFlag = true;
			return;
		}
		Child ch = ChildDatabase.selectChild(selectChild.getValue());
		Specialist sp = SpecialistDatabase.selectSpecialist(selectSpecialist.getValue());
		Partner part  = PartnerDatabase.selectPartner(selectPartner.getValue());
		Session s = new Session(sp, ch, part, dateofSession.getValue());
		SessionDatabase.insertNewSession(s);
		errorFlag = false;
		MUtils.notification("«œŒ«· Ã·”Â", " „ «œŒ«· Ã·”… »‰Ã«Õ\n «”„ «·Õ«·Â: " + s.getCh().getName());

		if(errorFlag == false)
		{
			closeStage();
		}
	}
	// Event Listener on Button[#cancelButton].onAction
	@FXML
	public void OnCancel(ActionEvent event) {
		Stage stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
	}
	@FXML
	private void initialize()
	{	
		initCombonents();
		addListeners();
	}
	private void addListeners()
	{
		addSelectChildListener();
		addSelectSpecialistListener();
		addSelectPartnerListener();
	}
	
	public void selectChildFromOut(int childId)
	{
		Child ch = ChildDatabase.selectChild(childId);
		selectChild.setValue(ch.getName());
		loadPartners(ch);
		loadRestSessions(ch);
	}
	

	private void loadPartners(Child ch)
	{
		if(ch == null || (ch.getP1().getId() == 0 && ch.getP2().getId() == 0))
		{
			selectPartner.getEditor().setText("·« ÌÊÃœ");
			selectPartner.setDisable(true);
			selectPartner.setValue(null);
			return;
		}

		else if ((ch.getP1().getId() != 0 && ch.getP2().getId() == 0)){
			selectPartner.getItems().setAll(ch.getP1().getName());
			selectPartner.setValue(selectPartner.getItems().get(0));
		}
		else if ((ch.getP1().getId() == 0 && ch.getP2().getId() != 0)){
			selectPartner.getItems().setAll(ch.getP2().getName());
			selectPartner.setValue(selectPartner.getItems().get(0));

		}
		else
		{
			selectPartner.getItems().setAll(ch.getP1().getName(), ch.getP2().getName());
			selectPartner.setValue(selectPartner.getItems().get(0));
		}
		
	}
	private void loadSpecialist()
	{
		selectSpecialist.getItems().addAll(SpecialistDatabase.selectAllSpecialistNames());
		if(selectSpecialist.getItems().size() == 0)
		{
			selectSpecialist.getEditor().setText("·« ÌÊÃœ");
			selectSpecialist.setDisable(true);
			selectSpecialist.setValue(null);
			return;

		}
		else
		{
			selectSpecialist.setValue(selectSpecialist.getItems().get(0));
		}
	}	
	private void loadRestSessions(Child ch) {
		restSessions.setText("" + (ch.getNumberOfSessions() - ch.getDoneSessions()));
		if((ch.getNumberOfSessions() - ch.getDoneSessions()) <= 0)
			warning.setVisible(true);
		else
			warning.setVisible(false);
		
	}
		
	private void addSelectChildListener()
	{
		selectChild.setOnHidden(event->{
			String select = selectChild.getSelectionModel().getSelectedItem();
			String value = selectChild.getEditor().getText();
			if(select == null || value == null)
			{
				childNameError.setVisible(true);
				disableCombonents(true);
				return;
			}
			else if(select.isEmpty()|| value.isEmpty())
			{
				childNameError.setVisible(true);
				disableCombonents(true);
				return;
			}
			else if(!value.equals(select))
			{
				selectChild.getEditor().setText(select);
				childNameError.setVisible(false);
				disableCombonents(false);
			}
			else
			{
				selectChild.getEditor().setText(select);
				childNameError.setVisible(false);
				disableCombonents(false);

			}
			Child ch = ChildDatabase.selectChild(select);
			loadRestSessions(ch);
			loadPartners(ch);
		});
	}

	private void addSelectSpecialistListener()
	{
		selectSpecialist.setOnHidden(event ->{
			if(selectSpecialist.isDisable()){
				specialistError.setVisible(false);
				return;
			}
			String select = selectSpecialist.getSelectionModel().getSelectedItem();
			String value = selectSpecialist.getEditor().getText();
			if(select == null || value == null)
			{
				specialistError.setVisible(true);
				return;
			}
			else if(select.isEmpty()|| value.isEmpty())
			{
				

				specialistError.setVisible(true);
				return;
			}
			else if(!value.equals(select))
			{
				selectSpecialist.getEditor().setText(select);
				specialistError.setVisible(false);
			}
			else
			{
				selectSpecialist.getEditor().setText(select);
				specialistError.setVisible(false);
			}
		});
	}

	private void addSelectPartnerListener()
	{
		selectPartner.setOnHidden(event ->{
			if(selectPartner.isDisable()){
				partnerError.setVisible(false);
			}
			String select = selectPartner.getSelectionModel().getSelectedItem();
			String value = selectPartner.getEditor().getText();
			if(select == null || value == null)
			{
				partnerError.setVisible(true);
				return;
			}
			else if(select.isEmpty()|| value.isEmpty())
			{
				partnerError.setVisible(true);
				return;
			}
			else if(!value.equals(select))
			{
				selectPartner.getEditor().setText(select);
				partnerError.setVisible(false);
			}
			else
			{
				selectPartner.getEditor().setText(select);
				partnerError.setVisible(false);
			}
		});
	}

	private boolean checkIfErrorsExist()
	{
		if(childNameError.isVisible() || specialistError.isVisible() || partnerError.isVisible()
			|| dateError.isVisible())
			return true;
		else
			return false;
	}
	private void disableCombonents(boolean dis)
	{
		selectPartner.setDisable(dis);
		selectSpecialist.setDisable(dis);
		dateofSession.setDisable(dis);
		restSessions.setDisable(dis);
		saveButton.setDisable(dis);
		if(selectPartner.getItems().size() == 0)
			selectPartner.setDisable(true);
		if(selectSpecialist.getItems().size() == 0)
			selectSpecialist.setDisable(true);
	}

}
























