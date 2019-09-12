package application.sub;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import application.MainFXMLController;
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
import application.utils.MUtils;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MenusController {
	MainFXMLController main;
	@FXML
	private VBox menuVBox;
	@FXML
	private MenuItem close;
	@FXML
	private MenuItem undo;
	@FXML
	private MenuItem redo;
	@FXML
	private MenuItem addChild;
	@FXML
	private MenuItem addSession;
	@FXML
	private MenuItem addBill;
	@FXML
	private MenuItem addPartner;
	@FXML
	private MenuItem addSpecialist;
	@FXML
	private MenuItem addStatus;
	@FXML
	private MenuItem delStatus;
	@FXML
	private MenuItem properties;
	@FXML
	private Button addChildButton;
	@FXML
	private Button addSessionButton;
	@FXML
	private Button addBillButton;
	@FXML
	private Button addSpecialistButton;
	@FXML
	private Button addPartnerButton;
	
	@FXML
	private Button addButton;
	@FXML
	private Button editButton;
	@FXML
	private Button delButton;
	@FXML
	private MenuItem saveButton;
	@FXML
	private MenuItem export;
	
	@FXML 
	private MenuItem about;
	
	@FXML
	private void onProperties(){
		try {
			Stage stage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/application/sub/Properties.fxml"));
			Parent root = loader.load();
			PropertiesController con = loader.getController();
			con.loadMain(main);
			Scene scene = new Scene(root);
			stage.setScene(scene);
			//stage.setAlwaysOnTop(true);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initStyle(StageStyle.UTILITY);
			stage.setResizable(false);
			stage.showAndWait();
			} catch (IOException e) { e.printStackTrace(); }
	}
	@FXML
	private void onAbout()
	{
		try {
			Stage stage = new Stage(StageStyle.UNDECORATED);
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/application/sub/AboutProgram.fxml"));
			Parent root = loader.load();
			AboutProgramController con = loader.getController();
			con.loadMain(main);
			Scene scene = new Scene(root);
			stage.setScene(scene);
			//stage.setAlwaysOnTop(true);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initStyle(StageStyle.UNDECORATED);
			stage.setResizable(false);
			stage.showAndWait();
			} catch (IOException e) { e.printStackTrace(); }
		
	}
	
	@FXML
	private void onExport()
	{
		File database = new File("src/Clinic.sqlite");
		DirectoryChooser chooser = new DirectoryChooser();
		chooser.setTitle("«Œ — „ﬂ«‰ · ’œÌ— «·„·›« ");
		chooser.setInitialDirectory(new File("src/"));
		File dir = chooser.showDialog(null);
		if (dir == null)
			return;
		File file = new File(dir + "/clinicDatabase.sqlite");
		copyFiles(database, file);
		MUtils.notification(" ’œÌ— ﬁ«⁄œ… «·»Ì«‰« ", " „  ’œÌ— ﬁ«⁄œ… «·»Ì«‰« ");
	}
	
	private void copyFiles(File source , File dis)
	{
		try {
			InputStream is = null;
		    OutputStream os = null;
		    try {
		        is = new FileInputStream(source);
		        os = new FileOutputStream(dis);
		        byte[] buffer = new byte[1024];
		        int length;
		        while ((length = is.read(buffer)) > 0) {
		            os.write(buffer, 0, length);
		        }
		    } finally {
		        is.close();
		        os.close();
		    }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void disableUndoButton(boolean dis)
	{
		undo.setDisable(dis);
	}
	public void disableRedoButton(boolean dis)
	{
		redo.setDisable(dis);
	}
	public void disableSaveButton(boolean dis)
	{
		saveButton.setDisable(dis);
	}
	@FXML 
	private void initialize()
	{
		disableUndoButton(true);
		disableRedoButton(true);
		disableSaveButton(true);
		addSessionButton.setDisable(true);
		addBillButton.setDisable(true);
		addBill.setDisable(true);
		addSession.setDisable(true);
	}
	@FXML
	private void onUndoButton()
	{
		Database.getUndoData().makeUndo();
	}
	@FXML
	private void onRedoButton()
	{
		Database.getRedoData().makeRedo();
	}
	
	
	

	// Event Listener on MenuItem[#close].onAction
	@FXML
	public void onClose(ActionEvent event) {
		Platform.exit();
		System.exit(0);
	}
	// Event Listener on Button[#addButton].onAction
	@FXML
	public void onAddButton() {
		try {
			Stage stage = new Stage(StageStyle.UNDECORATED);
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/application/sub/ButtonAdd.fxml"));
			Parent root = loader.<VBox>load();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			//stage.setAlwaysOnTop(true);
			stage.initModality(Modality.APPLICATION_MODAL);
			//stage.initStyle(StageStyle.UTILITY);
			stage.setResizable(false);
			stage.showAndWait();
			} catch (IOException e) { e.printStackTrace(); }
	}
	// Event Listener on Button[#editButton].onAction
	@FXML
	public void onEditButton() {
		Tab tab = main.getSelectedTab();
		if(tab == null)
			return;
		if(tab.getId().equals("child"))
		{
			if(main.getChildController().getSelectedChildId() == -1)
				return;
			try {
				Stage stage = new Stage();
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/application/sub/AddChild.fxml"));
				Parent root = loader.<ScrollPane>load();
				AddChildController add = loader.getController();
				add.setEditMode(main.getChildController().getSelectedChildId());
				Scene scene = new Scene(root);
				stage.setScene(scene);
				stage.initModality(Modality.APPLICATION_MODAL);
				stage.initStyle(StageStyle.UTILITY);
				//stage.setAlwaysOnTop(true);
				//stage.setResizable(false);
				stage.showAndWait();
				} catch (IOException e) { e.printStackTrace(); }
			
		}
		else if(tab.getId().equals("specialist"))
		{
			if(main.getSpecialistController().getSelectedSpecialistId() == -1)
				return;
			try {
				Stage stage = new Stage();
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/application/sub/AddSpecialist.fxml"));
				Parent root = loader.load();
				AddSpecialistController add = loader.getController();
				add.setEditMode(main.getSpecialistController().getSelectedSpecialistId());
				Scene scene = new Scene(root);
				stage.setScene(scene);
				stage.initModality(Modality.APPLICATION_MODAL);
				stage.initStyle(StageStyle.UTILITY);
				//stage.setAlwaysOnTop(true);
				//stage.setResizable(false);
				stage.showAndWait();
				} catch (IOException e) { e.printStackTrace(); }
		
		}
		else if(tab.getId().equals("partner"))
		{
			if(main.getPartnerController().getSelectedPartnerId()== -1)
				return;
			try {
				Stage stage = new Stage();
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/application/sub/AddPartner.fxml"));
				Parent root = loader.load();
				AddPartnerController add = loader.getController();
				add.setEditMode(main.getPartnerController().getSelectedPartnerId());
				Scene scene = new Scene(root);
				stage.setScene(scene);
				stage.initModality(Modality.APPLICATION_MODAL);
				stage.initStyle(StageStyle.UTILITY);
				//stage.setAlwaysOnTop(true);
				//stage.setResizable(false);
				stage.showAndWait();
				} catch (IOException e) { e.printStackTrace(); }
		}
		else
			return;
			
	}
	// Event Listener on Button[#delButton].onAction
	@FXML
	public void onDelButton() {
		Tab tab = main.getSelectedTab();
		if(tab == null)
			return;
		if(tab.getId().equals("child"))
		{
			if(main.getChildController().getSelectedChildId() == -1)
				return;
			if(MUtils.showConfirmMessage(" √ﬂÌœ Õ–› „‰ ﬁ«∆„… «·Õ«·« ", "Â· «‰  „ √ﬂœ „‰ Õ–› «·Õ«·«  «·„Õœœ…ø"))
			{
				ObservableList<Integer> ids = main.getChildController().getSelectedChildsId();
				if(ids.size() == 0)
					return;
				else if(ids.size() == 1){
					ChildDatabase.deleteChild(ids.get(0));
					return;
				}
				ArrayList<Child> chs = new ArrayList<>();
				for(int id : ids){
					chs.add(ChildDatabase.selectChild(id));
				}
				ChildDatabase.deleteChildMulti(chs);
				MUtils.notification(" √ﬂÌœ Õ–› «·Õ«·« ", " „ Õ–› «·Õ«·«  «·„Õœœ… »‰Ã«Õ");
			}
			
		}
		else if(tab.getId().equals("bill"))
		{
			if(main.getBillController().getSelectedBillId() == -1)
				return;
			if(MUtils.showConfirmMessage(" √ﬂÌœ Õ–› „‰ ﬁ«∆„… «·›Ê« Ì—", "Â· «‰  „ √ﬂœ „‰ Õ–› «·›Ê« Ì— «·„Õœœ…ø"))
			{
				ObservableList<Integer> ids = main.getBillController().getSelectedBillsId();
				if(ids.size() == 0)
					return;
				else if(ids.size() == 1){
					BillDatabase.deleteBill(ids.get(0));
					return;
				}
				ObservableList<Bill> bills = FXCollections.observableArrayList();
				for(int id : ids){
					bills.add(BillDatabase.selectBill(id));
				}
				BillDatabase.deleteBillMulti(bills);
				MUtils.notification(" √ﬂÌœ Õ–› «·›Ê« Ì—", " „ «Õ–› «·›Ê« Ì— «·„Õœœ…  »‰Ã«Õ");
			}
		}
		else if(tab.getId().equals("session"))
		{
			if(main.getSessionController().getSelectedSessionId()== -1)
				return;
			if(MUtils.showConfirmMessage(" √ﬂÌœ Õ–› „‰ ﬁ«∆„… «·Ã·”« ", "Â· «‰  „ √ﬂœ „‰ Õ–› «·Ã·”«  «·„Õœœ…ø"))
			{
				ObservableList<Integer> ids = main.getSessionController().getSelectedSessionsId();
				if(ids.size() == 0)
					return;
				else if(ids.size() == 1){
					SessionDatabase.deleteSession(ids.get(0));
					return;
				}
				ObservableList<Session> ses = FXCollections.observableArrayList();
				for(int id : ids){
					ses.add(SessionDatabase.selectSession(id));
				}
				SessionDatabase.deleteSessionMulti(ses);
				MUtils.notification(" √ﬂÌœ Õ–› «·Ã·”« ", " „ «Õ–› «·Ã·”«  «·„Õœœ…  »‰Ã«Õ");

			}
		}
		else if(tab.getId().equals("specialist"))
		{
			if(main.getSpecialistController().getSelectedSpecialistId() == -1)
				return;
			if(MUtils.showConfirmMessage(" √ﬂÌœ Õ–› „‰ ﬁ«∆„… «·«Œ’«∆ÌÌ‰", "Â· «‰  „ √ﬂœ „‰ Õ–› «·«Œ’«∆ÌÌ‰ «·„ÕœœÌ‰ø"))
			{
				ObservableList<Integer> ids = main.getSpecialistController().getSelectedSpecialistsId();
				if(ids.size() == 0)
					return;
				else if(ids.size() == 1){
					SpecialistDatabase.deleteSpecialist(ids.get(0));
					return;
				}
				ObservableList<Specialist> sps = FXCollections.observableArrayList();
				for(int id : ids){
					sps.add(SpecialistDatabase.selectSpecialist(id));
				}
				SpecialistDatabase.deleteSpecialistMulti(sps);
				MUtils.notification(" √ﬂÌœ Õ–› «·«Œ’«∆ÌÌ‰", " „ «Õ–› «·«ÿ»«¡/«·«Œ’«∆ÌÌ‰ «·„ÕœœÌ‰  »‰Ã«Õ");

			}
		}
		else if(tab.getId().equals("partner"))
		{
			if(main.getPartnerController().getSelectedPartnerId()== -1)
				return;
			if(MUtils.showConfirmMessage(" √ﬂÌœ Õ–› „‰ ﬁ«∆„… «·„—«›ﬁÌ‰", "Â· «‰  „ √ﬂœ „‰ Õ–› «·„—«›ﬁÌ‰ «·„ÕœœÌ‰ø"))
			{
				ObservableList<Integer> ids = main.getPartnerController().getSelectedPartnersId();
				if(ids.size() == 0)
					return;
				else if(ids.size() == 1){
					PartnerDatabase.deletePartner(ids.get(0));
					return;
				}
				ObservableList<Partner> parts = FXCollections.observableArrayList();
				for(int id : ids){
					parts.add(PartnerDatabase.selectPartner(id));
				}
				PartnerDatabase.deletePartnerMulti(parts);
				MUtils.notification(" √ﬂÌœ Õ–› «·„—«›ﬁÌ‰", " „ «Õ–› «·„—«›ﬁÌ‰ «·„ÕœœÌ‰  »‰Ã«Õ");

			}
		}
		else
			return;
		
	}
	// Event Listener on Button[#saveButton].onAction
	@FXML
	public void onSaveButton() {
		Database.getRedoData().removeAll();
		Database.getUndoData().removeAll();
		disableRedoButton(true);
		disableUndoButton(true);
		disableSaveButton(true);
	}
	
	public void loadMain(MainFXMLController main)
	{
		this.main = main;
	}

	public void loadFxml(String fileName)
	{
		try {
			Stage stage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/application/sub/" + fileName));
			Parent root = loader.<VBox>load();
			Scene scene = new Scene(root);
			if(fileName.equals("ViewChild.fxml"))
			{
				ViewChildController view = loader.getController();
				view.loadChildInfor(main.getChildController().getSelectedChildId());
			}
			stage.setScene(scene);
			//stage.setAlwaysOnTop(true);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initStyle(StageStyle.UTILITY);
			stage.setResizable(false);
			stage.showAndWait();
			} catch (IOException e) { e.printStackTrace(); }
	}
	@FXML
	public void onAddChildButton(ActionEvent event) {
		try {
			Stage stage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/application/sub/AddChild.fxml"));
			Parent root = loader.<ScrollPane>load();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initStyle(StageStyle.UTILITY);
			//stage.setAlwaysOnTop(true);
			//stage.setResizable(false);
			stage.showAndWait();
			} catch (IOException e) { e.printStackTrace(); }
	}
	@FXML
	public void onAddSessionButton(ActionEvent event) {
		Tab tab = main.getSelectedTab();
		if(tab == null)
			return;
		if(tab.getId().equals("child"))
		{
			if(main.getChildController().getSelectedChildId() == -1){
				loadSession();
			}
			else
			{
				loadSelectedSession();
			}

		}
	}
	
	public void loadSelectedSession()
	{
		try{
		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/application/sub/AddSession.fxml"));
		Parent root = loader.<VBox>load();
		AddSessionController addSession = loader.getController();
		int id = main.getChildController().getSelectedChildId();
		addSession.selectChildFromOut(id);
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.initStyle(StageStyle.UTILITY);
		//stage.setAlwaysOnTop(true);
		stage.setResizable(false);
		stage.showAndWait();
		} catch (IOException e) { e.printStackTrace(); }
	}
	
	public void loadSession()
	{
		try{
		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/application/sub/AddSession.fxml"));
		Parent root = loader.<VBox>load();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.initStyle(StageStyle.UTILITY);
		//stage.setAlwaysOnTop(true);
		stage.setResizable(false);
		stage.showAndWait();
		} catch (IOException e) { e.printStackTrace(); }
	}
	
	
	@FXML
	public void onAddBillButton(ActionEvent event) {
		Tab tab = main.getSelectedTab();
		if(tab == null)
			return;
		if(tab.getId().equals("child"))
		{
			if(main.getChildController().getSelectedChildId() == -1){
				loadBill();
			}
			else
				loadBillSelected();
		}
	}
	private void loadBillSelected(){
		try {
			Stage stage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/application/sub/AddBill.fxml"));
			Parent root = loader.<VBox>load();
			AddBillController add = loader.getController();
			int id = main.getChildController().getSelectedChildId();
			add.loadFromOut(id);
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initStyle(StageStyle.UTILITY);
			//stage.setAlwaysOnTop(true);
			stage.setResizable(false);
			stage.showAndWait();
			} catch (IOException e) { e.printStackTrace(); }
	}
	
	private void loadBill(){
		try {
			Stage stage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/application/sub/AddBill.fxml"));
			Parent root = loader.<VBox>load();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initStyle(StageStyle.UTILITY);
			//stage.setAlwaysOnTop(true);
			stage.setResizable(false);
			stage.showAndWait();
			} catch (IOException e) { e.printStackTrace(); }
	}
		
	@FXML
	public void onAddSpecialistButton(ActionEvent event) {
		try {
			Stage stage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/application/sub/AddSpecialist.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initStyle(StageStyle.UTILITY);
			//stage.setAlwaysOnTop(true);
			stage.setResizable(false);
			stage.showAndWait();
			} catch (IOException e) { e.printStackTrace(); }
	}
	@FXML
	public void onAddStatusButton(ActionEvent event) {
		try {
			Stage stage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/application/sub/AddChildStatus.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initStyle(StageStyle.UTILITY);
			//stage.setAlwaysOnTop(true);
			stage.setResizable(false);
			stage.showAndWait();
			} catch (IOException e) { e.printStackTrace(); }
	}
	@FXML
	public void onDelStatusButton(ActionEvent event) {
		try {
			Stage stage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/application/sub/DelStatus.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initStyle(StageStyle.UTILITY);
			//stage.setAlwaysOnTop(true);
			stage.setResizable(false);
			stage.showAndWait();
			} catch (IOException e) { e.printStackTrace(); }
	}
	
	@FXML
	public void onAddPartnerButton(ActionEvent event) {
		try {
			Stage stage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/application/sub/AddPartner.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initStyle(StageStyle.UTILITY);
			//stage.setAlwaysOnTop(true);
			stage.setResizable(false);
			stage.showAndWait();
			} catch (IOException e) { e.printStackTrace(); }
	}
	public MenuItem getAddChild() {
		return addChild;
	}
	public MenuItem getAddSession() {
		return addSession;
	}
	public MenuItem getAddBill() {
		return addBill;
	}
	public MenuItem getAddPartner() {
		return addPartner;
	}
	public MenuItem getAddSpecialist() {
		return addSpecialist;
	}
	public MenuItem getAddStatus() {
		return addStatus;
	}
	public MenuItem getDelStatus() {
		return delStatus;
	}
	public Button getAddChildButton() {
		return addChildButton;
	}
	public Button getAddSessionButton() {
		return addSessionButton;
	}
	public Button getAddBillButton() {
		return addBillButton;
	}
	public Button getAddSpecialistButton() {
		return addSpecialistButton;
	}
	public Button getAddPartnerButton() {
		return addPartnerButton;
	}
	public Button getAddButton() {
		return addButton;
	}
	public Button getEditButton() {
		return editButton;
	}
	public Button getDelButton() {
		return delButton;
	}
	public MenuItem getUndo() {
		return undo;
	}
	public MenuItem getRedo() {
		return redo;
	}
	
	
	
}





























