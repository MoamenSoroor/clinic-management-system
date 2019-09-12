package application.sub;

import java.io.IOException;

import application.MainFXMLController;
import application.database.PartnerDatabase;
import application.database.PropertiesDataBase;
import application.database.Table;
import application.model.PartnerModel;
import application.model.TableProperties;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.TableView;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;

public class TablePartnerController {
	MainFXMLController main;
	@FXML
	private AnchorPane PartnerTableAnchor;
	@FXML
	private TextField searchPartnerName;
	@FXML
	private TableView<PartnerModel> tablePartner;
	@FXML
	private TableColumn <PartnerModel , Integer>colId;
	@FXML
	private TableColumn <PartnerModel , String>colName;
	@FXML
	private TableColumn <PartnerModel , String>colGroundPhone;
	@FXML
	private TableColumn <PartnerModel , String>colPhone1;
	@FXML
	private TableColumn <PartnerModel , String>colPhone2;
	@FXML
	private TableColumn <PartnerModel , String>colAddress;
	
	@FXML 
	private Button clearButton;
	
	@FXML 
	private void onClearButton()
	{
		searchPartnerName.setText("");
		fillTable();
	}
	
	
	@FXML
	private void initialize()
	{
		tablePartner.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		colId.setCellValueFactory(e-> e.getValue().getId().asObject());
		colName.setCellValueFactory(e-> e.getValue().getName());
		colGroundPhone.setCellValueFactory(e-> e.getValue().getGroundPhone());
		colPhone1.setCellValueFactory(e-> e.getValue().getPhone2());
		colPhone2.setCellValueFactory(e-> e.getValue().getPhone3());
		colAddress.setCellValueFactory(e-> e.getValue().getAddress());
		tablePartner.setItems(PartnerDatabase.selectAllPartnerModel());
		
		colId.setStyle( "-fx-alignment: CENTER;");
		colGroundPhone.setStyle("-fx-alignment: CENTER");
		colPhone1.setStyle("-fx-alignment: CENTER");
		colPhone2.setStyle("-fx-alignment: CENTER");

		tablePartner.setOnMouseClicked((MouseEvent event)->{
			if(event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2)
				loadFxml("ViewPartner.fxml");
		});
		initSearch();
		setColumnsVisability(PropertiesDataBase.selectProperties(new TableProperties(Table.PRTNER)));

	}


	public void setColumnsVisability(TableProperties selectProperties) {
		colId.setVisible(selectProperties.isProperty1());
		colGroundPhone.setVisible(selectProperties.isProperty2());
		colPhone1.setVisible(selectProperties.isProperty3());
		colPhone2.setVisible(selectProperties.isProperty4());
		colAddress.setVisible(selectProperties.isProperty5());
		
	}


	private void initSearch() {
		
		searchPartnerName.setOnKeyTyped(event->{
			String name = searchPartnerName.getText();
			tablePartner.setItems(PartnerDatabase.SearchPartner(name));
		});
	}


	public void fillTable() {
		tablePartner.setItems(PartnerDatabase.selectAllPartnerModel());
	}
	public void loadMain(MainFXMLController mainFXMLController) {
		main = mainFXMLController;
	}
	public int getSelectedPartnerId()
	{
		if(tablePartner.getSelectionModel().getSelectedItem() == null)
			return -1;
		return tablePartner.getSelectionModel().getSelectedItem().getId().getValue();
	}
	public ObservableList<Integer> getSelectedPartnersId()
	{
		ObservableList<PartnerModel> s = tablePartner.getSelectionModel().getSelectedItems();
		ObservableList<Integer> sId = FXCollections.observableArrayList();
		for(PartnerModel oneS : s)
			sId.add(oneS.getId().getValue());
		return sId;
	}
	public void loadFxml(String fileName)
	{
		try {
			Stage stage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/application/sub/" + fileName));
			Parent root = loader.<VBox>load();
			Scene scene = new Scene(root);
			if(fileName.equals("ViewPartner.fxml"))
			{
				ViewPartnerController view = loader.getController();
				view.loadPartnerInfor(getSelectedPartnerId());
			}
			stage.setScene(scene);
			//stage.setAlwaysOnTop(true);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setResizable(false);
			stage.showAndWait();
			} catch (IOException e) { e.printStackTrace(); }
	}
	

}

/*
 	colId;
	colGroundPhone;
	colPhone1;
	colPhone2;
	colAddress;
 */
	
