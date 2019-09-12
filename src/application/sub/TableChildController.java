package application.sub;

import java.io.IOException;

import application.MainFXMLController;
import application.database.ChildDatabase;
import application.database.PropertiesDataBase;
import application.database.Table;
import application.model.ChildModel;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;

public class TableChildController {
	MainFXMLController main;
	@FXML
	private AnchorPane childTableAnchor;
	@FXML
	private TableView<ChildModel> tableChid;
	@FXML
	private TableColumn<ChildModel , Integer> colId;
	@FXML
	private TableColumn <ChildModel , String>colName;
	@FXML
	private TableColumn <ChildModel , String>colStatus;
	@FXML
	private TableColumn <ChildModel , String>colPartner1;
	@FXML
	private TableColumn <ChildModel , String>colPartner2;
	@FXML
	private TableColumn <ChildModel , Integer>colMaxSession;
	@FXML
	private TableColumn <ChildModel , Integer>colDoneSession;
	@FXML
	private TableColumn <ChildModel , String>colExpired;
	
	@FXML
	private TextField searchChildName;
	@FXML
	private TextField searchPartner1Name;
	@FXML
	private TextField searchPartner2Name;
	
	@FXML 
	private Button clearButton;
	
	@FXML 
	private void onClearButton()
	{
		searchChildName.setText("");
		searchPartner1Name.setText("");
		searchPartner2Name.setText("");
		fillTable();
	}
	
	public AnchorPane getChildTableAnchor()
	{
		return childTableAnchor;
	}
	
	public void disableSearchPart1Name(boolean dis){
		searchPartner1Name.setText("");
		searchPartner1Name.setDisable(dis);
	}
	public void disableSearchPart2Name(boolean dis){
		searchPartner2Name.setText("");
		searchPartner2Name.setDisable(dis);
	}
	
	
	@FXML
	void initialize()
	{
		tableChid.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		colId.setCellValueFactory(e-> e.getValue().getId().asObject());
		colName.setCellValueFactory(e-> e.getValue().getName());
		colStatus.setCellValueFactory(e-> e.getValue().getStatus());
		colPartner1.setCellValueFactory(e-> e.getValue().getP1());
		colPartner2.setCellValueFactory(e-> e.getValue().getP2());
		colMaxSession.setCellValueFactory(e-> e.getValue().getNumberOfSessions().asObject());
		colDoneSession.setCellValueFactory(e-> e.getValue().getDoneSessions().asObject());
		colExpired.setCellValueFactory(e-> e.getValue().getExpired());
		
		colId.setStyle( "-fx-alignment: CENTER;");
		colMaxSession.setStyle("-fx-alignment: CENTER");
		colDoneSession.setStyle("-fx-alignment: CENTER");
		colExpired.setStyle("-fx-alignment: CENTER");
		colStatus.setStyle("-fx-alignment: CENTER");
		
		tableChid.getItems().setAll(ChildDatabase.selectAllChildModel());
		tableChid.setOnMouseClicked((MouseEvent event) ->{
			if(event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2)
			{
				loadFxml("ViewChild.fxml");
			}
			if(tableChid.getSelectionModel().getSelectedItems() == null)
			{
				disableAdd(true);
			}
			else
			{
				if(getSelectedChildId() != -1)
					disableAdd(false);
			}
		});
		initSearch();
		setColumnsVisability(PropertiesDataBase.selectProperties(new TableProperties(Table.CHILD)));

	}
	public void setColumnsVisability(TableProperties selectProperties) {
		colId.setVisible(selectProperties.isProperty1());
		colStatus.setVisible(selectProperties.isProperty2());
		colPartner1.setVisible(selectProperties.isProperty3());
		colPartner2.setVisible(selectProperties.isProperty4());
		colMaxSession.setVisible(selectProperties.isProperty5());
		colDoneSession.setVisible(selectProperties.isProperty6());
		colExpired.setVisible(selectProperties.isProperty7());
		
	}

	public void disableAdd(boolean dis)
	{
		main.getMenusController().getAddBill().setDisable(dis);
		main.getMenusController().getAddBillButton().setDisable(dis);
		main.getMenusController().getAddSession().setDisable(dis);
		main.getMenusController().getAddSessionButton().setDisable(dis);
	}
	public void loadMain(MainFXMLController main)
	{
		this.main = main;
	}
	public void fillTable()
	{
		tableChid.getItems().setAll(ChildDatabase.selectAllChildModel());
		main.getMenusController().getAddBillButton().setDisable(true);
		main.getMenusController().getAddSessionButton().setDisable(true);

	}
	
	public int getSelectedChildId()
	{
		if(tableChid.getSelectionModel().getSelectedItem() == null)
			return -1;
		return tableChid.getSelectionModel().getSelectedItem().getId().getValue();
	}
	public ObservableList<Integer> getSelectedChildsId()
	{
		ObservableList<ChildModel> childs = tableChid.getSelectionModel().getSelectedItems();
		ObservableList<Integer> childId = FXCollections.observableArrayList();
		for(ChildModel ch : childs)
			childId.add(ch.getId().getValue());
		return childId;
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
				view.loadChildInfor(getSelectedChildId());
			}
			stage.setScene(scene);
			//stage.setAlwaysOnTop(true);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setResizable(false);
			stage.showAndWait();
			} catch (IOException e) { e.printStackTrace(); }
	}

	public void initSearch()
	{
		// searchChildName;
		// searchPartner1Name;
		// searchPartner2Name;		
		searchChildName.setOnKeyTyped(event->{
			String name = searchChildName.getText();
			String p1Name = searchPartner1Name.getText();
			String p2Name = searchPartner2Name.getText();
			tableChid.setItems(ChildDatabase.search(name, p1Name, p2Name));
		});
		searchPartner1Name.setOnKeyTyped(event->{
			String name = searchChildName.getText();
			String p1Name = searchPartner1Name.getText();
			String p2Name = searchPartner2Name.getText();
			tableChid.setItems(ChildDatabase.search(name, p1Name, p2Name));
		});
		searchPartner2Name.setOnKeyTyped(event->{
			String name = searchChildName.getText();
			String p1Name = searchPartner1Name.getText();
			String p2Name = searchPartner2Name.getText();
			tableChid.setItems(ChildDatabase.search(name, p1Name, p2Name));
		});

		
	}
}

/*
colId;
colStatus;
colPartner1;
colPartner2;
colMaxSession;
colDoneSession;
colExpired;
 */


























