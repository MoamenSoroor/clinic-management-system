package application.sub;

import java.io.IOException;

import application.MainFXMLController;
import application.database.BillDatabase;
import application.database.PropertiesDataBase;
import application.database.Table;
import application.model.BillModel;
import application.model.TableProperties;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;

public class TableBillController {
	MainFXMLController main;

	@FXML
	private AnchorPane billTableAnchor;
	@FXML
	private TableView<BillModel> TableBill;
	@FXML
	private TableColumn <BillModel , Integer>colId;
	@FXML
	private TableColumn <BillModel , String>colChildName;
	@FXML
	private TableColumn <BillModel , String>colPayDay;
	@FXML
	private TableColumn <BillModel , String>colPayDate;
	@FXML
	private TableColumn <BillModel , String>colPartnerName;
	@FXML
	private TableColumn <BillModel , Double>colPayment;
	@FXML
	private TableColumn <BillModel , Double> colOneSessionPrice;
	@FXML
	private TableColumn <BillModel , Double>colRestMoney;
	@FXML
	private TableColumn <BillModel , Double>colAllMoney;
	@FXML
	private TableColumn <BillModel , Integer>colNewMaxSession;
	@FXML 
	private TextField searchChildName;
	@FXML 
	private TextField searchPartnerName;
	@FXML 
	private Button clearButton;
	
	@FXML 
	private void onClearButton()
	{
		searchChildName.setText("");
		searchPartnerName.setText("");
		fillTable();
	}
	public void disableSearchChildName(boolean dis){
		searchChildName.setText("");
		searchChildName.setDisable(dis);
	}
	public void disableSearchPartnerName(boolean dis){
		searchPartnerName.setText("");
		searchPartnerName.setDisable(dis);
	}
	
	@FXML
	void initialize()
	{
		TableBill.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		colId.setCellValueFactory(e-> e.getValue().getId().asObject());
		colChildName.setCellValueFactory(e->e.getValue().getChildName());
		colPayDay.setCellValueFactory(e->e.getValue().getPayDay());
		colPayDate.setCellValueFactory(e->e.getValue().getPayDate());
		colPartnerName.setCellValueFactory(e->e.getValue().getPartnerName());
		colPayment.setCellValueFactory(e->e.getValue().getNewPayment().asObject());
		colOneSessionPrice.setCellValueFactory(e->e.getValue().getNewOneSessionPrice().asObject());
		colRestMoney.setCellValueFactory(e->e.getValue().getRestMoney().asObject());
		colAllMoney.setCellValueFactory(e->e.getValue().getAllMoney().asObject());
		colNewMaxSession.setCellValueFactory(e->e.getValue().getNewMaxSession().asObject());
	
		colId.setStyle( "-fx-alignment: CENTER;");
		colPayDay.setStyle("-fx-alignment: CENTER");
		colPayDate.setStyle("-fx-alignment: CENTER");
		colPayment.setStyle("-fx-alignment: CENTER");
		colOneSessionPrice.setStyle("-fx-alignment: CENTER");
		colRestMoney.setStyle("-fx-alignment: CENTER");
		colAllMoney.setStyle("-fx-alignment: CENTER");
		colNewMaxSession.setStyle("-fx-alignment: CENTER");

		
		TableBill.getItems().setAll(BillDatabase.selectAllBillModel());
		
		TableBill.setOnMouseClicked(event->{
			if(event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2){
				loadViewBill();
			}
		});
		initSearch();
		setColumnsVisability(PropertiesDataBase.selectProperties(new TableProperties(Table.BILL)));
	}
	public void setColumnsVisability(TableProperties pro){
		/*
		colId;
		colPayDay;
		colPayDate;
		colPartnerName;
		colRestMoney;
		colOneSessionPrice
		colAllMoney;
		colNewMaxSession;
		 */
		colId.setVisible(pro.isProperty1());
		colPayDay.setVisible(pro.isProperty2());
		colPayDate.setVisible(pro.isProperty3());
		colPartnerName.setVisible(pro.isProperty4());
		colOneSessionPrice.setVisible(pro.isProperty5());
		colRestMoney.setVisible(pro.isProperty6());
		colAllMoney.setVisible(pro.isProperty7());
		colNewMaxSession.setVisible(pro.isProperty8());
		
	}
	public void loadViewBill() {
		try {
			Stage stage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/application/sub/ViewBill.fxml"));
			Parent root = loader.<VBox>load();
			Scene scene = new Scene(root);
			ViewBillController view = loader.getController();
			view.loadBillInfor(getSelectedBillId());
			stage.setScene(scene);
			//stage.setAlwaysOnTop(true);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setResizable(false);
			stage.showAndWait();
			} catch (IOException e) { e.printStackTrace(); }
		
	}

	@FXML
	private void initSearch()
	{
		searchChildName.setOnKeyTyped(event->{
			String chName = searchChildName.getText();
			String PartName = searchPartnerName.getText();
			TableBill.setItems(BillDatabase.searchBill(chName, PartName));
		});
		searchPartnerName.setOnKeyTyped(event->{
			String chName = searchChildName.getText();
			String PartName = searchPartnerName.getText();
			TableBill.setItems(BillDatabase.searchBill(chName, PartName));
		});
	}
	public void loadMain(MainFXMLController main)
	{
		this.main = main;
	}
	public void fillTable()
	{
		TableBill.getItems().setAll(BillDatabase.selectAllBillModel());
	}
	public int getSelectedBillId()
	{
		if(TableBill.getSelectionModel().getSelectedItem() == null)
			return -1;
		return TableBill.getSelectionModel().getSelectedItem().getId().getValue();
	}
	public ObservableList<Integer> getSelectedBillsId()
	{
		ObservableList<BillModel> bills = TableBill.getSelectionModel().getSelectedItems();
		ObservableList<Integer> billId = FXCollections.observableArrayList();
		for(BillModel ch : bills)
			billId.add(ch.getId().getValue());
		return billId;
	}
	
	/*
	olId;
	colChildName;
	colPayDay;
	colPayDate;
	colExpiredDate;
	colDuration;
	colPartnerName;
	colPayment;
	colRestMoney;
	colAllMoney;
	colNewMaxSession;
	 */

}
