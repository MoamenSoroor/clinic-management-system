package application.sub;

import java.io.IOException;

import application.MainFXMLController;
import application.database.PropertiesDataBase;
import application.database.SpecialistDatabase;
import application.database.Table;
import application.model.SpecialistModel;
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

public class TableSpecialistController {
	MainFXMLController main;
	@FXML
	private AnchorPane SpecialistTableAnchor;
	@FXML
	private TextField searchSpecialistName;
	@FXML
	private TableView<SpecialistModel> tableSpecialist;
	@FXML
	private TableColumn <SpecialistModel , Integer> colId;
	@FXML
	private TableColumn <SpecialistModel , String>colName;
	@FXML
	private TableColumn <SpecialistModel , String>colPhone1;
	@FXML
	private TableColumn <SpecialistModel , String>colPhone2;

	@FXML 
	private Button clearButton;
	
	@FXML 
	private void onClearButton()
	{
		searchSpecialistName.setText("");
		fillTable();
	}
	@FXML
	private void initialize()
	{
		tableSpecialist.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		colId.setCellValueFactory(e-> e.getValue().getId().asObject());
		colName.setCellValueFactory(e-> e.getValue().getName());
		colPhone1.setCellValueFactory(e-> e.getValue().getPhone1());
		colPhone2.setCellValueFactory(e-> e.getValue().getPhone2());
		tableSpecialist.setItems(SpecialistDatabase.selectAllSpecialistModel());
		
		colId.setStyle( "-fx-alignment: CENTER;");
		colPhone1.setStyle("-fx-alignment: CENTER");
		colPhone2.setStyle("-fx-alignment: CENTER");
		
		tableSpecialist.setOnMouseClicked((MouseEvent event)->{
			if(event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2)
				loadFxml("ViewSpecialist.fxml");
		});
		initSearch();
		setColumnsVisability(PropertiesDataBase.selectProperties(new TableProperties(Table.SPECIALIST)));

	}

	public void setColumnsVisability(TableProperties selectProperties) {
		colId.setVisible(selectProperties.isProperty1());
		colPhone1.setVisible(selectProperties.isProperty2());
		colPhone2.setVisible(selectProperties.isProperty3());
	}
	private void initSearch() {
		searchSpecialistName.setOnKeyTyped(event ->{
			String name = searchSpecialistName.getText();
			tableSpecialist.setItems(SpecialistDatabase.searchSpecialist(name));
		});
		
	}
	public void loadMain(MainFXMLController mainFXMLController) {
		main = mainFXMLController;
	}
	public int getSelectedSpecialistId()
	{
		if(tableSpecialist.getSelectionModel().getSelectedItem() == null)
			return -1;
		return tableSpecialist.getSelectionModel().getSelectedItem().getId().getValue();
	}
	public ObservableList<Integer> getSelectedSpecialistsId()
	{
		ObservableList<SpecialistModel> s = tableSpecialist.getSelectionModel().getSelectedItems();
		ObservableList<Integer> sId = FXCollections.observableArrayList();
		for(SpecialistModel oneS : s)
			sId.add(oneS.getId().getValue());
		return sId;
	}

	public void fillTable() {
		tableSpecialist.setItems(SpecialistDatabase.selectAllSpecialistModel());
	}

	public void loadFxml(String fileName)
	{
		try {
			Stage stage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/application/sub/" + fileName));
			Parent root = loader.<VBox>load();
			Scene scene = new Scene(root);
			if(fileName.equals("ViewSpecialist.fxml"))
			{
				ViewSpecialistController view = loader.getController();
				view.loadSpecialistInfor(getSelectedSpecialistId());
			}
			stage.setScene(scene);
			//stage.setAlwaysOnTop(true);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setResizable(false);
			stage.showAndWait();
			} catch (IOException e) { e.printStackTrace(); }
	}



}
