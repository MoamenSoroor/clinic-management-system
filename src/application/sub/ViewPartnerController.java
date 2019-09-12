package application.sub;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.io.IOException;
import java.util.ArrayList;

import application.database.ChildDatabase;
import application.database.PartnerDatabase;
import application.model.Child;
import application.model.Partner;
import javafx.event.ActionEvent;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ViewPartnerController {
	Partner p1;
	String infor = "";
	@FXML
	private TextArea partnerInfor;
	@FXML
	private Button editButton;
	@FXML
	private Button cancelButton;
	@FXML
	private ImageView childPhoto;
	@FXML
	private ListView <String>childsList;

	// Event Listener on Button[#editButton].onAction
	@FXML
	public void onEdit(ActionEvent event) {
		try {
			Stage stage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/application/sub/AddPartner.fxml"));
			Parent root = loader.load();
			AddPartnerController add = loader.getController();
			add.setEditMode(p1.getId());
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
		childsList.setOnMouseClicked((MouseEvent event) ->{
			if(event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2)
			{
				loadFxml("ViewChild.fxml");
			}
			else if(event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 1)
			{
				Child ch =  ChildDatabase.selectChild(childsList.getSelectionModel().getSelectedItem());
				childPhoto.setImage(new Image("file:" + ch.getPhoto().getAbsolutePath()));
			}
		});
		
	}
	public void loadPartnerInfor(int id)
	{
		p1 = PartnerDatabase.selectPartner(id);
		ArrayList<Child> childs = ChildDatabase.selectChildOfPartner(id);
		for(Child ch : childs)
		{
			childsList.getItems().add(ch.getName());
		}
			infor = "„⁄·Ê„«  «·„—«›ﬁ «·«Ê·:\n"
					+ "==================================\n"
					+ "«”„ «·„—«›ﬁ «·«Ê·: " + p1.getName()
					+ "\n«·Â« › «·«—÷Ï: " + p1.getGroundPhone()
					+ "\n«·Â« › «·«Ê·: " + p1.getPhone1()
					+ "\n«·Â« › «·À«‰Ï: " + p1.getPhone2()
					+ "\n««·⁄‰Ê«‰: " + p1.getAddress()
					+ "\n==================================\n";
		partnerInfor.setText(infor);
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
				Child ch = ChildDatabase.selectChild(childsList.getSelectionModel().getSelectedItem());
				view.loadChildInfor(ch.getId());
			}
			stage.setScene(scene);
			//stage.setAlwaysOnTop(true);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setResizable(false);
			stage.showAndWait();
			} catch (IOException e) { e.printStackTrace(); }
	}
}
