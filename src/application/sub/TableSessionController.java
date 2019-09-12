package application.sub;

import java.io.IOException;

import application.MainFXMLController;
import application.database.PropertiesDataBase;
import application.database.SessionDatabase;
import application.database.Table;
import application.model.SessionModel;
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

public class TableSessionController {
	
	MainFXMLController main;

	@FXML
	private AnchorPane sessionTableAnchor;
	@FXML
	private TableView<SessionModel> tableSession;
	@FXML
	private TableColumn<SessionModel , Integer> colId;
	@FXML
	private TableColumn<SessionModel , String> colSessionDate;
	@FXML
	private TableColumn<SessionModel , String> colChildName;
	@FXML
	private TableColumn<SessionModel , String> colStatus;
	@FXML
	private TableColumn<SessionModel , String> ColSpName;
	@FXML
	private TableColumn<SessionModel , String> colPartner1;
	@FXML
	private TableColumn<SessionModel , Integer> colDoneSessions;
	@FXML
	private TableColumn<SessionModel , String> colExpired;
	
	@FXML 
	private TextField searchChildName;
	@FXML 
	private TextField searchPartnerName;
	@FXML 
	private TextField searchSpecialistName;
	
	@FXML 
	private Button clearButton;
	
	@FXML 
	private void onClearButton()
	{
		searchChildName.setText("");
		searchPartnerName.setText("");
		searchSpecialistName.setText("");
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
	public void disableSearchSpecialistName(boolean dis){
		searchSpecialistName.setText("");
		searchSpecialistName.setDisable(dis);
	}
	
	@FXML
	void initialize()
	{
		tableSession.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		colId.setCellValueFactory(e-> e.getValue().getId().asObject());
		colSessionDate.setCellValueFactory(e-> e.getValue().getSessionDate());
		colChildName.setCellValueFactory(e-> e.getValue().getChildName());
		colPartner1.setCellValueFactory(e-> e.getValue().getPartnerName());
		colStatus.setCellValueFactory(e-> e.getValue().getStatus());
		ColSpName.setCellValueFactory(e-> e.getValue().getSpecialistName());
		colExpired.setCellValueFactory(e-> e.getValue().getExpired());
		colDoneSessions.setCellValueFactory(e-> e.getValue().getChildDoneSessions().asObject());
		tableSession.getItems().setAll(SessionDatabase.selectAllSessionModel());
		
		colId.setStyle( "-fx-alignment: CENTER;");
		colSessionDate.setStyle("-fx-alignment: CENTER");
		colStatus.setStyle("-fx-alignment: CENTER");
		colExpired.setStyle("-fx-alignment: CENTER");
		colDoneSessions.setStyle("-fx-alignment: CENTER");

		tableSession.setOnMouseClicked(event->{
			if(event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2){
				loadViewSession();
			}
		});
		initSearch();
		setColumnsVisability(PropertiesDataBase.selectProperties(new TableProperties(Table.SESSION)));

	}
	
	public void setColumnsVisability(TableProperties selectProperties) {
		colId.setVisible(selectProperties.isProperty1());
		colSessionDate.setVisible(selectProperties.isProperty2());
		colPartner1.setVisible(selectProperties.isProperty3());
		colStatus.setVisible(selectProperties.isProperty4());
		ColSpName.setVisible(selectProperties.isProperty5());
		colExpired.setVisible(selectProperties.isProperty6());
		colDoneSessions.setVisible(selectProperties.isProperty7());
	}

	private void loadViewSession() {
		try {
			Stage stage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/application/sub/ViewSession.fxml"));
			Parent root = loader.<VBox>load();
			Scene scene = new Scene(root);
			ViewSessionController view = loader.getController();
			view.loadSessionInfor(getSelectedSessionId());
			stage.setScene(scene);
			//stage.setAlwaysOnTop(true);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setResizable(false);
			stage.showAndWait();
			} catch (IOException e) { e.printStackTrace(); }
		
	}

	private void initSearch() {
		searchChildName.setOnKeyTyped(event->{
			String chName = searchChildName.getText();
			String partName = searchPartnerName.getText();
			String spName = searchSpecialistName.getText();
			tableSession.setItems(SessionDatabase.searchSessions(chName, partName, spName));

		});
		searchPartnerName.setOnKeyTyped(event->{
			String chName = searchChildName.getText();
			String partName = searchPartnerName.getText();
			String spName = searchSpecialistName.getText();
			tableSession.setItems(SessionDatabase.searchSessions(chName, partName, spName));
		});
		searchSpecialistName.setOnKeyTyped(event->{
			String chName = searchChildName.getText();
			String partName = searchPartnerName.getText();
			String spName = searchSpecialistName.getText();
			tableSession.setItems(SessionDatabase.searchSessions(chName, partName, spName));
		});
		
	}

	public void loadMain(MainFXMLController main)
	{
		this.main = main;
	}
	public void fillTable()
	{
		tableSession.getItems().setAll(SessionDatabase.selectAllSessionModel());
	}
	public int getSelectedSessionId()
	{
		if(tableSession.getSelectionModel().getSelectedItem() == null)
			return -1;
		return tableSession.getSelectionModel().getSelectedItem().getId().getValue();
	}
	public ObservableList<Integer> getSelectedSessionsId()
	{
		ObservableList<SessionModel> s = tableSession.getSelectionModel().getSelectedItems();
		ObservableList<Integer> sId = FXCollections.observableArrayList();
		for(SessionModel oneS : s)
			sId.add(oneS.getId().getValue());
		return sId;
	}

}
