package application.sub;

import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import com.jfoenix.controls.JFXToggleButton;
import application.MainFXMLController;
import application.database.PropertiesDataBase;
import application.database.Table;
import application.model.PaidSystem;
import application.model.TableProperties;
import application.utils.MUtils;

public class PropertiesController {
	boolean closeFlag = false;
	MainFXMLController main;
	@FXML
	private JFXToggleButton childId;
	@FXML
	private JFXToggleButton Childstatus;
	@FXML
	private JFXToggleButton childParther1;
	@FXML
	private JFXToggleButton childPartner2;
	@FXML
	private JFXToggleButton childMaxSessions;
	@FXML
	private JFXToggleButton childDoneSessions;
	@FXML
	private JFXToggleButton childExpired;
	@FXML
	private JFXToggleButton partnerId;
	@FXML
	private JFXToggleButton partnerGroundPhone;
	@FXML
	private JFXToggleButton partnerPhone1;
	@FXML
	private JFXToggleButton partnerPhone2;
	@FXML
	private JFXToggleButton partnerAddress;
	@FXML
	private JFXToggleButton specialistId;
	@FXML
	private JFXToggleButton specialistPhone1;
	@FXML
	private JFXToggleButton specialistPhone2;
	@FXML
	private JFXToggleButton sessionId;
	@FXML
	private JFXToggleButton sessionDate;
	@FXML
	private JFXToggleButton sessionStatus;
	@FXML
	private JFXToggleButton sessionSp;
	@FXML
	private JFXToggleButton sessionPartner;
	@FXML
	private JFXToggleButton sessionDoneSession;
	@FXML
	private JFXToggleButton sessionExpired;
	@FXML
	private JFXToggleButton billId;
	@FXML
	private JFXToggleButton billPayDay;
	@FXML
	private JFXToggleButton billPayDate;
	@FXML
	private JFXToggleButton billPartnerName;
	@FXML
	private JFXToggleButton newOneSession;
	@FXML
	private JFXToggleButton billRestMoney;
	@FXML
	private JFXToggleButton billAllMoney;
	@FXML
	private JFXToggleButton billNewSessions;
	
	@FXML
	private Accordion accord;
	@FXML
	private TitledPane pane1;
	
	@FXML
	private TextField paidMoney1 , paidMoney2 , paidMoney3 , paidMoney4 , paidMoney5;
	@FXML
	private TextField oneSession1 , oneSession2 , oneSession3 , oneSession4 , oneSession5;
	
	@FXML
	private TextField maxDuration;
	private int duration;
	private double paidMoney11 , paidMoney22 , paidMoney33 , paidMoney44 , paidMoney55;

	private double oneSession11 , oneSession22 , oneSession33 , oneSession44 , oneSession55;
	
	private void onSavePaymentSystem(){
		if(MUtils.IsNumber(paidMoney1.getText()) && MUtils.IsNumber(paidMoney2.getText())
			&& MUtils.IsNumber(paidMoney3.getText()) && MUtils.IsNumber(paidMoney4.getText())	
			&& MUtils.IsNumber(paidMoney5.getText()) && MUtils.IsNumber(oneSession1.getText())
			&& MUtils.IsNumber(oneSession2.getText()) && MUtils.IsNumber(oneSession3.getText())
			&& MUtils.IsNumber(oneSession4.getText()) && MUtils.IsNumber(oneSession5.getText())
			&& MUtils.IsNumber(maxDuration.getText()))
		{
			try{
				paidMoney11 = Double.parseDouble(paidMoney1.getText());
				paidMoney22 = Double.parseDouble(paidMoney2.getText());
				paidMoney33 = Double.parseDouble(paidMoney3.getText());
				paidMoney44 = Double.parseDouble(paidMoney4.getText());
				paidMoney55 = Double.parseDouble(paidMoney5.getText());
				oneSession11 = Double.parseDouble(oneSession1.getText());
				oneSession22 = Double.parseDouble(oneSession2.getText());
				oneSession33 = Double.parseDouble(oneSession3.getText());
				oneSession44 = Double.parseDouble(oneSession4.getText());
				oneSession55 = Double.parseDouble(oneSession5.getText());
				duration = Integer.parseInt(maxDuration.getText());
				if( (paidMoney11/oneSession11 < 1) 
					|| (paidMoney22/oneSession22 < 1) 
					|| (paidMoney33/oneSession33 < 1) 
					|| (paidMoney44/oneSession44 < 1) 
					|| (paidMoney55/oneSession55 < 1) )
				{
					closeFlag = false;
					MUtils.showErrorMessage("ÎØÃ Ýì Þíã ÇäÙãÉ ÇáÏÝÚ", "íæÌÏ ÎØÃ Ýì ÃäÙãÉ ÇáÏÝÚ ÇáÑÌÇÁ ãÚÇáÌå ÇáÇÎØÇÁ");
					return;
				}
				MUtils.setDuration(duration);
				PaidSystem paid = new PaidSystem();
				paid.setPaidMoney11(paidMoney11);
				paid.setPaidMoney22(paidMoney22);
				paid.setPaidMoney33(paidMoney33);
				paid.setPaidMoney44(paidMoney44);
				paid.setPaidMoney55(paidMoney55);
				paid.setOneSession11(oneSession11);
				paid.setOneSession22(oneSession22);
				paid.setOneSession33(oneSession33);
				paid.setOneSession44(oneSession44);
				paid.setOneSession55(oneSession55);
				PropertiesDataBase.UpdatePropertiesPaid(paid);
				PropertiesDataBase.UpdatePropertiesSessions(paid);
				closeFlag = true;
			}
			catch(Exception ex){
				closeFlag = false;
				MUtils.showErrorMessage("ÎØÃ Ýì Þíã ÇäÙãÉ ÇáÏÝÚ", "íæÌÏ ÎØÃ Ýì ÃäÙãÉ ÇáÏÝÚ ÇáÑÌÇÁ ãÚÇáÌå ÇáÇÎØÇÁ");
			}
			
		}
		else{
			closeFlag = false;
			MUtils.showErrorMessage("ÎØÃ Ýì Þíã ÇäÙãÉ ÇáÏÝÚ", "íæÌÏ ÎØÃ Ýì ÃäÙãÉ ÇáÏÝÚ ÇáÑÌÇÁ ãÚÇáÌå ÇáÇÎØÇÁ");
		}
	}
	@FXML
	private Button cancelButton;
	@FXML
	private Button saveButton;
	@FXML
	private Button defaultButton;

	// Event Listener on Button[#cancelButton].onAction
	@FXML
	public void onCancelButton(ActionEvent event) {
		Stage stage = (Stage)cancelButton.getScene().getWindow();
		stage.close();
	}
	
	@FXML
	public void initialize(){
		setToDefaultFromDatabase();
		setPaidToFields();
		accord.setExpandedPane(pane1);
		maxDuration.setText("" + MUtils.getDuration());
	}
	

	private void setPaidToFields() {
		PaidSystem paid = PropertiesDataBase.selectPropertiesPaidSystem();
		paidMoney1.setText("" + paid.getPaidMoney11());
		paidMoney2.setText("" + paid.getPaidMoney22());
		paidMoney3.setText("" + paid.getPaidMoney33());
		paidMoney4.setText("" + paid.getPaidMoney44());
		paidMoney5.setText("" + paid.getPaidMoney55());

		oneSession1.setText("" + paid.getOneSession11());
		oneSession2.setText("" + paid.getOneSession22());
		oneSession3.setText("" + paid.getOneSession33());
		oneSession4.setText("" + paid.getOneSession44());
		oneSession5.setText("" + paid.getOneSession55());

		

		
	}

	// Event Listener on Button[#saveButton].onAction
	@FXML
	public void onSaveButton(ActionEvent event) {
		
		closeFlag = false;
		if(MUtils.showConfirmMessage("ÊÃßíÏ ÊÛííÑ ÎÕÇÆÕ ÇáÌÏÇæá", "åá ÇäÊ ãÊÃßÏ ãä ÇÍÏÇË åÐå ÇáÊÛíÑÇÊ¿")){
			onSavePaymentSystem();
			if(closeFlag == false)
				return;
			setNewPropertiesInSave();
			setSearchTextFields();
			MUtils.notification("ÊÛíÑ ÎÕÇÆÕ ÇáÌÏÇæá", "Êã ÊÛííÑ ÎÕÇÆÕ ÇáÌÏÇæá ÈäÌÇÍ");
		}
		if(closeFlag == true){
			Stage stage = (Stage) saveButton.getScene().getWindow();
			stage.close();
		}
	}
	// Event Listener on Button[#defaultButton].onAction
	@FXML
	public void onDefaultButton(ActionEvent event) {
		if(MUtils.showConfirmMessage("ÊÃßíÏ ÇáÚæÏÉ Çáì ÇáæÖÚ ÇáÇÝÊÑÇÖì", "åá ÇäÊ ãÊÃßÏ ãä ÇáÚæÏÉ Çáì ÇáæÖÚ ÇáÇÝÊÑÇÖì¿")){
			setToDefaultFromDatabase();
			setPaidToFields();
		}
	}
	public void loadMain(MainFXMLController main) {
		this.main = main;
		setSearchTextFields();
	}
	public void setToDefault(){
		childId.setSelected(true);
		Childstatus.setSelected(true);
		childParther1.setSelected(true);
		childPartner2.setSelected(true);
		childMaxSessions.setSelected(true);
		childDoneSessions.setSelected(true);
		Childstatus.setSelected(true);
		
		partnerId.setSelected(true);
		partnerGroundPhone.setSelected(true);
		partnerPhone1.setSelected(true);
		partnerPhone2.setSelected(true);
		partnerAddress.setSelected(true);
		
		
		specialistId.setSelected(true);
		specialistPhone1.setSelected(true);
		specialistPhone2.setSelected(true);

		sessionId.setSelected(true);
		sessionDate.setSelected(true);
		sessionStatus.setSelected(true);
		sessionSp.setSelected(true);
		sessionPartner.setSelected(true);
		sessionDoneSession.setSelected(true);
		sessionExpired.setSelected(true);

		billId.setSelected(true);
		billPayDay.setSelected(true);
		billPayDate.setSelected(true);
		billPartnerName.setSelected(true);
		newOneSession.setSelected(true);
		billRestMoney.setSelected(true);
		billAllMoney.setSelected(true);
		billNewSessions.setSelected(true);
	}
	
	public void setToDefaultFromDatabase(){
		TableProperties child =  PropertiesDataBase.selectProperties(Table.CHILD);
		TableProperties part =  PropertiesDataBase.selectProperties(Table.PRTNER);
		TableProperties sp =  PropertiesDataBase.selectProperties(Table.SPECIALIST);
		TableProperties session =  PropertiesDataBase.selectProperties(Table.SESSION);
		TableProperties bill =  PropertiesDataBase.selectProperties(Table.BILL);
		// child properties
		childId.setSelected(child.isProperty1());
		Childstatus.setSelected(child.isProperty2());
		childParther1.setSelected(child.isProperty3());
		childPartner2.setSelected(child.isProperty4());
		childMaxSessions.setSelected(child.isProperty5());
		childDoneSessions.setSelected(child.isProperty6());
		Childstatus.setSelected(child.isProperty7());

		partnerId.setSelected(part.isProperty1());
		partnerGroundPhone.setSelected(part.isProperty2());
		partnerPhone1.setSelected(part.isProperty3());
		partnerPhone2.setSelected(part.isProperty4());
		partnerAddress.setSelected(part.isProperty5());
		
		specialistId.setSelected(sp.isProperty1());
		specialistPhone1.setSelected(sp.isProperty2());
		specialistPhone2.setSelected(sp.isProperty3());

		sessionId.setSelected(session.isProperty1());
		sessionDate.setSelected(session.isProperty2());
		sessionStatus.setSelected(session.isProperty3());
		sessionSp.setSelected(session.isProperty4());
		sessionPartner.setSelected(session.isProperty5());
		sessionDoneSession.setSelected(session.isProperty6());
		sessionExpired.setSelected(session.isProperty7());

		billId.setSelected(bill.isProperty1());
		billPayDay.setSelected(bill.isProperty2());
		billPayDate.setSelected(bill.isProperty3());
		billPartnerName.setSelected(bill.isProperty4());
		newOneSession.setSelected(bill.isProperty5());
		billRestMoney.setSelected(bill.isProperty6());
		billAllMoney.setSelected(bill.isProperty7());
		billNewSessions.setSelected(bill.isProperty8());
	}
	
	public void setNewPropertiesInSave(){
		TableProperties pro = new TableProperties(Table.CHILD);
		pro.setProperty1(childId.isSelected());
		pro.setProperty2(Childstatus.isSelected());
		pro.setProperty3(childParther1.isSelected());
		pro.setProperty4(childPartner2.isSelected());
		pro.setProperty5(childMaxSessions.isSelected());
		pro.setProperty6(childDoneSessions.isSelected());
		pro.setProperty7(childExpired.isSelected());
		PropertiesDataBase.UpdateProperties(pro);

		/*
		 partnerId;
		partnerGroundPhone;
		partnerPhone1;
		partnerPhone2;
		partnerAddress;
		 */
		
		pro.setTableType(Table.PRTNER);
		pro.setProperty1(partnerId.isSelected());
		pro.setProperty2(partnerGroundPhone.isSelected());
		pro.setProperty3(partnerPhone1.isSelected());
		pro.setProperty4(partnerPhone2.isSelected());
		pro.setProperty5(partnerAddress.isSelected());
		PropertiesDataBase.UpdateProperties(pro);

		/*
		 specialistId;
		 specialistPhone1;
		 specialistPhone2;
		 */
		
		pro.setTableType(Table.SPECIALIST);
		pro.setProperty1(specialistId.isSelected());
		pro.setProperty2(specialistPhone1.isSelected());
		pro.setProperty3(specialistPhone2.isSelected());
		pro.setProperty4(partnerPhone2.isSelected());
		pro.setProperty5(partnerAddress.isSelected());
		PropertiesDataBase.UpdateProperties(pro);
		
		/*
		  sessionId;
		sessionDate;
		sessionStatus;
		sessionSp;
		sessionPartner;
		sessionDoneSession;
		sessionExpired
		 */
		pro.setTableType(Table.SESSION);
		pro.setProperty1(sessionId.isSelected());
		pro.setProperty2(sessionDate.isSelected());
		pro.setProperty3(sessionStatus.isSelected());
		pro.setProperty4(sessionSp.isSelected());
		pro.setProperty5(sessionPartner.isSelected());
		pro.setProperty6(sessionDoneSession.isSelected());
		pro.setProperty7(sessionExpired.isSelected());
		PropertiesDataBase.UpdateProperties(pro);
		
		/*
		
		billId;
		billPayDay;
		billPayDate;
		billExbiredDate;
		billDuration;
		billPartnerName;
		billRestMoney;
		billAllMoney;
		billNewSessions;
		 */
		pro.setTableType(Table.BILL);
		pro.setProperty1(billId.isSelected());
		pro.setProperty2(billPayDay.isSelected());
		pro.setProperty3(billPayDate.isSelected());
		pro.setProperty4(billPartnerName.isSelected());
		pro.setProperty5(newOneSession.isSelected());
		pro.setProperty6(billRestMoney.isSelected());
		pro.setProperty7(billAllMoney.isSelected());
		pro.setProperty8(billNewSessions.isSelected());
		PropertiesDataBase.UpdateProperties(pro);
		closeFlag = true;
	}

	public void setSearchTextFields(){
		if(!childParther1.isSelected())
			main.getChildController().disableSearchPart1Name(true);
		else
			main.getChildController().disableSearchPart1Name(false);
		
		if(!childPartner2.isSelected())
			main.getChildController().disableSearchPart2Name(true);
		else
			main.getChildController().disableSearchPart2Name(false);

		if(!sessionSp.isSelected())
			main.getSessionController().disableSearchSpecialistName(true);
		else
			main.getSessionController().disableSearchSpecialistName(false);
		
		if(!sessionPartner.isSelected())
			main.getSessionController().disableSearchPartnerName(true);
		else
			main.getSessionController().disableSearchPartnerName(false);
		
		if(!billPartnerName.isSelected())
			main.getBillController().disableSearchPartnerName(true);
		else
			main.getBillController().disableSearchPartnerName(false);
	}
}


/*
childId;
Childstatus;
childParther1;
childPartner2;
childMaxSessions;
childDoneSessions;
childStatus;

pecialistId;
pecialistPhone1;
specialistPhone2;

sessionId;
sessionDate;
sessionStatus;
sessionSp;
sessionPartner;
sessionDoneSession;
sessionExpired;

billId;
billPayDay;
billPayDate;
billExbiredDate;
billDuration;
billPartnerName;
billRestMoney;
billAllMoney;
billNewSessions;
*/
