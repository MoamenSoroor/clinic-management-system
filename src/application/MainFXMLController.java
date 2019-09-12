package application;

import application.database.Database;
import application.sub.MenusController;
import application.sub.TableBillController;
import application.sub.TableChildController;
import application.sub.TablePartnerController;
import application.sub.TableSessionController;
import application.sub.TableSpecialistController;
import javafx.application.HostServices;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class MainFXMLController {
	
	@FXML private VBox mainVBox;	
	@FXML private ListView<String> tablesList;
	@FXML private TabPane tabPane;
	@FXML private VBox menus;
	@FXML private MenusController menusController;
	private TableSessionController sessionController;
	private TableChildController childController;
	private TableBillController billController;
	private TablePartnerController partnerController;
	private TableSpecialistController specialistController;
	private HostServices host;

	private Tab specialist;
	private Tab child;
	private Tab partner;
	private Tab session;
	private Tab bill;

	public void refreshTables(){
		childController.fillTable();
		sessionController.fillTable();
		billController.fillTable();
		partnerController.fillTable();
		specialistController.fillTable();
	}
	public void passHost(HostServices host2)
	{
		host = host2;
	}
	public HostServices getHost()
	{
		return host;
	}
	
	public MenusController getMenusController() {
		return menusController;
	}
	private AnchorPane sessionAnchor;
	private AnchorPane childAhcnor;
	private AnchorPane billAnchor;
	private AnchorPane PartnerAnchor;
	private AnchorPane SpecialistAnchor;

	

	
	
	@FXML void initialize()
	{
		initTablesList();
		LoadPanels();
		loadMainController();
		tabPane.setTabMinHeight(25);
		tabPane.setTabMinWidth(120);
		tabPane.getSelectionModel().selectedItemProperty().addListener(event ->{
			if(getSelectedTab().getId().equals("child"))
			{
				if(childController.getSelectedChildId() != -1)
				{
					childController.disableAdd(false);
				}
				else
				{
					childController.disableAdd(true);

				}
			}
			else{
				childController.disableAdd(true);
			}
		});
		addChildTab();
	}
	
	private void loadMainController()
	{
		menusController.loadMain(this);
		sessionController.loadMain(this);
		childController.loadMain(this);
		billController.loadMain(this);
		partnerController.loadMain(this);
		specialistController.loadMain(this);
		Database.loadMain(this);
	}
	private void LoadPanels()
	{
		try
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/application/sub/TableChild.fxml"));
			childAhcnor = loader.<AnchorPane>load();
			childController = loader.getController();
			
			FXMLLoader loader2 = new FXMLLoader();
			loader2.setLocation(getClass().getResource("/application/sub/TableSession.fxml"));
			sessionAnchor = loader2.<AnchorPane>load();
			sessionController = loader2.getController();
			
			FXMLLoader loader3 = new FXMLLoader();
			loader3.setLocation(getClass().getResource("/application/sub/TableBill.fxml"));
			billAnchor = loader3.<AnchorPane>load();
			billController = loader3.getController();
			
			FXMLLoader loader4 = new FXMLLoader();
			loader4.setLocation(getClass().getResource("/application/sub/TablePartner.fxml"));
			PartnerAnchor = loader4.<AnchorPane>load();
			partnerController = loader4.getController();
			
			FXMLLoader loader5 = new FXMLLoader();
			loader5.setLocation(getClass().getResource("/application/sub/TableSpecialist.fxml"));
			SpecialistAnchor = loader5.<AnchorPane>load();
			specialistController = loader5.getController();
		} catch(Exception e){ e.printStackTrace(); }
		
	}


	//list working
	private void initTablesList()
	{
		ObservableList<String> tablesName = FXCollections.observableArrayList() ;
		tablesName.addAll("ﬁ«∆„… «·«ÿ›«·" , "ﬁ«∆„… «·Ã·”« ", "ﬁ«∆„… «·›Ê« Ì—" , "ﬁ«∆„… «·„—«›ﬁÌ‰"  , "ﬁ«∆„… «·«ÿ»«¡/«·√Œ’«∆ÌÌ‰");
		tablesList.getItems().addAll(tablesName);
		tablesList.setOnMouseClicked(event ->{
			if(event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2)
			{
				int selected  = tablesList.getSelectionModel().getSelectedIndex();
				if(selected == 0)
					addChildTab();
				else if(selected == 1)
					addSessionTab();
				else if(selected == 2)
					addBillTab();
				else if(selected == 3)
					addPartnerTab();
				else if(selected == 4)
					addSpecialistTab();
				
			}
			else if(event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 1)
			{
				int selected  = tablesList.getSelectionModel().getSelectedIndex();
				if(selected == 0)
					tabPane.getSelectionModel().select(selectTab("child"));
				else if(selected == 1)
					tabPane.getSelectionModel().select(selectTab("session"));
				else if(selected == 2)
					tabPane.getSelectionModel().select(selectTab("bill"));
				else if(selected == 3)
					tabPane.getSelectionModel().select(selectTab("partner"));
				else if(selected == 4)
					tabPane.getSelectionModel().select(selectTab("specialist"));
			}
		});
		
		tablesList.getSelectionModel().selectedIndexProperty().addListener(event ->{
			
			int selected  = tablesList.getSelectionModel().getSelectedIndex();
			if(selected == 0 && tabPane.getTabs().contains(child))
				tabPane.getSelectionModel().select(selectTab("child"));
			else if(selected == 1 && tabPane.getTabs().contains(session))
				tabPane.getSelectionModel().select(selectTab("session"));
			else if(selected == 2 && tabPane.getTabs().contains(bill))
				tabPane.getSelectionModel().select(selectTab("bill"));
			else if(selected == 3 && tabPane.getTabs().contains(partner))
				tabPane.getSelectionModel().select(selectTab("partner"));
			else if(selected == 4 && tabPane.getTabs().contains(specialist))
				tabPane.getSelectionModel().select(selectTab("specialist"));
		});
		
	}
	private Tab selectTab(String id)
	{
		ObservableList<Tab> tabs = tabPane.getTabs();
		for(Tab tab: tabs)
		{
			if(tab.getId() == id)
				return tab;
		}
		return null;
	}
	private void addBillTab() {
		bill = new Tab("«·›Ê« Ì—", billAnchor); 
		bill.setId("bill");
		for(Tab tab : tabPane.getTabs())
		{
			if(tab.getContent() == billAnchor)
				return;
		}
		tabPane.getTabs().add(bill);
		tabPane.getSelectionModel().select(bill);
	}
	private void addSessionTab() {
		session = new Tab("«·Ã·”« ", sessionAnchor);
		session.setId("session");
		for(Tab tab : tabPane.getTabs())
		{
			if(tab.getContent() == sessionAnchor)
				return;
		}
		tabPane.getTabs().add(session);
		tabPane.getSelectionModel().select(session);
	}
	private void addChildTab() {
		child = new Tab("«·«ÿ›«·", childAhcnor);
		child.setId("child");
		for(Tab tab : tabPane.getTabs())
		{
			if(tab.getContent() == childAhcnor)
				return;
		}
		tabPane.getTabs().add(child);	
		tabPane.getSelectionModel().select(child);

	}
	private void addPartnerTab() {
		partner = new Tab("«·„—«›ﬁÌ‰", PartnerAnchor);
		partner.setId("partner");
		for(Tab tab : tabPane.getTabs())
		{
			if(tab.getContent() == PartnerAnchor)
				return;
		}
		tabPane.getTabs().add(partner);	
		tabPane.getSelectionModel().select(partner);
	}
	private void addSpecialistTab() {
		specialist = new Tab("«·«Œ’«∆ÌÌ‰/«·«ÿ»«¡", SpecialistAnchor);
		specialist.setId("specialist");
		for(Tab tab : tabPane.getTabs())
		{
			if(tab.getContent() == SpecialistAnchor)
				return;
		}
		tabPane.getTabs().add(specialist);	
		tabPane.getSelectionModel().select(specialist);
	}

	public TableSessionController getSessionController() {
		return sessionController;
	}
	public TableChildController getChildController() {
		return childController;
	}
	public TableBillController getBillController() {
		return billController;
	}

	public TablePartnerController getPartnerController() {
		return partnerController;
	}
	public TableSpecialistController getSpecialistController() {
		return specialistController;
	}
	public Tab getSelectedTab()
	{
		if(tabPane.getTabs().size() == 0)
			return null;
		return tabPane.getSelectionModel().getSelectedItem();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
