package application.sub;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.io.IOException;

import application.database.SpecialistDatabase;
import application.model.Specialist;
import javafx.event.ActionEvent;

import javafx.scene.control.TextArea;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ViewSpecialistController {
	String infor = "";
	Specialist sp = null;
	@FXML
	private TextArea specialistInfor;
	@FXML
	private Button editButton;
	@FXML
	private Button cancelButton;

	// Event Listener on Button[#editButton].onAction
	@FXML
	public void onEdit(ActionEvent event) {
		try {
			Stage stage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/application/sub/AddSpecialist.fxml"));
			Parent root = loader.load();
			AddSpecialistController add = loader.getController();
			add.setEditMode(sp.getId());
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.initModality(Modality.APPLICATION_MODAL);
			//stage.setAlwaysOnTop(true);
			//stage.setResizable(false);
			stage.showAndWait();
			} catch (IOException e) { e.printStackTrace(); }
	}
	// Event Listener on Button[#cancelButton].onAction
	@FXML
	public void onCancel(ActionEvent event) {
		Stage stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
	}
	
	@FXML 
	private void initialize()
	{
		specialistInfor.setEditable(false);
	}
	public void loadSpecialistInfor(int id)
	{
		sp = SpecialistDatabase.selectSpecialist(id);
			infor = "„⁄·Ê„«  «·«Œ’«∆Ï/«·ÿ»Ì» :\n"
					+ "«”„ «·«Œ’«∆Ï/«·ÿ»Ì» : " + sp.getName()
					+ "\n«·Â« › «·«Ê·: " + sp.getPhone1()
					+ "\n«·Â« › «·À«‰Ï: " + sp.getPhone2()
					+ "\n==================================\n";
			specialistInfor.setText(infor);
	}
}
