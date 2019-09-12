package application.sub;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ButtonAddController {
	@FXML
	private VBox addButtons;
	@FXML
	private JFXButton addChildButton;
	@FXML
	private JFXButton addSessionButton;
	@FXML
	private JFXButton addBillButton;
	@FXML
	private JFXButton addSpecialistButton;
	@FXML
	private JFXButton addPartnerButton;
	@FXML
	private JFXButton addStatusButton;
	@FXML
	private JFXButton exitButton;

	// Event Listener on JFXButton[#addChildButton].onAction
	@FXML
	public void onAddChild(ActionEvent event) {
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
	// Event Listener on JFXButton[#addSessionButton].onAction
	@FXML
	public void onAddSession(ActionEvent event) {
		try {
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
	// Event Listener on JFXButton[#addBillButton].onAction
	@FXML
	public void onAddBill(ActionEvent event) {
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
	// Event Listener on JFXButton[#addSpecialistButton].onAction
	@FXML
	public void onAddSpecialist(ActionEvent event) {
		try {
		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/application/sub/AddSpecialist.fxml"));
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
	// Event Listener on JFXButton[#addPartnerButton].onAction
	@FXML
	public void onAddPartner(ActionEvent event) {
		try {
			Stage stage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/application/sub/AddPartner.fxml"));
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
	// Event Listener on JFXButton[#addStatusButton].onAction
	@FXML
	public void onAddStatus(ActionEvent event) {
		try {
		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/application/sub/AddChildStatus.fxml"));
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
	public void onExitButton(ActionEvent event) {
		Stage stage = (Stage) exitButton.getScene().getWindow();
		stage.close();
	}
	
}
